package com.xjj.cms.web.tld.channel;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.link.model.CmsChlLink;
import com.xjj.cms.link.model.CmsLink;
import com.xjj.cms.link.service.ICmsChlLinkService;
import com.xjj.cms.link.service.ICmsLinkService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.util.SpringContextUtil;


public class ChannelMenuTld  extends BodyTagSupport {
	
	public int doEndTag() throws JspException {
		
		HttpServletRequest _request = (HttpServletRequest)pageContext.getRequest();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());
		ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
		
		ICmsMenuService cmsMenuService = (ICmsMenuService) SpringContextUtil.getInstance().getBean("cmsMenuService");
		
		ChannelMainTld channelMainTld = (ChannelMainTld) TagSupport.findAncestorWithClass(this, ChannelMainTld.class);
		Integer pageSize = channelMainTld.getPageSize();
		if(pageSize == null){
			pageSize = 9999;
		}
		CmsChannel cmsChl = channelMainTld.getChl();
		//关联类型
		if(cmsChl.getRelevanceType().equals("0")){
			//栏目类型
			List<CmsMenu> menus = cmsMenuService.findAllMenuByChannelId(cmsChl.getId());
			if(menus.isEmpty()){
				return this.SKIP_BODY;
			}else{
				Iterator<CmsMenu> itr = menus.iterator();
				while(itr.hasNext() && pageSize > 0){
					CmsMenu menu = itr.next();
					if(menu.getMenu_img_url() != null){
						//栏目配图不为空
						BodyContent bc = getBodyContent();
						String body = bc.getString();
						String menuUrl = "menu.jsp?menuId=" + menu.getId(); 
						body = StringHelper.replace(body, "$_menu_url" , menuUrl);
						body = StringHelper.replace(body, "$_menu_img" , menu.getMenu_img_url());
						try {
				            pageContext.getOut().print(body);
				        } catch (IOException e) {
				        	e.printStackTrace();
				        }
						if(pageSize != null){
							pageSize -- ;
						}
					}else{
						//栏目配图为空 ---暂时没用
					}
				}
			}
			
		}else if(cmsChl.getRelevanceType().equals("1")){
			//超链接类型
			return this.SKIP_BODY;
		}else if(cmsChl.getRelevanceType().equals("2")){
			//投票类型
			return this.SKIP_BODY;
		}
		return this.EVAL_PAGE;
	}
		/*if(cmsChl.getRelevanceType().equals("0") || cmsChl.getRelevanceType().equals("2")){
			return this.SKIP_BODY;
		}else if(cmsChl.getRelevanceType().equals("1")){
			List<CmsChlLink> setChlItem = cmsChlLinkService.findAllLinkByChannelId(cmsChl.getId());
			if(setChlItem.isEmpty()){
				return this.SKIP_BODY;
			}else{
				
				Iterator itr = setChlItem.iterator();
				while(itr.hasNext() && pageSize > 0){
					CmsChlLink ccitem = (CmsChlLink)itr.next();
					CmsLink cl = (CmsLink)cmsLinkService.get(ccitem.getLinkId());
					if(cl.getLinkType().equals("0")){
					 //文字类型
					BodyContent bc = getBodyContent();
					String body = bc.getString();
					//根据超链类型展示的对外显示名称
					StringBuffer linkStr = new StringBuffer();
					if(StringUtils.isNotBlank(cl.getLinkAddr())){
									linkStr.append("<a href=\"")
										   .append(cl.getLinkAddr())
										   .append("\" target=\"")
										   .append("_blank")
										   .append("\">")
										   .append(cl.getLinkName())
										   .append("</a>");
								}else{
									linkStr.append(cl.getLinkName());
								}
								body = StringHelper.replace(body, "$_link_instance_byTypeShow_hasLink" , linkStr.toString());
								String name = "";
								if(cl.getLinkName().length() > 11){
									name = cl.getLinkName().substring(0, 10) + "...";
								}else{
									name = cl.getLinkName();
								}
								body = StringHelper.replace(body, "$_link_instance_name" , name);
								if(cl.getLinkAddr()==null){
									body = StringHelper.replace(body, "href=\"$_link_instance_addr\"" , "");
								}else{
								body = StringHelper.replace(body, "$_link_instance_addr" , cl.getLinkAddr());
								}
						        try {
						            pageContext.getOut().print(body);
						        } catch (IOException e) {
						        }
								if(pageSize != null){
									pageSize -- ;
								}
						}else if(cl.getLinkType().equals("1")){
							
							String url = _request.getScheme()+"://"+_request.getHeader("host");
							*//**StringBuffer newFilePathName = new StringBuffer();
							newFilePathName.append(CmsCC.ENCLOSURE_URI)
										   .append("/")
										   .append(CmsCC.LINK_IMAGE_PATH)
										   .append("/");*//*
							//图片型
								if(StringUtils.isNotEmpty(cl.getLinkImg())){
									BodyContent bc = getBodyContent();
									String body = bc.getString();
									//根据超链类型展示的对外显示名称
									StringBuffer imageStr = new StringBuffer("<img src=\"")
															.append(cl.getLinkImg())
															.append("\" />");
									StringBuffer linkStr = new StringBuffer();
									if(StringUtils.isNotBlank(cl.getLinkAddr())){
										linkStr.append("<a href=\"")
											   .append(cl.getLinkAddr())
											   .append("\" target=\"")
											   .append("_blank")
											   .append("\">")
											   .append(imageStr.toString())
											   .append("</a>");
									}else{
										linkStr.append(imageStr.toString());
									}

									body = StringHelper.replace(body, "$_link_instance_linkName" , cl.getLinkName().toString());
									body = StringHelper.replace(body, "$_link_instance_byTypeShow_hasLink" , linkStr.toString());
									body = StringHelper.replace(body, "$_link_instance_image" , cl.getLinkImg());
									body = StringHelper.replace(body, "$_link_instance_name" , cl.getLinkName());
									if(cl.getLinkAddr()==null){
										body = StringHelper.replace(body, "href=\"$_link_instance_addr\"" , "");
									}else{
									body = StringHelper.replace(body, "$_link_instance_addr" , cl.getLinkAddr());
									}
							        try {
							            pageContext.getOut().print(body);
							        } catch (IOException e) {
							        }
								}
								if(pageSize != null){
									pageSize -- ;
								}
							}
						}
						
					}

				}
				return this.EVAL_PAGE;
			}*/
}
