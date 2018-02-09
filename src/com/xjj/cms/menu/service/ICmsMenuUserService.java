package com.xjj.cms.menu.service;

import java.util.List;
import java.util.Map;

import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj.cms.menu.model.CmsMenuUser;
import com.xjj.cms.menu.model.CmsOrgUser;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsMenuUserService extends IBaseService<CmsMenuUser, String>{

	Page<CmsMenuUser> query(Map<String, Object> filtersMap) throws Exception;

	List<Zuser> getByAll();

	boolean exitUserInMenuUser(String id, String menuId);

	List<CmsOrgUser> getByParentId(String id)throws Exception;

	CmsMenuUser getMenuUser(String userId, String menuId);

	CmsMenuUser getMenuUserById(String id);

}
