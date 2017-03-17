package org.zunpeng.core.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.AbstractRememberMeManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by dapeng on 2017/3/17.
 */
public class RestRememberMeManager extends AbstractRememberMeManager {

	private static Logger logger = LoggerFactory.getLogger(RestRememberMeManager.class);

	@Override
	protected void forgetIdentity(Subject subject) {

	}

	public void forgetIdentity(SubjectContext subjectContext) {

	}

	@Override
	protected void rememberSerializedIdentity(Subject subject, byte[] serialized) {
		if (!WebUtils.isHttp(subject)) {
			if (logger.isDebugEnabled()) {
				String msg = "Subject argument is not an HTTP-aware instance.  This is required to obtain a servlet " +
						"request and response in order to set the rememberMe cookie. Returning immediately and " +
						"ignoring rememberMe operation.";
				logger.debug(msg);
			}
			return;
		}


		HttpServletRequest request = WebUtils.getHttpRequest(subject);

		//base 64 encode it and store as a cookie:
		String base64 = Base64.encodeToString(serialized);

		request.setAttribute("token", base64);
	}

	@Override
	protected byte[] getRememberedSerializedIdentity(SubjectContext subjectContext) {
		if (!WebUtils.isHttp(subjectContext)) {
			if (logger.isDebugEnabled()) {
				String msg = "SubjectContext argument is not an HTTP-aware instance.  This is required to obtain a " +
						"servlet request and response in order to retrieve the rememberMe cookie. Returning " +
						"immediately and ignoring rememberMe operation.";
				logger.debug(msg);
			}
			return null;
		}

		WebSubjectContext wsc = (WebSubjectContext) subjectContext;
		if (isIdentityRemoved(wsc)) {
			return null;
		}

		HttpServletRequest request = WebUtils.getHttpRequest(wsc);

		String base64 = request.getHeader("token");

		// Browsers do not always remove cookies immediately (SHIRO-183)
		// ignore cookies that are scheduled for removal
		if (Cookie.DELETED_COOKIE_VALUE.equals(base64)) return null;

		if (base64 != null) {
			base64 = ensurePadding(base64);
			if (logger.isTraceEnabled()) {
				logger.trace("Acquired Base64 encoded identity [" + base64 + "]");
			}
			byte[] decoded = Base64.decode(base64);
			if (logger.isTraceEnabled()) {
				logger.trace("Base64 decoded byte array length: " + (decoded != null ? decoded.length : 0) + " bytes.");
			}
			return decoded;
		} else {
			//no cookie set - new site visitor?
			return null;
		}
	}

	private String ensurePadding(String base64) {
		int length = base64.length();
		if (length % 4 != 0) {
			StringBuilder sb = new StringBuilder(base64);
			for (int i = 0; i < length % 4; ++i) {
				sb.append('=');
			}
			base64 = sb.toString();
		}
		return base64;
	}

	private boolean isIdentityRemoved(WebSubjectContext subjectContext) {
		ServletRequest request = subjectContext.resolveServletRequest();
		if (request != null) {
			Boolean removed = (Boolean) request.getAttribute(ShiroHttpServletRequest.IDENTITY_REMOVED_KEY);
			return removed != null && removed;
		}
		return false;
	}
}
