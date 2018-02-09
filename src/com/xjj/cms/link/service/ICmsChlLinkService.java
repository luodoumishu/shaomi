package com.xjj.cms.link.service;

import java.util.List;
import java.util.Map;

import com.xjj.cms.link.model.CmsChlLink;
import com.xjj.cms.link.model.CmsLink;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsChlLinkService extends IBaseService<CmsChlLink, String>{

	void addLink(String linkId, String channelId);

	List<CmsChlLink> findAllLinkByChannelId(String channelId);

	void deleteAll();

	Page<CmsChlLink> query(Map<String, Object> filtersMap);

	List<CmsLink> getAllLinks();

	boolean exitLinkInChannelLink(String id, String channelId);

	CmsLink findByLinkId(String linkId);

	void addCmsChlLink(CmsLink link, String channelId);

}
