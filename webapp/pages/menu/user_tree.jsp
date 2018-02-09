<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
String menuId = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>栏目权限配置-用户树</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="<%=path %>/assets/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path %>/assets/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
    <!-- 脚本 -->
    <script type="text/javascript">
    var path = "<%=path%>";
    var menuId ="<%=menuId%>";
    var menuTree = null;
    var rootNode = null;
 // 树配置
	var setting = {
			check: {
				enable: true,
			    chkboxType : { "Y": "s", "N": "ps" }
			},
			async: {
				enable: true,
				url:path+"/cms/menuUser/getMenuTree.json",
				autoParam:["id"],
				otherParam: ["menuId", menuId], //根据实际设置成频道id
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
	};	
 	
	rootNode = [{id:"0",name:"用户列表",isParent:true, open:true}];
	
	$(document).ready(function(){
		/**根节点**/
		menuTree = $.fn.zTree.init($("#tree"), setting,rootNode);
		var nodes = menuTree.getNodes();
		if (nodes.length>0) {
			//设置第一个节点选中
			menuTree.selectNode(nodes[0]);
			//展开根据点
			menuTree.expandNode(nodes[0], true, null, null, null);
		}
	});
	
	//监听保存按钮
	$("#submit_confirm").click(function () {
	   var treeObj = $.fn.zTree.getZTreeObj("tree");
 		var nodes = treeObj.getCheckedNodes(true);
 		var length = nodes.length;
 		var change_id ="";
 		for(var i =0;i<length;i++){
 			if(!nodes[i].isParent){
 			change_id += nodes[i].id+",";
 			}
 		}
		AjaxDoPost(path+"/cms/menuUser/changeUser.json",
							"models=" + JSON.stringify(change_id),
							 false,Success,Error);
	});
		//添加用户成功回调函数
					function Success(result) {
						if (result.resultCode === 0) {
							if (result.resultMsg == "") {
							    closewindow("div");
								dataSource.read();
							} else {
								alert(result.resultMsg);
							}
						} else {
							alert(result.resultMsg);
						}
					}

					//添加用户失败回调函数
					function Error(result) {
					}
					
					//展开机构树
					function ExpandTree() {
						var node = menuTree.getSelectedNodes();
						menuTree.expandNode(node[0],true);
					}

					//收缩机构树
					function CollapseTree() {
						var node = menuTree.getSelectedNodes();
						menuTree.expandNode(node[0],false);
					}

					//刷新按钮
					$("#refresh").click(function() {
						dataSource.read();
						menuTree.refresh();
					});
					//展开按钮
					$("#expand").click(function() {
						ExpandTree();
					});

					//收缩按钮
					$("#collapse").click(function() {
						CollapseTree();
					});
	 </script>
	 <style type="text/css">
	 	#button{
	 		position: relative;
	 	}
	 	#tree{
	 		height: 85%;
	 		overflow-y:scroll; 
	 	}
	 </style>
</head>
<body scroll="no">
    <!-- 界面 -->
    <div class="xjj-tree-toolbar" >
                    <div class="xjj-group">
                        <a id="refresh" class="k-button">
                            <span class="k-icon k-i-refresh"></span>刷新
                        </a>
                    </div>
                    <div class="xjj-l-bar-separator" id="TreeRefreshSeparator"></div>
                    <div class="xjj-group">
                        <a id="expand" class="k-button ">
                            <span class="k-icon k-i-expand"></span>展开
                        </a>
                    </div>
                    <div class="xjj-l-bar-separator"></div>
                    <div class="xjj-group">
                        <a id="collapse" class="k-button">
                            <span class="k-icon k-i-collapse"></span>收起
                        </a>
                    </div>
                </div>
    <div id="tree" class="ztree">
     </div>
     <div align="center" id="button">
        <button id="submit_confirm" class="k-button width70">保存</button>
    </div>
</body>
</html>