package org.zunpeng.core.shiro.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.mindrot.jbcrypt.BCrypt;

public class CustomSaltCredentialsMatcher implements CredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		Object tokenCredentials = getCredentials(token);
		Object accountCredentials = getCredentials(info);
		return equals(tokenCredentials, accountCredentials);
	}

	protected String getCredentials(AuthenticationToken token) {
		if(token instanceof UsernamePasswordToken){
			UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
			return new String(usernamePasswordToken.getPassword());
		}
		return null;
	}

	protected Object getCredentials(AuthenticationInfo info) {
		return info.getCredentials();
	}

	protected boolean equals(Object tokenCredentials, Object accountCredentials) {
		if(tokenCredentials != null && accountCredentials != null){
			return BCrypt.checkpw((String) tokenCredentials, (String) accountCredentials);
		}
		return false;
	}
}
