<%@page import="com.xjj.jdk17.utils.StringUtil"%>
<%@page import="com.xjj.cms.channel.service.*"%>
<%@page import="com.xjj.cms.menu.service.*"%>
<%@page import="com.xjj.cms.menu.model.*"%>
<%@page import="com.xjj.cms.channel.model.*"%>
<%@page import="com.xjj.cms.article.model.*"%>
<%@page import="com.xjj.cms.article.service.ICmsArticleService"%>
<%@page import="com.xjj.framework.core.util.SpringContextUtil"%>
<%@page import="java.net.URLDecoder"%>
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
	orgName = java.net.URLEncoder.encode(orgName, "UTF-8");
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<li>
	<a href="c_index.jsp?orgCode=<%=orgCode %>&orgName=<%=orgName %>" id="shouye" class="hove">首页</a>
</li>
			<%
            ICmsArticleService cmsArticleService = (ICmsArticleService)SpringContextUtil.getInstance().getBean("cmsArticleService");
		    ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
		    ICmsMenuService cmsMenuService =  (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
	        ICmsChannelService iCmsChannelService = (ICmsChannelService)SpringContextUtil.getInstance().getBean("CmsChannelService");
		    
			String menuId = request.getParameter("menuId")!=null?(request.getParameter("menuId")):"";
	        CmsChannel cc = (CmsChannel)iCmsChannelService.findByChlCode("c_daohang");
	        
            List<CmsChannelItem> setChlItem = cmsChannelItemService.findAllItem(cc.getId());
		
   			String parentId = "";
   			if(!menuId.equals("")){
   				CmsMenu currentMenu = (CmsMenu)cmsMenuService.get( menuId);
   				parentId = currentMenu.getParentMenuId()!=null?currentMenu.getParentMenuId():"";
   			}
   			
   			String path = request.getContextPath();
   			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
   			
   			Integer length = setChlItem.size();
   			//若是有限制栏目个数
   			Integer childMenuNum = cc.getChildMenuNum();
   			if(null != childMenuNum && childMenuNum != 0){
				if(length > childMenuNum){
	   				length = childMenuNum;	
				}
   			}
   			for(int index_menu= 0;index_menu < length;index_menu++){
   					CmsChannelItem ccItem = (CmsChannelItem)setChlItem.get(index_menu);
   	   				String itemId = ccItem.getMenuId();
   	   				CmsMenu cm = (CmsMenu)cmsMenuService.get(itemId);
   	   				String cm_id = cm.getId();
   	   				String menuUrl = "";
   	   				
   	   				Integer openMode = cm.getOpenMode();
   	   				String open_target = "";
   	   				if(openMode == 0){
   	   					//当前窗口打开
   	   					open_target = "_self";
   	   				}else{
   	   					//新窗口打开
   	   					open_target = "_blank";
   	   				}
   	   				
   	   				if(cm.getMenuType() == 0){
   	   					//真实栏目
  						menuUrl = basePath + "web/c/menu.jsp"+"?menuName="+cm.getMenuName()+"&menuId="+itemId.toString()+"&orgCode="+orgCode+"&orgName="+orgName;
   	   				}else if(cm.getMenuType() == 1){
   	   				    //跳转栏目
   	   					menuUrl = cm.getSkipUrl();
   	   				}
   	   				
   	   				if(menuId.equals(cm_id)){
   	   		  %>
<li><a href="<%=menuUrl %>" id="<%=cm_id%>" class="hove" target="<%=open_target %>"><%=cm.getMenuName()%></a></li>
			<%
   	   		}else{
   	   		  %>
<li><a href="<%=menuUrl %>" id="<%=cm_id%>" target="<%=open_target %>"><%=cm.getMenuName()%></a></li>
			<%
   	   		}
   				}
   	   		%>