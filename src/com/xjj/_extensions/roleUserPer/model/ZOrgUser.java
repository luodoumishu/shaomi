package com.xjj._extensions.roleUserPer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.framework.audit.NoAuditLog;
import com.xjj.framework.core.model.BaseModel;
import com.xjj.framework.core.model.annotation.Label;

@Entity
@Table(name = "z_org_user_t")
@Label( "单位用户信息")
@NoAuditLog
public class ZOrgUser extends BaseModel{
	
	private String orgId;//单位ID
	private String orgName;//单位名称
	private String userId;//用户ID
	private String userName;//用户名称
	
	@Column(name = "orgId", length = 50)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name = "orgName", length = 250)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
