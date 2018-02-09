package com.xjj.cms.menu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.channel.dao.ICmsChannelItemDao;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.menu.dao.ICmsMenuDao;
import com.xjj.cms.menu.dao.ICmsMenuOrgDao;
import com.xjj.cms.menu.dao.ICmsMenuTransmitDao;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.model.CmsMenuOrg;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("cmsMenuService")
public class CmsMenuService extends BaseService<CmsMenu, String> implements
		ICmsMenuService {
	
	@Autowired
	@Qualifier("cmsMenuTransmitDao")
	private ICmsMenuTransmitDao cmsMenuTransmitDao;
	
	@Autowired
	@Qualifier("cmsMenuDao")
	private ICmsMenuDao cmsMenuDao;

	@Autowired
	@Qualifier("cmsMenuOrgDao")
	private ICmsMenuOrgDao cmsMenuOrgDao;
	
	@Autowired
	@Qualifier("CmsChannelItemDao")
	private ICmsChannelItemDao iCmsChannelItemDao;

	@Autowired
	@Qualifier("cmsMenuDao")
	@Override
	public void setBaseDao(IBaseDao<CmsMenu, String> baseDao) {
		this.baseDao = cmsMenuDao;
	}

	@Override
	public Page<CmsMenu> query(Map<String, Object> filtersMap) throws Exception {
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

		List<CmsMenu> items = cmsMenuDao.query(skip, pageSize, queryMap,
				" order by sortNo,endModifyTime desc");
		// 获取总记录数
		int total = cmsMenuDao.total(queryMap);
		// 构造返回对象page
		Page<CmsMenu> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}

	@Override
	public boolean checkExistChildMenu(String menuId) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentMenuId", menuId);
		List<CmsMenu> list = cmsMenuDao.query(-1, -1, queryMap, null);
		if (!list.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<CmsMenu> listAllByMenuRootId() throws Exception {
		return cmsMenuDao.listAllByMenuRootId();
	}

	@Override
	public List<CmsMenu> getByParentId(String parentId) throws Exception {
		return cmsMenuDao.getByParentId(parentId);
	}

	@Override
	public List<CmsMenu> getAllChildMenus(String parentMentId,
			List<CmsMenu> childMenuList) throws Exception {
		List<CmsMenu> f_menuList = cmsMenuDao.getByParentId(parentMentId);
		if (!f_menuList.isEmpty()) {
			int size = f_menuList.size();
			for (int i = 0; i < size; i++) {
				CmsMenu menu = f_menuList.get(i);
				String p_menuId = menu.getId();
				childMenuList.add(menu);
				getAllChildMenus(p_menuId, childMenuList);
			}
		}
		return childMenuList;
	}

	@Override
	public List<CmsMenu> userMenuTreeList(String userId, String[] showModes)
			throws Exception {
		return cmsMenuDao.userMenuTreeList(userId, showModes);
	}

	@Override
	public List<CmsChannelItem> findCmsChannelItemByMenuId(String menuId) {
		return cmsMenuDao.findCmsChannelItemByMenuId(menuId);
	}

	@Override
	public List<CmsMenu> getAllmenu2ZTree(String orgId) throws Exception {
		List<CmsMenu> listMenu = new ArrayList<CmsMenu>();
		listMenu = cmsMenuDao.getAllmenu();
		if (null != orgId) {
			if (null != listMenu && listMenu.size() > 0) {
				int size = listMenu.size();
				for (int i = 0; i < size; i++) {
					CmsMenu menu = listMenu.get(i);
					if (null != menu) {
						String menuId = menu.getId();
						String hql = "from CmsMenuOrg mo where mo.org.id = '"
								+ orgId + "' and mo.menu.id = '" + menuId + "'";
						List<CmsMenuOrg> cmoList = cmsMenuDao.list(hql, null);
						if (null != cmoList && cmoList.size() > 0) {
							menu.setNocheck(false);
						}
					}
				}
			}
		}
		return listMenu;
	}

	@Override
	public List<CmsMenu> getMenuByOrgId(String orgId) throws Exception {

		List<CmsMenu> menuList = new ArrayList<CmsMenu>();
		String orgId_new = cmsMenuOrgDao.getByLoopOrgId(orgId);
		String hql = "from CmsMenu cm where cm.id in (select mo.menu.id from CmsMenuOrg mo where mo.org.id= '"
				+ orgId_new + "')";
		menuList = cmsMenuDao.list(hql);
		return menuList;
	}

	@Override
	public List<CmsMenu> findAllMenuByChannelId(String channelId) {
		List<CmsChannelItem> items =  iCmsChannelItemDao.findAllItem(channelId);
		if(!items.isEmpty() && items.size() >0 ){
			List<CmsMenu> menus = new ArrayList<CmsMenu>();
			for (int i = 0; i < items.size(); i++) {
				CmsChannelItem item = items.get(i);
				CmsMenu menu = cmsMenuDao.get(item.getMenuId());
				menus.add(menu);
			}
			return menus;
		}
		return null;
	}

	@Override
	public List<CmsMenu> getAllmenu2ZTree2(String orgid, String checkMenus) throws Exception{
		List<CmsMenu> listMenu = new ArrayList<CmsMenu>();
		listMenu = cmsMenuDao.getAllmenu();
		String[] menids = checkMenus.split(",");
		String sql = "";
		for (int i = 0; i < menids.length; i++) {
			if(i == 0){
				sql += "select cmt.target_menuId from cms_menu_transmit cmt where cmt.source_menuId = '"+menids[i]+"'";
			}else{
				sql += " intersect select cmt.target_menuId from cms_menu_transmit cmt where cmt.source_menuId = '"+menids[i]+"'";
			}
		}
		List cmtList = cmsMenuTransmitDao.listByNative(sql, null);
		if(null != listMenu && listMenu.size() > 0){
			for (CmsMenu cm : listMenu) {
				if(cmtList.contains(cm.getId())){
					cm.setNocheck(false);
				}
			}
		}
		return listMenu;
	}

}
