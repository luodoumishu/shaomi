package com.xjj.cms.menu.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xjj._extensions.roleUserPer.dao.IZorganizeDao;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.cms.menu.dao.ICmsMenuOrgDao;
import com.xjj.cms.menu.model.CmsMenuOrg;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("cmsMenuOrgDao")
public class CmsMenuOrgDao extends BaseHibernateDao<CmsMenuOrg, String> implements ICmsMenuOrgDao {
	
	@Autowired
	@Qualifier("ZorganizeDao")
	private IZorganizeDao orgDao;
	
	
	@Override
	public List<CmsMenuOrg> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsMenuOrg> list = listPage(hql.toString(), start, pageSize, queryMap);
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
	 *  @param queryMap 查询条件
	 * @author yeyunfeng 2014-9-2  下午3:15:01
	 * @param queryMap
	 * @param hql
	 *
	 */
	protected void builtCondition(Map<String, Object> queryMap,
			StringBuilder hql) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (queryMap != null) {
			if (queryMap.get("menuId") != null && queryMap.get("menuId") != "") {
				hql.append(" and menu.id = :menuId");
				paramMap.put("menuId", queryMap.get("menuId"));
			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}



	@Override
	public void deleteByOrgId(String orgId) throws Exception {
		String hql = "delete CmsMenuOrg mo where mo.org.id = '"+orgId+"'";
		this.execteBulk(hql, null);
	}



	@Override
	public String getByLoopOrgId(String orgId) throws Exception {
		String hql = "from CmsMenuOrg mo where mo.org.id = '"+orgId+"'";
		List<CmsMenuOrg> list = list(hql);
		if (null == list || list.size() == 0) {
			Zorganize org = orgDao.get(orgId);
			if (null != org) {
				orgId = org.getParentId();
				return getByLoopOrgId(orgId);
			}
		}
		return orgId;
	}

}
