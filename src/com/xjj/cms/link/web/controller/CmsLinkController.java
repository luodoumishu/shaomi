package com.xjj.cms.link.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.util.PropertiesUtil;
import com.xjj.cms.base.util.Random;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.file.model.CmsFile;
import com.xjj.cms.link.model.CmsLink;
import com.xjj.cms.link.service.ICmsLinkService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;

@Controller
@RequestMapping(value = "/cms/link")
public class CmsLinkController  extends CMSBaseController<CmsLink>{
	@Autowired
	@Qualifier("cmsLinkService")
	private ICmsLinkService cmsLinkService;
	
	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult list(String filters) throws IOException {
		// //json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsLink> page = cmsLinkService.query(filtersMap);

		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}
	
	@RequestMapping(value = "/add",method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult add(String models) throws IOException, Exception {
		if (models != null && models.length() > 1) {
			CmsLink model = JsonUtil.fromJson(models, CmsLink.class);
			this.setCmsBaseModel(model, BaseConstant.ADD);
			cmsLinkService.save(model);
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	@RequestMapping(value = "/update")
	public JsonResult update(String models) throws Exception {
		if (models != null && models.length() > 1) {
			CmsLink model = JsonUtil.fromJson(models, CmsLink.class);
			this.setCmsBaseModel(model, BaseConstant.UPDATE);
			model.update();
			String linkId = model.getId();
			cmsLinkService.updateCmsChlLink(linkId);
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	
	@RequestMapping(value = "/findLink")
	public JsonResult findLink(String linkId) throws Exception {
		linkId = linkId.replaceAll("\"", "");
		CmsLink link = cmsLinkService.findLink(linkId);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", link);
		return jr;
	}
	
	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws Exception {
		JsonResult jr = new JsonResult(0, "", null);
		CmsLink model = JsonUtil.fromJson(models, CmsLink.class);
		cmsLinkService.delete(model);
		String linkId = model.getId();
		cmsLinkService.deleteCmsChlLink(linkId);
		// 构造返回对象
		return jr;
	}
	
	@RequestMapping(value = "/upLinkImg",method = {RequestMethod.POST})
	public void upLinkImg(@RequestParam("imgFile") List<MultipartFile> imgFile,HttpServletResponse response) throws IOException, Exception {
		int resultCode = 0;
		String resultMsg = "";
		String showPath = "";
		try {
			
			for(MultipartFile mFile:imgFile){
				//得到文件名    
	            String filename =mFile.getOriginalFilename();
	            String ext = filename.substring(filename.lastIndexOf("."));
	            //新文件名
	            String newFileName=System.currentTimeMillis()+Random.getStrRandom(3)+ext;
	            long imageFileSize  = mFile.getSize();
	            //图片保存路径
	            String file_path = PropertiesUtil.getProperty(
						CmsCC.WEB_CONFIG_ZH, CmsCC.LINK_PATH);
				String fileFormat = PropertiesUtil.getProperty(
						CmsCC.FILE_FORMAT, CmsCC.IMAGES_FORMAT);
				String limitFileSize_str = PropertiesUtil.getProperty(
						CmsCC.FILE_FORMAT, CmsCC.MENU_IMAGES_SIZE);
				String ui = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH,
						CmsCC.ARTICLE_AFFIX_UI);
	            long limitImgSize = Long.valueOf(limitFileSize_str)*1l*1024*1024; 
	            if(!com.xjj.cms.base.util.FileTools.checkFormat(filename, fileFormat)){
	            	resultCode = 1;
	            	resultMsg = "上传文件格式不对，只支持"+fileFormat+"格式，请检查！";
				}else if(imageFileSize>limitImgSize){
					resultCode = 2;
					resultMsg = "上传文件不能超过"+limitImgSize+"M，请检查！";
				}else {
					File file = new File(file_path);
					if (!file.exists()) {
						file.mkdir();
					}
					com.xjj.cms.base.util.FileTools.uploadFile(mFile.getInputStream(), file_path+File.separator+newFileName);
					//找到目录保存的最后目录名称
					int menuStrIndex = file_path.lastIndexOf(File.separator);
					if (menuStrIndex>0) {
						showPath =  ui+file_path.substring(menuStrIndex)+File.separator+newFileName;
					}else {
						resultCode = 2;
						resultMsg = "文件栏目图片保存路径还未配置，请联系系统管理员！";
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResult jr = new JsonResult(resultCode, resultMsg, showPath);
		response.getWriter().write(JSONObject.fromObject(jr).toString());
	}
	
}
