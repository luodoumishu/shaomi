xjj.nameSpace("xjj.cms.file");
//创建工具栏按钮
xjj.cms.file = (function(){
  	var ToolbarHtml = new CreateToolBarButton({
            GridId: "grid",
            Url: path+"/pages/file/file_edit.jsp",
            Add: true,
            Edit: true,
            Del: true
     });
  //数据源
  	dataSource = CreateGridDataSource({
 	    Transport: {
           ReadUrl: path+"/cms/file/list.json",
           ReadDataFunc: function () {
         	    queryObj = new Object;
             	return queryObj;
           }
      }
 	  });
 // 加载栏目树
	var loadUserMenuTree = function() {
		JsonPostForWaiting({
			Url : path + "/cms/menu/userMenuTree.json?showMode=2",
			WaittingText : "正在加载栏目...",
			//AimDiv : "#LeftLayout",
			Success : function(ResultMsg, Parameter) {
				var data = "";
				if (ResultMsg.ResultMsg.resultData != null) {
					data = ResultMsg.ResultMsg.resultData;
				}
				var TreeData = TransformToTreeFormat(data, "id",
						"parentMenuId", "ChildLists");
				var ParentTree = new Array();
				var ParentData = new Object;
				ParentData.id = "0";
				ParentData.parentMenuId = "0";
				ParentData.menuName = "栏目列表";
				ParentData.ChildLists = TreeData;
				ParentTree.push(ParentData);
				selectSource = new kendo.data.HierarchicalDataSource({
					data : ParentTree,
					schema : {
						model : {
							children : "ChildLists"
						}
					}
				});
			},
			Error : function(ResultMsg, Parameter) {
				DetailAlertDialog({
					Title : "出错信息",
					Message : "加载栏目树失败！",
					DetailMessage : GetErrorMsg(ResultMsg),
					Icon : "k-ext-error"
				});
			}
		});
	};
    
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
                  { field: "fileTitle",width: "15%",title: "标题"},
                  { field: "cmsAffixs",template: function(dataItem){
                	  var affixs = dataItem.cmsAffixs;
                	  var fileNameHtml = "<ul>";
                	  if(affixs!=null){
                		  for(var i = 0; i < affixs.length;i++){
                    		  var fileName = affixs[i].affix_originalName;
                    		  var name = "<li>"+fileName+"</li>";
                    		  fileNameHtml += name;
                    	  }
                	  }
                	  return fileNameHtml;
                  	},width: "55%",title: "文件名称"},
                  { field: "menuName",width: "30%",title: "所属栏目名称"}
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
	      loadUserMenuTree();
	      //下拉栏目树
		 selectMenu();
		  //新建对象绑定模型
		 if (editType == "add") {//VIEW_MODEL层的数据初始化
			 item.set("menuId",0);
		 }
		 if (editType == "edit") {//VIEW_MODEL层的数据初始化
			 AjaxDoPost(path + "/cms/file/findAffix.json","modelId=" + JSON.stringify(item.id),false,Success,Error);
		 }
		 if(item.menuId==0){
			  selectMenuTree.selectItem("请选择");
		 }else{
			 selectMenuTree.selectItem(item.menuName);
		 }
	  };
	  //监听退出按钮
	  $("#cancel").click(function(){
		  window.location.href= path+"/pages/file/file_list.jsp";
	      closewindow("div");
	  });
	  //监听保存按钮
	  $("#submit_confirm").click(function(){
		  if (checkForm()){
			  var options = {
				         type:'post',
				         success:function(data){
				        	 var jsonDate = eval( "(" + data + ")" );
				        	 if (jsonDate.resultCode === 0){
				        		 window.location.href= path+"/pages/file/file_list.jsp";
				      	          closewindow("div");
				      	    }else{
				      	          alert(decodeURIComponent(jsonDate.resultMsg,"utf-8"));
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

	  //新增组成功回调
	var addSuccess = function(result){
		if (result.resultCode === 0){
			  grid.dataSource.read();
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
	 
	 /**
	  * 下拉栏目树
	  */
	 var selectMenu = function(){
			selectMenuTree = CreateDropDownTreeView({
			        ID: "menuId_div",
			        AutoBind: true,
			        DataSource: selectSource,
			        TextField: "menuName",
			        OnSelect: function (e) {
			            var dataItem = this.dataItem(e.node);
			            var menuId = dataItem.id;
						var menuName = dataItem.menuName;
						item.menuId = menuId;
						item.menuName = menuName;
						$("#menuId").val(menuId);
						$("#menuName").val(menuName);
			        }
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
     	AjaxDoPost(path+"/cms/file/delete.json","models=" + JSON.stringify(data),true,xjj.cms.file.deleteSuccess,xjj.cms.file.deleteError);
     }
 };

var Success = function(data){
	 var list = data.resultData;
	 var affix_originalName = "";
	 if(list != null){
		 for(var i = 0;i<list.length;i++){
			 affix_originalName = list[i].affix_originalName;
			 var id = list[i].id;
			 var fileExtension = affix_originalName.split(".")[1];
			 var fileImg = "";
			 if(fileExtension == "zip" || fileExtension == "rar"){
				 fileImg = "zip-file";
			 }else if(fileExtension == "doc" || fileExtension == "docx" || fileExtension == "wps"){
				 fileImg = "doc-file";
			 }else if(fileExtension == "pdf"){
				 fileImg = "pdf-file";
			 }else if(fileExtension == "xls" || fileExtension == "xlsx"){
				 fileImg = "xls-file";
			 }else{
				 fileImg = "default-file";
			 }
			 var appendHtml = "<tr><div id='"+id+"' class='file-wrapper' style='width: 300px;margin-left:13px;'>" +
			 "<div onclick=downAffix('"+id+"');><span class='file-icon "+fileImg+"'></span>" +
			 "<h4 class='file-heading file-name-heading'>"+affix_originalName+"</h4></div>" +
			 "<button class='k-upload-action k-button k-button-bare' style='display: inline-block;float:right;' type='button' onclick=delelAffix('"+id+"');>" +
			 "<span title='删除' class='k-icon k-i-close k-delete'/></span></button></div></tr>";
			 $("#affix").html($("#affix").html() + appendHtml);
		 }
	 }
};

var Error = function(){
};
var delelAffix = function(data){
	AjaxDoPost(path+"/cms/file/delelAffix.json","affixId=" + JSON.stringify(data),true,delSuccess(data));
};
var delSuccess = function(data){
	document.getElementById(data).innerHTML="";
};
var downAffix = function(data){
	/*AjaxDoPost(path+"/cms/file/downAffix.json","affixId=" + JSON.stringify(data),true);*/
	window.open(path+"/cms/file/downAffix.json?affixId=" + JSON.stringify(data));
	
};