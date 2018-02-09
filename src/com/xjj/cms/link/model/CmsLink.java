package com.xjj.cms.link.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.cms.base.model.CMSBaseModel;


/**
 * Link entity 超链接管理实体
 */
@Entity
@Table(name = "CMS_LINK")
public class CmsLink extends CMSBaseModel implements java.io.Serializable {
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 超链接名称
	 */
	private String linkName;
	/**
	 * 超链接地址
	 */
	private String linkAddr;
	/**
	 * 超链接类型
	 */
	private String linkType;
	/**
	 * 超链接图片
	 */
	private String linkImg;
	/**
	 * 打开方式
	 */
	private String openType;
	/**
	 * 备注
	 */
	private String remark;
	
	public CmsLink() {
	}

	
	public CmsLink(String linkName, String linkAddr, String linkType,
			String linkImg, String openType, String remark) {
		super();
		this.linkName = linkName;
		this.linkAddr = linkAddr;
		this.linkType = linkType;
		this.linkImg = linkImg;
		this.openType = openType;
		this.remark = remark;
	}





	@Column(name = "LINK_NAME", length = 516)
	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	@Column(name = "LINK_ADDR", length = 516)
	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}
	@Column(name = "LINK_TYPE", length = 32)
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	@Column(name = "LINK_IMG", length = 516)
	public String getLinkImg() {
		return linkImg;
	}

	public void setLinkImg(String linkImg) {
		this.linkImg = linkImg;
	}
	@Column(name = "REMARK", length = 1024)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "OPEN_TYPE", length = 32)
	public String getOpenType() {
		return openType;
	}


	public void setOpenType(String openType) {
		this.openType = openType;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((linkAddr == null) ? 0 : linkAddr.hashCode());
		result = prime * result + ((linkImg == null) ? 0 : linkImg.hashCode());
		result = prime * result
				+ ((linkName == null) ? 0 : linkName.hashCode());
		result = prime * result
				+ ((linkType == null) ? 0 : linkType.hashCode());
		result = prime * result
				+ ((openType == null) ? 0 : openType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CmsLink other = (CmsLink) obj;
		if (linkAddr == null) {
			if (other.linkAddr != null)
				return false;
		} else if (!linkAddr.equals(other.linkAddr))
			return false;
		if (linkImg == null) {
			if (other.linkImg != null)
				return false;
		} else if (!linkImg.equals(other.linkImg))
			return false;
		if (linkName == null) {
			if (other.linkName != null)
				return false;
		} else if (!linkName.equals(other.linkName))
			return false;
		if (linkType == null) {
			if (other.linkType != null)
				return false;
		} else if (!linkType.equals(other.linkType))
			return false;
		if (openType == null) {
			if (other.openType != null)
				return false;
		} else if (!openType.equals(other.openType))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "CmsLink [linkName=" + linkName + ", linkAddr=" + linkAddr
				+ ", linkType=" + linkType + ", linkImg=" + linkImg
				+ ", openType=" + openType + ", remark=" + remark + "]";
	}
	
	
}
