package com.xjj.cms.link.service;

import java.util.Map;

import com.xjj.cms.link.model.CmsLink;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsLinkService  extends IBaseService<CmsLink, String>{

	Page<CmsLink> query(Map<String, Object> filtersMap);

	CmsLink findLink(String linkId);

	void deleteCmsChlLink(String linkId);

	void updateCmsChlLink(String linkId);

}
