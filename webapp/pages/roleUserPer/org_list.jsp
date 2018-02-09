<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>单位管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>    
</head>
<body>
    <!-- 界面 -->
    <div id="MainLayout" style="height: 99%;">
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
            <div id="itemTree"></div>
        </div>
           <div id="Right">
               <div id="grid"></div>
           </div>
       </div>

    <!-- 脚本 -->
    <script type="text/javascript">
    
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
		  //编辑的类型,默认为新增
		  var EditType = "add";
		  
		  //下拉数据源
		  var selectSource = null;
		  //指标名称
		  var kpiName = null;
		  
		  var parentId = "0";//初始值
		  
		  var parentName = "单位列表";
		  
        $(document).ready(function () {
        	//页面分隔
        	$("#MainLayout").kendoSplitter({
                panes: [
                    { collapsible: true, size: '20%' },
                    { collapsible: true, size: "80%" },
                ]
            });
        	//创建工具栏按钮
          	var ToolbarHtml = new CreateToolBarButton({
                  GridId: "grid",
                  Url: "${ctx}/pages/roleUserPer/org_edit.jsp",
                  Add: true,
                  Edit: true,
                  Del: true
              });
        	
          //数据源
       	  dataSource = CreateGridDataSource({
       	    Transport: {
                 ReadUrl: "${ctx}/Zorganize/list.json",
                 ReadDataFunc: function () {
               	    queryObj = new Object;
               	    queryObj.parentId=parentId;
               	 	queryObj.name=kpiName;
                   	return queryObj;
                 }
            }
       	  });
        	      
            //数据窗口
            grid = CreateGrid({
            ID: "grid",
            DataSource: dataSource,
            ToolBarTemplate: ToolbarHtml.Str,
            Height: "99%",
            Editable:"popup",
            ColumnMenu: false,    
            Filterable: false,    
            Columns: [ 
                      { field: "name",width: "30%",title: "单位名称"},
                      { field: "parentName",width: "30%",title: "上级单位名称"},
                      { field: "pyCode",width: "30%",title: "单位代码"},
                      { field: "pri",width: "10%",title: "排序"},
                 ]
             });
            itemTree = $("#itemTree").kendoTreeView({
			                 dataTextField: "name",
			                 select: function(e) {
			                 	parentId = this.dataItem(e.node).id;
			                 	parentName = this.dataItem(e.node).name;
			                 	grid.dataSource.read();
			                   }
			             }).data("kendoTreeView");
            LoadItemTree();
        });
        
      //加载渔船检验机构树
        function LoadItemTree() {
            JsonPostForWaiting({
                Url: "${ctx}/Zorganize/listOrgs.json",
                WaittingText: "正在加载单位...",
                AimDiv: "#LeftLayout",
                Success: function (ResultMsg, Parameter) {
                        var data="";
                        if(ResultMsg.ResultMsg.resultData!=null){
                           data = ResultMsg.ResultMsg.resultData;
                        }
                    //if(ResultMsg.ResultMsg.resultData!=null){
                        var TreeData = TransformToTreeFormat(data,
                        "id", "parentId", "ChildLists");

	                    var ParentTree = new Array();
	                    var ParentData = new Object;
	                    ParentData.id = "0";
	                    ParentData.parentId = 0;
	                    ParentData.name = "单位列表";
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
// 	                    itemTree.expand(".k-item");
// 	                    itemTree.expand(".k-item");
                    //}
                },
                Error: function (ResultMsg, Parameter) {
                	DetailAlertDialog({
                        Title: "出错信息",
                        Message: "加载单位失败！",
                        DetailMessage: GetErrorMsg(ResultMsg),
                        Icon: "k-ext-error"
                    });
                }
            });
        }
      //新增初始化
        onAdd = function () {
        	Item = new Object;
        	//编辑的类型：新增
     		EditType="add";
            //可以在这里进行新增页面的初始化值设置
            searchMaxSort("${ctx}/Zorganize/searchMaxSort.json");
            return true;
         }
        var searchMaxSort = function(url){
    		$.ajax({
    			url : url,
    			type : "POST",
    			success : function(result){
    				if(result.resultCode === 0){
    					Item.pri = result.resultData;	
    				}
    			},
    			error : function(result){
    				
    			}
    		});
    	};
      
        //修改初始化
    	onUpdate = function () {
            var row = grid.select();
            selectRowIndex = row.index();
            Item = grid.dataItem(row);
            //编辑的类型：修改
     		EditType="edit";
            if (Item == null) {
            	AlertDialog({
                    Title: "提示信息",
                    Message: "请选择您要编辑的数据！",
                    Icon: "k-ext-warning"
                });
                return false;
            }
            else {
                return true;
            }
        }
        
        //删除初始化
        onDelete = function () {
            var row = grid.select();
            var data = grid.dataItem(row);
            if (data == null) {
            	AlertDialog({
                    Title: "提示信息",
                    Message: "请选择您要删除的数据！",
                    Icon: "k-ext-warning"
                });
                return false;
            }
            else {
                return true;
            }
        }
        
        del = function () {
            var row = grid.select();
            var datas = new Array();
            for (var i = 0; i < row.length; i++) {
                var data = grid.dataItem(row[i]);
                datas.push(data);
            }
            if (typeof (data) != undefined) {
            	AjaxDoPost("${ctx}/Zorganize/delete.json","models=" + JSON.stringify(data),true,deleteSuccess, deleteError);
            }
        }
        
         //删除成功回调函数
        function deleteSuccess(result) {
            if (result.resultCode === 0) {
	            if(result.resultMsg==""){
		            var row = grid.select();
	                for (var i = 0; i < row.length; i++) {
	                    grid.removeRow(row[i]);
	                }
	                grid.dataSource.read();
	                LoadItemTree();
	                AlertDialog({
	                    Title: "提示信息",
	                    Message: "删除成功！",
	                    Icon: "k-ext-information"
	                });
	           }else{
	                alert(result.resultMsg);
	           }
                
            }
            else {
                alert(result.resultMsg);
            }
        }

        //删除失败回调函数
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
        	grid.dataSource.read();
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
    </script>
</body>
</html>