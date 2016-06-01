package org.zunpeng.config;


import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Map;

/**
 * Created by dapeng on 16/1/17.
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Value("${spring.profiles.active}")
	private String profile;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
	}

	/**
	 * DruidWebStatFilter过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean druidWebStatFilter(){
		WebStatFilter druidWebStatFilter = new WebStatFilter();
		druidWebStatFilter.setProfileEnable(true);
		druidWebStatFilter.setSessionStatEnable(true);

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(druidWebStatFilter);
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));

		Map<String, String> druidWebStatFilterInitParameters = Maps.newHashMap();
		druidWebStatFilterInitParameters.put("exclusions", "*.less,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid,/druid/*,/druid/**");
		druidWebStatFilterInitParameters.put("profileEnable", "true");
//		druidWebStatFilterInitParameters.put("principalCookieName", "da_uid");
//		druidWebStatFilterInitParameters.put("principalSessionName", "da_ss_ud");
		druidWebStatFilterInitParameters.put("principalSessionName", "sqp_re_aa");
		druidWebStatFilterInitParameters.put("sessionStatEnable", "true");
		druidWebStatFilterInitParameters.put("sessionStatMaxCount", "1000");
		registration.setInitParameters(druidWebStatFilterInitParameters);
		registration.addUrlPatterns("/", "/*", "/**");
		return registration;
	}

	/**
	 * Druid监控Servlet
	 * @return
	 */
	@Bean
	public ServletRegistrationBean druidStatViewServlet(){
		StatViewServlet druidStatViewServlet = new StatViewServlet();
		ServletRegistrationBean registration = new ServletRegistrationBean();
		registration.setServlet(druidStatViewServlet);

		Map<String, String> druidStatViewServletInitParameters = Maps.newHashMap();
		druidStatViewServletInitParameters.put("loginUsername", "duobeiyunweike");
		druidStatViewServletInitParameters.put("loginPassword", "weike2016");
		registration.setInitParameters(druidStatViewServletInitParameters);
		registration.addUrlMappings("/druid", "/druid/*", "/druid/**");
		return registration;
	}

}
