package org.zunpeng.config.security;

import java.io.Serializable;

public class EnhanceUser implements Serializable {

	private static final long serialVersionUID = -7336389088639221141L;

	private Long accountId;

	private String openId;

	private String appid;

	private String slug;

	public EnhanceUser(Long accountId, String appid, String openId, String slug){
		this.accountId = accountId;
		this.openId = openId;
		this.appid = appid;
		this.slug = slug;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
}
