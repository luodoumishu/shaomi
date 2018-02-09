package com.xjj._extensions.adrsbook.web.controller;

import java.util.HashMap;
import java.util.Map;

public class AddressContactVo {

	private String id;
	private String text;
	/**
	 * ORG :机构
	 * ORG_USER ：机构下用户
	 * ASSIGN_ORG ：指定机构
	 * GROUP_TYPE ：组类型
	 * GROUP：组
	 * GROUP_USER：组下用户
	 */
	private String type;
	private Long userCount;
	private boolean hasChildren;

	/**
	 * item 是否可选
	 */
	private boolean enableChoose=true;
	
	/**
	 *  "highLightStr", 被高亮字符串
	    "oaduty", 职位
		"userName", 用户名称
		"userAccount", 用户账户
		"orgName",机构名称
		"orgId", 机构ID
	 */
	Map<String, String> userInfo = new HashMap<String, String>();


	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChild) {
		this.hasChildren = hasChild;
	}
	public Long getUserCount() {
		return userCount;
	}
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}
	public Map<String, String> getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Map<String, String> userInfo) {
		this.userInfo = userInfo;
	}

	public boolean getEnableChoose() {
		return enableChoose;
	}

	public void setEnableChoose(boolean enableChoose) {
		this.enableChoose = enableChoose;
	}
}
