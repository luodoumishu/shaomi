package com.xjj.cms.base.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CmsAffix entity. @author MyEclipse Persistence Tools
 */
/**
 * 附件实体
 * <p>
 * @author yeyunfeng 2014-9-2 上午9:16:57 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
@Entity
@Table(name = "CMS_AFFIX")
public class CmsAffix extends CMSBaseModel implements java.io.Serializable {

	// Fields

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 对象model的id
	 */
	private String modeId;
	/**
	 * 附件名称(系统自己命名)
	 */
	private String affixName;
	/**
	 * 附件原始名称
	 */
	private String affix_originalName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 附件URL
	 */
	private String affixUrl;

	// Constructors

	/** default constructor */
	public CmsAffix() {
	}

	/** full constructor */
	public CmsAffix(String modeId, String affixName, String affix_originalName,
			String remark, 
			String affixUrl) {
		super();
		this.modeId = modeId;
		this.affixName = affixName;
		this.affix_originalName = affix_originalName;
		this.remark = remark;
		this.affixUrl = affixUrl;
	}
	

	@Override
	public String toString() {
		return "CmsAffix [modeId=" + modeId + ", affixName=" + affixName
				+ ", affix_originalName=" + affix_originalName + ", remark="
				+ remark + ", affixUrl=" + affixUrl + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((affixName == null) ? 0 : affixName.hashCode());
		result = prime * result
				+ ((affixUrl == null) ? 0 : affixUrl.hashCode());
		result = prime
				* result
				+ ((affix_originalName == null) ? 0 : affix_originalName
						.hashCode());
		result = prime * result + ((modeId == null) ? 0 : modeId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		CmsAffix other = (CmsAffix) obj;
		if (affixName == null) {
			if (other.affixName != null) {
				return false;
			}
		} else if (!affixName.equals(other.affixName)) {
			return false;
		}
		if (affixUrl == null) {
			if (other.affixUrl != null) {
				return false;
			}
		} else if (!affixUrl.equals(other.affixUrl)) {
			return false;
		}
		if (affix_originalName == null) {
			if (other.affix_originalName != null) {
				return false;
			}
		} else if (!affix_originalName.equals(other.affix_originalName)) {
			return false;
		}
		if (modeId == null) {
			if (other.modeId != null) {
				return false;
			}
		} else if (!modeId.equals(other.modeId)) {
			return false;
		}
		if (remark == null) {
			if (other.remark != null) {
				return false;
			}
		} else if (!remark.equals(other.remark)) {
			return false;
		}
		return true;
	}

	@Column(name = "MODEID", length = 128)
	public String getModeId() {
		return modeId;
	}
	public void setModeId(String modeId) {
		this.modeId = modeId;
	}
	
	// Property accessors
	@Column(name = "AFFIX_NAME", length = 516)
	public String getAffixName() {
		return this.affixName;
	}

	public void setAffixName(String affixName) {
		this.affixName = affixName;
	}

	@Column(name = "AFFIX_ORIGINALNAME", length = 516)
	public String getAffix_originalName() {
		return affix_originalName;
	}

	public void setAffix_originalName(String affix_originalName) {
		this.affix_originalName = affix_originalName;
	}

	@Column(name = "REMARK", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "AFFIX_URL", length = 516)
	public String getAffixUrl() {
		return this.affixUrl;
	}

	public void setAffixUrl(String affixUrl) {
		this.affixUrl = affixUrl;
	}

	
}