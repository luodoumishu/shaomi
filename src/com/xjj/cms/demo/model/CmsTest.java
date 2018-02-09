package com.xjj.cms.demo.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.framework.core.model.BaseModel;

/**
 * CmsAffix entity. @author MyEclipse Persistence Tools
 */
/**
 * 附件实体
 * <p>
 * @author yeyunfeng 2014-9-2 上午9:16:57 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
@Entity
@Table(name = "CMS_TEST")
public class CmsTest extends BaseModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String url;
	
	public CmsTest() {
		
	}
	public CmsTest(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	
	@Column(name = "NAME", length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "URL", length = 100)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CmsTest other = (CmsTest) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "CmsTest [name=" + name + ", url=" + url + "]";
	}
	
	
}