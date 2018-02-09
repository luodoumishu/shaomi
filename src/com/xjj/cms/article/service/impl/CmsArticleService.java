package com.xjj.cms.article.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.article.dao.ICmsArticleDao;
import com.xjj.cms.article.dao.ICmsArticleHistoryDao;
import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

/**
 * 文章service接口实现
 * <p>
 * 
 * @author yeyunfeng 2014-9-2 下午3:25:40
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
@Service("cmsArticleService")
public class CmsArticleService extends BaseService<CmsArticle, String>
		implements ICmsArticleService {
	@Autowired
	@Qualifier("cmsArticleDao")
	private ICmsArticleDao cmsArticleDao;
	
	
	@Autowired
	@Qualifier("cmsArticleHistoryDao")
	private ICmsArticleHistoryDao cmsArticleHistoryDao;

	@Autowired
	@Qualifier("cmsMenuService")
	private ICmsMenuService cmsMenuService;

	@Autowired
	@Qualifier("cmsArticleDao")
	@Override
	public void setBaseDao(IBaseDao<CmsArticle, String> baseDao) {
		this.baseDao = cmsArticleDao;
	}

	@Override
	public Page<CmsArticle> query(Map<String, Object> filtersMap)
			throws Exception {
		// 开始查询下标
		int skip = filtersMap.get("skip") == null ? 0 : (Integer) filtersMap
				.get("skip");
		// 每页显示条数
		int pageSize = filtersMap.get("pageSize") == null ? 20
				: (Integer) filtersMap.get("pageSize");

		// 获取查询条件
		@SuppressWarnings("unchecked")
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap
				.get("queryObj");
		
		List<CmsArticle> items = cmsArticleDao.query(skip, pageSize, queryMap,
				" order by publicTime desc,endModifyTime desc");
		// 获取总记录数
		int total = cmsArticleDao.total(queryMap);
		// 构造返回对象page
		Page<CmsArticle> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}

	public Page<CmsArticle> getByuser4List(String menuId,String userId,
			Map<String, Object> filtersMap) throws Exception {

		List<CmsMenu> childMenuList = new ArrayList<CmsMenu>();
		StringBuffer menuIdsStr = new StringBuffer();
		String menuIdsr = "";
		if("0".equals(menuId) || null == menuId){
			String[] showModes = {"0","1"};
			childMenuList = cmsMenuService.userMenuTreeList(userId,showModes);
			int childSize = childMenuList.size();
			for (int i = 0; i < childSize; i++) {
				menuIdsStr.append(",'" + childMenuList.get(i).getId()+"'");
			}
			if(childSize != 0){
				menuIdsr = menuIdsStr.substring(1);
			}
		}else {
			childMenuList = cmsMenuService.getAllChildMenus(menuId, childMenuList);
			menuIdsStr.append("'"+menuId+"'");
			int childSize = childMenuList.size();
			for (int i = 0; i < childSize; i++) {
				menuIdsStr.append(",'" + childMenuList.get(i).getId()+"'");
			}
			/*if(childSize != 0){
				//menuIdsr = menuIdsStr.substring(1);
			}*/
			menuIdsr = menuIdsStr.toString();
		}
		
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
		queryMap.put("menuIds", menuIdsr);
		queryMap.put("creatUserId", userId);
		/*List<CmsArticle> items = cmsArticleDao.query(skip, pageSize, queryMap,
				);*/
		
		List<CmsArticle> items = cmsArticleDao.query(skip, pageSize, " order by publicTime desc,endModifyTime desc", queryMap);
		// 获取总记录数
		int total = cmsArticleDao.hql_total(queryMap);
		// 构造返回对象page
		Page<CmsArticle> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}
	
	
	@Override
	public Page<CmsArticle> getALLArticlesByMenuId(String menuId,
			Map<String, Object> filtersMap) throws Exception {

		List<CmsMenu> childMenuList = new ArrayList<CmsMenu>();
		childMenuList = cmsMenuService.getAllChildMenus(menuId, childMenuList);
		StringBuffer menuIdsStr = new StringBuffer();
		menuIdsStr.append("'"+menuId+"'");
		int childSize = childMenuList.size();
		for (int i = 0; i < childSize; i++) {
			menuIdsStr.append(",'" + childMenuList.get(i).getId()+"'");
		}
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
		queryMap.put("menuIds", menuIdsStr.toString());
		/*List<CmsArticle> items = cmsArticleDao.query(skip, pageSize, queryMap,
				);*/
		
		List<CmsArticle> items = cmsArticleDao.query(skip, pageSize, " order by publicTime desc,endModifyTime desc", queryMap);
		// 获取总记录数
		int total = cmsArticleDao.hql_total(queryMap);
		// 构造返回对象page
		Page<CmsArticle> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}

	@Override
	public void saveVisitNumAddOne(CmsArticle ca) throws Exception {
		cmsArticleDao.saveVisitNumAddOne(ca);
	}

	public Page<CmsArticle> getALLArticlesByMenuIds(String[] menuIds,
			Map<String, Object> filtersMap) throws Exception {
		List<CmsMenu> allChildMenuList = new ArrayList<CmsMenu>();
		StringBuffer menuIdsStr = new StringBuffer();
		int menuidLength = menuIds.length;
		for(int i=0;i<menuidLength;i++){
			List<CmsMenu> childMenuList = new ArrayList<CmsMenu>();
			childMenuList = cmsMenuService.getAllChildMenus(menuIds[i], childMenuList);
			allChildMenuList.addAll(childMenuList);
			if(i==0){
				menuIdsStr.append("'"+menuIds[i]+"'");
			}else{
				menuIdsStr.append(",'"+menuIds[i]+"'");
			}
		}
		int childSize = allChildMenuList.size();
		for (int i = 0; i < childSize; i++) {
			menuIdsStr.append(",'" + allChildMenuList.get(i).getId()+"'");
		}
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
		queryMap.put("menuIds", menuIdsStr.toString());
		/*List<CmsArticle> items = cmsArticleDao.query(skip, pageSize, queryMap,
				);,endModifyTime*/
		
		List<CmsArticle> items = cmsArticleDao.query(skip, pageSize, " order by publicTime desc", queryMap);
		// 获取总记录数
		int total = cmsArticleDao.total(queryMap);
		// 构造返回对象page
		Page<CmsArticle> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}

	@Override
	public List<CmsArticle> getALLArticlesByMenuId(String menuId) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("menuId", menuId);
		List<CmsArticle> items = cmsArticleDao.query(0, 11, " order by publicTime desc,endModifyTime desc", queryMap);
		return items;
	}
	@Override
	public List<CmsArticle> getOrgArticlesByMenuId(String menuId,String orgId) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("menuId", menuId);
		queryMap.put("fjsq_orgCode", orgId);
		List<CmsArticle> items = cmsArticleDao.query(0, 11, " order by publicTime desc,endModifyTime desc", queryMap);
		return items;
	}

	@Override
	public List<CmsArticle> findArticleBySearch(String searchValue, String org) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("searchValue", searchValue);
		queryMap.put("fjsq_orgCode", org);
		List<CmsArticle> items = cmsArticleDao.query(0, 100, " order by publicTime desc,endModifyTime desc", queryMap);
		return items;
	}

	@Override
	public List<CmsArticleHistory> get8ArticleByMenuId(String menuId) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(menuId.equals("0")){
			queryMap.put("menuId", menuId);
		}else{
			List<CmsMenu> childMenuList = new ArrayList<CmsMenu>();
			childMenuList = cmsMenuService.getAllChildMenus(menuId, childMenuList);
			StringBuffer menuIdsStr = new StringBuffer();
			menuIdsStr.append("'"+menuId+"'");
			int childSize = childMenuList.size();
			for (int i = 0; i < childSize; i++) {
				menuIdsStr.append(",'" + childMenuList.get(i).getId()+"'");
			}
			queryMap.put("menuIds", menuIdsStr);
		}
		queryMap.put("check_log", 2);
		queryMap.put("isDelete", "0");
		List<CmsArticleHistory> items = cmsArticleHistoryDao.query(0, 8, queryMap," order by publicTime desc");
		return items;
	}
}
