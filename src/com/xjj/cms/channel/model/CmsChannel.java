package com.xjj.cms.channel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.cms.base.model.CMSBaseModel;

/**
 * CmsChannel entity 频道实体
 */
@Entity
@Table(name = "CMS_CHANNEL")
public class CmsChannel extends CMSBaseModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 频道名称
	 */
	private String channeName;
	/**
	 * 频道代码
	 */
	private String channeCode;
	/**
	 * 排序号
	 */
	private Integer sortno;
	/**
	 * 父频道ID
	 */
	private String parentChanneid;
	/**
	 * 父频道ID
	 */
	private String parentChanneName;
	
	/**
	 * 关联类型 0-栏目类型 1-超链接类型 2-投票类型
	 */
	private String relevanceType;
	/**
	 * 显示方式 0-标题 1-内容
	 */
	private String showMode;
	/**
	 * 备注
	 */
	private String demo;
	
	private Integer childMenuNum;

	// Constructors

	/** default constructor */
	public CmsChannel() {

	}

	public CmsChannel(String channeName, String channeCode, Integer sortno,
			String parentChanneid, String parentChanneName,String relevanceType, String showMode,
			String demo,Integer childMenuNum) {
		this.channeName = channeName;
		this.channeCode = channeCode;
		this.sortno = sortno;
		this.parentChanneid = parentChanneid;
		this.relevanceType = relevanceType;
		this.showMode = showMode;
		this.demo = demo;
		this.parentChanneName = parentChanneName;
		this.childMenuNum = childMenuNum;
	}

	@Column(name = "CHANNE_NAME", length = 128)
	public String getChanneName() {
		return this.channeName;
	}

	public void setChanneName(String channeName) {
		this.channeName = channeName;
	}

	@Column(name = "CHANNE_CODE", length = 64)
	public String getChanneCode() {
		return this.channeCode;
	}

	public void setChanneCode(String channeCode) {
		this.channeCode = channeCode;
	}

	@Column(name = "SORTNO")
	public Integer getSortno() {
		return this.sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	@Column(name = "PARENT_CHANNEID", length = 128)
	public String getParentChanneid() {
		return this.parentChanneid;
	}

	public void setParentChanneid(String parentChanneid) {
		this.parentChanneid = parentChanneid;
	}
	
	@Column(name = "PARENT_CHANNENAME", length = 128)
	public String getParentChanneName() {
		return parentChanneName;
	}
	
	public void setParentChanneName(String parentChanneName) {
		this.parentChanneName = parentChanneName;
	}
	
	@Column(name = "RELEVANCE_TYPE", length = 64)
	public String getRelevanceType() {
		return this.relevanceType;
	}

	public void setRelevanceType(String relevanceType) {
		this.relevanceType = relevanceType;
	}

	@Column(name = "SHOW_MODE", length = 64)
	public String getShowMode() {
		return this.showMode;
	}

	public void setShowMode(String showMode) {
		this.showMode = showMode;
	}

	@Column(name = "DEMO", length = 1024)
	public String getDemo() {
		return this.demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}
	
	@Column(name = "CHILD_MENU_NUM")
	public Integer getChildMenuNum() {
		return childMenuNum;
	}

	public void setChildMenuNum(Integer childMenuNum) {
		this.childMenuNum = childMenuNum;
	}

	@Override
	public String toString() {
		return "CmsChannel [channeName=" + channeName + ", channeCode="
				+ channeCode + ", sortno=" + sortno + ", parentChanneid="
				+ parentChanneid + ", parentChanneName=" + parentChanneName
				+ ", relevanceType=" + relevanceType + ", showMode=" + showMode
				+ ", demo=" + demo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((channeCode == null) ? 0 : channeCode.hashCode());
		result = prime * result
				+ ((channeName == null) ? 0 : channeName.hashCode());
		result = prime * result + ((demo == null) ? 0 : demo.hashCode());
		result = prime
				* result
				+ ((parentChanneName == null) ? 0 : parentChanneName.hashCode());
		result = prime * result
				+ ((parentChanneid == null) ? 0 : parentChanneid.hashCode());
		result = prime * result
				+ ((relevanceType == null) ? 0 : relevanceType.hashCode());
		result = prime * result
				+ ((showMode == null) ? 0 : showMode.hashCode());
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
		CmsChannel other = (CmsChannel) obj;
		if (channeCode == null) {
			if (other.channeCode != null) {
				return false;
			}
		} else if (!channeCode.equals(other.channeCode)) {
			return false;
		}
		if (channeName == null) {
			if (other.channeName != null) {
				return false;
			}
		} else if (!channeName.equals(other.channeName)) {
			return false;
		}
		if (demo == null) {
			if (other.demo != null) {
				return false;
			}
		} else if (!demo.equals(other.demo)) {
			return false;
		}
		if (parentChanneName == null) {
			if (other.parentChanneName != null) {
				return false;
			}
		} else if (!parentChanneName.equals(other.parentChanneName)) {
			return false;
		}
		if (parentChanneid == null) {
			if (other.parentChanneid != null) {
				return false;
			}
		} else if (!parentChanneid.equals(other.parentChanneid)) {
			return false;
		}
		if (relevanceType == null) {
			if (other.relevanceType != null) {
				return false;
			}
		} else if (!relevanceType.equals(other.relevanceType)) {
			return false;
		}
		if (showMode == null) {
			if (other.showMode != null) {
				return false;
			}
		} else if (!showMode.equals(other.showMode)) {
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