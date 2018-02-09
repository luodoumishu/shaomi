package com.xjj.cms.link.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.framework.core.model.BaseModel;

/**
 * Channel-Link entity 频道-超链接管理实体
 */
@Entity
@Table(name = "CMS_CHL_LINK")
public class CmsChlLink extends BaseModel implements java.io.Serializable{
	
	// Fields
	private static final long serialVersionUID = 1L;
	private String channelId;//频道id
	private String channelName;//频道名称
	private String linkId;//超链接项id
	private String linkName;//超链接名称
	private String linkAddr;// 超链接地址
	private String linkType;// 超链接类型
	private String sort;//排序号
	private String remark;//备注
	public CmsChlLink() {
		super();
	}
	
	public CmsChlLink(String channelId, String channelName, String linkId,
			String linkName, String linkAddr, String linkType, String sort,
			String remark) {
		super();
		this.channelId = channelId;
		this.channelName = channelName;
		this.linkId = linkId;
		this.linkName = linkName;
		this.linkAddr = linkAddr;
		this.linkType = linkType;
		this.sort = sort;
		this.remark = remark;
	}


	@Column(name = "CHANNEL_ID", length = 128)
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	@Column(name = "CHANNEL_NAME", length = 516)
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@Column(name = "LINK_ID", length = 128)
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	@Column(name = "LINK_NAME", length = 516)
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	@Column(name = "SORT", length = 128)
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	@Column(name = "REMARK", length = 1024)	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "LINK_ADDR", length = 1024)	
	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}
	@Column(name = "LINK_TYPE", length = 1024)	
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	
}
