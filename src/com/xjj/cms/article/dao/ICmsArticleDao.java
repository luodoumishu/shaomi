package com.xjj.cms.article.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.framework.core.dao.IBaseDao;

/**
 * 文章dao接口
 * <p>
 * @author yeyunfeng 2014-9-2 下午3:24:45 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
public interface ICmsArticleDao extends IBaseDao<CmsArticle, String>{

    List<CmsArticle> query(int start, int pageSize, Map<String, Object> queryMap,
            String subHql);

    int total(Map<String, Object> queryMap);
 

    /**
     * 构造根据hql查询
     * @author yeyunfeng 2014-9-3  下午5:40:46
     * @param start
     * @param pageSize
     * @param queryMap
     * @param subhql
     * @return
     * @throws Exception
     *
     */
    public List<CmsArticle> query(int start, int pageSize,String subhql,Map<String, Object> queryMap)throws Exception;
    
    /**
     * 构造hql查询总条数
     * @author yeyunfeng 2014-9-3  下午5:39:05
     * @param queryMap
     * @return
     * @throws Exception
     *
     */
    public int hql_total(Map<String, Object> queryMap) throws Exception;
    /**
     * 阅读数加1
     * @author fengjunkong 2014-9-4  下午5:00:05
     * @param queryMap
     * @return
     * @throws Exception
     *
     */
    public void saveVisitNumAddOne(CmsArticle ca) throws Exception;
    
}
