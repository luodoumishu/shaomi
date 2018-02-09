package com.xjj.cms.demo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.demo.dao.ICmsTestDao;
import com.xjj.cms.demo.model.CmsTest;
import com.xjj.cms.demo.service.ICmsTestService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.service.impl.BaseService;

/**
 * @author yeyunfeng 2014-9-2 下午3:25:40
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
@Service("cmsTestService")
public class CmsTestService extends BaseService<CmsTest, String>
		implements ICmsTestService {
	
	@Autowired
	@Qualifier("cmsTestDao")
	private ICmsTestDao cmsTestDao;

	@Override
	public void setBaseDao(IBaseDao<CmsTest, String> baseDao) {
		this.baseDao = cmsTestDao;
	}
}
