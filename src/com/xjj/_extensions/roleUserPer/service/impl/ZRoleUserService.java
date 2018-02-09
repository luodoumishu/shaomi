package com.xjj._extensions.roleUserPer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.roleUserPer.dao.IZRoleUserDao;
import com.xjj._extensions.roleUserPer.model.ZRoleUser;
import com.xjj._extensions.roleUserPer.service.IZRoleUserService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.service.impl.BaseService;

@Service("ZRoleUserService")
public class ZRoleUserService extends BaseService<ZRoleUser, String> implements IZRoleUserService{
	
	@Autowired
    @Qualifier("ZRoleUserDao")
    private IZRoleUserDao dao;

    @Autowired
    @Qualifier("ZRoleUserDao")
    @Override
	public void setBaseDao(IBaseDao<ZRoleUser, String> baseDao) {
		this.baseDao = dao;
	}

	public List<ZRoleUser> query(int start, int pageSize,
			ZRoleUser zRoleUser) {
		return dao.query(start, pageSize, zRoleUser ,null);
	}
	public void deleteByModel(ZRoleUser zRoleUser) {
		if(zRoleUser!=null && ((zRoleUser.getUserId()!=null && zRoleUser.getUserId()!="")||(zRoleUser.getRoleId()!=null && zRoleUser.getRoleId()!=""))){
			List<ZRoleUser> zRoleUsers= this.query(-1, -1, zRoleUser);
			for(ZRoleUser roleUser:zRoleUsers){
				dao.deleteByPK(roleUser.getId());
			}
		}
	}
}
