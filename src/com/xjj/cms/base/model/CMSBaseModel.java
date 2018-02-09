/**
 * sgyweb
 * CMSBaseModel.java
 * @Copyright: 2014 海南新境界软件有限公司. All Right Reserved.
 * @author yeyunfeng 2014-8-19 上午11:54:53 
 * @Description: 本内容仅限于海南新境界软件有限公司内部使用，禁止转发.
 */
package com.xjj.cms.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xjj.framework.core.model.BaseModel;

/**
 * <p>
 * CMS基础实体类
 * 
 * @author yeyunfeng 2014-8-19 上午11:54:53
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-8-19
 * @modify by reason:{方法名}:{原因}
 */
@MappedSuperclass
public class CMSBaseModel extends BaseModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人Id
	 */
	private String creatUserId;
	/**
	 * 创建人名称
	 */
	private String creatUserName;
	/**
	 * 最后修改时间
	 */
	private Date endModifyTime;
	/**
	 * 最后修改人Id
	 */
	private String endModifyUserId;

	/**
	 * 最后修改人名称
	 */
	private String endModifyUserName;
	
	/**
	 * 删除   0-未删除  1-逻辑删除
	 */
	private String isDelete;

	// Property accessors
	
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREAT_USERID", length = 128)
	public String getCreatUserId() {
		return creatUserId;
	}
	
	public void setCreatUserId(String creatUserId) {
		this.creatUserId = creatUserId;
	}

	@Column(name = "CREAT_USERNAME", length = 128)
	public String getCreatUserName() {
		return this.creatUserName;
	}

	public void setCreatUserName(String creatUserName) {
		this.creatUserName = creatUserName;
	}

	@Column(name = "ENDMODIFY_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	public Date getEndModifyTime() {
		return endModifyTime;
	}
	
	public void setEndModifyTime(Date endModifyTime) {
		this.endModifyTime = endModifyTime;
	}

	@Column(name = "ENDMODIFY_USERID", length = 128)
	public String getEndModifyUserId() {
		return endModifyUserId;
	}
	
	public void setEndModifyUserId(String endModifyUserId) {
		this.endModifyUserId = endModifyUserId;
	}

	@Column(name = "ENDMODIFY_USERNAME", length = 128)
	public String getEndModifyUserName() {
		return this.endModifyUserName;
	}

	public void setEndModifyUserName(String endModifyUserName) {
		this.endModifyUserName = endModifyUserName;
	}
	@Column(name = "IS_DELETE", length = 32)
	public String getIsDelete() {
		return isDelete;
	}
	
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "CMSBaseModel [createTime=" + createTime + ", creatUserId="
				+ creatUserId + ", creatUserName=" + creatUserName
				+ ", endModifyTime=" + endModifyTime + ", endModifyUserId="
				+ endModifyUserId + ", endModifyUserName=" + endModifyUserName
				+ ", isDelete=" + isDelete + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creatUserId == null) ? 0 : creatUserId.hashCode());
		result = prime * result
				+ ((creatUserName == null) ? 0 : creatUserName.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((endModifyTime == null) ? 0 : endModifyTime.hashCode());
		result = prime * result
				+ ((endModifyUserId == null) ? 0 : endModifyUserId.hashCode());
		result = prime
				* result
				+ ((endModifyUserName == null) ? 0 : endModifyUserName
						.hashCode());
		result = prime * result
				+ ((isDelete == null) ? 0 : isDelete.hashCode());
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
		CMSBaseModel other = (CMSBaseModel) obj;
		if (creatUserId == null) {
			if (other.creatUserId != null) {
				return false;
			}
		} else if (!creatUserId.equals(other.creatUserId)) {
			return false;
		}
		if (creatUserName == null) {
			if (other.creatUserName != null) {
				return false;
			}
		} else if (!creatUserName.equals(other.creatUserName)) {
			return false;
		}
		if (createTime == null) {
			if (other.createTime != null) {
				return false;
			}
		} else if (!createTime.equals(other.createTime)) {
			return false;
		}
		if (endModifyTime == null) {
			if (other.endModifyTime != null) {
				return false;
			}
		} else if (!endModifyTime.equals(other.endModifyTime)) {
			return false;
		}
		if (endModifyUserId == null) {
			if (other.endModifyUserId != null) {
				return false;
			}
		} else if (!endModifyUserId.equals(other.endModifyUserId)) {
			return false;
		}
		if (endModifyUserName == null) {
			if (other.endModifyUserName != null) {
				return false;
			}
		} else if (!endModifyUserName.equals(other.endModifyUserName)) {
			return false;
		}
		if (isDelete == null) {
			if (other.isDelete != null) {
				return false;
			}
		} else if (!isDelete.equals(other.isDelete)) {
			return false;
		}
		return true;
	}

}
