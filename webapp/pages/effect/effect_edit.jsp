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
<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
<script src="${ctx}/assets/_base/js/jquery.form.js"></script>
<script src="${ctx}/comm/js/core.js"></script>
<script type="text/javascript">
    	var path = "<%=path%>";
    	var ip  = "<%=ip%>";
</script>
<script src="${ctx}/pages/effect/js/effect.js"></script>
</head>
<body>
	<div id="menuInfoTab">
		<ul>
			<li id="FinanceItemTab" class="k-state-active">特效信息</li>
		</ul>
		<div id="form-container-edit">
			<form id="dataform" name="dataform" modelAttribute="dataform" method="post" action="${ctx}/cms/effect/saveOrUpdate.json" enctype="multipart/form-data">
				
				<input type="hidden" id="id" name="id" data-bind="value:id" />
				<input type="hidden" data-bind="value:orgId" />
				<input type="hidden" id="createTime" name="createTime" data-bind="value:createTime" />
				<input type="hidden" id="creatUserId" name="creatUserId" data-bind="value:creatUserId" />
				<input type="hidden" id="creatUserName" name="creatUserName" data-bind="value:creatUserName" />
				
				<table class="xjj-table-detail" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="100">特效名称<span style="color: red;"> *</span></th>
						<td colspan="3"><input id="name" class="required k-textbox"
							validationMessage="请输入特效名称"
							style="width: 95%" name="name" data-bind="value: name" />
						</td>
						<th width="100">特效代码</th>
						<td colspan="3">
							<input id="code" class="k-textbox"  style="width: 95%" name="code" data-bind="value: code" />
						</td>
					</tr>
					<tr>
						<th>排序号</th>
						<td colspan="2">
                			<input id="sortNo" value="0" style="width: 95%" name="sortNo" data-bind="value: sortNo" />
						</td>
						<th>特效类型</th>
						<td colspan="2">
							<input type="radio" name="type" value="0" data-bind="checked: type" checked="checked" onchange="xjj.cms.effect.showOrhide2pc(this);"/>飘窗
                			<input type="radio" name="type" value="1" data-bind="checked: type" onchange="xjj.cms.effect.showOrhide2pc(this);"/>自定义
						</td>
						<th>页面是否显示</th>
						<td colspan="2">
							<input type="radio" name="isShow" value="0" data-bind="checked: isShow"  checked="checked"/>显示
                			<input type="radio" name="isShow" value="1" data-bind="checked: isShow" />隐藏
						</td>
					</tr>
					<tr pc="true">
						<th width="100">飘窗图片</th>
						<td>
								<iframe id="hidden_iframe" src="${ctx}/pages/effect/effectImgForm.jsp">
								</iframe>
						</td>
						<th width="100">图片预览</th>
						<td colspan="5">
							<div id = "show_pc_img_div"></div>
							<input type="hidden" id="imgUrl" name="imgUrl" data-bind="value:imgUrl" />
						</td>
						
					</tr>
					<tr pc="true">
						<th width="100">链接地址</th>
						<td colspan="7">
						<input type="text" class="k-textbox" name="url" style="width: 95%" data-bind="value: url" />
						</td>
					</tr>
					<tr pc="true">
						<th width="100">移动步长</th>
						<td>
							<input id="step" style="width: 95%" name="step" data-bind="value: step" />
						</td>
						<th width="100">移动间隔</th>
						<td width="10%">
							<input id="delay" style="width: 95%" name="delay" data-bind="value: delay"/>
						</td>
						<th width="100">X轴偏移量</th>
						<td width="10%">
							<input id="x_offset" style="width: 95%" name="x_offset" data-bind="value: x_offset"/>
						</td>
						<th width="100">Y轴偏移量</th>
						<td width="10%">
							<input id="y_offset" style="width: 95%" name="y_offset" data-bind="value: y_offset"/>
						</td>
					</tr>
					<tr id="htmlCode_code" style="display: none">
						<th>html代码</th>
						<td colspan="7">
							<textarea id="htmlCode" class="k-textbox" style="width: 98%;height: 400px" name="htmlCode" data-bind="value: htmlCode"></textarea>
						</td>
					</tr>
				</table>
			</form>
			<div class="clear" style="height: 10px"></div>
			<div align="center">
				<button id="submit_confirm" class="k-button width70">保存</button>
				<button id="cancel" class="k-button width70">退出</button>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
    //下拉数据源
    $(document).ready(function(){
    	xjj.cms.effect.init_editPage();
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