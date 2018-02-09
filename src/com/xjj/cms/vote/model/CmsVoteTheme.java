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
@Table(name = "CMS_VOTE_THEME")
public class CmsVoteTheme extends CMSBaseModel implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String themeName;//投票主题名称
	private String showCode;//显示代码，用于首页显示
	private String isValid;//是否有效（0是，1 否）
	private Integer sort;//排序
	private Integer same_ip_delay;//同ip延迟（小时）
	private String is_same_ip_vote;//是否同ip只能投票一次（0是，1 否）
	private String is_same_mac_vote;//是否同mac只能投票一次（0是，1 否）
	private String is_need_person;//是否要求输入投票人（0是，1 否）
	private Integer voteTotal;//总投票数
	private String remark;//备注
	
	private List<CmsVoteItem> cmsVoteItems;//投票项集合 
	private List<CmsVoteIp> cmsVoteIps; //投票IP集合
	private List<CmsVotePerson> cmsVotePersons; //投票人集合
	
	// Constructors

		/** default constructor */
		public CmsVoteTheme() {
		}

		/** full constructor */
		public CmsVoteTheme(String themeName, String showCode, String isValid,
				Integer sort, Integer same_ip_delay, String is_same_ip_vote,
				String is_same_mac_vote, String is_need_person,
				Integer voteTotal, String remark) {
			super();
			this.themeName = themeName;
			this.showCode = showCode;
			this.isValid = isValid;
			this.sort = sort;
			this.same_ip_delay = same_ip_delay;
			this.is_same_ip_vote = is_same_ip_vote;
			this.is_same_mac_vote = is_same_mac_vote;
			this.is_need_person = is_need_person;
			this.voteTotal = voteTotal;
			this.remark = remark;
		}

		@Column(name = "THEME_NAME", length = 516)
		public String getThemeName() {
			return themeName;
		}

		public void setThemeName(String themeName) {
			this.themeName = themeName;
		}
		@Column(name = "SHOW_CODE", length = 516)
		public String getShowCode() {
			return showCode;
		}

		public void setShowCode(String showCode) {
			this.showCode = showCode;
		}
		@Column(name = "IS_VALID", length = 32)
		public String getIsValid() {
			return isValid;
		}

		public void setIsValid(String isValid) {
			this.isValid = isValid;
		}
		@Column(name = "SORT")
		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}
		@Column(name = "SAME_IP_DELAY")
		public Integer getSame_ip_delay() {
			return same_ip_delay;
		}

		public void setSame_ip_delay(Integer same_ip_delay) {
			this.same_ip_delay = same_ip_delay;
		}
		@Column(name = "IS_SAME_IP_VOTE", length = 32)
		public String getIs_same_ip_vote() {
			return is_same_ip_vote;
		}

		public void setIs_same_ip_vote(String is_same_ip_vote) {
			this.is_same_ip_vote = is_same_ip_vote;
		}
		@Column(name = "IS_SAME_MAC_VOTE", length = 32)
		public String getIs_same_mac_vote() {
			return is_same_mac_vote;
		}

		public void setIs_same_mac_vote(String is_same_mac_vote) {
			this.is_same_mac_vote = is_same_mac_vote;
		}
		@Column(name = "IS_NEED_PERSON", length = 32)
		public String getIs_need_person() {
			return is_need_person;
		}

		public void setIs_need_person(String is_need_person) {
			this.is_need_person = is_need_person;
		}
		@Column(name = "VOTE_TOTAL")
		public Integer getVoteTotal() {
			return voteTotal;
		}

		public void setVoteTotal(Integer voteTotal) {
			this.voteTotal = voteTotal;
		}
		@Column(name = "REMARK", length = 1024)
		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		
		@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)  
	    @JoinColumn(name="THEME_ID")
		@OrderBy("sort asc")	
		public List<CmsVoteItem> getCmsVoteItems() {
			return cmsVoteItems;
		}

		public void setCmsVoteItems(List<CmsVoteItem> cmsVoteItems) {
			this.cmsVoteItems = cmsVoteItems;
		}
		@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)  
	    @JoinColumn(name="THEME_ID")
		public List<CmsVoteIp> getCmsVoteIps() {
			return cmsVoteIps;
		}

		public void setCmsVoteIps(List<CmsVoteIp> cmsVoteIps) {
			this.cmsVoteIps = cmsVoteIps;
		}
		@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)  
	    @JoinColumn(name="THEME_ID")
		public List<CmsVotePerson> getCmsVotePersons() {
			return cmsVotePersons;
		}

		public void setCmsVotePersons(List<CmsVotePerson> cmsVotePersons) {
			this.cmsVotePersons = cmsVotePersons;
		}

		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result
					+ ((isValid == null) ? 0 : isValid.hashCode());
			result = prime
					* result
					+ ((is_need_person == null) ? 0 : is_need_person.hashCode());
			result = prime
					* result
					+ ((is_same_ip_vote == null) ? 0 : is_same_ip_vote
							.hashCode());
			result = prime
					* result
					+ ((is_same_mac_vote == null) ? 0 : is_same_mac_vote
							.hashCode());
			result = prime * result
					+ ((remark == null) ? 0 : remark.hashCode());
			result = prime * result
					+ ((same_ip_delay == null) ? 0 : same_ip_delay.hashCode());
			result = prime * result
					+ ((showCode == null) ? 0 : showCode.hashCode());
			result = prime * result + ((sort == null) ? 0 : sort.hashCode());
			result = prime * result
					+ ((themeName == null) ? 0 : themeName.hashCode());
			result = prime * result
					+ ((voteTotal == null) ? 0 : voteTotal.hashCode());
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
			CmsVoteTheme other = (CmsVoteTheme) obj;
			if (isValid == null) {
				if (other.isValid != null)
					return false;
			} else if (!isValid.equals(other.isValid))
				return false;
			if (is_need_person == null) {
				if (other.is_need_person != null)
					return false;
			} else if (!is_need_person.equals(other.is_need_person))
				return false;
			if (is_same_ip_vote == null) {
				if (other.is_same_ip_vote != null)
					return false;
			} else if (!is_same_ip_vote.equals(other.is_same_ip_vote))
				return false;
			if (is_same_mac_vote == null) {
				if (other.is_same_mac_vote != null)
					return false;
			} else if (!is_same_mac_vote.equals(other.is_same_mac_vote))
				return false;
			if (remark == null) {
				if (other.remark != null)
					return false;
			} else if (!remark.equals(other.remark))
				return false;
			if (same_ip_delay == null) {
				if (other.same_ip_delay != null)
					return false;
			} else if (!same_ip_delay.equals(other.same_ip_delay))
				return false;
			if (showCode == null) {
				if (other.showCode != null)
					return false;
			} else if (!showCode.equals(other.showCode))
				return false;
			if (sort == null) {
				if (other.sort != null)
					return false;
			} else if (!sort.equals(other.sort))
				return false;
			if (themeName == null) {
				if (other.themeName != null)
					return false;
			} else if (!themeName.equals(other.themeName))
				return false;
			if (voteTotal == null) {
				if (other.voteTotal != null)
					return false;
			} else if (!voteTotal.equals(other.voteTotal))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "CmsVoteTheme [themeName=" + themeName + ", showCode="
					+ showCode + ", isValid=" + isValid + ", sort=" + sort
					+ ", same_ip_delay=" + same_ip_delay + ", is_same_ip_vote="
					+ is_same_ip_vote + ", is_same_mac_vote="
					+ is_same_mac_vote + ", is_need_person=" + is_need_person
					+ ", voteTotal=" + voteTotal + ", remark=" + remark + "]";
		}
		
}
