package com.xjj.cms.menu.model;

public class CmsOrgUser {
	/**
	 * 部门id
	 */
	private String orgId;
	/**
	 * 部门名称
	 */
	private String orgName;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名称
	 */
	private String userName;
	
	public CmsOrgUser() {
	}

	public CmsOrgUser(String orgId, String orgName, String userId,
			String userName) {
		this.orgId = orgId;
		this.orgName = orgName;
		this.userId = userId;
		this.userName = userName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
