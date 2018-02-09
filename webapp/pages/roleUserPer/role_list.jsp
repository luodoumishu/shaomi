<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>角色管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>
    <script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
	<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
    
</head>
<body>
    <!-- 界面 -->
    <div id="MainLayout">
        <div id="Right">
            <div id="grid"></div>
        </div>
    </div>

    <!-- 脚本 -->
    <script type="text/javascript">
    
          //树对象
          var ycjyjgTree = null;
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
    
        $(document).ready(function () {
        	//创建工具栏按钮
          	var ToolbarHtml = new CreateToolBarButton({
                  GridId: "grid",
                  Url: "${ctx}/pages/roleUserPer/role_edit.jsp",
                  Add: true,
                  Edit: true,
                  Del: true
            });
        	
			//数据源
			dataSource = CreateGridDataSource({
			  Transport: {
			       ReadUrl: "${ctx}/zrole/list.json",
			       ReadDataFunc: function () {
			     	  queryObj = new Object;
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
				Height: $(window).height()*0.9,         
				Editable:"popup",
				detailInit:detailInit,
				ColumnMenu: false,    
            	Filterable: false,
				Columns: [ 
				     { field: "name",width: "25%",title: "角色名称"},
				     { field: "isValid",width: "25%",title: "使用状态",values: [{ text: "启用", value: 1 },
                                                                        { text: "禁用", value: 0 }
                                                                       ]},
				     { field: "users",width: "25%",title: "具有权限的用户人数",template: "#=users.length#人"},
				     { field: "pri",width: "25%",title: "排序"}                                                 
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
            	AjaxDoPost("${ctx}/zrole/delete.json","models=" + JSON.stringify(data),true,deleteSuccess, deleteError);
            }
        }
        
         //删除成功回调函数
        function deleteSuccess(result) {
            if (result.resultCode === 0) {
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
        
        function detailInit(e) {
        	var role = e;
		    $("<div/>").appendTo(e.detailCell).kendoGrid({
		        dataSource: {
		        	data: e.data.users,
                    schema: {
                        model: {
                            fields: {
                                name: { type: "string" },
                                pri: { type: "number" }
                            }
                        }
                    }
		        },
		        scrollable: false,
		        sortable: true,
		        editable: {
		        	update: false,
    				destroy: true
  				},
		        remove: function(e) {
		        	if (!confirm("确定移除"+e.model.name+"用户吗?")) {
       					this.cancelChanges();
    				}else{
						$.ajax({
							url : "${ctx}/zrole/deleteuser.json?userId="+e.model.id+"&roleId="+role.data.id,
							type : "POST",
							success : function(result) {
								if(result!=null && result.resultCode === 0){
									alert("移除成功");
									grid.dataSource.read();
								}else{
									alert("移除失败");
								}
							},
							error : function() {
								alert("系统异常");
							}
						});
    				}
  				},
		        columns: [
		        	{ command: ["destroy"], title: "&nbsp;",width: "10%" },
                    { field: "name",width: "30%",title: "具有权限的用户名称"},
                    { field: "account",width: "30%",title: "具有权限的用户帐号"},
                    { field: "pri",width: "30%",title: "排序"}
                ]
		    });
		}
    </script>
</body>
</html>