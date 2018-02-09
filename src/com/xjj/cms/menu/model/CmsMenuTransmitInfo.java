package com.xjj.cms.menu.model;


/**
 * 栏目推送表(用于页面展示)
 * 
 * @author wuqilin
 * @date 2016年4月13日 下午4:05:34
 */
public class CmsMenuTransmitInfo {
	/**
	 * 源推送menu_id
	 */
	private String source_menu_id;
	/**
	 * 源推送menu_name
	 */
	private String source_menu_name;
	/**
	 * 源推送 menu_parent_name
	 */
	private String source_menu_parent_name;
	/**
	 * 目标menu_id
	 */
	private String target_menu_id;
	/**
	 * 目标menu_name
	 */
	private String target_menu_name;
	/**
	 * 目标单位ID
	 */
	private String target_org_id;
	/**
	 * 目标单位名称
	 */
	private String target_org_name;

	public CmsMenuTransmitInfo() {
	}

	public CmsMenuTransmitInfo(String source_menu_id, String source_menu_name,
			String source_menu_parent_name, String target_menu_id,
			String target_menu_name, String target_org_id,
			String target_org_name) {
		this.source_menu_id = source_menu_id;
		this.source_menu_name = source_menu_name;
		this.source_menu_parent_name = source_menu_parent_name;
		this.target_menu_id = target_menu_id;
		this.target_menu_name = target_menu_name;
		this.target_org_id = target_org_id;
		this.target_org_name = target_org_name;
	}

	public String getSource_menu_id() {
		return source_menu_id;
	}

	public void setSource_menu_id(String source_menu_id) {
		this.source_menu_id = source_menu_id;
	}

	public String getSource_menu_name() {
		return source_menu_name;
	}

	public void setSource_menu_name(String source_menu_name) {
		this.source_menu_name = source_menu_name;
	}

	public String getSource_menu_parent_name() {
		return source_menu_parent_name;
	}

	public void setSource_menu_parent_name(String source_menu_parent_name) {
		this.source_menu_parent_name = source_menu_parent_name;
	}

	public String getTarget_menu_id() {
		return target_menu_id;
	}

	public void setTarget_menu_id(String target_menu_id) {
		this.target_menu_id = target_menu_id;
	}

	public String getTarget_menu_name() {
		return target_menu_name;
	}

	public void setTarget_menu_name(String target_menu_name) {
		this.target_menu_name = target_menu_name;
	}

	public String getTarget_org_id() {
		return target_org_id;
	}

	public void setTarget_org_id(String target_org_id) {
		this.target_org_id = target_org_id;
	}

	public String getTarget_org_name() {
		return target_org_name;
	}

	public void setTarget_org_name(String target_org_name) {
		this.target_org_name = target_org_name;
	}

}
