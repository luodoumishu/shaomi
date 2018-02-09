package com.xjj.cms.file.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xjj.cms.base.model.CMSBaseModel;
import com.xjj.cms.base.model.CmsAffix;

/**
 * File entity 文件管理实体
 */
@Entity
@Table(name = "CMS_FILE")
public class CmsFile extends CMSBaseModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文件标题
	 */
	private String fileTitle;
	/**
	 * 所属栏目ID
	 */
	private String menuId;
	/**
	 * 所属栏目名称
	 */
	private String menuName;
	/**
	 * 备注
	 */
	private String remark;
	
	private List<CmsAffix> cmsAffixs = new ArrayList<CmsAffix>();
	
	public CmsFile() {
	
	}


	public CmsFile(String fileTitle, String menuId,
			String menuName, String remark) {
		super();
		this.fileTitle = fileTitle;
		this.menuId = menuId;
		this.menuName = menuName;
		this.remark = remark;
	}


	@Column(name = "FILE_TITLE", length = 516)
	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}


	@Column(name = "MENU_ID", length = 128)
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "MENU_NAME", length = 128)
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "REMARK", length = 1024)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public List<CmsAffix> getCmsAffixs() {
		return cmsAffixs;
	}
	
	public void setCmsAffixs(List<CmsAffix> cmsAffixs) {
		this.cmsAffixs = cmsAffixs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((fileTitle == null) ? 0 : fileTitle.hashCode());
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		result = prime * result
				+ ((menuName == null) ? 0 : menuName.hashCode());
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
		CmsFile other = (CmsFile) obj;
		if (fileTitle == null) {
			if (other.fileTitle != null)
				return false;
		} else if (!fileTitle.equals(other.fileTitle))
			return false;
		if (menuId == null) {
			if (other.menuId != null)
				return false;
		} else if (!menuId.equals(other.menuId))
			return false;
		if (menuName == null) {
			if (other.menuName != null)
				return false;
		} else if (!menuName.equals(other.menuName))
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
		return "CmsFile [fileTitle=" + fileTitle + ", menuId=" + menuId
				+ ", menuName=" + menuName + ", remark=" + remark
				+ ", cmsAffixs=" + cmsAffixs + "]";
	}

}