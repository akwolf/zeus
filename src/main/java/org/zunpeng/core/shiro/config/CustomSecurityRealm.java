package org.zunpeng.core.shiro.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zunpeng.domain.AccountInfo;
import org.zunpeng.mapper.AccountInfoMapper;

@Component
public class CustomSecurityRealm extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(CustomSecurityRealm.class);

	@Autowired
	private AccountInfoMapper accountInfoMapper;

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		logger.info("authorization: 授权回调函数 " + principals.getRealmNames());

		EnhanceUser enhanceUser = (EnhanceUser) principals.fromRealm(getName()).iterator().next();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		AccountInfo accountInfo = accountInfoMapper.getById(enhanceUser.getAccountId());
		if(accountInfo == null){
			return null;
		}

		return simpleAuthorizationInfo;
	}

	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//		logger.info("authentication: 认证回调函数");

		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

		AccountInfo accountInfo = accountInfoMapper.getByUsername(usernamePasswordToken.getUsername());
		if(accountInfo == null){
			throw new AuthenticationException("Invalid username/password combination!");
		}
		accountInfo.setBrief("hello world");
		accountInfoMapper.update(accountInfo);

		return new SimpleAuthenticationInfo(new EnhanceUser(accountInfo.getId(), accountInfo.getSlug()), accountInfo.getPassword(), getName());
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		credentialsMatcher = new CustomSaltCredentialsMatcher();
		super.setCredentialsMatcher(credentialsMatcher);
	}

	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		return principals.getPrimaryPrincipal().toString();
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
}
