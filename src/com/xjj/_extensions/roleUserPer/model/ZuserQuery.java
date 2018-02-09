package com.xjj._extensions.roleUserPer.model;

/**
 * 主要用于查询条件的扩展（不建议在实体类里直接扩展字段，会破坏json和xml输出内容的结构）
 */
public class ZuserQuery extends Zuser {

	private String showName;

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
	
}
