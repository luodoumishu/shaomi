package com.xjj.cms.link.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.link.model.CmsChlLink;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsChlLinkDao extends IBaseDao<CmsChlLink, String>{

	List<CmsChlLink> findAllLinkByChannelId(String channelId);

	List<CmsChlLink> findAll();

	List<CmsChlLink> query(int skip, int pageSize,
			Map<String, Object> queryMap, String string);

	int total(Map<String, Object> queryMap);

	boolean exitLinkInChannelLink(String id, String channelId);

	int countByChannelId(String channelId);

	List<CmsChlLink> findAllByLinkId(String linkId);


}
