<%@page import="com.xjj.jdk17.utils.StringUtil"%>
<%@page import="com.xjj.cms.channel.service.*"%>
<%@page import="com.xjj.cms.menu.service.*"%>
<%@page import="com.xjj.cms.menu.model.*"%>
<%@page import="com.xjj.cms.channel.model.*"%>
<%@page import="com.xjj.cms.article.model.*"%>
<%@page import="com.xjj.cms.article.service.ICmsArticleService"%>
<%@page import="com.xjj.framework.core.util.SpringContextUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%
	String orgCode = request.getParameter("orgCode");
	String orgName = request.getParameter("orgName");
	if(StringUtil.isEmpty(orgCode)){
		orgCode = "lgx";
	}
	if(StringUtil.isEmpty(orgName)){
		orgName = "海南临高";
	}
	request.setAttribute("orgCode", orgCode);
	request.setAttribute("orgName", orgName);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			<%
            ICmsArticleService cmsArticleService = (ICmsArticleService)SpringContextUtil.getInstance().getBean("cmsArticleService");
		    ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
		    ICmsMenuService cmsMenuService =  (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
	        ICmsChannelService iCmsChannelService = (ICmsChannelService)SpringContextUtil.getInstance().getBean("CmsChannelService");
		    
			String menuId = request.getParameter("menuId")!=null?(request.getParameter("menuId")):"";
	        CmsChannel cc = (CmsChannel)iCmsChannelService.findByChlCode("c_menu_left_daohang");
	        
            List<CmsChannelItem> setChlItem = cmsChannelItemService.findAllItem(cc.getId());
		
   			String parentId = "";
   			if(!menuId.equals("")){
   				CmsMenu currentMenu = (CmsMenu)cmsMenuService.get( menuId);
   				parentId = currentMenu.getParentMenuId()!=null?currentMenu.getParentMenuId():"";
   			}
   			
   			String path = request.getContextPath();
   			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
   			
   			for(int index_menu= 0;index_menu<setChlItem.size();index_menu++){
   					CmsChannelItem ccItem = (CmsChannelItem)setChlItem.get(index_menu);
   	   				String itemId = ccItem.getMenuId();
   	   				CmsMenu cm = (CmsMenu)cmsMenuService.get(itemId);
   	   				String cm_id = cm.getId();
   	   				String menuUrl = "";
   	   				
   	   				if(cm.getMenuType() == 0){
   	   					//真实栏目
   	   					menuUrl = basePath + "web/c/menu.jsp"+"?menuName="+cm.getMenuName()+"&menuId="+itemId.toString()+"&orgCode="+orgCode+"&orgName="+orgName;
   	   				}else if(cm.getMenuType() == 1){
   	   				    //跳转栏目
   	   					menuUrl = cm.getSkipUrl();
   	   				}
   	   				
   	   				if(menuId.equals(cm_id)){
   	   		  %>
<li><a href="<%=menuUrl %>" id="<%=cm_id%>" class="hove"><%=cm.getMenuName()%></a></li>
			<%
   	   		}else{
   	   		  %>
<li><a href="<%=menuUrl %>" id="<%=cm_id%>"><%=cm.getMenuName()%></a></li>
			<%
   	   		}
   				}
   	   		%>