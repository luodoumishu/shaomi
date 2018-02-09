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
<script src="${ctx}/pages/menu/js/menu.js"></script>
</head>
<body>
	<div id="menuInfoTab">
		<ul>
			<li id="FinanceItemTab" class="k-state-active">栏目信息</li>
		</ul>
		<div id="form-container-edit">
			<form id="dataform" name="dataform" method="post">
				<input type="hidden" id="id" name="id" data-bind="value:id" />
				<input type="hidden" data-bind="value:parentMenuName" />
				<table class="xjj-table-detail" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="100">栏目名称<span style="color: red;"> *</span></th>
						<td width="50%"><input id="menuName" class="k-textbox"
							required="required" validationMessage="请输入栏目名称"
							style="width: 95%" name="menuName" data-bind="value: menuName" />
						</td>
						<th width="100">排序号</th>
						<td><input id="sortNo" value="0"
							style="width: 95%" name="sortNo" data-bind="value: sortNo" />
						</td>
						
					</tr>
					<tr>
						<th>父栏目</th>
						<td>
							<div id="parentMenuId" data-bind="value: parentMenuId" style="width: 95%;"></div>
						</td>
						<th>是否有效</th>
						<td>
							<input type="radio" name="isValid" value="0" data-bind="checked: isValid" checked="checked"/>有效
                			<input type="radio" name="isValid" value="1" data-bind="checked: isValid" />无效
						</td>
					</tr>
					<tr>
						<th>栏目类型</th>
						<td>
							<input type="radio" name="menuType" value="0" data-bind="checked: menuType" checked="checked" onclick="checkMenuType(this);"/>真实栏目
                			<input type="radio" name="menuType" value="1" data-bind="checked: menuType" onclick="checkMenuType(this);"/>跳转栏目 
                			<input type="radio" name="menuType" value="2" data-bind="checked: menuType" onclick="checkMenuType(this);"/>虚拟栏目
						</td>
						<th>内容类型</th>
						<td>
							<input type="radio" name="articleContentType" value="0" data-bind="checked: articleContentType" checked="checked"/>文章类
                			<input type="radio" name="articleContentType" value="1" data-bind="checked: articleContentType" />视频类
						</td>
					</tr>
					<tr id="url" style="display: none;">
						<th>链接地址</th>
						<td colspan="3">
							<input type="text" class="k-textbox" name="skipUrl" style="width: 95%" data-bind="value: skipUrl" />
						</td>
					</tr>
					<tr>
						<th>打开方式</th>
						<td>
							<input type="radio" name="openMode" value="0" data-bind="checked: openMode" checked="checked"/>当前窗口打开
                			<input type="radio" name="openMode" value="1" data-bind="checked: openMode" />新窗口打开
						</td>
						<th>显示方式</th>
						<td>
							<input type="radio" name="showMode" value="0" data-bind="checked: showMode" checked="checked"/>文章列表
                			<input type="radio" name="showMode" value="1" data-bind="checked: showMode" />文章内容
                			<input type="radio" name="showMode" value="2" data-bind="checked: showMode" />下载列表
						</td>
						</tr>
					<tr>
						<th>栏目配图</th>
						<td colspan="2">
							<div id = "show_menu_img_div"></div>
							<input type="hidden" id="menu_img_url" name="menu_img_url" data-bind="value:menu_img_url" />
						</td>
						<td>
								<iframe id="hidden_iframe" src="${ctx}/pages/menu/menuImgForm.jsp">
								</iframe>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea id="remark" class="k-textbox" style="width: 98%;height: 60px" name="remark" data-bind="value: remark"></textarea>
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
    	xjj.cms.menu.init_editPage();
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