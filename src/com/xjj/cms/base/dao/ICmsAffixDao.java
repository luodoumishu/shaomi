package com.xjj.cms.base.dao;


import java.util.List;
import java.util.Map;

import com.xjj.cms.base.model.CmsAffix;
import com.xjj.framework.core.dao.IBaseDao;


/**
 * 文章附件dao接口
 * <p>
 * @author yeyunfeng 2014-9-5 下午8:12:17 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-5
 * @modify by reason:{方法名}:{原因}
 */
public interface ICmsAffixDao extends IBaseDao<CmsAffix, String>{

    List<CmsAffix> query(int start, int pageSize, Map<String, Object> queryMap,
            String subHql);

    int total(Map<String, Object> queryMap);
    /**
     * 根据modelId查询附件
     * @param modelId
     * @return List<CmsAffix>
     * @author wuqlin
     *  上午9:14:13
     */
	List<CmsAffix> findAffixByModelId(String modelId);
    
}
