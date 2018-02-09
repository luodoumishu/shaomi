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
<script src="${ctx}/assets/kendoUI/js-ext/kendo.upload.ext.js"></script>
<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
<script src="${ctx}/assets/_base/js/jquery.form.js"></script>
<script src="${ctx}/comm/js/core.js"></script>
<script type="text/javascript">
    	var path = "<%=path%>";
    	var ip  = "<%=ip%>";
</script>
<script src="${ctx}/pages/link/js/link.js"></script>
</head>
<body>
	<div id="menuInfoTab">
		<ul>
			<li id="FinanceItemTab" class="k-state-active">超链接项信息</li>
		</ul>
		<div id="form-container-edit">
			<form id="dataform" name="dataform" method="post" >
				<input type="hidden" id="id" name="id" data-bind="value:id" />
				<!-- <input type="hidden" id="menuName" name="menuName" data-bind="value:menuName" />
				<input type="hidden" id="menuId" name="menuId" data-bind="value:menuId" /> -->
				<table class="xjj-table-detail" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="100">超链接项名称<span style="color: red;"> *</span></th>
						<td width="50%"><input id="linkName" class="k-textbox"
							required="required" validationMessage="请输入超链接项名称"
							style="width: 95%" name="linkName" data-bind="value:linkName" />
						</td>
						<th>超链接类型</th>
						<td>
                		<input type="radio" name="linkType" value="0" data-bind="checked: linkType" checked="checked" onclick="checkLinkType(this);"/>文字类型
                		<input type="radio" name="linkType" value="1" data-bind="checked: linkType" onclick="checkLinkType(this);"/>图片类型
                	</td>
					</tr>
					<tr>
						<th>超链接地址</th>
						<td>
							<input id="linkAddr" class="k-textbox" style="width: 95%;" name="linkAddr" data-bind="value: linkAddr"></input>
						</td>
						<th>打开方式</th>
						<td>
						<input type="radio" name="openType" value="0" data-bind="checked: openType" checked="checked"/>新窗口打开
                		<input type="radio" name="openType" value="1" data-bind="checked: openType" />当前窗口打开
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea id="remark" class="k-textbox" style="width: 98%;height: 60px" name="remark" data-bind="value: remark"></textarea>
						</td>
					</tr>
					<tr id = "linkImg" style="display: none;">
						<th>上传图片</th>
						<td colspan="2">
							<div id = "show_link_img_div"></div>
							<input type="hidden" id="linkImg" name="linkImg" data-bind="value:linkImg" style="height:100px;width:200px;"/>
						</td>
						<td>
								<iframe id="hidden_iframe" src="${ctx}/pages/link/linkImgForm.jsp">
								</iframe>
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
    	xjj.cms.link.init_editPage();
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
    function checkLinkType(e){
    	if(e.value == 1){
    		document.getElementById("linkImg").style.display = "";
    	}else{
    		document.getElementById("linkImg").style.display = "none";
    	}
    }
</script>

</html>