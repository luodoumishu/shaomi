package com.xjj.cms.file.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.util.PropertiesUtil;
import com.xjj.cms.base.util.Random;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.file.model.CmsFile;
import com.xjj.cms.file.service.ICmsFileService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;

@Controller
@RequestMapping(value = "/cms/file")
public class CmsFileController extends CMSBaseController<CmsFile> {
	@Autowired
	@Qualifier("cmsFileService")
	private ICmsFileService cmsFileService;

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
		Page<CmsFile> page = cmsFileService.query(filtersMap);

		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public void save(CmsFile cmsFile, @RequestParam MultipartFile[] files,
			HttpServletResponse response) throws IOException, Exception {
		
		int resultCode = 0;
		String resultMsg = "";
		String showPath = "";
		List<CmsAffix> affixList = new ArrayList<CmsAffix>();
		String fileId = cmsFile.getId();
		if (fileId.isEmpty()) {
			this.setCmsBaseModel(cmsFile, BaseConstant.ADD);
		} else {
			this.setCmsBaseModel(cmsFile, BaseConstant.UPDATE);
		}
		try {
			for (MultipartFile mFile : files) {
				// 得到文件名
				CmsAffix affix = new CmsAffix();
				this.setCmsBaseModel(affix, BaseConstant.ADD);
				String filename = mFile.getOriginalFilename();
				if (!filename.isEmpty()) {
					String ext = filename.substring(filename.lastIndexOf("."));
					// 新文件名
					String newFileName = System.currentTimeMillis()
							+ Random.getStrRandom(3) + ext;
					long fileSize = mFile.getSize();
					// 文件保存路径
					Date addDate = new Date();
					Calendar c = Calendar.getInstance();
					c.setTime(addDate);
					int aYear = c.get(Calendar.YEAR);
					int aMonth = c.get(Calendar.MONTH) + 1;
					String hm_path = File.separator + aYear + File.separator
							+ aMonth;
					String file_path = PropertiesUtil.getProperty(
							CmsCC.WEB_CONFIG_ZH, CmsCC.FILE_PATH) + hm_path;
					String fileFormat = PropertiesUtil.getProperty(
							CmsCC.FILE_FORMAT, CmsCC.AFFIX_FORMAT);
					String limitFileSize_str = PropertiesUtil.getProperty(
							CmsCC.FILE_FORMAT, CmsCC.FILE_SIZE);
					String ui = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH,
							CmsCC.ARTICLE_AFFIX_UI);

					long limitFileSize = Long.valueOf(limitFileSize_str) * 1l * 1024 * 1024;
					boolean affixCheckFlag = true;
					if (!com.xjj.cms.base.util.FileTools.checkFormat(filename,
							fileFormat)) {
						resultCode = 1;
						affixCheckFlag = false;
						resultMsg = "上传文件格式不对，只支持" + fileFormat + "格式，请检查！";
						resultMsg = java.net.URLEncoder.encode(resultMsg,"utf-8");
					} else if (fileSize > limitFileSize) {
						resultCode = 1;
						affixCheckFlag = false;
						resultMsg = "上传文件不能超过" + limitFileSize + "M，请检查！";
						resultMsg = java.net.URLEncoder.encode(resultMsg,"utf-8");
					} else {
						File file = new File(file_path);
						if (!file.exists()) {
							file.mkdirs();
						}
						com.xjj.cms.base.util.FileTools.uploadFile(
								mFile.getInputStream(), file_path
										+ File.separator + newFileName);
						// 找到目录保存的最后目录名称
						showPath = ui + hm_path + File.separator + newFileName;
					}
					if(affixCheckFlag){
						affix.setAffix_originalName(filename);
						affix.setAffixName(newFileName);
						affix.setAffixUrl(showPath);
						affixList.add(affix);
						cmsFileService.saveFileAndAffix(cmsFile, affixList);
					}
				} else {
					if (fileId.isEmpty()) {
						cmsFileService.save(cmsFile);
					}else{
						cmsFileService.update(cmsFile);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(resultCode, resultMsg, null);
		response.getWriter().write(JSONObject.fromObject(jr).toString());
	}

	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws Exception {
		CmsFile model = JsonUtil.fromJson(models, CmsFile.class);
		cmsFileService.del(model.getId());
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	@RequestMapping(value = "/findAffix")
	public JsonResult findAffix(String modelId) throws Exception {
		modelId = modelId.replaceAll("\"", "");
		List<CmsAffix> affix = cmsFileService.findAffixByModelId(modelId);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", affix);
		return jr;
	}

	@RequestMapping(value = "/delelAffix")
	public JsonResult delelAffix(String affixId) throws Exception {
		affixId = affixId.replaceAll("\"", "");
		CmsAffix affix = (CmsAffix) cmsFileService.findAffixById(affixId);
		if (affix != null) {
			affix.delete();
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	@RequestMapping(value = "/downAffix")
	public void downAffix(String affixId, HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response) throws Exception {
		// 构造返回对象
		// JsonResult jr = new JsonResult(0, "", null);
		String downLoadPath = "";
		CmsAffix affix = null;
		String file_path = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH,
				CmsCC.ARTICLE_AFFIX_PATH);
		affixId = affixId.replaceAll("\"", "");
		affix = (CmsAffix) cmsFileService.findAffixById(affixId);
		downLoadPath = file_path + affix.getAffixUrl();

		File aFile = new File(downLoadPath);
		if (!aFile.exists()) {
			response.setContentType("text/html;charset=GBK");
			response.getWriter().print("指定文件不存在！");
			return;
		}
		InputStream in = null;
		try {
			response.setContentType("application/x-download");
			response.setCharacterEncoding("UTF-8");
			String fileName = affix.getAffix_originalName();
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);

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

		return;

	}

}
