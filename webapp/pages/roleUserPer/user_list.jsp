<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
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
        	<div id="" style="background-color: #F3F3F4;border: #dbdbde solid 1px;">
            	<table >
	              <tr>
	                   <td>
	                        &nbsp;姓名：<input id="name" name="name" style="width: 120px" class="k-textbox" />
	                        &nbsp;部门：
	                   </td>
	                   <td>
	                        <div id="orgId" style="width: 180px" ></div>
	                   </td>
	                   <td>
	                        &nbsp;<input id="qsearch" type="button" class="k-button" value=" 查 询 " />
	               		 &nbsp;<input id="clear" type="button" class="k-button" value=" 重 置 " />
	                   </td>
	               </tr>
	           </table>
          	</div>
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
		  
		  var SelectCorp = null;
		  //下拉数据源
		  var selectSource = null;
		  //指标名称
		  var kpiName = null;
    
        $(document).ready(function () {
        	
        	//创建工具栏按钮
          	var ToolbarHtml = new CreateToolBarButton({
                  GridId: "grid",
                  Url: "${ctx}/pages/roleUserPer/user_edit.jsp",
                  Add: true,
                  Edit: true,
                  Del: true
              });
        	
          //数据源
			dataSource = CreateGridDataSource({
			  Transport: {
			       ReadUrl: "${ctx}/zuser/list.json",
			       ReadDataFunc: function () {
			      	 	queryObj = new Object;
			       	 	queryObj.name=$("#name").val();
			        	queryObj.orgId=$("#orgId").val();
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
            ColumnMenu: false,    
            Filterable: false,    
            Columns: [ 
                      { field: "account",width: "12%",title: "登录帐号"},
                      { field: "name",width: "12%",title: "姓名"},
                      { field: "orgs.id",width: "20%",title: "所属单位",template:"#if(orgs.length>0){#<ul>#for(var i=0;orgs.length>i;i++){#<li>#=i+1#.#=orgs[i].name#</li>#}#</ul>#}else{#暂无配置单位#}#"},
                      { field: "roles.id",width: "20%",title: "具有的角色",template: "#if(roles.length>0){#<ul>#for(var i=0;roles.length>i;i++){#<li>#=i+1#.#=roles[i].name#</li>#}#</ul>#}else{#暂无配置角色#}#"},
                      { field: "状态",width: "12%",template:"#if(status == '0'){#正常#}else{#已冻结#}#"},
                      { field: "pri",width: "12%",title: "排序"},
                      { field: "操作",width: "12%",template:"#if(status == '0'){#<a class='k-button k-button-icontext k-grid-详情' href=\"javascript:thaw('#=id#', '#=name#', '-1')\">冻结</a>#}else{#<a class='k-button k-button-icontext k-grid-详情' href=\"javascript:thaw('#=id#','#=name#', '0')\">解冻</a>#}#"},
                 ]
            });
            LoadItemTree();
            SelectCorp = CreateDropDownTreeView({
		       ID: "orgId",
		       AutoBind: true,
		       DataSource: selectSource,
		       TextField: "name",
		       OnSelect: function (e) {
		           var dataItem = this.dataItem(e.node);
		           $("#orgId").val(dataItem.id);
		       }
		    });
		    SelectCorp.selectItem("全部");
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
            	AjaxDoPost("${ctx}/zuser/delete.json","models=" + JSON.stringify(data),true,deleteSuccess, deleteError);
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
        
        //异步请求的方法
		function doAjax(url,type){
			var ob = '[]';
			$.ajax({
				url : url,
				type : type,
				cache : false,
				async : false,
				error : function(textStatus, errorThrown){
					alert("系统ajax交互错误"+textStatus);
					return;
				},
				success : function(data){
					ob = data;
				}
			});
			return ob;
		}
        
        function LoadItemTree() {
			var ResultMsg = doAjax("${ctx}/Zorganize/listOrgs.json","POST");
            var TreeData = TransformToTreeFormat(ResultMsg.resultData,
                "id", "parentId", "ChildLists");
            var ParentTree = new Array();
            var ParentData = new Object;
            ParentData.id = "";
            ParentData.parentId = 0;
            ParentData.name = "全部";
            ParentData.ChildLists = TreeData;
            ParentTree.push(ParentData);

            selectSource = new kendo.data.HierarchicalDataSource({
                data: ParentTree,
                schema: {
                    model: {
                        children: "ChildLists"
                    }
                }
            });
        }
        
        //重置
		$("#clear").click(function(){
			$("#name").val("");
			$("#orgId").val("");
			SelectCorp.selectItem("全部");
		});
		
		//快速查询
		$("#qsearch").click(qSearch);
		
		//快速查询
		function qSearch() {
			var dataSource = grid.dataSource;
		    dataSource.page(1);
		}
		
        function thaw(id, name, status){
        	var str =  (status == "0" ? "解冻" : "冻结"); 
            $.when(ConfirmDialog({
                Title: "提示信息",
                Message: "确定要对"+name+"的账户进行"+str+"操作！",
                Icon: "k-ext-question"
            })).done(function (response) {
                if (response.button == "确定"){
		        	$.ajax({
						url : "${ctx}/zuser/thaw.json",
						type : "POST",
						data : {"id":id, "status" :status},
						cache : false,
						async : false,
						error : function(textStatus, errorThrown){
				            AlertDialog({
				                Title: "提示信息",
				                Message: str+name+"状态失败，请联系系统维护人员！",
				                Icon: "k-ext-error"
				            });
						},
						success : function(result){
							 if (result.resultCode == 0) {
		 			            AlertDialog({
		 			                Title: "提示信息",
		 			                Message: str+name+"状态成功！",
		 			                Icon: "k-ext-information"
		 			            });
		 			           grid.dataSource.page(1);
		 			        }else {
		 			            AlertDialog({
		 			                Title: "提示信息",
		 			                Message: "初始化"+name+"密码失败，请联系系统维护人员！",
		 			                Icon: "k-ext-error"
		 			            });
		 			        }
						}
					});
                }else{
                    return;
                }
            });
        }
        
    </script>
</body>
</html>