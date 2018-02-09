<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head>
    <title>栏目推送</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="<%=path %>/assets/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>
    <script type="text/javascript">
   		var path = "<%=path%>";
   		var basePath ="<%=basePath%>";
   	 	var checkMenus = new Array();
   	 	var orgTreeAry = new Array();
	</script>
	<script type="text/javascript" src="<%=basePath %>/assets/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath %>/assets/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
</head>
<body>
 <!-- 界面 -->
    <div style="height: 55%;">
    	<div style="float: left;width: 30%;height: 100%;overflow: auto;border: #dbdbde solid 1px;">
    		<div>待推送栏目列表</div>
			<ul id="menuTree" class="ztree"></ul>
		</div>
		<div style="float: left;width: 30%;height: 100%;overflow: auto;border: #dbdbde solid 1px;">
    		<div>目标单位列表</div>
			<ul id="orgTree" class="ztree"></ul>
		</div>
		<div style="float: left;height: 100%;overflow: auto;border: #dbdbde solid 1px;width: 38%;">
    		<div>目标栏目列表</div>
			<ul id="targetMenuTree" class="ztree"></ul>
		</div>
    </div>
    <div style="height: 35%;">
		<div style="float: left;width: 30%;height: 100%;overflow: auto;border: #dbdbde solid 1px;">
			<div>待推送栏目:</div>
			<ul id="check_menu" class="ztree"></ul>
		</div>
		<div style="float: left;width: 30%;height: 100%;overflow: auto;">
		</div>
		<div style="float: left;height: 100%;overflow: auto;border: #dbdbde solid 1px;width: 38%;">
			<div>目标栏目</div>
			<ul id="target_menu" class="ztree"></ul>
		</div>
    </div>
    <div style="text-align: center;">
    	<a id="transmit_bt" class="k-button"><span> 推 送 </span></a>
    </div>
    <!-- 脚本 -->
    <script type="text/javascript" src="js/menu_transmit.js"></script>
    <script type="text/javascript">
	$(document).ready(function(){
		ajax4SourceMenuTree();
  		$("#transmit_bt").click(function(){
	    	var menuTree = $.fn.zTree.getZTreeObj("menuTree");
	    	if(null == menuTree){
	    		AlertDialog({
					Title : "提示信息",
					Message : "请选择待推送栏目！",
					Icon : "k-ext-information"
				});
	    		return;
	    	}else{
	    		var menus = menuTree.getCheckedNodes(true);
	    	}
	    	var targetMenuTree = $.fn.zTree.getZTreeObj("targetMenuTree");
	    	if(null == targetMenuTree){
	    		AlertDialog({
					Title : "提示信息",
					Message : "请选择目标栏目！",
					Icon : "k-ext-information"
				});
	    		return;
	    	}else{
	    		var targetMenus = targetMenuTree.getCheckedNodes(true);
	    		var orgTree = $.fn.zTree.getZTreeObj("orgTree");
		    	var orgs = orgTree.getCheckedNodes(true);
	    	}
			if(null == menus || menus.length == 0){
				AlertDialog({
					Title : "提示信息",
					Message : "请选择待推送栏目！",
					Icon : "k-ext-information"
				});
				return;
			}else if(null == targetMenus || targetMenus.length == 0){
				AlertDialog({
					Title : "提示信息",
					Message : "请选择目标栏目！",
					Icon : "k-ext-information"
				});
				return;
			}else{
// 				alert("推送");
				var menuTreeAry = new Array();
				var targetMenuTreeAry = new Array();
				for (var i = 0; i < menus.length; i++) {
					var checkNode = menus[i];
					if(!checkNode.isParent){
						menuTreeAry.push(checkNode.id);
					}
				}
				for (var j = 0; j < targetMenus.length; j++) {
					var checkNode = targetMenus[j];
					if(!checkNode.isParent){
						targetMenuTreeAry.push(checkNode.id);
					}
				}
				$.ajax({
					url : "${ctx}/cms/menuTransmit/saveTransmit.json",
					data : {
						"menuTreeAry" : menuTreeAry.toString(),
						"targetMenuTreeAry" : targetMenuTreeAry.toString(),
						"orgTreeAry" : orgTreeAry.toString()
					},
					success : function(result){
// 						Icon: "k-ext-information"
						AlertDialog({
							Title : "提示信息",
							Message : "推送成功！",
							Icon : "k-ext-information"
						}).done(function(){
							window.location.href="/pages/menu/menu_transmit_list.jsp";
						});
					},
					error : function(result){
						AlertDialog({
							Title : "提示信息",
							Message : "推送失败！",
							Icon : "k-ext-error"
						});
					}
				});
			}
		});
	});
	
    </script>
</body>
</html>