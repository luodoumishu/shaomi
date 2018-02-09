package com.xjj.cms.menu.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.framework.core.model.BaseModel;


/**
 * 分级授权（栏目跟用户关系表 ）
 * <p>
 * @author yeyunfeng 2015年5月15日 下午5:02:07 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年5月15日
 * @modify by reason:{方法名}:{原因}
 */
@Entity
@Table(name = "CMS_MENU_ORG")
public class CmsMenuOrg extends BaseModel implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	/**
	 * 栏目id
	 */
	private CmsMenu menu;
	/**
	 * 部门ID
	 */
	private Zorganize org;

	// Constructors

	/** default constructor */
	public CmsMenuOrg() {
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MENU_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public CmsMenu getMenu() {
		return menu;
	}
	
	public void setMenu(CmsMenu menu) {
		this.menu = menu;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORG_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public Zorganize getOrg() {
		return org;
	}
	
	public void setOrg(Zorganize org) {
		this.org = org;
	}
}