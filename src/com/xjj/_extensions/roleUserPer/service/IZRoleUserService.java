package com.xjj._extensions.roleUserPer.service;

import java.util.List;

import com.xjj._extensions.roleUserPer.model.ZRoleUser;
import com.xjj.framework.core.service.IBaseService;

public interface IZRoleUserService extends IBaseService<ZRoleUser, String>{

	public List<ZRoleUser> query(int start, int pageSize, ZRoleUser zRoleUser );
	
	public void deleteByModel(ZRoleUser zRoleUser);
	
}
