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
@Table(name = "CMS_VOTE_IP")
public class CmsVoteIp extends CMSBaseModel implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String vote_ip;//投票IP
	private String vote_mac;//投票MAC
	private Date vote_time;//投票时间
	private Date next_vote_time;//下次投票时间
	
	private CmsVoteTheme cmsVoteTheme;//对应主题

	public CmsVoteIp() {
		super();
	}

	public CmsVoteIp(String vote_ip, String vote_mac, Date vote_time,
			Date next_vote_time, CmsVoteTheme cmsVoteTheme) {
		super();
		this.vote_ip = vote_ip;
		this.vote_mac = vote_mac;
		this.vote_time = vote_time;
		this.next_vote_time = next_vote_time;
		this.cmsVoteTheme = cmsVoteTheme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((cmsVoteTheme == null) ? 0 : cmsVoteTheme.hashCode());
		result = prime * result
				+ ((next_vote_time == null) ? 0 : next_vote_time.hashCode());
		result = prime * result + ((vote_ip == null) ? 0 : vote_ip.hashCode());
		result = prime * result
				+ ((vote_mac == null) ? 0 : vote_mac.hashCode());
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
		CmsVoteIp other = (CmsVoteIp) obj;
		if (cmsVoteTheme == null) {
			if (other.cmsVoteTheme != null)
				return false;
		} else if (!cmsVoteTheme.equals(other.cmsVoteTheme))
			return false;
		if (next_vote_time == null) {
			if (other.next_vote_time != null)
				return false;
		} else if (!next_vote_time.equals(other.next_vote_time))
		if (vote_ip == null) {
			if (other.vote_ip != null)
				return false;
		} else if (!vote_ip.equals(other.vote_ip))
			return false;
		if (vote_mac == null) {
			if (other.vote_mac != null)
				return false;
		} else if (!vote_mac.equals(other.vote_mac))
			return false;
		if (vote_time == null) {
			if (other.vote_time != null)
				return false;
		} else if (!vote_time.equals(other.vote_time))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CmsVoteIp [vote_ip=" + vote_ip + ", vote_mac=" + vote_mac
				+ ", vote_time=" + vote_time + ", next_vote_time="
				+ next_vote_time + ", cmsVoteTheme="
				+ cmsVoteTheme + "]";
	}
	@Column(name = "VOTE_IP", length = 516)
	public String getVote_ip() {
		return vote_ip;
	}

	public void setVote_ip(String vote_ip) {
		this.vote_ip = vote_ip;
	}
	@Column(name = "VOTE_MAC", length = 516)
	public String getVote_mac() {
		return vote_mac;
	}

	public void setVote_mac(String vote_mac) {
		this.vote_mac = vote_mac;
	}
	@Column(name = "VOTE_TIME")
	public Date getVote_time() {
		return vote_time;
	}

	public void setVote_time(Date vote_time) {
		this.vote_time = vote_time;
	}
	@Column(name = "NEXT_VOTE_TIME")
	public Date getNext_vote_time() {
		return next_vote_time;
	}

	public void setNext_vote_time(Date next_vote_time) {
		this.next_vote_time = next_vote_time;
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
