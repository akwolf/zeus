package org.zunpeng.domain;

import java.util.Date;

/**
 * Created by dapeng on 16/8/8.
 */
public class AccountPermission extends AbstractEntity {

	private static final long serialVersionUID = -1884736040885432128L;

	private Long accountId;

	private Long permissionId;

	private boolean disabled = false;

	private Date lastModifyTime;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
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
