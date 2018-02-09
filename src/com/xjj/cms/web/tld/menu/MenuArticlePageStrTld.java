package com.xjj.cms.web.tld.menu;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;



/**
 * 分页器
 * @author fengjunkong
 * @date 2014-09-05
 */
public class MenuArticlePageStrTld  extends TagSupport {
	
	public int doEndTag() throws JspException {
		String pageStr = (String)pageContext.getAttribute("pageStr");
		if(pageStr == null){
			pageStr = "";
		}
		TagUtils.getInstance().write(pageContext, pageStr);
		return (EVAL_PAGE);
	}
}
