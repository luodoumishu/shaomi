<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/assets/ueditor1_4_3/themes/default/css/ueditor.css" type="text/css">
<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
<script src="${ctx}/assets/_base/js/jquery.form.js"></script>
<script src="${ctx}/assets/ColorDialog/ControlInfo.js" type="text/javascript"></script>
<script src="${ctx}/assets/ColorDialog/ColorDialog.js" type="text/javascript"></script>
<script type="text/javascript">
   var path = "<%=path%>";
   var basePath ="<%=basePath%>";
   //设置UEDITOR的路径
   window.UEDITOR_HOME_URL = location.protocol + "//"+ document.domain + (location.port ? (":" + location.port):"") + path+"/assets/ueditor1_4_3/";
   //window.UEDITOR_HOME_URL = path+"/assets/ueditor1_4_3/";
   var editor = null;
</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/assets/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/assets/ueditor1_4_3/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/assets/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script src="${ctx}/comm/js/core.js"></script>
<script src="${ctx}/pages/article/js/article.js"></script>

</head>
<body id="openId">
	<div id="menuInfoTab">
		<ul>
			<li id="FinanceItemTab" class="k-state-active">文章信息</li>
		</ul>
		<div id="form-container-edit">
			<form:form id="dataform" name="dataform" modelAttribute="dataform" method="post" action="${ctx}/cms/article/saveOrUpdate.json" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id" data-bind="value:id" />
				<input type="hidden" id="createTime" name="createTime" data-bind="value:createTime" />
				<input type="hidden" id="creatUserId" name="creatUserId" data-bind="value:creatUserId" />
				<input type="hidden" id="creatUserName" name="creatUserName" data-bind="value:creatUserName" />
				<table class="xjj-table-detail" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="100">标题<span style="color: red;"> *</span></th>
						<td colspan="2" width="60%"><input id="title" class="k-textbox" disabled="disabled"
							required="required" validationMessage="标题不能为空!" style="width: 85%;" name="title" data-bind="value: title" maxlength="90"/>
							<span style="margin-left: 2px;"><font style="color: red;">标题最长为90字</font></span>
						</td>
						<td>
							<span>颜色</span>
							<input type="text" disabled="disabled" id="titleColor" class="k-textbox" style="width: 50px" name="titleColor" readonly="readonly" data-bind="value: titleColor"/>
							<span>大小</span>
							<input style="width: 55px;" disabled="disabled" id="titleSize" name="titleSize" data-bind="value: titleSize"/>
					</td>
					</tr>
					<tr>
						<th>选择栏目<span style="color: red;"> *</span></th>
						<td width="50%">
							<div id="menuId_div" data-bind="value: menuId" style="z-index: 15000;width: 90%;" ></div>
							<input type="hidden" id="menuId" name="menuId" data-bind="value:menuId" />
							<input type="hidden" id="menuName" name="menuName" data-bind="value:menuName" />
						</td>
						<th width="100">文章作者</th>
						<td>
                			<input disabled="disabled" style="width: 95%" id="author" name="author" class="k-textbox" data-bind="value: author"/>
						</td>
					</tr>
					<tr>
						<th>文章来源</th>
						<td>
							<input id="source" disabled="disabled" name="source" class="k-textbox" data-bind="value: source" style="width: 95%"/>
						</td>
						<th>发文时间</th>
						<td>
							<input style="width: 95%" disabled="disabled" id="publicTime" name="publicTime" data-bind="value: publicTime"/>
						</td>
					</tr>
					<tr>
						<th>阅读次数</th>
						<td>
							<input style="width: 95%" disabled="disabled" id="readCount" name="readCount" data-bind="value: readCount"/>
						</td>
						<th>关键字</th>
						<td>
							<input style="width: 95%"  disabled="disabled" id="keyWord" class="k-textbox" name="keyWord" data-bind="value: keyWord"/>
						</td>
					</tr>
					<tr>
						<th>正文<span style="color: red;"> *</span></th>
						<td colspan="3">
							<textarea disabled="disabled" id="content" rows="" cols="" style="width:100%;height:400px;" name="content" required="required" validationMessage="正文不能为空!"></textarea>
							<%--<script  id="content"  style="width:100%;height:400px;" name="content" type="text/plain" onmouseout="test();"></script>
							--%>
							<script type="text/javascript">
								editor = UE.getEditor("content");
								editor.addListener('focus',function(){
									xjj.cms.article.getImg();
								 })
							</script>
						</td>
					</tr>
					<tr>
						<th>摘要</th>
						<td colspan="3">
							<textarea disabled="disabled" style="width: 98%;height: 80px;" id="summary" name="summary" data-bind="value: summary"></textarea>
						</td>
					</tr>
					<tr>
						<th>代表性图片</th>
						<td >
							<div id="show_behalfImage" style="float: left;"></div>
							<!-- <button id="del_behalfImage"  class="k-button width40" style=" margin-left: 8px" onclick="return xjj.cms.article.delDehalfImg();">清除</button> -->
						</td>
					</tr>
					</table>
			</form:form>
			<div class="clear" style="height: 10px"></div>
			<div align="center">
				<button id="check_pass" class="k-button width70">通过</button>
				<button id="check_unpass"  class="k-button width70">不通过</button>
			</div>
		</div>
	</div>
	  
</body>
<script type="text/javascript">

var _height = $(document.body).height();
var height_body = $(document).height();
if(height_body > _height){
	_height = height_body - _height;
	if(_height < 100){
		_height += 100;
	}
}else{
	_height = 190;
}
//监听UE准备就绪后会触发事件 
	editor.addListener("ready",function(){
		editor.setHeight(300);
		if("edit" == editType){
			if(null != editor){
				editor.execCommand("insertHtml", item.content || "");
			}
		}
	})
//监听UE内容发生改变时会触发事件 ，自动获取正文前200个字，做为摘要
editor.addListener("contentChange",function(){
	editor.setHeight(300);
	var plainTxt = editor.getContentTxt();
	if(" " != plainTxt){
		$("#summary").text(plainTxt.substring(0,200));
	}
})

    //下拉数据源
    $(document).ready(function(){
    	$("#menuInfoTab").height(height_body);
    	$("#edui1_toolbarbox").hide();
    	xjj.cms.article.init_editPage();
    	$("#del_behalfImage").click(function(){
  			var $behalfImage =  $("#show_behalfImage img");
  			if(null != $behalfImage && "undefined" != $behalfImage){
  				$behalfImage.remove();
  			}
    	})
    });
</script>
</html>