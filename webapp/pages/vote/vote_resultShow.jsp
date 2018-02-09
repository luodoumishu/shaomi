<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>投票结果展示</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>
    <link href="${ctx}/pages/vote/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
    	var basePath = "<%=basePath%>";
    	var path = "<%=path%>";
    	var themeId = null;
    	var dataSource = null;
    	var queryObj = null;
    	var grid = null;
    </script>
    <script src="${ctx}/assets/_base/js/core.js"></script>
    <script src="${ctx}/pages/vote/js/vote_show.js"></script>
</head>
<body>
<div>
</div>
<!-- 界面 -->
<div id="MainLayout">
	
    <div id="left">
        <div id="sys_grid"></div>
    </div>
    <div id="Right">
    	<div class="message" id="voteResult_show"></div>
    </div>
</div>

<!-- 脚本 -->
<script type="text/javascript">
	var contentHeight = $(self.parent.frames["mainContentIframe"].document).height()-40;
	$("#MainLayout").height(contentHeight);
	$("#sys_grid").height(contentHeight-10);
    $(document).ready(function () {
    	
    	xjj.cms.voteShow.init_showResultPage();
    });
  
</script>
</body>
</html>