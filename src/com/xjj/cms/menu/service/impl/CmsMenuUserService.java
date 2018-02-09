package com.xjj.cms.menu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.menu.dao.ICmsMenuOrgDao;
import com.xjj.cms.menu.dao.ICmsMenuUserDao;
import com.xjj.cms.menu.model.CmsMenuUser;
import com.xjj.cms.menu.model.CmsOrgUser;
import com.xjj.cms.menu.service.ICmsMenuUserService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;
import com.xjj.framework.core.web.filter.WebContext;

@Service("cmsMenuUserService")
public class CmsMenuUserService extends BaseService<CmsMenuUser, String> implements ICmsMenuUserService{
	@Autowired
	@Qualifier("cmsMenuUserDao")
	private ICmsMenuUserDao cmsMenuUserDao;

	
	@Autowired
	@Qualifier("cmsMenuOrgDao")
	private ICmsMenuOrgDao cmsMenuOrgDao;

	
	@Autowired
	@Qualifier("cmsMenuUserDao")
	@Override
	public void setBaseDao(IBaseDao<CmsMenuUser, String> baseDao) {
		this.baseDao = cmsMenuUserDao;
	}
	@Override
	public Page<CmsMenuUser> query(Map<String, Object> filtersMap) throws Exception {
		// 开始查询下标
		int skip = filtersMap.get("skip") == null ? 1 : (Integer) filtersMap
				.get("skip");
		// 每页显示条数
		int pageSize = filtersMap.get("pageSize") == null ? 20
				: (Integer) filtersMap.get("pageSize");

		// 获取查询条件
		@SuppressWarnings("unchecked")
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap
				.get("queryObj");

		List<CmsMenuUser> items = cmsMenuUserDao.query(skip, pageSize, queryMap,
				" order by id");
		// 获取总记录数
		int total = cmsMenuUserDao.total(queryMap);
		// 构造返回对象page
		Page<CmsMenuUser> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}
	@Override
	public List<Zuser> getByAll() {
		return cmsMenuUserDao.getByAll();
	}
	@Override
	public boolean exitUserInMenuUser(String id, String menuId) {
		return cmsMenuUserDao.exitUserInMenuUser(id,menuId);
	}
	@Override
	public List<CmsOrgUser> getByParentId(String parentId) throws Exception{
		
		if (CmsCC.ENABLED_MENU_FJSQ && "0".equals(parentId)) {//启用分级授权时，要先获取市县跟部门
			String orgId = WebContext.getInstance().getHandle().getOrgID();
			parentId = cmsMenuOrgDao.getByLoopOrgId(orgId);
		}
			//判断传来的id是否还有子部门
//			if(cmsMenuUserDao.findChildOrg(parentId)){
//				List<CmsOrgUser> list = new ArrayList<CmsOrgUser>();
//				List<CmsOrgUser> list4user= new ArrayList<CmsOrgUser>();
//				List<CmsOrgUser> listorg = new ArrayList<CmsOrgUser>();
//				list4user = cmsMenuUserDao.getByParentId(parentId);
//				listorg = cmsMenuUserDao.getByParentOrg(parentId);
//				if (null != list4user && !list4user.isEmpty()) {
//					list.addAll(list4user);
//				}
//				if (null !=listorg && !listorg.isEmpty()) {
//					list.addAll(listorg);
//				}
//				//有子部门，返回的是该部门下的子部门
//				return list;
//			}else{
				//没有子部门，返回的是改部门下所有的用户
				return cmsMenuUserDao.getByParentId(parentId);
//			}
		
	}
	@Override
	public CmsMenuUser getMenuUser(String userId, String menuId) {
		CmsMenuUser menuUser = new CmsMenuUser();
		if(!cmsMenuUserDao.isAddUser(userId,menuId)){
			menuUser.setMenuId(menuId);
			String menuName = cmsMenuUserDao.getMenuNameByMenuId(menuId);
			menuUser.setMenuName(menuName);
			menuUser.setUserId(userId);
			String userName = cmsMenuUserDao.getUserNameByUserId(userId);
			menuUser.setUserName(userName);
			return menuUser;
		}
		return null;
	}
	@Override
	public CmsMenuUser getMenuUserById(String id) {
		return cmsMenuUserDao.getMenuUserById(id);
	}
}
