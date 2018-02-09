package com.xjj.cms.vote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.xjj.cms.base.model.CMSBaseModel;

@Entity
@Table(name = "CMS_VOTE_DETAIL")
public class CmsVoteDetail extends CMSBaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String detail_name;//投票项明细名称
	private Integer vote_num;//投票数
	private Integer sort;//排序
	private String is_valid;//是否有效
	
	private String item_id;//对应投票项
	
	public CmsVoteDetail() {
		super();
	}
	
	public CmsVoteDetail(String detail_name, Integer vote_num, Integer sort,
			String is_valid, String item_id) {
		super();
		this.detail_name = detail_name;
		this.vote_num = vote_num;
		this.sort = sort;
		this.is_valid = is_valid;
		this.item_id = item_id;
	}



	@Column(name = "DETAIL_NAME", length = 516)
	public String getDetail_name() {
		return detail_name;
	}
	public void setDetail_name(String detail_name) {
		this.detail_name = detail_name;
	}
	@Column(name = "VOTE_NUM")
	public Integer getVote_num() {
		return vote_num;
	}
	public void setVote_num(Integer vote_num) {
		this.vote_num = vote_num;
	}
	@Column(name = "SORT")
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Column(name = "IS_VALID", length = 32)
	public String getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	@Column(name = "ITEM_ID", length = 128)
	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((detail_name == null) ? 0 : detail_name.hashCode());
		result = prime * result
				+ ((is_valid == null) ? 0 : is_valid.hashCode());
		result = prime * result + ((item_id == null) ? 0 : item_id.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result
				+ ((vote_num == null) ? 0 : vote_num.hashCode());
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
		CmsVoteDetail other = (CmsVoteDetail) obj;
		if (detail_name == null) {
			if (other.detail_name != null)
				return false;
		} else if (!detail_name.equals(other.detail_name))
			return false;
		if (is_valid == null) {
			if (other.is_valid != null)
				return false;
		} else if (!is_valid.equals(other.is_valid))
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
		if (vote_num == null) {
			if (other.vote_num != null)
				return false;
		} else if (!vote_num.equals(other.vote_num))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CmsVoteDetail [detail_name=" + detail_name + ", vote_num="
				+ vote_num + ", sort=" + sort + ", is_valid=" + is_valid
				+ ", item_id=" + item_id + "]";
	}
	
	
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "ITEM_ID",updatable = true, insertable = true)
//	@NotFound(action=NotFoundAction.IGNORE)
//	public CmsVoteItem getCmsVoteItem() {
//		return cmsVoteItem;
//	}
//
//	public void setCmsVoteItem(CmsVoteItem cmsVoteItem) {
//		this.cmsVoteItem = cmsVoteItem;
//	}
	
	
}
