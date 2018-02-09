package com.xjj.cms.web.tld.file;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.channel.service.ICmsChannelService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.cms.web.tld.channel.ChannelMainTld;
import com.xjj.framework.core.util.SpringContextUtil;



/**
 * 频道模块文件下载的的更多按钮的链接
 * 首先去频道对象的more_uri的值，若有则以此为准
 * 若无则取频道的第一个栏目对应的 栏目显示模版 的jsp页面，传入该栏目的id即为更多的链接
 * 父标签为 chl:main
 * @author fengjunkong
 *
 */
public class ChannelMoreTld  extends BodyTagSupport {
	/**
	 * 制定的more按钮的uri的来源
	 * 值可以为 channel  或者  menu 的其中的一个
	 */
	private String moreSource;

	public void setMoreSource(String moreSource) {
		this.moreSource = moreSource;
	}
	

	public int doEndTag() throws JspException {

		ICmsArticleService cmsArticleService = (ICmsArticleService)SpringContextUtil.getInstance().getBean("cmsArticleService");
		ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
		ICmsMenuService cmsMenuService =  (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
		
		
		BodyContent bc = getBodyContent();
        String body = bc.getString();
		String replaceStr = "";
		ChannelMainTld channelMainTld = (ChannelMainTld) TagSupport.findAncestorWithClass(this, ChannelMainTld.class);
		CmsChannel cmsChl = channelMainTld.getChl();
		String channelMoreUri = "";//cmsChl.getChannel_more_uri();
		if(StringUtils.isBlank(moreSource)){
//			if(StringUtils.isNotBlank(channelMoreUri)){
//				replaceStr = channelMoreUri;
//			}else{
				replaceStr = rtnStrByMenu(cmsChl , cmsMenuService,cmsChannelItemService);
//			}
		}else{
			if(moreSource.equals("menu")){
				replaceStr = rtnStrByMenu(cmsChl , cmsMenuService,cmsChannelItemService);
			}else if(moreSource.equals("channel")){
				if(StringUtils.isNotBlank(channelMoreUri)){
					replaceStr = channelMoreUri;
				}else{
					replaceStr = rtnStrByMenu(cmsChl , cmsMenuService,cmsChannelItemService);
				}
			}
		}
		body = StringHelper.replace(body, "$_channel_more_uri" , replaceStr);
        try {
            pageContext.getOut().print(body);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return this.EVAL_PAGE;
		
	}
	
	
	private String rtnStrByMenu(CmsChannel cmsChl , ICmsMenuService cmsMenuService,ICmsChannelItemService cmsChannelItemService){
		String strRtn = "";
		String s=  pageContext.getServletContext().getContextPath();
		String ids="";
		List setChlItem = cmsChannelItemService.findAllItem(cmsChl.getId());
		if(setChlItem.size()==0){
			strRtn = "#";
		}else{
			CmsChannelItem ccItem = (CmsChannelItem)setChlItem.get(0);
			CmsMenu cm = (CmsMenu)cmsMenuService.get(ccItem.getMenuId());
			if(cm.getMenuType()==1){
				String skipurl = cm.getSkipUrl();
				if(skipurl.contains("http://")){
					strRtn = skipurl;
				}else{
					strRtn = skipurl+"?menuId="+cm.getId()+"&menuName="+cm.getMenuName();
				}
			}else if(cm.getMenuType()==0){
				String menuUrl ="file.jsp";//cm.getCmsTemplet().getTemplet_url();
				strRtn = menuUrl+"?menuId="+cm.getId()+"&menuName="+cm.getMenuName();
			}
		}
		return strRtn;
	}
	//2014.09.03 新增获取文件下载页的地址 fengjunkong
/*	private String rtnfileStrByMenu(CmsChannel cmsChl , CmsMenuManager cmsMenuManager){
		String strRtn = "";
		String allId="";
		Set setChlItem = cmsChl.getSetCmsChannelItem();
		if(setChlItem.isEmpty()){
			strRtn = "#";
		}else{
			Iterator<CmsChannelItem> it = setChlItem.iterator(); 
			while(it.hasNext()){  
				CmsChannelItem ccItem = (CmsChannelItem)it.next(); 
			    CmsMenu cm = (CmsMenu)cmsMenuManager.getBaseDao().get(CmsMenu.class, ccItem.getItem_id());
			    String menuUrl = cm.getCmsTemplet().getTemplet_url();
			    allId += cm.getId()+",";
			} 
			String[] ids= allId.split(",");
			//暂时写死路径
			strRtn = CC.CTX+"/web/default/file.jsp"+"?menuId="+ids[0]+"&allId="+allId;
		}
		return strRtn;
	}*/
}
