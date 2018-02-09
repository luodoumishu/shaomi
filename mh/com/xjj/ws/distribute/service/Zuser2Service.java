package com.xjj.ws.distribute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.service.impl.BaseService;
import com.xjj.ws.distribute.dao.Zuser2Dao;
import com.xjj.ws.distribute.model.Zuser2;

@Service("zuser2Service")
public class Zuser2Service extends BaseService<Zuser2, String> {
	
	@Autowired
	@Qualifier("zuser2Dao")
	private Zuser2Dao zuser2Dao;

	@Autowired
	@Qualifier("zuser2Dao")
	@Override
	public void setBaseDao(IBaseDao<Zuser2, String> baseDao) {
		this.baseDao = zuser2Dao;
	}

}
