package com.xjj.cms.channel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.cms.base.model.CMSBaseModel;

/**
 * 
 * <p>
 * CmsChannelItem entity 频道项实体
 * 
 * @author yeyunfeng 2014-8-21 下午3:33:45
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-8-21
 * @modify by reason:{方法名}:{原因}
 */
@Entity
@Table(name = "CMS_CHANNEL_ITEM")
public class CmsChannelItem extends CMSBaseModel implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 频道项名称
	 */
	private String channeItemName;
	/**
	 * 栏目名称
	 */
	private String menuName;
	/**
	 * 栏目ID
	 */
	private String menuId;
	/**
	 * 频道ID
	 */
	private String channelId;
	/**
	 * 频道名称
	 */
	private String channeName;
	/**
	 * 排序号
	 */
	private Integer sortno;

	// Constructors

	/** default constructor */
	public CmsChannelItem() {
	}

	public CmsChannelItem(String channeItemName, String menuName,
			String menuId, String channelId, String channeName, Integer sortno) {
		super();
		this.channeItemName = channeItemName;
		this.menuName = menuName;
		this.menuId = menuId;
		this.channelId = channelId;
		this.channeName = channeName;
		this.sortno = sortno;
	}

	// Property accessors

	@Column(name = "CHANNE_ITEM_NAME", length = 128)
	public String getChanneItemName() {
		return this.channeItemName;
	}

	public void setChanneItemName(String channeItemName) {
		this.channeItemName = channeItemName;
	}

	@Column(name = "MENU_NAME", length = 128)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "MENU_ID", length = 128)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "CHANNEL_ID", length = 128)
	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Column(name = "CHANNE_NAME", length = 128)
	public String getChanneName() {
		return this.channeName;
	}

	public void setChanneName(String channeName) {
		this.channeName = channeName;
	}

	@Column(name = "SORTNO")
	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	@Override
	public String toString() {
		return "CmsChannelItem [channeItemName=" + channeItemName
				+ ", menuName=" + menuName + ", menuId=" + menuId
				+ ", channelId=" + channelId + ", channeName=" + channeName
				+ ", sortno=" + sortno + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((channeItemName == null) ? 0 : channeItemName.hashCode());
		result = prime * result
				+ ((channeName == null) ? 0 : channeName.hashCode());
		result = prime * result
				+ ((channelId == null) ? 0 : channelId.hashCode());
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		result = prime * result
				+ ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((sortno == null) ? 0 : sortno.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CmsChannelItem other = (CmsChannelItem) obj;
		if (channeItemName == null) {
			if (other.channeItemName != null) {
				return false;
			}
		} else if (!channeItemName.equals(other.channeItemName)) {
			return false;
		}
		if (channeName == null) {
			if (other.channeName != null) {
				return false;
			}
		} else if (!channeName.equals(other.channeName)) {
			return false;
		}
		if (channelId == null) {
			if (other.channelId != null) {
				return false;
			}
		} else if (!channelId.equals(other.channelId)) {
			return false;
		}
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
		if (sortno == null) {
			if (other.sortno != null) {
				return false;
			}
		} else if (!sortno.equals(other.sortno)) {
			return false;
		}
		return true;
	}

}