package com.xjj.cms.win.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.win.model.LeagueInfo;
import com.xjj.framework.core.dao.IBaseDao;

public interface ILeagueInfoDao extends IBaseDao<LeagueInfo, String>{

	List<LeagueInfo> query(int skip, int pageSize,
			Map<String, Object> queryMap, String string);

	int total(Map<String, Object> queryMap);

}
