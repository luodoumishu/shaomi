xjj.nameSpace("xjj.cms.vote");

xjj.cms.vote = (function(){
 	
	//创建工具栏按钮
	var ToolbarHtml = new CreateToolBarButton({
        GridId: "grid",
        Url: path+"/pages/vote/vote_item_edit.jsp",
        Add: true,
        Edit: true,
        Del: true
 	});
	
	//数据源
	dataSource = CreateGridDataSource({
 	    Transport: {
           ReadUrl: path+"/cms/vote/item/list.json",
           ReadDataFunc: function () {
         	    queryObj = new Object;
             	return queryObj;
           }
      }
 	  });
	
	var init_listPage = function(){
		// 页面分隔 
			$("#MainLayout").kendoSplitter({
		  		panes: [
		  		        {collapsible: true, size: '20%' },
		                { collapsible: true, size: "80%" }
		                ]
		      });
	  // 列表表格
	        grid = CreateGrid({
		        ID: "grid",
		        DataSource: dataSource,
		        ToolBarTemplate: ToolbarHtml.Str,
		        Height: $(window).height()*1.0,
		        Editable:"popup",
		        ColumnMenu: false,    
		        Filterable: false,    
		        Columns: [ 
		                  { field: "item_name",width: "10%",title: "投票项名称"},
		                  { field: "item_type",width: "10%",title: "投票项类型",values: [{text: "单选",value: 0 
	           		   		},{text: "多选", value: 1},{text: "问答", value: 2}]},
		                  { field: "is_valid",width: "10%",title: "是否有效",values: [{text: "有效",value: 0 
	           		   		},{text: "无效", value: 1}]},
		                  { field: "sort",width: "10%",title: "排序"}
		       ]
	         });
	};
	/******编辑页面js******/
	var validator = null;
	//验证
	var  checkForm =  function () {
	      if (validator.validate()) {
	          return true;
	      }else{
	          return false;
	      }
	};
	  //初始化表单
	  var initForm = function (){
	      //数据模型绑定,MVVM
	      item = kendo.observable(item);
	      //数据模型绑定
	      kendo.bind($("#form-container-edit"), item);
		  //新建对象绑定模型
		 if (editType == "add") {//VIEW_MODEL层的数据初始化
			 item.sort = 0;
			 item.isValid = 0;
			 item.item_type = 0;
		 }
		 if (editType == "edit") {//VIEW_MODEL层的数据初始化

		 }
	  };
	  //监听退出按钮
	  $("#cancel").click(function(){
		  window.location.href= path+"/pages/vote/vote_item_list.jsp";
	      closewindow("div");
	  });
	  //监听保存按钮
	  $("#submit_confirm").click(function(){
		  if (checkForm()){
			  var options = {
				         type:'post',
				         success:function(data){
//				        	 var jsonDate = eval( "(" + data + ")" );
//				        	 alert(data.jsonResult.resultCode);
				        	 if (data.jsonResult.resultCode === 0){
				      	          closewindow("div");
				      	          window.location.href= path+"/pages/vote/vote_item_list.jsp";
				      	    }else{
				      	          alert(decodeURIComponent(data.jsonResult.resultMsg,"utf-8"));
				      	      }
				         },
				         error:function(){
				        	 alert("保存失败");
				         }
				      };
			  if(options != null){
				  $("#dataform").ajaxSubmit(options);
			  }
		  }
	   });
	  var init_editPage = function(){
			//初始化表单
			initForm();
			//初始化附件
		    //kendoui校验器初始化
		    validator = $("#dataform").kendoValidator().data("kendoValidator");
		  	//标签页
//			 $("#menuInfoTab").kendoTabStrip({
//		          activate: onTabActivate
//		      });
			 $("#sort").kendoNumericTextBox({
					decimals : 0,//小数位数，即VIEW_MODEL层的精度
					value: item.sort,//初始值，VIEW层
					min : 0,//最小值
					format: "0",
					step : 1,//每次点击增减按钮的浮动值
		     	});
	  };
	  // 删除成功回调函数
		 var deleteSuccess  = function(result) {
		      if (result.resultCode === 0) {
		          if(result.resultMsg==""){
		  	          var row = grid.select();
		              for (var i = 0; i < row.length; i++) {
		                  grid.removeRow(row[i]);
		              }
		              grid.dataSource.read();
		              loadItemTree();
		         }else{
		              alert(result.resultMsg);
		         }
		      }else {
		          alert(result.resultMsg);
		      }
		  };
		  // 删除失败回调函数
		  var deleteError = function (result) {
		  	AlertDialog({
		          Title: "提示信息",
		          Message: "删除失败！",
		          Icon: "k-ext-error"
		      });
		  };
	return{
		  init_listPage : init_listPage,
		  init_editPage : init_editPage,
		  deleteSuccess : deleteSuccess,
		  deleteError : deleteError
	};
})();

//Tab单项激活事件
function OnTabActivate(e) {
    var TabText = $(e.item).find("> .k-link").text();
    RefreshTabContent(TabText);

}
//刷新Tab页内容
function RefreshTabContent(TabText) {
    
}

//新增初始化
var onAdd = function () {
 	item = new Object;
 	// 编辑的类型：新增
 	editType="add";
     // 可以在这里进行新增页面的初始化值设置
    
     return true;
};

 // 修改初始化
var	onUpdate = function () {
     var row = grid.select();
     selectRowIndex = row.index();
     item = grid.dataItem(row);
     // 编辑的类型：修改
 		editType="edit";
     if (item == null) {
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
 };

 // 删除初始化
var  onDelete = function () {
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
 };
 var del = function () {
     var row = grid.select();
     var datas = new Array();
     for (var i = 0; i < row.length; i++) {
         var data = grid.dataItem(row[i]);
         datas.push(data);
     }
     if (typeof (data) != undefined) {
     	AjaxDoPost(path+"/cms/vote/item/delete.json","models=" + JSON.stringify(data),true,xjj.cms.vote.deleteSuccess,xjj.cms.vote.deleteError);
     }
 };