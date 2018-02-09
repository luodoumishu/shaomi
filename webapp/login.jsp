<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/comm/taglib.jsp"%>
<%-- <%@ page import="org.apache.shiro.SecurityUtils" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    //org.apache.shiro.SecurityUtils.getSubject().logout();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户登录</title>
	<link rel="stylesheet" href="${ctx}/assets/_base/css/loading-new.css">
	<script type="text/javascript" src="assets/js/encode64.js"></script>
	<style type="text/css">
		html,body{
			height:100%;
		}
		body{
			font-size:12px; font-family:"宋体";
			background:url(${ctx}/assets/_base/images/login/dltu001.jpg) repeat-x #6db5dc;
			margin:0;
			padding:0;
		}
		.yz_box{
			background:#fff;
		}
		.yz_boximg{
			padding:7px;
		}
		.yz_ipt{
			color:#0e3f87;
			font-weight:bold;
		}
		.yz_tex{
			text-align:center;
			color:#aaa9a9;
			line-height:23px;
		}
		.yz_bot{
			height:11px;
			background:url(${ctx}/assets/_base/images/login/btss.jpg) no-repeat;
			font-size:8px;
		}
		/*隐藏图层*/
		#mainhideDiv {
			background-color:#CCCCCC;
			filter: Alpha(Opacity=50);
		}
	</style>
</head>
<body>
<%
	Object obj = request.getAttribute(org.apache.shiro.web.filter.authc.
	        FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	String msg = "";
	if (obj != null) {
	    if ("org.apache.shiro.authc.UnknownAccountException".equals(obj))
	        msg = "尊敬的用户你好，您所填写的用户不存在或密码不正确，请重新填写！谢谢！";
	    else if ("org.apache.shiro.authc.IncorrectCredentialsException".equals(obj))
	        msg = "尊敬的用户你好，您所填写的用户不存在或密码不正确，请重新填写！谢谢！";
	    else if ("org.apache.shiro.authc.AuthenticationException".equals(obj))
	        msg = "尊敬的用户你好，您所填写的用户不存在或密码不正确，请重新填写！谢谢！";
	}
	if(request.getParameter("msg")!=null){
	   msg = request.getParameter("msg");
	}
%>

	<div id="mainhideDiv" style="position:absolute;z-index:100;visibility:hidden"></div>
	<span class="loading" style="display:none" id="shoId"><img src="${ctx}/assets/_base/images/login/loading.gif">正在登录系统...</span>
	<form name="loginform" action="${ctx}/login.do" method="post" style="display:block;height:100%;">
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
			<td valign="middle">
				<table width="632" height="394" border="0" align="center" cellpadding="0" cellspacing="0" class="yz_mid">
				  <tr>
					<td valign="top">
						<div class="yz_box">
							<div class="yz_boximg"><img src="${ctx}/assets/_base/images/login/ztu.jpg" /></div>
							<div class="yz_ipt">
							   	<img src="${ctx}/assets/_base/images/login/dltu006.jpg" align="absmiddle"/>
								<span style="font-size: 12px;margin-left: -8px;">用户名:</span><input type="text" size="8" name="username" id="username" value="admin" Check=1 Show="用户名" onkeyup="if(event.keyCode==13)login()" /> 
								<span style="font-size: 12px;margin-left: -5px;">密码:</span><input type="password" size="8" name="_upwd" id="_upwd" value="876543" Check=1 Show="密码" onkeyup="if(event.keyCode==13)login()"/>
								<!-- onkeyup="if(event.keyCode==13)login()" _verifyCode -->
								<span style="font-size: 12px;margin-left: -5px;">验证码:</span><input type="text" size="4" name="_verifyCode" id="_verifyCode" MaxLength="4" Check=1 Show="验证码" onkeyup="if(event.keyCode==13)login()"/>
								<span id="randomStr" class="gy_spa"><img src="${ctx}/imageServlet/verifyCode.do" style="cursor:pointer;margin-left: -5px;" onclick="changimage()" width="60" height="20" /></span>
							   <img title="点击登录"　id="LoginImg" src="${ctx}/assets/_base/images/login/dltu007.jpg" align="absmiddle"  onClick="javascript:login();" style="cursor:pointer;" />
							</div>
							<%
								if(msg!=null && !"".equals(msg)){
							%><div style="color:red">&nbsp;&nbsp;
								<%=msg %>
							</div>
							<%} %>
							<div class="yz_tex">海南新境界软件有限公司技术支持</div>
							<div class="yz_bot"></div>
						</div>
					</td>
				  </tr>
			  </table>
			</td>
		  </tr>
		</table>
	</form>
</body>
</html>
<script type="text/javascript">
		
	function login(){
		var user_name = document.loginform.username.value;
		var user_pwd = document.loginform._upwd.value;
		user_name = encode64(user_name);
		user_pwd = encode64(user_pwd);
		document.loginform.username.value = user_name;
		document.loginform._upwd.value = user_pwd;
		hideDivShow();
		document.getElementById("shoId").style.display="";
// 		alert(document.loginform.username.value);
		document.loginform.submit();
	}
	function hideDivShow(){
		var w = (screen.width-680)/2;
		var _w = 680+w;
		var obj = document.getElementById("mainhideDiv");
		obj.style.left = 0;
		obj.style.top = 0;
		obj.style.width=screen.width;
		obj.style.height = screen.height;//document.body.scrollHeight
		obj.style.visibility = "visible";
	}
	function changimage(){
		document.getElementById("randomStr").innerHTML="<img src='${ctx}/imageServlet/verifyCode.do?id="+Math.random()+"' style='cursor:pointer;' onclick='changimage()' width='60' height='20' />";
	}
</script>

