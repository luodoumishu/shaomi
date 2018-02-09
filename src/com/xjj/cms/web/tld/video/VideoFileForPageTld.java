package com.xjj.cms.web.tld.video;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;

import com.xjj._extensions.roleUserPer.service.IZorganizeService;
import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.DateTools;
import com.xjj.cms.base.util.MenuPage;
import com.xjj.cms.base.util.NumberTools;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.cms.video.model.CmsVideo;
import com.xjj.cms.video.service.ICmsVideoService;
import com.xjj.cms.web.tld.channel.ChannelMainTld;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.SpringContextUtil;
import com.xjj.jdk17.utils.StringUtil;

public class VideoFileForPageTld  extends BodyTagSupport {
	
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
	
	public int doEndTag() throws JspException {
		try {
			ICmsVideoService cmsVideoService = (ICmsVideoService)SpringContextUtil.getInstance().getBean("cmsVideoService");
			ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
			ICmsMenuService cmsMenuService =  (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
			if(pageNo == null){
				pageNo = 0;	
			}
			Map<String, Object>  map = new HashMap<String, Object> ();
			String strPageNo = pageContext.getRequest().getParameter("pageNo");
			if(!StringUtil.isEmpty(strPageNo)){
				pageNo = Integer.parseInt(strPageNo);
			}
			map.put("skip", pageNo*pageSize-pageSize);
			map.put("pageSize", pageSize);
			Map<String, Object>  queryObjMap = new HashMap<String, Object> ();
			queryObjMap.put("state", 3);
			map.put("queryObj", queryObjMap);
			
			ChannelMainTld channelMainTld = (ChannelMainTld) TagSupport.findAncestorWithClass(this, ChannelMainTld.class);
			CmsChannel cmsChl = channelMainTld.getChl();
			List<CmsChannelItem> setChlItem = cmsChannelItemService.findAllItem(cmsChl.getId());
			CmsChannelItem ccitem = setChlItem.get(0);
			CmsMenu cm = cmsMenuService.get(ccitem.getMenuId());
			
//			CmsMenu cm = (CmsMenu)pageContext.getAttribute("curMenu");
			
//			String strPageNo = pageContext.getRequest().getParameter("pageNo");
			
			if(StringUtils.isEmpty(strPageNo)){
				pageNo = 1;
			}else{
				pageNo = NumberTools.formatObject2IntDefaultParamNoExp(strPageNo, 1);
			}
			if(cm != null){
				if(cm.getArticleContentType() == 1){
					Page<CmsVideo> pageVideo = cmsVideoService.getALLVideosByMenuId(cm.getId(),map);
					if(pageVideo != null){
						String strPage = MenuPage.getInstance().createPageStr((HttpServletRequest)pageContext.getRequest(), pageVideo, pageSize );
						pageContext.setAttribute("pageStr", strPage);
						List<CmsVideo> listVideo = pageVideo.getItems();
						
						String body ="";
						BodyContent bc = getBodyContent();
						if(listVideo.size()!=0){
							for(int i = 0 ; i < listVideo.size() ; i ++ ){
								CmsVideo video = listVideo.get(i);
								body = getBodyStr(bc.getString(), video, i);
								try {
						            pageContext.getOut().print(body);
						        } catch (IOException e) {
						        	e.printStackTrace();
						        }
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

	
	
	
	
	
	private String getBodyStr(String body, CmsVideo video, int i) {
		
		ICmsMenuService cmsMenuService = (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
		
		int addNumber = 0;
		
		Date issueTime = video.getCreateTime();
        String strIssueTime = DateTools.dateToString( issueTime , DateTools.FULL_YMD);
        body = StringHelper.replace(body, "$_video_issue_time" , strIssueTime);
		String[] issues_split = strIssueTime.split("-");
		//年
		body = StringHelper.replace(body, "$_video_issue_year" , issues_split[0]);
		//月日
		if(issues_split.length>2){
		body = StringHelper.replace(body, "$_video_issue_md" , issues_split[1]+"-"+issues_split[2]);
		}
        String newcss = newCss;
        if(StringUtils.isNotEmpty(newcss)){
            if(DateTools.getDateAfterDay(issueTime, CmsCC.ARTICLE_NEW_FLAG_DAY).after(new Date())){
            	body = StringHelper.replace(body, "$_video_new_flag" , "<img src=\""+newcss+"\" align=\"absmiddle\" />");
            	addNumber = addNumber - 2;
            }else{
            	body = StringHelper.replace(body, "$_video_new_flag" , "");
            }
        }else{
        	body = StringHelper.replace(body, "$_video_new_flag" , "");
        }
		
        /* 文章链接 */
        Object objJspUrl = "video.jsp";
    	String jspUrl = (String)objJspUrl;
    	
    	if(jspUrl.indexOf("?") > 0){  
    		jspUrl = jspUrl + "&videoId=" + video.getId();

    	}else{
    		jspUrl = jspUrl +"?videoId=" + video.getId();
    	}
    	
    	body = StringHelper.replace(body, "$_video_url" , jspUrl);
    	
    	String articleTitle = video.getName().replaceAll("<BR>", " ").replaceAll("<br>", " ");
        String showTitle = null;
        if(titleLength == null){
        	showTitle = articleTitle;
        }else{
            if(articleTitle.length() <=titleLength){
            	showTitle = articleTitle;
            }else{
            	showTitle = articleTitle.substring(0 , titleLength-1) + "...";
            }
        }
        body = StringHelper.replace(body, "$_video_video_title" , showTitle);
        body = StringHelper.replace(body, "$_video_video_full_title" , articleTitle);
        body = StringHelper.replace(body, "$_video_video_id" , video.getId().toString());
        body = StringHelper.replace(body, "$_video_author" , video.getCreatUserName());
        
    	body = StringHelper.replace(body, "$_video_img" , video.getImg_filepath()+"/"+video.getImg_filename());
		
		return body;
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
	
}
