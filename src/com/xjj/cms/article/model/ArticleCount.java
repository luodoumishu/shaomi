package com.xjj.cms.article.model;

import com.xjj._extensions.roleUserPer.model.Zorganize;

public class ArticleCount implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Zorganize org;
	private Integer num;

	public ArticleCount() {
	}

	public ArticleCount(Zorganize org, Integer num) {
		this.org = org;
		this.num = num;
	}

	public Zorganize getOrg() {
		return org;
	}

	public void setOrg(Zorganize org) {
		this.org = org;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
