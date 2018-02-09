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
<link href="${ctx}/assets/_base/css/attachment.css" rel="stylesheet"></link>
<%@ include file="/comm/kendo_js.jsp"%>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.upload.ext.js"></script>
<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
<script src="${ctx}/assets/_base/js/jquery.form.js"></script>
<script src="${ctx}/comm/js/core.js"></script>
<script type="text/javascript">
    	var path = "<%=path%>";
    	var ip  = "<%=ip%>";
</script>
<script src="${ctx}/pages/file/js/file.js"></script>
</head>
<body>
	<div id="menuInfoTab">
		<ul>
			<li id="FinanceItemTab" class="k-state-active">文件信息</li>
		</ul>
		<div id="form-container-edit">
			<form id="dataform" action="${ctx }/cms/file/save.json" name="dataform" method="post" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id" data-bind="value:id" />
				<input type="hidden" id="menuName" name="menuName" data-bind="value:menuName" />
				<input type="hidden" id="menuId" name="menuId" data-bind="value:menuId" />
				<table class="xjj-table-detail" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="100">文件标题<span style="color: red;"> *</span></th>
						<td width="50%"><input id="fileTitle" class="k-textbox"
							required="required" validationMessage="请输入文件标题"
							style="width: 95%" name="fileTitle" data-bind="value: fileTitle" />
						</td>
						<th>所属栏目</th>
						<td>
							<div id="menuId_div"  style="width: 95%;"></div>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea id="remark" class="k-textbox" style="width: 98%;height: 60px" name="remark" data-bind="value: remark"></textarea>
						</td>
					</tr>
					<tr>
						<th width="100">上传附件<span style="color: red;"> *</span></th>
						<td colspan="2" width="60%">
							<table id ="affix">
								<tr><input name="files" id="files" type="file" value="上传附件"/></tr>
							</table>
						</td>
					</tr>
				</table>
			</form>
			<div class="clear" style="height: 10px"></div>
			<div align="center">
				<button id="submit_confirm" class="k-button width70" >保存</button>
				<button id="cancel" class="k-button width70">退出</button>
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
        case '.wps':
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
    //下拉数据源
    $(document).ready(function(){
    	xjj.cms.file.init_editPage();
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
    });
    function checkMenuType(e){
    	if(e.value == 1){
    		document.getElementById("url").style.display = "";
    	}else{
    		document.getElementById("url").style.display = "none";
    	}
    }
</script>

</html>