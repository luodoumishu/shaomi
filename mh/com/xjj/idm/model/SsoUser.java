package com.xjj.idm.model;

import java.util.Date;

@SuppressWarnings("serial")
public class SsoUser implements java.io.Serializable {

	private Long id;
	private String name;
	private String account;
	private Long orgid;
	private Date pwdlimitdate;
	private Long loginmode;
	private Long pwderrnum;
	private Long pwderrlock;
	private Long priority;
	private String duty;
	private String addr;
	private String phoneno;
	private String mobileno;
	private String faxno;
	private String email;
	private String gender;
	private String userdesc;
	private String certificate;
	private Long status;
	private String password;

	// Constructors

	/** default constructor */
	public SsoUser() {
	}

	/** minimal constructor */
	public SsoUser(String name, String account, Long orgid, Date pwdlimitdate,
			Long loginmode, Long pwderrnum, Long pwderrlock, Long priority,
			String gender, Long status) {
		this.name = name;
		this.account = account;
		this.orgid = orgid;
		this.pwdlimitdate = pwdlimitdate;
		this.loginmode = loginmode;
		this.pwderrnum = pwderrnum;
		this.pwderrlock = pwderrlock;
		this.priority = priority;
		this.gender = gender;
		this.status = status;
	}

	/** full constructor */
	public SsoUser(String name, String account, Long orgid, Date pwdlimitdate,
			Long loginmode, Long pwderrnum, Long pwderrlock, Long priority,
			String duty, String addr, String phoneno, String mobileno,
			String faxno, String email, String gender, String userdesc,
			String certificate, Long status, String password) {
		this.name = name;
		this.account = account;
		this.orgid = orgid;
		this.pwdlimitdate = pwdlimitdate;
		this.loginmode = loginmode;
		this.pwderrnum = pwderrnum;
		this.pwderrlock = pwderrlock;
		this.priority = priority;
		this.duty = duty;
		this.addr = addr;
		this.phoneno = phoneno;
		this.mobileno = mobileno;
		this.faxno = faxno;
		this.email = email;
		this.gender = gender;
		this.userdesc = userdesc;
		this.certificate = certificate;
		this.status = status;
		this.password = password;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Date getPwdlimitdate() {
		return this.pwdlimitdate;
	}

	public void setPwdlimitdate(Date pwdlimitdate) {
		this.pwdlimitdate = pwdlimitdate;
	}

	public Long getLoginmode() {
		return this.loginmode;
	}

	public void setLoginmode(Long loginmode) {
		this.loginmode = loginmode;
	}

	public Long getPwderrnum() {
		return this.pwderrnum;
	}

	public void setPwderrnum(Long pwderrnum) {
		this.pwderrnum = pwderrnum;
	}

	public Long getPwderrlock() {
		return this.pwderrlock;
	}

	public void setPwderrlock(Long pwderrlock) {
		this.pwderrlock = pwderrlock;
	}

	public Long getPriority() {
		return this.priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhoneno() {
		return this.phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getMobileno() {
		return this.mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getFaxno() {
		return this.faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserdesc() {
		return this.userdesc;
	}

	public void setUserdesc(String userdesc) {
		this.userdesc = userdesc;
	}

	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}