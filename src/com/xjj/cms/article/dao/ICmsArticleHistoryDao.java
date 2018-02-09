package com.xjj.cms.article.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsArticleHistoryDao extends IBaseDao<CmsArticleHistory, String>{

	List<CmsArticleHistory> query(int skip, int pageSize,
			Map<String, Object> queryMap, String string) throws Exception;

	int total(Map<String, Object> queryMap) throws Exception;

	void saveVisitNumAddOne(CmsArticleHistory cah);

	Integer getArticleNumByOrg(Zorganize org, String startDate, String endDate);

}
