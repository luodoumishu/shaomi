package com.xjj._extensions.roleUserPer.model;

import java.util.List;

public class MenuTree {

	String menuName;// 菜单名

	String url;// 菜单链接

	String img;// 图片

	List<MenuTree> child = null;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<MenuTree> getChild() {
		return child;
	}

	public void setChild(List<MenuTree> child) {
		this.child = child;
	}
}
