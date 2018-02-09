package com.xjj.cms.menu.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xjj.cms.base.model.CMSBaseModel;

/**
 * CmsMenu.entity 栏目实体
 * <p>
 * 
 * @author yeyunfeng 2014-8-22 上午11:25:07
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-8-22
 * @modify by reason:{方法名}:{原因}
 */
@Entity
@Table(name = "CMS_MENU")
public class CmsMenu extends CMSBaseModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 栏目名称
	 */
	private String menuName;
	/**
	 * 排序号
	 */
	private Integer sortNo;
	/**
	 * 父栏目ID
	 */
	private String parentMenuId;
	/**
	 * 父栏目名称
	 */
	private String parentMenuName;
	/**
	 * 是否有效 0-有效 1-无效
	 */
	private Integer isValid;
	/**
	 * 打开方式 0-当前窗口打开 1-新窗口打开
	 */
	private Integer openMode;
	/**
	 * 显示方式 0-文章列表 1-文章内容
	 */
	private Integer showMode;
	/**
	 * 文章类型 0 -文章类 1-视频类
	 */
	private Integer articleContentType;
	/**
	 * 栏目类型 0-真实栏目 1-跳转栏目 2-虚拟栏目
	 */
	private Integer menuType;
	/**
	 * 栏目模板ID
	 */
	private String memuTemplateId;
	/**
	 * 文章模板ID
	 */
	private Integer articleTemplate;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 跳转链接地址
	 */
	private String skipUrl;
	
	/**
	 * 栏目配图
	 */
	private String menu_img_url;
	
	private Boolean nocheck;
	
	/**
	 * 子栏目集合
	 */
	private List<CmsMenu> childMenuList =  new ArrayList<CmsMenu>();;
	
	@Transient
	public List<CmsMenu> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<CmsMenu> childMenuList) {
		this.childMenuList = childMenuList;
	}
	
	// Constructors
	/** default constructor */
	public CmsMenu() {
	}

	/** full constructor */
	public CmsMenu(String menuName, Integer sortNo, String parentMenuId,
			String parentMenuName, Integer isValid, Integer openMode,
			Integer showMode, Integer articleContentType, Integer menuType,
			String memuTemplateId, Integer articleTemplate, String remark,
			String skipUrl,String menu_img_url) {
		super();
		this.menuName = menuName;
		this.sortNo = sortNo;
		this.parentMenuId = parentMenuId;
		this.parentMenuName = parentMenuName;
		this.isValid = isValid;
		this.openMode = openMode;
		this.showMode = showMode;
		this.articleContentType = articleContentType;
		this.menuType = menuType;
		this.memuTemplateId = memuTemplateId;
		this.articleTemplate = articleTemplate;
		this.remark = remark;
		this.skipUrl = skipUrl;
		this.menu_img_url = menu_img_url;
	}

	// Property accessors

	@Column(name = "MENU_NAME", length = 128)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "SORTNO")
	public Integer getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	@Column(name = "PARENT_MENU_ID", length = 128)
	public String getParentMenuId() {
		return this.parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	@Column(name = "PARENT_MENU_NAME", length = 128)
	public String getParentMenuName() {
		return this.parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	@Column(name = "IS_VALID")
	public Integer getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	@Column(name = "OPEN_MODE")
	public Integer getOpenMode() {
		return this.openMode;
	}

	public void setOpenMode(Integer openMode) {
		this.openMode = openMode;
	}

	@Column(name = "SHOW_MODE")
	public Integer getShowMode() {
		return this.showMode;
	}

	public void setShowMode(Integer showMode) {
		this.showMode = showMode;
	}

	@Column(name = "ARTICLE_CONTENT_TYPE")
	public Integer getArticleContentType() {
		return this.articleContentType;
	}

	public void setArticleContentType(Integer articleContentType) {
		this.articleContentType = articleContentType;
	}

	@Column(name = "MENU_TYPE")
	public Integer getMenuType() {
		return this.menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	@Column(name = "MEMU_TEMPLATE_ID", length = 128)
	public String getMemuTemplateId() {
		return this.memuTemplateId;
	}

	public void setMemuTemplateId(String memuTemplateId) {
		this.memuTemplateId = memuTemplateId;
	}

	@Column(name = "ARTICLE_TEMPLATE")
	public Integer getArticleTemplate() {
		return this.articleTemplate;
	}

	public void setArticleTemplate(Integer articleTemplate) {
		this.articleTemplate = articleTemplate;
	}

	@Column(name = "REMARK", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "SKIP_URL", length = 128)
	public String getSkipUrl() {
		return this.skipUrl;
	}

	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}
	
	@Column(name = "MENU_IMG_URL", length = 516)
	public String getMenu_img_url() {
		return menu_img_url;
	}
	
	public void setMenu_img_url(String menu_img_url) {
		this.menu_img_url = menu_img_url;
	}

	@Transient
	public Boolean getNocheck() {
		return nocheck;
	}
	
	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}

	@Override
	public String toString() {
		return "CmsMenu [menuName=" + menuName + ", sortNo=" + sortNo
				+ ", parentMenuId=" + parentMenuId + ", parentMenuName="
				+ parentMenuName + ", isValid=" + isValid + ", openMode="
				+ openMode + ", showMode=" + showMode + ", articleContentType="
				+ articleContentType + ", menuType=" + menuType
				+ ", memuTemplateId=" + memuTemplateId + ", articleTemplate="
				+ articleTemplate + ", remark=" + remark + ", skipUrl="
				+ skipUrl + ", menu_img_url=" + menu_img_url + ", nocheck="
				+ nocheck + ", childMenuList=" + childMenuList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((articleContentType == null) ? 0 : articleContentType
						.hashCode());
		result = prime * result
				+ ((articleTemplate == null) ? 0 : articleTemplate.hashCode());
		result = prime * result
				+ ((childMenuList == null) ? 0 : childMenuList.hashCode());
		result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
		result = prime * result
				+ ((memuTemplateId == null) ? 0 : memuTemplateId.hashCode());
		result = prime * result
				+ ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result
				+ ((menuType == null) ? 0 : menuType.hashCode());
		result = prime * result
				+ ((menu_img_url == null) ? 0 : menu_img_url.hashCode());
		result = prime * result + ((nocheck == null) ? 0 : nocheck.hashCode());
		result = prime * result
				+ ((openMode == null) ? 0 : openMode.hashCode());
		result = prime * result
				+ ((parentMenuId == null) ? 0 : parentMenuId.hashCode());
		result = prime * result
				+ ((parentMenuName == null) ? 0 : parentMenuName.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((showMode == null) ? 0 : showMode.hashCode());
		result = prime * result + ((skipUrl == null) ? 0 : skipUrl.hashCode());
		result = prime * result + ((sortNo == null) ? 0 : sortNo.hashCode());
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
		CmsMenu other = (CmsMenu) obj;
		if (articleContentType == null) {
			if (other.articleContentType != null) {
				return false;
			}
		} else if (!articleContentType.equals(other.articleContentType)) {
			return false;
		}
		if (articleTemplate == null) {
			if (other.articleTemplate != null) {
				return false;
			}
		} else if (!articleTemplate.equals(other.articleTemplate)) {
			return false;
		}
		if (childMenuList == null) {
			if (other.childMenuList != null) {
				return false;
			}
		} else if (!childMenuList.equals(other.childMenuList)) {
			return false;
		}
		if (isValid == null) {
			if (other.isValid != null) {
				return false;
			}
		} else if (!isValid.equals(other.isValid)) {
			return false;
		}
		if (memuTemplateId == null) {
			if (other.memuTemplateId != null) {
				return false;
			}
		} else if (!memuTemplateId.equals(other.memuTemplateId)) {
			return false;
		}
		if (menuName == null) {
			if (other.menuName != null) {
				return false;
			}
		} else if (!menuName.equals(other.menuName)) {
			return false;
		}
		if (menuType == null) {
			if (other.menuType != null) {
				return false;
			}
		} else if (!menuType.equals(other.menuType)) {
			return false;
		}
		if (menu_img_url == null) {
			if (other.menu_img_url != null) {
				return false;
			}
		} else if (!menu_img_url.equals(other.menu_img_url)) {
			return false;
		}
		if (nocheck == null) {
			if (other.nocheck != null) {
				return false;
			}
		} else if (!nocheck.equals(other.nocheck)) {
			return false;
		}
		if (openMode == null) {
			if (other.openMode != null) {
				return false;
			}
		} else if (!openMode.equals(other.openMode)) {
			return false;
		}
		if (parentMenuId == null) {
			if (other.parentMenuId != null) {
				return false;
			}
		} else if (!parentMenuId.equals(other.parentMenuId)) {
			return false;
		}
		if (parentMenuName == null) {
			if (other.parentMenuName != null) {
				return false;
			}
		} else if (!parentMenuName.equals(other.parentMenuName)) {
			return false;
		}
		if (remark == null) {
			if (other.remark != null) {
				return false;
			}
		} else if (!remark.equals(other.remark)) {
			return false;
		}
		if (showMode == null) {
			if (other.showMode != null) {
				return false;
			}
		} else if (!showMode.equals(other.showMode)) {
			return false;
		}
		if (skipUrl == null) {
			if (other.skipUrl != null) {
				return false;
			}
		} else if (!skipUrl.equals(other.skipUrl)) {
			return false;
		}
		if (sortNo == null) {
			if (other.sortNo != null) {
				return false;
			}
		} else if (!sortNo.equals(other.sortNo)) {
			return false;
		}
		return true;
	}
	
}