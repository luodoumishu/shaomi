package com.xjj.cms.link.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj.cms.link.dao.ICmsLinkDao;
import com.xjj.cms.link.model.CmsLink;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("cmsLinkDao")
public class CmsLinkDao extends BaseHibernateDao<CmsLink, String> implements ICmsLinkDao{

	@Override
	public List<CmsLink> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsLink> list = listPage(hql.toString(), start, pageSize, queryMap);
		//文件下有多少个附件信息；
		return list;
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
			if (queryMap.get("linkName") != null && queryMap.get("linkName") != "") {
				hql.append(" and linkName like :linkName");
				paramMap.put("linkName", "%"+queryMap.get("linkName")+"%");
			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}

	@Override
	public int total(Map<String, Object> queryMap) {
		StringBuilder hql = new StringBuilder(HQL_COUNT_ALL);
		builtCondition(queryMap, hql);
		Long total = unique(hql.toString(), queryMap);
		return total.intValue();
	}

	@Override
	public CmsLink findLink(String linkId) {
		String hql = "from CmsLink where id='" + linkId + "'";
		List<CmsLink> list = list(hql);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

}
