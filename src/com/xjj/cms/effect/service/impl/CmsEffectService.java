package com.xjj.cms.effect.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.cms.effect.model.CmsEffect;
import com.xjj.cms.effect.service.ICmsEffectService;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.FjsqUtil;
import com.xjj.cms.effect.dao.ICmsEffectDao;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;
import com.xjj.jdk17.utils.StringUtil;

/**
 * 特效service
 * <p>
 * 
 * @author yeyunfeng 2015年6月10日 下午7:45:23
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年6月10日
 * @modify by reason:{方法名}:{原因}
 */
@Service("cmsEffectService")
public class CmsEffectService extends BaseService<CmsEffect, String> implements
		ICmsEffectService {

	@Autowired
	@Qualifier("cmsEffectDao")
	private ICmsEffectDao cmsEffectDao;

	@Override
	public void setBaseDao(IBaseDao<CmsEffect, String> baseDao) {
		this.baseDao = cmsEffectDao;
	}

	@Override
	public Page<CmsEffect> query(Map<String, Object> filtersMap)
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

		if (CmsCC.ENABLED_MENU_FJSQ) {
			String orgIds = "";
			try {
				String sx_orgId = FjsqUtil.getInstance()
						.getOrg4MemuFjsqByCurrOrg();
				orgIds = FjsqUtil.getInstance().getChildOrgIds(sx_orgId, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!StringUtil.isEmpty(orgIds)) {
				queryMap.put("orgIds", orgIds);
			}
		}
		List<CmsEffect> items = cmsEffectDao.query(skip, pageSize, queryMap,
				" order by sortNo,endModifyTime desc");
		// 获取总记录数
		int total = cmsEffectDao.total(queryMap);
		// 构造返回对象page
		Page<CmsEffect> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}

	@Override
	public void deleteById(String id) throws Exception {
		String hql = "delete CmsEffect ce where ce.id='" + id + "'";
		cmsEffectDao.execteBulk(hql, null);
	}

	@Override
	public Page<CmsEffect> query4Remote(Map<String, Object> filtersMap)
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

		if (CmsCC.ENABLED_MENU_FJSQ) { // 栏目分级授权开启
			String pyName = filtersMap.get("fjsq_orgCode").toString();
			String orgIds = FjsqUtil.getInstance().getChildOrgIdsByPyName(
					pyName);
			if (!StringUtil.isEmpty(orgIds)) {
				queryMap.put("orgIds", orgIds);
			}
		}
		List<CmsEffect> items = cmsEffectDao.query(skip, pageSize, queryMap,
				" order by sortNo,endModifyTime desc");
		// 获取总记录数
		int total = cmsEffectDao.total(queryMap);
		// 构造返回对象page
		Page<CmsEffect> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}

}
