package com.xjj.cms.article.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xjj.framework.core.model.BaseModel;



@Entity
@Table(name = "CMS_ARTICLE_COMMENT")
public class CmsArticleComment extends BaseModel implements java.io.Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 文章ID
	 */
	private String article_id;
	/**
	 * 评论人姓名
	 */
	private String comment_name;
	/**
	 * 评论人联系方式
	 */
	private String comment_phone;
	/**
	 * 评论人email
	 */
	private String comment_email;
	/**
	 * 评论内容
	 */
	private String comment_content;
	/**
	 * 评论时间
	 */
	private Date comment_time;
	
	
	public CmsArticleComment() {
	
	}


	public CmsArticleComment(String article_id, String comment_name,
			String comment_phone, String comment_email, String comment_content,
			Date comment_time) {
		this.article_id = article_id;
		this.comment_name = comment_name;
		this.comment_phone = comment_phone;
		this.comment_email = comment_email;
		this.comment_content = comment_content;
		this.comment_time = comment_time;
	}


	@Column(name = "ARTICLE_ID")
	public String getArticle_id() {
		return article_id;
	}


	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	@Column(name = "COMMENT_NAME")
	public String getComment_name() {
		return comment_name;
	}


	public void setComment_name(String comment_name) {
		this.comment_name = comment_name;
	}

	@Column(name = "COMMENT_PHONE")
	public String getComment_phone() {
		return comment_phone;
	}


	public void setComment_phone(String comment_phone) {
		this.comment_phone = comment_phone;
	}

	@Column(name = "COMMENT_EMAIL")
	public String getComment_email() {
		return comment_email;
	}


	public void setComment_email(String comment_email) {
		this.comment_email = comment_email;
	}

	@Column(name = "COMMENT_CONTENT")
	public String getComment_content() {
		return comment_content;
	}


	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	@Column(name = "COMMENT_TIME")
	public Date getComment_time() {
		return comment_time;
	}


	public void setComment_time(Date comment_time) {
		this.comment_time = comment_time;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((article_id == null) ? 0 : article_id.hashCode());
		result = prime * result
				+ ((comment_content == null) ? 0 : comment_content.hashCode());
		result = prime * result
				+ ((comment_email == null) ? 0 : comment_email.hashCode());
		result = prime * result
				+ ((comment_name == null) ? 0 : comment_name.hashCode());
		result = prime * result
				+ ((comment_phone == null) ? 0 : comment_phone.hashCode());
		result = prime * result
				+ ((comment_time == null) ? 0 : comment_time.hashCode());
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
		CmsArticleComment other = (CmsArticleComment) obj;
		if (article_id == null) {
			if (other.article_id != null)
				return false;
		} else if (!article_id.equals(other.article_id))
			return false;
		if (comment_content == null) {
			if (other.comment_content != null)
				return false;
		} else if (!comment_content.equals(other.comment_content))
			return false;
		if (comment_email == null) {
			if (other.comment_email != null)
				return false;
		} else if (!comment_email.equals(other.comment_email))
			return false;
		if (comment_name == null) {
			if (other.comment_name != null)
				return false;
		} else if (!comment_name.equals(other.comment_name))
			return false;
		if (comment_phone == null) {
			if (other.comment_phone != null)
				return false;
		} else if (!comment_phone.equals(other.comment_phone))
			return false;
		if (comment_time == null) {
			if (other.comment_time != null)
				return false;
		} else if (!comment_time.equals(other.comment_time))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "CmsArticleComment [article_id=" + article_id
				+ ", comment_name=" + comment_name + ", comment_phone="
				+ comment_phone + ", comment_email=" + comment_email
				+ ", comment_content=" + comment_content + ", comment_time="
				+ comment_time + "]";
	}	
	
}