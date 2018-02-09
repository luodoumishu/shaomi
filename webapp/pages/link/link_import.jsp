<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String channelId= request.getParameter("channelId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>栏目管理</title>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
     <link rel="stylesheet" href="<%=path %>/assets/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>
    <script src="${ctx}/comm/js/core.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
   
    <script type="text/javascript">
     var path = "<%=path%>";
     var channelId = "<%=channelId%>";
    	//树对象
   	 var itemTree = null;
   	 // 数据源对象
   	 var dataSource = null;
   	 // 列表对象,用于对表格数据的操作
   	 var grid = null;
   	 // 查询条件对象
   	 var queryObj = null;
   	 // 数据对象，用于新增和修改
   	 var item = null;
   	 // 编辑的类型,默认为新增
   	 var editType = "add";
   	 // 下拉数据源
   	 var selectSource = null;
   	 // 栏目名称
   	 var menuName = null;
   	 //父栏目id初始值
   	 var parentMenuId = "0";
   	 //跟节点
   	 var rootId = "0";
   	 //下拉栏目树
   	 var selectMenuTree = null;
   	 var menuTreeObj = null;
     </script>
	<script src="${ctx}/pages/link/js/link.js"></script>
	<script type="text/javascript" src="${ctx}/comm/js/commonJs.js"></script>
</head>
<body scroll="no">
    <!-- 界面 -->
     <div id="MainLayout" style="height: 598px;">
      <div class="k-toolbar">
                <div class="xjj-tree-toolbar" >
                    <div class="xjj-group">
                        <a id="TreeRefresh" class="k-button">
                            <span class="k-icon k-i-refresh"></span>刷新
                        </a>
                    </div>
                    <div class="xjj-l-bar-separator" id="TreeRefreshSeparator"></div>
                    <div class="xjj-group">
                        <a id="TreeExpand" class="k-button ">
                            <span class="k-icon k-i-expand"></span>展开
                        </a>
                    </div>
                    <div class="xjj-l-bar-separator"></div>
                    <div class="xjj-group">
                        <a id="TreeCollapse" class="k-button">
                            <span class="k-icon k-i-collapse"></span>收起
                        </a>
                    </div>
                </div>
            <div id="LeftLayout" class="ztree">
            </div>
        </div>
        <div id="Right">
		     <!-- <div style="background-color: #F5F5F5">
			       <a id="addLink" class="k-button" ><span>保存</span></a>
			        &nbsp;
			       <a id="reback" class="k-button"><span>返回</span></a>
		     </div> -->
        <div id="grid"></div>
       </div>
       </div>
</body>
 <!-- 脚本 -->
    <script type="text/javascript">
    var contentHeight = $(self.parent.frames["mainContentIframe"].document).height()-5;
	 $("#MainLayout").height(contentHeight);
	 $("#grid").height(contentHeight);
	 $(document).ready(function () {
	 	 $("#MainLayout").kendoSplitter({
                panes: [
                    { collapsible: true, size: "20%" },
                    { collapsible: true, size: "80%" },
                ]
         });
         // 树配置
		var setting = {
				check: {
					enable: true,
				    chkboxType : { "Y": "s", "N": "ps" }
				},
				async: {
					enable: true,
					chkStyle: "checkbox",
					url:path+"/cms/chlLink/getLinkTree.json",
					otherParam: ["channelId", channelId], //根据实际设置成频道id
					dataFilter: filter
				},
				view:{
					showIcon:false,
					showLine:false
				},
				callback: {
					onCheck: addChangeNode
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
	 	
		var rootNode = [{id:"0",name:"超链接列表",isParent:true, open:true}];
	  
	   menuTreeObj = $.fn.zTree.init($("#LeftLayout"), setting,rootNode);
			var nodes = menuTreeObj.getNodes();
			if (nodes.length>0) {
				//设置第一个节点选中
				menuTreeObj.selectNode(nodes[0]);
				//展开根据点
				menuTreeObj.expandNode(nodes[0], true, null, null, null);
			}
		 xjj.cms.link.init_importListPage();
  	 });
  	 
  	 function addChangeNode(){
			var treeObj = $.fn.zTree.getZTreeObj("LeftLayout");
 			var nodes = treeObj.getCheckedNodes(true);
 			var length = nodes.length;
 			var change_id = "";
 			for(var i =0;i<length;i++){ 
 				change_id += nodes[i].id+",";
 			}
 				//alert(change_id);
				//AjaxDoPost(path+"/cms/chlLink/addChangeNodes.json",{"linkIds":change_id,"channelId":channelId},false,Success,Error);
			$.ajax({ 
				url: path+"/cms/chlLink/addChangeNodes.json",
				type:"post",
				data:{"linksId":JSON.stringify(change_id),"channelId":channelId},
			success: function(result){
				if (result.resultCode === 0) {
					if (result.resultMsg == "") {
						grid.dataSource.read();
					}else{
						alert(result.resultMsg);
					}
				}else{
						alert(result.resultMsg);
				}
      		},
      		error:function(){
      			alert("保存失败");
      		}
      });
			}				 	
	//添加栏目成功回调函数
	function Success(result) {
		if (result.resultCode === 0) {
				if (result.resultMsg == "") {
					grid.dataSource.read();
				} else {
					alert(result.resultMsg);
				}
				} else {
					alert(result.resultMsg);
				}
			}

	//添加栏目失败回调函数
	function Error(result) {
	}
  	 //保存按钮
	$("#addLink").click(function () {
		var checkList = document.getElementsByName("chbox");
		var linkIds = "";
		if(checkList != null){
			for ( var i = 0; i < checkList.length; i++) {
				if(checkList[i].checked == true){
					linkIds += checkList[i].id + ",";
				}
			}
		}
		$.ajax({ 
		url: path+"/cms/chlLink/addLink.json",
		type:"post",
		data:{"linkIds":linkIds,"channelId":channelId},
		success: function(){
			window.location.href= path+"/pages/channel/channel_list.jsp";
      	},
      	error:function(){
      		alert("保存失败");
      	}
      });
	});
	//返回按钮
	$("#reback").click(function () {
		history.back();
	});
	//刷新按钮
	$("#TreeRefresh").click(function() {
		grid.dataSource.read();
		menuTreeObj.refresh();
	});
	//展开按钮
	$("#TreeExpand").click(function() {
		ExpandItemTree();
	});
	//收缩按钮
	$("#TreeCollapse").click(function() {
		CollapseItemTree();
	});
	//展开树
	function ExpandItemTree() {
		menuTreeObj.expandAll(true);
	}
	//收缩树
	function CollapseItemTree() {
		menuTreeObj.expandAll(false);
	}
	 </script>
</html>