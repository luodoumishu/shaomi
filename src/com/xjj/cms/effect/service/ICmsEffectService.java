package com.xjj.cms.effect.service;

import java.util.Map;

import com.xjj.cms.effect.model.CmsEffect;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

/**
 * 特效service接口
 * <p>
 * 
 * @author yeyunfeng 2015年6月10日 下午7:41:27
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年6月10日
 * @modify by reason:{方法名}:{原因}
 */
public interface ICmsEffectService extends IBaseService<CmsEffect, String> {

	/**
	 * 根据条件查询
	 * @author yeyunfeng 2015年6月10日  下午8:26:00
	 * @param filtersMap
	 * @return
	 *
	 */
	Page<CmsEffect> query(Map<String, Object> filtersMap)throws Exception;

	void deleteById (String id) throws Exception;

	/**
	 * 查询首页上显示飘窗
	 * @author yeyunfeng 2015年6月15日  下午7:17:16
	 * @param filtersMap
	 * @return
	 *
	 */
	Page<CmsEffect> query4Remote(Map<String, Object> filtersMap)throws Exception;
}
