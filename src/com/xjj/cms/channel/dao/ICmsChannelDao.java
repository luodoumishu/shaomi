package com.xjj.cms.channel.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsChannelDao  extends IBaseDao<CmsChannel, String>{

	public List<CmsChannel> query(int skip, int pageSize, Map<String, Object> queryMap,
			String string);

	public int total(Map<String, Object> queryMap);

	public List<CmsChannel> listAllByOrgRootId(String orgRootId);

	public boolean checkExistChildOrg(String orgId);

	public List<CmsChannelItem> findCmsChannelItemByChannelId(String channelId);
    //fengjunkong 根据频道代码获得频道
	public CmsChannel findByChlCode(String chlCode);

	public List<CmsChannel> findByChildChlById(String id);


}
