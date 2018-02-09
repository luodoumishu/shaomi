package com.xjj.cms.base.dao.hibernate4;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj.cms.base.dao.ICmsAffixDao;
import com.xjj.cms.base.model.CmsAffix;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

/**
 * 
 * <p>
 * @author yeyunfeng 2014-9-2 下午3:24:21 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
/**
 * 文章附件dao实现
 * <p>
 * @author yeyunfeng 2014-9-5 下午8:13:43 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-5
 * @modify by reason:{方法名}:{原因}
 */
@Repository("cmsAffixDao")
public class CmsAffixDao extends BaseHibernateDao<CmsAffix, String> implements
		ICmsAffixDao {
	
	@Override
	public List<CmsAffix> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsAffix> list = listPage(hql.toString(), start, pageSize, queryMap);
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
	 * 
	 * @author yeyunfeng 2014-9-2  下午3:13:28
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
			if (queryMap.get("isDelete") != null && queryMap.get("isDelete") != "") {
				hql.append(" and isDelete = :isDelete");
				paramMap.put("isDelete", queryMap.get("isDelete"));
			}
			//解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}

	@Override
	public List<CmsAffix> findAffixByModelId(String modelId) {
		String hql = "from CmsAffix where 1=1 and modeId='" + modelId +"'";
		List<CmsAffix> list = new ArrayList<CmsAffix>();
		list = list(hql);
		if(list.size() > 0){
			return list;
		}else{
			return null;
		}
	}
}
