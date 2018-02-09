package com.xjj.cms.base.service;


import java.util.Map;

import com.xjj.cms.base.model.CmsAffix;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

/**
 * 文章附件service接口
 * <p>
 * @author yeyunfeng 2014-9-2 下午3:25:09 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
public interface ICmsAffixService extends IBaseService<CmsAffix, String>
{

	/**
	 * 根据查询条件
	 * @author yeyunfeng 2014-9-2  下午3:16:44
	 * @param filtersMap
	 * @return
	 * @throws Exception
	 *
	 */
	public Page<CmsAffix> query(Map<String, Object> filtersMap)throws Exception;
	
}
