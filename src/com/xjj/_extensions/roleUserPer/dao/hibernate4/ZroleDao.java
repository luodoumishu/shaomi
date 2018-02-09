package com.xjj._extensions.roleUserPer.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj._extensions.roleUserPer.dao.IZroleDao;
import com.xjj._extensions.roleUserPer.model.Zrole;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("ZroleDao")
public class ZroleDao extends BaseHibernateDao<Zrole, String> implements IZroleDao {

	public List<Zrole> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<Zrole> list = listPage(hql.toString(), start, pageSize,
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
			if (queryMap.get("name") != null && queryMap.get("name") != "") {
				hql.append(" and name = :name");
				paramMap.put("name", queryMap.get("name"));
			}
			if (queryMap.get("notCode") != null && queryMap.get("notCode") != "") {
				hql.append(" and (code != :notCode or code is null)");
				paramMap.put("notCode", queryMap.get("notCode"));
			}
			if (queryMap.get("code") != null && queryMap.get("code") != "") {
				hql.append(" and code = :code");
				paramMap.put("code", queryMap.get("code"));
			}
			if (queryMap.get("id") != null && queryMap.get("id") != "") {
				hql.append(" and id != :id");
				paramMap.put("id", queryMap.get("id"));
			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}
}
