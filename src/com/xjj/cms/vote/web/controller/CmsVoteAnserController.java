package com.xjj.cms.vote.web.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.vote.model.CmsVoteAnser;
import com.xjj.cms.vote.service.ICmsVoteAnserService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;



@Controller
@RequestMapping(value = "/cms/vote/anser")
public class CmsVoteAnserController extends CMSBaseController<CmsVoteAnser>{
	
	@Autowired
	@Qualifier("cmsVoteAnserService")
	private ICmsVoteAnserService cmsVoteAnserService;
	
	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
		
	}
	@RequestMapping(value = "/findAnserList")
	public JsonResult findAnserList() throws Exception {
		List<CmsVoteAnser> ansers =  cmsVoteAnserService.listAll();
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", ansers);
		return jr;
	}
	
	@RequestMapping(value = "/getByItemId")
	public JsonResult getByItemId(String itemId) throws Exception {
		List<CmsVoteAnser> ansers =  cmsVoteAnserService.listAllByItemId(itemId);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", ansers);
		return jr;
	}
}
