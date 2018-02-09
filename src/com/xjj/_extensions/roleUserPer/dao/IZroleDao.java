package com.xjj._extensions.roleUserPer.dao;

import java.util.List;
import java.util.Map;

import com.xjj._extensions.roleUserPer.model.Zrole;
import com.xjj.framework.core.dao.IBaseDao;

public interface IZroleDao extends IBaseDao<Zrole, String> {

	public List<Zrole> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql);

	public int total(Map<String, Object> queryMap);
	
}
