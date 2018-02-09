package com.xjj.cms.effect.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.effect.model.CmsEffect;
import com.xjj.framework.core.dao.IBaseDao;

/**
 * 特效dao接口
 * <p>
 * 
 * @author yeyunfeng 2015年6月10日 下午7:27:19
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年6月10日
 * @modify by reason:{方法名}:{原因}
 */
public interface ICmsEffectDao extends IBaseDao<CmsEffect, String> {

	List<CmsEffect> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql);

	int total(Map<String, Object> queryMap);

}
