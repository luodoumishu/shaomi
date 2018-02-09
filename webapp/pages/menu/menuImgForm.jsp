<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
String basePath = ip+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>
<script src="${ctx}/assets/_base/js/jquery.form.js"></script>
<script src="${ctx}/comm/js/core.js"></script>
<script type="text/javascript">
    	var path = "<%=path%>";
    	var ip  = "<%=ip%>";
    	var upMenuImg = function(){
   		 var options ={
   			url:path+"/cms/menu/upMenuImg.json",
   	        type:'post',                    
   	        success:function(data){
   	        var jsonDate = eval( "(" + data + ")" );
   	       	 if(0 != jsonDate.resultCode){
   	       		 alert(jsonDate.resultMsg);
   	       	 }else{
   	       		 var imURL =  jsonDate.resultData;
   	       		 var imgHtml = '<img  style="height:150px;width:500px;" src="'+ip+imURL+'"></img>';
   	       		 $(parent.document).find("#show_menu_img_div").html(imgHtml);
   	       		 $(parent.document).find("#menu_img_url").val(imURL);
   	       	 }
   	        },
   	        error:function(){
   	       	 	alert("上传图片失败");
   	        }
   	     };
   	   var form =$("#menu_img_form");
   	   form.ajaxSubmit(options);  
   	}
   	var delMenuImg = function(){
   		var $menuImage =  $(parent.document).find("#show_menu_img_div");
   		if(null != $menuImage && "undefined" != $menuImage){
   			$menuImage.remove();
   			$(parent.document).find("#menu_img_url").val("");
   		}
   	}
</script>
<%--<script src="${ctx}/pages/menu/js/menu.js"></script>
--%></head>
<body>
	<form id="menu_img_form" style="border: 0" method="post" enctype="multipart/form-data">
		<input name ="imgFile" type="file" style="width: 160px;" onchange="upMenuImg()"></input>
		<%--<button class="k-button width40" style="margin-left: 10px;" onclick="upMenuImg();">提交</button>
		--%><button class="k-button width40" style="margin-left: 10px;" onclick="delMenuImg();">清除</button>
	</form>
</body>
</html>