package com.xjj.cms.channel.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj.cms.channel.dao.ICmsChannelItemDao;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("CmsChannelItemDao")
public class CmsChannelItemDao extends BaseHibernateDao<CmsChannelItem, String> implements ICmsChannelItemDao {
	
	@Override
	public List<CmsChannelItem> queryByChannelId(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsChannelItem> list = listPage(hql.toString(), start, pageSize,
				queryMap);
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
	 * @Description: 解析查询条件，拼接查询sql
	 * @param queryMap
	 *            查询
	 * @author longdp 2013-12-11下午12:14:20
	 */
	protected void builtCondition(Map<String, Object> queryMap,
			StringBuilder hql) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (queryMap != null) {
			if (queryMap.get("itemId") != null && queryMap.get("itemId") != "") {
				hql.append(" and channelId = :itemId");
				paramMap.put("itemId", queryMap.get("itemId"));
			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}

	@Override
	public List<CmsMenu> findByMenuId(String menuId) {
		String hql = "";
		hql = "from CmsChannelItem where menuId ='" + menuId +"'";
		List<CmsChannelItem> itemList  = list(hql);
		if(null != itemList && itemList.size()>0){
			CmsChannelItem item = itemList.get(0);
			hql = " from CmsMenu where id ='"+item.getMenuId()+"'";
			return list(hql);
		}else{
			hql = " from CmsMenu where id ='"+menuId+"'";
			return list(hql);
		}
	}

	@Override
	public boolean exitMenuInChannelItem(String menuId,String channelId) throws Exception {
		String hql = "from CmsChannelItem ci where ci.menuId ='" + menuId +"' and ci.channelId = '"+channelId+"'";
		List<CmsChannelItem> itemList  = list(hql);
		if (null == itemList || itemList.isEmpty()) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean ifAdd(String menuId,String channelId) {
		String hql = "";
		List<CmsChannelItem> list = null;
		hql = " from CmsChannelItem where menuId ='"+menuId+"'"+" and channelId='"+channelId+"'";
		list = list(hql);
		if(list.size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<CmsChannel> findByChannelId(String channelId) {
		String hql = "";
		hql = " from CmsChannel where ID ='"+channelId+"'";
		return list(hql);
	}

	@Override
	public List<CmsChannelItem> findCmsChannelItem(String menuId,String channelId) {
		String hql =" from CmsChannelItem where menuId ='"+menuId+"'" +" and channelId='"+channelId+"'";
		return list(hql);
	}

	@Override
	public List<CmsMenu> findAllMenu() {
		String hql = "from CmsMenu where 1=1 ";
		return list(hql);
	}

	@Override
	public List<CmsMenu> child(String menuId) {
		String hql = "";
		hql = "from CmsChannelItem where menuId ='" + menuId +"'";
		List<CmsChannelItem> list  = list(hql);
		if(null != list && list.size()>0){
			CmsChannelItem item = list.get(0);
			hql = " from CmsMenu where parentMenuId ='"+item.getMenuId()+"'";
			return list(hql);
		}else{
			hql = " from CmsMenu where parentMenuId ='"+menuId+"'";
			return list(hql);
		}
	}

	@Override
	public List<CmsChannelItem> findAllItem(String channelId) {
		String hql = "";
		hql = "from CmsChannelItem where 1=1 and channelId='"+channelId+"' order by sortno";
		return list(hql);
	}

	@Override
	public int findCountByItem(String channelId) {
		String hql = "";
		hql = "from CmsChannelItem where 1=1 and channelId='"+channelId+"'";
		return list(hql).size();
	}
}
