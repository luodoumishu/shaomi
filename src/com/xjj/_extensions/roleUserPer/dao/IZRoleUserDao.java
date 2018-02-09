package com.xjj._extensions.roleUserPer.dao;

import java.util.List;

import com.xjj._extensions.roleUserPer.model.ZRoleUser;
import com.xjj.framework.core.dao.IBaseDao;

public interface IZRoleUserDao extends IBaseDao<ZRoleUser, String> {
	
	public List<ZRoleUser> query(int start, int pageSize, ZRoleUser zRoleUser, String orderby);
}
