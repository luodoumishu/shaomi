package com.xjj._extensions.attachment.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj.framework.audit.NoAuditLog;
import com.xjj.framework.core.model.BaseModel;
import com.xjj.framework.core.model.annotation.Label;

@Entity
@Table(name = "Z_ATTACH_T")
@Label( "附件表")
@NoAuditLog
public class CommonDocumentModel extends BaseModel{
	public CommonDocumentModel() {
	}
	@Column(name = "DATAID", length = 255)
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	@Column(name = "FILENAME", length = 255)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Column(name = "FILEPATH", length = 255)
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	@Column(name = "EXT", length = 255)
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
	@Column(name = "FILESIZE", length = 255)
	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	@Column(name = "LOCALNAME", length = 255)
	public String getLocalname() {
		return localname;
	}

	public void setLocalname(String localname) {
		this.localname = localname;
	}
	@Column(name = "CREATETIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "TYPEID", length = 255)
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	@Column(name = "ALIASES", length = 255)
	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}
	@Column(name = "TYPENAME", length = 255)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Column(name = "pri")
	public Integer getPri() {
		return pri;
	}

	public void setPri(Integer pri) {
		this.pri = pri;
	}

	
	@Column(name = "userId")
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId",updatable = false, insertable = false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Zuser getZuser() {
		return zuser;
	}
	
	public void setZuser(Zuser zuser) {
		this.zuser = zuser;
	}

	private String dataId;
	private String filename;
	private String filepath;
	private String ext;
	private String filesize;
	private String localname;
	private Date createTime;
	private String typeId;
	private String aliases;
	private String typeName;
	private Integer pri;
	private String userId;
	private Zuser zuser;
	
}
