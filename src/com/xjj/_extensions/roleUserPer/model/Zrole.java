package com.xjj._extensions.roleUserPer.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xjj.framework.audit.NoAuditLog;
import com.xjj.framework.core.model.BaseModel;
import com.xjj.framework.core.model.annotation.Label;

@Entity
@Table(name = "z_role_t")
@Label( "角色信息")
@NoAuditLog
public class Zrole extends BaseModel{
	
	private String name;//角色名称
	private String code;//角色代码
	private Integer pri;//排序
	private String remarks;//备注
	private Date createTime;//创建时间
	private String permissions;//多个权限代码用逗号","隔开
	private String module;//模块名
	private Integer isValid;
	private Set<Zuser> users;

	@Column(name = "name", length = 250)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "pri")
	public Integer getPri() {
		return pri;
	}

	public void setPri(Integer pri) {
		this.pri = pri;
	}

	@Column(name = "remarks", length = 250)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "createTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "z_role_user_t",
	joinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name = "userId", referencedColumnName ="id")})
	@OrderBy("pri asc")
	public Set<Zuser> getUsers() {
		return users;
	}

	public void setUsers(Set<Zuser> users) {
		this.users = users;
	}

	@Column(name = "PERMISSIONS", length = 4000)
	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	@Column(name = "module", length = 150)
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Column(name = "isvalid")
	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	
}
