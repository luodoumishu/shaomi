package com.xjj.cms.demo.web.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xjj._extensions.web.controller.BaseController;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.demo.model.CmsTest;
import com.xjj.cms.demo.service.ICmsTestService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;

/**
 * 栏目Controller
 * <p>
 * 
 * @author yeyunfeng 2014-9-2 下午3:25:50
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
@Controller
@RequestMapping(value = "/cms/test")
public class CmsTestController extends BaseController<CmsTest> {

	@Autowired
	@Qualifier("cmsTestService")
	private ICmsTestService cmsTestService;

	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}



	@RequestMapping(value = "/add", method = {
			RequestMethod.POST })
	public JsonResult add(HttpServletRequest request, String models)
			throws IOException, Exception {
		System.out.println(models.toString());
		if (models != null && models.length() > 1) {
			
			
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}



	
	@RequestMapping(value = "/formSave", method = {
			RequestMethod.POST })
	public JsonResult saveOrUpdate(CmsTest test,@RequestParam MultipartFile[] files) throws Exception {
		
		
		 for(MultipartFile myfile : files){  
			 System.out.println(myfile.getOriginalFilename());
			 
        }  
		//test.save();
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

}
