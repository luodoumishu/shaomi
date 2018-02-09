package com.xjj.cms.win.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.win.dao.ILeagueInfoDao;
import com.xjj.cms.win.dao.ISeasonInfoDao;
import com.xjj.cms.win.model.LeagueInfo;
import com.xjj.cms.win.model.SeasonInfo;
import com.xjj.cms.win.service.ISeasonInfoService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("SeasonInfoService")
public class SeasonInfoService extends BaseService<SeasonInfo, String> implements ISeasonInfoService{

	@Autowired
	@Qualifier("SeasonInfoDao")
	private ISeasonInfoDao seasonInfoDao;
	
	@Autowired
	@Qualifier("SeasonInfoDao")
	@Override
	public void setBaseDao(IBaseDao<SeasonInfo, String> baseDao) {
		this.baseDao = seasonInfoDao;
	}

	@Override
	public Page<SeasonInfo> query(Map<String, Object> filtersMap) {
		//开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		//进行条件查询
		List<SeasonInfo> items = seasonInfoDao.query(skip,pageSize,queryMap, " order by sort");
		//获取总记录数
		int total =  seasonInfoDao.total(queryMap);
		//构造返回对象page
		Page<SeasonInfo> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

}
