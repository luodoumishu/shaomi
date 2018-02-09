package com.xjj.cms.index.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.service.IZorganizeService;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;

/**
 * 用户首页显示的请求方法
 * @author wuqilin
 *
 */

@Controller
@RequestMapping(value = "/cms/index")
public class CmsIndexController  extends CMSBaseController<CmsArticleHistory>{

	@Autowired
	@Qualifier("ZorganizeService")
	private IZorganizeService zorganizeService;
	
	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}

	
	/**
	 * 根据单位名称查询1级单位返回前台
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFirstLevelByOrgName", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult getFirstLevelOrg(HttpServletRequest request) throws Exception {
		JsonResult jr = new JsonResult(0, "", null);
		String orgName = request.getParameter("orgName");
		List<Zorganize> orgs = zorganizeService.getFirstLevelByOrgName(orgName);
		if(null != orgs){
			jr.setResultData(orgs);
		}
		// 构造返回对象
		return jr;
	}
	
	/**
	 * 根据orgCode查询1级单位返回前台
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFirstLevelByorgCode", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult getFirstLevelByorgCode(HttpServletRequest request) throws Exception {
		JsonResult jr = new JsonResult(0, "", null);
		String orgCode = request.getParameter("orgCode");
		Zorganize org = zorganizeService.getByPyName(orgCode);
		if(null != org){
			List<Zorganize> orgs = zorganizeService.getOrgByLevel(org.getId(), 1);
			if(!orgs.isEmpty() && orgs.size() > 0){
				jr.setResultData(orgs);
			}
		}
		// 构造返回对象
		return jr;
	}
	
	
}
