package org.zunpeng.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dapeng on 16/1/17.
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@EnableAsync
@ComponentScan(basePackages = "org.zunpeng", useDefaultFilters = false,
		includeFilters = {@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class)})
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	@Value("${spring.profiles.active}")
	private String profile;

	private ApplicationContext applicationContext;

	public WebConfig() {
		super();
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//增加拦截器

		super.addInterceptors(registry);
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * 配置静态资源的访问路径
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/image/**").addResourceLocations("/static/image/").setCachePeriod(0);
		registry.addResourceHandler("/style/**").addResourceLocations("/static/style/").setCachePeriod(0);
		registry.addResourceHandler("/javascript/**").addResourceLocations("/static/javascript/").setCachePeriod(0);
		registry.addResourceHandler("/flash/**").addResourceLocations("/static/flash/").setCachePeriod(0);
		super.addResourceHandlers(registry);
	}

	/**
	 * 请求参数处理
	 * @param argumentResolvers
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(pageableHandlerMethodArgumentResolver());
		argumentResolvers.add(sortHandlerMethodArgumentResolver());
		super.addArgumentResolvers(argumentResolvers);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		Map<String, MediaType> mediaTypeMap = Maps.newHashMap();
		mediaTypeMap.put("atom", MediaType.APPLICATION_ATOM_XML);
		mediaTypeMap.put("html", MediaType.TEXT_HTML);
		mediaTypeMap.put("json", MediaType.APPLICATION_JSON);

		configurer.favorParameter(false).favorPathExtension(false)
				.ignoreAcceptHeader(false).useJaf(false)
				.defaultContentType(MediaType.APPLICATION_JSON_UTF8).mediaTypes(mediaTypeMap);
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver(){
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(this.applicationContext);
		templateResolver.setSuffix(".html");
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCacheable(true);
		templateResolver.setOrder(1);
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine(){
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);

//		templateEngine.addDialect("shiro", new ShiroDialect());
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver(){
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setOrder(1);
//		thymeleafViewResolver.setViewNames(new String[]{"*.html,*.xhtml"});
		thymeleafViewResolver.setCache(true);
		thymeleafViewResolver.setCharacterEncoding("UTF-8");

		Map<String, String> staticVariables = Maps.newHashMap();
		staticVariables.put("footer", "DAPENG Co.,Ltd");
		thymeleafViewResolver.setStaticVariables(staticVariables);

		return thymeleafViewResolver;
	}

	@Bean
	public ContentNegotiatingViewResolver contentNegotiatingViewResolver(){
		ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();

		List<ViewResolver> viewResolverList = Lists.newArrayList();
		viewResolverList.add(thymeleafViewResolver());

		List<View> defaultViews = Lists.newArrayList();
		defaultViews.add(new MappingJackson2JsonView());

		contentNegotiatingViewResolver.setOrder(1);
		contentNegotiatingViewResolver.setViewResolvers(viewResolverList);
		contentNegotiatingViewResolver.setDefaultViews(defaultViews);

		return contentNegotiatingViewResolver;
	}

	@Bean
	public ResourceBundleMessageSource messageSource(){
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setBasename("international/messages");
		messageSource.setCacheSeconds(10);
		return messageSource;
	}

	@Bean
	public FormattingConversionServiceFactoryBean conversionService(){
		FormattingConversionServiceFactoryBean conversionService = new FormattingConversionServiceFactoryBean();

		Set converters = Sets.newHashSet();
		conversionService.setConverters(converters);

		Set formatters = Sets.newHashSet();
		conversionService.setFormatters(formatters);

		return conversionService;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		commonsMultipartResolver.setMaxUploadSize(50 * 1024 * 1024);
		return commonsMultipartResolver;
	}

	/**
	 * 分页相关的参数处理
	 * @return
	 */
	@Bean
	public SortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver(){
		return new SortHandlerMethodArgumentResolver();
	}

	@Bean
	public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver(){
		return new PageableHandlerMethodArgumentResolver();
	}

}
