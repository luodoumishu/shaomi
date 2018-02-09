<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head>
    <title>栏目管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="<%=path %>/assets/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
    <script type="text/javascript" src="<%=path %>/assets/zTree_v3/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
    <!-- 脚本 -->
    <script type="text/javascript">
    var path = "<%=path%>";
 // 树配置
	var setting = {
			check: {
				enable: true,
			    chkboxType : { "Y": "s", "N": "ps" }
			},
			async: {
				enable: true,
				url:path+"/CmsChannelItem/getMenuTree.json",
				autoParam:["id"],
				otherParam: ["channelId", "2b3d8eef-2beb-42b0-bd75-760205db95fa"], //根据实际设置成频道id
				dataFilter: filter
			}
		};
 	/**名称过滤**/
	var filter = function(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	} 	
 	
 	var getChangeNode = function(){
 		var treeObj = $.fn.zTree.getZTreeObj("MainLayout");
 		var nodes = treeObj.getCheckedNodes(true);
 		var length = nodes.length;
 		var change_name ="改变的名称： "; 
 		for(var i =0;i<length;i++){ 
 			change_name += nodes[i].name+",";
 		}
		alert(change_name);
 	} 
 	
	var rootNode = [{id:"0",name:"栏目列表",isParent:true, open:true}];
	
	$(document).ready(function(){
		/**根节点**/
		var menuTreeObj = $.fn.zTree.init($("#MainLayout"), setting,rootNode);
		var nodes = menuTreeObj.getNodes();
		if (nodes.length>0) {
			//设置第一个节点选中
			menuTreeObj.selectNode(nodes[0]);
			//展开根据点
			menuTreeObj.expandNode(nodes[0], true, null, null, null);
		}
	});
	 </script>
</head>
<body>
    <!-- 界面 -->
    <div id="MainLayout" style="height: 598px;"  class="ztree">
    	
     </div>
     <button onclick="getChangeNode()">获取选中的id</button>
</body>
</html>