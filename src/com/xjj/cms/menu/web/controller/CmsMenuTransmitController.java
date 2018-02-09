package com.xjj.cms.menu.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.roleUserPer.service.IZorganizeService;
import com.xjj._extensions.web.controller.BaseController;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.menu.model.CmsMenuTransmit;
import com.xjj.cms.menu.model.CmsMenuTransmitInfo;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.cms.menu.service.ICmsMenuTransmitService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;

@Controller
@RequestMapping(value = "/cms/menuTransmit")
public class CmsMenuTransmitController extends BaseController<CmsMenuTransmit> {

	@Autowired
	@Qualifier("cmsMenuTransmitService")
	private ICmsMenuTransmitService cmsMenuTransmitService;
	
	@Autowired
	@Qualifier("cmsMenuService")
	private ICmsMenuService cmsMenuService;
	
	@Autowired
	@Qualifier("cmsArticleService")
	private ICmsArticleService cmsArticleService;
	
	@Autowired
	@Qualifier("ZorganizeService")
	private IZorganizeService iZorganizeService;

	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery, OrderBy orderBy) {
	}
	
	@RequestMapping(value = "/saveTransmit", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult saveTransmit(HttpServletRequest request) throws Exception{
		String menuTreeAry = request.getParameter("menuTreeAry");
		String targetMenuTreeAry = request.getParameter("targetMenuTreeAry");
		String orgTreeAry = request.getParameter("orgTreeAry");
		String[] menus  = menuTreeAry.split(",");
		String[] targetMenus = targetMenuTreeAry.split(",");
		String[] orgs = orgTreeAry.split(",");
		if(menus.length > 0 && targetMenus.length > 0 && orgs.length > 0){
			for (int i = 0; i < menus.length; i++) {
				String menuid = menus[i];
				for (int j = 0; j < orgs.length; j++) {
					String orgid = orgs[j];
//					cmsMenuTransmitService.deleteBySourceMenuAndOrg(menuid,orgid);
					for (int k = 0; k < targetMenus.length; k++) {
						String targetMenuid = targetMenus[k];
						CmsMenuTransmit cmt = new CmsMenuTransmit(menuid, orgid, targetMenuid);
						if(!cmsMenuTransmitService.checkCMTIsExist(menuid, orgid, targetMenuid)){
							cmsMenuTransmitService.save(cmt);
						}
					}
				}
			}
		}
		JsonResult jr = new JsonResult(0, "", null);
        return jr;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult list(String filters) throws Exception {
		// json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		if (null == filters ||filters.isEmpty()) {
			filters = "{\"skip\":-1,\"pageSize\":-1,\"queryObj\":{}"; 
		}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters, Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsMenuTransmitInfo> page = cmsMenuTransmitService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}
	
	@RequestMapping(value = "/cancleTarget", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult cancleTarget(HttpServletRequest request) throws Exception{
		JsonResult jr = new JsonResult(1, "取消推送失败", null);
		String source_menu_id = request.getParameter("source_menu_id");
		String target_menu_id = request.getParameter("target_menu_id");
		String target_org_id = request.getParameter("target_org_id");
		if(null != source_menu_id && !source_menu_id.equals("") && null != target_menu_id && !target_menu_id.equals("") && null != target_org_id && !target_org_id.equals("")){
			if(cmsMenuTransmitService.deleteByIds(source_menu_id, target_menu_id,target_org_id)){
				jr.setResultCode(0);
			}
		}
        return jr;
	}
	
}


