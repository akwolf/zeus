package org.zunpeng.domain;

import java.util.Date;

/**
 * Created by dapeng on 2016/10/16.
 */
public class RolePermission extends AbstractEntity {

	private static final long serialVersionUID = -3528077195527252713L;

	private Long roleId;

	private Long permissionId;

	private boolean disabled = false;

	private Date lastModifyTime;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
