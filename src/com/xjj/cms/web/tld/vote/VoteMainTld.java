package com.xjj.cms.web.tld.vote;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.hibernate.util.StringHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.base.util.DateTools;
import com.xjj.cms.base.util.NumberTools;
import com.xjj.cms.base.util.StringTools;
import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.cms.vote.service.ICmsVoteThemeService;
import com.xjj.framework.core.util.SpringContextUtil;


public class VoteMainTld extends BodyTagSupport {
	private CmsVoteTheme cmsVoteTheme;
	public Integer total_vote_num;
	public String selectName = "";


	public int doStartTag() throws JspTagException {
		ICmsVoteThemeService cmsVoteThemeService = (ICmsVoteThemeService)SpringContextUtil.getInstance().getBean("cmsVoteThemeService");
		String themeId = (String) pageContext.getRequest().getAttribute("themeId");
		
		if(themeId == null){
			return this.SKIP_PAGE;
		}else{
			CmsVoteTheme cmsVoteTheme = cmsVoteThemeService.get(themeId);
			this.cmsVoteTheme = cmsVoteTheme;
			return this.EVAL_BODY_AGAIN;
		}
	}
	
	
	public int doEndTag() throws JspException {
		String themeId = (String) pageContext.getRequest().getAttribute("themeId");
		if(themeId == null){
			return this.SKIP_PAGE;
		}else{
			
			BodyContent bc = getBodyContent();
			String body = bc.getString();
			
			body = StringHelper.replace(body, "$_vote_main_title" , cmsVoteTheme.getThemeName());
			body = StringHelper.replace(body, "$_vote_main_issue_time" , DateTools.dateToString(cmsVoteTheme.getCreateTime(),DateTools.FULL_YMD));
			body = StringHelper.replace(body, "$_vote_main_total_num" , NumberTools.formatInt2StrDefaultZero(cmsVoteTheme.getVoteTotal()));
			
			body = StringHelper.replace(body, "$_vote_main_selectName" , selectName);
			
			try {
	            pageContext.getOut().print(body);
	        } catch (IOException e) {
	        }
	        
	        
			this.cmsVoteTheme = null;
			this.selectName = "";
			
			return this.EVAL_PAGE;			
		}
		
	}
	
	
	
	

	public CmsVoteTheme getCmsVoteTheme() {
		return cmsVoteTheme;
	}
	public void setCmsVoteTheme(CmsVoteTheme cmsVoteTheme) {
		this.cmsVoteTheme = cmsVoteTheme;
	}
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	public Integer getTotal_vote_num() {
		return total_vote_num;
	}
	public void setTotal_vote_num(Integer total_vote_num) {
		this.total_vote_num = total_vote_num;
	}

}
