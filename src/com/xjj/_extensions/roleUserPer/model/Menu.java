package com.xjj._extensions.roleUserPer.model;


public class Menu {
	String menu;// 一级菜单.,二级菜单..,三级菜单...
	String menuName;// 菜单名
	String mark;// 系统标识
	String mark_up;// 上级标识
	String url;// 菜单链接
	String codeStr;// 权限代码串:中间用逗号,隔开
	Integer pri;//排序
	String img;//图片
	String module;//模块(应用)标识
	// String leaf;//是否叶子节点:1,是

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getPri() {
		return pri;
	}

	public void setPri(Integer pri) {
		this.pri = pri;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getMark_up() {
		return mark_up;
	}

	public void setMark_up(String mark_up) {
		this.mark_up = mark_up;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCodeStr() {
		return codeStr;
	}

	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}

}
