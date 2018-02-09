xjj.nameSpace("xjj.cms.effect");
// 创建工具栏按钮
xjj.cms.effect = (function() {
	var ToolbarHtml = new CreateToolBarButton({
		GridId : "grid",
		Url : path + "/pages/effect/effect_edit.jsp",
		Add : true,
		Edit : true,
		Del : true
	});
	// 数据源
	dataSource = CreateGridDataSource({
		Transport : {
			ReadUrl : path + "/cms/effect/list.json",
			ReadDataFunc : function() {
				queryObj = new Object;
				return queryObj;
			}
		}
	});
	
	var init_listPage = function() {
		// 列表表格
		grid = CreateGrid({
			ID : "grid",
			DataSource : dataSource,
			ToolBarTemplate : ToolbarHtml.Str,
			Editable : "popup",
			ColumnMenu : false,
			Filterable : false,
			Columns : [ {
				field : "name",
				title : "特效名称"
			}, {
				field : "type",
				width : "10%",
				values: [
				         { text: "飘窗", value: 0 },
				         { text: "自定义", value: 1 }]
			}, {
				field : "code",
				width : "10%",
				title : "特效代码"
			}, {
				field : "isShow",
				width : "10%",
				title : "是否显示",
				values: [
				         { text: "显示", value: 0 },
				         { text: "隐藏", value: 1 }]
			}, {
				field : "sortNo",
				width : "10%",
				title : "排序号"
			}
			]
		});
	}
	/** ****编辑页面js***** */
	var validator = null;
	// 验证
	var checkForm = function() {
		if (validator.validate()) {
			return true;
		} else {
			return false;
		}
	}
	
	// 初始化表单
	var initForm = function() {
		// 数据模型绑定,MVVM
		item = kendo.observable(item);
		// 数据模型绑定
		kendo.bind($("#form-container-edit"), item);
		
		if("edit" == editType){
			var imgUrl = item.imgUrl;
			var type = item.type;
			if(type == 0){
				if( null != imgUrl && "undefined" != imgUrl){
					showImg(imgUrl); //显示飘窗图片 
				}
			}else{
				var $pc = $("tr[pc='true']");//飘窗项
				var $htmlCode = $("#htmlCode_code");//自定义html代码
				$pc.hide();
				$htmlCode.show();
			}
			
		}
		 //排序号
		 $("#sortNo").kendoNumericTextBox({
				decimals : 0,//小数位数，即VIEW_MODEL层的精度
				value: item.sortNo || 0 ,//初始值，VIEW层
				min : 0,//最小值
				format: "0",
				step : 1//每次点击增减按钮的浮动值
	     	});
		 //移动步长
		 $("#step").kendoNumericTextBox({
				decimals : 0,//小数位数，即VIEW_MODEL层的精度
				value: item.step || 1,//初始值，VIEW层
				min : 0,//最小值
				format: "0",
				step : 1//每次点击增减按钮的浮动值
	     	});
		 //移动间隔
		 $("#delay").kendoNumericTextBox({
				decimals : 0,//小数位数，即VIEW_MODEL层的精度
				value: item.delay || 20,//初始值，VIEW层
				min : 0,//最小值
				format: "0",
				step : 10//每次点击增减按钮的浮动值
	     	});
		 //X轴偏移量
		 $("#x_offset").kendoNumericTextBox({
				decimals : 0,//小数位数，即VIEW_MODEL层的精度
				value: item.xOffset || 150,//初始值，VIEW层
				min : 0,//最小值
				format: "0",
				step : 10//每次点击增减按钮的浮动值
	     	});
		 //Y轴偏移量
		 $("#y_offset").kendoNumericTextBox({
				decimals : 0,//小数位数，即VIEW_MODEL层的精度
				value: item.yOffset||300,//初始值，VIEW层
				min : 0,//最小值
				format: "0",
				step : 10//每次点击增减按钮的浮动值
	     	});
    
	}

	// 监听退出按钮
	$("#cancel").click(function() {
		closewindow("div");
		window.location.href= path+"/pages/effect/effect_list.jsp";
	});
	// 监听保存按钮
	$("#submit_confirm").click(
			function() {
				if (checkForm()) {
					var name = item.name;
					if("" == name || undefined == name || "0" == name){
						alert("请输入特效名称");
						return ;
					}
					 var options = "";
						if (editType == "edit") {
							 options ={
							         type:'POST',
							         success:function(data){
							        	 editSuccess(null);
							         },
							         error:function(){
							        	 editError(null);
							         }
							      };
						} else if (editType == "add") {
							options ={
							         type:'POST', 
							         success:function(data){
							        	 addSuccess(null);
							         },
							         error:function(){
							        	 addError(null);
							         }
							      };
						}
						//提交表单
						$("#dataform").ajaxSubmit(options);
				}
			});

	// 新增组成功回调
	var addSuccess = function(result) {
		closewindow("div");
		window.location.href= path+"/pages/effect/effect_list.jsp";
	}

	// 新增组失败回调
	var addError = function(result) {
		AlertDialog({
			Title : "提示信息",
			Message : "添加失败！",
			Icon : "k-ext-error"
		});
	}

	// 修改成功回调函数
	var editSuccess = function(result) {
		closewindow("div");
		window.location.href= path+"/pages/effect/effect_list.jsp";
	}

	// 修改失败回调函数
	var editError = function(result) {
		AlertDialog({
			Title : "提示信息",
			Message : "修改失败！",
			Icon : "k-ext-error"
		});
	}
	

	// 删除成功回调函数
	var deleteSuccess = function(result) {
		if (result.resultCode === 0) {
			if (result.resultMsg == "") {
				var row = grid.select();
				for ( var i = 0; i < row.length; i++) {
					grid.removeRow(row[i]);
				}
				grid.dataSource.read();
			} else {
				alert(result.resultMsg);
			}
		} else {
			alert(result.resultMsg);
		}
	}

	// 删除失败回调函数
	var deleteError = function(result) {
		AlertDialog({
			Title : "提示信息",
			Message : "删除失败！",
			Icon : "k-ext-error"
		});
	}
	var init_editPage = function() {
		// 初始化表单
		initForm();
		//kendoui校验器初始化
	    validator = $("#dataform").kendoValidator().data("kendoValidator");
	    //标签页
		 $("#menuInfoTab").kendoTabStrip({
	          activate: onTabActivate
	      });
	}
	
	var showImg = function (url){
		if("" != url  && "undefined" != url && null != url){
			var img = "<img height=\"210\" width=\"260\" src='"+url+"' border='0'  />";
			$("#show_pc_img_div").html(img);
		}
	}
	
	 //Tab单项激活事件
	 var onTabActivate = function (e){
	      var TabText = $(e.item).find("> .k-link").text();
	      refreshTabContent(TabText);
	  }
	  //刷新Tab页内容
	 var refreshTabContent = function(TabText){

	  }
	 
	/**
	 * 显示或隐藏飘窗项
	 */
	var showOrhide2pc = function(obj){
		var $pc = $("tr[pc='true']");//飘窗项
		var $htmlCode = $("#htmlCode_code");//自定义html代码
		if(obj.value==0){ //显示飘窗项
			$pc.show();
			$htmlCode.hide();
		}else{ //隐藏飘窗项，显示自定义
			$pc.hide();
			$htmlCode.show();
		}
	}
	return {
		init_listPage : init_listPage,
		init_editPage : init_editPage,
		showImg:showImg,
		deleteSuccess : deleteSuccess,
		deleteError : deleteError,
		showOrhide2pc:showOrhide2pc
	}

})();

// 新增初始化
var onAdd = function() {
	item = new Object;
	// 编辑的类型：新增
	editType = "add";
	// 可以在这里进行新增页面的初始化值设置
	return true;
}

// 修改初始化
var onUpdate = function() {
	var row = grid.select();
	selectRowIndex = row.index();
	item = grid.dataItem(row);
	// 编辑的类型：修改
	editType = "edit";
	if (item == null) {
		AlertDialog({
			Title : "提示信息",
			Message : "请选择您要编辑的数据！",
			Icon : "k-ext-warning"
		});
		return false;
	} else {
		return true;
	}
}///data/webfile/slga/effect
// 删除初始化
var onDelete = function() {
	var row = grid.select();
	var data = grid.dataItem(row);
	if (data == null) {
		AlertDialog({
			Title : "提示信息",
			Message : "请选择您要删除的数据！",
			Icon : "k-ext-warning"
		});
		return false;
	} else {
		return true;
	}
}

var del = function() {
	var row = grid.select();
	var datas = new Array();
	for ( var i = 0; i < row.length; i++) {
		var data = grid.dataItem(row[i]);
		datas.push(data);
	}
	if (typeof (data) != undefined) {
		AjaxDoPost(path + "/cms/effect/delete.json", "models="
				+ JSON.stringify(data.id), true, xjj.cms.effect.deleteSuccess,
				xjj.cms.effect.deleteError);
	}
}
