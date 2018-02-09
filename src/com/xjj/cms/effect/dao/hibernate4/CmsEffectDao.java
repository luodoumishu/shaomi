package com.xjj.cms.effect.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj.cms.effect.model.CmsEffect;
import com.xjj.cms.effect.dao.ICmsEffectDao;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

/**
 * 特效dao实现
 * <p>
 * 
 * @author yeyunfeng 2015年6月10日 下午7:36:03
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年6月10日
 * @modify by reason:{方法名}:{原因}
 */
@Repository("cmsEffectDao")
public class CmsEffectDao extends BaseHibernateDao<CmsEffect, String> implements
		ICmsEffectDao {
	@Override
	public List<CmsEffect> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsEffect> list = listPage(hql.toString(), start, pageSize,
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

	protected void builtCondition(Map<String, Object> queryMap,
			StringBuilder hql) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (queryMap != null) {
			if (queryMap.get("id") != null && queryMap.get("id") != "") {
				hql.append(" and id = :id");
				paramMap.put("id", queryMap.get("id"));
			}
			if (queryMap.get("name") != null && queryMap.get("name") != "") {
				hql.append(" and name like :name");
				paramMap.put("name", "%" +queryMap.get("name")+ "%");
				
			}
			if (queryMap.get("isShow") != null && queryMap.get("isShow") != "") {
				hql.append(" and isShow = :isShow");
				paramMap.put("isShow",queryMap.get("isShow"));
				
			}
			if (queryMap.get("orgIds") != null && queryMap.get("orgIds") != "") {
				hql.append(" and orgId in ("+ queryMap.get("orgIds")+")");
			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}
}
