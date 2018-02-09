package com.xjj.cms.web.tld.video;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.hibernate.util.StringHelper;

import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.cms.web.tld.channel.ChannelMainTld;
import com.xjj.framework.core.util.SpringContextUtil;

public class VideoMoreTld  extends BodyTagSupport {
	
	public int doEndTag() throws JspException {
		try {
			ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
			ICmsMenuService cmsMenuService =  (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
			
			ChannelMainTld channelMainTld = (ChannelMainTld) TagSupport.findAncestorWithClass(this, ChannelMainTld.class);
			CmsChannel cmsChl = channelMainTld.getChl();
			BodyContent bc = getBodyContent();
			 String body = bc.getString();
			String replaceStr = "";
	        replaceStr = rtnStrByMenu(cmsChl , cmsMenuService,cmsChannelItemService);
	        body = StringHelper.replace(body, "$_video_more_uri" , replaceStr);
	        try {
	            pageContext.getOut().print(body);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.EVAL_PAGE;
	}
	
	private String rtnStrByMenu(CmsChannel cmsChl , ICmsMenuService cmsMenuService,ICmsChannelItemService cmsChannelItemService){
		String strRtn = "";
		String s=  pageContext.getServletContext().getContextPath();
		String ids="";
		List setChlItem = cmsChannelItemService.findAllItem(cmsChl.getId());
		String orgCode = (String)pageContext.getRequest().getAttribute("orgCode");
		String orgName = (String)pageContext.getRequest().getAttribute("orgName");
		
		if(setChlItem.size()==0){
			strRtn = "#";
		}else{
			CmsChannelItem ccItem = (CmsChannelItem)setChlItem.get(0);
			CmsMenu cm = (CmsMenu)cmsMenuService.get(ccItem.getMenuId());
			strRtn ="video_more.jsp?menuId="+cm.getId()+"&menuName="+cm.getMenuName()+"&orgCode="+orgCode+"&orgName="+orgName;
		}
		return strRtn;
	}
	
}
