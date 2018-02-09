package com.xjj.cms.vote.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.xjj.cms.base.model.CMSBaseModel;


@Entity
@Table(name = "CMS_VOTE_ITEM")
public class CmsVoteItem extends CMSBaseModel implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String item_name;//投票项名称
	private String item_type;//投票类型(0单1多2问答)
	private String show_type;//显示类型(0横排1竖排)
	private String is_valid;//是否有效
	private Integer sort;//排序
	//主题
	private String theme_id;//对应主题
	
	private List<CmsVoteDetail> cmsVoteDetails;
	
	public CmsVoteItem() {
		super();
	}

	public CmsVoteItem(String item_name, String item_type, String show_type,
			String is_valid, Integer sort, String theme_id,
			List<CmsVoteDetail> cmsVoteDetails) {
		super();
		this.item_name = item_name;
		this.item_type = item_type;
		this.show_type = show_type;
		this.is_valid = is_valid;
		this.sort = sort;
		this.theme_id = theme_id;
		this.cmsVoteDetails = cmsVoteDetails;
	}






	@Column(name = "ITEM_NAME", length = 516)
	public String getItem_name() {
		return item_name;
	}


	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}


	@Column(name = "ITEM_TYPE", length = 32)
	public String getItem_type() {
		return item_type;
	}


	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	
	@Column(name = "SHOW_TYPE", length = 32)
	public String getShow_type() {
		return show_type;
	}


	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}

	@Column(name = "IS_VALID", length = 32)
	public String getIs_valid() {
		return is_valid;
	}


	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}

	@Column(name = "SORT")
	public Integer getSort() {
		return sort;
	}


	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Column(name = "THEME_ID", length = 128)
	public String getTheme_id() {
		return theme_id;
	}

	public void setTheme_id(String theme_id) {
		this.theme_id = theme_id;
	}
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "THEME_ID",updatable = true, insertable = true)
//	@NotFound(action=NotFoundAction.IGNORE)
//	public CmsVoteTheme getCmsVoteTheme() {
//		return cmsVoteTheme;
//	}
//
//	public void setCmsVoteTheme(CmsVoteTheme cmsVoteTheme) {
//		this.cmsVoteTheme = cmsVoteTheme;
//	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)  
    @JoinColumn(name="ITEM_ID")
	@OrderBy("sort asc")
	public List<CmsVoteDetail> getCmsVoteDetails() {
		return cmsVoteDetails;
	}

	public void setCmsVoteDetails(List<CmsVoteDetail> cmsVoteDetails) {
		this.cmsVoteDetails = cmsVoteDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((cmsVoteDetails == null) ? 0 : cmsVoteDetails.hashCode());
		result = prime * result
				+ ((is_valid == null) ? 0 : is_valid.hashCode());
		result = prime * result
				+ ((item_name == null) ? 0 : item_name.hashCode());
		result = prime * result
				+ ((item_type == null) ? 0 : item_type.hashCode());
		result = prime * result
				+ ((show_type == null) ? 0 : show_type.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result
				+ ((theme_id == null) ? 0 : theme_id.hashCode());
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
		CmsVoteItem other = (CmsVoteItem) obj;
		if (cmsVoteDetails == null) {
			if (other.cmsVoteDetails != null)
				return false;
		} else if (!cmsVoteDetails.equals(other.cmsVoteDetails))
			return false;
		if (is_valid == null) {
			if (other.is_valid != null)
				return false;
		} else if (!is_valid.equals(other.is_valid))
			return false;
		if (item_name == null) {
			if (other.item_name != null)
				return false;
		} else if (!item_name.equals(other.item_name))
			return false;
		if (item_type == null) {
			if (other.item_type != null)
				return false;
		} else if (!item_type.equals(other.item_type))
			return false;
		if (show_type == null) {
			if (other.show_type != null)
				return false;
		} else if (!show_type.equals(other.show_type))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		if (theme_id == null) {
			if (other.theme_id != null)
				return false;
		} else if (!theme_id.equals(other.theme_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CmsVoteItem [item_name=" + item_name + ", item_type="
				+ item_type + ", show_type=" + show_type + ", is_valid="
				+ is_valid + ", sort=" + sort + ", theme_id=" + theme_id + "]";
	}
	
}
