package org.zunpeng.service.account;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zunpeng.domain.AccountInfo;
import org.zunpeng.mapper.ShiroAccountInfoMapper;
import org.zunpeng.mapper.result.AccountPermissionBean;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dapeng on 2016/10/16.
 */
@Service
@Transactional
public class ShiroAccountServiceImpl implements ShiroAccountService {

	@Autowired
	private ShiroAccountInfoMapper shiroAccountInfoMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public AccountInfo getById(Long accountId) {
		return shiroAccountInfoMapper.getById(accountId);
	}

	@Override
	public AccountInfo getByUsername(String username) {
		String prefix = "zeus_reids_shiro_account_info:";
		String key = prefix + username;

		if(stringRedisTemplate.hasKey(key)){
			return readFromRedis(key, AccountInfo.class);
		} else {
			return saveToRedis(key, shiroAccountInfoMapper.getByUsername(username));
		}
	}

	@Override
	public Collection<String> getAllRolesByAccountId(Long accountId) {
		String prefix = "zeus_reids_shiro_role_info:";
		String key = prefix + accountId;

		if(stringRedisTemplate.hasKey(key)){
			Set<String> roleSet = readFromRedis(key, Set.class);
			return roleSet == null ? Sets.newHashSet() : roleSet;
		} else {

			List<AccountPermissionBean> accountPermissionBeanList = shiroAccountInfoMapper.getAllPermissionByAccountId(accountId);
			if(accountPermissionBeanList == null){
				return Sets.newHashSet();
			}

			Set<String> roleSet = accountPermissionBeanList.stream().map((accountPermissionBean) -> accountPermissionBean.getPermission())
									.collect(Collectors.toSet());

			saveToRedis(key, roleSet);
			return roleSet == null ? Sets.newHashSet() : roleSet;
		}
	}

	@Override
	public Collection<String> getAllPermissionsByAccountId(Long accountId) {
		String prefix = "zeus_reids_shiro_permission_info:";
		String key = prefix + accountId;

		if(stringRedisTemplate.hasKey(key)){
			Set<String> permissionSet = readFromRedis(key, Set.class);
			return permissionSet == null ? Sets.newHashSet() : permissionSet;
		} else {

			List<AccountPermissionBean> accountPermissionBeanList = shiroAccountInfoMapper.getAllRolePermissionByAccountId(accountId);
			if(accountPermissionBeanList == null){
				return Sets.newHashSet();
			}

			Set<String> permissionSet = accountPermissionBeanList.stream().map(accountPermissionBean -> accountPermissionBean.getPermission())
											.collect(Collectors.toSet());

			saveToRedis(key, permissionSet);
			return permissionSet == null ? Sets.newHashSet() : permissionSet;
		}
	}

	private <T> T saveToRedis(String key, T t){
		//TODO 空账号不能记录到缓存中
		if(t == null){
			return null;
		}

		String value = (t == null ? "null" : JSONObject.toJSONString(t));
		BoundValueOperations<String, String> boundValueOperations = stringRedisTemplate.boundValueOps(key);
		boundValueOperations.set(value);
		boundValueOperations.expireAt(new DateTime().plusMinutes(5).toDate());
		return t;
	}

	private <T> T readFromRedis(String key, Class<T> clazz){
		String value = stringRedisTemplate.boundValueOps(key).get();
		if("null".equals(value)){
			return null;
		} else {
			return JSONObject.parseObject(value, clazz);
		}
	}
}
