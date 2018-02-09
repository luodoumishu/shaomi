package com.xjj.cms.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.template.dao.ICmsTemplateTypeDao;
import com.xjj.cms.template.model.CmsTemplateType;
import com.xjj.cms.template.service.ICmsTemplateTypeService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.service.impl.BaseService;


/**
 * 模板类型service
 * <p>
 * @author yeyunfeng 2015年5月28日 下午4:01:51 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年5月28日
 * @modify by reason:{方法名}:{原因}
 */
@Service("cmsTemplateTypeService")
public class CmsTemplateTypeService extends BaseService<CmsTemplateType, String>
		implements ICmsTemplateTypeService {
	
	@Autowired
	@Qualifier("cmsTemplateTypeDao")
	private ICmsTemplateTypeDao cmsTemplateTypeDao;

	@Override
	public void setBaseDao(IBaseDao<CmsTemplateType, String> baseDao) {
		this.baseDao = cmsTemplateTypeDao;
	}
}
