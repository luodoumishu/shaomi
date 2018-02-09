package com.xjj.cms.article.service;

import java.util.List;
import java.util.Map;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

/**
 * 文章service接口
 * <p>
 * @author yeyunfeng 2014-9-2 下午3:25:09 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
public interface ICmsArticleService extends IBaseService<CmsArticle, String>
{

	/**
	 * 根据查询条件
	 * @author yeyunfeng 2014-9-2  下午3:16:44
	 * @param filtersMap
	 * @return
	 * @throws Exception
	 *
	 */
	public Page<CmsArticle> query(Map<String, Object> filtersMap)throws Exception;
	
	/**
	 * 获取一个栏目下的所有的文章（包括本身节点）
	 * @author yeyunfeng 2014-9-3  下午3:35:06
	 * @param menuId
	 * @param filtersMap 过滤条件
	 * @return
	 * @throws Exception
	 *
	 */
	public Page<CmsArticle> getALLArticlesByMenuId(String menuId,Map<String, Object> filtersMap)throws Exception;
	/**
     * 阅读数加1
     * @author fengjunkong 2014-9-4  下午5:00:05
     * @param queryMap
     * @return
     * @throws Exception
     *
     */
    public void saveVisitNumAddOne(CmsArticle ca) throws Exception;
	
	/**
	 * 获取一个栏目下的所有的文章（包括本身节点）
	 * @author fengjunkong 2014-9-5  下午5:35:06
	 * @param menuId[]
	 * @param filtersMap 过滤条件
	 * @return
	 * @throws Exception
	 *
	 */
	public Page<CmsArticle> getALLArticlesByMenuIds(String[] menuId,Map<String, Object> filtersMap)throws Exception;
	
	/**
	 * 根据用户栏目权限，显示文章
	 * @author yeyunfeng 2014-11-13  下午5:34:44
	 * @param menuId
	 * @param userId
	 * @param filtersMap
	 * @return
	 * @throws Exception
	 *
	 */
	public Page<CmsArticle> getByuser4List(String menuId,String userId,
			Map<String, Object> filtersMap) throws Exception;

	/**
	 * 获取一个栏目下的所有的文章（包括本身节点）
	 * @author wuqilin 2014-9-5  下午5:35:06
	 * @param menuId
	 * @return
	 * @throws Exception
	 *
	 */
	public List<CmsArticle> getALLArticlesByMenuId(String menuId) throws Exception;
	
	/**
	 * 分级授权下，根据部门拼音代码查询该下所有子部门对应的栏目文章
	 * @author yeyunfeng 2015年5月26日  下午6:33:44
	 * @param menuId
	 * @return
	 *
	 */
	public List<CmsArticle> getOrgArticlesByMenuId(String menuId,String pyCode) throws Exception;

	/**
	 * 查询改部门拼音下所有符合条件的文章
	 * @param searchValue
	 * @param org
	 * @return
	 * @throws Exception 
	 */
	public List<CmsArticle> findArticleBySearch(String searchValue, String org) throws Exception;
	/**
	 * 获取最新的8篇文章
	 * 
	 * @param menuId
	 * @return
	 * @throws Exception 
	 */
	public List<CmsArticleHistory> get8ArticleByMenuId(String menuId) throws Exception;
	
}
