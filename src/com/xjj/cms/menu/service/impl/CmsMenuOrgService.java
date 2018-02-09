package com.xjj.cms.menu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.roleUserPer.dao.IZorganizeDao;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.cms.menu.dao.ICmsMenuDao;
import com.xjj.cms.menu.dao.ICmsMenuOrgDao;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.model.CmsMenuOrg;
import com.xjj.cms.menu.service.ICmsMenuOrgService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;
import com.xjj.jdk17.utils.StringUtil;

@Service("cmsMenuOrgService")
public class CmsMenuOrgService extends BaseService<CmsMenuOrg, String> implements ICmsMenuOrgService{
	
	@Autowired
	@Qualifier("cmsMenuOrgDao")
	private ICmsMenuOrgDao cmsMenuOrgDao;

	
	@Autowired
	@Qualifier("cmsMenuDao")
	private ICmsMenuDao cmsMenuDao;
	
	@Autowired
	@Qualifier("ZorganizeDao")
	private IZorganizeDao orgDao;
	
	@Autowired
	@Qualifier("cmsMenuOrgDao")
	@Override
	public void setBaseDao(IBaseDao<CmsMenuOrg, String> baseDao) {
		this.baseDao = cmsMenuOrgDao;
	}
	
	@Override
	public Page<CmsMenuOrg> query(Map<String, Object> filtersMap) throws Exception {
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

		List<CmsMenuOrg> items = cmsMenuOrgDao.query(skip, pageSize, queryMap,
				" order by id");
		// 获取总记录数
		int total = cmsMenuOrgDao.total(queryMap);
		// 构造返回对象page
		Page<CmsMenuOrg> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}

	@Override
	public void save(String menuIds, String orgId) throws Exception {
		if (!StringUtil.isEmpty(menuIds) && !StringUtil.isEmpty(orgId)) {
			String[] ids = menuIds.split(",");
			Zorganize org = orgDao.get(orgId);
			if (null != org) {
				cmsMenuOrgDao.deleteByOrgId(orgId);
				String rootMenuId = "0";
				for (String menuId : ids) {
					CmsMenu menu = null;
					if (rootMenuId.equals(menuId)) {
						menu = new CmsMenu();
						menu.setId(rootMenuId);
					}else {
						menu = cmsMenuDao.get(menuId);
					}
					if (null != menu) {
						CmsMenuOrg menuOrg = new CmsMenuOrg();
						menuOrg.setOrg(org);
						menuOrg.setMenu(menu);
						cmsMenuOrgDao.save(menuOrg);
					}
					
				}
			}
			
		}
	
	}
	
}
