package com.xjj._extensions.roleUserPer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.roleUserPer.dao.IZOrgUserDao;
import com.xjj._extensions.roleUserPer.model.ZOrgUser;
import com.xjj._extensions.roleUserPer.service.IZOrgUserService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.service.impl.BaseService;

@Service("ZOrgUserService")
public class ZOrgUserService extends BaseService<ZOrgUser, String> implements IZOrgUserService{

	@Autowired
    @Qualifier("ZOrgUserDao")
    private IZOrgUserDao dao;

    @Autowired
    @Qualifier("ZOrgUserDao")
    @Override
	public void setBaseDao(IBaseDao<ZOrgUser, String> baseDao) {
		this.baseDao = dao;
	}

	public List<ZOrgUser> query(int start, int pageSize,
			ZOrgUser zOrgUser) {
		return dao.query(start, pageSize, zOrgUser ,null);
	}
	public void deleteByModel(ZOrgUser zOrgUser) {
		if(zOrgUser!=null && ((zOrgUser.getUserId()!=null && zOrgUser.getUserId()!="")||(zOrgUser.getOrgId()!=null && zOrgUser.getOrgId()!=""))){
			List<ZOrgUser> zOrgUsers= this.query(-1, -1, zOrgUser);
			for(ZOrgUser orgUser:zOrgUsers){
				dao.deleteByPK(orgUser.getId());
			}
		}
	}

	@Override
	public ZOrgUser checkExistZOrgUser(String orgId, String userId) {
		return this.dao.checkExistZOrgUser(orgId, userId);
	}

	@Override
	public ZOrgUser checkExistZOrgUser(String userId) {
		return this.dao.checkExistZOrgUser(userId);
	}
	
}
