package com.xjj.cms.channel.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.xjj.cms.channel.dao.ICmsChannelDao;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("CmsChannelDao")
public class CmsChannelDao extends BaseHibernateDao<CmsChannel, String> implements ICmsChannelDao {
	public List<CmsChannel> getGroupsById(String id) {
		StringBuilder sql = new StringBuilder(SQL_LIST_ALL);
		sql.append(" and id=:id");

		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);

		return super.listByNative(sql.toString(), CmsChannel.class, map);
	}
	
	public List<CmsChannel> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsChannel> list = listPage(hql.toString(), start, pageSize,
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
			if (queryMap.get("channeName") != null && queryMap.get("channeName") != "") {
				hql.append(" and channeName like :channeName");
				paramMap.put("channeName", "%"+queryMap.get("channeName")+"%");
			}
			if (queryMap.get("parentChanneid") != null && queryMap.get("parentChanneid") != "") {
				hql.append(" and parentChanneid = :parentChanneid");
				paramMap.put("parentChanneid", queryMap.get("parentChanneid"));
			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}

	@Override
	public List<CmsChannel> listAllByOrgRootId(String orgRootId) {
		List<String> ids = getListByOrgId(orgRootId);
		if(ids!=null && ids.size()>0){
			String orgIds = "";
			for(String id:ids){
				if(!"".equals(orgIds)){
					orgIds += ",";
				}
				orgIds += "'"+id+"'";
			}
			String hsql = "from CmsChannel t where t.id in ("+orgIds+") order by t.sortno";
			Query query = getSession().createQuery(hsql);
			setParameters(query, null);
			List i =query.list();
			return i;
		}
		return null;
	}

		public List<String> getListByOrgId(String orgId){
			String sql = "select id from CMS_CHANNEL";
			//String sql = "select id from z_organize_t s start with s.id='"+orgId+"' connect by prior s.id=s.parentid";
			List<String> ids = getSession().createSQLQuery(sql).addScalar("id",StringType.INSTANCE).list();
			return ids;
		}

		@Override
		public boolean checkExistChildOrg(String orgId) {
			String sql = "";
			List<CmsChannel> list = null;
			sql = " select * from CMS_CHANNEL where PARENT_CHANNEID ='"+orgId+"'";
			list = getSession().createSQLQuery(sql).addScalar("id",StringType.INSTANCE).list();	
			if(!list.isEmpty()){
				return true;
			}else{
				return false;
			}	
	    }

		@Override
		public List<CmsChannelItem> findCmsChannelItemByChannelId(String channelId) {
			String hql = "from CmsChannelItem where channelId='"+channelId+"'";
			List<CmsChannelItem> item = list(hql);
			if(item.size() > 0){
				return item;
			}
			return null;
		}
		@Override
		public CmsChannel findByChlCode(String chlCode) {
			String hql = "";
			List<CmsChannel> list = null;
			hql = "from CmsChannel where channeCode ='" + chlCode +"'";
			list = list(hql);
			if(null != list && list.size()>0){
				return list.get(0);
			}
			return null;
		}

		@Override
		public List<CmsChannel> findByChildChlById(String id) {
			String hql = "";
			List<CmsChannel> list = null;
			hql = "from CmsChannel where parentChanneid ='" + id +"'";
			list = list(hql);
			if(null != list && list.size()>0){
				return list;
			}
			return null;
		}


}
