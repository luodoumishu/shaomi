package com.xjj.ws.distribute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.service.impl.BaseService;
import com.xjj.ws.distribute.dao.Zorganize2Dao;
import com.xjj.ws.distribute.model.Zorganize2;

@Service("zorganize2Service")
public class Zorganize2Service extends BaseService<Zorganize2, String> {
	
	@Autowired
	@Qualifier("zorganize2Dao")
	private Zorganize2Dao zorganize2Dao;

	@Autowired
	@Qualifier("zorganize2Dao")
	@Override
	public void setBaseDao(IBaseDao<Zorganize2, String> baseDao) {
		this.baseDao = zorganize2Dao;
	}

}
