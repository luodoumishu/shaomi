package com.xjj.cms.effect.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.cms.base.model.CMSBaseModel;

/**
 * 特效管理
 * <p>
 * 
 * @author yeyunfeng 2015年6月10日 下午7:15:23
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年6月10日
 * @modify by reason:{方法名}:{原因}
 */
@Entity
@Table(name = "CMS_EFFECT")
public class CmsEffect extends CMSBaseModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 特效名称
	 */
	private String name;
	/**
	 * 特效代码
	 */
	private String code;
	/**
	 * 特效类型0-飘窗 1-自定义
	 */
	private Integer type;
	/**
	 * 飘窗移动的步长，值越大移动速度越快
	 */
	private Integer step;
	/**
	 * 飘窗移动的时间间隔,单位为毫秒，值越小移动速度越快
	 */
	private Integer delay;
	/**
	 * 飘窗X轴偏移量
	 */
	private Integer x_offset;
	/**
	 * 飘窗Y轴偏移量
	 */
	private Integer y_offset;
	/**
	 * 飘窗图片地址
	 */
	private String imgUrl;
	/**
	 * 飘窗链接地址
	 */
	private String url;
	/**
	 * HTML代码
	 */
	private String htmlCode;
	/**
	 * 0-显示 1-隐藏
	 */
	private Integer isShow;
	/**
	 * 排序
	 */
	private Integer sortNo;

	/**
	 * 部门ID
	 */
	private String orgId;

	// Constructors

	/** default constructor */
	public CmsEffect() {
	}

	public CmsEffect(String name, String code, Integer type, Integer step,
			Integer delay, Integer x_offset, Integer y_offset, String imgUrl,
			String url, String htmlCode, Integer isShow, Integer sortNo,
			String orgId) {
		super();
		this.name = name;
		this.code = code;
		this.type = type;
		this.step = step;
		this.delay = delay;
		this.x_offset = x_offset;
		this.y_offset = y_offset;
		this.imgUrl = imgUrl;
		this.url = url;
		this.htmlCode = htmlCode;
		this.isShow = isShow;
		this.sortNo = sortNo;
		this.orgId = orgId;
	}

	@Column(name = "NAME", length = 516)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE", length = 128)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "TYPE")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "STEP")
	public Integer getStep() {
		return this.step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	@Column(name = "DELAY")
	public Integer getDelay() {
		return this.delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	@Column(name = "X_OFFSET")
	public Integer getX_offset() {
		return x_offset;
	}
	
	public void setX_offset(Integer x_offset) {
		this.x_offset = x_offset;
	}

	@Column(name = "Y_OFFSET")
	public Integer getY_offset() {
		return y_offset;
	}
	
	public void setY_offset(Integer y_offset) {
		this.y_offset = y_offset;
	}
	
	@Column(name = "IMG_URL", length = 516)
	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Column(name = "URL", length = 516)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "HTML_CODE")
	public String getHtmlCode() {
		return this.htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}

	@Column(name = "IS_SHOW")
	public Integer getIsShow() {
		return this.isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	@Column(name = "SORTNO")
	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	@Column(name = "ORG_ID", length = 128)
	public String getOrgId() {
		return orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
	public String toString() {
		return "CmsEffect [name=" + name + ", code=" + code + ", type=" + type
				+ ", step=" + step + ", delay=" + delay + ", x_offset="
				+ x_offset + ", y_offset=" + y_offset + ", imgUrl=" + imgUrl
				+ ", url=" + url + ", htmlCode=" + htmlCode + ", isShow="
				+ isShow + ", sortNo=" + sortNo +", orgId=" + orgId +  "]";
	}

}