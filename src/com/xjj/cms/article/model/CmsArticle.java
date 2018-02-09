package com.xjj.cms.article.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xjj.cms.base.model.CMSBaseModel;
import com.xjj.cms.base.model.CmsAffix;


/**
 * 文章实体 
 * <p>
 * @author yeyunfeng 2014-9-2 上午9:20:46 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
@Entity
@Table(name = "CMS_ARTICLE")
public class CmsArticle extends CMSBaseModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 标题颜色
	 */
	private String titleColor;
	/**
	 * 标题字体大小
	 */
	private String titleSize;
	/**
	 * 栏目id
	 */
	private String menuId;
	/**
	 * 栏目名称
	 */
	private String menuName;
	/**
	 * 文章作者
	 */
	private String author;
	/**
	 * 文章来源
	 */
	private String source;
	/**
	 * 阅读次数
	 */
	private String readCount;
	/**
	 * 关键字
	 */
	private String keyWord;
	/**
	 * 文章正文
	 */
	private String content;
	/**
	 * 代表图片地址
	 */
	private String behalf_imageUrl;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 部门ID
	 */
	private String orgId;
	/**
	 * 部门名称
	 */
	private String orgName;
	/**
	 * 发文时间
	 */
	private Date publicTime;
	/**
	 * 转向链接
	 */
	private String linkUrl;
	
	/**
	 * 是否评论
	 * 0不评论    1评论
	 */
	private String ifComment;
	
	/**
	 * 审核节点（0-未提交审核，1-审核中，2-审核通过，3-审核不通过，4-退回）
	 */
	private Integer check_log;
	
	/**
	 * 代表性图片宽度
	 */
	private String behalf_image_width;
	/**
	 * 代表性图片高度
	 */
	private String behalf_image_height;
	
	private List<CmsAffix> cmsAffixs = new ArrayList<CmsAffix>();
	
	// Constructors

	/** default constructor */
	public CmsArticle() {
	}
	
	/** full constructor */
	public CmsArticle(String title, String titleColor, String titleSize,
			String menuId, String menuName, String author, String source,
			String readCount, String keyWord, String content,
			String behalf_imageUrl, String summary, String orgId,
			String orgName, Date publicTime, String linkUrl, String ifComment,
			Integer check_log, List<CmsAffix> cmsAffixs,String behalf_image_width,String behalf_image_height) {
		this.title = title;
		this.titleColor = titleColor;
		this.titleSize = titleSize;
		this.menuId = menuId;
		this.menuName = menuName;
		this.author = author;
		this.source = source;
		this.readCount = readCount;
		this.keyWord = keyWord;
		this.content = content;
		this.behalf_imageUrl = behalf_imageUrl;
		this.summary = summary;
		this.orgId = orgId;
		this.orgName = orgName;
		this.publicTime = publicTime;
		this.linkUrl = linkUrl;
		this.ifComment = ifComment;
		this.check_log = check_log;
		this.cmsAffixs = cmsAffixs;
		this.behalf_image_width = behalf_image_width;
		this.behalf_image_height = behalf_image_height;
	}
	
	
	@Override
	public String toString() {
		return "CmsArticle [title=" + title + ", titleColor=" + titleColor
				+ ", titleSize=" + titleSize + ", menuId=" + menuId
				+ ", menuName=" + menuName + ", author=" + author + ", source="
				+ source + ", readCount=" + readCount + ", keyWord=" + keyWord
				+ ", content=" + content + ", behalf_imageUrl="
				+ behalf_imageUrl + ", summary=" + summary + ", orgId=" + orgId
				+ ", orgName=" + orgName + ", publicTime=" + publicTime
				+ ", cmsAffixs=" + cmsAffixs + "]";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result
				+ ((behalf_imageUrl == null) ? 0 : behalf_imageUrl.hashCode());
		result = prime * result
				+ ((cmsAffixs == null) ? 0 : cmsAffixs.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((keyWord == null) ? 0 : keyWord.hashCode());
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		result = prime * result
				+ ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result
				+ ((publicTime == null) ? 0 : publicTime.hashCode());
		result = prime * result
				+ ((readCount == null) ? 0 : readCount.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((titleColor == null) ? 0 : titleColor.hashCode());
		result = prime * result
				+ ((titleSize == null) ? 0 : titleSize.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CmsArticle other = (CmsArticle) obj;
		if (author == null) {
			if (other.author != null) {
				return false;
			}
		} else if (!author.equals(other.author)) {
			return false;
		}
		if (behalf_imageUrl == null) {
			if (other.behalf_imageUrl != null) {
				return false;
			}
		} else if (!behalf_imageUrl.equals(other.behalf_imageUrl)) {
			return false;
		}
		if (cmsAffixs == null) {
			if (other.cmsAffixs != null) {
				return false;
			}
		} else if (!cmsAffixs.equals(other.cmsAffixs)) {
			return false;
		}
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (keyWord == null) {
			if (other.keyWord != null) {
				return false;
			}
		} else if (!keyWord.equals(other.keyWord)) {
			return false;
		}
		if (menuId == null) {
			if (other.menuId != null) {
				return false;
			}
		} else if (!menuId.equals(other.menuId)) {
			return false;
		}
		if (menuName == null) {
			if (other.menuName != null) {
				return false;
			}
		} else if (!menuName.equals(other.menuName)) {
			return false;
		}
		if (orgId == null) {
			if (other.orgId != null) {
				return false;
			}
		} else if (!orgId.equals(other.orgId)) {
			return false;
		}
		if (orgName == null) {
			if (other.orgName != null) {
				return false;
			}
		} else if (!orgName.equals(other.orgName)) {
			return false;
		}
		if (publicTime == null) {
			if (other.publicTime != null) {
				return false;
			}
		} else if (!publicTime.equals(other.publicTime)) {
			return false;
		}
		if (readCount == null) {
			if (other.readCount != null) {
				return false;
			}
		} else if (!readCount.equals(other.readCount)) {
			return false;
		}
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (summary == null) {
			if (other.summary != null) {
				return false;
			}
		} else if (!summary.equals(other.summary)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (titleColor == null) {
			if (other.titleColor != null) {
				return false;
			}
		} else if (!titleColor.equals(other.titleColor)) {
			return false;
		}
		if (titleSize == null) {
			if (other.titleSize != null) {
				return false;
			}
		} else if (!titleSize.equals(other.titleSize)) {
			return false;
		}
		return true;
	}
	// Property accessors
	@Column(name = "TITLE", length = 1024)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "TITLE_COLOR", length = 64)
	public String getTitleColor() {
		return this.titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	@Column(name = "TITLE_SIZE", length = 64)
	public String getTitleSize() {
		return this.titleSize;
	}

	public void setTitleSize(String titleSize) {
		this.titleSize = titleSize;
	}

	@Column(name = "MENU_ID", length = 128)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "MENU_NAME", length = 128)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "AUTHOR", length = 128)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "SOURCE", length = 128)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "READ_COUNT", length = 128)
	public String getReadCount() {
		return this.readCount;
	}

	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	@Column(name = "KEY_WORD", length = 128)
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "BEHALF_IMAGE_URL", length = 128)
	public String getBehalf_imageUrl() {
		return behalf_imageUrl;
	}
	
	public void setBehalf_imageUrl(String behalf_imageUrl) {
		this.behalf_imageUrl = behalf_imageUrl;
	}

	@Column(name = "SUMMARY", length = 1024)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Column(name = "ORG_ID", length = 128)
	public String getOrgId() {
		return orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Column(name = "ORG_NAME", length = 128)
	public String getOrgName() {
		return orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Column(name = "PUBLICE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	public Date getPublicTime() {
		return publicTime;
	}
	
	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}
	@Column(name = "LINK_URL", length = 128)
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	@Column(name = "IF_COMMENT")
	public String getIfComment() {
		return ifComment;
	}

	public void setIfComment(String ifComment) {
		this.ifComment = ifComment;
	}

	@Column(name = "CHECK_LOG")
	public Integer getCheck_log() {
		return check_log;
	}

	public void setCheck_log(Integer check_log) {
		this.check_log = check_log;
	}
	
	@Column(name = "BEHALF_IMAGE_WIDTH")
	public String getBehalf_image_width() {
		return behalf_image_width;
	}

	public void setBehalf_image_width(String behalf_image_width) {
		this.behalf_image_width = behalf_image_width;
	}

	@Column(name = "BEHALF_IMAGE_HEIGHT")
	public String getBehalf_image_height() {
		return behalf_image_height;
	}

	public void setBehalf_image_height(String behalf_image_height) {
		this.behalf_image_height = behalf_image_height;
	}

	@Transient
	public List<CmsAffix> getCmsAffixs() {
		return cmsAffixs;
	}
	
	public void setCmsAffixs(List<CmsAffix> cmsAffixs) {
		this.cmsAffixs = cmsAffixs;
	}
}