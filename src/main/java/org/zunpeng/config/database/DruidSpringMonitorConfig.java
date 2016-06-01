package org.zunpeng.config.database;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by dapeng on 4/22/16.
 */
@Configuration
public class DruidSpringMonitorConfig {

	@Bean(name = "druid-stat-interceptor")
	public DruidStatInterceptor druidStatInterceptor(){
		return new DruidStatInterceptor();
	}

	@Bean(name = "druid-stat-pointcut")
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public JdkRegexpMethodPointcut jdkRegexpMethodPointcut(){
		JdkRegexpMethodPointcut jdkRegexpMethodPointcut = new JdkRegexpMethodPointcut();

		jdkRegexpMethodPointcut.setPatterns("com.duobeiyun.repository.*", "com.duobeiyun.service.*");

		return jdkRegexpMethodPointcut;
	}


	//TODO spring aop config
	/**
	 <aop:config proxy-target-class="true">
	    <aop:advisor advice-ref="druid-stat-interceptor"
	        pointcut-ref="druid-stat-pointcut" />
	 </aop:config>
	 */
	@Bean
	public DefaultPointcutAdvisor defaultPointcutAdvisor(){
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
		defaultPointcutAdvisor.setPointcut(jdkRegexpMethodPointcut());
		defaultPointcutAdvisor.setAdvice(druidStatInterceptor());
		return defaultPointcutAdvisor;
	}
}
