package com.xjj.cms.menu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.roleUserPer.dao.IZorganizeDao;
import com.xjj.cms.menu.dao.ICmsMenuDao;
import com.xjj.cms.menu.dao.ICmsMenuTransmitDao;
import com.xjj.cms.menu.model.CmsMenuTransmit;
import com.xjj.cms.menu.model.CmsMenuTransmitInfo;
import com.xjj.cms.menu.service.ICmsMenuTransmitService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("cmsMenuTransmitService")
public class CmsMenuTransmitService extends BaseService<CmsMenuTransmit, String> implements ICmsMenuTransmitService{
	
	@Autowired
	@Qualifier("cmsMenuTransmitDao")
	private ICmsMenuTransmitDao cmsMenuTransmitDao;
	
	@Autowired
	@Qualifier("cmsMenuDao")
	private ICmsMenuDao cmsMenuDao;
	
	@Autowired
	@Qualifier("ZorganizeDao")
	private IZorganizeDao iZorganizeDao;
	
	@Autowired
	@Qualifier("cmsMenuTransmitDao")
	@Override
	public void setBaseDao(IBaseDao<CmsMenuTransmit, String> baseDao) {
		this.baseDao = cmsMenuTransmitDao;
	}


	@Override
	public boolean checkCMTIsExist(String menuid, String orgid,
			String targetMenuid) {
		String hql = "from CmsMenuTransmit where 1 = 1 and source_menuId = '" +menuid+ "' and target_orgId = '" +orgid+ "' and target_menuId = '" +targetMenuid+ "'";
		List<CmsMenuTransmit> list = cmsMenuTransmitDao.list(hql);
		if(null != list && list.size() > 0){
			return true;
		}
		return false;
	}


	@Override
	public List<CmsMenuTransmit> findTransmit4OrgAMenu(String target_menuId,
			String target_orgId) {
		String hql = "from CmsMenuTransmit where 1 = 1 and target_menuId = '" +target_menuId+ "' and target_orgId = '" +target_orgId+ "'";
		List<CmsMenuTransmit> list = cmsMenuTransmitDao.list(hql);
		if(null != list && list.size() > 0){
			return list;
		}
		return null;
	}
	
	@Override
	public Page<CmsMenuTransmitInfo> query(Map<String, Object> filtersMap) throws Exception {
		// 开始查询下标
		int skip = filtersMap.get("skip") == null ? 1 : (Integer) filtersMap.get("skip");
		// 每页显示条数
		int pageSize = filtersMap.get("pageSize") == null ? 20 : (Integer) filtersMap.get("pageSize");
		// 获取查询条件
		@SuppressWarnings("unchecked")
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap.get("queryObj");
		String hql = "from CmsMenuTransmit cmt where 1=1";
		if(queryMap.get("keys") != null && queryMap.get("keys") != ""){
			hql += " and cmt.source_menuId in (select id from CmsMenu where menuName like '%"+queryMap.get("keys")+"%')";
			hql += " or cmt.target_orgId in (select id from Zorganize where name like '%"+queryMap.get("keys")+"%')";
			hql += " or cmt.target_menuId in (select id from CmsMenu where menuName like '%"+queryMap.get("keys")+"%')";
		}
		List<CmsMenuTransmit> cmts = cmsMenuTransmitDao.listPage(hql.toString(), skip, pageSize, new Object[0]);
		int total = cmsMenuTransmitDao.countAll();
		List<CmsMenuTransmitInfo> items = new ArrayList<>();
		if(null != cmts && cmts.size() > 0){
			for (CmsMenuTransmit cmt : cmts) {
				String source_menu_id = cmt.getSource_menuId();
				String source_menu_name = cmsMenuDao.get(source_menu_id).getMenuName();
				String source_menu_parent_name = cmsMenuDao.get(source_menu_id).getParentMenuName();
				String target_menu_id = cmt.getTarget_menuId();
				String target_menu_name = cmsMenuDao.get(target_menu_id).getMenuName();
				String target_org_id = cmt.getTarget_orgId();
				String target_org_name = iZorganizeDao.get(target_org_id).getName();
				CmsMenuTransmitInfo info = new CmsMenuTransmitInfo(source_menu_id, source_menu_name, 
						source_menu_parent_name, target_menu_id, target_menu_name, target_org_id, target_org_name);
				items.add(info);	
			}
//			total = items.size();
		}
		// 构造返回对象page
		Page<CmsMenuTransmitInfo> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}


	@Override
	public boolean deleteByIds(String source_menu_id, String target_menu_id, String target_org_id) {
		String hql = "from CmsMenuTransmit where source_menuId='"+source_menu_id+"' and target_orgId = '"+target_org_id+"' and target_menuId = '" +target_menu_id+"'";
		List<CmsMenuTransmit> list = cmsMenuTransmitDao.list(hql);
		if(null != list && list.size() > 0){
			cmsMenuTransmitDao.delete(list.get(0));
			return true;
		}
		return false;
	}
	
}
