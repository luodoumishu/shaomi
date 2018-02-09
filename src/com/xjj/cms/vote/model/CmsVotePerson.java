package com.xjj.cms.vote.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.xjj.cms.base.model.CMSBaseModel;

@Entity
@Table(name = "CMS_VOTE_PERSON")
public class CmsVotePerson extends CMSBaseModel implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//private String theme_id;//投票主题ID
	private String person_name;//投票人姓名
	private String person_mobile;//投票人手机
	private String person_addr;//投票人地址
	private String person_email;//投票人email
	private Date vote_time;//投票时间
	
	private CmsVoteTheme cmsVoteTheme;//对应主题

	public CmsVotePerson() {
		super();
	}

	public CmsVotePerson(String person_name,
			String person_mobile, String person_addr, String person_email,
			Date vote_time, CmsVoteTheme cmsVoteTheme) {
		super();
		//this.theme_id = theme_id;
		this.person_name = person_name;
		this.person_mobile = person_mobile;
		this.person_addr = person_addr;
		this.person_email = person_email;
		this.vote_time = vote_time;
		this.cmsVoteTheme = cmsVoteTheme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((cmsVoteTheme == null) ? 0 : cmsVoteTheme.hashCode());
		result = prime * result
				+ ((person_addr == null) ? 0 : person_addr.hashCode());
		result = prime * result
				+ ((person_email == null) ? 0 : person_email.hashCode());
		result = prime * result
				+ ((person_mobile == null) ? 0 : person_mobile.hashCode());
		result = prime * result
				+ ((person_name == null) ? 0 : person_name.hashCode());
		/**result = prime * result
				+ ((theme_id == null) ? 0 : theme_id.hashCode());*/
		result = prime * result
				+ ((vote_time == null) ? 0 : vote_time.hashCode());
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
		CmsVotePerson other = (CmsVotePerson) obj;
		if (cmsVoteTheme == null) {
			if (other.cmsVoteTheme != null)
				return false;
		} else if (!cmsVoteTheme.equals(other.cmsVoteTheme))
			return false;
		if (person_addr == null) {
			if (other.person_addr != null)
				return false;
		} else if (!person_addr.equals(other.person_addr))
			return false;
		if (person_email == null) {
			if (other.person_email != null)
				return false;
		} else if (!person_email.equals(other.person_email))
			return false;
		if (person_mobile == null) {
			if (other.person_mobile != null)
				return false;
		} else if (!person_mobile.equals(other.person_mobile))
			return false;
		if (person_name == null) {
			if (other.person_name != null)
				return false;
		} else if (!person_name.equals(other.person_name))
			return false;
		/**if (theme_id == null) {
			if (other.theme_id != null)
				return false;
		} else if (!theme_id.equals(other.theme_id))
			return false;*/
		if (vote_time == null) {
			if (other.vote_time != null)
				return false;
		} else if (!vote_time.equals(other.vote_time))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CmsVotePerson [person_name="
				+ person_name + ", person_mobile=" + person_mobile
				+ ", person_addr=" + person_addr + ", person_email="
				+ person_email + ", vote_time=" + vote_time + ", cmsVoteTheme="
				+ cmsVoteTheme + "]";
	}
	/**@Column(name = "THEME_ID", length = 128)
	public String getTheme_id() {
		return theme_id;
	}

	public void setTheme_id(String theme_id) {
		this.theme_id = theme_id;
	}*/
	@Column(name = "PERSON_NAME", length = 516)
	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}
	@Column(name = "PERSON_MOBILE", length = 516)
	public String getPerson_mobile() {
		return person_mobile;
	}

	public void setPerson_mobile(String person_mobile) {
		this.person_mobile = person_mobile;
	}
	@Column(name = "PERSON_ADDR", length = 516)
	public String getPerson_addr() {
		return person_addr;
	}

	public void setPerson_addr(String person_addr) {
		this.person_addr = person_addr;
	}
	@Column(name = "PERSON_EMAIL", length = 516)
	public String getPerson_email() {
		return person_email;
	}

	public void setPerson_email(String person_email) {
		this.person_email = person_email;
	}
	@Column(name = "VOTE_TIME")
	public Date getVote_time() {
		return vote_time;
	}

	public void setVote_time(Date vote_time) {
		this.vote_time = vote_time;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "THEME_ID",updatable = true, insertable = true)
	@NotFound(action=NotFoundAction.IGNORE)
	public CmsVoteTheme getCmsVoteTheme() {
		return cmsVoteTheme;
	}

	public void setCmsVoteTheme(CmsVoteTheme cmsVoteTheme) {
		this.cmsVoteTheme = cmsVoteTheme;
	}
	
}
