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
<script src="${ctx}/pages/vote/js/vote_item.js"></script>
</head>
<body>
	<div id="menuInfoTab">
		<ul>
			<li id="FinanceItemTab" class="k-state-active">投票主题信息</li>
		</ul>
		<div id="form-container-edit">
			<form:form id="dataform" name="dataform" modelAttribute="dataform" method="post" action="${ctx}/cms/vote/item/saveOrUpdate.json">
				<input type="hidden" id="id" name="id" data-bind="value:id" />
				<!-- <input type="hidden" id="menuName" name="menuName" data-bind="value:menuName" />
				<input type="hidden" id="menuId" name="menuId" data-bind="value:menuId" /> -->
				<table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th width="150px">投票项名称<span style="color: red;"> *</span></th>
						<td colspan="3"><input id="item_name" class="k-textbox"
							required="required" validationMessage="请输入投票项名称"
							style="width: 95%" name="item_name" data-bind="value:item_name" />
						</td>
					</tr>
					<tr>
						<th>投票项类型</th>
						<td>
						<input type="radio" name="item_type" value="0" data-bind="checked: item_type" checked="checked" />单选
                		<input type="radio" name="item_type" value="1" data-bind="checked: item_type" />多选
                		<input type="radio" name="item_type" value="2" data-bind="checked: item_type" />问答
						</td>
					  <th>排序号</th>
                	<td>
                    	<input id="sort" name="sort" data-bind="value: sort" style="width:20%"/>
                	</td>
					</tr>
					<tr>
					<th>是否有效</th>
						<td colspan="3">
						<input type="radio" name="is_valid" value="0" data-bind="checked: is_valid" checked="checked" />是
                		<input type="radio" name="is_valid" value="1" data-bind="checked: is_valid" />否
						</td>
					</tr>
				</table>
			</form:form>
			<div class="clear" style="height: 10px"></div>
			<div align="center">
				<button id="submit_confirm" class="k-button width70" >保存</button>
				<button id="cancel" class="k-button width70">退出</button>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
    //下拉数据源
    $(document).ready(function(){
    	xjj.cms.vote.init_editPage();
    });
    function check(){
    	var same_ip_delay = document.getElementById("same_ip_delay");
    	var voteTotal = document.getElementById("voteTotal");
    	if(same_ip_delay != null){
    		if (isNaN(same_ip_delay.value)){
		    	alert("只能输入正整数！");
				same_ip_delay.value="";
			}
    	}
    	if(voteTotal != null){
    		if (isNaN(voteTotal.value)){
		    	alert("只能输入正整数！");
				voteTotal.value="";
			}
    	}
    	
    }
</script>

</html>