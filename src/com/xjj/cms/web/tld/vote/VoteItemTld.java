package com.xjj.cms.web.tld.vote;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.hibernate.util.StringHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.vote.model.CmsVoteItem;
import com.xjj.cms.vote.service.ICmsVoteItemService;
import com.xjj.framework.core.util.SpringContextUtil;


public class VoteItemTld extends BodyTagSupport{
	/**
	 * 是否用于显示投票结果,默认为false
	 */
	private Boolean forShowResult;
	
	/**
	 * mainId对应的有效的投票项的Iterator集合
	 */
	public Iterator itrVoteItem;
	public String sourceBody = "";
	public String body = "";
	/**
	 * itrVoteItem集合中的某一个当前的投票项
	 */
	public CmsVoteItem curCmsVoteItem;
	public int i = 1;
	/**
	 * 当前投票项的被投票总数
	 * 由VoteDetailTld赋值。
	 */
	public Integer vote_number = 0;
	
//	如果body的内容需要被计算，那么doStartTag方法必须返回EVAL_BODY_BUFFERED，否则，它将返回 SKIP_BODY。
	public int doStartTag() throws JspException {
		//System.out.println("------VoteItemTld-----doStartTag()------");
		String strMainId = pageContext.getRequest().getParameter("themeId");
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());
		ICmsVoteItemService cmsVoteItemService = (ICmsVoteItemService)SpringContextUtil.getInstance().getBean("cmsVoteItemService");
		List listValidItem;
		if(forShowResult == null){
			forShowResult = false;
		}
		if(forShowResult){
			listValidItem = cmsVoteItemService.getValidListOnlySelectByMainId(strMainId);
		}else{
			listValidItem = cmsVoteItemService.getValidListByMainId(strMainId);
		}
	    
	    itrVoteItem = listValidItem.iterator();
	    
		return this.EVAL_BODY_BUFFERED;
	}
	
	
    public void doInitBody() {
    	//System.out.println("------VoteItemTld-----doInitBody()------");

    }
	
    
    
	//EVAL_BODY_BUFFERED，否则，它应返回SKIP_BODY
	public int doAfterBody() throws JspException {
		//System.out.println("------VoteItemTld-----doAfterBody()------");
		BodyContent bc = getBodyContent();
		sourceBody = bc.getString();
		bc.clearBody();

		if(curCmsVoteItem != null){
			String tmp = sourceBody;
			tmp = StringHelper.replace(tmp, "$_vote_item_title" , curCmsVoteItem.getItem_name());
			tmp = StringHelper.replace(tmp, "$_vote_item_vote_number" , vote_number+"");
			
			tmp = StringHelper.replace(tmp, "$_vote_item_no" , i+"");
			i ++;
			body += tmp;
	        return super.EVAL_BODY_BUFFERED;//继续循环
		}else{
			return super.SKIP_BODY;
		}
		
	}
	
	
	public int doEndTag() throws JspTagException {
		//System.out.println("------VoteItemTld-----doEndTag()------");
		
		try {
            pageContext.getOut().print(body);
        } catch (IOException e) {
        }

        itrVoteItem = null;
        sourceBody = "";
        body = "";
        curCmsVoteItem = null;
        i = 1;
        return this.EVAL_PAGE;
	}


	public boolean isForShowResult() {
		return forShowResult;
	}


	public void setForShowResult(boolean forShowResult) {
		this.forShowResult = forShowResult;
	}
}
