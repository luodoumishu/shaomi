package com.xjj.idm.model;

@SuppressWarnings("serial")
public class SsoOrg implements java.io.Serializable {

	private Long id;
	private String name;
	private String orgdesc;
	private Long parentId;
	private Long priority;
	private String phoneno;
	private String faxno;
	private String addr;
	private String linkman;
	private String linkmanPhoneno;
	private Long status;

	// Constructors

	/** default constructor */
	public SsoOrg() {
	}

	/** minimal constructor */
	public SsoOrg(String name, Long parentId) {
		this.name = name;
		this.parentId = parentId;
	}

	/** full constructor */
	public SsoOrg(String name, String orgdesc, Long parentId, Long priority,
			String phoneno, String faxno, String addr, String linkman,
			String linkmanPhoneno, Long status) {
		this.name = name;
		this.orgdesc = orgdesc;
		this.parentId = parentId;
		this.priority = priority;
		this.phoneno = phoneno;
		this.faxno = faxno;
		this.addr = addr;
		this.linkman = linkman;
		this.linkmanPhoneno = linkmanPhoneno;
		this.status = status;
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

	public String getOrgdesc() {
		return this.orgdesc;
	}

	public void setOrgdesc(String orgdesc) {
		this.orgdesc = orgdesc;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getPriority() {
		return this.priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public String getPhoneno() {
		return this.phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getFaxno() {
		return this.faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkmanPhoneno() {
		return this.linkmanPhoneno;
	}

	public void setLinkmanPhoneno(String linkmanPhoneno) {
		this.linkmanPhoneno = linkmanPhoneno;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}