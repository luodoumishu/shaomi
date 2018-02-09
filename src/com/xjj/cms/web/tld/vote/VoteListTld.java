package com.xjj.cms.web.tld.vote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.hibernate.util.StringHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.base.util.DateTools;
import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.cms.vote.service.ICmsVoteThemeService;
import com.xjj.framework.core.util.SpringContextUtil;

public class VoteListTld   extends BodyTagSupport {
	/**
	 * 投票标题长度
	 */
	private Integer titleLength; 
	/**
	 * 显示样式
	 */
	private String styleClass;
	/**
	 * 每页显示的长度 
	 */
	private Integer pageSize; 
	/**
	 * 第几页  
	 */
	private Integer pageNo;
	
	public int doEndTag() throws JspException {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());
		ICmsVoteThemeService cmsVoteThemeService = (ICmsVoteThemeService)SpringContextUtil.getInstance().getBean("cmsVoteThemeService");
		if(pageNo == null){
			pageNo = 0;
		}
		List<CmsVoteTheme> listVoteTheme = new ArrayList<CmsVoteTheme>();
		if(pageSize!=null){
			listVoteTheme = cmsVoteThemeService.getVoteByPagesize(pageSize, pageNo);
		}else{
			listVoteTheme = cmsVoteThemeService.getAllValidMain();
		}
		for(int i = 0 ; i < listVoteTheme.size() ; i ++ ){
			CmsVoteTheme cmsVoteTheme = (CmsVoteTheme)(listVoteTheme.get(i));
			BodyContent bc = getBodyContent();
	        String body = bc.getString();
	        
			body = StringHelper.replace(body, "$_vote_main_no" , i+1+"");
			
			
			
	        String voteTitle = cmsVoteTheme.getThemeName();
	        String showTitle = null;
	        
	        if(titleLength == null){
	        	showTitle = voteTitle;
	        }else{
	            if(voteTitle.length() < titleLength){
	            	showTitle = voteTitle;
	            }else{
	            	showTitle = voteTitle.substring(0 , titleLength) + "...";
	            }
	        }
	        Integer no= i+1;
	        body = StringHelper.replace(body, "$_vote_no" ,no.toString());
	        body = StringHelper.replace(body, "$_vote_main_title" , showTitle);
	        body = StringHelper.replace(body, "$_vote_main_full_title" , voteTitle);
	        
			body = StringHelper.replace(body, "$_vote_main_id" , cmsVoteTheme.getId().toString());
			body = StringHelper.replace(body, "$_vote_main_time" , DateTools.dateToString(cmsVoteTheme.getCreateTime(), DateTools.FULL_YMD));
		
			
	        try {
	            pageContext.getOut().print(body);
	        } catch (IOException e) {
	        }
			
		}
	
		return this.EVAL_PAGE;
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
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	
	
}

