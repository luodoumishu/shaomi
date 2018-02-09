package com.xjj.cms.menu.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.framework.core.model.BaseModel;

/**
 * 栏目——用户权限表
 * <p>
 * @author yeyunfeng 2014-9-2 下午2:39:14 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
@Entity
@Table(name = "CMS_MENU_USER")
public class CmsMenuUser extends BaseModel implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	/**
	 * 栏目id
	 */
	private String menuId;
	/**
	 * 栏目名称
	 */
	private String menuName;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名称
	 */
	private String userName;

	// Constructors

	/** default constructor */
	public CmsMenuUser() {
	}

	/** minimal constructor */

	/** full constructor */
	public CmsMenuUser(String menuId, String menuName,
			String userId, String userName) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.userId = userId;
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "CmsMenuUser [menuId=" + menuId + ", menuName=" + menuName
				+ ", userId=" + userId + ", userName=" + userName + "]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		result = prime * result
				+ ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CmsMenuUser other = (CmsMenuUser) obj;
		if (menuId == null) {
			if (other.menuId != null) {
				return false;
			}
		} else if (!menuId.equals(other.menuId)) {
			return false;
		}
		if (menuName == null) {
			if (other.menuName != null) {
				return false;
			}
		} else if (!menuName.equals(other.menuName)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
	}

	// Property accessors
	@Column(name = "MENU_ID", length = 128)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "MENU_NAME", length = 128)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "USER_ID", length = 128)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 128)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}