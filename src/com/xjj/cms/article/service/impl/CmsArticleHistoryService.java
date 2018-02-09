package com.xjj.cms.article.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.service.IZorganizeService;
import com.xjj.cms.article.dao.ICmsArticleHistoryDao;
import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.cms.article.service.ICmsArticleHistoryService;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.model.CmsMenuTransmit;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.cms.menu.service.ICmsMenuTransmitService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;
import com.xjj.jdk17.utils.StringUtil;

@Service("cmsArticleHistoryService")
public class CmsArticleHistoryService extends BaseService<CmsArticleHistory, String> implements
		ICmsArticleHistoryService {

	@Autowired
	@Qualifier("cmsMenuTransmitService")
	private ICmsMenuTransmitService cmsMenuTransmitService;
	
	@Autowired
	@Qualifier("ZorganizeService")
	private IZorganizeService  ZorganizeService;
	
	@Autowired
	@Qualifier("cmsArticleHistoryDao")
	private ICmsArticleHistoryDao cmsArticleHistoryDao;

	@Autowired
	@Qualifier("cmsMenuService")
	private ICmsMenuService cmsMenuService;

	@Autowired
	@Qualifier("cmsArticleHistoryDao")
	@Override
	public void setBaseDao(IBaseDao<CmsArticleHistory, String> baseDao) {
		this.baseDao = cmsArticleHistoryDao;
	}

	@Override
	public Page<CmsArticleHistory> query(Map<String, Object> filtersMap)
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

		// order by publicTime desc,endModifyTime desc
		List<CmsArticleHistory> items = cmsArticleHistoryDao.query(skip,
				pageSize, queryMap, " order by publicTime desc");
		// 获取总记录数
		int total = cmsArticleHistoryDao.total(queryMap);
		// 构造返回对象page
		Page<CmsArticleHistory> page = PageUtil.getPage(skip, pageSize, items,
				total);
		return page;
	}

	public Page<CmsArticleHistory> getALLArticlesByMenuIds(String[] menuIds,
			Map<String, Object> filtersMap) throws Exception {
		List<CmsMenu> allChildMenuList = new ArrayList<CmsMenu>();
		StringBuffer menuIdsStr = new StringBuffer();
		int menuidLength = menuIds.length;
		for (int i = 0; i < menuidLength; i++) {
			List<CmsMenu> childMenuList = new ArrayList<CmsMenu>();
			childMenuList = cmsMenuService.getAllChildMenus(menuIds[i],
					childMenuList);
			allChildMenuList.addAll(childMenuList);
			if (i == 0) {
				menuIdsStr.append("'" + menuIds[i] + "'");
			} else {
				menuIdsStr.append(",'" + menuIds[i] + "'");
			}

		}
		int childSize = allChildMenuList.size();
		for (int i = 0; i < childSize; i++) {
			menuIdsStr.append(",'" + allChildMenuList.get(i).getId() + "'");
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
		/*
		 * List<CmsArticle> items = cmsArticleDao.query(skip, pageSize,
		 * queryMap, );,endModifyTime
		 */

		List<CmsArticleHistory> items = cmsArticleHistoryDao.query(skip,
				pageSize, queryMap, " order by publicTime desc");
		// 获取总记录数
		int total = cmsArticleHistoryDao.total(queryMap);
		// 构造返回对象page
		Page<CmsArticleHistory> page = PageUtil.getPage(skip, pageSize, items,
				total);
		return page;
	}

	public CmsArticle history2article(CmsArticleHistory model) {
		String title = model.getTitle();
		String titleColor = model.getTitleColor();
		String titleSize = model.getTitleSize();
		String menuId = model.getMenuId();
		String menuName = model.getMenuName();
		String author = model.getAuthor();
		String source = model.getSource();
		String readCount = model.getReadCount();
		String keyWord = model.getKeyWord();
		String content = model.getContent();
		String behalf_imageUrl = model.getBehalf_imageUrl();
		String summary = model.getSummary();
		String orgId = model.getOrgId();
		String orgName = model.getOrgName();
		Date publicTime = model.getPublicTime();
		String linkUrl = model.getLinkUrl();
		String ifComment = model.getIfComment();
		Integer check_log = model.getCheck_log();
		List<CmsAffix> cmsAffixs = model.getCmsAffixs();
		String behalf_image_width = model.getBehalf_image_width();
		String behalf_image_height = model.getBehalf_image_height();

		CmsArticle history = new CmsArticle(title, titleColor, titleSize,
				menuId, menuName, author, source, readCount, keyWord, content,
				behalf_imageUrl, summary, orgId, orgName, publicTime, linkUrl,
				ifComment, check_log, cmsAffixs,behalf_image_width,behalf_image_height);

		history.setCreateTime(model.getCreateTime());
		history.setCreatUserId(model.getCreatUserId());
		history.setCreatUserName(model.getCreatUserName());
		history.setEndModifyTime(model.getEndModifyTime());
		history.setEndModifyUserId(model.getEndModifyUserId());
		history.setEndModifyUserName(model.getEndModifyUserName());

		return history;
	}

	@Override
	public void saveVisitNumAddOne(CmsArticleHistory cah) throws Exception {
		cmsArticleHistoryDao.saveVisitNumAddOne(cah);
	}

	@Override
	public List<CmsArticleHistory> getOrgArticlesByMenuId(String menuId,
			String orgId) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("menuId", menuId);
		queryMap.put("fjsq_orgCode", orgId);
		List<CmsArticleHistory> items = cmsArticleHistoryDao.query(0, 11,
				queryMap, " order by publicTime desc,endModifyTime desc");
		return items;
	}

	@Override
	public List<CmsArticleHistory> getALLArticlesByMenuId(String menuId)
			throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("menuId", menuId);
		List<CmsArticleHistory> items = cmsArticleHistoryDao.query(0, 11,
				queryMap, " order by publicTime desc,endModifyTime desc");
		return items;
	}

	@Override
	public Page<CmsArticleHistory> getALLArticlesByMenuId(String menuId,
			Map<String, Object> filtersMap) throws Exception {
		List<CmsMenu> childMenuList = new ArrayList<CmsMenu>();
		childMenuList = cmsMenuService.getAllChildMenus(menuId, childMenuList);
		StringBuffer menuIdsStr = new StringBuffer();
		menuIdsStr.append("'" + menuId + "'");
		int childSize = childMenuList.size();
		for (int i = 0; i < childSize; i++) {
			menuIdsStr.append(",'" + childMenuList.get(i).getId() + "'");
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
		/*
		 * List<CmsArticle> items = cmsArticleDao.query(skip, pageSize,
		 * queryMap, );
		 */

		/**
		 * 推送文章 
		 */
		String orgid = "";
		if (null != queryMap.get("fjsq_orgCode") && !queryMap.get("fjsq_orgCode").toString().isEmpty()) {
			String pyName = queryMap.get("fjsq_orgCode").toString();
			orgid = ZorganizeService.getByPyName(pyName).getId();
		}
		StringBuffer transmitMenuId = new StringBuffer(); 
		List<CmsMenuTransmit> list = new ArrayList<CmsMenuTransmit>();
//		for(int j = 0; j < menuidLength; j++){
//			String menuid = menuIds[j];
			List<CmsMenuTransmit> cmts = cmsMenuTransmitService.findTransmit4OrgAMenu(menuId,orgid);
			if(null != cmts){
				list.addAll(cmts);
			}
//		}
		if(list.size() > 0){
			for (int z = 0; z < list.size(); z++) {
				if(z == 0){
					transmitMenuId.append("'" + list.get(z).getSource_menuId() + "'");
				}else{
					transmitMenuId.append(",'" + list.get(z).getSource_menuId() + "'");
				}
			}
			queryMap.put("transmitMenuIds", transmitMenuId.toString());
		}
		
		
		List<CmsArticleHistory> items = cmsArticleHistoryDao.query(skip,
				pageSize, queryMap,
				" order by publicTime desc,endModifyTime desc");
		// 获取总记录数
		int total = cmsArticleHistoryDao.total(queryMap);
		// 构造返回对象page
		Page<CmsArticleHistory> page = PageUtil.getPage(skip, pageSize, items,
				total);
		return page;
	}

	@Override
	public List<CmsArticleHistory> findArticleBySearch(String searchValue,String orgCode)
			throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("articleName", searchValue);
		if (CmsCC.ENABLED_MENU_FJSQ) { //栏目分级授权开启
			if (StringUtil.isEmpty(orgCode)) {
				throw new JspException("获取部门代码异常，请联系管理员！");
			}else{
				queryMap.put("fjsq_orgCode", orgCode);
			}
		}
		//显示未逻辑删除的文章
		queryMap.put("isDelete", "0");
		List<CmsArticleHistory> items = cmsArticleHistoryDao.query(0, 15, queryMap, " order by publicTime desc,endModifyTime desc");
		return items;
	}

	@Override
	public Integer getArticleNumByOrg(Zorganize org, String startDate, String endDate) {
		return cmsArticleHistoryDao.getArticleNumByOrg(org,startDate,endDate);
	}

}
