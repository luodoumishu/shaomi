package com.xjj._extensions.roleUserPer.model;

import java.util.Date;
import java.util.List;
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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xjj._extensions.roleUserPer.util.SimpleZroleSerializer;
import com.xjj.framework.audit.NoAuditLog;
import com.xjj.framework.core.model.BaseModel;
import com.xjj.framework.core.model.annotation.Label;

@Entity
@Table(name = "z_user_t")
@Label( "用户信息")
@NoAuditLog
public class Zuser extends BaseModel{
	
	private String account;//登录账号
	private String password;//登录密码
	private String name;//用户名称
	private Integer pri;//排序
	private String remarks;//备注
	private String orgId;//单位id
	private Date createTime;//创建时间
	private String salt;
	private String orgRootId;//单位根id
	
	private String loginmode;
	private String gender;
	private String moblie;
	private String shortnum;
	private String acntlimitdate;
	private String pwdlimitdate;
	private String pwderrlock;
	private String oaduty;
	private String oaphoneno;
	private String oafaxno;
	private String oaemail;
	private String certificate;
	private String userdesc;
	private String status;   //0正常  -1不正常
	private String dn;
	private Integer oauserid;
	private String oldorg;
	private Date optime;
	private String email;
	
	/**
	 * 最后修改密码时间
	 */
	private Date lastupdatedate;
	/**
	 * 最近5次的原始密码
	 */
	private String originalpassword;
	
	/**
	 * 失败次数
	 */
	private int failuretimes;
	
	
	private List<Zorganize> orgs;
	private Set<Zrole> roles;

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

	@Column(name = "remarks", length = 250)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "orgId", length = 50)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name = "createTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "z_role_user_t",   
		joinColumns ={@JoinColumn(name = "userId", referencedColumnName = "id") },   
		inverseJoinColumns = { @JoinColumn(name = "roleId", referencedColumnName = "id")   
	})
	//@JsonBackReference
	@JsonSerialize(using = SimpleZroleSerializer.class)
	@OrderBy("pri asc")
	public Set<Zrole> getRoles() {
		return roles;
	}

	public void setRoles(Set<Zrole> roles) {
		this.roles = roles;
	}

	@Column(name = "salt", length = 250)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@ManyToMany()
	@JoinTable(name = "z_org_user_t",   
		joinColumns ={@JoinColumn(name = "userId", referencedColumnName = "id") },   
		inverseJoinColumns = { @JoinColumn(name = "orgId", referencedColumnName = "id")   
	})
	@OrderBy("pri asc")
	public List<Zorganize> getOrgs() {
		return orgs;
	}
	
	public void setOrgs(List<Zorganize> orgs) {
		this.orgs = orgs;
	}

	@Column(name = "ORGROOTID", length = 50)
	public String getOrgRootId() {
		return orgRootId;
	}

	public void setOrgRootId(String orgRootId) {
		this.orgRootId = orgRootId;
	}

	@Column(name = "LOGINMODE", length = 20)
	public String getLoginmode() {
		return this.loginmode;
	}

	public void setLoginmode(String loginmode) {
		this.loginmode = loginmode;
	}

	@Column(name = "GENDER", length = 4)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "MOBLIE", length = 20)
	public String getMoblie() {
		return this.moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}

	@Column(name = "SHORTNUM", length = 20)
	public String getShortnum() {
		return this.shortnum;
	}

	public void setShortnum(String shortnum) {
		this.shortnum = shortnum;
	}

	@Column(name = "ACNTLIMITDATE", length = 20)
	public String getAcntlimitdate() {
		return this.acntlimitdate;
	}

	public void setAcntlimitdate(String acntlimitdate) {
		this.acntlimitdate = acntlimitdate;
	}

	@Column(name = "PWDLIMITDATE", length = 20)
	public String getPwdlimitdate() {
		return this.pwdlimitdate;
	}

	public void setPwdlimitdate(String pwdlimitdate) {
		this.pwdlimitdate = pwdlimitdate;
	}

	@Column(name = "PWDERRLOCK", length = 10)
	public String getPwderrlock() {
		return this.pwderrlock;
	}

	public void setPwderrlock(String pwderrlock) {
		this.pwderrlock = pwderrlock;
	}

	@Column(name = "OADUTY", length = 50)
	public String getOaduty() {
		return this.oaduty;
	}

	public void setOaduty(String oaduty) {
		this.oaduty = oaduty;
	}

	@Column(name = "OAPHONENO", length = 20)
	public String getOaphoneno() {
		return this.oaphoneno;
	}

	public void setOaphoneno(String oaphoneno) {
		this.oaphoneno = oaphoneno;
	}

	@Column(name = "OAFAXNO", length = 20)
	public String getOafaxno() {
		return this.oafaxno;
	}

	public void setOafaxno(String oafaxno) {
		this.oafaxno = oafaxno;
	}

	@Column(name = "OAEMAIL", length = 200)
	public String getOaemail() {
		return this.oaemail;
	}

	public void setOaemail(String oaemail) {
		this.oaemail = oaemail;
	}

	@Column(name = "CERTIFICATE")
	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Column(name = "USERDESC")
	public String getUserdesc() {
		return this.userdesc;
	}

	public void setUserdesc(String userdesc) {
		this.userdesc = userdesc;
	}

	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DN")
	public String getDn() {
		return this.dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	@Column(name = "OAUSERID")
	public Integer getOauserid() {
		return this.oauserid;
	}

	public void setOauserid(Integer oauserid) {
		this.oauserid = oauserid;
	}

	@Column(name = "OLDORG", length = 20)
	public String getOldorg() {
		return this.oldorg;
	}

	public void setOldorg(String oldorg) {
		this.oldorg = oldorg;
	}

	@Column(name = "OPTIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
	public Date getOptime() {
		return this.optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	@Column(name = "email", length = 150)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "lastupdatedate")
	public Date getLastupdatedate() {
		return lastupdatedate;
	}

	public void setLastupdatedate(Date lastupdatedate) {
		this.lastupdatedate = lastupdatedate;
	}

	@Column(name = "originalpassword")
	public String getOriginalpassword() {
		return originalpassword;
	}

	public void setOriginalpassword(String originalpassword) {
		this.originalpassword = originalpassword;
	}

	@Column(name = "failuretimes")
	public int getFailuretimes() {
		return failuretimes;
	}

	public void setFailuretimes(int failuretimes) {
		this.failuretimes = failuretimes;
	}
	
	
	
}
