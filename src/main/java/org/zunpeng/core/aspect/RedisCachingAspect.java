package org.zunpeng.core.aspect;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.oldpeng.core.utils.BeanCopyUtils;
import com.oldpeng.core.utils.Md5Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zunpeng.core.annotation.Caching;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by dapeng on 2016/10/1.
 */
@Component
@Aspect
@Transactional
public class RedisCachingAspect {

	private static Logger logger = LoggerFactory.getLogger(RedisCachingAspect.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	private String redisDbPrefix = "redis_database:";

	@Around("execution(* org.zunpeng.mapper.*.*(..))")
	public Object readCache(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
		Method method = joinPointObject.getMethod();
		Object[] parameters = joinPoint.getArgs();

		Caching caching = method.getAnnotation(Caching.class);
		Class<?> declaringClass = method.getDeclaringClass();
		String className = declaringClass.toString();
		String classBrief = Md5Utils.md5(className);

		if(caching != null
				&& (caching.operator().getId() == Caching.OperatorType.SAVE.getId()
					|| caching.operator().getId() == Caching.OperatorType.UPDATE.getId()
					|| caching.operator().getId() == Caching.OperatorType.CLEAR.getId())){

			Object cloneParam = null;
			if(parameters != null && parameters.length == 1){
				cloneParam = BeanCopyUtils.copy(parameters[0], parameters[0].getClass());
			}

			//按插入或者更新逻辑处理
			joinPoint.proceed();

			/**
			 * 如果插入数据（save），删除获取列表的缓存信息
			 * 如果是更新数据（update），删除获取列表的缓存信息和此次更新的数据缓存
			 */
			Set<String> selectKeyPrefixSet = Sets.newHashSet();
			Set<String> selectKeySet = Sets.newHashSet();

			if(caching.operator().getId() == Caching.OperatorType.SAVE.getId()
					|| caching.operator().getId() == Caching.OperatorType.UPDATE.getId()){
				selectKeyPrefixSet = retrieveSelectKeyPrefixSet(redisDbPrefix + classBrief, declaringClass);

				if(caching.operator().getId() == Caching.OperatorType.UPDATE.getId() && parameters != null && parameters.length == 1){
					selectKeySet = retrieveSelectKeySet(selectKeyPrefixSet, cloneParam);
				}
			}

			//删除已存在缓存
			Set<String> keys = stringRedisTemplate.keys("*:" + classBrief + ":*");
			Set<String> deleteKeys = new HashSet<>(keys);

			for(String key : keys){
				if(startWith(key, selectKeyPrefixSet)){
					deleteKeys.remove(key);
				}
			}

			deleteKeys.addAll(selectKeySet);


			if(deleteKeys != null){
				stringRedisTemplate.delete(deleteKeys);
			}

			return null;
		} else {
			//按查询逻辑处理
			Class<?> returnType = method.getReturnType();

			String methodName = method.toString();

			if(caching != null){
				Class<?>[] clazzes = caching.deps();

				/**
				 * 添加数据依赖的类名
				 */
				for(Class<?> clazz : clazzes){
					classBrief += (":" + Md5Utils.md5(clazz.toString()));
				}
			}

			//构造存储的key  类名:方法名:具体参数 或 多个类名:方法名:具体参数
			String key = redisDbPrefix + classBrief + ":" + Md5Utils.md5(methodName);
			if(parameters != null){
				key += (":" + Md5Utils.md5(JSONObject.toJSONString(parameters)));
			}

			BoundValueOperations<String, String> boundValueOperations = stringRedisTemplate.boundValueOps(key);
			String value = boundValueOperations.get();

			if(stringRedisTemplate.hasKey(key)){
				return JSONObject.parseObject(value, returnType);
			} else {
				Object returnObject = joinPoint.proceed();

				if(returnObject != null){
					boundValueOperations.set(JSONObject.toJSONString(returnObject));
					boundValueOperations.expire(1, TimeUnit.DAYS);
				}

				return returnObject;
			}
		}

	}

	private boolean startWith(String key, Set<String> selectKeyPrefixSet) {
		if(selectKeyPrefixSet == null || selectKeyPrefixSet.size() == 0){
			return false;
		}

		for(String prefix : selectKeyPrefixSet){
			if(key.startsWith(prefix)){
				return true;
			}
		}

		return false;
	}

	private Set<String> retrieveSelectKeySet(Set<String> selectKeyPrefixSet, Object parameter) {
		Set<String> selectKeySet = Sets.newHashSet();

		Method[] methods = parameter.getClass().getMethods();

		try {
			for(Method method : methods){
				String methodName = method.getName();
				if(methodName.equals("getId") || methodName.equals("getSlug")){
					Object result = method.invoke(parameter);
					if(result == null){
						continue;
					}

					String paramValue = Md5Utils.md5(JSONObject.toJSONString(new Object[]{result}));

					for(String selectKeyPrefix : selectKeyPrefixSet){
						selectKeySet.add(selectKeyPrefix + ":" + paramValue);
					}
				}
			}
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
		}

		return selectKeySet;
	}

	private Set<String> retrieveSelectKeyPrefixSet(String prefix, Class<?> declaringClass) {
		Set<String> prefixSet = Sets.newHashSet();

		Method[] methods = declaringClass.getMethods();
		for(Method method : methods){
			Caching caching = method.getAnnotation(Caching.class);
			if(caching != null && caching.operator().getId() == Caching.OperatorType.GET_SINGLE.getId()){
				prefixSet.add(prefix + ":" + Md5Utils.md5(method.toString()));
			}
		}

		return prefixSet;
	}

}
