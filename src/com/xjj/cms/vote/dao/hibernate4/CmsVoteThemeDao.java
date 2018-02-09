package com.xjj.cms.vote.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj.cms.vote.dao.ICmsVoteThemeDao;
import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("cmsVoteThemeDao")
public class CmsVoteThemeDao extends BaseHibernateDao<CmsVoteTheme, String> implements ICmsVoteThemeDao {
	@Override
	public List<CmsVoteTheme> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsVoteTheme> list = listPage(hql.toString(), start, pageSize,
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
//			if (queryMap.get("itemId") != null && queryMap.get("itemId") != "") {
//				hql.append(" and channelId = :itemId");
//				paramMap.put("itemId", queryMap.get("itemId"));
//			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}
	
	@Override
	public CmsVoteTheme getById(String themeId) throws Exception {
		CmsVoteTheme theme = null;
		String hql = "from CmsVoteTheme vt where vt.isDelete='0' and vt.isValid='0' and vt.id='"+themeId+"'";
		theme = this.unique(hql, null);
		return theme;
	}

}
