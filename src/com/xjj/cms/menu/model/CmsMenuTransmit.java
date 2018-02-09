package com.xjj.cms.menu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.framework.core.model.BaseModel;

/**
 * 栏目推送表
 * 
 * @author wuqilin
 * @date 2016年4月13日 下午4:05:34
 */
@Entity
@Table(name = "CMS_MENU_TRANSMIT")
public class CmsMenuTransmit extends BaseModel implements java.io.Serializable {
	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 原menuId
	 */
	private String source_menuId;
	/**
	 * 推送orgId
	 */
	private String target_orgId;
	/**
	 * 推送menuId
	 */
	private String target_menuId;

	public CmsMenuTransmit() {
	}

	public CmsMenuTransmit(String source_menuId, String target_orgId, String target_menuId) {
		this.source_menuId = source_menuId;
		this.target_orgId = target_orgId;
		this.target_menuId = target_menuId;
	}

	@Column(name = "SOURCE_MENUID")
	public String getSource_menuId() {
		return source_menuId;
	}

	public void setSource_menuId(String source_menuId) {
		this.source_menuId = source_menuId;
	}

	@Column(name = "TARGET_ORGID")
	public String getTarget_orgId() {
		return target_orgId;
	}

	public void setTarget_orgId(String target_orgId) {
		this.target_orgId = target_orgId;
	}

	@Column(name = "TARGET_MENUID")
	public String getTarget_menuId() {
		return target_menuId;
	}

	public void setTarget_menuId(String target_menuId) {
		this.target_menuId = target_menuId;
	}

}
