package com.xjj.cms.vote.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.cms.vote.service.ICmsVoteThemeService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.service.ICommonService;


/**
 * 投票外部需要调用的url，不需要过滤
 * <p>
 * @author yeyunfeng 2014-12-10 下午2:51:02 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-12-10
 * @modify by reason:{方法名}:{原因}
 */
@Controller
@RequestMapping(value = "/cms/vote/remote")
public class CmsVoteTheme4RemoteController extends CMSBaseController<CmsVoteTheme>{
	@Autowired
	@Qualifier("cmsVoteThemeService")
	private ICmsVoteThemeService cmsVoteThemeService;
	
	@Autowired
	@Qualifier("CommonService")
	private ICommonService commonService;
	
	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	
	
	@RequestMapping(value = "/doVote")
	public JsonResult doVote(HttpServletRequest request) throws Exception {
		String[] detailIdList = request.getParameterValues("detailList[]");
		String[] itemIdList = request.getParameterValues("itemIdList[]");
		String[] contentList = request.getParameterValues("contentList[]");
		String themeId = request.getParameter("themeId");
		if(detailIdList != null){
			cmsVoteThemeService.addVote(detailIdList,themeId);
		}
		if(itemIdList!=null && contentList!=null){
			cmsVoteThemeService.saveVoteContent(itemIdList,contentList);
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	
	@RequestMapping(value = "/findVote")
	public CmsVoteTheme findVote(HttpServletRequest request) throws Exception {
		String themeId = request.getParameter("themeId");
		CmsVoteTheme cmsVoteTheme = cmsVoteThemeService.getById(themeId);
		//JSONObject json  = JSONObject.fromObject(cmsVoteTheme);
		// 构造返回对象
	//	JsonResult jr = new JsonResult(0, "", cmsVoteTheme);
		return cmsVoteTheme;
	}
}