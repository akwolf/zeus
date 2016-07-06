package org.zunpeng.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by dapeng on 4/21/16.
 */
@Configuration
@EnableConfigurationProperties(DruidProperties.class)
public class DruidDatabaseConfig {

	private static Logger logger = LoggerFactory.getLogger(DruidDatabaseConfig.class);

	@Autowired
	private DruidProperties properties;

	@Bean(initMethod = "init", destroyMethod = "close")
	public DataSource dataSource(){
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(properties.getUrl());
		druidDataSource.setUsername(properties.getUsername());
		druidDataSource.setPassword(properties.getPassword());
		druidDataSource.setDriverClassName(properties.getDriverClassName());

		druidDataSource.setMaxActive(properties.getMaxActive());
		druidDataSource.setInitialSize(properties.getInitialSize());
		druidDataSource.setMaxWait(properties.getMaxWait());
		druidDataSource.setMinIdle(properties.getMinIdle());

		druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
		druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());

		druidDataSource.setValidationQuery(properties.getValidationQuery());

		druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
		druidDataSource.setTestOnReturn(properties.isTestOnReturn());
		druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
		druidDataSource.setRemoveAbandoned(properties.isRemoveAbandoned());
		druidDataSource.setRemoveAbandonedTimeout(properties.getRemoveAbandonedTimeout());

		druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());

		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());

		try {
			druidDataSource.setFilters(properties.getFilters());
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
		}

		return druidDataSource;
	}
}
