package com.xjj._extensions.roleUserPer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.framework.audit.NoAuditLog;
import com.xjj.framework.core.model.BaseModel;
import com.xjj.framework.core.model.annotation.Label;

@Entity
@Table(name = "z_role_user_t")
@Label( "角色用户信息")
@NoAuditLog
public class ZRoleUser extends BaseModel{
	
	private String roleId;//角色ID
	private String roleName;//角色名称
	private String userId;//用户ID
	private String userName;//用户名称
	
	@Column(name = "roleId", length = 50)
	public String getRoleId() {
		return roleId;
	}
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@Column(name = "roleName", length = 250)
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Column(name = "userId", length = 50)
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "userName", length = 250)
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
