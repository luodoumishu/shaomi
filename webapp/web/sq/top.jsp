<%@page import="java.net.URLDecoder"%>
<%@page import="com.xjj.jdk17.utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@ include file="/comm/js/taglib.jsp"%>
<%@ taglib prefix="xjj" uri="/WEB-INF/tld/xjj_portal.tld"%>
<%
	String path = request.getContextPath();
	String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String basePath = ip+path+"/";
	
	String orgCode = request.getParameter("orgCode");
	String orgName = request.getParameter("orgName");
	if(StringUtil.isEmpty(orgCode)){
		orgCode = "lgx";
	}
	if(StringUtil.isEmpty(orgName)){
		orgName = "海南临高";
	}
	orgName = URLDecoder.decode(orgName,"utf-8");
%>
<script src="${ctx}/assets/_base/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<!--公共-->
<div class="lg_tygb" >
	<iframe width="100%" height="60px" frameborder="0" scrolling="no" id="dwlm"></iframe>
</div>
<!--公共 end-->
<script>
window.onload = function(){
	var _src = "http://dwlm.12371.cn/daohang/index.shtml"; 
	$("#dwlm").attr("src",_src);
}
</script>

<!--头部-->
<!--乡镇-->
<div class="xz_box">
    <h1>| <%=orgName%></h1>
    <div class="xz_cunm" id="childlist" style="max-height: 80px;overflow: auto;">
    </div>
</div>
<!--乡镇 end-->
<div class="lg_top">
	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"   
	  codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="1000" height="173">  
	  <param name="movie" value="images/top.swf" />  
	  <param name="quality" value="high" />  
	  <param name="wmode" value="transparent">  
	  <embed wmode="transparent" src="images/top.swf" mce_src="images/top.swf" quality="high" type="application/x-shockwave-flash"   
	  pluginspage="http://www.macromedia.com/go/getflashplayer" width="1000" height="173"></embed>  
	  </object> 
</div>
<!--头部 end-->

<!--导航-->
<div class="lg_navbox">
	<div class="lg_nav">
		<ul>
			<c:import url="daohang.jsp"></c:import>
		</ul>
	</div>
	<div class="lg_timenav">
        <div class="lg_serch">
        	<input id="searchValue" name="" type="text"  class="lg_ipt2" />
        	<img src="images/lg_sousuo.png" width="57" height="23" onclick="search_title();"  style="cursor: pointer"/> 
        </div> 
    </div>
</div>

	<script type="text/javascript">
		var path = "<%=basePath%>";
   		$(document).ready(function(){
   			var orgCode = "<%=orgCode%>";
   			initChildOrgs(orgCode);
   		});
   		
		function search_title(){
			var searchValue = $("#searchValue").val();
			if(searchValue == ""){
				alert("请输入查询条件！");
				return;
			}
			searchValue = encodeURI(searchValue,'utf-8');
			var url = "searchResult.jsp?searchValue=" + searchValue+"&orgCode=<%=orgCode%>&orgName=<%=orgName%>";
			window.open(url);	
		}
	</script>
<!--导航 end-->
