<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/comm/taglib.jsp"%>
<%@ include file="/comm/javaScript.jsp"%>
<%
	if(session.getAttribute("userId")==null){
%>
	<SCRIPT LANGUAGE="JavaScript">alert("登录超时！请重新登录!");parent.window.location="<%=request.getContextPath()%>/login.jsp";</SCRIPT>  
<%}
%>
<%
	String toUrl = request.getParameter("url");
    if(toUrl.indexOf(";")!=-1) toUrl = toUrl.replaceAll(";","&");
%>
<html>
	<head>
		<link rel="stylesheet" href="${ctx}/assets/_base/css/loading-new.css">
	</head>
	<body>
		<div id="mainhideDiv" style="position:absolute;z-index:100;visibility:hidden"></div>
		<span class="loading" style="display:none" id="shoId"><img src="${ctx}/assets/_base/img/loading.gif">正在处理您的请求...</span>
    </body>
</html>
<script type="text/javascript">
	toUrlIndex('<%=toUrl%>');
	function toUrlIndex(url){
		hideDivShow();
		document.getElementById("shoId").style.display="";
		window.location.href=decodeURIComponent("${ctx}"+url);
	}
</script>