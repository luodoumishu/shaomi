package com.xjj.cms.channel.service;

import java.util.List;
import java.util.Map;

import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.framework.core.model.SimpleModel;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsChannelService extends IBaseService<CmsChannel, String>{

	public Page<CmsChannel> query(Map<String, Object> filtersMap);

	public List<CmsChannel> listAllByOrgRootId(String orgRootId);

	public boolean checkExistChildOrg(String orgId);
	 //fengjunkong 根据频道代码获得频道
	public CmsChannel findByChlCode(String chlCode);

	public List<CmsChannelItem> findCmsChannelItemByChannelId(String channelId);

	public List<CmsChannel> findByChildChlById(String id);


}
