<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<link href="${ctx}/assets/_base/css/attachment.css" rel="stylesheet"></link>
<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
<%@ include file="/comm/kendo_js.jsp"%>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.upload.ext.js"></script>
<script src="${ctx}/assets/_base/js/jquery.form.js"></script>
<script type="text/javascript">
   var path = "<%=path%>";
   var basePath ="<%=basePath%>";
</script>
<script src="${ctx}/comm/js/core.js"></script>

</head>
<body id="openId">
	<div id="menuInfoTab">
		<ul>
			<li id="FinanceItemTab" class="k-state-active">上传附件例子</li>
		</ul>
		<div id="form-container-edit">
			<form:form id="dataform" name="dataform" modelAttribute="dataform" method="post" action="${ctx}/cms/test/formSave.json" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id" data-bind="value:id" />
				<table class="xjj-table-detail" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="100">附件标题<span style="color: red;"> *</span></th>
						<td colspan="2" width="60%"><input id="name" class="k-textbox"
							required="required" style="width: 95%" name="name" data-bind="value: title" />
						</td>
					</tr>
					<tr>
						<th width="100">上传附件<span style="color: red;"> *</span></th>
						<td colspan="2" width="60%">
							<input name="files" id="files" type="file" value="上传附件"/>
						</td>
					</tr>
					</table>
			</form:form>
			<div class="clear" style="height: 10px"></div>
			<div align="center">
				<button id="submit_confirm" class="k-button width70">保存</button>
				<button id="cancel"  class="k-button width70">退出</button>
			</div>
		</div>
	</div>
	  
</body>
<script type="text/javascript">
function addExtensionClass(extension) {
    switch (extension) {
        case '.jpg':
        case '.img':
        case '.png':
        case '.gif':
            return "img-file";
        case '.doc':
        case '.docx':
            return "doc-file";
        case '.xls':
        case '.xlsx':
            return "xls-file";
        case '.pdf':
            return "pdf-file";
        case '.zip':
        case '.rar':
            return "zip-file";
        default:
            return "default-file";
    }
}

    $(document).ready(function(){
    	 $(document).ready(function() {
             $("#files").kendoUpload({
            	 localization: {
  	            	"select": "选择附件",
  	                "cancel": "取消",
  	                "retry": "重试",
  	                "remove": "删除",
  	                "uploadSelectedFiles": "上传选定的文件",
  	                "statusUploading": "上传中",
  	                "statusUploaded": "上传",
  	                "statusFailed": "上传失败",
  	                "statusWarning": "上传警告",
  	                "headerStatusUploading": "上传中...",
  	                "headerStatusUploaded": "完成"
  	            },
  	          template: "<div class='file-wrapper' style='width: 300px'><span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button></div>"
             });
             
             var  options ={
			         type:'post',                    
			         success:function(data){
			        	 alert("执行成功回调");
			         },
			         error:function(){
			        	 alert("执行失败回调");
			         }
			      }; 
             $("#submit_confirm").click(function(){
            	 $("#dataform").ajaxSubmit(options);
             });
         });
    });
</script>
</html>