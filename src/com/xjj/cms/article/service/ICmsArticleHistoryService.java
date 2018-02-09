package com.xjj.cms.article.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsArticleHistoryService extends IBaseService<CmsArticleHistory, String>{

	Page<CmsArticleHistory> query(Map<String, Object> filtersMap) throws Exception;

	Page<CmsArticleHistory> getALLArticlesByMenuIds(String[] listMenuId,
			Map<String, Object> map) throws Exception;
	
	CmsArticle history2article(CmsArticleHistory model);

	/**
     * 阅读数加1
     * @author fengjunkong 2014-9-4  下午5:00:05
     * @param queryMap
     * @return
     * @throws Exception
     *
     */
    public void saveVisitNumAddOne(CmsArticleHistory cah) throws Exception;

	List<CmsArticleHistory> getOrgArticlesByMenuId(String menuId, String orgCode) throws Exception;

	List<CmsArticleHistory> getALLArticlesByMenuId(String menuId) throws Exception;

	Page<CmsArticleHistory> getALLArticlesByMenuId(String menuId,
			Map<String, Object> map) throws Exception;

	List<CmsArticleHistory> findArticleBySearch(String searchValue,String orgCode) throws Exception;
	/**
	 * 获取部门发文量
	 * @param org
	 * @param endDate 
	 * @param startDate 
	 * @return
	 */
	Integer getArticleNumByOrg(Zorganize org, String startDate, String endDate);

	
}
