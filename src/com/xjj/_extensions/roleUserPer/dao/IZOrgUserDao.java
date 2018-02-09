package com.xjj._extensions.roleUserPer.dao;

import java.util.List;

import com.xjj._extensions.roleUserPer.model.ZOrgUser;
import com.xjj.framework.core.dao.IBaseDao;

public interface IZOrgUserDao extends IBaseDao<ZOrgUser, String> {

    public List<ZOrgUser> query(int start, int pageSize, ZOrgUser zOrgUser,
            String orderby);
    
	public ZOrgUser checkExistZOrgUser(String orgId, String userId);
	public ZOrgUser checkExistZOrgUser(String userId);
}
