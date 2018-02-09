package com.xjj.cms.win.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.win.model.LeagueInfo;
import com.xjj.cms.win.service.ILeagueInfoService;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.ICommonService;
import com.xjj.framework.core.util.JsonUtil;
import com.xjj.framework.core.web.controller.BaseController;

@Controller
@RequestMapping(value = "/win/LeagueInfo")
public class LeagueInfoController extends BaseController{
	
	@Autowired
	@Qualifier("LeagueInfoService")
	private ILeagueInfoService leagueInfoService;
	
	@Autowired
	@Qualifier("CommonService")
	private ICommonService commonService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,RequestMethod.POST })
	public JsonResult list(String filters) throws Exception {
		// //json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<LeagueInfo> page = leagueInfoService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}
	
	
	@RequestMapping(value = "/add")
	public JsonResult add(String models) throws Exception {
		LeagueInfo model = JsonUtil.fromJson(models,LeagueInfo.class);
		model.setCreateTime(new Date());
		model.save();
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	
	@RequestMapping(value = "/update")
	public JsonResult update(String models) throws Exception {
		LeagueInfo model = JsonUtil.fromJson(models,LeagueInfo.class);
		model.update();
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws IOException {
		JsonResult jr = new JsonResult(0, "", null);
		LeagueInfo model = JsonUtil.fromJson(models,LeagueInfo.class);
		model.delete();
		// 构造返回对象
		return jr;
	}
	
	//获取联赛信息
	@RequestMapping(value = "/getLeagueInfo4DropDownList")
    public List<Map> getLeagueInfo4DropDownList() throws IOException{
		List<Map> data=commonService.mapListByNative("select * from F_League_Info where 1=1 order by sort");
		return data;
    }
}
