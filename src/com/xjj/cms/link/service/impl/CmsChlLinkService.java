package com.xjj.cms.link.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.channel.dao.ICmsChannelItemDao;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.link.dao.ICmsChlLinkDao;
import com.xjj.cms.link.dao.ICmsLinkDao;
import com.xjj.cms.link.model.CmsChlLink;
import com.xjj.cms.link.model.CmsLink;
import com.xjj.cms.link.service.ICmsChlLinkService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("cmsChlLinkService")
public class CmsChlLinkService extends BaseService<CmsChlLink, String> implements ICmsChlLinkService{
	@Autowired
	@Qualifier("cmsChlLinkDao")
	private ICmsChlLinkDao cmsChlLinkDao;
	
	@Autowired
	@Qualifier("CmsChannelItemDao")
	private ICmsChannelItemDao iCmsChannelItemDao;
	
	@Autowired
	@Qualifier("cmsLinkDao")
	private ICmsLinkDao cmsLinkDao;
	
	@Autowired
    @Qualifier("cmsLinkDao")
	@Override
	public void setBaseDao(IBaseDao<CmsChlLink, String> baseDao) {
		this.baseDao = cmsChlLinkDao;
	}

	@Override
	public void addLink(String linkId, String channelId) {
		CmsLink link = cmsLinkDao.findLink(linkId);
		CmsChannel channel = iCmsChannelItemDao.findByChannelId(channelId).get(0);
		CmsChlLink chlLink = new CmsChlLink();
		chlLink.setChannelId(channelId);
		chlLink.setChannelName(channel.getChanneName());
		chlLink.setLinkId(linkId);
		chlLink.setLinkName(link.getLinkName());
		cmsChlLinkDao.save(chlLink);
	}

	@Override
	public List<CmsChlLink> findAllLinkByChannelId(String channelId) {
		return cmsChlLinkDao.findAllLinkByChannelId(channelId);
	}

	@Override
	public void deleteAll() {
		List<CmsChlLink> list = cmsChlLinkDao.findAll();
		if(!list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				CmsChlLink chlLink = list.get(i);
				chlLink.delete();
			}
		}
	}
	
	@Override
	public Page<CmsChlLink> query(Map<String, Object> filtersMap) {
		 //开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		//进行条件查询
		List<CmsChlLink> items = cmsChlLinkDao.query(skip,pageSize,queryMap, " order by sort");
		//获取总记录数
		int total =  cmsChlLinkDao.total(queryMap);
		//构造返回对象page
		Page<CmsChlLink> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

	@Override
	public List<CmsLink> getAllLinks() {
		return cmsLinkDao.listAll();
	}

	@Override
	public boolean exitLinkInChannelLink(String id, String channelId) {
		return cmsChlLinkDao.exitLinkInChannelLink(id,channelId);
	}

	@Override
	public CmsLink findByLinkId(String linkId) {
		return cmsLinkDao.findLink(linkId);
	}

	@Override
	public void addCmsChlLink(CmsLink link, String channelId) {
		CmsChannel channel = iCmsChannelItemDao.findByChannelId(channelId).get(0);
		CmsChlLink chlLink = new CmsChlLink();
		chlLink.setChannelId(channelId);
		chlLink.setChannelName(channel.getChanneName());
		chlLink.setLinkId(link.getId());
		chlLink.setLinkName(link.getLinkName());
		chlLink.setLinkAddr(link.getLinkAddr());
		chlLink.setLinkType(link.getLinkType());
		chlLink.setSort(cmsChlLinkDao.countByChannelId(channelId)+1+"");
		cmsChlLinkDao.save(chlLink);
	}
}
