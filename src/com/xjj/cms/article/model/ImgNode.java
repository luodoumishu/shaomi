/**
 * sgyweb
 * ImgNode.java
 * @Copyright: 2014 海南新境界软件有限公司. All Right Reserved.
 * @author yeyunfeng 2014-9-11 下午5:19:41 
 * @Description: 本内容仅限于海南新境界软件有限公司内部使用，禁止转发.
 */
package com.xjj.cms.article.model;

/**
 * <p>
 * @author yeyunfeng 2014-9-11 下午5:19:41 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-11
 * @modify by reason:{方法名}:{原因}
 */
public class ImgNode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String alt;
	private String src;
	
	
	public ImgNode(String title, String alt, String src) {
		super();
		this.title = title;
		this.alt = alt;
		this.src = src;
	}

	@Override
	public String toString() {
		return "ImgNode [title=" + title + ", alt=" + alt + ", src=" + src
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alt == null) ? 0 : alt.hashCode());
		result = prime * result + ((src == null) ? 0 : src.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		ImgNode other = (ImgNode) obj;
		if (alt == null) {
			if (other.alt != null) {
				return false;
			}
		} else if (!alt.equals(other.alt)) {
			return false;
		}
		if (src == null) {
			if (other.src != null) {
				return false;
			}
		} else if (!src.equals(other.src)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	
}
