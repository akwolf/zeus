package org.zunpeng.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by dapeng on 16/8/14.
 */
@Configuration
public class RedisConfig {

	@Value("${redis.server.host}")
	private String redisHost;

	@Value("${redis.server.port}")
	private int redisPort;

	@Bean
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(10);
		jedisPoolConfig.setMaxWaitMillis(1000*60);
		jedisPoolConfig.setMaxTotal(2000);
		jedisPoolConfig.setTestOnBorrow(true);
		return jedisPoolConfig;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(){
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(redisHost);
		jedisConnectionFactory.setPort(redisPort);
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
		jedisConnectionFactory.afterPropertiesSet();
		return jedisConnectionFactory;
	}

	@Bean
	public RedisTemplate redisTemplate(){
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setHashKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(jdkSerializationRedisSerializer());
		redisTemplate.setHashValueSerializer(stringRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate(){
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
		stringRedisTemplate.setKeySerializer(stringRedisSerializer());
		stringRedisTemplate.setHashKeySerializer(stringRedisSerializer());
		stringRedisTemplate.setValueSerializer(jdkSerializationRedisSerializer());
		stringRedisTemplate.setHashKeySerializer(stringRedisSerializer());
		return stringRedisTemplate;
	}

	public JdkSerializationRedisSerializer jdkSerializationRedisSerializer(){
		return new JdkSerializationRedisSerializer();
	}

	public StringRedisSerializer stringRedisSerializer(){
		return new StringRedisSerializer();
	}

}
