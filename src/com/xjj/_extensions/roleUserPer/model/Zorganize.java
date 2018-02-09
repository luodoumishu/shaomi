package com.xjj._extensions.roleUserPer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xjj.framework.audit.NoAuditLog;
import com.xjj.framework.core.model.BaseModel;
import com.xjj.framework.core.model.annotation.Label;

@Entity
@Table(name = "Z_ORGANIZE_T")
@Label( "单位信息")
@NoAuditLog
public class Zorganize extends BaseModel{
	
	private String name;
	
	private String parentId;
	
	private String note;
	
	private Integer pri;//排序
	
	private Date createTime;//创建时间
	
	private String fullPath;

	private String parentName;
	
	private boolean isParent;
	
	private String pyCode; //部门拼音代码
	
	private Boolean nocheck;
	
	@Column(name = "name", length = 200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "parentId", length = 50)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "note", length = 500)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "pri")
	public Integer getPri() {
		return pri;
	}

	public void setPri(Integer pri) {
		this.pri = pri;
	}

	@Column(name = "fullPath", length = 600)
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	@Column(name = "createTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "parentname", length = 200)
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Transient
	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	@Column(name = "PY_CODE", length = 500)
	public String getPyCode() {
		return pyCode;
	}
	
	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}

	@Transient
	public Boolean getNocheck() {
		return nocheck;
	}
	
	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}
	
	@Override
	public String toString() {
		return "Zorganize [name=" + name + ", parentId=" + parentId + ", note="
				+ note + ", pri=" + pri + ", createTime=" + createTime
				+ ", fullPath=" + fullPath + ", parentName=" + parentName
				+ ", isParent=" + isParent + ", pyCode=" + pyCode + "]";
	}

}
