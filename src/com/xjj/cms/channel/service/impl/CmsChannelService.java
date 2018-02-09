package com.xjj.cms.channel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.channel.dao.ICmsChannelDao;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.channel.service.ICmsChannelService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("CmsChannelService")
public class CmsChannelService extends BaseService<CmsChannel, String> implements ICmsChannelService{
	@Autowired
	@Qualifier("CmsChannelDao")
	private ICmsChannelDao iCmsChannelDao;
	
	@Autowired
    @Qualifier("CmsChannelDao")
	@Override
	public void setBaseDao(IBaseDao<CmsChannel, String> baseDao) {
		this.baseDao = iCmsChannelDao;
	}

	@Override
	public Page<CmsChannel> query(Map<String, Object> filtersMap) {
		 //开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		//进行条件查询
		List<CmsChannel> items = iCmsChannelDao.query(skip,pageSize,queryMap, " order by sortno");
		//获取总记录数
		int total =  iCmsChannelDao.total(queryMap);
		//构造返回对象page
		Page<CmsChannel> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

	@Override
	public List<CmsChannel> listAllByOrgRootId(String orgRootId) {
			return iCmsChannelDao.listAllByOrgRootId(orgRootId);
	}

	@Override
	public boolean checkExistChildOrg(String orgId) {
		return iCmsChannelDao.checkExistChildOrg(orgId);
	}

	@Override
	public List<CmsChannelItem> findCmsChannelItemByChannelId(String channelId) {
		return iCmsChannelDao.findCmsChannelItemByChannelId(channelId);
	}
	@Override
	public CmsChannel findByChlCode(String chlCode) {
		// TODO Auto-generated method stub
		return iCmsChannelDao.findByChlCode(chlCode);
	}

	@Override
	public List<CmsChannel> findByChildChlById(String id) {
		// TODO Auto-generated method stub
		return iCmsChannelDao.findByChildChlById(id);
	}


}
