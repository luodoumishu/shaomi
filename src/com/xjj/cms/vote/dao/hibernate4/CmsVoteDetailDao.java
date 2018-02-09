package com.xjj.cms.vote.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.vote.dao.ICmsVoteDetailDao;
import com.xjj.cms.vote.model.CmsVoteDetail;
import com.xjj.cms.vote.model.CmsVoteItem;
import com.xjj.cms.vote.web.controller.CmsVoteDetailController;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("cmsVoteDetailDao")
public class CmsVoteDetailDao extends BaseHibernateDao<CmsVoteDetail, String> implements ICmsVoteDetailDao {
	@Override
	public List<CmsVoteDetail> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsVoteDetail> list = listPage(hql.toString(), start, pageSize,
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
	public void creatByDetailName(String[] detailNames, CmsVoteItem item) throws Exception {
		if(detailNames.length != 0){
			for (int i = 0; i < detailNames.length; i++) {
				String detailName = detailNames[i];
				if(detailName != null && detailName !=""){
					CmsVoteDetail detail = new CmsVoteDetail();
					detail.setItem_id(item.getId());
					CMSBaseController<CmsVoteDetail> controller = new CmsVoteDetailController();
					controller.setCmsBaseModel(detail, BaseConstant.ADD);
					detail.setDetail_name(detailName);
					String hql = "from CmsVoteDetail where item_id='"+ item.getId()+"'";
					Integer sort = 1;
					if(!list(hql).isEmpty()){
						sort = list(hql).size() + 1;
					}
					detail.setSort(sort);
					detail.save();
				}
			}
		}
	}

	@Override
	public List<CmsVoteDetail> findListByItemId(String itemId) {
		String hql = "from CmsVoteDetail where item_id ='" + itemId + "'";
		List<CmsVoteDetail> details = list(hql);
		if(!details.isEmpty()){
			return details;
		}
		return null;
	}

	@Override
	public void deleteAll(String itemId) {
		String hql = "from CmsVoteDetail where item_id ='" + itemId + "'";
		List<CmsVoteDetail> details = list(hql);
		if(!details.isEmpty()){
			for (int i = 0; i < details.size(); i++) {
				CmsVoteDetail detail = details.get(i);
				detail.delete();
			}
		}
	}

	@Override
	public List<CmsVoteDetail> getValidDetailListByItemId(String itemId) {
		String hql = "from CmsVoteDetail cvd where cvd.item_id =:itemId order by cvd.sort asc";
		Map mp = new HashMap();
		mp.put("itemId", itemId);
		return list(hql.toString(),mp);
	}
	
	@Override
	public List<CmsVoteDetail> getValidtByItemId(String itemId) throws Exception{
		String hql = "from CmsVoteDetail cvd where cvd.item_id =:itemId " +
				" and cvd.isDelete ='0' order by cvd.sort asc";
		Map mp = new HashMap();
		mp.put("itemId", itemId);
		return list(hql.toString(),mp);
	}
}
