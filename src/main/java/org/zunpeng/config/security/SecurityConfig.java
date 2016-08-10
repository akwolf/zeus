package org.zunpeng.config.security;

import com.google.common.collect.Maps;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.mgt.SubjectDAO;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.zunpeng.core.shiro.ZeusShiroFilterFactoryBean;

import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class SecurityConfig {

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	@Bean
	public CustomSecurityRealm customSecurityRealm(){
		return new CustomSecurityRealm();
	}

	@Bean
	@Autowired
	public WebSecurityManager securityManager(CustomSecurityRealm customSecurityRealm,
											  SubjectDAO subjectDAO,
											  RememberMeManager rememberMeManager,
											  SessionManager sessionManager){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(customSecurityRealm);
		securityManager.setSubjectDAO(subjectDAO);
		securityManager.setRememberMeManager(rememberMeManager);
		securityManager.setSessionManager(sessionManager);
		return securityManager;
	}

	@Bean
	public DefaultSubjectDAO subjectDAO(SessionStorageEvaluator sessionStorageEvaluator){
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
		return subjectDAO;
	}

	@Bean
	public DefaultWebSessionStorageEvaluator sessionStorageEvaluator(){
		DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
		sessionStorageEvaluator.setSessionStorageEnabled(true);
		return sessionStorageEvaluator;
	}

	@Bean
	@Autowired
	public MethodInvokingFactoryBean methodInvokingFactoryBean(WebSecurityManager securityManager){
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		methodInvokingFactoryBean.setArguments(new Object[]{securityManager});
		return methodInvokingFactoryBean;
	}

	@Bean
	@Autowired
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(WebSecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	@Autowired
	public ShiroFilterFactoryBean shiroFilter(WebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilter = new ZeusShiroFilterFactoryBean();
		shiroFilter.setLoginUrl("/login");
		shiroFilter.setUnauthorizedUrl("/login");
		shiroFilter.setSuccessUrl("/admin");

		shiroFilter.setSecurityManager(securityManager);

		Map<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();

		//注销
		filterChainDefinitionMap.put("/logout", "logout");

		//访问管理页面
		filterChainDefinitionMap.put("/admin", "authc");
		filterChainDefinitionMap.put("/admin/*", "authc");
		filterChainDefinitionMap.put("/admin/**", "authc");

		//druid监控
		filterChainDefinitionMap.put("/druid", "anon");
		filterChainDefinitionMap.put("/druid/*", "anon");
		filterChainDefinitionMap.put("/druid/**", "anon");

		//禁止访问其他页面
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/*", "anon");
		filterChainDefinitionMap.put("/**", "anon");

		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilter;
	}

	@Bean
	@Autowired
	public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie){
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie);
//		cookieRememberMeManager.setCipherKey(Base64.decode(cipherKey));
		return cookieRememberMeManager;
	}

	@Bean
	public DefaultWebSessionManager sessionManager(SimpleCookie sessionIdCookie){
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionIdCookie(sessionIdCookie);
		sessionManager.setGlobalSessionTimeout(1000 * 60 * 60 * 24 * 7);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
//		sessionManager.setSessionDAO(redisSessionDAO);
		return sessionManager;
	}

	@Bean
	public SimpleCookie rememberMeCookie(){
		SimpleCookie simpleCookie = new SimpleCookie();
		simpleCookie.setName("sqp_re_mm");
		simpleCookie.setMaxAge(60 * 60 * 24 * 7);
		simpleCookie.setHttpOnly(true);
		simpleCookie.setPath("/");
		return simpleCookie;
	}

	@Bean
	public SimpleCookie sessionIdCookie(){
		SimpleCookie simpleCookie = new SimpleCookie();
		simpleCookie.setName("sqp_re_aa");
		simpleCookie.setMaxAge(-1);
		simpleCookie.setHttpOnly(true);
		simpleCookie.setPath("/");
		return simpleCookie;
	}

//	@Bean
//	@Autowired
//	public ShiroRedisSessionDAO redisSessionDAO(RedisTemplate redisTemplateBean){
//		ShiroRedisSessionDAO shiroRedisSessionDAO = new ShiroRedisSessionDAO();
//		shiroRedisSessionDAO.setRedisTemplate(redisTemplateBean);
//		return shiroRedisSessionDAO;
//	}

//	@Bean
//	@Autowired
//	public RedisTemplate redisTemplateBean(RedisTemplate redisTemplate){
//		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
//		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//		redisTemplate.setKeySerializer(stringRedisSerializer);
//		redisTemplate.setHashKeySerializer(stringRedisSerializer);
//		redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
//		redisTemplate.setHashValueSerializer(stringRedisSerializer);
//		redisTemplate.setEnableTransactionSupport(true);
//		return redisTemplate;
//	}
}
