package com.xjj.cms.vote.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.vote.model.CmsVoteItem;
import com.xjj.cms.vote.service.ICmsVoteItemService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;

/**
 * 投票主题Controller
 * @author wuqilin
 * @time 2014-10-21
 */

@Controller
@RequestMapping(value = "/cms/vote/item")
public class CmsVoteItemController extends CMSBaseController<CmsVoteItem>{
	@Autowired
	@Qualifier("cmsVoteItemService")
	private ICmsVoteItemService cmsVoteItemService;
	
	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,RequestMethod.POST })
	public JsonResult list(String filters) throws IOException {
		// //json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsVoteItem> page = cmsVoteItemService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}
	
	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws Exception {
		JsonResult jr = new JsonResult(0, "", null);
		CmsVoteItem item = JsonUtil.fromJson(models, CmsVoteItem.class);
		cmsVoteItemService.delete(item);
		// 构造返回对象
		return jr;
	}
	@RequestMapping(value = "/deleteByItemId")
	public JsonResult deleteByItemId(String itemId) throws Exception {
		itemId = itemId.replaceAll("\"", "");
		JsonResult jr = new JsonResult(0, "", null);
		cmsVoteItemService.deleteByItemId(itemId);
		// 构造返回对象
		return jr;
	}
	@RequestMapping(value = "/findByItemId")
	public JsonResult findByItemId(String itemId) throws Exception {
		itemId = itemId.replaceAll("\"", "");
		CmsVoteItem item = cmsVoteItemService.get(itemId);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", item);
		return jr;
	}
	@RequestMapping(value = "/saveOrUpdate", method = {
			RequestMethod.POST })
	public JsonResult saveOrUpdate(@Valid @ModelAttribute("dataform") CmsVoteItem item,HttpServletRequest request) throws Exception {
		String themeId = request.getParameter("themeId");
		String[] detailNames = request.getParameterValues("detailNames[]");
		String id = item.getId();
		if (null != id && !id.isEmpty()) { 
			//修改
			this.setCmsBaseModel(item, BaseConstant.UPDATE);
			if(detailNames!=null){
				cmsVoteItemService.updateThemeAndDetail(item,themeId,detailNames);
				cmsVoteItemService.updateDetail(item);
			}else{
				cmsVoteItemService.update(item);
			}
		}else { 
			//新增
			this.setCmsBaseModel(item, BaseConstant.ADD);
			cmsVoteItemService.addThemeAndDetail(item,themeId,detailNames);
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
}
