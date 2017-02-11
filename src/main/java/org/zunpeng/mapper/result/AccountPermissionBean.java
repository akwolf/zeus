package org.zunpeng.mapper.result;

import java.util.Date;

/**
 * Created by dapeng on 2016/10/12.
 */
public class AccountPermissionBean {

	private Long accountPermissionId;

	private String roleId;

	private String role;

	private Long rolePermissionId;

	private Long permissionId;

	private String permission;

	private Date createTime;

	private boolean deleted;

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getAccountPermissionId() {
		return accountPermissionId;
	}

	public void setAccountPermissionId(Long accountPermissionId) {
		this.accountPermissionId = accountPermissionId;
	}

	public Long getRolePermissionId() {
		return rolePermissionId;
	}

	public void setRolePermissionId(Long rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
	}
}
