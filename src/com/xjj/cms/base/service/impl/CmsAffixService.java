package com.xjj.cms.base.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.base.dao.ICmsAffixDao;
import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.base.service.ICmsAffixService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

/**
 * 文章s附件ervice接口实现
 * <p>
 * 
 * @author yeyunfeng 2014-9-2 下午3:25:40
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
@Service("cmsAffixService")
public class CmsAffixService extends BaseService<CmsAffix, String>
		implements ICmsAffixService {
	@Autowired
	@Qualifier("cmsAffixDao")
	private ICmsAffixDao cmsAffixDao;

	@Autowired
	@Qualifier("cmsAffixDao")
	@Override
	public void setBaseDao(IBaseDao<CmsAffix, String> baseDao) {
		this.baseDao = cmsAffixDao;
	}

	@Override
	public Page<CmsAffix> query(Map<String, Object> filtersMap)
			throws Exception {
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

		List<CmsAffix> items = cmsAffixDao.query(skip, pageSize, queryMap,
				" order by createTime,endModifyTime desc");
		// 获取总记录数
		int total = cmsAffixDao.total(queryMap);
		// 构造返回对象page
		Page<CmsAffix> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}
}
