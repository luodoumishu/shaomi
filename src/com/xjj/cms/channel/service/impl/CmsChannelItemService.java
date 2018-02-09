package com.xjj.cms.channel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.channel.dao.ICmsChannelItemDao;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("CmsChannelItemService")
public class CmsChannelItemService extends BaseService<CmsChannelItem, String> implements ICmsChannelItemService{
	@Autowired
	@Qualifier("CmsChannelItemDao")
	private ICmsChannelItemDao iCmsChannelItemDao;
	
	@Autowired
    @Qualifier("CmsChannelItemDao")
	@Override
	public void setBaseDao(IBaseDao<CmsChannelItem, String> baseDao) {
		this.baseDao = iCmsChannelItemDao;
	}
	
	@Override
	public Page<CmsChannelItem> query(Map<String, Object> filtersMap) {
		 //开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		//进行条件查询
		List<CmsChannelItem> items = iCmsChannelItemDao.queryByChannelId(skip,pageSize,queryMap, " order by sortno");
		//获取总记录数
		int total =  iCmsChannelItemDao.total(queryMap);
		//构造返回对象page
		Page<CmsChannelItem> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

	@Override
	public List<CmsMenu> findByMenuId(String menuId) {
		return iCmsChannelItemDao.findByMenuId(menuId);
	}

	@Override
	public boolean ifAdd(String menuId,String channelId) {
		return iCmsChannelItemDao.ifAdd(menuId,channelId);
	}

	@Override
	public List<CmsChannel> findByChannelId(String channelId) {
		return iCmsChannelItemDao.findByChannelId(channelId);
	}

	@Override
	public List<CmsChannelItem> findCmsChannelItem(String menuId,String channelId) {
		return iCmsChannelItemDao.findCmsChannelItem(menuId,channelId);
	}

	@Override
	public List<CmsMenu> findAllMenu() {
		return iCmsChannelItemDao.findAllMenu();
	}

	@Override
	public List<CmsMenu> child(String menuId) {
		return iCmsChannelItemDao.child(menuId);
	}

	@Override
	public List<CmsChannelItem> findAllItem(String channelId) {
		return  iCmsChannelItemDao.findAllItem(channelId);
	}

	@Override
	public int findCountByItem(String channelId) {
		return iCmsChannelItemDao.findCountByItem(channelId);
	}
	
	public boolean exitMenuInChannelItem(String menuId,String channelId)
			throws Exception {
		return iCmsChannelItemDao.exitMenuInChannelItem(menuId,channelId);
	}



}
