<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>联赛管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="${ctx}/comm/js/commonJs.js"></script>
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>    
</head>
<body scroll="no">
 <!-- 界面 -->
    <div id="MainLayout" style="height:598px;">
              <div id="grid"></div>
    </div>
       
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
		  
		  var parentChanneid = "0";//初始值
    
        $(document).ready(function () {
        	//创建工具栏按钮
          	var ToolbarHtml = new CreateToolBarButton({
                  GridId: "grid",
                  Url: "${ctx}/pages/win/league_info_edit.jsp",
                  Add: true,
                  Edit: true,
                  Del: true
              });
        	
          //数据源
       	  dataSource = CreateGridDataSource({
       	    Transport: {
                 ReadUrl: "${ctx}/win/LeagueInfo/list.json",
                 ReadDataFunc: function () {
               	    queryObj = new Object;
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
                      { field: "name",width: "45%",title: "联赛名称"},
                      { field: "country",width: "45%",title: "所在国家"},
					  { field: "sort",width: "10%",title: "排序"}
                 ]
             });
        });
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
        
        
         del = function () {
            var row = grid.select();
            var datas = new Array();
            for (var i = 0; i < row.length; i++) {
                var data = grid.dataItem(row[i]);
                datas.push(data);
            }
            if (typeof (data) != undefined) {
            	AjaxDoPost("${ctx}/win/LeagueInfo/delete.json","models=" + JSON.stringify(data),true,deleteSuccess, deleteError);
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
        
        //重置
		$("#clear").click(function(){
			$("#name").val("");
			SelectCorp.selectItem("全部");
		});
        
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