package com.xjj.cms.channel.service;

import java.util.List;
import java.util.Map;

import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsChannelItemService extends IBaseService<CmsChannelItem, String>{
	
	public Page<CmsChannelItem> query(Map<String, Object> filtersMap);

	public List<CmsMenu> findByMenuId(String menuId);

	public boolean ifAdd(String menuId,String channelId);

	public List<CmsChannel> findByChannelId(String channelId);
	
	public List<CmsChannelItem> findCmsChannelItem(String menuId,String channelId);

	public List<CmsMenu> findAllMenu();

	public List<CmsMenu> child(String menuId);

	public List<CmsChannelItem> findAllItem(String channelId);

	public int findCountByItem(String channelId);
	
	/**
	 * 根据栏目id，频道id，查询是否存在频道项
	 * @author yeyunfeng 2014-9-1  下午3:54:17
	 * @param menuId
	 * @param channelId
	 * @return true-存在  false-不存在
	 * @throws Exception
	 *
	 */
	public boolean exitMenuInChannelItem(String menuId,String channelId)throws Exception;

}
