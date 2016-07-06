package org.zunpeng.config.mybatis;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableTransactionManagement
@MapperScan("org.zunpeng.mapper")
public class MybatisConfig {

	private static Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

	@Autowired
	private DataSource dataSource;

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setVfs(SpringBootVFS.class);

//		sessionFactoryBean.setConfigLocation(resolveConfigLocation("classpath:mybatis-config.xml"));

		sessionFactoryBean.setTypeAliasesPackage("org.zunpeng.domain");
		sessionFactoryBean.setMapperLocations(resolveMapperLocations("classpath:org/zunpeng/persistent/**/*.xml"));
//		sessionFactoryBean.setTypeHandlersPackage("");

		return sessionFactoryBean;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.SIMPLE);
	}

	public Resource[] resolveMapperLocations(String... mapperLocations) {
		List<Resource> resources = new ArrayList<Resource>();
		if (mapperLocations != null) {
			for (String mapperLocation : mapperLocations) {
				Resource[] mappers;
				try {
					mappers = new PathMatchingResourcePatternResolver().getResources(mapperLocation);
					resources.addAll(Arrays.asList(mappers));
				} catch (Throwable e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		Resource[] mapperLocationResources = new Resource[resources.size()];
		mapperLocationResources = resources.toArray(mapperLocationResources);
		return mapperLocationResources;
	}

	public Resource resolveConfigLocation(String configLocation){
		try {
			return new PathMatchingResourcePatternResolver().getResource(configLocation);
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			return null;
		}
	}

}
