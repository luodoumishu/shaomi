package com.xjj.cms.menu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xjj._extensions.web.controller.BaseController;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.menu.model.CmsMenuUser;
import com.xjj.cms.menu.service.ICmsMenuOrgService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy; 
 
/**
 *  栏目分级授权Controller
 * <p>
 * @author yeyunfeng 2015年5月18日 下午4:13:41 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年5月18日
 * @modify by reason:{方法名}:{原因}
 */
@Controller
@RequestMapping(value = "/cms/menuOrg")
public class CmsMenuOrgController extends BaseController<CmsMenuUser> {
	

	@Autowired
	@Qualifier("cmsMenuOrgService")
	private ICmsMenuOrgService cmsMenuOrgService;

	@RequestMapping(value="save",method={RequestMethod.POST,RequestMethod.GET})
	public JsonResult save(String menuIds,String orgId)throws Exception {
		
		cmsMenuOrgService.save(menuIds, orgId);
		JsonResult result = new JsonResult(1, "", null);
		return result;
	}
	
	
	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	

}
