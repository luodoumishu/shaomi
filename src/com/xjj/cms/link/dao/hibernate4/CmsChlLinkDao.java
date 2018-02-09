package com.xjj.cms.link.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj.cms.link.dao.ICmsChlLinkDao;
import com.xjj.cms.link.model.CmsChlLink;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;


@Repository("cmsChlLinkDao")
public class CmsChlLinkDao extends BaseHibernateDao<CmsChlLink, String> implements ICmsChlLinkDao{

	@Override
	public List<CmsChlLink> findAllLinkByChannelId(String channelId) {
		String hql = "from CmsChlLink where channelId = '" + channelId +"' order by sort";
		return list(hql);
	}

	@Override
	public List<CmsChlLink> findAll() {
		return listAll();
	}
	
	@Override
	public List<CmsChlLink> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsChlLink> list = listPage(hql.toString(), start, pageSize, queryMap);
		//文件下有多少个附件信息；
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
			if (queryMap.get("channelId") != null && queryMap.get("channelId") != "") {
				hql.append(" and channelId = :channelId");
				paramMap.put("channelId",queryMap.get("channelId"));
			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}

	@Override
	public boolean exitLinkInChannelLink(String id, String channelId) {
		String hql = "from CmsChlLink ci where ci.linkId ='" + id +"' and ci.channelId = '"+channelId+"'";
		List<CmsChlLink> itemList  = list(hql);
		if (null == itemList || itemList.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public int countByChannelId(String channelId) {
		List<CmsChlLink> list = findAllLinkByChannelId(channelId);
		return list.size();
	}

	@Override
	public List<CmsChlLink> findAllByLinkId(String linkId) {
		String hql = "from CmsChlLink where linkId = '" + linkId +"'";
		return list(hql);
	}

}
