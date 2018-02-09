package com.xjj.cms.win.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj.cms.win.dao.ISeasonInfoDao;
import com.xjj.cms.win.model.SeasonInfo;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("SeasonInfoDao")
public class SeasonInfoDao extends BaseHibernateDao<SeasonInfo, String> implements ISeasonInfoDao{

	@Override
	public List<SeasonInfo> query(int skip, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<SeasonInfo> list = listPage(hql.toString(), skip, pageSize,
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

}
