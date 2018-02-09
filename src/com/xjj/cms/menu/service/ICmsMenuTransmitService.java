package com.xjj.cms.menu.service;

import java.util.List;
import java.util.Map;

import com.xjj.cms.menu.model.CmsMenuTransmit;
import com.xjj.cms.menu.model.CmsMenuTransmitInfo;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsMenuTransmitService extends IBaseService<CmsMenuTransmit, String>{

	boolean checkCMTIsExist(String menuid, String orgid, String targetMenuid);

	List<CmsMenuTransmit> findTransmit4OrgAMenu(String target_menuId, String target_orgId);

	Page<CmsMenuTransmitInfo> query(Map<String, Object> filtersMap) throws Exception;

	boolean deleteByIds(String source_menu_id, String target_menu_id, String target_org_id);

}
