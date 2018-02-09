package com.xjj._extensions.roleUserPer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.roleUserPer.dao.IZroleDao;
import com.xjj._extensions.roleUserPer.model.Zrole;
import com.xjj._extensions.roleUserPer.service.IZroleService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("ZroleService")
public class ZroleService extends BaseService<Zrole, String> implements IZroleService{
	
	@Autowired
    @Qualifier("ZroleDao")
    private IZroleDao dao;

    @Autowired
    @Qualifier("ZroleDao")
    @Override
	public void setBaseDao(IBaseDao<Zrole, String> baseDao) {
		this.baseDao = dao;
	}
	
	public Page<Zrole> query(Map<String, Object> filtersMap) {
		 //开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		
		//进行条件查询
		List<Zrole> items = dao.query(skip,pageSize,queryMap, " order by pri");
		//获取总记录数
		int total =  dao.total(queryMap);
		//构造返回对象page
		Page<Zrole> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

	@Override
	public List<Zrole> query(Map<String, Object> queryMap, String subHql) {
		return dao.query(-1 , -1, queryMap ,subHql);
	}
}
