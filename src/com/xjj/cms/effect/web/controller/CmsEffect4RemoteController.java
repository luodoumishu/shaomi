package com.xjj.cms.effect.web.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.effect.model.CmsEffect;
import com.xjj.cms.effect.service.ICmsEffectService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.jdk17.utils.StringUtil;

/**
 * 特效外部需要调用的url，不需要过滤
 * <p>
 * @author yeyunfeng 2015年6月12日 上午12:29:34 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年6月12日
 * @modify by reason:{方法名}:{原因}
 */
@Controller
@RequestMapping(value = "/cms/effect/remote")
public class CmsEffect4RemoteController extends CMSBaseController<CmsEffect>{
	
	@Autowired
	@Qualifier("cmsEffectService")
	private ICmsEffectService cmsEffectService;
	
	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	
	
	/**
	 * 获取所有显示的效果
	 * @author yeyunfeng 2015年6月12日  上午1:02:45
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getShowEffect", method = { RequestMethod.POST,
			RequestMethod.GET })
	public JsonResult getShowEffect(HttpServletRequest request) throws Exception {

		String filters = "{\"skip\":-1,\"pageSize\":-1,\"queryObj\":{\"isShow\":0}}";
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		//部门拼音代码
		String orgCode = request.getParameter("org");
		if (StringUtil.isEmpty(orgCode)) {
			orgCode = "hns";
		}
		filtersMap.put("fjsq_orgCode", orgCode);
		
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsEffect> page = cmsEffectService.query4Remote(filtersMap);
		List<CmsEffect> effectList = new ArrayList<CmsEffect>();
		if (null != page) {
			effectList = page.getItems();
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", effectList);
		return jr;
	}
}