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
    	var upLinkImg = function(){
   		 var options ={
   			url:path+"/cms/link/upLinkImg.json",
   	        type:'post',                    
   	        success:function(data){
   	        var jsonDate = eval( "(" + data + ")" );
   	       	 if(0 != jsonDate.resultCode){
   	       		 alert(jsonDate.resultMsg);
   	       	 }else{
   	       		 var imURL =  jsonDate.resultData;
   	       		 var imgHtml = '<img  style="height: 100px; width: 250px;" src="'+ip+imURL+'"></img>';
   	       		 $(parent.document).find("#show_link_img_div").html(imgHtml);
   	       		 $(parent.document).find("#linkImg").val(imURL);
   	       	 }
   	        },
   	        error:function(){
   	       	 	alert("上传图片失败");
   	        }
   	     };
   	   var form =$("#link_img_form");
   	   form.ajaxSubmit(options);  
   	}
   	var delLinkImg = function(){
   		var $linkImage =  $(parent.document).find("#show_link_img_div");
   		if(null != $linkImage && "undefined" != $linkImage){
   			$linkImage.remove();
   			$(parent.document).find("#linkImg").val("");
   		}
   	}
</script>
<%--<script src="${ctx}/pages/menu/js/menu.js"></script>
--%></head>
<body>
	<form id="link_img_form" style="border: 0" method="post" enctype="multipart/form-data">
		<input name ="imgFile" type="file" style="width: 160px;" onchange="upLinkImg()"></input>
		<button class="k-button width40" style="margin-left: 10px;" onclick="delLinkImg();">清除</button>
	</form>
</body>
</html>