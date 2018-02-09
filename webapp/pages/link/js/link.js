xjj.nameSpace("xjj.cms.link");
//创建工具栏按钮
xjj.cms.link = (function(){
  	var ToolbarHtml = new CreateToolBarButton({
            GridId: "grid",
            Url: path+"/pages/link/link_edit.jsp",
            Add: true,
            Edit: true,
            Del: true
     });
  //数据源
  	dataSource = CreateGridDataSource({
 	    Transport: {
           ReadUrl: path+"/cms/link/list.json",
           ReadDataFunc: function () {
         	    queryObj = new Object;
         	   queryObj.linkName = $("#linkName").val();
             	return queryObj;
           }
      }
 	  });
  	importDataSource = CreateGridDataSource({
 	    Transport: {
           ReadUrl: path+"/cms/chlLink/list.json",
           ReadDataFunc: function () {
         	    queryObj = new Object;
         	    queryObj.channelId=channelId;
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
        //Height: $(window).height()*0.8,
        Editable:"popup",
        ColumnMenu: false,    
        Filterable: false,    
        Columns: [ 
                  { field: "linkName",title: "超链接名称"},
                  { field: "linkAddr",width: "40%",title: "超链接地址"},
                  { field: "linkType",width: "15%",title: "超链接类型",values: [
                      { text: "文字类型", value: 0 },
                      { text: "图片类型", value: 1 }
                  ]}
             ]
         });
};
	var init_importListPage = function(){
	    // 列表表格
	    grid = CreateGrid({
	    ID: "grid",
	    DataSource: importDataSource,
	    //ToolBarTemplate: ToolbarHtml.Str,
	    Height: $(window).height()*0.9,
	    Editable:"popup",
	    ColumnMenu: false,    
	    Filterable: false,    
	    Columns: [
//	              { template: "<input type='checkbox' text='"+'#: id #'+"' value='"+'#: id #'+"'  id='"+'#: id #'+"' name='chbox' >"
//            		  ,width: "4%",title: "<div><input type='checkbox' id='allbox' name='allbox'  onclick=selectAll('allbox')></div>"},
	              { field: "linkName",width: "15%",title: "超链接名称"},
	              { field: "linkAddr",width: "30%",title: "超链接地址"},
	              { field: "linkType",width: "15%",title: "超链接类型",values: [
	                  { text: "文字类型", value: 0 },
	                  { text: "图片类型", value: 1 },
	              ]},
	              { field: "sort",width: "10%",title: "排序"},
	              { field: "",width: "35%",title: "操作", command:[ {text: "编辑", click: editSort}] }
	         ]
	     });
	};
	var init_checked = function(){
		$.ajax({ 
			url: path+"/cms/chlLink/findAddLink.json",
			type:"post",
			dataType:"json",
			data:{"channelId":channelId},
			success: function(data){
				var linkIdList = new Array();
				linkIdList = data.toString().split(",");
				if(linkIdList != null && linkIdList != ""){
					for ( var i = 0; i < linkIdList.length; i++) {
						var linkId = linkIdList[i];
						if("" != linkId && null != linkId && linkId != "undefined"){
							document.getElementById(linkId).checked = true;
						}
					}
				}
	      	},
	      	error:function(){
	      	}
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
	//设置单选框选中的值，并绑定数据
	var setRadio_item = function(name){
		$('input[name="'+name+'"][checked]').each(function(i){
			 item.set(name,$(this).val());
		 });
	};
	  //初始化表单
	  var initForm = function (){
	      //数据模型绑定,MVVM
	      item = kendo.observable(item);
	      //数据模型绑定
	      kendo.bind($("#form-container-edit"), item);
	      //loadUserMenuTree();
	      //下拉栏目树
		  //selectMenu();
		  //新建对象绑定模型
		 if (editType == "add") {//VIEW_MODEL层的数据初始化
			 item.set("linkType",0); 
			 item.set("openType",0);
		 }
		 if (editType == "edit") {//VIEW_MODEL层的数据初始化
			 if(document.getElementsByName("linkType")[1].checked == true){
				 $("#linkImg").val(item.linkImg);
				 var imgUrl = item.linkImg;
				 if(null != imgUrl && "undefined" != imgUrl){
					 var imgHtml = '<img  style="height: 100px; width: 250px;" src="'+imgUrl+'"></img>';
		    		 $("#show_link_img_div").html(imgHtml);
				 }
			 }
		 }
		 if(item.linkType == 1){
			 document.getElementById("linkImg").style.display = "";
		 }
	  };
	  //监听退出按钮
	  $("#cancel").click(function(){
		  window.location.href= path+"/pages/link/link_list.jsp";
	      closewindow("div");
	  });
	  //监听保存按钮
	  $("#submit_confirm").click(function(){
		  if (checkForm()){
			  item.linkImg = $("#linkImg").val();
			  if (editType == "edit"){
				  AjaxDoPost(path+"/cms/link/update.json", "models="
	                              + JSON.stringify(item), false, addSuccess,addError);
			 }else if (editType == "add"){
	        	 if(checkForm()){
	        		AjaxDoPost(path+"/cms/link/add.json", "models="
	                                  + JSON.stringify(item), false, addSuccess,addError);
	                      }
	                  }
	              }
	   });

	  //新增组成功回调
	var addSuccess = function(result){
		if (result.resultCode === 0){
			  window.location.href= path+"/pages/link/link_list.jsp";
	          closewindow("div");
	    }else{
	          alert(result.resultMsg);
	      }
	  };

	  //新增组失败回调
	 var addError = function(result){
	      AlertDialog({
	          Title : "提示信息",
	          Message : "添加失败！",
	          Icon : "k-ext-error"
	      });
	  };

	  //修改成功回调函数
	 var editSuccess = function(result){
	      if (result.resultCode === 0){
	    	  grid.dataSource.read();
	          loadItemTree();
	          itemTree.expand(".k-item");
	          closewindow("div");
	      }else{
	          alert(result.resultMsg);
	      }
	  };

	  //修改失败回调函数
	 var editError = function(result){
	      AlertDialog({
	          Title : "提示信息",
	          Message : "修改失败！",
	          Icon : "k-ext-error"
	      });
	  };
	  //Tab单项激活事件
	 var onTabActivate = function (e){
	      var TabText = $(e.item).find("> .k-link").text();
	      refreshTabContent(TabText);
	  };
	  //刷新Tab页内容
	 var refreshTabContent = function(TabText){

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
	 var init_editPage = function(){
		//初始化表单
		initForm();
		//初始化附件
	    //kendoui校验器初始化
	    validator = $("#dataform").kendoValidator().data("kendoValidator");
	  	//标签页
		 $("#menuInfoTab").kendoTabStrip({
	          activate: onTabActivate
	      });
	  };
  return{
	  init_listPage : init_listPage,
	  init_editPage : init_editPage,
	  init_importListPage : init_importListPage,
	  init_checked:init_checked,
	  deleteSuccess : deleteSuccess,
	  deleteError : deleteError
  };
	
})();

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
     	AjaxDoPost(path+"/cms/link/delete.json","models=" + JSON.stringify(data),true,xjj.cms.link.deleteSuccess,xjj.cms.link.deleteError);
     }
 };

var delSuccess = function(data){
	document.getElementById(data).innerHTML="";
};
var editSort = function(){
	var row = grid.select();
	selectRowIndex = row.index();
	Item = grid.dataItem(row);
	var url = path+"/pages/link/link_import_edit.jsp";
	openwindow(url, "", "", 'edit', 'div');
};
