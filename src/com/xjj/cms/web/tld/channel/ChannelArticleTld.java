package com.xjj.cms.web.tld.channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.cms.article.service.ICmsArticleHistoryService;
import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.NumberTools;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.cms.web.tld._ShowArticleCommon;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.SpringContextUtil;


public class ChannelArticleTld   extends BodyTagSupport {
	/**
	 * 当前页
	 */
	private Integer pageNo;
	
	/**
	 * 跳过多少条
	 */
	private Integer skipNo;
	/**
	 * 文章标题长度
	 */
	private Integer titleLength;
	/**
	 * 备注长度
	 */
	private Integer memoLength;
	
	/**
	 * 是否包含图片
	 */
	private Boolean hasImage;	
	/**
	 * 显示"new"图片的样式和不显示"new"的样式，中间以";"分隔
	 * 第一位的是显示图片的样式，第二位的是不显示图片的样式
	 */
	private String newCss;
	/**
	 * 样式
	 */
	private String styleClass;
	
	/**
	 * 跳过图片新闻条数
	 */
	private Integer skipImgNo;
	
	
	public int doEndTag() throws JspException {
		
		ICmsArticleService cmsArticleService = (ICmsArticleService)SpringContextUtil.getInstance().getBean("cmsArticleService");
		ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
		ICmsMenuService cmsMenuService =  (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
		ICmsArticleHistoryService cmsArticleHistoryService = (ICmsArticleHistoryService)SpringContextUtil.getInstance().getBean("cmsArticleHistoryService");
		
		String strPageNo = pageContext.getRequest().getParameter("pageNo");
		
		if(StringUtils.isEmpty(strPageNo)){
				pageNo = 1;
			//pageNo = pageNo;
		}else{
			if(pageNo!=null){
				pageNo = pageNo;
			}else{
				pageNo = NumberTools.formatObject2IntDefaultParamNoExp(strPageNo, 1);
			}
		}
		if(skipNo==null){
			skipNo = 0;
		}
		if(skipImgNo==null){
			skipImgNo = 0;
		}
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());

		ChannelMainTld channelMainTld = (ChannelMainTld) TagSupport.findAncestorWithClass(this, ChannelMainTld.class);
		Integer pageSize = channelMainTld.getPageSize();
		CmsChannel cmsChl = channelMainTld.getChl();
		List<CmsChannelItem> setChlItem = cmsChannelItemService.findAllItem(cmsChl.getId());
		String[] listMenuId = new String[setChlItem.size()];
		try {
		for(int i =0;i<setChlItem.size();i++)
		 {
			CmsChannelItem ccitem = setChlItem.get(i);
			CmsMenu cm;
		
				cm = cmsMenuService.get(ccitem.getMenuId());
				if(cm.getIsValid() == 0){
					listMenuId[i] =(String) cm.getId();
				}
		 } 
		//部门拼音代码
		String orgCode = (String)pageContext.getRequest().getAttribute("orgCode");
		String orgName = (String)pageContext.getRequest().getAttribute("orgName");
		
		Map<String, Object>  map = new HashMap<String, Object> ();
		map.put("skip", skipNo);
		map.put("pageSize", pageSize);
		Map<String, Object>  queryObjMap = new HashMap<String, Object> ();
		if(hasImage!= null&&hasImage == true){
			queryObjMap.put("hasImage",1);
		}
		if (CmsCC.ENABLED_MENU_FJSQ) { //栏目分级授权开启
			queryObjMap.put("fjsq_orgCode", orgCode);
		}
		
		//显示审核通过的文章
		queryObjMap.put("check_log", 2);
		//显示未逻辑删除的文章
		queryObjMap.put("isDelete", "0");
		map.put("queryObj", queryObjMap);
		/*Page<CmsArticle> page = null;
		if(listMenuId.length != 0){
			page = cmsArticleService.getALLArticlesByMenuIds(listMenuId,map);
		}*/
		Page<CmsArticleHistory> page = null;
		if(listMenuId.length != 0){
			page = cmsArticleHistoryService.getALLArticlesByMenuIds(listMenuId,map);
		}

		if(page == null){ 
		//	 pageContext.getOut().print("暂无信息");
			return this.SKIP_BODY;
		}else{
//			pageContext.setAttribute("pageStr", Page.getInstance().createPageStr((HttpServletRequest)pageContext.getRequest(), pageArticle));
			List listArticle = page.getItems();
			
//			if(cmsChl.getShow_article_type() == null || cmsChl.getShow_article_type().intValue() == 0){
				Map param = new HashMap();
				param.put("titleLength", titleLength);
				param.put("memoLength", memoLength);
				param.put("styleClass", styleClass);
				param.put("newcss", newCss);
				if (CmsCC.ENABLED_MENU_FJSQ) { //栏目分级授权开启
					//部门拼音代码
					param.put("orgCode", orgCode);
					param.put("orgName", orgName);
				}
				for(int i = 0 ; i < listArticle.size() ; i ++ ){
					//CmsArticle cmsArticle = (CmsArticle)(listArticle.get(i));
					CmsArticleHistory cmsArticleHistory = (CmsArticleHistory)(listArticle.get(i));
					BodyContent bc = getBodyContent();
			        String body = _ShowArticleCommon.getBodyStr(bc.getString(), cmsArticleHistory, param , i);
			        try {
			            pageContext.getOut().print(body);
			        } catch (IOException e) {
			        	e.printStackTrace();
			        }
					
				}
//			}else if(cmsChl.getShow_article_type().intValue() == 1){
//				JspWriter out = pageContext.getOut();
//				try {
//					StringBuffer outContent = new StringBuffer();
//					Iterator itrArticle = listArticle.iterator();
//					int length = 0;
//					while(itrArticle.hasNext()){
//						CmsArticle ca = (CmsArticle)itrArticle.next();
//						CmsArticleContent  cac = ca.getContent();
//						if(cac != null){
//							String strContent = StringTools.htmlToText(StringTools.formatNull2Blank(cac.getArticle_content()));
//							length += strContent.length();
//							if(length < titleLength){
//								outContent.append(strContent);
//							}else{
//								outContent.append(strContent);
//								break;
//							}
//						}
//					}
//					String outstr = outContent.toString();
//					if(StringUtils.isNotBlank(outstr)){
//						if(length < titleLength){
//							out.print(outstr);
//						}else{
//							out.print(outContent.toString().substring(0 , titleLength));
//						}
//					}else{
//						out.print("&nbsp;");
//					}
//					
//				} catch (IOException e) {
//					e.printStackTrace();
//				} catch(NullPointerException ne){
//					try{
//						out.print("&nbsp;");
//					}catch(IOException e){
//						e.printStackTrace();
//					}
//				}
//			}

		}
		}catch (Exception e) {
			// TODO Auto-generated catch block
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
	public Integer getTitleLength() {
		return titleLength;
	}
	public void setTitleLength(Integer titleLength) {
		this.titleLength = titleLength;
	}
	public Boolean isHasImage() {
		return hasImage;
	}
	public void setHasImage(Boolean hasImage) {
		this.hasImage = hasImage;
	}
	public Integer getSkipNo() {
		return skipNo;
	}
	public void setSkipNo(Integer skipNo) {
		this.skipNo = skipNo;
	}
	public Integer getMemoLength() {
		return memoLength;
	}
	public void setMemoLength(Integer memoLength) {
		this.memoLength = memoLength;
	}
	public Integer getSkipImgNo() {
		return skipImgNo;
	}
	public void setSkipImgNo(Integer skipImgNo) {
		this.skipImgNo = skipImgNo;
	}
	public Boolean getHasImage() {
		return hasImage;
	}
	
	
}
