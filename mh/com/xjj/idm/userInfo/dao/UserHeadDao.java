package com.xjj.idm.userInfo.dao;

import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.idm.userInfo.model.UserHead;

public interface UserHeadDao extends IBaseDao<UserHead, Long> {

	public UserHead findByUserAccount(String account);
	public void deleteByUserAccount(String account);
	public void saveUserHead(UserHead head);
	public void updateUserHead(UserHead head);

}