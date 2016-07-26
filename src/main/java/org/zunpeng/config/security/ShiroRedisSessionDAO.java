//package org.zunpeng.config.security;
//
//import com.google.common.collect.Lists;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.session.UnknownSessionException;
//import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
//import org.joda.time.DateTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.core.BoundValueOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.Serializable;
//import java.util.Collection;
//import java.util.Set;
//
//@Transactional
//public class ShiroRedisSessionDAO extends AbstractSessionDAO {
//
//	private static Logger logger = LoggerFactory.getLogger(ShiroRedisSessionDAO.class);
//
//	private RedisTemplate redisTemplate;
//
//	private String prefix = "shiro_redis_session";
//
//	private int expireSeconds = 60 * 60 * 24 * 7;
//
//	@Override
//	protected Serializable doCreate(Session session) {
//		Serializable sessionId = this.generateSessionId(session);
//		this.assignSessionId(session, sessionId);
//		BoundValueOperations<String, Session> boundValueOperations = redisTemplate.boundValueOps(buildKey(sessionId));
//		boundValueOperations.set(session);
//		boundValueOperations.expireAt(new DateTime().plusDays(7).toDate());
//		return sessionId;
//	}
//
//	@Override
//	protected Session doReadSession(Serializable sessionId) {
//		if(sessionId == null || !redisTemplate.hasKey(buildKey(sessionId))){
//			return null;
//		}
//		BoundValueOperations<String, Session> boundValueOperations = redisTemplate.boundValueOps(buildKey(sessionId));
//		return boundValueOperations.get();
//	}
//
//	@Override
//	public void update(Session session) throws UnknownSessionException {
//		if(session == null || session.getId() == null){
//			return;
//		}
//
//		if(!redisTemplate.hasKey(buildKey(session.getId()))){
//			EnhanceSecurityUtils.getSubject().logout();
//			return;
//		}
//
//		BoundValueOperations<String, Session> boundValueOperations = redisTemplate.boundValueOps(buildKey(session.getId()));
//		boundValueOperations.set(session);
//	}
//
//	@Override
//	public void delete(Session session) {
//		if(session == null || session.getId() == null || !redisTemplate.hasKey(buildKey(session.getId()))){
//			return;
//		}
//
//		redisTemplate.delete(buildKey(session.getId()));
//	}
//
//	@Override
//	public Collection<Session> getActiveSessions() {
//		Collection<Session> sessionList = Lists.newArrayList();
//
//		Set keySet = redisTemplate.keys(prefix + ":*");
//		for(Object key : keySet){
//			BoundValueOperations<String, Session> boundValueOperations = redisTemplate.boundValueOps(key);
//			if(boundValueOperations == null){
//				continue;
//			}
//			Session session = boundValueOperations.get();
//			if(session == null){
//				continue;
//			}
//			sessionList.add(session);
//		}
//
//		return sessionList;
//	}
//
//	public void setRedisTemplate(RedisTemplate redisTemplate) {
//		this.redisTemplate = redisTemplate;
//	}
//
//	public void setPrefix(String prefix){
//		this.prefix = prefix.trim();
//	}
//
//	public void setExpireSeconds(int expireSeconds) {
//		this.expireSeconds = expireSeconds;
//	}
//
//	private String buildKey(Serializable sessionId){
//		String key = prefix + ":" + sessionId;
//		return key;
//	}
//}
