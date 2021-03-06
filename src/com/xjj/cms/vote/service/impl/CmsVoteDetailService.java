package com.xjj.cms.vote.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.vote.dao.ICmsVoteDetailDao;
import com.xjj.cms.vote.model.CmsVoteDetail;
import com.xjj.cms.vote.service.ICmsVoteDetailService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("cmsVoteDetailService")
public class CmsVoteDetailService extends BaseService<CmsVoteDetail, String> implements ICmsVoteDetailService {

	@Autowired
	@Qualifier("cmsVoteDetailDao")
	private ICmsVoteDetailDao cmsVoteDetailDao;

	@Autowired
	@Qualifier("cmsVoteDetailDao")
	@Override
	public void setBaseDao(IBaseDao<CmsVoteDetail, String> baseDao) {
		this.baseDao = cmsVoteDetailDao;
	}

	@Override
	public Page<CmsVoteDetail> query(Map<String, Object> filtersMap) {
		// 开始查询下标
		int skip = filtersMap.get("skip") == null ? 1 : (Integer) filtersMap
				.get("skip");
		// 每页显示条数
		int pageSize = filtersMap.get("pageSize") == null ? 20
				: (Integer) filtersMap.get("pageSize");
		// 获取查询条件
		@SuppressWarnings("unchecked")
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap
				.get("queryObj");
		// 进行条件查询
		List<CmsVoteDetail> items = cmsVoteDetailDao.query(skip, pageSize,
				queryMap, " order by sort");
		// 获取总记录数
		int total = cmsVoteDetailDao.total(queryMap);
		// 构造返回对象page
		Page<CmsVoteDetail> page = PageUtil.getPage(skip, pageSize, items, total);

		return page;
	}

	@Override
	public List<CmsVoteDetail> getValidDetailListByItemId(String itemId) {
		return cmsVoteDetailDao.getValidDetailListByItemId(itemId);
	}

}
