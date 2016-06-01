package org.zunpeng.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by dapeng on 4/21/16.
 */
@Configuration
@ConfigurationProperties(prefix = "druid.datasource")
public class DruidDatabaseConfig {

	private static Logger logger = LoggerFactory.getLogger(DruidDatabaseConfig.class);

	private String username;

	private String password;

	private String url;

	private String driverClassName;

	private int maxActive = 2000;

	private int initialSize = 1;

	private int maxWait = 60000;

	private int minIdle = 1;

	private long timeBetweenEvictionRunsMillis = 60000;

	private long minEvictableIdleTimeMillis = 300000;

	private String validationQuery = "SELECT 'x'";

	private boolean testWhileIdle = true;

	private boolean testOnBorrow = false;

	private boolean testOnReturn = false;

	private boolean poolPreparedStatements = true;

	private int maxPoolPreparedStatementPerConnectionSize = 20;

	private boolean removeAbandoned = true;

	private int removeAbandonedTimeout = 1800;

	private boolean logAbandoned = true;

	private String filters = "config,stat,log4j,wall";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	@Bean(initMethod = "init", destroyMethod = "close")
	public DataSource dataSource(){
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(url);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		druidDataSource.setDriverClassName(driverClassName);

		druidDataSource.setMaxActive(maxActive);
		druidDataSource.setInitialSize(initialSize);
		druidDataSource.setMaxWait(maxWait);
		druidDataSource.setMinIdle(minIdle);

		druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

		druidDataSource.setValidationQuery(validationQuery);

		druidDataSource.setTestOnBorrow(testOnBorrow);
		druidDataSource.setTestOnReturn(testOnReturn);
		druidDataSource.setTestWhileIdle(testWhileIdle);
		druidDataSource.setRemoveAbandoned(removeAbandoned);
		druidDataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);

		druidDataSource.setPoolPreparedStatements(poolPreparedStatements);

		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

		try {
			druidDataSource.setFilters(filters);
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
		}

		return druidDataSource;
	}
}
