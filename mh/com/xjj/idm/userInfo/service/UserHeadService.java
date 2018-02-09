package com.xjj.idm.userInfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.service.impl.BaseService;
import com.xjj.idm.userInfo.dao.UserHeadDao;
import com.xjj.idm.userInfo.model.UserHead;

@Service("userHeadService")
public class UserHeadService extends BaseService<UserHead, Long>{
	
	@Autowired
	@Qualifier("userHeadDao")
	private UserHeadDao userHeadDao;
	
	@Autowired
	@Qualifier("userHeadDao")
	@Override
	public void setBaseDao(IBaseDao<UserHead, Long> baseDao) {
		this.baseDao = userHeadDao;
	}

	public UserHead findByUserAccount(String account) {
		return userHeadDao.findByUserAccount(account);
	}

	public void deleteByUserAccount(String account) {
		userHeadDao.deleteByUserAccount(account);
	}

	public void saveOrUpdateUserHead(String account, String picturePath) {
		UserHead head = userHeadDao.findByUserAccount(account);
		if (head != null) {
			head.setPicturePath(picturePath);
			userHeadDao.updateUserHead(head);
		} else {
			userHeadDao.saveUserHead(new UserHead(account, picturePath));
		}
	}
}
