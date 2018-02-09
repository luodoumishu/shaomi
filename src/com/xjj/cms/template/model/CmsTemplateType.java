package com.xjj.cms.template.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.cms.base.model.CMSBaseModel;

/**
 * CmsTemplateType entity. @author MyEclipse Persistence Tools
 */
/**
 * 模板类别
 * <p>
 * @author yeyunfeng 2015年5月28日 上午10:06:30 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年5月28日
 * @modify by reason:{方法名}:{原因}
 */
@Entity
@Table(name = "CMS_TEMPLATE_TYPE")
public class CmsTemplateType extends CMSBaseModel implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	/**
	 * 类别名称
	 */
	private String name;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 序号
	 */
	private Integer sortNo;

	// Constructors

	/** default constructor */
	public CmsTemplateType() {
	}


	/** full constructor */
	public CmsTemplateType(String name, String remark, Integer sortNo) {
		super();
		this.name = name;
		this.remark = remark;
		this.sortNo = sortNo;
	}

	// Property accessors

	@Column(name = "NAME", length = 516)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "REMARK", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "SORTNO")
	public Integer getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

}