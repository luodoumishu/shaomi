<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>栏目管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/comm/kendo_css.jsp"%>
<%@ include file="/comm/kendo_js.jsp"%>
<script type="text/javascript">
     var path = "<%=path%>";
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
</script>
</head>
<body>
	<!-- 界面 -->
	<div id="MainLayout" style="height:598px;overflow: hidden;">
		<div id="Right">
			<div id="" style="background-color: #F3F3F4;border: #dbdbde solid 1px;">
            <table >
	              <tr>
	                   <td>
	                       	 关键字：<input id="keys" name="keys" style="width: 200px" class="k-textbox" /> &nbsp;&nbsp; &nbsp;&nbsp;
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
</body>
<!-- 脚本 -->
<script type="text/javascript">
	var contentHeight = $(self.parent.frames["mainContentIframe"].document)
			.height() - 5;
	$("#MainLayout").height(contentHeight);
	$("#grid").height(contentHeight);
	dataSource = CreateGridDataSource({
 	    Transport: {
           ReadUrl: path+"/cms/menuTransmit/list.json",
           ReadDataFunc: function () {
         	    queryObj = new Object;
         	   	queryObj.keys = $("#keys").val();
             	return queryObj;
           }
      }
	});
	//快速查询
 	$("#qsearch").click(qSearch);
	
 	//快速查询
 	function qSearch() {
 		var dataSource = grid.dataSource;
    	dataSource.page(1);
 	}
 	$("#clear").click(function(){
 		$("#keys").val("");
    	var dataSource = grid.dataSource;
	    dataSource.page(1);
    });
	$(document).ready(function() {
		grid = CreateGrid({
			ID : "grid",
			DataSource : dataSource,
			Height : $(window).height() * 0.95,
			Editable : "popup",
			ColumnMenu : false,
			Filterable : false,
			Columns : [ 
			 	{width : "40%",title : "栏目名称",template: function(dataItem) {
			 		return "【 "+dataItem.source_menu_parent_name+" 】"+dataItem.source_menu_name;
			 	}
			 	},
			 	{width : "40%",title : "推送记录",template: function(dataItem) {
			 		return "【 "+dataItem.target_org_name+" 】"+dataItem.target_menu_name;
			 	}}
			 	,{width: "20%",title: "操作", 
					  template: function(dataItem){
						  var source_menu_id = dataItem.source_menu_id;
						  var target_menu_id = dataItem.target_menu_id;
						  var target_org_id = dataItem.target_org_id;
						  var html = "<button class='k-button' onclick='cancleTarget(\""+source_menu_id+"\",\""+target_menu_id+"\",\""+target_org_id+"\")' style='width:10px;'>取消推送</button>";
						  return html;
					  }
				  }
			]
		});
	});
	function cancleTarget(source_menu_id,target_menu_id,target_org_id){
		$.ajax({
	        url: path + "/cms/menuTransmit/cancleTarget.json",
	        type: "POST",
	        async: false,
	        dataType: "json",
	        data: {"source_menu_id":source_menu_id,"target_menu_id":target_menu_id,"target_org_id":target_org_id},
	        success: function (result) {
	        	if(result.resultCode == 0){
	        		AlertDialog({
						Title : "提示信息",
						Message : "取消推送成功！",
						Icon : "k-ext-information"
					});
	        		grid.dataSource.read();
	        	}else{
	        		AlertDialog({
						Title : "提示信息",
						Message : result.resultMsg,
						Icon : "k-ext-information"
					});
	        	}
	        },
	        error: function (result) {
	        	AlertDialog({
					Title : "提示信息",
					Message : "取消推送失败！",
					Icon : "k-ext-error"
				});
	            return;
	        }
	    });
	}
</script>
</html>