<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
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
						<td colspan="2" width="60%"><input id="title" class="k-textbox"
							required="required" validationMessage="标题不能为空!" style="width: 85%;" name="title" data-bind="value: title" maxlength="90"/>
							<span style="margin-left: 2px;"><font style="color: red;">标题最长为90字</font></span>
						</td>
						<td>
							<span>颜色</span>
							<input type="text" id="titleColor" class="k-textbox" style="width: 50px" name="titleColor" readonly="readonly" data-bind="value: titleColor"/>
							<span>大小</span>
							<input style="width: 55px;" id="titleSize" name="titleSize" data-bind="value: titleSize"/>
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
                			<input style="width: 95%" id="author" name="author" class="k-textbox" data-bind="value: author"/>
						</td>
					</tr>
					<tr>
						<th>文章来源</th>
						<td>
							<input id="source"  name="source" class="k-textbox" data-bind="value: source" style="width: 95%"/>
						</td>
						<th>发文时间</th>
						<td>
							<input style="width: 95%" id="publicTime" name="publicTime" data-bind="value: publicTime"/>
						</td>
					</tr>
					<tr>
						<th>阅读次数</th>
						<td>
							<input style="width: 95%" id="readCount" name="readCount" data-bind="value: readCount"/>
						</td>
						<th>关键字</th>
						<td>
							<input style="width: 95%" id="keyWord" class="k-textbox" name="keyWord" data-bind="value: keyWord"/>
						</td>
					</tr>
					<!-- <tr>
						<th>是否启用网上评论</th>
						<td>
							<input type="radio" name="ifComment" value="0" data-bind="checked: ifComment" checked="checked"/>不启用
	                		<input type="radio" name="ifComment" value="1" data-bind="checked: ifComment" />启用
						</td>
					</tr> -->
					<tr>
						<th>正文<span style="color: red;"> *</span></th>
						<td colspan="3">
							<textarea id="content" rows="" cols="" style="width:100%;height:400px;" name="content" required="required" validationMessage="正文不能为空!"></textarea>
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
							<textarea  style="width: 98%;height: 80px;" id="summary" name="summary" data-bind="value: summary"></textarea>
						</td>
					</tr>
					<tr>
						<th>代表性图片地址</th>
						<td id="behalf_imageUrl_td">
							<input type="hidden" id="behalf_imageUrl" name="behalf_imageUrl" data-bind="value:behalf_imageUrl" />
						</td>
						<td>
							<input type="button" class="k-button" onclick="xjj.cms.article.getImg();" value="获取代表性图片地址" />
						</td>
						<td>
							<div  style="font-size: 12px;color:blue">为了提高页面的浏览效果，代表性图片大小设置为<font color="red" style="font-size: 14px">302x242</font></div>
						</td>
					</tr>
					<tr>
						<th>代表性图片预览<span style="color: red;"> *</span></th>
						<td id="img_show_div" colspan="3">
							<ul class="ul_style" style="margin-top: 3px;">
								<li>
									宽度：<input id="behalf_image_width"  name="behalf_image_width" data-bind="value:behalf_image_width" type="text" style="width: 50px" onkeyup="xjj.cms.article.changeImgWidth()"/>
									高度：<input id="behalf_image_height" name="behalf_image_height" data-bind="value:behalf_image_height"  type="text" style="width: 50px" onkeyup="xjj.cms.article.changeImgHeight()"/>
									<span style="margin-top: 10px"><img id="lockImg" src="images/unlock.png" style="width: 20px;height: 20px;cursor: pointer;"  title="不锁定" onclick="lockClick()" /></span>
									<img id="resetImg" src="images/reset.png" style="width: 20px;height: 20px;cursor: pointer;"  title="恢复尺寸" onclick="resetClick()" />
									<span><button id="del_behalfImage"  class="k-button" style="height:22px;margin-left: 5px" onclick="return xjj.cms.article.delDehalfImg();">清除</button></span>
								</li>
								<li style="float: left;">
									<div id="show_behalfImage" style="float: bottom;overflow: auto;margin-top: 3px"></div>
								</li>
							</ul>
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
//判断是否锁定图片尺寸
function lockClick(){
	var title = $("#lockImg").attr("title");
	if(title == "锁定比例"){
		$("#lockImg").attr("src","images/unlock.png");
		$("#lockImg").attr("title","不锁定");
	}else{
		$("#lockImg").attr("src","images/lock.png");
		$("#lockImg").attr("title","锁定比例");
	}

}

//恢复图片原始尺寸
function resetClick(){
// 		var src = $("#image").attr("src");
// 		xjj.cms.article.showImg(src);
	var imgH = $("#image").attr("height");
	var imgW = $("#image").attr("width");
	$("#behalf_image_height").val(imgH);
	$("#behalf_image_width").val(imgW);
	$("#image").css("height",imgH);
	$("#image").css("width",imgW);
}
//设定代表性图片TD的宽度
$("#img_show_div").ready(function(){
	var img_show_div_w = $("#img_show_div").width();
	$("#show_behalfImage").css({
		"max-height":"273px",
		"max-width":img_show_div_w
	});
})

    //下拉数据源
    $(document).ready(function(){
    	$("#menuInfoTab").height(height_body);
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