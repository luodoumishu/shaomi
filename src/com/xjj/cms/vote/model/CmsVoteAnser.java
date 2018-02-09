package com.xjj.cms.vote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.cms.base.model.CMSBaseModel;
@Entity
@Table(name = "CMS_VOTE_ANSER")
public class CmsVoteAnser extends CMSBaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String item_id;//投票项ID
	private String content;//问答信息
	private Integer sort;//排序
	
	
	public CmsVoteAnser() {
	}


	public CmsVoteAnser(String item_id, String content, Integer sort) {
		this.item_id = item_id;
		this.content = content;
		this.sort = sort;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((item_id == null) ? 0 : item_id.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CmsVoteAnser other = (CmsVoteAnser) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (item_id == null) {
			if (other.item_id != null)
				return false;
		} else if (!item_id.equals(other.item_id))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "CmsVoteAnser [item_id=" + item_id + ", content=" + content
				+ ", sort=" + sort + "]";
	}

	@Column(name = "ITEM_ID", length = 128)
	public String getItem_id() {
		return item_id;
	}


	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	@Column(name = "CONTENT", length = 1024)
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "SORT")
	public Integer getSort() {
		return sort;
	}


	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
}
