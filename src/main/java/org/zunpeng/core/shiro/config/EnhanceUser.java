package org.zunpeng.core.shiro.config;

import java.io.Serializable;

public class EnhanceUser implements Serializable {

	private static final long serialVersionUID = -7336389088639221141L;

	private Long accountId;

	private String slug;

	public EnhanceUser(Long accountId, String slug){
		this.accountId = accountId;
		this.slug = slug;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
}
