package org.zunpeng.core.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by dapeng on 2017/3/17.
 */
public class RestSessionManager extends DefaultWebSessionManager {

	public static Logger logger = LoggerFactory.getLogger(RestSessionManager.class);

	private String sessionIdKey = "token";

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		Serializable id = httpServletRequest.getHeader(sessionIdKey);
		if(id == null) {

			Object idObject = request.getAttribute(sessionIdKey);

			if(id != null){
				id = (Serializable) idObject;
			}
		}
		return id;
	}

	@Override
	protected void onStart(Session session, SessionContext context) {
		super.onStart(session, context);

		if (!WebUtils.isHttp(context)) {
			logger.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response " +
					"pair. No session ID cookie will be set.");
			return;
		}

		HttpServletRequest request = WebUtils.getHttpRequest(context);

		Serializable sessionId = session.getId();
		request.setAttribute(sessionIdKey, sessionId);
	}

	public void setSessionIdKey(String sessionIdKey) {
		this.sessionIdKey = sessionIdKey;
	}
}
