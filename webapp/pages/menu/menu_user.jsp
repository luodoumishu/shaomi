<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>栏目权限配置</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>    
     <script type="text/javascript" src="${ctx}/comm/js/commonJs.js"></script>
     <script type="text/javascript">
   var path = "<%=path%>";
   var basePath ="<%=basePath%>";
</script>
</head>
<body scroll="no">
 <!-- 界面 -->
    <div id="MainLayout" style="height:598px;">
    	<div id="LeftLayout">
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
            </div>
            <div id="itemTree" style="height: 94%;"></div>
        </div>
           <div id="Right">
           			<div style="background-color: #F5F5F5">
	           			<a id="adduser" class="k-button" ><span>添加用户</span></a>
	           			&nbsp;
	                	<a id="deluser" class="k-button"><span>删除用户</span></a>
                	</div>
               <div id="grid"></div>
           </div>
       </div>
    <!-- 脚本 -->
    <script type="text/javascript">
    	var contentHeight = $(self.parent.frames["mainContentIframe"].document).height()-5;
		$("#MainLayout").height(contentHeight);
	 	$("#grid").height(contentHeight);
          //树对象
          var itemTree = null;
		  //数据源对象
		  var dataSource = null;
		  //列表对象,用于对表格数据的操作
		  var grid = null;
		  //查询条件对象
		  var queryObj = null;
		  //数据对象，用于新增和修改
	 	  var Item = null;
		  //下拉数据源
		  var selectSource = null;
		  //初始值
		  var parentMenuId = "0";
    	  var menuId = "0";
        $(document).ready(function () {
        	//页面分隔
        	$("#MainLayout").kendoSplitter({
                panes: [
                    { collapsible: true, size: "20%" },
                    { collapsible: true, size: "80%" }
                ]
            });
          //数据源
       	  dataSource = CreateGridDataSource({
       	    Transport: {
                 ReadUrl: path+"/cms/menuUser/list.json",
                 ReadDataFunc: function () {
               	    queryObj = new Object;
               	  	queryObj.menuId = menuId;
                   	return queryObj;
                 }
            }
       	  });
            //数据窗口
            grid = CreateGrid({
            ID: "grid",
            DataSource: dataSource,
            Height: $(window).height()*0.95,
            Editable:"popup",
            ColumnMenu: false,    
            Filterable: false,    
            Columns: [
            		  { template: "<input type='checkbox' text='"+'#: id #'+"' value='"+'#: id #'+"'  id='"+'#: id #'+"' name='chbox' >"
            		  ,width: "1%",title: "<div><input type='checkbox' id='allbox' name='allbox'  onclick=selectAll('allbox')></div>"}, 
                      { field: "userName",width: "15%",title: "用户名称"}
                    /*   { field: "orgs.id",width: "24%",title: "所属单位"}, */
                 ]
             });
            itemTree = $("#itemTree").kendoTreeView({
			                 dataTextField: "menuName",
			                 select: function(e) {
			                 	menuId = this.dataItem(e.node).id;
			                 	dataSource.read();
			                   }
			             }).data("kendoTreeView");
            LoadItemTree();
        });
        
        function LoadItemTree() {
            JsonPostForWaiting({
                Url: path+"/cms/menu/fjsqTreeList.json",
                WaittingText: "正在加载栏目...",
                AimDiv: "#LeftLayout",
                Success: function (ResultMsg, Parameter) {
                        var data="";
                        if(ResultMsg.ResultMsg.resultData!=null){
                           data = ResultMsg.ResultMsg.resultData;
                        }
                    //if(ResultMsg.ResultMsg.resultData!=null){
                        var TreeData = TransformToTreeFormat(data,
                        "id", "parentMenuId", "ChildLists");
	                    var ParentTree = new Array();
	                    var ParentData = new Object;
	                    ParentData.id = "0";
	                    ParentData.parentMenuId = parentMenuId;
	                    ParentData.menuName = "栏目列表";
	                    ParentData.ChildLists = TreeData;
	                    ParentTree.push(ParentData);
	                    var jgSource = new kendo.data.HierarchicalDataSource({
	                        data: ParentTree,
	                        schema: {
	                            model: {
	                            	children: "ChildLists"
	                            }
	                        }
	                    });
	                    selectSource = new kendo.data.HierarchicalDataSource({
	                        data: ParentTree,
	                        schema: {
	                            model: {
	                                children: "ChildLists"
	                            }
	                        }
	                    });
	                    itemTree.setDataSource(jgSource);
	                    itemTree.expand(".k-item");
	                    itemTree.expand(".k-item");
	                    itemTree.expand(".k-item");
	                    itemTree.expand(".k-item");
                    //}
                },
                Error: function (ResultMsg, Parameter) {
                	DetailAlertDialog({
                        Title: "出错信息",
                        Message: "加载栏目失败！",
                        DetailMessage: GetErrorMsg(ResultMsg),
                        Icon: "k-ext-error"
                    });
                }
            });
        }
        
         //成功回调函数
        function deleteSuccess() {
	         dataSource.read();
        }

        //失败回调函数
        function deleteError(result) {
        	AlertDialog({
                Title: "提示信息",
                Message: "删除失败！",
                Icon: "k-ext-error"
            });
        }
        //展开机构树
        function ExpandItemTree() {
            itemTree.expand(".k-item");
        }

        //收缩机构树
        function CollapseItemTree() {
            itemTree.collapse(".k-item");
        }
        
         //刷新按钮
        $("#TreeRefresh").click(function () {
        	dataSource.read();
            LoadItemTree();
        });

        //展开按钮
        $("#TreeExpand").click(function () {
            ExpandItemTree();
        });

        //收缩按钮
        $("#TreeCollapse").click(function () {
            CollapseItemTree();
        });
        //添加用户按钮
        $("#adduser").click(function () {
           if(menuId!=null&&menuId == 0){
           	alert("请选择栏目后在进行添加用户的操作！");
           }else{
           openwindow("${ctx}/pages/menu/user_tree.jsp?menuId="+menuId,350, 450, "", "div");
           }
        });
        //删除用户按钮
        $("#deluser").click(function () {
			var chbox = document.getElementsByName("chbox");
			var delId = "";
			for(var i = 0;i<chbox.length;i++){
				if(chbox[i].checked == true){
					delId += chbox[i].id+",";
				}
			}
			if(delId == "" || delId == null || delId == undefined){
				alert("请选择要删除的数据!");
			}else{
				AjaxDoPost(path+"/cms/menuUser/delete.json","models=" + JSON.stringify(delId),false,deleteSuccess,deleteError);
			}
        });
    </script>
</body>
</html>