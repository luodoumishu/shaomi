package com.xjj.cms.video.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.util.FileTools;
import com.xjj.cms.base.util.PropertiesUtil;
import com.xjj.cms.base.util.progress.BackFileUploadUtil;
import com.xjj.cms.base.util.progress.Progress;
import com.xjj.cms.base.util.progress.ProgressPool;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.channel.service.ICmsChannelService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.cms.video.VideoConvertThread;
import com.xjj.cms.video.model.CmsVideo;
import com.xjj.cms.video.service.ICmsVideoService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;
import com.xjj.jdk17.utils.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cms/video")
public class CmsVideoController extends CMSBaseController<CmsVideo> {

	@Autowired
    @Qualifier("cmsVideoService")
    private ICmsVideoService cmsVideoService;

	@Autowired
	@Qualifier("CmsChannelService")
	private ICmsChannelService iCmsChannelService;

	@Autowired
	@Qualifier("CmsChannelItemService")
	private ICmsChannelItemService cmsChannelItemService;

	@Autowired
	@Qualifier("cmsMenuService")
	private ICmsMenuService cmsMenuService;

	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult list(String filters) throws Exception {
		// //json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsVideo> page = cmsVideoService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}

	@RequestMapping(value = "/addAndEdit",method = { RequestMethod.POST ,RequestMethod.GET})
	public void addAndEdit(CmsVideo cmsVideo,  HttpServletResponse response,HttpServletRequest request) throws Exception{
		int resultCode = 0;
		String resultMsg = "";
		JsonResult jr = new JsonResult(resultCode, resultMsg, null);
		String fname = cmsVideo.getVideo_filename();
		int index =fname.indexOf(".");
		String ext = null;
		if(-1 != index){
			ext = fname.substring(index,fname.length());
		}
		if(cmsVideo.getId().isEmpty()){
			this.setCmsBaseModel(cmsVideo, BaseConstant.ADD);
			cmsVideo.setState("2");
			cmsVideo.setRead_count(0);
			if (!StringUtil.isEmpty(ext)){
				if (".flv".equals(ext)){
					cmsVideo.setState("3");
				}
				cmsVideoService.save(cmsVideo);
			}else {
				resultCode = -1;
				resultMsg = "请检查，上传的视频格式";
			}
		}else{
			this.setCmsBaseModel(cmsVideo, BaseConstant.UPDATE);
			if (!StringUtil.isEmpty(ext)){
				if (".flv".equals(ext)){
					cmsVideo.setState("3");
				}
				cmsVideoService.update(cmsVideo);
			}else {
				resultCode = -1;
				resultMsg = "请检查，上传的视频格式";
			}

		}
		//构造返回对象
		jr.setResultMsg(resultMsg);
		jr.setResultCode(resultCode);
		response.getWriter().write(JSONObject.fromObject(jr).toString());
		if(-1 != index){
			// 获取配置文件中的路径
			String basePath = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH,CmsCC.VIDEO_PATH);
			String vp = cmsVideo.getVideo_filepath();
			vp = vp.substring(vp.indexOf(File.separator)+1);
			String filePath =  basePath+File.separator+vp+File.separator+cmsVideo.getVideo_filename();
			VideoConvertThread videoTherad = new VideoConvertThread(filePath,cmsVideo,request);
			Thread thread = new Thread(videoTherad);
			thread.start();
		}
	}
	
	@RequestMapping(value = "/justUpdateAttribute",method = { RequestMethod.POST , RequestMethod.GET})
    public void justUpdateAttribute(CmsVideo cmsVideo, HttpServletResponse response,HttpServletRequest request) throws Exception{
		int resultCode = 0;
		String resultMsg = "";
		
		CmsVideo temp =  cmsVideoService.findCmsVideoById(cmsVideo.getId());
		if(temp!=null){
			temp.setName(cmsVideo.getName());
			temp.setMenuId(cmsVideo.getMenuId());
			temp.setMenuName(cmsVideo.getMenuName());
			temp.setRemark(cmsVideo.getRemark());
			this.setCmsBaseModel(temp, BaseConstant.UPDATE);
			cmsVideoService.update(temp);
		}
		
		JsonResult jr = new JsonResult(resultCode, resultMsg, null);
		response.getWriter().write(JSONObject.fromObject(jr).toString());
	}
	
	@RequestMapping(value = "/update")
    public JsonResult update(String models) throws Exception{
		CmsVideo model = JsonUtil.fromJson(models, CmsVideo.class);
		this.setCmsBaseModel(model, BaseConstant.UPDATE);
		cmsVideoService.update(model);
      //构造返回对象
        JsonResult jr =  new JsonResult(0,"",model.getId());
        return jr;
    }
	
	@RequestMapping(value = "/delete")
    public JsonResult delete(HttpServletRequest request,String models) throws IOException{
		CmsVideo model = JsonUtil.fromJson(models, CmsVideo.class);
		cmsVideoService.delete(model);
    	//构造返回对象
        JsonResult jr =  new JsonResult(0,"",null);
        return jr;
    }
	
	@RequestMapping(value = "/delelAffixByIdAndType")
    public JsonResult delelAffixByIdAndType(HttpServletRequest request) throws Exception{
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        CmsVideo tempCV =  cmsVideoService.findCmsVideoById(id);
        if(tempCV !=null){
        	if(type.equals("img")){ //type == img
        		tempCV.setImg_ext("");
        		tempCV.setImg_filename("");
        		tempCV.setImg_filepath("");
        		tempCV.setImg_filesize(null);
        		tempCV.setImg_localname("");
        	}else{ //type == video
        		tempCV.setVideo_ext(null);
        		tempCV.setVideo_filename(null);
        		tempCV.setVideo_filepath(null);
        		tempCV.setVideo_filesize(null);
        		tempCV.setVideo_localname(null);
        	}
        	this.setCmsBaseModel(tempCV, BaseConstant.UPDATE);
        	tempCV.update();
        }
		JsonResult jr =  new JsonResult(0,"",null);
        return jr;
    }
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/downAffixByDownloadPath")
	public void downAffixByFilePath(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String downloadPath="";
		String fileName = "";
		CmsVideo tempCV = cmsVideoService.findCmsVideoById(id);
		if(tempCV != null){
			if(type.equals("img")){
				downloadPath = tempCV.getImg_filepath();
				fileName = tempCV.getImg_filename();
			}else{
				downloadPath = tempCV.getVideo_filepath();
				fileName = tempCV.getVideo_filename();
			}
		}

		File aFile = new File(downloadPath);
		if (!aFile.exists()) {
			response.setContentType("text/html;charset=GBK");
			response.getWriter().print("指定文件不存在！");
			return;
		}
		InputStream in = null;
		try {
			response.setContentType("application/x-download");
			response.setCharacterEncoding("UTF-8");
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			in = new FileInputStream(aFile);

			if (in == null) {
				response.getOutputStream().close();
				return;
			}
			byte[] b = new byte[1024];
			int len;
			while ((len = in.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
			in.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			try {
				in.close();
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}



	/**
	 * 根据频道ID获取分页视频(审核通过的），传入的参数为：chlId，pageNo，pageSize
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getVideoByChlId")
	public JsonResult getVideoByChlId(HttpServletRequest request) throws Exception {
		JsonResult jr = new JsonResult(0, "", null);
		String chlId = request.getParameter("chlId");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String orgCode = request.getParameter("orgCode");
		StringBuffer menuIdsStr = new StringBuffer();
		if (!StringUtil.isEmpty(chlId)) {
			CmsChannel cc = iCmsChannelService.get(chlId);
			if (null != cc) {
				List<CmsChannelItem> setChlItem = cmsChannelItemService.findAllItem(cc.getId());
				menuIdsStr = new StringBuffer();
				for (int i = 0; i < setChlItem.size(); i++) {
					CmsChannelItem ccItem = setChlItem.get(i);
					String itemId = ccItem.getMenuId();
					CmsMenu cm = cmsMenuService.get(itemId);
					String menuId = cm.getId();
					if (i == 0) {
						menuIdsStr.append("'" + menuId + "'");
					} else {
						menuIdsStr.append(",'" + menuId + "'");
					}
				}
			}else{ //频道不存在
				return  jr;
			}
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("menuIds", menuIdsStr.toString());
		if (CmsCC.ENABLED_MENU_FJSQ) {
			queryMap.put("fjsq_orgCode", orgCode);
		}
		if (StringUtil.isEmpty(pageNo)){
			jr.setResultMsg("pageNo参数有误");
			return jr;
		}
		int pageNo_int = Integer.parseInt(pageNo);
		int _pageNo = pageNo_int-1;
		int _pageSize = Integer.parseInt(pageSize);
		int skip = _pageNo* _pageSize;
		String filters = "{\"skip\":" + skip + ",\"pageSize\":" + pageSize + "}";
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		filtersMap.put("queryObj", queryMap);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsVideo> page = cmsVideoService.query(filtersMap);
		page.setStart(pageNo_int);
		jr.setResultData(page);

		// 构造返回对象
		return jr;
	}

	@RequestMapping(value = "/getProgress", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult getProgress(HttpServletRequest request) throws  Exception{
		JsonResult jr = new JsonResult(0,"",null);
		Progress progress = ProgressPool.getInstance().get(request.getRemoteAddr());
		jr.setResultData(progress);
		return  jr;
	}

	/**
	 * 根据key获取装换视频进度
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProgressByKey", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult getProgressByKey(String key) throws  Exception{
		JsonResult jr = new JsonResult(0,"",null);
		Progress progress = ProgressPool.getInstance().get(key);
		jr.setResultData(progress);
		return  jr;
	}

	/**
	 * 根据key移除进度条池中的进度条
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeProgressByKey", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult removeProgressByKey(String key) throws  Exception{
		JsonResult jr = new JsonResult(0,"",null);
		ProgressPool.getInstance().remove(key);
		return  jr;
	}

	/**
	 * 上传视频
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int resultCode = 0;
		String resultMsg = "";
		// 获取配置文件中的路径
		String basePath = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH, CmsCC.VIDEO_PATH);
		//以时间建目录
		String savePath = FileTools.createDirByTime(basePath);
		String ui = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH, CmsCC.ARTICLE_AFFIX_UI);
		Map<String,Object> map = new HashMap<>();
		// 判断客户端<form>标记的enctype属性是否是“multipart/form-data"
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("file");
			String name = file.getOriginalFilename();
			String ext = name.substring(name.lastIndexOf(".") + 1, name.length());
			//获取配置文件中支持的文件格式
			String fileFormat = PropertiesUtil.getProperty(CmsCC.FILE_FORMAT, CmsCC.VIDEO_FORMAT2PR);
			boolean f_b =  FileTools.checkFormat(name, fileFormat);//检查文件格式
			if (f_b){
				String fileSize_str = PropertiesUtil.getProperty(CmsCC.FILE_FORMAT, CmsCC.VIDEO_SIZE2PR);
				if (!fileSize_str.isEmpty()){
					int fileSize = Integer.parseInt(fileSize_str);
					long size = file.getSize();
					if (size>fileSize){
						resultCode = -1;
						resultMsg = "上传的视频过大，请处理后再重新上传！";
					}else {
						BackFileUploadUtil bfu = new BackFileUploadUtil(savePath);
						map = bfu.fileUpload(request, "file");
						String up =(String)map.get("up");
						if (up.equals("true")){
							int index = basePath.lastIndexOf(File.separator)+1;
							String visitPath = basePath.substring(index,basePath.length())+savePath.replace(basePath,"");
							map.put("visitPath",visitPath);
							resultMsg= "视频上传成功！";
						}else{
							resultCode = -1;
							resultMsg = "视频上传失败，请联系管理员！";
						}
					}
				}else {
					resultCode = -1;
					resultMsg = "不能上传空的视频文件，请重新上传！";
				}


			}else {
				resultCode = -1;
				resultMsg= "上传的视频"+ext+"格式暂不支持,只支持avi、mpg、wmv、3gp、mp4、mov、vob、flv、swf格式，请重新上传！";
			}


		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		JsonResult jr = new JsonResult(resultCode, resultMsg, jsonObject);
		response.setContentType("text/html;charset=UTF-8"); 
		response.getWriter().write(JSONObject.fromObject(jr).toString());
	}

	@RequestMapping(value = "/removeFile", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult removeFile(String videoPath,String fileName)throws  Exception{
		JsonResult jr = new JsonResult(0, null, null);
		String basePath = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH, CmsCC.VIDEO_PATH);
		String path = basePath+File.separator+videoPath+File.separator+fileName;
		if(!StringUtil.isEmpty(path)){
			FileTools.deleteFile(path);
		}else {
			jr.setResultMsg("文件路径参数为空");
		}

		return jr;
	}
}
