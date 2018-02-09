package com.xjj.cms.web.tld.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.base.util.MenuPage;
import com.xjj.cms.base.util.NumberTools;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.file.model.CmsFile;
import com.xjj.cms.file.service.ICmsFileService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.cms.web.tld._ShowFileCommon;
import com.xjj.cms.web.tld.channel.ChannelMainTld;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.SpringContextUtil;


public class ChannelFileTld   extends BodyTagSupport {
	/**
	 * 当前页
	 */
	private Integer pageNo;
	
	/**
	 * 跳过多少条
	 */
	private Integer skipNo;
	/**
	 * 文件标题长度
	 */
	private Integer titleLength;

	/**
	 * 显示"new"图片的样式和不显示"new"的样式，中间以";"分隔
	 * 第一位的是显示图片的样式，第二位的是不显示图片的样式
	 */
	private String newCss;
	/**
	 * 样式
	 */
	private String styleClass;
	
	/**
	 * 分页方式 js/url，默认为url
	 */
	private String fyType;
	
	public int doEndTag() throws JspException {
		
		String strPageNo = pageContext.getRequest().getParameter("pageNo");
		if(StringUtils.isEmpty(strPageNo)){
			if(pageNo==null)
				pageNo = 1;
		}else{
			pageNo = NumberTools.formatObject2IntDefaultParamNoExp(strPageNo, 1);
		}
		if(skipNo==null){
			skipNo = 0;
		}
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());
		ICmsMenuService cmsMenuService = (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
		ICmsFileService cmsFileService = (ICmsFileService)SpringContextUtil.getInstance().getBean("cmsFileService");
		ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
		
		ChannelMainTld channelMainTld = (ChannelMainTld) TagSupport.findAncestorWithClass(this, ChannelMainTld.class);
		Integer pageSize = channelMainTld.getPageSize();
		CmsChannel cmsChl = channelMainTld.getChl();
		List<CmsChannelItem> setChlItem = cmsChannelItemService.findAllItem(cmsChl.getId());
		String[] listMenuId = new String[setChlItem.size()];
		try {
			for(int i =0;i<setChlItem.size();i++){
				CmsChannelItem ccitem = setChlItem.get(i);
				CmsMenu cm;
			
					cm = cmsMenuService.get(ccitem.getMenuId());
					if(cm.getIsValid() == 0){
						listMenuId[i] =(String) cm.getId();
					}
			 } 
		Map<String, Object>  map = new HashMap<String, Object> ();
		map.put("skip", 0);
		map.put("pageSize", pageSize);
		Map<String, Object>  queryObjMap = new HashMap<String, Object> ();
		map.put("queryObj", queryObjMap);
		Page<CmsAffix> page = cmsFileService.getALLFilesByMenuIds(listMenuId,map);

			if(page == null){ 
				return this.SKIP_BODY;
			}else{
				String strPage;
				if(StringUtils.isBlank(fyType) || fyType.equals("url")){
					strPage = MenuPage.getInstance().createPageStr((HttpServletRequest)pageContext.getRequest(), page, pageSize );
				}else{
					strPage = MenuPage.getInstance().createPageStrForJs((HttpServletRequest)pageContext.getRequest(),page, pageSize);
				}
				pageContext.setAttribute("pageStr", strPage);
					List listFile = page.getItems();
					Map param = new HashMap();
					param.put("titleLength", titleLength);
					param.put("styleClass", styleClass);
					param.put("newcss", newCss);
					for(int i = 0 ; i < listFile.size() ; i ++ ){
						CmsAffix cmsAffix = (CmsAffix)(listFile.get(i));
						BodyContent bc = getBodyContent();
				        String body = _ShowFileCommon.getBodyStr(bc.getString(), cmsAffix, param , i);
				        try {
				            pageContext.getOut().print(body);
				          } catch (IOException e) {
				        }
						
					}
	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return this.EVAL_PAGE;
	}
	public String getNewCss() {
		return newCss;
	}
	public void setNewCss(String newCss) {
		this.newCss = newCss;
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
	public Integer getTitleLength() {
		return titleLength;
	}
	public void setTitleLength(Integer titleLength) {
		this.titleLength = titleLength;
	}

	public Integer getSkipNo() {
		return skipNo;
	}
	public void setSkipNo(Integer skipNo) {
		this.skipNo = skipNo;
	}
	
	
	
}
