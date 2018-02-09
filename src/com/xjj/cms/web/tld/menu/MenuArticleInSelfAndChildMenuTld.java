package com.xjj.cms.web.tld.menu;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.cms.article.service.ICmsArticleHistoryService;
import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.MenuPage;
import com.xjj.cms.base.util.NumberTools;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.cms.web.tld._ShowArticleCommon;
import com.xjj.cms.web.tld.channel.ChannelMainTld;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.SpringContextUtil;
import com.xjj.jdk17.utils.StringUtil;
/**
 * 栏目文章页标签
 * @author fengjunkong
 * @date 2014-9-4
 */
public class MenuArticleInSelfAndChildMenuTld  extends BodyTagSupport {
	private Integer pageNo;
	/**
	 * 每页显示文章条数
	 */
	private Integer pageSize;
	/**
	 * 文章标题长度
	 */
	private Integer titleLength; 
	/**
	 * 显示"new"图片的样式和不显示"new"的样式，中间以";"分隔
	 * 第一位的是显示图片的样式，第二位的是不显示图片的样式
	 */
	private String newCss;
	/**
	 * 显示样式
	 */
	private String styleClass;
	/**
	 * 获取栏目id
	 */
	private String menuId;
	/**
	 * 是否包含图片
	 */
	private Boolean hasImage;
	/**
	 * 分页方式 js/url，默认为url
	 */
	private String fyType;
	
	private String cId;
	
	private Boolean byCId;

	public String getFyType() {
		return fyType;
	}

	public void setFyType(String fyType) {
		this.fyType = fyType;
	}

	public int doEndTag() throws JspException {
		try {
		
		//System.out.println("-----------MenuArticleInSelfAndChildMenuTld-----doEndTag---------");
		ICmsArticleService cmsArticleService = (ICmsArticleService)SpringContextUtil.getInstance().getBean("cmsArticleService");
		ICmsArticleHistoryService cmsArticleHistoryService = (ICmsArticleHistoryService)SpringContextUtil.getInstance().getBean("cmsArticleHistoryService");
		ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
		ICmsMenuService cmsMenuService =  (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
		
		String searchValue = null;
		try{
			searchValue = URLDecoder.decode(pageContext.getRequest().getParameter("value") , "UTF-8");
		}catch(Exception e){
			searchValue = "";
		}
		
		if(pageNo == null){
			pageNo = 0;	
		}
		CmsMenu cm;
		if(byCId!=null && byCId){
			String cId = pageContext.getRequest().getParameter("cId");
			if(cId!=null)
				 cm = cmsMenuService.get(cId);
			else{

				if(StringUtils.isNotBlank(menuId)){
					cm = cmsMenuService.get(menuId);
				}else{
					//返回父标签 
					MenuLoopChildTld menuLoopChildTld = (MenuLoopChildTld) TagSupport.findAncestorWithClass(this, MenuLoopChildTld.class);
					if(menuLoopChildTld == null){
						//ChannelGetObjectTld处赋值
						cm = (CmsMenu)pageContext.getAttribute("curMenu");
					}else{
						cm = menuLoopChildTld.curChildCm;
					}
				}
			}
		}else{
			if(StringUtils.isNotBlank(menuId)){
					cm = cmsMenuService.get(menuId);
			}else{
				//返回父标签 
				MenuLoopChildTld menuLoopChildTld = (MenuLoopChildTld) TagSupport.findAncestorWithClass(this, MenuLoopChildTld.class);
				if(menuLoopChildTld == null){
					//ChannelGetObjectTld处赋值
					cm = (CmsMenu)pageContext.getAttribute("curMenu");
				}else{
					cm = menuLoopChildTld.curChildCm;
				}
			}
		}
		
		if(cm == null){
//			ChannelMainTld channelMainTld = (ChannelMainTld) TagSupport.findAncestorWithClass(this, ChannelMainTld.class);
//			if(channelMainTld != null){
//				CmsChannel cc = channelMainTld.getChl();
//				
//				Set setCci = cc.getSetCmsChannelItem();
//				if(setCci != null && !setCci.isEmpty()){
//					Iterator itr = setCci.iterator();
//					CmsChannelItem cci = (CmsChannelItem)itr.next();
//					cm = (CmsMenu)cmsMenuManager.getCmsMenuDao().get(CmsMenu.class, cci.getItem_id());
//				}
//			}
			
		}
		//部门拼音代码
		String pyCode = (String)pageContext.getRequest().getAttribute("orgCode");
		String pyName = (String)pageContext.getRequest().getAttribute("orgName");
		
		if(cm != null && cm.getShowMode() != null && cm.getShowMode() == 1){
			//若主送栏目无文章，则获取所有文章
			//List<CmsArticle> listArticle = new ArrayList<CmsArticle>();
			List<CmsArticleHistory> listArticle = new ArrayList<CmsArticleHistory>();
			String cmId = cm.getId();
			if (CmsCC.ENABLED_MENU_FJSQ) { //栏目分级授权开启
				//listArticle = cmsArticleService.getOrgArticlesByMenuId(cmId, pyCode);
				listArticle = cmsArticleHistoryService.getOrgArticlesByMenuId(cmId, pyCode);
			}else{
				//listArticle = cmsArticleService.getALLArticlesByMenuId(cmId);
				listArticle = cmsArticleHistoryService.getALLArticlesByMenuId(cmId);
			}

			
			//System.out.println("-----------listArticle = " + listArticle.size());
			
			
//			if(listArticle.isEmpty()){
//				listArticle = cmsArticleManager.getArticleListByMenuId(cm.getId());
//			}
			
			//直接显示文章内容
			JspWriter out = pageContext.getOut();
			try {
				String outContent = "";
				for(int i = 0 ; i < listArticle.size() && i<1 ; i ++ ){
					//CmsArticle ca = (CmsArticle)listArticle.get(i);
					CmsArticleHistory ca = (CmsArticleHistory)listArticle.get(i);
			        if(ca != null){
			        	outContent += ca.getContent();
			        }
				}
				out.print("<div id=\"acontent\">"+outContent+"</div>");
			} catch (IOException e) {
				e.printStackTrace();
			} catch(NullPointerException ne){
				try{
					out.print("");
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			pageContext.setAttribute("pageStr", "");
		}else{
			String strPageNo = pageContext.getRequest().getParameter("pageNo");
			
			
			if(StringUtils.isEmpty(strPageNo)){
				pageNo = 1;
			}else{
				pageNo = NumberTools.formatObject2IntDefaultParamNoExp(strPageNo, 1);
			}
			
			Integer[] arrayMenuId = null;
			
//			if(cm != null){
//				List list = new ArrayList();
//				list.add(cm.getId());
//				//int fs=list.size();
//				arrayMenuId = new Integer[list.size()];
//				for(int i = 0 ; i < list.size() ; i ++ ){
//					arrayMenuId[i] = (Integer)list.get(i);
//				}
//			}
			//PageSupport pageArticle = cmsArticleManager.getCheckedArticleByMenuIdsForPage(arrayMenuId , pageSize , pageNo , map);
			Map<String, Object>  map = new HashMap<String, Object> ();
			map.put("skip", pageNo*pageSize-pageSize);
			map.put("pageSize", pageSize);
			Map<String, Object>  queryObjMap = new HashMap<String, Object> ();
			queryObjMap.put("title",searchValue);
			//部门拼音代码
			if (CmsCC.ENABLED_MENU_FJSQ) { //栏目分级授权开启
				if (StringUtil.isEmpty(pyCode)) {
					throw new JspException("获取部门代码异常，请联系管理员！");
				}else{
					queryObjMap.put("fjsq_orgCode", pyCode);
				}
			}
			queryObjMap.put("check_log",2);
			//显示未逻辑删除的文章
			queryObjMap.put("isDelete", "0");
			//这两个专栏进来的不进行文章置顶处理
			if(cm.getMenuName().equals("专题-机关专栏") || cm.getMenuName().equals("专题-乡镇专栏")){
				queryObjMap.put("notTop", "true");
			}
			map.put("queryObj", queryObjMap);
			
			//Page<CmsArticle> pageArticle = cmsArticleService.getALLArticlesByMenuId(cm.getId(),map);
			Page<CmsArticleHistory> pageArticle = cmsArticleHistoryService.getALLArticlesByMenuId(cm.getId(),map);
			if(pageArticle == null){
				
			}else{
					
				String strPage;
				if(StringUtils.isBlank(fyType) || fyType.equals("url")){
					strPage = MenuPage.getInstance().createPageStr((HttpServletRequest)pageContext.getRequest(), pageArticle, pageSize );
				}else{
					strPage = MenuPage.getInstance().createPageStrForJs((HttpServletRequest)pageContext.getRequest(),pageArticle, pageSize);
				}
				pageContext.setAttribute("pageStr", strPage);
				
				List listArticle = pageArticle.getItems();
				Map param = new HashMap();
				param.put("titleLength", titleLength);
				param.put("styleClass", styleClass);
				param.put("newcss", newCss);
				param.put("startPageNo", (pageNo-1)*pageSize);
				param.put("pageNo", pageNo);
				param.put("pageSize", pageSize);
				param.put("curMenu", cm);
				if (CmsCC.ENABLED_MENU_FJSQ) { //栏目分级授权开启
					//部门拼音代码
					param.put("orgCode", pyCode);
					param.put("orgName", pyName);
				}
				String body ="";
				BodyContent bc = getBodyContent();
				if(listArticle.size()!=0){
				
				for(int i = 0 ; i < listArticle.size() ; i ++ ){
					//CmsArticle cmsArticle = (CmsArticle)(listArticle.get(i));
					CmsArticleHistory cmsArticle = (CmsArticleHistory)(listArticle.get(i));
					
					param.put("arrayMenuId", cmsArticle.getMenuId());
					body = _ShowArticleCommon.getBodyStr(bc.getString(), cmsArticle, param , i);
			        try {
			            pageContext.getOut().print(body);
			        } catch (IOException e) {
			        	e.printStackTrace();
			        }
					
				}
				
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}


		return this.EVAL_PAGE;
	}

	public String getNewCss() {
		return newCss;
	}

	public void setNewCss(String newCss) {
		this.newCss = newCss;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTitleLength() {
		return titleLength;
	}

	public void setTitleLength(Integer titleLength) {
		this.titleLength = titleLength;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public Boolean getHasImage() {
		return hasImage;
	}

	public void setHasImage(Boolean hasImage) {
		this.hasImage = hasImage;
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public Boolean getByCId() {
		return byCId;
	}

	public void setByCId(Boolean byCId) {
		this.byCId = byCId;
	}
	
}
