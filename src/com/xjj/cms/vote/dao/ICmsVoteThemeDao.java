package com.xjj.cms.vote.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsVoteThemeDao extends IBaseDao<CmsVoteTheme, String>{

	List<CmsVoteTheme> query(int skip, int pageSize,
			Map<String, Object> queryMap, String string);

	int total(Map<String, Object> queryMap);
	
	CmsVoteTheme getById(String themeId)throws Exception;
}
