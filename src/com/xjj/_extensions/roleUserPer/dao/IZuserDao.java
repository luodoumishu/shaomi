package com.xjj._extensions.roleUserPer.dao;

import java.util.List;
import java.util.Map;

import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj.framework.core.dao.IBaseDao;

public interface IZuserDao extends IBaseDao<Zuser, String> {
	
	public List<Zuser> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql);

	public int total(Map<String, Object> queryMap);
	
	public List<Zuser> query(int start, int pageSize, Zuser zuser, String orderby);
	
	public Zuser getObjectById(String id);
	
	public List<Zuser> getListByOrgId(List<String> orgIds);

	public List<Zuser> getListByOrgidFilterstr(String orgid, String filterStr);

	public void updateAccount(String id,String password);

	public String getAccountById(String curUserId);
	
	public Map getUserStatus(String account);
	
}

