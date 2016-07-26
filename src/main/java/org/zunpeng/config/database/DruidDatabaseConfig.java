package org.zunpeng.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by dapeng on 4/21/16.
 */
@Configuration
public class DruidDatabaseConfig {

	private static Logger logger = LoggerFactory.getLogger(DruidDatabaseConfig.class);

	@Value("${druid.datasource.url}")
	private String jdbcUrl;

	@Value("${druid.datasource.username}")
	private String username;

	@Value("${druid.datasource.password}")
	private String password;

	@Value("${druid.datasource.driver_class_name}")
	private String driverClassName;

	@Value("${druid.datasource.public_key}")
	private String publicKey;

	@Bean(initMethod = "init", destroyMethod = "close")
	public DataSource dataSource(){
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(jdbcUrl);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		druidDataSource.setDriverClassName(driverClassName);

		druidDataSource.setConnectionProperties("config.decrypt=true;config.decrypt.key=" + publicKey);
		druidDataSource.setMaxActive(2000);
		druidDataSource.setInitialSize(1);
		druidDataSource.setMaxWait(60000);
		druidDataSource.setMinIdle(1);

		druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
		druidDataSource.setMinEvictableIdleTimeMillis(300000);

		druidDataSource.setValidationQuery("SELECT 'x'");

		druidDataSource.setTestOnBorrow(false);
		druidDataSource.setTestOnReturn(false);
		druidDataSource.setTestWhileIdle(true);
		druidDataSource.setRemoveAbandoned(true);
		druidDataSource.setRemoveAbandonedTimeout(1800);

		druidDataSource.setPoolPreparedStatements(true);

		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

		try {
			druidDataSource.setFilters("config,stat,log4j,wall");
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
		}

		return druidDataSource;
	}
}
