package com.xjj.cms.win.service;

import java.util.Map;

import com.xjj.cms.win.model.SeasonInfo;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ISeasonInfoService extends IBaseService<SeasonInfo, String>{

	Page<SeasonInfo> query(Map<String, Object> filtersMap);

}
