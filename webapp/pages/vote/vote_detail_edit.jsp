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
<script src="${ctx}/pages/vote/js/vote_detail.js"></script>
</head>
<body>
	<div id="menuInfoTab">
		<ul>
			<li id="FinanceItemTab" class="k-state-active">投票项明细信息</li>
		</ul>
		<div id="form-container-edit">
			<form:form id="dataform" name="dataform" modelAttribute="dataform" method="post" action="${ctx}/cms/vote/detail/saveOrUpdate.json">
				<input type="hidden" id="id" name="id" data-bind="value:id" />
				<table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th width="150px">投票项明细名称<span style="color: red;"> *</span></th>
						<td colspan="3"><input id="detail_name" class="k-textbox"
							required="required" validationMessage="请输入投票项明细名称"
							style="width: 95%" name="detail_name" data-bind="value:detail_name" />
						</td>
					</tr>
					<tr>
						<th>投票数</th>
						<td>
                			<input type="text" id="vote_num" class="k-textbox" onkeyup="check();" style="width: 98%;" name="vote_num" data-bind="value: vote_num"></input>
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
    	var voteNum = document.getElementById("vote_num");
    	if(voteNum != null){
    		if (isNaN(voteNum.value)){
		    	alert("只能输入正整数！");
		    	voteNum.value="";
			}
    	}
    	
    }
</script>

</html>