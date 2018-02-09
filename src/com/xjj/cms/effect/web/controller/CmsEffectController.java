package com.xjj.cms.effect.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.util.PropertiesUtil;
import com.xjj.cms.base.util.Random;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.effect.model.CmsEffect;
import com.xjj.cms.effect.service.ICmsEffectService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.web.filter.WebContext;
import com.xjj.jdk17.utils.DateUtil;

/**
 * 特效controller
 * <p>
 * 
 * @author yeyunfeng 2015年6月10日 下午7:48:08
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年6月10日
 * @modify by reason:{方法名}:{原因}
 */
@Controller
@RequestMapping(value = "/cms/effect")
public class CmsEffectController extends CMSBaseController<CmsEffect> {

	@Autowired
	@Qualifier("cmsEffectService")
	private ICmsEffectService cmsEffectService;

	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}

	/**
	 * 根据条件查询
	 * 
	 * @author yeyunfeng 2015年6月10日 下午8:24:59
	 * @param filters
	 * @return
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult list(String filters) throws Exception {
		// json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		/*
		 * if (null == filters ||filters.isEmpty()) { filters =
		 * "{\"skip\":-1,\"pageSize\":-1,\"queryObj\":{\"name\":null}}"; }
		 */
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsEffect> page = cmsEffectService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}

	@RequestMapping(value = "/saveOrUpdate", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void saveOrUpdate(
			@Valid @ModelAttribute("dataform") CmsEffect effect,
			BindingResult bindingResult) throws Exception {

		String orgId = WebContext.getInstance().getHandle().getOrgID();
		String id = effect.getId();
		effect.setOrgId(orgId);
		if (null != id && !id.isEmpty()) { // 修改
			String createTimeStr = (String) bindingResult
					.getFieldValue("createTime");
			Date createTime = DateUtil.getString2Date(createTimeStr,
					"yyyy-MM-dd HH:mm:ss");
			effect.setCreateTime(createTime);
			this.setCmsBaseModel(effect, BaseConstant.UPDATE);
			effect.update();
		} else { // 新增
			this.setCmsBaseModel(effect, BaseConstant.ADD);
			effect.save();
		}
		// 构造返回对象
		// JsonResult jr = new JsonResult(0, "", article);
		// return jr;
	}

	@RequestMapping(value = "/upPcImg", method = { RequestMethod.POST })
	public void upPcImg(@RequestParam("imgFile") List<MultipartFile> imgFile,
			HttpServletResponse response) throws IOException, Exception {
		int resultCode = 0;
		String resultMsg = "";
		String showPath = "";
		try {

			for (MultipartFile mFile : imgFile) {
				// 得到文件名
				String filename = mFile.getOriginalFilename();
				String ext = filename.substring(filename.lastIndexOf("."));
				// 新文件名
				String newFileName = System.currentTimeMillis()
						+ Random.getStrRandom(3) + ext;
				long imageFileSize = mFile.getSize();
				// 图片保存路径
				String effectImg_path = PropertiesUtil.getProperty(
						CmsCC.WEB_CONFIG_ZH, CmsCC.EFFECT_IMAGE_PATH);
				String imageFormat = PropertiesUtil.getProperty(
						CmsCC.FILE_FORMAT, CmsCC.IMAGES_FORMAT);
				String limitImgSize_str = PropertiesUtil.getProperty(
						CmsCC.FILE_FORMAT, CmsCC.MENU_IMAGES_SIZE);
				String ui = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH,
						CmsCC.ARTICLE_AFFIX_UI);
				long limitImgSize = Long.valueOf(limitImgSize_str) * 1l * 1024 * 1024;
				if (!com.xjj.cms.base.util.FileTools.checkFormat(filename,
						imageFormat)) {
					resultCode = 1;
					resultMsg = "上传文件格式不对，只支持" + imageFormat + "格式，请检查！";
				} else if (imageFileSize > limitImgSize) {
					resultCode = 2;
					resultMsg = "上传文件不能超过" + limitImgSize + "M，请检查！";
				} else {
					File file = new File(effectImg_path);
					if (!file.exists()) {
						file.mkdir();
					}
					com.xjj.cms.base.util.FileTools.uploadFile(
							mFile.getInputStream(), effectImg_path
									+ File.separator + newFileName);
					// 找到目录保存的最后目录名称
					int menuStrIndex = effectImg_path
							.lastIndexOf(File.separator);
					if (menuStrIndex > 0) {
						showPath = ui + effectImg_path.substring(menuStrIndex)
								+ File.separator + newFileName;
					} else {
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


	@RequestMapping(value = "/delete", method = { RequestMethod.POST,
			RequestMethod.GET })
	public JsonResult delete(String models) throws Exception {
		String id = models.replaceAll("\"", "");
		cmsEffectService.deleteById(id);
		JsonResult jr = new JsonResult(0, "", null);
		// 构造返回对象
		return jr;
	}

}
