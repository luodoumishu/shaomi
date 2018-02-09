<%@page import="com.xjj.jdk17.utils.StringUtil"%>
<%@page import="com.xjj.cms.article.model.CmsArticleHistory"%>
<%@page import="com.xjj.framework.core.util.SpringContextUtil"%>
<%@page import="com.xjj.cms.article.service.ICmsArticleHistoryService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@ include file="/comm/js/taglib.jsp"%>
<%@ taglib prefix="xjj" uri="/WEB-INF/tld/xjj_portal.tld"%>
<%
	String path = request.getContextPath();
	String ip = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort();
	String basePath = ip + path + "/";
	ICmsArticleHistoryService cmsArticleHistoryService = (ICmsArticleHistoryService) SpringContextUtil.getInstance().getBean("cmsArticleHistoryService");
	String articleId = request.getParameter("articleId");
	CmsArticleHistory article = cmsArticleHistoryService.get(articleId);
	
	String orgCode = request.getParameter("orgCode");
	String orgName = request.getParameter("orgName");
	if(StringUtil.isEmpty(orgCode)){
		orgCode = "btx";
	}
	if(StringUtil.isEmpty(orgName)){
		orgName = "海南保亭";
	}
	request.setAttribute("orgCode", orgCode);
	request.setAttribute("orgName", orgName);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title><%=orgName %>党建网</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/_base/js/jquery-1.8.3.js"></script>
</head>
<script type="text/javascript">
	function closeW() {
		window.opener = null;
		window.open("","_self") ;
		window.close(); 
	}
	$(document).ready(function() {
		$("#shouye").removeClass("hove"); 
		var comment = "<%=article.getTitle()%>";
		var length = Math.ceil(parseInt(comment.length+1)/45);
		length = parseInt(110)/parseInt(length);
		$("#_title").css("line-height",length+"px");
		var $imgs = $(".nei_neir img");
	       if(null != $imgs && "undefined" != $imgs){
	    	   var size = $imgs.length;
	    	   for(var i = 0;i<size;i++){
	    		   var $img = $imgs[i];
	    		   var src_width = $($img).width();
	    		   var src_height = $($img).height();
	    		   var chance_width = 930;
	    		   if(src_width > chance_width){
	    			   var chance_height = src_height*chance_width/src_width;
	    			   $($img).width(chance_width);
	    			   $($img).height(chance_height);
	    		   }
	    	   }
	       }
	});
</script>
<body>
<div class="bt_box">
  	<!-- 头部 Start -->
	<c:import url="top.jsp"></c:import>
	<!-- 头部 End  -->
  	<!--内容-->
	<div class="bt_main">
		<article:showContent showAll="true">
			<div class="nei_top">
		    	<h1 id="_title">$_article_title</h1>
		        <p><span>文章来源：$_article_source</span><span>稿件作者：$_article_author</span><span>发布日期：$_article_public_time</span></p>
		    </div>
		    <div class="nei_neir">
		   		$_article_content
		    </div>
	    	<div style="text-align: center;"><input type="button" onclick="closeW();" value="关闭本页" style="cursor: pointer;"></div><br/>
	    </article:showContent>
	</div>
	<!--内容 end-->
 	<!--底部-->
	<c:import url="end.jsp"></c:import>
	<!--底部 end-->
</div>

</body>
</html>
