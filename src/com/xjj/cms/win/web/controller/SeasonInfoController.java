package com.xjj.cms.win.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.win.model.LeagueInfo;
import com.xjj.cms.win.model.SeasonInfo;
import com.xjj.cms.win.service.ISeasonInfoService;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;
import com.xjj.framework.core.web.controller.BaseController;

@Controller
@RequestMapping(value = "/win/SeasonInfo")
public class SeasonInfoController extends BaseController{
	
	@Autowired
	@Qualifier("SeasonInfoService")
	private ISeasonInfoService seasonInfoService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,RequestMethod.POST })
	public JsonResult list(String filters) throws Exception {
		// //json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<SeasonInfo> page = seasonInfoService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}
	
	
	@RequestMapping(value = "/add")
	public JsonResult add(String models) throws Exception {
		SeasonInfo model = JsonUtil.fromJson(models,SeasonInfo.class);
		model.setCreateTime(new Date());
		model.save();
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	
	@RequestMapping(value = "/update")
	public JsonResult update(String models) throws Exception {
		SeasonInfo model = JsonUtil.fromJson(models,SeasonInfo.class);
		model.update();
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws IOException {
		JsonResult jr = new JsonResult(0, "", null);
		SeasonInfo model = JsonUtil.fromJson(models,SeasonInfo.class);
		model.delete();
		// 构造返回对象
		return jr;
	}
	
}
