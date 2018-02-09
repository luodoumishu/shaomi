package com.xjj.cms.win.service;

import java.util.Map;

import com.xjj.cms.win.model.LeagueInfo;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ILeagueInfoService extends IBaseService<LeagueInfo, String>{

	Page<LeagueInfo> query(Map<String, Object> filtersMap) throws Exception;

}
