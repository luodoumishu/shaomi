package com.xjj.cms.menu.service;

import java.util.Map;

import com.xjj.cms.menu.model.CmsMenuOrg;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsMenuOrgService extends IBaseService<CmsMenuOrg, String>{

	Page<CmsMenuOrg> query(Map<String, Object> filtersMap) throws Exception;

	/**
	 * 保存栏目分级授权信息
	 * @author yeyunfeng 2015年5月18日  下午4:10:20
	 * @param menuIds
	 * @param orgId
	 * @throws Exception
	 *
	 */
	public void save(String menuIds,String orgId) throws Exception;
}
