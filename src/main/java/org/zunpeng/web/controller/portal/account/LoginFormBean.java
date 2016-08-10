package org.zunpeng.web.controller.portal.account;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by dapeng on 16/8/8.
 */
public class LoginFormBean {

	@NotBlank
	@Pattern(regexp = "\\d{11}")
	private String mobile;

	@NotBlank
	private String password;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
