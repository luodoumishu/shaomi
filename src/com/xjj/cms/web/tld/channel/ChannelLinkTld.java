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
import com.xjj.framework.core.util.SpringContextUtil;


public class ChannelLinkTld  extends BodyTagSupport {
	
	public int doEndTag() throws JspException {
		
		HttpServletRequest _request = (HttpServletRequest)pageContext.getRequest();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());
		ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
		ICmsLinkService  cmsLinkService = (ICmsLinkService) SpringContextUtil.getInstance().getBean("cmsLinkService");
		ICmsChlLinkService cmsChlLinkService = (ICmsChlLinkService) SpringContextUtil.getInstance().getBean("cmsChlLinkService");
		//BaseDao baseDao = (BaseDao) context.getBean("baseDao");
		
		ChannelMainTld channelMainTld = (ChannelMainTld) TagSupport.findAncestorWithClass(this, ChannelMainTld.class);
		Integer pageSize = channelMainTld.getPageSize();
		if(pageSize == null){
			pageSize = 9999;
		}
		CmsChannel cmsChl = channelMainTld.getChl();
		if(cmsChl.getRelevanceType().equals("0") || cmsChl.getRelevanceType().equals("2")){
			//栏目类型
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
					String openType = cl.getOpenType();
					String open_type = "";
					if(openType.equals("1")){
						//当前页面打开
						open_type = "_self";
					}else{
						//新窗口页面打开
						open_type = "_blank";
					}
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
										   .append(""+open_type+"")
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
								body = StringHelper.replace(body, "$_link_open_type" , open_type);
						        try {
						            pageContext.getOut().print(body);
						        } catch (IOException e) {
						        }
								if(pageSize != null){
									pageSize -- ;
								}
						}else if(cl.getLinkType().equals("1")){
							
							String url = _request.getScheme()+"://"+_request.getHeader("host");
							/**StringBuffer newFilePathName = new StringBuffer();
							newFilePathName.append(CmsCC.ENCLOSURE_URI)
										   .append("/")
										   .append(CmsCC.LINK_IMAGE_PATH)
										   .append("/");*/
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
											   .append(""+open_type+"")
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
									body = StringHelper.replace(body, "$_link_open_type" , open_type);
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
			}
}
