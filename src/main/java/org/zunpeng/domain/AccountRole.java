package org.zunpeng.domain;

import java.util.Date;

/**
 * Created by dapeng on 16/8/8.
 */
public class AccountRole extends AbstractEntity {

	private static final long serialVersionUID = 3139162006745495246L;

	private Long accountId;

	private Long roleId;

	private boolean disabled = false;

	private Date lastModifyTime;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
}
