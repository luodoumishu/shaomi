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
		orgCode = "btx";
	}
	if(StringUtil.isEmpty(orgName)){
		orgName = "海南保亭";
	}
	orgName = URLDecoder.decode(orgName,"utf-8");
%>
<script src="${ctx}/assets/_base/js/jquery-1.8.3.js"></script>
<!--公共-->
<div class="xjj_topdb" >
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

<div class="bt_top"><img src="images/bt_top.jpg" width="1000" height="219" /></div>
<!--头部 end-->
<!--导航-->
<div class="bt_navbox">
	<div class="bt_nav">
		<ul>
			<c:import url="daohang.jsp"></c:import>
		</ul>
	</div>
	<div class="bt_funav">
		<div class="bt_pathtime">
            <div class="tb_time">
            	<script language="JavaScript">
				
				var enabled = 0; today = new Date();
				var day; var date;
				if(today.getDay()==0) day = "星期日"
				if(today.getDay()==1) day = "星期一"
				if(today.getDay()==2) day = "星期二"
				if(today.getDay()==3) day = "星期三"
				if(today.getDay()==4) day = "星期四"
				if(today.getDay()==5) day = "星期五"
				if(today.getDay()==6) day = "星期六"
				document.fgColor = "000000";
				date = "◎今天是:" + (today.getFullYear()) + "年" + (today.getMonth() + 1 ) + "月" + today.getDate() + "日&nbsp;&nbsp;&nbsp;&nbsp;" + day +"";
				document.write(date);
				</script>
            </div>
            <div style="float: right;margin-top: 5px;width: 220px;">
			 		<input id="searchValue" name="searchValue" type="text" style="line-height:20px;position: absolute;height: 20px;" />
            		<img src="images/bt_ss.jpg" width="64" height="27" onclick="search_title();" style="cursor: pointer;position: absolute;margin-left:160px;" /> 
			</div>
        </div>
	</div>
</div>

	<script language="javascript">
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
