package com.xjj.cms.menu.dao;

import java.util.List;
import java.util.Map;

import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj.cms.menu.model.CmsMenuUser;
import com.xjj.cms.menu.model.CmsOrgUser;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsMenuUserDao extends IBaseDao<CmsMenuUser, String> {

	List<CmsMenuUser> query(int skip, int pageSize,
			Map<String, Object> queryMap, String string);

	int total(Map<String, Object> queryMap);

	List<Zuser> getByAll();

	boolean exitUserInMenuUser(String id, String menuId);

	List<CmsOrgUser> getByParentId(String id);

	boolean findChildOrg(String parentId);

	List<CmsOrgUser> getByParentOrg(String parentId);

	boolean isAddUser(String userId, String menuId);

	String getMenuNameByMenuId(String menuId);

	String getUserNameByUserId(String userId);

	CmsMenuUser getMenuUserById(String id);

}
