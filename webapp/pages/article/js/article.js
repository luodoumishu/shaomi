xjj.nameSpace("xjj.cms.article");
// 创建工具栏按钮
xjj.cms.article = (function() {
	var ToolbarHtml = new CreateToolBarButton({
		GridId : "grid",
		Url : path + "/pages/article/article_edit.jsp",
		Add : true,
		Edit : true,
		Del : true,
		Check : true
	});
	
	var CheckToolbarHtml = new CreateToolBarButton({
		GridId : "grid",
		DCheck : true
	});
	
	var TuihuiToolbarHtml = new CreateToolBarButton({
		GridId : "grid",
		Url : path + "/pages/article/article_history_edit.jsp",
		TuiHui : true,
		Edit : true,
		CdDel : true
//		ArticleTJ : true
	});
	
	// 数据源
	dataSource = CreateGridDataSource({
		Transport : {
			ReadUrl : path + "/cms/article/byuser4List.json",
			ReadDataFunc : function() {
				queryObj = new Object;
				queryObj.articleName = $("#articleName").val();
				queryObj.menuId = menuId; // 栏目id
				queryObj.check = "0,4";
				return queryObj;
			}
		}
	});
	
	checkSource = CreateGridDataSource({
		Transport : {
			ReadUrl : path + "/cms/article/allList.json",
			ReadDataFunc : function() {
				queryObj = new Object;
				queryObj.articleName = $("#articleName").val();
				queryObj.menuId = menuId; // 栏目id
				queryObj.check = "1";
				queryObj.orgId = orgId;
				queryObj.orgName = orgName;
				return queryObj;
			}
		}
	});
	
	
	historySource = CreateGridDataSource({
		Transport : {
			ReadUrl : path + "/cms/article/historyList.json",
			ReadDataFunc : function() {
				queryObj = new Object;
				queryObj.articleName = $("#articleName").val();
				queryObj.menuId = menuId; // 栏目id
				queryObj.orgId = orgId;
				queryObj.orgName = orgName;
				queryObj.isDelete = zt;
				return queryObj;
			}
		}
	});
	
	// 加载栏目树
	var loadMenuTree = function() {
		JsonPostForWaiting({
			//Url : path + "/cms/menu/treeList.json",
			Url : path + "/cms/menu/userMenuTree.json?showMode=0,1",
			WaittingText : "正在加载栏目...",
			AimDiv : "#LeftLayout",
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

				var jgSource = new kendo.data.HierarchicalDataSource({
					data : ParentTree,
					schema : {
						model : {
							children : "ChildLists"
						}
					}
				});

				menuTree.setDataSource(jgSource);
				menuTree.expand(".k-item");
				menuTree.expand(".k-item");
				menuTree.expand(".k-item");
				menuTree.expand(".k-item");
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
	}
	
	// 加载栏目树
	var loadUserMenuTree = function() {
		JsonPostForWaiting({
			Url : path + "/cms/menu/userMenuTree.json?showMode=0,1",
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
	}
	
	// 展开栏目树
	var expandMenuTree = function() {
		menuTree.expand(".k-item");
	}

	// 收缩栏目树
	var collapseMenuTree = function() {
		menuTree.collapse(".k-item");
	}
	
	//文章状态
	var articleStatus = function(check_log){
		if(check_log == '0'){
			return "<span style='color:blue;text-algin:centre;'>未提交审核</span>"
		}else if(check_log == '1'){
			return "<span style='color:blue;text-algin:centre;'>待审核</span>"
		}else if(check_log == '2'){
			return "<span style='color:green;text-algin:centre;'>审核通过</span>"
		}else if(check_log == '3'){
			return "<span style='color:red;text-algin:centre;'>审核不通过</span>"
		}else if(check_log == '4'){
			return "<span style='color:black;text-algin:centre;'>退回</span>"
		}
	}
	
	
	var init_listPage = function() {
		// 页面分隔
		$("#MainLayout").kendoSplitter({
			panes : [ {
				collapsible : true,
				size : '20%'
			}, {
				collapsible : true,
				size : "80%"
			} ]
		});
		// 列表表格
		grid = CreateGrid({
			ID : "grid",
			DataSource : dataSource,
			ToolBarTemplate : ToolbarHtml.Str,
			Height : $(window).height()*0.9,
			Editable : "popup",
			ColumnMenu : false,
			Filterable : false,
			Columns : [ {
				field : "check_log",
				title : "文章状态",
				width : "10%",
				template: function(dataItem) {
				      return articleStatus(dataItem.check_log);
			    }
			}, {
				field : "title",
				width : "35%",
				title : "文章标题"
			}, {
				field : "menuName",
				width : "10%",
				title : "所属栏目"
			}, {
				field : "creatUserName",
				width : "10%",
				title : "发文人"
			},{
				field : "orgName",
				width : "10%",
				title : "发文部门"
			}, {
				field : "readCount",
				width : "10%",
				title : "阅读次数"
			}, {
				field : "publicTime",
				width : "15%",
				title : "发文时间"
			}
			]
		});
		menuTree = $("#itemTree").kendoTreeView({
			dataTextField : "menuName",
			select : function(e) {
				menuId = this.dataItem(e.node).id;
				grid.dataSource.read();
			}
		}).data("kendoTreeView");
		loadMenuTree();
		// 刷新按钮
		$("#TreeRefresh").click(function() {
			grid.dataSource.read();
			loadMenuTree();
		});

		// 展开按钮
		$("#TreeExpand").click(function() {
			expandMenuTree();
		});

		// 收缩按钮
		$("#TreeCollapse").click(function() {
			collapseMenuTree();
		});
	}
	
	var init_checkPage = function() {
		// 页面分隔
		$("#MainLayout").kendoSplitter({
			panes : [ {
				collapsible : true,
				size : '20%'
			}, {
				collapsible : true,
				size : "80%"
			} ]
		});
		// 列表表格
		grid = CreateGrid({
			ID : "grid",
			DataSource : checkSource,
			ToolBarTemplate : CheckToolbarHtml.Str,
			Height : $(window).height()*0.9,
			Editable : "popup",
			ColumnMenu : false,
			Filterable : false,
			Columns : [ {
				field : "check_log",
				title : "文章状态",
				width : "10%",
				template: function(dataItem) {
				      return articleStatus(dataItem.check_log);
			    }
			}, {
				/*field : "title",*/
				width : "35%",
				title : "文章标题",
				template : "<a href='javascript:void(0);' onclick='showDetil();' style='color:blue'>#=title#</a>"
			},{
				field : "menuName",
				width : "10%",
				title : "所属栏目"
			}, {
				field : "creatUserName",
				width : "10%",
				title : "发文人"
			}, {
				field : "orgName",
				width : "10%",
				title : "发文部门"
			}, {
				field : "readCount",
				width : "10%",
				title : "阅读次数"
			}, {
				field : "publicTime",
				width : "15%",
				title : "发文时间"
			}
			]
		});
		menuTree = $("#itemTree").kendoTreeView({
			dataTextField : "menuName",
			select : function(e) {
				menuId = this.dataItem(e.node).id;
				grid.dataSource.read();
			}
		}).data("kendoTreeView");
		loadMenuTree();
		// 刷新按钮
		$("#TreeRefresh").click(function() {
			grid.dataSource.read();
			loadMenuTree();
		});

		// 展开按钮
		$("#TreeExpand").click(function() {
			expandMenuTree();
		});

		// 收缩按钮
		$("#TreeCollapse").click(function() {
			collapseMenuTree();
		});
	}
	
	var history_listPage = function() {
		// 页面分隔
		$("#MainLayout").kendoSplitter({
			panes : [ {
				collapsible : true,
				size : '20%'
			}, {
				collapsible : true,
				size : "80%"
			} ]
		});
		// 列表表格
		grid = CreateGrid({
			ID : "grid",
			DataSource : historySource,
			ToolBarTemplate : TuihuiToolbarHtml.Str,
			Height : $(window).height()*0.9,
			Editable : "popup",
			ColumnMenu : false,
			Filterable : false,
			Columns : [ {
				field : "check_log",
				title : "文章状态",
				width : "10%",
				template: function(dataItem) {
				      return articleStatus(dataItem.check_log);
			    }
			}, {
				field : "title",
				width : "30%",
				title : "文章标题"
			}, {
				field : "menuName",
				width : "10%",
				title : "所属栏目"
			},{
				field : "creatUserName",
				width : "8%",
				title : "发文人"
			}, {
				field : "orgName",
				width : "8%",
				title : "发文部门"
			}, {
				field : "readCount",
				width : "8%",
				title : "阅读次数"
			}, {
				field : "publicTime",
				width : "10%",
				title : "发文时间"
			},{width: "10%",title: "操作", 
				  template: function(dataItem){
				  	  var _top = dataItem.top;
				  	  var check_log = dataItem.check_log;
					  var html = "";
					  if(dataItem.isDelete !=1 ){
						  if(check_log == 2){
							  if(_top==1){
								  html += "<button class='k-button' onclick='cancleTop(\""+dataItem.id+"\")' style='width:10px;'>取消置顶</button>";
							  }else{
								  html += "<button class='k-button' onclick='Top(\""+dataItem.id+"\")' style='width:10px;'>置顶</button>";
							  }
						  }
					  }
					  return html;
				  }
			  }
		]
		});
		menuTree = $("#itemTree").kendoTreeView({
			dataTextField : "menuName",
			select : function(e) {
				menuId = this.dataItem(e.node).id;
				grid.dataSource.read();
			}
		}).data("kendoTreeView");
		loadMenuTree();
		// 刷新按钮
		$("#TreeRefresh").click(function() {
			grid.dataSource.read();
			loadMenuTree();
		});

		// 展开按钮
		$("#TreeExpand").click(function() {
			expandMenuTree();
		});

		// 收缩按钮
		$("#TreeCollapse").click(function() {
			collapseMenuTree();
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
	// 设置单选框选中的值，并绑定数据
	var setRadio_item = function(name) {
		$('input[name="' + name + '"][checked]').each(function(i) {
			item.set(name, $(this).val());
		})
	}
	// 初始化表单
	var initForm = function() {
		// 数据模型绑定,MVVM
		item = kendo.observable(item);
		// 数据模型绑定
		kendo.bind($("#form-container-edit"), item);
		loadUserMenuTree();
		// 下拉栏目树
		selectUserMenu();
		// 新建对象绑定模型
		if (editType == "add") {// VIEW_MODEL层的数据初始化
			item.set("readCount", 0);
			item.set("ifComment", 0);
		}
		if("edit" == editType){
			var imgUrl = item.behalf_imageUrl;
			if(null != imgUrl && "undefined" != imgUrl){
				showImg(imgUrl); 
			}
		}
		
   	 	if(item.parentMenuId==0){
			  selectMenuTree.selectItem("栏目列表");
		 }else{
			  selectMenuTree.selectItem(item.menuName);
		 }
		// kendoui校验器初始化
		validator = $("#dataform").kendoValidator().data("kendoValidator");
		// 标签页
		$("#menuInfoTab").kendoTabStrip({
			activate : onTabActivate
		});
		/*********阅读数 start*********/
		$("#readCount").kendoNumericTextBox({
			decimals : 0,// 小数位数，即VIEW_MODEL层的精度
			value : item.readCount,// 初始值，VIEW层
			min : 0,// 最小值
			format : "0",
			step : 1// 每次点击增减按钮的浮动值
		});
		/*********阅读数 end*********/
		/*********颜色选择框 start*********/
		var $titleColor = document.getElementById("titleColor");
    	var colorDialog = new ColorDialog($titleColor);
    	colorDialog.selectedColor = $titleColor.value;
    	colorDialog.onColorSelected = function () {
    		$titleColor.value = colorDialog.selectedColor; 
    	}
    	colorDialog.create("menuInfoTab");
    	/*********颜色选择框 end***********/
    	/*********字体大小 start*********/
    	$("#titleSize").kendoComboBox({
            dataTextField: "text",
            dataValueField: "value",
            dataSource: [
                { text: "12", value: "12" },
                { text: "14", value: "14" },
                { text: "16", value: "16" },
                { text: "18", value: "18" },
                { text: "20", value: "20" },
                { text: "22", value: "22" },
            ],
            filter: "contains",
            suggest: true,
            index:5
           
        });
    	/*********字体大小 end***********/
    	/*********发布时间 start*********/
    	initTime("publicTime","nowtime");
    	/*********发布时间 end*********/
    	var getImg = function(){
    		var pageContent =  editor.getContent();
    		alert(pageContent);
    		$.ajax({
   	            url: path + "/cms/article/getImg.json",
   	            type: "POST",
   	            data:{
   	            	pageContent:pageContent
   	            },
   	            success: function (result) {
   	            	getImgSuccess(result);
   	            },
   	            error: function (result) {
   	            	getImgError(result);
   	            }
   	        });
    	//	alert(editor.isFocus());
            //var isFocus = UE.getEditor('content').isFocus();
           // if(isFocus){
            //	alert("ff");
            //}
           // UE.dom.domUtils.preventDefault(e)
        }
    	
    	/*$("#content").blur(function(s){
    		alert("ff");
    		var pageContent =  editor.getContent();
    		 $.ajax({
    	            url: path + "/cms/article/getImg.json",
    	            type: "POST",
    	            data:{
    	            	pageContent:pageContent
    	            },
    	            success: function (result) {
    	            	getImgSuccess(result);
    	            },
    	            error: function (result) {
    	            	getImgError(result);
    	            }
    	        });
    	});*/
	}

	// 监听退出按钮
	$("#cancel").click(function() {
		closewindow("div");
		window.location.href= path+"/pages/article/article_list.jsp";
	});
	// 监听保存按钮
	$("#submit_confirm").click(
			function() {
				if (checkForm()) {
					var itemMenuId = item.menuId;
					if("" == itemMenuId || undefined == itemMenuId || "0" == itemMenuId){
						alert("请选择栏目");
						return ;
					}
					if(null != editor){
						item.content = editor.getContent() || "";
					}
					var $behalfImage =  $("#show_behalfImage img");
					if(null != $behalfImage && "undefined" != $behalfImage){
						item.behalf_imageUrl = $behalfImage.attr("src") || "";
					}
					var options = "";
					if (editType == "edit") {
						 options ={
						         type:'POST',
						         //dataType:'json',
						         success:function(data){
						        	 //editSuccess(data.jsonResult);
						        	 editSuccess(null);
						         },
						         error:function(){
						        	// editError(data.jsonResult);
						        	 editError(null);
						         }
						      };
					} else if (editType == "add") {
						options ={
						         type:'POST', 
						        // dataType:null,
						         success:function(data){
						        	 //addSuccess(data.jsonResult);
						        	 addSuccess(null);
						         },
						         error:function(){
						        	// addError(data.jsonResult);
						        	 addError(null);
						         }
						      };
					}
					//提交表单
					$("#dataform").ajaxSubmit(options);
				}
			});
	
	
	// 监听退出按钮
	$("#history_cancel").click(function() {
		window.location.href= path+"/pages/article/article_history.jsp?zt=0";
	});
	// 监听保存按钮
	$("#history_submit_confirm").click(
			function() {
				if (checkForm()) {
					var itemMenuId = item.menuId;
					if("" == itemMenuId || undefined == itemMenuId || "0" == itemMenuId){
						alert("请选择栏目");
						return ;
					}
					if(null != editor){
						item.content = editor.getContent() || "";
					}
					var $behalfImage =  $("#show_behalfImage img");
					if(null != $behalfImage && "undefined" != $behalfImage){
						item.behalf_imageUrl = $behalfImage.attr("src") || "";
					}
					var options = "";
					if (editType == "edit") {
						 options ={
						         type:'POST',
						         //dataType:'json',
						         success:function(data){
						        	 //editSuccess(data.jsonResult);
						        	 editHisSuccess(null);
						         },
						         error:function(){
						        	// editError(data.jsonResult);
						        	 editError(null);
						         }
						      };
					} else if (editType == "add") {
						options ={
						         type:'POST', 
						        // dataType:null,
						         success:function(data){
						        	 //addSuccess(data.jsonResult);
						        	 addSuccess(null);
						         },
						         error:function(){
						        	// addError(data.jsonResult);
						        	 addError(null);
						         }
						      };
					}
					//提交表单
					$("#dataform").ajaxSubmit(options);
				}
			});
	
	//查看文章首页时审核
	$("#check_pass").click(function() {
		var data_id = item.id;
		window.location.href= path+"/pages/article/article_check.jsp";
		AjaxDoPost(path + "/cms/article/dCheck.json", "models="
				+ JSON.stringify(data_id), true, xjj.cms.article.dCheckSuccess,
				xjj.cms.article.dCheckError);
		
	});
	$("#check_unpass").click(function() {
		var data_id = item.id;
		window.location.href= path+"/pages/article/article_check.jsp";
		AjaxDoPost(path + "/cms/article/dNotCheck.json", "models="
				+ JSON.stringify(data_id), true, xjj.cms.article.dCheckSuccess,
				xjj.cms.article.dCheckError);
	});
	
	// 新增组成功回调
	var addSuccess = function(result) {
			closewindow("div");
			window.location.href= path+"/pages/article/article_list.jsp";
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
		//if (result.resultCode === 0) {
			closewindow("div");
			window.location.href= path+"/pages/article/article_list.jsp"; 

		/*} else {
			alert(result.resultMsg);
		}*/
	}
	var editHisSuccess = function(result) {
			var url = path+"/pages/article/article_history.jsp?zt=0";
			window.location.href= url;
	}

	// 修改失败回调函数
	var editError = function(result) {
		AlertDialog({
			Title : "提示信息",
			Message : "修改失败！",
			Icon : "k-ext-error"
		});
	}
	
	var getImgSuccess = function(result){
		if (result.resultCode === 0) {
			var imgList = result.resultData;
			var checksHtml ="";
			if("" != imgList && "undefined" !=imgList ){
				var size = imgList.length;
				for(var i =0; i<size;i++){
					var src = imgList[i].src;
					var checkHtml = '<input type="radio" name="behalf_imageUrl" value="'+src+'" data-bind="checked: behalf_imageUrl" onclick="xjj.cms.article.showImg(\''+src+'\');"';
					if("edit" == editType){
						if(item.behalf_imageUrl == src){							
							checkHtml += ' checked="checked" ';
						}
					}
					checkHtml += "/>";
					var text = "<span>"+imgList[i].title+"</span>";
					checksHtml += (checkHtml+text);
				}
			}
			$("#behalf_imageUrl_td").html(checksHtml);
		} else {
			AlertDialog({
				Title : "提示信息",
				Message : "获取代表性图片失败！",
				Icon : "k-ext-error"
			});
		}
	}
	
	var getImgError = function(result) {
		AlertDialog({
			Title : "提示信息",
			Message : "获取代表性图片失败！",
			Icon : "k-ext-error"
		});
	}
	// Tab单项激活事件
	var onTabActivate = function(e) {
		var TabText = $(e.item).find("> .k-link").text();
		refreshTabContent(TabText);
	}
	// 刷新Tab页内容
	var refreshTabContent = function(TabText) {

	}

	/**
	 * 下拉栏目权限树
	 */
	var selectUserMenu = function() {
		selectMenuTree = CreateDropDownTreeView({
			ID : "menuId_div",
			AutoBind : true,
			DataSource : selectSource,
			TextField : "menuName",
			Height: 400,
			OnSelect : function(e) {
				var dataItem = this.dataItem(e.node);
				var menuId = dataItem.id;
				var menuName = dataItem.menuName;
				item.menuId = menuId;
				item.menuName = menuName;
				$("#menuId").val(menuId);
				$("#menuName").val(menuName);
			}
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
				AlertDialog({
					Title : "提示信息",
					Message : "删除成功！",
					Icon : "k-ext-information"
				});
				grid.dataSource.read();
				loadMenuTree();
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
	}
	
	var showImg = function (url){
		if("" != url  && "undefined" != url && null != url){
			var img = "<img height=\"210\" width=\"260\" src='"+url+"' border='0'  />";
			$("#show_behalfImage").html(img);
		}
	}
	/**
	 * 删除代表性图片
	 */
	var delDehalfImg = function(){
		var $behalfImage =  $("#show_behalfImage img");
		if(null != $behalfImage && "undefined" != $behalfImage){
			$behalfImage.remove();
			item.behalf_imageUrl ="";
			return false;
		}
	}
	
	var getImg = function(){
		var pageContent =  editor.getContent();
		$.ajax({
	            url: path + "/cms/article/getImg.json",
	            type: "POST",
	            data:{
	            	pageContent:pageContent
	            },
	            success: function (result) {
	            	getImgSuccess(result);
	            },
	            error: function (result) {
	            	getImgError(result);
	            }
	        });
    }
	
	var checkSuccess = function(result) {
		if (result.resultCode === 0) {
			if (result.resultMsg == "") {
				var row = grid.select();
				for ( var i = 0; i < row.length; i++) {
					grid.removeRow(row[i]);
				}
				AlertDialog({
					Title : "提示信息",
					Message : "提交审核成功！",
					Icon : "k-ext-information"
				});
				grid.dataSource.read();
				loadMenuTree();
			} else {
				alert(result.resultMsg);
			}
		} else {
			alert(result.resultMsg);
		}
	}

	var checkError = function(result) {
		AlertDialog({
			Title : "提示信息",
			Message : "提交审核失败！",
			Icon : "k-ext-error"
		});
	}
	
	
	var tuiHuiSuccess = function(result) {
		if (result.resultCode === 0) {
			if (result.resultMsg == "") {
				var row = grid.select();
				for ( var i = 0; i < row.length; i++) {
					grid.removeRow(row[i]);
				}
				AlertDialog({
					Title : "提示信息",
					Message : "退回成功！",
					Icon : "k-ext-information"
				});
				grid.dataSource.read();
				loadMenuTree();
			} else {
				alert(result.resultMsg);
			}
		} else {
			alert(result.resultMsg);
		}
	}

	var tuiHuiError = function(result) {
		AlertDialog({
			Title : "提示信息",
			Message : "提交审核失败！",
			Icon : "k-ext-error"
		});
	}
	
	// 删除成功回调函数
	var dCheckSuccess = function(result) {
		if (result.resultCode === 0) {
			if (result.resultMsg == "") {
				var row = grid.select();
				for ( var i = 0; i < row.length; i++) {
					grid.removeRow(row[i]);
				}
				AlertDialog({
					Title : "提示信息",
					Message : "审核成功！",
					Icon : "k-ext-information"
				});
				grid.dataSource.read();
				loadMenuTree();
			} else {
				alert(result.resultMsg);
			}
		} else {
			alert(result.resultMsg);
		}
	}

	// 删除失败回调函数
	var dCheckError = function(result) {
		AlertDialog({
			Title : "提示信息",
			Message : "审核失败！",
			Icon : "k-ext-error"
		});
	}
	//改变代表性图片的宽
	var changeImgWidth = function(){
		var lockTitle = $("#lockImg").attr("title");
		var imgH = $("#image").attr("height");
		var imgW = $("#image").attr("width");

		if(lockTitle == "锁定比例"){
			var width = $("#behalf_image_width").val();
			var height = width*imgH/imgW;
			alert(width);
			$("#behalf_image_height").val(height);
			$("#image").css("width",width+"px");
			$("#image").css("height",height+"px");
		}else{
			var width = $("#behalf_image_width").val();
			$("#image").css("width",width+"px");
		}


	}
	//改变代表性图片的高
	var changeImgHeight = function(){
		var lockTitle = $("#lockImg").attr("title");
		var imgH = $("#image").attr("height");
		var imgW = $("#image").attr("width");
		if(lockTitle == "锁定比例"){
			var height = $("#behalf_image_height").val();
			var width = height*imgW/imgH;
			$("#behalf_image_width").val(width);
			$("#image").css("width",width+"px");
			$("#image").css("height",height+"px");
		}else{
			var height = $("#behalf_image_height").val();
			$("#image").css("height",height+"px");
		}
	}
	
	
	return {
		history_listPage : history_listPage,
		init_checkPage : init_checkPage,
		init_listPage : init_listPage,
		init_editPage : init_editPage,
		tuiHuiSuccess : tuiHuiSuccess,
		tuiHuiError : tuiHuiError,
		deleteSuccess : deleteSuccess,
		deleteError : deleteError,
		checkSuccess : checkSuccess,
		checkError : checkError,
		dCheckSuccess : dCheckSuccess,
		dCheckError : dCheckError,
		showImg:showImg,
		delDehalfImg:delDehalfImg,
		getImg:getImg,
		changeImgWidth:changeImgWidth,
		changeImgHeight:changeImgHeight
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
}

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
		AjaxDoPost(path + "/cms/article/delete.json", "models="
				+ JSON.stringify(data.id), true, xjj.cms.article.deleteSuccess,
				xjj.cms.article.deleteError);
	}
}

//审核初始化
var onCheck = function() {
	var row = grid.select();
	var data = grid.dataItem(row);
	if (data == null) {
		AlertDialog({
			Title : "提示信息",
			Message : "请选择您要提交审核的数据！",
			Icon : "k-ext-warning"
		});
		return false;
	} else {
		return true;
	}
}

var check = function(){
	var row = grid.select();
	var datas = new Array();
	for ( var i = 0; i < row.length; i++) {
		var data = grid.dataItem(row[i]);
		datas.push(data);
	}
	if (typeof (data) != undefined) {
		AjaxDoPost(path + "/cms/article/check.json", "models="
				+ JSON.stringify(data.id), true, xjj.cms.article.checkSuccess,
				xjj.cms.article.checkError);
	}
}

var onDCheck = function() {
	var row = grid.select();
	var data = grid.dataItem(row);
	if (data == null) {
		AlertDialog({
			Title : "提示信息",
			Message : "请选择您要审核的数据！",
			Icon : "k-ext-warning"
		});
		return false;
	} else {
		return true;
	}
}

var onTuiHui = function(){
	var row = grid.select();
	var data = grid.dataItem(row);
	if (data == null) {
		AlertDialog({
			Title : "提示信息",
			Message : "请选择您要退回的数据！",
			Icon : "k-ext-warning"
		});
		return false;
	} else {
		return true;
	}
}

var tuiHui = function(){
	var row = grid.select();
	var datas = new Array();
	for ( var i = 0; i < row.length; i++) {
		var data = grid.dataItem(row[i]);
		datas.push(data);
	}
	if (typeof (data) != undefined) {
		AjaxDoPost(path + "/cms/article/tuiHui.json", "models="
				+ JSON.stringify(data.id), true, xjj.cms.article.tuiHuiSuccess,
				xjj.cms.article.tuiHuiError);
	}
}

var onCdDel = function(){
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

var cdDel = function(){
	var row = grid.select();
	var datas = new Array();
	for ( var i = 0; i < row.length; i++) {
		var data = grid.dataItem(row[i]);
		datas.push(data);
	}
	if (typeof (data) != undefined) {
		AjaxDoPost(path + "/cms/article/cdDel.json", "models="
				+ JSON.stringify(data.id), true, xjj.cms.article.deleteSuccess,
				xjj.cms.article.deleteError);
	}
}
//确认对话框
function ConfirmYesOrNoDialog(Options) {
  return kendo.ui.ExtYesOrNoDialog.show({ 
  	title: Options.Title,
      message: Options.Message,
      icon: Options.Icon,
      Width: Options.Width,
      Height: Options.Height
	   });
}

var dCheck = function(){
	ConfirmYesOrNoDialog({
        Title: "提示信息",
        Message: "请选择审批意见！",
        Icon: "k-ext-question"
    }).done(function (response) {
        if (response.button == "Yes"){
        	dYesCheck();
        }else{
        	dNotCheck();
        }
    });
}

var dYesCheck = function(){
	var row = grid.select();
	var datas = new Array();
	for ( var i = 0; i < row.length; i++) {
		var data = grid.dataItem(row[i]);
		datas.push(data);
	}
	if (typeof (data) != undefined) {
		AjaxDoPost(path + "/cms/article/dCheck.json", "models="
				+ JSON.stringify(data.id), true, xjj.cms.article.dCheckSuccess,
				xjj.cms.article.dCheckError);
	}
}

var dNotCheck = function(){
	var row = grid.select();
	var datas = new Array();
	for ( var i = 0; i < row.length; i++) {
		var data = grid.dataItem(row[i]);
		datas.push(data);
	}
	if (typeof (data) != undefined) {
		AjaxDoPost(path + "/cms/article/dNotCheck.json", "models="
				+ JSON.stringify(data.id), true, xjj.cms.article.dCheckSuccess,
				xjj.cms.article.dCheckError);
	}
}

var openComment = function(articleId){
	 newEvent(path + "/pages/article/article_comment.jsp?articleId="+articleId, "whole1","查看评论");
	 createevent_window.open();
}

//获取
var showWorkLog = function(divId,fyId,url,par){
	var posturl = url + par;
	$.ajax({
        url: posturl,
        type: "POST",
        async: true,
        dataType: "json",
        data: {
        },
        beforeSend:function(){
        	$("#showMes").html('loading...');
        },
        success: function (result) {
        	if(result!=null && result!=""){
		  		$("#"+divId).html("");
	        	$("#"+fyId).html("");
				if(result !=null){
					var item = result.resultData.items;
					var html = "";
					$("#"+divId).append(html);
					for(var i=0 ; i < item.length ; i++){
						if(i%2==0){
							html += '<tr><td width="80" align="center"><table class="rl"  border="0" cellspacing="0" cellpadding="0">';
						}else{
							html += '<tr><td width="80" bgcolor="f5f8fa" align="center"><table class="rl"  border="0" cellspacing="0" cellpadding="0">';
						}
						var comment_time = item[i].comment_time;
						var monuth = "";
						if(comment_time.substring(5,6) == "0"){
							monuth = comment_time.substring(6,7)+"月";
						}else{
							monuth = comment_time.substring(5,7);
						}
						var day = comment_time.substring(8,10) + "日";
						html += '<tr>'
	                         +'<td height="25" style="margin-top:3px;">'+monuth+'</td>'
	                         +'</tr>'
	                         + '<tr>'
	                         +'<td class="rlday">'+day+'</td>'
	                         +'</tr>'
	                         +'</table></td>';
	                    if(i%2==0){
	     					html += '<td>';
	     				}else{
	     					html += '<td bgcolor="f5f8fa">';
	     				}     
	                    html += '<div class="titlecss">'+item[i].comment_name+'<span class="cz">';
	     				html +='</span></div>';
	     				var wlc = item[i].comment_content;
	     				if(wlc.length>300){
	     					wlc = wlc.substring(0,300)+"..."+"<img id='' class='up_and_down' src='../images/dx01.gif'/>";
	     				}
	     				html +='<div class="comlog">'+wlc+'</div>';
	                    html += '<div style="display:none;" id="com'+item[i].id+'"><div id="comment'+item[i].id+'">';
	                    html +='</div><br/>'
	                    +'</td>'	
	                    +'</tr>';
					}
					$("#"+divId).append(html);	
					$("#showMes").html('');
				}
		  	}else{
		  	}
        },
        error: function (result) {
            alert("查询出错");
        }
    });
}
function newEvent(url, winName,title) {
    createevent_window = $("<div id=" + winName + " style='padding:0;'/>").kendoWindow({
          title: title,
          modal: true,
          activate: function () {
              windowOpen(winName);
          },
          deactivate: function () {
              windowCLose(winName);
              this.destroy();
          },
          content: url
      }).data("kendoWindow");
    if(winName == "menu"){
        $(".k-window").css({"left":"456px","top": "20%","width": "380px","height": "450px"});
    }else if(winName == "addlink"){
        $(".k-window").css({"left":"300px","top": "20%","width": "600px","height": "200px"});
    }else if(winName == "article"){
    	$(".k-window").css({"left":"50px","top": "20%","width": "90%","height": "90%"});
    }else{
   		createevent_window.maximize();
    }
}
//窗体关闭时
function windowCLose(winName) {
    return true;
}
//窗体打开时
function windowOpen(winName) {
    return true;
}

var showArticle = function(title,wzly,fbsj,neriong,url){
	$.ajax({
        url: url,
        type: "POST",
        async: false,
        dataType: "json",
        data: {
        },
        success: function (result) {
        	if(result!=null && result!=""){
        		var article = result.resultData;
        		$("#"+title).html(article.title);
        		$("#"+wzly).html(article.source);
        		$("#"+fbsj).html(article.publicTime);
        		$("#"+neriong).html(article.content);
        	}
        },
        error: function (result) {
            alert("查询出错");
        }
    });
}

function showDetil(){
	openwindow(path + "/pages/article/article_detail.jsp" ,"","","edit","div","detaildiv");
}

function Top(articleId){
	$.ajax({
        url: path + "/cms/article/top.json",
        type: "POST",
        async: false,
        dataType: "json",
        data: {"articleId":articleId},
        success: function (result) {
        	if(result.resultCode === 0){
        		AlertDialog({
					Title : "提示信息",
					Message : "置顶成功！",
					Icon : "k-ext-information"
				});
        		grid.dataSource.read();
        	}
        },
        error: function (result) {
            alert("置顶出错");
            return;
        }
    });
}
function cancleTop(articleId){
	$.ajax({
        url: path + "/cms/article/cancleTop.json",
        type: "POST",
        async: false,
        dataType: "json",
        data: {"articleId":articleId},
        success: function (result) {
        	if(result.resultCode === 0){
        		AlertDialog({
					Title : "提示信息",
					Message : "取消置顶成功！",
					Icon : "k-ext-information"
				});
        		grid.dataSource.read();
        	}
        },
        error: function (result) {
            alert("置顶出错");
            return;
        }
    });
}
//栏目复制初始化
var onArticleTJ = function () {
     var row = grid.select();
     var data = grid.dataItem(row);
     if (data == null) {
     	AlertDialog({
             Title: "提示信息",
             Message: "请选择您要推荐的文章！",
             Icon: "k-ext-warning"
         });
         return false;
     }
     else {
         return true;
     }
 }

var articleTJ = function () {
    var row = grid.select();
    var copyMenuId = null;
    var copySelectSource = null;
 	var selectCopyMenuTree = null;
    var datas = new Array();
    for (var i = 0; i < row.length; i++) {
        var data = grid.dataItem(row[i]);
        datas.push(data);
    }
    var dialog = art.dialog({
   	    width:500,
   		id: 'copyMenu',
   	    title: '文章推荐',
   	    content: "<table><tr width='500px'><td width='150px'>目标栏目：</td><td width='350px'><div id='copyMenuTree' width='100%'></div></td></tr></table>",
   	    lock: true,
   	    fixed: true,
   	    esc : false,
   	    drag: false,
   	    resize: false,
   	    okVal: '推荐',
   	    ok: function () {
   	    	var articleId = data.id;
   	    	if(copyMenuId == null){
   	    		copyMenuId = 0;
   	    	}
   	    	$.ajax({
   	    		url : path+"/cms/article/articleTJ.json",
   	    		async : false,
   	    		type : "POST",
   	    		data : {"copyMenuId" : copyMenuId,"articleId" : articleId},
   	    		success : function (e){
   	    			if(e.resultCode === 0){
   	    				AlertDialog({
   	    		             Title: "提示信息",
   	    		             Message: "推荐成功！",
   	    		             Icon: "k-ext-information"
   	    		         });
   	    				grid.dataSource.read();
   	    				return false;
   	    			}
   	    		},
   	    		error : function(e){
   	    			alert("推荐失败!");
   	    			return false;
   	    		}
   	    	});
   	    },
   	    cancelVal : '取消',
   	    cancel : true,
   	    init : function(){
   	    	JsonPostForWaiting({
   	            Url: path+"/cms/menu/alltreeList.json",
   	            WaittingText: "正在加载栏目...",
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

   	                    copySelectSource = new kendo.data.HierarchicalDataSource({
   	                        data: ParentTree,
   	                        schema: {
   	                            model: {
   	                                children: "ChildLists"
   	                            }
   	                        }
   	                    });
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
   	    	
   	    	selectCopyMenuTree = CreateDropDownTreeView({
			        ID: "copyMenuTree",
			        AutoBind: true,
			        DataSource: copySelectSource,
			        TextField: "menuName",
			        Height: 200,
			        OnSelect: function (e) {
			            var dataItem = this.dataItem(e.node);
			            copyMenuId = dataItem.id;
			        }
			    });
   	    	selectCopyMenuTree.selectItem("栏目列表");
   	    }
    });
}