package com.xjj.ws.distribute.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xjj.framework.audit.NoAuditLog;
import com.xjj.framework.core.model.annotation.Label;

@SuppressWarnings("serial")
@Entity
@Table(name = "z_user_t")
@Label( "用户信息")
@NoAuditLog
public class Zuser2 implements java.io.Serializable {
	
	private String id;
	private String account;	//登录账号
	private String password;//登录密码
	private String name;	//用户名称
	private Integer pri;	//排序
	private Date createTime;//创建时间
	private String phoneNo;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "account", length = 150)
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "password", length = 150)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name", length = 250)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pri")
	public Integer getPri() {
		return pri;
	}
	public void setPri(Integer pri) {
		this.pri = pri;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "oaphoneno", length = 20)
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
