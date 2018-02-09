package com.xjj.cms.menu.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.menu.model.CmsMenuOrg;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsMenuOrgDao extends IBaseDao<CmsMenuOrg, String> {

	List<CmsMenuOrg> query(int skip, int pageSize,
			Map<String, Object> queryMap, String string);

	int total(Map<String, Object> queryMap);

	void deleteByOrgId(String orgId)throws Exception;
	
	
	/**
	 * 根据部门id,递归该部门，直到找到出现在CmsMenuOrg表中的部门
	 * @author yeyunfeng 2015年5月21日  上午10:53:09
	 * @param orgId
	 * @return
	 * @throws Exception
	 *
	 */
	public String getByLoopOrgId(String orgId)throws Exception;
}
