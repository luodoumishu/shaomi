package com.xjj.cms.channel.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsChannelItemDao extends IBaseDao<CmsChannelItem, String>{
	public List<CmsChannelItem> queryByChannelId(int skip, int pageSize,
			Map<String, Object> queryMap, String string);

	public int total(Map<String, Object> queryMap);

	public List<CmsMenu> findByMenuId(String itemId);

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
	public boolean exitMenuInChannelItem(String menuId,String channelId) throws Exception;
}
