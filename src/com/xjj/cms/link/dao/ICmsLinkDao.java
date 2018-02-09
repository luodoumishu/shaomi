package com.xjj.cms.link.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.link.model.CmsLink;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsLinkDao extends IBaseDao<CmsLink, String>{

	List<CmsLink> query(int skip, int pageSize, Map<String, Object> queryMap,
			String string);

	int total(Map<String, Object> queryMap);

	CmsLink findLink(String linkId);

}
