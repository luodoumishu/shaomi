<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>频道管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="${ctx}/comm/js/commonJs.js"></script>
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>    
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
            <div id="itemTree"></div>
        </div>
           <div id="Right">
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
		  //编辑的类型,默认为新增
		  var EditType = "add";
		  
		  //下拉数据源
		  var selectSource = null;
		  
		  var parentChanneid = "0";//初始值
    
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
                  Url: "${ctx}/pages/channel/channel_edit.jsp",
                  Add: true,
                  Edit: true,
                  Del: true
              });
        	
          //数据源
       	  dataSource = CreateGridDataSource({
       	    Transport: {
                 ReadUrl: "${ctx}/CmsChannel/list.json",
                 ReadDataFunc: function () {
               	    queryObj = new Object;
               	  	queryObj.parentChanneid=parentChanneid;
                   	return queryObj;
                 }
            }
       	  });
        	      
            //数据窗口
            grid = CreateGrid({
            ID: "grid",
            DataSource: dataSource,
            ToolBarTemplate: ToolbarHtml.Str,
            Height: $(window).height()*0.95,
            Editable:"popup",
            ColumnMenu: false,    
            Filterable: false,    
            Columns: [ 
                      { field: "channeName",width: "15%",title: "频道名称"},
                      { field: "channeCode",width: "15%",title: "频道代码"},
                      { field: "relevanceType",width: "15%", title: "类型",values: [
                                             { text: "栏目类型", value: 0 },
                                             { text: "超链接类型", value: 1 },
                                             { text: "投票类型", value: 2 }
                       ]},
                      { field: "showMode",width: "15%",title: "显示方式",values: [
                                             { text: "标题", value: 0 },
                                             { text: "内容", value: 1 }
					 ]},
					  { field: "sortno",width: "5%",title: "排序"},
                      {width: "35%",title: "操作", /* command:{
                      				text: "批量导入栏目", 
           							click: batchOrgs 
                      } */
                      template: function(dataItem){
                	  	  var type = dataItem.relevanceType;
	                	  var html = "";
	                	  if(type!=null){
	                		  if(type == "0" || type == "2"){
	                		  	html += "<button class='k-button' onclick='batchOrgs();'>批量导入栏目</button>";
	                		  }
	                		  if(type == "1"){
	                		  	html += "<button class='k-button' onclick='batchLinks();'>批量导入链接</button>";
	                		  }
	                	  }
	                	  return html;
                  	  }
                      }
                 ]
             });
            itemTree = $("#itemTree").kendoTreeView({
			                 dataTextField: "channeName",
			                 select: function(e) {
			                 	parentChanneid = this.dataItem(e.node).id;
			                	 grid.dataSource.read();
			                   }
			             }).data("kendoTreeView");
            LoadItemTree();
        });
        
        function LoadItemTree() {
            JsonPostForWaiting({
                Url: "${ctx}/CmsChannel/listOrgs.json",
                WaittingText: "正在加载频道...",
                AimDiv: "#LeftLayout",
                Success: function (ResultMsg, Parameter) {
                        var data="";
                        if(ResultMsg.ResultMsg.resultData!=null){
                           data = ResultMsg.ResultMsg.resultData;
                        }
                    //if(ResultMsg.ResultMsg.resultData!=null){
                        var TreeData = TransformToTreeFormat(data,
                        "id", "parentChanneid", "ChildLists");

	                    var ParentTree = new Array();
	                    var ParentData = new Object;
	                    ParentData.id = "0";
	                    ParentData.parentChanneid = 0;
	                    ParentData.channeName = "频道列表";
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
                        Message: "加载频道失败！",
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
            return true;
         }
      
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
        
        //批量删除初始化
        onBatchDelete = function () {
            var chb= document.getElementsByName("chbox");
            var count = 0;
           	for (var i = 0; i < chb.length; i++) {
                if(chb[i].checked==true){
                  	count++;		
                }
            }
            if(count == 0){
            		AlertDialog({
                    Title: "提示信息",
                    Message: "请选择您要删除的数据！",
                    Icon: "k-ext-warning"
                });
                return false;
            }else{
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
            	AjaxDoPost("${ctx}/CmsChannel/delete.json","models=" + JSON.stringify(data),true,deleteSuccess, deleteError);
            }
        }
        
        batchDel = function () {
            var datas = new Array();
            var chb= document.getElementsByName("chbox");
            for (var i = 0; i < chb.length; i++) {
                if(chb[i].checked==true){
                  datas.push(chb[i].value); 
                }
            }
            if (typeof (datas) != undefined) {
            	AjaxDoPost("${ctx}/Demo/batchDelete.json","models=" + JSON.stringify(datas),true,deleteSuccess, deleteError);
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
          //重置
		$("#clear").click(function(){
			$("#name").val("");
			SelectCorp.selectItem("全部");
		});
		//批量导入栏目
		function batchOrgs() {
		var row = grid.select();
        selectRowIndex = row.index();
        Item = grid.dataItem(row);
            location.href="${ctx}/pages/channel/channel_batchMenu.jsp?id="+Item.id;
        }
        
        //批量导入链接
		function batchLinks() {
		var row = grid.select();
        selectRowIndex = row.index();
        Item = grid.dataItem(row);
            location.href="${ctx}/pages/link/link_import.jsp?channelId="+Item.id;
        }
        //快速查询
		$("#qsearch").click(qSearch);
		
		//快速查询
		function qSearch() {
			var dataSource = grid.dataSource;
		    dataSource.page(1);
		}
    </script>
</body>
</html>