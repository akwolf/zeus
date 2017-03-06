package org.zunpeng.web.controller.portal.account;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by dapeng on 2017/3/4.
 */
public class RegisterFormBean {

	@NotBlank
	@Pattern(regexp = "\\d{11}")
	private String mobile;

	@NotBlank
	private String password;

	@NotBlank
	private String rePassword;

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

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
}
