package org.zunpeng.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zunpeng.config.database.DruidDatabaseConfig;
import org.zunpeng.config.mybatis.MybatisConfig;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = "org.zunpeng", excludeFilters = {@ComponentScan.Filter(Configuration.class),
		@ComponentScan.Filter(Controller.class),
		@ComponentScan.Filter(ControllerAdvice.class)})
@Import({DruidDatabaseConfig.class, MybatisConfig.class})
@PropertySources({@PropertySource("classpath:application-${zeus.env}.properties")})
public class AppConfig {

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}

}
