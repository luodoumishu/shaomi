package com.xjj.cms.vote.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.vote.model.CmsVoteDetail;
import com.xjj.cms.vote.service.ICmsVoteDetailService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;

/**
 * 投票项明细Controller
 * @author wuqilin
 * @time 2014-10-30
 */

@Controller
@RequestMapping(value = "/cms/vote/detail")
public class CmsVoteDetailController extends CMSBaseController<CmsVoteDetail>{
	@Autowired
	@Qualifier("cmsVoteDetailService")
	private ICmsVoteDetailService cmsVoteDetailService;
	
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
		Page<CmsVoteDetail> page = cmsVoteDetailService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}
	
	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws Exception {
		JsonResult jr = new JsonResult(0, "", null);
		CmsVoteDetail model = JsonUtil.fromJson(models, CmsVoteDetail.class);
		cmsVoteDetailService.delete(model);
		// 构造返回对象
		return jr;
	}
	@RequestMapping(value = "/saveOrUpdate", method = {
			RequestMethod.POST })
	public JsonResult saveOrUpdate(@Valid @ModelAttribute("dataform") CmsVoteDetail detail) throws Exception {
		System.out.println(detail);
//		String id = detail.getId();
//		if (null != id && !id.isEmpty()) { //修改
//			this.setCmsBaseModel(detail, BaseConstant.UPDATE);
//			detail.update();
//		}else { //新增
//			this.setCmsBaseModel(detail, BaseConstant.ADD);
//			detail.save();
//		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
}
