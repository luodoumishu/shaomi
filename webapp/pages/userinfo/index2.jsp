<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="${ctx}/pages/userinfo/js/jquery-1.3.2.js"></script>
<link href="${ctx}/pages/userinfo/css/blue.css" rel="stylesheet" type="text/css" id="stylecss"/>
<script type="text/javascript">
	function updatePwdSubmit(){
		var obj = /^(([a-zA-Z]+[0-9]+)|([0-9]+[a-zA-Z]+))[a-zA-Z0-9]*$/i;
		var pw = $.trim($("#pw").val());
		var pw1 = $.trim($("#pw1").val());
		var pw2 = $.trim($("#pw2").val());
		if(pw.length==0){
			alert("请输入原密码！");
			$("#pw").focus();
		} else if(pw1.length==0){
			alert("新密码不能为空！");
			$("#pw1").focus();
		} else if(!(obj.test(pw1) && pw1.length>=8 && pw1.length<=15)) {
			alert("密码必须为字母和数字混合8到15位!");
			$("#pw1").focus();
		} else if(pw1 != pw2){
			alert("请再次确认新密码");
			$("#pw2").focus();
		}else{
			doAjaxPost("${ctx}/pages/userinfo/updatepwd.jsp", "");
		}
	}
	function changeDiv(div){
		$("#"+div).show();
	}
	function doAjaxPost(url, traget) {
		$.ajax({
			type: "POST",
			url: url,
			dateType: "html",
			data:{
				"id":$("#id").val(),
				"name":$("#name").val(),
				"gender":$("input[name='gender']:checked").val(),
				"addr":$("#addr").val(),
				"phoneno":$("#phoneno").val(),
				"mobileno":$("#mobileno").val(),
				"email":$("#email").val(),
				"faxno":$("#faxno").val(),
				"oldPwd":$("#pw").val(),
				"newPwd":$("#pw1").val()
				},
			success:function(data){
				var _temp = $.trim(data);
				if(_temp == "1") {
					alert("更改成功!系统将退出登录");
					window.opener.location.href="${ctx}/logout.jsp";
				} else {
					alert("更改失败!");
				}
				window.close();
			},
			error: function() {
				alert("加载页面错误"); 
			}
		});
	}
	var flag=true;
	function checkInput() {
		$("INPUT,TEXTAREA").bind("input propertychange", function() {
			var inputLength=$(this).val().length;
			var maxLength=parseInt($(this).attr("max"));
			var minLength=parseInt($(this).attr("min"));
			var obj=$(this).attr("Show");
			if(inputLength>maxLength){
				this.value = this.value.substring(0, maxLength);
				if(flag){
					alert(obj+"不能超过"+maxLength+"个字符");
					flag=false;
				}
			}else if(inputLength<maxLength){
				flag=true;
			}
			 if($(this).attr("limit")=="number"){
				 var o=this.value;
				 var n = this.value.replace(/[^0-9,-]/gi, "");
				 if(o!=n){
					 this.value= this.value.replace(/[^0-9,-]/gi, "");
				 }
			} 
		});
	}
	$(document).ready(function(){
		checkInput();
		$("INPUT,TEXTAREA").blur(function(){flag=true;});
	});
</script>
<style type="text/css">
* {
	font-size: 12px;
}
.fcleft {
	margin-left: 15px;
}
.fccon {
height: 240px;
}
body{margin:0 auto;text-align:center}
</style>
</head>
<body>
	<div class="fcbox" id="userinfoDiv">
	<div class="fctop" id="userinfoMove">修改密码</div>
    <div class="fccon">
    <%
		String imageFile=(String)request.getAttribute("imageFile");
		if(imageFile==null){
			imageFile="tu1.jpg";
	}
	%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="90" valign="top" style="border-right:1px solid #e0e0e0;">
    	<div class="fcleft">
        	<div class="fctoux"><img alt="" src='${ctx }/heads/<%=imageFile%>'></div>
            <div class="fccnav">
            	<ul>
                    <li><a href="#">修改密码</a></li>
                </ul>
            </div>
        </div>
    </td>
    <td valign="top">
			<!-- 修改密码 -->
			<div id="changePw" class="content" style="width: 100%;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="fctab">
					<tr>
						<td colspan="2" align="center">
							<span style="color: red">您的密码强度不符合要求,请修改您的密码</span>
						</td>
					</tr>
					<tr>
						<td align="right">原密码：</td><td><INPUT type="password" name="oldPw" id="pw" class="fcipt" max="15" min="6" Show="原密码"/></td>
					</tr><tr>
						<td align="right">新密码：</td><td><INPUT type="password" name="newPw" id="pw1" class="fcipt" max="15" min="8" Show="新密码"/></td>
					</tr><tr>
						<td align="right">确认密码：</td><td><INPUT type="password" name="newPw2" id="pw2" class="fcipt" maxlength="15"/></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<span class="span1" onclick="updatePwdSubmit();" style="margin-left: 20px">提交</span><br/>
							<span style="color: red">*密码为8到15位数字和字母组合字符串</span>
					</td>
					</tr>
				</table>
			</div>
	</td>
  </tr>
		</table>
    </div>
    <div class="fcbot"></div>
</div>
<script type="text/javascript">
    $(function() {
        window.onresize = function () {
            var heights = $(window).height();
            var widths = $(window).width();
            $("#userinfoDiv").css("margin-left", widths/2 - 200);
            $("#userinfoDiv").css("margin-top", heights/2 - 200);
        };
        window.onresize();
    });
    </script>
</body>
</html>
