package com.xjj._extensions.roleUserPer.service;

import java.util.List;
import java.util.Map;

import com.xjj._extensions.roleUserPer.model.Zrole;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface IZroleService  extends IBaseService<Zrole, String>{

	public Page<Zrole> query(Map<String, Object> filtersMap);
	
	public List<Zrole> query(Map<String, Object> queryMap, String subHql );
}
