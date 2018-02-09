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
<script src="${ctx}/pages/vote/js/vote_theme.js"></script>
</head>
<body>
	<div id="menuInfoTab">
		<ul>
			<li id="FinanceItemTab" class="k-state-active">投票主题信息</li>
		</ul>
		<div id="form-container-edit">
			<form:form id="dataform" name="dataform" modelAttribute="dataform" method="post" action="${ctx}/cms/vote/theme/saveOrUpdate.json">
				<input type="hidden" id="id" name="id" data-bind="value:id" />
				<!-- <input type="hidden" id="menuName" name="menuName" data-bind="value:menuName" />
				<input type="hidden" id="menuId" name="menuId" data-bind="value:menuId" /> -->
				<table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th width="150px">投票主题名称<span style="color: red;"> *</span></th>
						<td><input id="themeName" class="k-textbox"
							required="required" validationMessage="请输入投票主题名称"
							style="width: 70%" name="themeName" onkeyup="checkLen(this);" data-bind="value:themeName" />
							<span style="margin-left: 10px;color: gray;">还可以输入 </span><span id="count" style="color: gray;">40</span><span style="color: gray;"> 个文字</span> 
						</td>
						<th width="150px">显示代码</th>
						<td>
                		<input id="showCode" class="k-textbox" style="width: 98%;" name="showCode" data-bind="value: showCode"></input>
                		</td>
					</tr>
					<tr>
						<th>是否有效</th>
						<td>
						<input type="radio" name="isValid" value="0" data-bind="checked: isValid" checked="checked" />是
                		<input type="radio" name="isValid" value="1" data-bind="checked: isValid" />否
						</td>
					  <th>排序号</th>
                	<td>
                    	<input id="sort" name="sort" data-bind="value: sort" style="width:20%"/>
                	</td>
					</tr>
					<tr>
						<%--<th>同IP延迟（小时）</th>
						<td>
                			<input type="text" id="same_ip_delay" class="k-textbox" onkeyup="check();" style="width: 98%;" name="same_ip_delay" data-bind="value: same_ip_delay"></input>
                		</td>--%>
                		<th>总投票数</th>
						<td colspan="3">
                			<input type="text" id="voteTotal" class="k-textbox" onkeyup="check();" style="width: 98%;" name="voteTotal" data-bind="value: voteTotal"></input>
                		</td>
					</tr>
					<%--<tr>
						<th>是否同IP只能投票一次</th>
						<td colspan="3">
						<input type="radio" name="is_same_ip_vote" value="0" data-bind="checked: is_same_ip_vote" checked="checked" />是
                		<input type="radio" name="is_same_ip_vote" value="1" data-bind="checked: is_same_ip_vote" />否
						</td>
					</tr>
					<tr>
						<th>是否同MAC只能投票一次</th>
						<td colspan="3">
						<input type="radio" name="is_same_mac_vote" value="0" data-bind="checked: is_same_mac_vote" checked="checked" />是
                		<input type="radio" name="is_same_mac_vote" value="1" data-bind="checked: is_same_mac_vote" />否
						</td>
					</tr>
					<tr>
						<th>是否要求输入投票人</th>
						<td colspan="3"> 
						<input type="radio" name="is_need_person" value="0" data-bind="checked: is_need_person" checked="checked" />是
                		<input type="radio" name="is_need_person" value="1" data-bind="checked: is_need_person" />否
						</td>
					</tr>
					 --%><tr>
                	<th >备注</th>
	                	<td colspan="3">
	                    	<textarea id="remark" class="k-textbox" style="width: 98%;height: 60px" name="remark" data-bind="value: remark"></textarea>
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
    function checkLen(obj) {  

    	var maxChars = 40;//最多字符数  

    	if (obj.value.length > maxChars)  obj.value = obj.value.substring(0,maxChars);  

    	var curr = maxChars - obj.value.length;  

    	document.getElementById("count").innerHTML = curr.toString(); 
    }
</script>

</html>