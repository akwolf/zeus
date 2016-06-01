package org.zunpeng.config;//package com.duobeiyun.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@EnableAspectJAutoProxy
//public class RedisConfig {
//
//	@Autowired
//	private JedisConnectionFactory jedisConnectionFactory;
//
//	@Bean
//	@Autowired
//	public RedisTemplate redisTemplate(JdkSerializationRedisSerializer jdkSerializationRedisSerializer,
//	                                   StringRedisSerializer stringRedisSerializer){
//		RedisTemplate redisTemplate = new RedisTemplate();
//		redisTemplate.setConnectionFactory(jedisConnectionFactory);
//		redisTemplate.setKeySerializer(stringRedisSerializer);
//		redisTemplate.setHashKeySerializer(stringRedisSerializer);
//		redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
//		redisTemplate.setHashValueSerializer(stringRedisSerializer);
//		redisTemplate.setEnableTransactionSupport(true);
//		return redisTemplate;
//	}
//
//	@Bean
//	@Autowired
//	public StringRedisTemplate stringRedisTemplate(JdkSerializationRedisSerializer jdkSerializationRedisSerializer,
//	                                               StringRedisSerializer stringRedisSerializer){
//		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//		stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
//		stringRedisTemplate.setKeySerializer(stringRedisSerializer);
//		stringRedisTemplate.setHashKeySerializer(stringRedisSerializer);
//		stringRedisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
//		stringRedisTemplate.setHashKeySerializer(stringRedisSerializer);
//		return stringRedisTemplate;
//	}
//
//	@Bean
//	public JdkSerializationRedisSerializer jdkSerializationRedisSerializer(){
//		return new JdkSerializationRedisSerializer();
//	}
//
//	@Bean
//	public StringRedisSerializer stringRedisSerializer(){
//		return new StringRedisSerializer();
//	}
//
//}
