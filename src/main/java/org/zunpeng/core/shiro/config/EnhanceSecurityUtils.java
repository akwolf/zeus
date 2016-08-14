package org.zunpeng.core.shiro.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class EnhanceSecurityUtils extends SecurityUtils {

	public static EnhanceUser retrieveEnhanceUser() {
		Subject subject = getSubject();
		if (subject == null) {
			return new NullEnhanceUser();
		}
		Object principal = subject.getPrincipal();
		if (principal != null && principal instanceof EnhanceUser) {
			EnhanceUser enhanceUser = (EnhanceUser) principal;
			return enhanceUser;
		}
		return new NullEnhanceUser();
	}

	public static boolean judgeLogin(){
		return retrieveEnhanceUser().getAccountId() > 0;
	}

}
