package com.xjj.cms.vote.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj.cms.vote.dao.ICmsVoteItemDao;
import com.xjj.cms.vote.model.CmsVoteItem;
import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("cmsVoteItemDao")
public class CmsVoteItemDao extends BaseHibernateDao<CmsVoteItem, String> implements ICmsVoteItemDao {
	@Override
	public List<CmsVoteItem> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsVoteItem> list = listPage(hql.toString(), start, pageSize,
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
			if (queryMap.get("isValid") != null && queryMap.get("isValid") != "") {
				hql.append(" and isValid = :isValid");
				paramMap.put("isValid", queryMap.get("isValid").toString());
			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}

	@Override
	public Integer findItemByThemeId(String themeId) {
		String hql = "from CmsVoteItem where theme_id ='" + themeId + "'";
		List<CmsVoteItem> item = list(hql);
		if(!item.isEmpty()){
			return item.size();
		}
		return 0;
	}

	@Override
	public List<CmsVoteItem> findListByThemeId(String themeId) {
		String hql = "from CmsVoteItem where theme_id ='" + themeId + "'";
		List<CmsVoteItem> item = list(hql);
		if(!item.isEmpty()){
			return item;
		}
		return null;
	}
	@Override
	public List<CmsVoteTheme> getVoteByPagesize(Integer pageSize, Integer pageNo) {
		StringBuilder hql = new StringBuilder("from CmsVoteTheme cvt where cvt.isValid=:isValid order by cvt.sort asc");
		Map param = new HashMap();
		param.put("isValid","0");
		return listPage(hql.toString(), pageNo, pageSize, param);
	}

	@Override
	public List<CmsVoteTheme> getAllValidMain() {
		String hql = "from CmsVoteTheme cvt where cvt.isValid=:isValid order by cvt.sort asc";
		Map param = new HashMap();
		param.put("isValid","0");
		return list(hql.toString(),param);
	}

	@Override
	public List<CmsVoteItem> getValidListOnlySelectByMainId(String themeId) {
		String hql = "from CmsVoteItem cvi where cvi.theme_id =:themeId and cvi.item_type in(:item_type) order by cvi.sort asc";
		Map mp = new HashMap();
		mp.put("themeId", themeId);
		mp.put("vote_type", new Integer[]{0,1});
		return list(hql.toString(),mp);
	}

	@Override
	public List<CmsVoteItem> getValidListByMainId(String themeId) {
		String hql = "from CmsVoteItem cvi where cvi.theme_id =:themeId order by cvi.sort asc";
		Map mp = new HashMap();
		mp.put("themeId", themeId);
		return list(hql.toString(),mp);
	}
	
	@Override
	public List<CmsVoteItem> getValidListBythemeId(String themeId) throws Exception{
		String hql = "from CmsVoteItem cvi where cvi.theme_id =:themeId " +
				" and cvi.isDelete=:isDelete order by cvi.sort asc";
		Map mp = new HashMap();
		mp.put("themeId", themeId);
		//mp.put("is_valid", "0");
		mp.put("isDelete", "0");
		
		return list(hql.toString(),mp);
	}
}
