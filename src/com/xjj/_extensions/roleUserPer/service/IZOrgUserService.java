package com.xjj._extensions.roleUserPer.service;

import java.util.List;

import com.xjj._extensions.roleUserPer.model.ZOrgUser;
import com.xjj.framework.core.service.IBaseService;

public interface IZOrgUserService extends IBaseService<ZOrgUser, String>{

	public List<ZOrgUser> query(int start, int pageSize, ZOrgUser zOrgUser );
	
	public void deleteByModel(ZOrgUser zOrgUser);
	
	public ZOrgUser checkExistZOrgUser(String orgId, String userId);
	public ZOrgUser checkExistZOrgUser(String userId);
	
}
