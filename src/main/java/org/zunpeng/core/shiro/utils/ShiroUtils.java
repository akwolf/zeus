package org.zunpeng.core.shiro.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.util.SavedRequest;
import org.zunpeng.core.shiro.config.EnhanceSecurityUtils;

/**
 * Created by dapeng on 2016/11/18.
 */
public class ShiroUtils {

	private static String getRequestURI(SavedRequest shiroSavedRequest) {
		SavedRequest savedRequest = shiroSavedRequest;
		String requestURI = savedRequest.getRequestURI();
		String queryString = savedRequest.getQueryString();

		if(StringUtils.isBlank(requestURI)){
			return "/";
		}

		if(StringUtils.isBlank(queryString)){
			return requestURI;
		}

		return requestURI + "?" + queryString;
	}

	public static String getRequestURI(){
		Object shiroSavedRequest = EnhanceSecurityUtils.getSubject().getSession().getAttribute("shiroSavedRequest");

		if(shiroSavedRequest == null){
			return "/";
		}

		return getRequestURI((SavedRequest)shiroSavedRequest);
	}

}
