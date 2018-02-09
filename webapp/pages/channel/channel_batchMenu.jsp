<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
     String id =request.getParameter("id");
	 String path = request.getContextPath();
	 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>频道管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
     <link rel="stylesheet" href="<%=path %>/assets/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>  
     <script type="text/javascript" src="${ctx}/comm/js/commonJs.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
</head>
<body>
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
        		<!-- <button onclick="getChangeNode()">导入所选栏目</button> -->
               <div id="grid"></div>
           </div>
       </div>
       
       <!-- 选择框 -->
       <!-- <script type="text/x-kendo-template" id="chbox">
                <input name="chbox"  type="checkbox" />              
       </script> -->
       
    <!-- 脚本 -->
    <script type="text/javascript">
		  var contentHeight = $(self.parent.frames["mainContentIframe"].document).height()-5;
		  $("#MainLayout").height(contentHeight);
	 	  $("#grid").height(contentHeight);
		  //数据源对象
		  var dataSource = null;
		  //列表对象,用于对表格数据的操作
		  var grid = null;
		  //查询条件对象
		  var queryObj = null;
		  //数据对象，用于新增和修改
	 	  var Item = null;
		  //编辑的类型,默认为新增
		  var EditType = "add";
		  
		  //下拉数据源
		  var selectSource = null;
		  var jgSource = null;
		  var rootId  ="0";
		  var parentMenuId = rootId;//初始值
		  var path = "<%=path%>";
		  //频道ID
    	  var channelId =  "<%=id%>";
    	  var menuTreeObj = null;
        	$(document).ready(function () {
        	//页面分隔
        	$("#MainLayout").kendoSplitter({
                panes: [
                    { collapsible: true, size: "20%" },
                    { collapsible: true, size: "80%" }
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
						url:path+"/CmsChannelItem/getMenuTree.json",
						autoParam:["id"],
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
		 	
			var rootNode = [{id:"0",name:"栏目列表",isParent:true, open:true}];
    	  
    	   menuTreeObj = $.fn.zTree.init($("#LeftLayout"), setting,rootNode);
				var nodes = menuTreeObj.getNodes();
				if (nodes.length>0) {
					//设置第一个节点选中
					menuTreeObj.selectNode(nodes[0]);
					//展开根据点
					menuTreeObj.expandNode(nodes[0], true, null, null, null);
				}
			
          //数据源
       	  dataSource = CreateGridDataSource({
       	    Transport: {
                 ReadUrl: "${ctx}/CmsChannelItem/listMenu.json",
                 ReadDataFunc: function () {
               	    queryObj = new Object;
               	  	queryObj.parentMenuId=parentMenuId;
               	 	queryObj.itemId="<%=id%>";
                   	return queryObj;
                 }
            }
       	  });
        	      
            //数据窗口
            grid = CreateGrid({
            ID: "grid",
            DataSource: dataSource,
            Height: $(window).height()*0.9,
            Editable:"popup",
            ColumnMenu: false,    
            Filterable: false,    
            Columns: [ 
					{ field: "channeItemName",width: "15%",title: "频道项名称"},
					{ field: "sortno",width: "15%",title: "排序号"},
					{ field: "",width: "35%",title: "操作", command:[
					  {text: "编辑", click: editOrgs}
                      ]
                      }
                 ]
             });
         
        });
        
		       
				
					//修改初始化
					onUpdate = function() {
						var row = grid.select();
						selectRowIndex = row.index();
						Item = grid.dataItem(row);
						//编辑的类型：修改
						EditType = "edit";
						if (Item == null) {
							AlertDialog({
								Title : "提示信息",
								Message : "请选择您要编辑的数据！",
								Icon : "k-ext-warning"
							});
							return false;
						} else {
							return true;
						}
					}
					function addChangeNode(){
						var treeObj = $.fn.zTree.getZTreeObj("LeftLayout");
 						var nodes = treeObj.getCheckedNodes(true);
 						var length = nodes.length;
 						var change_id = "";
 						for(var i =0;i<length;i++){ 
 							change_id += nodes[i].id+",";
 						}
						AjaxDoPost("${ctx}/CmsChannelItem/addChangeNodes.json","models=" + JSON.stringify(change_id),false,Success,Error);
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
					
					function editOrgs() {
						var row = grid.select();
						selectRowIndex = row.index();
						Item = grid.dataItem(row);
						var url = "${ctx}/pages/channel/channel_batchMenu_edit.jsp";
						openwindow(url, "", "", 'edit', 'div');
					}
					//展开机构树
					function ExpandItemTree() {
						menuTreeObj.expandAll(true);
					}

					//收缩机构树
					function CollapseItemTree() {
						menuTreeObj.expandAll(false);
					}

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
					
				function expandLevel(treeObj,node,level){  
	            	var childrenNodes = node.children;  
	            	for(var i=0;i<childrenNodes.length;i++)  
	            		{  
	                	treeObj.expandNode(childrenNodes[i], true, false, false);  
	                	level=level-1;  
	                	if(level>0)  
	                	{  
	                    	expandLevel(treeObj,childrenNodes[i],level);  
	                	}  
	            	}  
       		 	}  
</script>
</body>
</html>
