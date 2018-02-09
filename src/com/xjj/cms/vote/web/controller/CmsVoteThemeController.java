package com.xjj.cms.vote.web.controller;

import java.io.IOException;
import java.util.List;
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
import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.cms.vote.service.ICmsVoteThemeService;
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
@RequestMapping(value = "/cms/vote/theme")
public class CmsVoteThemeController extends CMSBaseController<CmsVoteTheme>{
	@Autowired
	@Qualifier("cmsVoteThemeService")
	private ICmsVoteThemeService cmsVoteThemeService;
	
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
		Page<CmsVoteTheme> page = cmsVoteThemeService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}
	
	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws Exception {
		JsonResult jr = new JsonResult(0, "", null);
		CmsVoteTheme model = JsonUtil.fromJson(models, CmsVoteTheme.class);
		cmsVoteThemeService.delete(model);
		// 构造返回对象
		return jr;
	}
	@RequestMapping(value = "/saveOrUpdate", method = {
			RequestMethod.POST })
	public JsonResult saveOrUpdate(@Valid @ModelAttribute("dataform") CmsVoteTheme theme) throws Exception {
		
		String id = theme.getId();
		if (null != id && !id.isEmpty()) { //修改
//			String createTimeStr = (String)bindingResult.getFieldValue("createTime");
//			Date createTime =  DateUtil.getString2Date(createTimeStr, "yyyy-MM-dd HH:mm:ss");
//			theme.setCreateTime(createTime);
			this.setCmsBaseModel(theme, BaseConstant.UPDATE);
			cmsVoteThemeService.updateTheme(theme);
		}else { //新增
			this.setCmsBaseModel(theme, BaseConstant.ADD);
			theme.save();
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	@RequestMapping(value = "/findItem")
	public JsonResult findItem(HttpServletRequest request) throws Exception {
		String themeId = request.getParameter("themeId");
		CmsVoteTheme theme =  cmsVoteThemeService.get(themeId);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", theme);
		return jr;
	}
	@RequestMapping(value = "/doVote")
	public JsonResult doVote(HttpServletRequest request) throws Exception {
		String[] detailIdList = request.getParameterValues("detailList");
		String[] itemIdList = request.getParameterValues("itemIdList");
		String[] contentList = request.getParameterValues("contentList");
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
	@RequestMapping(value = "/findAll")
	public JsonResult findAll() throws Exception {
		List<CmsVoteTheme> themeList =  cmsVoteThemeService.listAll();
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", themeList);
		return jr;
	}
}
