package com.xjj._extensions.attachment.model;

import java.util.Date;

public class UploadInitialFile {

	private String dataId;
	
	private String name;
	
	private long size;
	
	private String extension;
	
	private String uploadSuccess;
	
	private String localname;
	
	private Date createTime;
	
	private String userName;

	

	public UploadInitialFile(String dataId, String name, long size,
			String extension, String uploadSuccess, String localname,
			Date createTime, String userName) {
		super();
		this.dataId = dataId;
		this.name = name;
		this.size = size;
		this.extension = extension;
		this.uploadSuccess = uploadSuccess;
		this.localname = localname;
		this.createTime = createTime;
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getUploadSuccess() {
		return uploadSuccess;
	}

	public void setUploadSuccess(String uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getLocalname() {
		return localname;
	}

	public void setLocalname(String localname) {
		this.localname = localname;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
