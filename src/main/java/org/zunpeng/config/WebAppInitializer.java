package org.zunpeng.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.zunpeng.config.security.SecurityConfig;

import javax.servlet.*;
import java.util.Map;

/**
 * Created by dapeng on 16/7/26.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{AppConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);

		//字符编码过滤器
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		servletContext.addFilter("characterEncodingFilter", characterEncodingFilter)
				.addMappingForUrlPatterns(null, false, "/*");

		//Http请求方式过滤器
		servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
				.addMappingForUrlPatterns(null, false, "/*");


		//DruidWebStatFilter过滤器
		WebStatFilter druidWebStatFilter = new WebStatFilter();
		druidWebStatFilter.setProfileEnable(true);
		druidWebStatFilter.setSessionStatEnable(true);
		FilterRegistration.Dynamic druidWebStatFilterDynamic = servletContext.addFilter("druidWebStatFilter", druidWebStatFilter);
		Map<String, String> druidWebStatFilterInitParameters = Maps.newHashMap();
		druidWebStatFilterInitParameters.put("exclusions", "*.less,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid,/druid/*,/druid/**");
		druidWebStatFilterInitParameters.put("profileEnable", "true");
		druidWebStatFilterInitParameters.put("principalCookieName", "zs_uid");
//		druidWebStatFilterInitParameters.put("principalSessionName", "da_ss_ud");
		druidWebStatFilterInitParameters.put("principalSessionName", "zs_re_aa");
		druidWebStatFilterInitParameters.put("sessionStatEnable", "true");
		druidWebStatFilterInitParameters.put("sessionStatMaxCount", "1000");
		druidWebStatFilterDynamic.setInitParameters(druidWebStatFilterInitParameters);
		druidWebStatFilterDynamic.addMappingForUrlPatterns(null, false, "/", "/*", "/**");

		//Druid监控Servlet
		StatViewServlet druidStatViewServlet = new StatViewServlet();
		ServletRegistration.Dynamic druidStatViewServletDynamic = servletContext.addServlet("druidStatViewServlet", druidStatViewServlet);
		Map<String, String> druidStatViewServletInitParameters = Maps.newHashMap();
		druidStatViewServletInitParameters.put("loginUsername", "dapengzeus");
		druidStatViewServletInitParameters.put("loginPassword", "zeus2016");
		druidStatViewServletDynamic.setInitParameters(druidStatViewServletInitParameters);
		druidStatViewServletDynamic.addMapping("/druid", "/druid/*", "/druid/**");
	}

	@Override
	protected Filter[] getServletFilters() {
		logger.info("配置Shiro权限控制");
		DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy("shiroFilter");
		delegatingFilterProxy.setTargetFilterLifecycle(true);
		return new Filter[]{delegatingFilterProxy};
	}
}
