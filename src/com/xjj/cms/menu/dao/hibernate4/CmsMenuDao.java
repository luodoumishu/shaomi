package com.xjj.cms.menu.dao.hibernate4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.menu.dao.ICmsMenuDao;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("cmsMenuDao")
public class CmsMenuDao extends BaseHibernateDao<CmsMenu, String> implements
		ICmsMenuDao {

	@Override
	public List<CmsMenu> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsMenu> list = listPage(hql.toString(), start, pageSize, queryMap);
		return list;
	}

	@Override
	public int total(Map<String, Object> queryMap) {
		StringBuilder hql = new StringBuilder(HQL_COUNT_ALL);
		builtCondition(queryMap, hql);
		Long total = unique(hql.toString(), queryMap);
		return total.intValue();
	}

	/**
	 * 解析查询条件，拼接查询sql
	 * 
	 * @param queryMap
	 *            查询条件
	 * @author yeyunfeng 2014-9-2 下午3:15:01
	 * @param queryMap
	 * @param hql
	 * 
	 */
	protected void builtCondition(Map<String, Object> queryMap,
			StringBuilder hql) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (queryMap != null) {
			if (queryMap.get("id") != null && queryMap.get("id") != "") {
				hql.append(" and id = :id");
				paramMap.put("id", queryMap.get("id"));
			}
			if (queryMap.get("parentMenuId") != null
					&& queryMap.get("parentMenuId") != "") {
				hql.append(" and parentMenuId = :parentMenuId");
				paramMap.put("parentMenuId", queryMap.get("parentMenuId"));
			}
			if (queryMap.get("menuName") != null
					&& queryMap.get("menuName") != "") {
				hql.append(" and menuName = :menuName");
				paramMap.put("menuName", queryMap.get("menuName"));
			}

			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}

	@Override
	public List<CmsMenu> listAllByMenuRootId() throws Exception {
		
		return getAllmenu();
		/*List<String> ids = geAllList();
		if (ids != null && ids.size() > 0) {
			String orgIds = "";
			for (String id : ids) {
				if (!"".equals(orgIds)) {
					orgIds += ",";
				}
				orgIds += "'" + id + "'";
			}
			String hsql = "from CmsMenu t where t.id in (" + orgIds
					+ ") order by t.sortNo,t.endModifyTime desc ";
			Query query = getSession().createQuery(hsql);
			setParameters(query, null);
			return query.list();
		}
		return null;*/
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CmsMenu> getByParentId(String parentId) throws Exception {
		String sql = "from CmsMenu t where t.parentMenuId = ? order by t.sortNo,t.endModifyTime desc ";
		String[] paramList = { parentId };
		Query query = getSession().createQuery(sql);
		setParameters(query, paramList);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	private List<String> geAllList() {
		String hql = "select m.id from CmsMenu m";
		Query query = getSession().createQuery(hql);
		List<String> ids = query.list();
		return ids;
	}

	@Override
	public List<CmsMenu> userMenuTreeList(String userId,String[] showModes) throws Exception {

		List<CmsMenu> menuList = new ArrayList<CmsMenu>();
		String hql = "select m.menuId from CmsMenuUser m where m.userId = '"+userId+"'";
		Query query = getSession().createQuery(hql);
		List<String> ids = query.list();
		StringBuffer idsStr = new StringBuffer();
		
		if (null != ids) {
			int length = ids.size();
			for (int i = 0; i < length; i++) {
				idsStr.append(",'"+ids.get(i) + "'");
			}
			
			if (idsStr.length() > 1) {
				String idsString = idsStr.substring(1);
				String menu_hql = "from CmsMenu m where m.id in (" + idsString
						+ ")";
				if(showModes!=null){
					String showMode = "";
					for (int i = 0; i < showModes.length; i++) {
						showMode += showModes[i] +",";
					}
					showMode = showMode.substring(0, showMode.length()-1);
					menu_hql += "and m.showMode in (" + showMode + ")";
				}
				menu_hql += " order by sortNo";
				menuList = list(menu_hql);
			}
		}
		
		return menuList;
	}

	@Override
	public List<CmsChannelItem> findCmsChannelItemByMenuId(String menuId) {
		String hql = "from CmsChannelItem where menuId='"+menuId+"'";
		List<CmsChannelItem> item = list(hql);
		if(item.size() > 0){
			return item;
		}
		return null;
	}
	
	public List<CmsMenu> getAllmenu()throws Exception{
		List<CmsMenu> listMenu = new ArrayList<CmsMenu>();
		String hql = "from CmsMenu cm where cm.isDelete='0' order by sortNo,cm.endModifyTime desc";
		listMenu = list(hql);
		return listMenu;
	}
	
}
