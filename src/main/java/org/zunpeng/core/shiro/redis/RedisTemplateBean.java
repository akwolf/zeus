package org.zunpeng.core.shiro.redis;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * Created by dapeng on 16/8/14.
 */
public class RedisTemplateBean {

	@Resource
	private RedisTemplate redisTemplate;

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}
}
