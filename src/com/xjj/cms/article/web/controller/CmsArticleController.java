/**
 * sgyweb
 * CmsMenuController.java
 * @Copyright: 2014 海南新境界软件有限公司. All Right Reserved.
 * @author yeyunfeng 2014-9-2 下午3:25:50 
 * @Description: 本内容仅限于海南新境界软件有限公司内部使用，禁止转发.
 */
package com.xjj.cms.article.web.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.htmlparser.Node;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.service.IZorganizeService;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.article.model.ArticleCount;
import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.cms.article.model.ImgNode;
import com.xjj.cms.article.service.ICmsArticleHistoryService;
import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.article.util.Crawler;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.util.ImageCompressUtil;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.web.filter.WebContext;
import com.xjj.jdk17.utils.DateUtil;
import com.xjj.jdk17.utils.StringUtil;

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
@RequestMapping(value = "/cms/article")
public class CmsArticleController extends CMSBaseController<CmsArticle> {

	@Autowired
	@Qualifier("cmsArticleService")
	private ICmsArticleService cmsArticleService;

	@Autowired
	@Qualifier("cmsArticleHistoryService")
	private ICmsArticleHistoryService cmsArticleHistoryService;
	
	@Autowired
	@Qualifier("ZorganizeService")
	private IZorganizeService iZorganizeService;
	
	@Autowired
	@Qualifier("cmsMenuService")
	private ICmsMenuService cmsMenuService;

	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}

	/**
	 * 根据条件查询
	 * 
	 * @author yeyunfeng 2014-9-2 下午3:28:07
	 * @param filters
	 *            过滤条件
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult list(String filters) throws Exception {
		// json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		if (null == filters || filters.isEmpty()) {
			filters = "{\"skip\":-1,\"pageSize\":-1,\"}";
		}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsArticle> page = cmsArticleService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/byuser4List", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult byuser4List(String filters) throws Exception {
		// json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		if (null == filters || filters.isEmpty()) {
			filters = "{\"skip\":-1,\"pageSize\":-1,\"}";
		}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap
				.get("queryObj");
		String menuId = (String) queryMap.get("menuId");
		queryMap.remove("menuId");
		String userId = WebContext.getInstance().getHandle().getUserId();
		// queryMap.put("isDelete", "0");
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsArticle> page = cmsArticleService.getByuser4List(menuId,
				userId, filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/allList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult allList(String filters) throws Exception {
		// json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		if (null == filters || filters.isEmpty()) {
			filters = "{\"skip\":-1,\"pageSize\":-1,\"}";
		}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap
				.get("queryObj");
		String menuId = (String) queryMap.get("menuId");
		queryMap.remove("menuId");
		// queryMap.put("isDelete", "0");
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsArticle> page = cmsArticleService.getALLArticlesByMenuId(
				menuId, filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}

	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public JsonResult add(HttpServletRequest request, String models)
			throws IOException, Exception {
		if (models != null && models.length() > 1) {
			JSONObject jsonObject = JSONObject.fromObject(models);
			String title = jsonObject.getString("title");
			String titleColor = jsonObject.getString("titleColor");
			String titleSize = jsonObject.getString("titleSize");
			String menuId = jsonObject.getString("menuId");
			String menuName = jsonObject.getString("menuName");
			String author = jsonObject.getString("author");
			String source = jsonObject.getString("source");
			String publicTime = jsonObject.getString("publicTime");
			String readCount = jsonObject.getString("readCount");
			String keyWord = jsonObject.getString("keyWord");
			String content = jsonObject.getString("content");
			String summary = jsonObject.getString("summary");
			String behalf_imageUrl = jsonObject.getString("behalf_imageUrl");
			String behalf_image_width = jsonObject.getString("behalf_image_width");
			String behalf_image_height = jsonObject.getString("behalf_image_height");

			List<CmsAffix> affixList = new ArrayList<CmsAffix>();

			CmsArticle article = new CmsArticle(title, titleColor, titleSize, menuId, menuName, author, source, readCount, keyWord, content,
					behalf_imageUrl, summary, "", "", DateUtil.getString2Date(publicTime, "yyyy-MM-dd HH:mm:ss"), "", null, 0,
					affixList, behalf_image_width, behalf_image_height);
			this.setCmsBaseModel(article, BaseConstant.ADD);
			cmsArticleService.save(article);
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public JsonResult update(String models) throws Exception {
		if (models != null && models.length() > 1) {
			JSONObject jsonObject = JSONObject.fromObject(models);
			String id = jsonObject.getString("id");
			String title = jsonObject.getString("title");
			String titleColor = jsonObject.getString("titleColor");
			String titleSize = jsonObject.getString("titleSize");
			String menuId = jsonObject.getString("menuId");
			String menuName = jsonObject.getString("menuName");
			String author = jsonObject.getString("author");
			String source = jsonObject.getString("source");
			String publicTime = jsonObject.getString("publicTime");
			String readCount = jsonObject.getString("readCount");
			String keyWord = jsonObject.getString("keyWord");
			String content = jsonObject.getString("content");
			String summary = jsonObject.getString("summary");
			String behalf_imageUrl = jsonObject.getString("behalf_imageUrl");

			CmsArticle temp_articleArticle = cmsArticleService.get(id);
			temp_articleArticle.setTitle(title);
			temp_articleArticle.setTitleColor(titleColor);
			temp_articleArticle.setTitleSize(titleSize);
			temp_articleArticle.setMenuId(menuId);
			temp_articleArticle.setMenuName(menuName);
			temp_articleArticle.setAuthor(author);
			temp_articleArticle.setPublicTime(DateUtil.getString2Date(
					publicTime, "yyyy-MM-dd HH:mm:ss"));
			temp_articleArticle.setReadCount(readCount);
			temp_articleArticle.setKeyWord(keyWord);
			temp_articleArticle.setContent(content);
			temp_articleArticle.setSummary(summary);
			temp_articleArticle.setBehalf_imageUrl(behalf_imageUrl);
			temp_articleArticle.setSource(source);
			this.setCmsBaseModel(temp_articleArticle, BaseConstant.UPDATE);
			cmsArticleService.update(temp_articleArticle);
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	@RequestMapping(value = "/saveOrUpdate", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void saveOrUpdate(
			@Valid @ModelAttribute("dataform") CmsArticle article,
			BindingResult bindingResult) throws Exception {

		String publicTimeStr = (String) bindingResult
				.getFieldValue("publicTime");
		Date publicTime = DateUtil.getString2Date(publicTimeStr,
				"yyyy-MM-dd HH:mm:ss");
		String orgId = WebContext.getInstance().getHandle().getOrgID();
		String oraName = WebContext.getInstance().getHandle().getOrgName();
		String id = article.getId();
		article.setPublicTime(publicTime);
		article.setOrgId(orgId);
		article.setOrgName(oraName);
		article.setCheck_log(0);
		
		String behalf_imageFath = article.getBehalf_imageUrl();
		if(!StringUtil.isEmpty(behalf_imageFath)){
			String aui = com.xjj.cms.base.util.PropertiesUtil.getProperty(
					CmsCC.WEB_CONFIG_ZH, CmsCC.ARTICLE_AFFIX_UI);
			String article_affix_path = com.xjj.cms.base.util.PropertiesUtil.getProperty(
					CmsCC.WEB_CONFIG_ZH, CmsCC.ARTICLE_AFFIX_PATH)+behalf_imageFath;
			String[] path_sp = article_affix_path.split(aui);
			article_affix_path =path_sp[0]+path_sp[1];
			String width = article.getBehalf_image_width();
			String height = article.getBehalf_image_height();
			if (!StringUtil.isEmpty(width) && !StringUtil.isEmpty(height)){
				int width_i = Integer.parseInt(width);
				int height_i = Integer.parseInt(height);
				ImageCompressUtil.zipImageFile(article_affix_path, width_i, height_i, 1f, "_small");
				int index =  behalf_imageFath.indexOf(".");
				behalf_imageFath = behalf_imageFath.substring(0,index)+"_small."+behalf_imageFath.substring(index+1);
				article.setBehalf_imageUrl(behalf_imageFath);
			}
		}
		
		if (null != id && !id.isEmpty()) { // 修改
			String createTimeStr = (String) bindingResult
					.getFieldValue("createTime");
			Date createTime = DateUtil.getString2Date(createTimeStr,
					"yyyy-MM-dd HH:mm:ss");
			article.setCreateTime(createTime);
			this.setCmsBaseModel(article, BaseConstant.UPDATE);
			article.update();
		} else { // 新增
			this.setCmsBaseModel(article, BaseConstant.ADD);
			article.save();
		}
		// 构造返回对象
		// JsonResult jr = new JsonResult(0, "", article);
		// return jr;
	}
	
	@RequestMapping(value = "/saveOrUpdateHistory", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void saveOrUpdateHistory(
			@Valid @ModelAttribute("dataform") CmsArticleHistory article,
			BindingResult bindingResult) throws Exception {

		String publicTimeStr = (String) bindingResult
				.getFieldValue("publicTime");
		Date publicTime = DateUtil.getString2Date(publicTimeStr,
				"yyyy-MM-dd HH:mm:ss");
		String orgId = WebContext.getInstance().getHandle().getOrgID();
		String oraName = WebContext.getInstance().getHandle().getOrgName();
		String id = article.getId();
		article.setPublicTime(publicTime);
		article.setOrgId(orgId);
		article.setOrgName(oraName);
		if (null != id && !id.isEmpty()) { // 修改
			String createTimeStr = (String) bindingResult
					.getFieldValue("createTime");
			Date createTime = DateUtil.getString2Date(createTimeStr,
					"yyyy-MM-dd HH:mm:ss");
			article.setCreateTime(createTime);
			this.setCmsBaseModel(article, BaseConstant.UPDATE);
			article.update();
		} else { // 新增
			this.setCmsBaseModel(article, BaseConstant.ADD);
			article.save();
		}
		// 构造返回对象
		// JsonResult jr = new JsonResult(0, "", article);
		// return jr;
	}
	

	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws Exception {
		String aritcleId = models.replaceAll("\"", "");
		JsonResult jr = new JsonResult(0, "", null);
		// CmsArticle model = JsonUtil.fromJson(models, CmsArticle.class);
		CmsArticle model = cmsArticleService.get(aritcleId);
		cmsArticleService.delete(model);
		// 构造返回对象
		return jr;
	}

	@RequestMapping(value = "/check")
	public JsonResult check(String models) throws Exception {
		String aritcleId = models.replaceAll("\"", "");
		JsonResult jr = new JsonResult(0, "", null);
		// CmsArticle model = JsonUtil.fromJson(models, CmsArticle.class);
		CmsArticle model = cmsArticleService.get(aritcleId);
		model.setCheck_log(1);
		cmsArticleService.saveOrUpdate(model);
		// 构造返回对象
		return jr;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/historyList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult historyList(String filters) throws Exception {
		// json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		if (null == filters || filters.isEmpty()) {
			filters = "{\"skip\":-1,\"pageSize\":-1,\"}";
		}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsArticleHistory> page = cmsArticleHistoryService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}

	@RequestMapping(value = "/tuiHui")
	public JsonResult tuiHui(String models) throws Exception {
		String aritcleId = models.replaceAll("\"", "");
		JsonResult jr = new JsonResult(0, "", null);
		// CmsArticle model = JsonUtil.fromJson(models, CmsArticle.class);
		CmsArticleHistory model = cmsArticleHistoryService.get(aritcleId);

		CmsArticle history = history2article(model);
		history.setCheck_log(4);
		cmsArticleService.saveOrUpdate(history);
		cmsArticleHistoryService.delete(model);
		// 构造返回对象
		return jr;
	}
	
	@RequestMapping(value = "/cdDel")
	public JsonResult cdDel(String models) throws Exception {
		String aritcleId = models.replaceAll("\"", "");
		JsonResult jr = new JsonResult(0, "", null);
		CmsArticleHistory model = cmsArticleHistoryService.get(aritcleId);
		if(model.getIsDelete().equals("1")){
			cmsArticleHistoryService.delete(model);
		}else{
			model.setIsDelete("1");
			cmsArticleHistoryService.update(model);
		}
		// 构造返回对象
		return jr;
	}
	
	@RequestMapping(value = "/dCheck")
	public JsonResult dCheck(String models) throws Exception {
		String aritcleId = models.replaceAll("\"", "");
		JsonResult jr = new JsonResult(0, "", null);
		// CmsArticle model = JsonUtil.fromJson(models, CmsArticle.class);
		CmsArticle model = cmsArticleService.get(aritcleId);

		CmsArticleHistory history = article2history(model);
		history.setCheck_log(2);
		history.setIsDelete("0");
		cmsArticleHistoryService.saveOrUpdate(history);
		cmsArticleService.delete(model);
		// 构造返回对象
		return jr;
	}
	
	@RequestMapping(value = "/dNotCheck")
	public JsonResult dNotCheck(String models) throws Exception {
		String aritcleId = models.replaceAll("\"", "");
		JsonResult jr = new JsonResult(0, "", null);
		// CmsArticle model = JsonUtil.fromJson(models, CmsArticle.class);
		CmsArticle model = cmsArticleService.get(aritcleId);

		CmsArticleHistory history = article2history(model);
		history.setCheck_log(3);
		history.setIsDelete("0");
		cmsArticleHistoryService.saveOrUpdate(history);
		cmsArticleService.delete(model);
		// 构造返回对象
		return jr;
	}
	
	
	private CmsArticleHistory article2history(CmsArticle model){
		String title = model.getTitle();
		String titleColor = model.getTitleColor();
		String titleSize = model.getTitleSize();
		String menuId = model.getMenuId();
		String menuName = model.getMenuName();
		String author = model.getAuthor();
		String source = model.getSource();
		String readCount = model.getReadCount();
		String keyWord = model.getKeyWord();
		String content = model.getContent();
		String behalf_imageUrl = model.getBehalf_imageUrl();
		String summary = model.getSummary();
		String orgId = model.getOrgId();
		String orgName = model.getOrgName();
		Date publicTime = model.getPublicTime();
		String linkUrl = model.getLinkUrl();
		String ifComment = model.getIfComment();
		Integer check_log = model.getCheck_log();
		List<CmsAffix> cmsAffixs = model.getCmsAffixs();
		String behalf_image_width = model.getBehalf_image_width();
		String behalf_image_height = model.getBehalf_image_height();
		
		CmsArticleHistory history = new CmsArticleHistory(title, titleColor,
				titleSize, menuId, menuName, author, source, readCount,
				keyWord, content, behalf_imageUrl, summary, orgId, orgName,
				publicTime, linkUrl, ifComment, check_log, 0, cmsAffixs,behalf_image_width,behalf_image_height);
		
		history.setCreateTime(model.getCreateTime());
		history.setCreatUserId(model.getCreatUserId());
		history.setCreatUserName(model.getCreatUserName());
		history.setEndModifyTime(model.getEndModifyTime());
		history.setEndModifyUserId(model.getEndModifyUserId());
		history.setEndModifyUserName(model.getEndModifyUserName());
		
		return history;
	}
	
	
	private CmsArticle history2article(CmsArticleHistory model){
		String title = model.getTitle();
		String titleColor = model.getTitleColor();
		String titleSize = model.getTitleSize();
		String menuId = model.getMenuId();
		String menuName = model.getMenuName();
		String author = model.getAuthor();
		String source = model.getSource();
		String readCount = model.getReadCount();
		String keyWord = model.getKeyWord();
		String content = model.getContent();
		String behalf_imageUrl = model.getBehalf_imageUrl();
		String summary = model.getSummary();
		String orgId = model.getOrgId();
		String orgName = model.getOrgName();
		Date publicTime = model.getPublicTime();
		String linkUrl = model.getLinkUrl();
		String ifComment = model.getIfComment();
		Integer check_log = model.getCheck_log();
		List<CmsAffix> cmsAffixs = model.getCmsAffixs();
		String behalf_image_width = model.getBehalf_image_width();
		String behalf_image_height = model.getBehalf_image_height();
		
//		CmsArticle history = new CmsArticle(title, titleColor,
//				titleSize, menuId, menuName, author, source, readCount,
//				keyWord, content, behalf_imageUrl, summary, orgId, orgName,
//				publicTime, linkUrl, ifComment, check_log, cmsAffixs);
		
		CmsArticle history = new CmsArticle(title, titleColor, titleSize, menuId, menuName, author, source,
				readCount, keyWord, content, behalf_imageUrl, summary, orgId, orgName, publicTime, linkUrl, ifComment, check_log, cmsAffixs, behalf_image_width, behalf_image_height);
		
		history.setCreateTime(model.getCreateTime());
		history.setCreatUserId(model.getCreatUserId());
		history.setCreatUserName(model.getCreatUserName());
		history.setEndModifyTime(model.getEndModifyTime());
		history.setEndModifyUserId(model.getEndModifyUserId());
		history.setEndModifyUserName(model.getEndModifyUserName());
		
		return history;
	}
	
	
	@RequestMapping(value = "/getArticleById")
	public JsonResult getArticleById(String articleId) throws Exception {
		// CmsArticle model = JsonUtil.fromJson(models, CmsArticle.class);
		CmsArticle model = cmsArticleService.get(articleId);
		JsonResult jr = new JsonResult(0, "", model);
		// 构造返回对象
		return jr;
	}

	@RequestMapping(value = "/findArticleBySearch")
	public JsonResult findArticleBySearch(HttpServletRequest request)
			throws Exception {
		JsonResult jr = new JsonResult(0, "", null);
		String searchValue = request.getParameter("searchValue");
		String orgCode = request.getParameter("orgCode");
		List<CmsArticleHistory> list = cmsArticleHistoryService.findArticleBySearch(searchValue,orgCode);
		if (list.size() > 0) {
			jr.setResultData(list);
		}
		return jr;
	}

	/**
	 * 获取代表性图片
	 * 
	 * @author yeyunfeng 2014-9-17 上午9:46:06
	 * @param pageContent
	 * @return
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/getImg", method = { RequestMethod.POST })
	public JsonResult getImg(String pageContent) throws Exception {
		List<ImgNode> imgList = new ArrayList<ImgNode>();
		String article_affix_ui = com.xjj.cms.base.util.PropertiesUtil
				.getProperty(CmsCC.WEB_CONFIG_ZH, CmsCC.ARTICLE_AFFIX_UI);
		if (null != pageContent && !pageContent.isEmpty()) {
			NodeList imgNodelist = Crawler.getNodeList(pageContent, "IMG",
					"UTF-8");
			if (null != imgNodelist) {
				int imgSize = imgNodelist.size();
				for (int i = 0; i < imgSize; i++) {
					Node imgNode = imgNodelist.elementAt(i);
					if (imgNode instanceof ImageTag) {
						ImageTag imgTag = (ImageTag) imgNode;
						String title = imgTag.getAttribute("title");
						String src = imgTag.getAttribute("src");
						int index = src.indexOf(article_affix_ui);
						if (index > 0) {
							src = src.substring(index);
						}
						String alt = imgTag.getAttribute("alt");
						if (!StringUtil.isEmpty(title)) {
							ImgNode imgModel = new ImgNode(title, alt, src);
							imgList.add(imgModel);
						}
					}
				}
			}
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", imgList);
		return jr;
	}

	/**
	 * 根据传入的参数获取部门发文量并封装好
	 * @param request
	 * @return 
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getDataByOrgAndArticle", method = { RequestMethod.POST })
	public JsonResult getDataByOrgAndArticle(HttpServletRequest request) throws Exception {
		List<ArticleCount> list = new ArrayList<>();
		String parentId = request.getParameter("parentId");
		String startTime = request.getParameter("startDate");
		String endTime = request.getParameter("endDate");
		List<Zorganize> orgs = iZorganizeService.getChildOrg(parentId);
		if(null != orgs && orgs.size() > 0){
			for (int i = 0; i < orgs.size(); i++) {
				ArticleCount ac = new ArticleCount();
				Zorganize org = orgs.get(i);
				Integer articleNum = cmsArticleHistoryService.getArticleNumByOrg(org,startTime,endTime);
				ac.setOrg(org);
				ac.setNum(articleNum);
				list.add(ac);
			}
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", list);
		return jr;
	}
	
	@RequestMapping(value = "/top", method = { RequestMethod.POST })
	public JsonResult top(HttpServletRequest request) throws Exception {
		String articleId = request.getParameter("articleId");
		CmsArticleHistory article = cmsArticleHistoryService.get(articleId);
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		String userId = WebContext.getInstance().getHandle().getUserId();
		String userName = WebContext.getInstance().getHandle().getUserName();
		article.setEndModifyTime(currentTime);
		article.setEndModifyUserId(userId);
		article.setEndModifyUserName(userName);
		article.setTop(1);
		cmsArticleHistoryService.update(article);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	
	@RequestMapping(value = "/cancleTop", method = { RequestMethod.POST })
	public JsonResult cancleTop(HttpServletRequest request) throws Exception {
		String articleId = request.getParameter("articleId");
		CmsArticleHistory article = cmsArticleHistoryService.get(articleId);
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		String userId = WebContext.getInstance().getHandle().getUserId();
		String userName = WebContext.getInstance().getHandle().getUserName();
		article.setEndModifyTime(currentTime);
		article.setEndModifyUserId(userId);
		article.setEndModifyUserName(userName);
		article.setTop(0);
		cmsArticleHistoryService.update(article);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	
	@RequestMapping(value = "/articleTJ", method = { RequestMethod.POST })
	public JsonResult articleTJ(HttpServletRequest request) throws Exception {
		String articleId = request.getParameter("articleId");
		String copyMenuId = request.getParameter("copyMenuId");
		CmsMenu menu  = cmsMenuService.get(copyMenuId);
		CmsArticleHistory article = cmsArticleHistoryService.get(articleId);
		CmsArticleHistory newArticle = new CmsArticleHistory(article.getTitle(), article.getTitleColor(), article.getTitleSize(), copyMenuId, menu.getMenuName(), 
										article.getAuthor(), article.getSource(), article.getReadCount(), article.getKeyWord(), article.getContent(), article.getBehalf_imageUrl(), 
										article.getSummary(), article.getOrgId(), article.getOrgName(), article.getPublicTime(), article.getLinkUrl(), article.getIfComment(), 
										article.getCheck_log(), article.getTop(), null,article.getBehalf_image_width(),article.getBehalf_image_height());
		newArticle.setIsDelete("0");
		newArticle.setCreateTime(article.getCreateTime());
		newArticle.setCreatUserId(article.getCreatUserId());
		newArticle.setCreatUserName(article.getCreatUserName());
		newArticle.setEndModifyTime(article.getEndModifyTime());
		newArticle.setEndModifyUserId(article.getEndModifyUserId());
		newArticle.setEndModifyUserName(article.getEndModifyUserName());
		cmsArticleHistoryService.save(newArticle);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	
}
