package org.zunpeng.core.aspect;

import com.alibaba.fastjson.JSONObject;
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

	@Around("execution(* org.zunpeng.mapper.*.*(..))")
	public Object readCache(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
		Method method = joinPointObject.getMethod();

		Caching caching = method.getAnnotation(Caching.class);
		String className = method.getDeclaringClass().toString();
		String prefix = Md5Utils.md5(className);
		if(caching != null && caching.value().getId() == (Caching.CacheType.REMOVE.getId())){
			//按插入或者更新逻辑处理
			joinPoint.proceed();

			//删除已存在缓存
			Set<String> keys = stringRedisTemplate.keys(prefix + ":*");
			if(keys != null){
				stringRedisTemplate.delete(keys);
			}

			return null;
		} else {
			//按查询逻辑处理
			Object[] parameters = joinPoint.getArgs();
			Class<?> returnType = method.getReturnType();

			String methodName = method.toString();

			//构造存储的key  类名:方法名:具体参数
			String key = prefix + ":" + Md5Utils.md5(methodName);
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

}
