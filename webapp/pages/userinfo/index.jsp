<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="${ctx}/pages/userinfo/js/jquery-1.3.2.js"></script>
<script src="${ctx}/pages/userinfo/js/jquery.Jcrop.js"></script> 
<link rel="stylesheet" href="${ctx}/pages/userinfo/css/jquery.Jcrop.css" type="text/css" /> 
<link href="${ctx}/pages/userinfo/css/blue.css" rel="stylesheet" type="text/css" id="stylecss"/>
<script type="text/javascript">
	function imgSubmit(obj) {
		if (document.getElementById("file").value == "") {
			alert("附件不能为空");
			return false;
		 }else{
			var url = $("#file").val();
			var ext = url.substr(url.indexOf("."));
			if (ext != ".jpg"&&ext != ".JPG") {
				alert("不可上传该格式文件");
				return false;
			} 
		}
		obj.submit();
		$("#uploadFile").hide();
		$("#uploading").show();
	}
	function infoSubmit() {
		doAjaxPost("${ctx}/pages/userinfo/userinfo.jsp", "");
	}

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
		$(".content").hide();
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
	/*  function checkImg(filespec)
	{
	   var fso, f;
	   var maxsize=1;//定义允许文件的大小，单位MB
	   fso = new ActiveXObject("Scripting.FileSystemObject");
	   
	   if (fso.FolderExists(filespec)) {
	      f = fso.GetFolder(filespec);
	    } else if (fso.FileExists(filespec))   {
	            f = fso.GetFile(filespec);
	        }   else   {
	            alert("该文件不存在！");
	            return false;
	    }
	   if(f.size>(maxsize*1024*1024))
	    {    alert( f.size);
	        alert("文件大小超出规定，请您选择小于"+maxsize+"字节的文件进行上传");
	        return false;
	    }
	   return true;
	}  */

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
</style>
</head>
<body>
<c:if test="${msg=='headSaveSuccess'}">
	<script type="text/javascript">
	alert("头像保存成功!");
	window.opener.location.reload();
	window.close();
	</script>
</c:if>
	<div class="fcbox" style="position:absolute; z-index:1002;" id="userinfoDiv">
	<div class="fctop" id="userinfoMove">个人信息</div>
	
	<!-- 图片剪裁 -->
	<c:if test="${!empty imageFileName}">
	<div id="cropImage" class="fccon">
		<script type="text/javascript">
			jQuery(window).load(function() {
				jQuery('#cropbox').Jcrop({
					onChange : showPreview,
					onSelect : showPreview,
					setSelect : [ 31, 0, 160, 150 ],
					aspectRatio : 6 / 7
				});
			});
			function showPreview(coords) {
				if (parseInt(coords.w) > 0) {
					// 预览图片的大小
					var rx = 120 / coords.w;
					var ry = 140 / coords.h;
					var _rx = '${imageWidth}';
					var _ry = '${imageHeigth}';

					jQuery('#preview').css(
							{
								width : Math.round(rx * _rx) + 'px',
								height : Math.round(ry * _ry) + 'px',
								marginLeft : '-' + Math.round(rx * coords.x) + 'px',
								marginTop : '-' + Math.round(ry * coords.y) + 'px'
							});
					jQuery('#x1').val(coords.x);
					jQuery('#y1').val(coords.y);
					jQuery('#x2').val(coords.x2);
					jQuery('#y2').val(coords.y2);
					jQuery('#w').val(coords.w);
					jQuery('#h').val(coords.h);
				}
			}
		</script>
		<form method="post" action="${ctx }/userInfo/crop.do" name="picForm">
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="fctab">
				<tr>
					<td align="center" valign="middle">
						请选择要作为头像的区域 <img src="${ctx}/heads/${imageFileName}" id="cropbox" />
					</td>
					<td align="center" valign="middle">
						<input type="hidden" name="account" value="${sessionScope.userAccount}" />
						<input type="hidden" name="imageFile" value="${imageFileName}" /> 
						<input type="hidden" size="4" id="x1" name="x1" /> 
						<input type="hidden" size="4" id="y1" name="y1" /> 
						<input type="hidden" size="4" id="x2" name="x2" /> 
						<input type="hidden" size="4" id="y2" name="y2" /> 
						<input type="hidden" size="4" id="w" name="width" /> 
						<input type="hidden" size="4" id="h" name="height" />
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" valign="bottom">
					<span class="span1" onclick="document.picForm.submit()" style="margin-left: 20px">提交</span></td>
				</tr>
			</table>
		</form>
	</div>
	</c:if>

	<c:if test="${empty imageFileName}">
	<script type="text/javascript">
	$(document).ready(function() {	
		//用户头像
		   $.ajax({
			type:"GET",
			dataType:"text",
			url:"${ctx }/userInfo/getUserHead.json?todo=headPicture",
			success:function(date){
				$("#headPicture2").attr("src","${ctx }/heads/"+date);
			},
			error:function(){
			}
		}); 
	});
	</script>
	<div class="fccon">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="90" valign="top"
					style="border-right: 1px solid #e0e0e0;">
					<div class="fcleft">
						<div class="fctoux">
							<img id="headPicture2" src=''>
						</div>
						<div class="fccnav">
							<ul>
								<li><a href="#" onclick="changeDiv('headPicture')">修改头像</a></li>
								<li><a href="#" onclick="changeDiv('changePw')">修改密码</a></li>
							</ul>
						</div>
					</div>
				</td>
				<td valign="top">
					<!-- 个人信息 -->
					<div id="userInfo" class="content" style="width: 100%;">
						<input type="hidden" id="id" name="id" value='${sessionScope.userId}' />
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="fctab">
							<tr>
								<td width="60" style="text-align: right;">姓名：</td>
								<td>
									&nbsp;<INPUT type="text" name="name" id="name" class="fcipt" value="${sessionScope.userName}" max="8" min="2" Show="姓名" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;">性别：</td>
								<td>&nbsp; 
									<input type="radio" name="gender" id="radio" value="1" ${sessionScope.gender=="1"?"checked": ""} />男 
									<input type="radio" name="gender" id="radio" value="2" ${sessionScope.gender=="2"?"checked": ""} />女
								</td>
							</tr>
							<tr>
								<td style="text-align: right;">地址：</td>
								<td>
									&nbsp;<INPUT type="text" name="addr" id="addr" class="fcipt" value="${sessionScope.addr}" max="80" min="0" Show="地址" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;">手机：</td>
								<td>
									&nbsp;<input type="text" name="mobileno" id="mobileno" class="fcipt" value="${sessionScope.mobileNo}" max="50" min="0" Show="手机号码" limit="number" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;">座机：</td>
								<td>
									&nbsp;<input type="text" name="phoneno" id="phoneno" class="fcipt" value="${sessionScope.phoneNo}" max="16" min="0" Show="座机号码" limit="number" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;">邮箱：</td>
								<td>
									&nbsp;<INPUT type="text" name="email" id="email" class="fcipt" value="${sessionScope.email}" max="30" min="0" Show="电子邮箱" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;">传真：</td>
								<td>
									&nbsp;<INPUT type="text" name="faxno" id="faxno" class="fcipt" value="${sessionScope.faxNo}" max="30" min="0" Show="传真" limit="number" />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td style="padding-left: 55px;">
									<span class="span1" onclick="infoSubmit();" style="margin-left: 20px">保存</span> 
									<!--  <span class="span1" onclick="userinfoOp('close');" style="margin-left: 20px">关闭</span> -->
								</td>
							</tr>
						</table>
					</div> <!-- 上传头像 -->
					<form action="${ctx }/userInfo/upload.do" name="imgForm" enctype="multipart/form-data" method="post">
						<div id="headPicture" class="content" style="display: none; width: 100%;">
							<div id="uploadFile">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="fctab">
									<tr>
										<td valign="middle" align="center">选择头像文件 <br />
											注意：文件格式为jpg或JPG,文件大小建议不要超过5M <br /> 
											<input type="file" name="file" id="file" style="height: 23px" />
										</td>
									<tr>
										<td style="padding-left: 55px;">
											<span class="span1" onclick="imgSubmit(document.imgForm);" style="margin-left: 20px">提交</span> 
											<span class="span1" onclick="changeDiv('userInfo');" style="margin-left: 20px">返回</span>
										</td>
									</tr>
								</table>
							</div>
							<div id="uploading" style="display: none; width: 100%; height: 100%; text-align: center; vertical-align: middle;">
								<img src="${ctx }/pages/userinfo/images/loading.gif" />上传中，请稍等...
							</div>
						</div>
					</form> 
					
					<!-- 修改密码 -->
					<div id="changePw" class="content" style="display: none; width: 100%;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="fctab">
							<tr>
								<td align="right">原密码：</td>
								<td><INPUT type="password" name="oldPw" id="pw" class="fcipt" max="15" min="4" Show="原密码" /></td>
							</tr>
							<tr>
								<td align="right">新密码：</td>
								<td><INPUT type="password" name="newPw" id="pw1" class="fcipt" max="15" min="8" Show="新密码" /></td>
							</tr>
							<tr>
								<td align="right">确认密码：</td>
								<td><INPUT type="password" name="newPw2" id="pw2" class="fcipt" maxlength="15" /></td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<span class="span1" onclick="updatePwdSubmit();" style="margin-left: 20px">提交</span>
									<span class="span1" onclick="changeDiv('userInfo');" style="margin-left: 20px">返回</span> <br />
									<span style="color: red">*密码为8到15位数字和字母组合字符串</span>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</c:if>
    <div class="fcbot"></div>
</div>
</body>
</html>
