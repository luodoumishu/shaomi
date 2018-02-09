xjj.nameSpace("xjj.cms.menu");
//创建工具栏按钮
xjj.cms.menu = (function(){
  	var ToolbarHtml = new CreateToolBarButton({
            GridId: "grid",
            Url: path+"/pages/menu/menu_edit.jsp",
            Add: true,
            Edit: true,
            Del: true
     });
  //数据源
  	dataSource = CreateGridDataSource({
 	    Transport: {
           ReadUrl: path+"/cms/menu/list.json",
           ReadDataFunc: function () {
         	    queryObj = new Object;
         	    queryObj.parentMenuId = parentMenuId;
         	 	queryObj.menuName = menuName;
             	return queryObj;
           }
      }
 	  });
 // 加载栏目树
    var loadItemTree = function () {
        JsonPostForWaiting({
            Url: path+"/cms/menu/alltreeList.json",
            WaittingText: "正在加载栏目...",
            AimDiv: "#LeftLayout",
            Success: function (ResultMsg, Parameter) {
                    var data="";
                    if(ResultMsg.ResultMsg.resultData!=null){
                       data = ResultMsg.ResultMsg.resultData;
                    }
                    var TreeData = TransformToTreeFormat(data,
                    "id", "parentMenuId", "ChildLists");
                    var ParentTree = new Array();
                    var ParentData = new Object;
                    ParentData.id = "0";
                    ParentData.parentMenuId = "0";
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
//                    itemTree.expand(".k-item");
//                    itemTree.expand(".k-item");
            },
            Error: function (ResultMsg, Parameter) {
            	DetailAlertDialog({
                    Title: "出错信息",
                    Message: "加载栏目树失败！",
                    DetailMessage: GetErrorMsg(ResultMsg),
                    Icon: "k-ext-error"
                });
            }
        });
    }
    
    // 展开栏目树
   var expandItemTree = function () {
        itemTree.expand(".k-item");
    }

    // 收缩栏目树
   var collapseItemTree = function() {
        itemTree.collapse(".k-item");
    }
   
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
	    Height: $(window).height()*0.95,
	    Editable:"popup",
	    ColumnMenu: false,    
	    Filterable: false,    
	    Columns: [{ 
	            	   field: "menuName",
	            	   width: "30%",
	            	   title: "栏目名称"
	               },{ 
	            	   field: "menuType",
	            	   width: "30%",
	            	   title: "栏目类型",
	            	   values: [{ 
	    		   			text: "真实栏目", 
	    		   			value: 0 
	    		   		},{
	    		   			text: "跳转栏目 ", 
	    		   			value: 1 
	    		   		},{
	    		   			text: "虚拟栏目", 
	    		   			value: 2 
	    		   		}]
	            	},{ 
	            		field: "openMode",
	            		width: "30%",
	            		title: "打开方式",
	 	            	values: [{ 
           		   			text: "当前窗口打开", 
           		   			value: 0 
           		   		},{
           		   			text: "新窗口打开 ", 
           		   			value: 1 
           		   		}]
	            	},{ 
	            		field: "sortNo",
	            		width: "10%",
	            		title: "排序号"
	            	}]
	       });
	  
	  itemTree = $("#itemTree").kendoTreeView({
	       dataTextField: "menuName",
	       select: function(e) {
	      	 parentMenuId = this.dataItem(e.node).id;
	      	 grid.dataSource.read();
	       }
	   }).data("kendoTreeView");
	  
	  loadItemTree();
};
 // 刷新按钮
 $("#TreeRefresh").click(function () {
 	 dataSource.read();
    loadItemTree();
 });

 // 展开按钮
 $("#TreeExpand").click(function () {
     expandItemTree();
 });

 // 收缩按钮
 $("#TreeCollapse").click(function () {
     collapseItemTree();
 });
/******编辑页面js******/
	var validator = null;
//验证
	var  checkForm =  function () {
	      if (validator.validate()) {
	          return true;
	      }else{
	          return false;
	      }
	  }
	//设置单选框选中的值，并绑定数据
	var setRadio_item = function(name){
		$('input[name="'+name+'"][checked]').each(function(i){
			 item.set(name,$(this).val());
		 })
	}
	  //初始化表单
	  var initForm = function (){
	      //数据模型绑定,MVVM
	      item = kendo.observable(item);
	      //数据模型绑定
	      kendo.bind($("#form-container-edit"), item);
	      //下拉栏目树
		  selectMenu();
		  //新建对象绑定模型
		 if (editType == "add") {//VIEW_MODEL层的数据初始化
			 item.set("sortNo",0);
			 item.set("parentMenuId",0);
			 setRadio_item("isValid");
			 setRadio_item("menuType");
			 setRadio_item("articleContentType");
			 setRadio_item("openMode");
			 setRadio_item("showMode");
		 }
		 if (editType == "edit") {//VIEW_MODEL层的数据初始化
			 var imgUrl = item.menu_img_url;
			 if(null != imgUrl && "undefined" != imgUrl){
				 var imgHtml = '<img  style="height:150px;width:500px;" src="'+imgUrl+'"></img>';
	    		 $("#show_menu_img_div").html(imgHtml);
			 }
			 
		 }
		 if(item.menuType == 1){
			 document.getElementById("url").style.display = "";
		 }
		 if(item.parentMenuId==0){
			  selectMenuTree.selectItem("栏目列表");
		 }else{
			  selectMenuTree.selectItem(item.parentMenuName);
		 }
	  }

	  //监听退出按钮
	  $("#cancel").click(function(){
	      closewindow("div");
	      grid.dataSource.read();
	  });
	  //监听保存按钮
	  $("#submit_confirm").click(function(){
		  if (checkForm()){
			  item.menu_img_url = $("#menu_img_url").val();
			  if (editType == "edit"){
				  AjaxDoPost(path+"/cms/menu/update.json", "models="
	                              + JSON.stringify(item), false, editSuccess,
	                              editError);
			 }else if (editType == "add"){
	        	 if(checkForm()){
	        		AjaxDoPost(path+"/cms/menu/add.json", "models="
	                                  + JSON.stringify(item), false, addSuccess,
	                                  addError);
	                      }
	                  }
	              }
	   });

	  //新增组成功回调
	var addSuccess = function(result){
		if (result.resultCode === 0){
			  closewindow("div");
		      grid.dataSource.read();
	          loadItemTree();
	    }else{
	          alert(result.resultMsg);
	      }
	  }

	  //新增组失败回调
	 var addError = function(result){
	      AlertDialog({
	          Title : "提示信息",
	          Message : "添加失败！",
	          Icon : "k-ext-error"
	      });
	  }

	  //修改成功回调函数
	 var editSuccess = function(result){
	      if (result.resultCode === 0){
	    	  closewindow("div");
	    	  grid.dataSource.read();
	          loadItemTree();
	      }else{
	          alert(result.resultMsg);
	      }
	  }

	  //修改失败回调函数
	 var editError = function(result){
	      AlertDialog({
	          Title : "提示信息",
	          Message : "修改失败！",
	          Icon : "k-ext-error"
	      });
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
	  * 下拉栏目树
	  */
	 var selectMenu = function(){
			selectMenuTree = CreateDropDownTreeView({
			        ID: "parentMenuId",
			        AutoBind: true,
			        DataSource: selectSource,
			        TextField: "menuName",
			        Height: 400,
			        OnSelect: function (e) {
			            var dataItem = this.dataItem(e.node);
			            item.parentMenuId = dataItem.id;
			            item.parentMenuName = dataItem.menuName;
			        }
			    });
	 }
	 
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
	  }

	  // 删除失败回调函数
	  var deleteError = function (result) {
	  	AlertDialog({
	          Title: "提示信息",
	          Message: "删除失败！",
	          Icon: "k-ext-error"
	      });
	  }
	 var init_editPage = function(){
		//初始化表单
		initForm();
	    //kendoui校验器初始化
	    validator = $("#dataform").kendoValidator().data("kendoValidator");
	  	//标签页
		 $("#menuInfoTab").kendoTabStrip({
	          activate: onTabActivate
	      });
		 $("#sortNo").kendoNumericTextBox({
				decimals : 0,//小数位数，即VIEW_MODEL层的精度
				value: item.sortNo,//初始值，VIEW层
				min : 0,//最小值
				format: "0",
				step : 1//每次点击增减按钮的浮动值
	     	});
	  }
	 /**
	  * 上传栏目图片
	  */
	 var upMenuImg = function(){
		 var options ={
			 url:path+"/cms/menu/upMenuImg.json",
	         type:'post',                    
	         success:function(data){
	        	 if(0 != data.resultCode){
	        		 alert(data.resultMsg);
	        	 }else{
	        		 var imURL =  data.resultData;
	        		 var imgHtml = '<img  style="height:150px;width:500px;" src="'+ip+imURL+'"></img>';
	        		 $(parent.document).find("#show_menu_img_div").html(imgHtml);
	        		 $(parent.document).find("#menu_img_url").val(imURL);
	        	 }
	         },
	         error:function(){
	        	 alert("上传图片失败");
	         }
	      };
        var form =$("#menu_img_form");
        form.ajaxSubmit(options);  
	 }
	 var delMenuImg = function(){
			var $menuImage =  $(parent.document).find("#show_menu_img_div");
			if(null != $menuImage && "undefined" != $menuImage){
				$menuImage.remove();
			}
	}
  return{
	  init_listPage : init_listPage,
	  init_editPage : init_editPage,
	  deleteSuccess : deleteSuccess,
	  deleteError : deleteError,
	  upMenuImg:upMenuImg,
	  delMenuImg:delMenuImg
  }
	
})();

//新增初始化
var onAdd = function () {
 	item = new Object;
 	// 编辑的类型：新增
 	editType="add";
     // 可以在这里进行新增页面的初始化值设置
    
     return true;
  }

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
 }

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
 }

var del = function () {
     var row = grid.select();
     var datas = new Array();
     for (var i = 0; i < row.length; i++) {
         var data = grid.dataItem(row[i]);
         datas.push(data);
     }
     if (typeof (data) != undefined) {
     	AjaxDoPost(path+"/cms/menu/delete.json","models=" + JSON.stringify(data),true,xjj.cms.menu.deleteSuccess,xjj.cms.menu.deleteError);
     }
 }