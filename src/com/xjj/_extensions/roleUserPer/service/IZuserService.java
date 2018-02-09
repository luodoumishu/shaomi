package com.xjj._extensions.roleUserPer.service;

import java.util.List;
import java.util.Map;

import com.xjj._extensions.adrsbook.web.controller.AddressContactVo;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface IZuserService extends IBaseService<Zuser, String> {
	public List<Zuser> query(int start, int pageSize, Zuser zuser);
	
	public Page<Zuser> query(Map<String, Object> filtersMap);
	
	public Zuser getObjectById(String id);
	
	public List<Zuser> getListByOrgId(String orgId,boolean includeSubOrg);

	public Zuser getZuserByAccount(String account);

	/**
	 * yeyunfeng 2015-07-27
	 * @param userIds
	 * @return
	 */
	public List<Zuser> getListByuserIds(String userIds);

	public List<AddressContactVo> getUserOrgname(String assignOrgId,
			String filterStr);

	public List<Zuser> queryList(Map<String, Object> filtersMap);

	public void updataAccount(String id,String password);
	
	public void updateFailuretimes(String account, String status);
	
	public void updateStatus(String id, String status);
	
	public Map getUserStatus(String account);
}
