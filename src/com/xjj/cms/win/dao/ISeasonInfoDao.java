package com.xjj.cms.win.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.win.model.SeasonInfo;
import com.xjj.framework.core.dao.IBaseDao;

public interface ISeasonInfoDao extends IBaseDao<SeasonInfo, String>{

	List<SeasonInfo> query(int skip, int pageSize,
			Map<String, Object> queryMap, String string);

	int total(Map<String, Object> queryMap);

}
