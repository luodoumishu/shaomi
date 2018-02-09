package com.xjj.idm.userInfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xjj.framework.audit.NoAuditLog;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_USER_HEAD_PICTURE")
@NoAuditLog
public class UserHead implements java.io.Serializable {
	
	private String id;
	private String account;
	private String picturePath;


	public UserHead() {
	}
	public UserHead(String id, String account, String picturePath) {
		super();
		this.id = id;
		this.account = account;
		this.picturePath = picturePath;
	}
	public UserHead(String account, String picturePath) {
		super();
		this.account = account;
		this.picturePath = picturePath;
	}

	@Id
	@GenericGenerator(name="system-uuid", strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator="system-uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "ACCOUNT", length = 50)
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "PICTURE_PATH", length = 200)
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

}