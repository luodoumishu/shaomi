package com.xjj.cms.web.tld.vote;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.hibernate.util.StringHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.base.util.StringTools;
import com.xjj.cms.vote.model.CmsVoteDetail;
import com.xjj.cms.vote.model.CmsVoteItem;
import com.xjj.cms.vote.service.ICmsVoteDetailService;



public class VoteDetailTld extends BodyTagSupport{
	
	public int doStartTag() throws JspException {
		//System.out.println("------MenuArticleMoreTld-----doStartTag()------");
		//返回父标签 
		VoteItemTld voteItemTld = (VoteItemTld) TagSupport.findAncestorWithClass(this, VoteItemTld.class);
		if(voteItemTld.itrVoteItem.hasNext()){
			voteItemTld.curCmsVoteItem = (CmsVoteItem) voteItemTld.itrVoteItem.next();
		}else{
			voteItemTld.curCmsVoteItem = null;
		}
		return super.EVAL_BODY_BUFFERED;
		
	}
	
	
	public int doEndTag() throws JspTagException {
		
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());
		ICmsVoteDetailService cmsVoteDetailService = (ICmsVoteDetailService) context.getBean("cmsVoteDetailService");
		
		VoteItemTld voteItemTld = (VoteItemTld) TagSupport.findAncestorWithClass(this, VoteItemTld.class);
		VoteMainTld voteMainTld = (VoteMainTld) TagSupport.findAncestorWithClass(this, VoteMainTld.class);
		CmsVoteItem cmsVoteItem = voteItemTld.curCmsVoteItem;
		
		if(cmsVoteItem == null){
			return super.SKIP_BODY;
		}else{
			String itemType = cmsVoteItem.getItem_type();
			String showType = cmsVoteItem.getShow_type();
			String selectName = "chbItemlist"+cmsVoteItem.getId();
			voteMainTld.selectName += selectName + ";";
	        List<CmsVoteDetail> listValidDetail = cmsVoteDetailService.getValidDetailListByItemId(cmsVoteItem.getId());
	        Iterator itr = listValidDetail.iterator();
	        BodyContent bc = getBodyContent();
	        String body = bc.getString();
	        String textStr = "";
			String selectBox = "";
	        while(itr.hasNext()){
	        	CmsVoteDetail detail = (CmsVoteDetail)itr.next();
	        	bc = getBodyContent();
	        	body = bc.getString();
	        	selectBox = "";
				if(showType != null){
					if(showType.equals("0")){
						//横排
						if(itemType.equals("0")){
							selectBox = "<input type=\"radio\" name=\""+selectName+"\" value=\""+detail.getId()+"\">";
						}else if(itemType.equals("1")){
							selectBox = "<input type=\"checkbox\" name=\""+selectName+"\" value=\""+detail.getId()+"\" />";
						}
					}else if(showType.equals("1")){
						//竖排
						if(itemType.equals("0")){
							selectBox = "<li><input type=\"radio\" name=\""+selectName+"\" value=\""+detail.getId()+"\">";
						}else if(itemType.equals("1")){
							selectBox = "<li><input type=\"checkbox\" name=\""+selectName+"\" value=\""+detail.getId()+"\"/>";
						}
					}
				}
				body = StringHelper.replace(body, "$_vote_detail_title" , StringTools.formatNull2Blank(detail.getDetail_name()) + textStr);
		        body = StringHelper.replace(body, "$_vote_detail_select" , selectBox);
		        body = StringHelper.replace(body, "$_vote_detail_id" , detail.getId().toString());
		        try {
		            pageContext.getOut().print(body);
		        } catch (IOException e) {
		        }
	        }
	        if(itemType.equals("2")){
				textStr = "<textarea onkeyup='checkLen(this);' name=\""+selectName+"\" cols=\"80\" rows=\"4\" id=\""+cmsVoteItem.getId()+"\" onpropertychange=\"style.posHeight=scrollHeight+5\" ></textarea>100字以内";
				body = StringHelper.replace(body, "$_vote_detail_title" , textStr);
				body = StringHelper.replace(body, "$_vote_detail_select" , "");
		        body = StringHelper.replace(body, "$_vote_detail_id" ,"");
		        try {
		            pageContext.getOut().print(body);
		        } catch (IOException e) {
		        }
	        }
			return super.EVAL_PAGE;
		}
	}
}
