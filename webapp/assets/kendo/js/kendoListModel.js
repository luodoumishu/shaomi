// 列表对象,用于对表格数据的操作
   var grid = null;

   // 数据对象，用于新增和修改
   var Item = null;
   
   //数据源对象
   var dataSource =null;
   
   
var kendoList = function(goSource){
	
	
	var kendoListui = '<div id="form-container">'+
										' <div class="search_div" id="DivQueryInput">'+
										' <div class="even" id="qxsz">&nbsp;'+		
										' <input id="qsearch" type="button" class="k-button" value=" 查 询 " /> &nbsp;'+
										' <input id="clear" type="button" class="k-button" value=" 重 置 " />'+
										' </div>'+
										' </div>'+
										' <div id="QueryGrid"></div>'+
										' <div id="grid"></div>'+
										' </div>';

	//' 姓名：<input id="name" name="name" class="k-textbox" /> &nbsp;账号：'+
	//' <input id="account" name="account" class="k-textbox" /> &nbsp;'+
	
   // kendoListui对象
   var listMain = $(kendoListui);
	
   //查询对象
   var qObjet ;
	
   

	
   // 工具栏对象
   this.BL;

   
   var toolObj =goSource.toolObj;
   var delUrl = goSource.delUrl;
   
   // 验证数组值是否正确的传入按钮值
   var ifan= function(toolObj){
	   var tool="Add|Edit|Del|Copy|ReName|Filter|Export|Import";//自定义的按钮
		var b = true;
		for(i=0;i<toolObj.length;i++){
			if(tool.indexOf(toolObj[i]) < 0){
				b = false;				
				return b;
			}
		}
		return b;
	};
	
	//查询条件栏
	var getcxObj = function(){
		var qxl="";
		$.each(goSource.cxunObj,function(i, cdmx) {		 
			qxl += cdmx.title+':'+
			 	 '<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" class="k-textbox" /> &nbsp;'
		 });
		return qxl;
	};
	//查询条件栏2
	var getcxObjfz = function(){
		var qxl="";
		$.each(goSource.cxunObj,function(i, cdmx) {	
			
			if('style' in cdmx){
				if('type' in cdmx){
					if(cdmx.type !="" && cdmx.type !="text"){
						if(cdmx.type == "kendoDropdownList"){
							qxl += cdmx.title+':'+
				 				'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'"'+ cdmx.style+'data-bind="value: '+cdmx.cxkey+'"/> &nbsp;'
						}else{
							qxl += cdmx.title+':'+
					 			'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" data-role="'+cdmx.type+'"'+cdmx.style+'"/> &nbsp;'	
						}
					}else{
						qxl += cdmx.title+':'+
				 			'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" class="k-textbox"'+cdmx.style+'" /> &nbsp;'
					}
				}else{
					qxl += cdmx.title+':'+
		 			'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" class="k-textbox"'+cdmx.style+'" /> &nbsp;'
				}
				
			}else{
				if('type' in cdmx){
					if(cdmx.type !="" && cdmx.type !="text"){
						if(cdmx.type == "kendoDropdownList"){
							qxl += cdmx.title+':'+
				 				 '<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'"data-bind="value: '+cdmx.cxkey+'"/> &nbsp;'
						}else{
							qxl += cdmx.title+':'+
					 			'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" data-role="'+cdmx.type+'"/> &nbsp;'	
						}
					}else{
						qxl += cdmx.title+':'+
				 			'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" class="k-textbox" /> &nbsp;'
					}
				}else{
					qxl += cdmx.title+':'+
		 				'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" class="k-textbox" /> &nbsp;'
				}
				
			}

			
			/**
			 * if('type' in cdmx){				
				if(cdmx.type !="" && cdmx.type !="text"){
					if('style' in cdmx){
						qxl += cdmx.title+':'+
					 		'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" data-role="'+cdmx.type+'"'+cdmx.style+'"/> &nbsp;'	
					}else{
						qxl += cdmx.title+':'+
				 		'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" data-role="'+cdmx.type+'"/> &nbsp;'	
					}
								
				}else{
					if('style' in cdmx){
						qxl += cdmx.title+':'+
					 		'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" class="k-textbox"'+cdmx.style+'" /> &nbsp;'
					}else{
						qxl += cdmx.title+':'+
					 		'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" class="k-textbox" /> &nbsp;'
					}
				}				
			}else{
				if('style' in cdmx){
					qxl += cdmx.title+':'+
				 		'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" class="k-textbox"'+cdmx.style+'" /> &nbsp;'
				}else{
					qxl += cdmx.title+':'+
				 		'<input id="'+cdmx.cxkey+'" name="'+cdmx.cxkey+'" class="k-textbox" /> &nbsp;'
				}				
			}
			 */
			
			
		});
		return qxl;
	};
	
	
	//初始化
	var initqxsz = function(){
		listMain.find("#qxsz").prepend(getcxObjfz());		
		kendo.init(listMain.find("#qxsz"));	
	};
	
	//是否有复杂组件初始化
	var initOtherButton = function(){
	//下拉组件
		if('otherDropListObj' in goSource && goSource.otherDropListObj ==true){
			$.each(goSource.otherDropList,function(i, cdmx) {	
				if(cdmx.lx == '1'){
					var dropListDataSource = new kendo.data.DataSource(
							{
								transport : {
									read : {
										url : cdmx.url,
										dataType : "json"
									}
								},
								schema : {
									//判断是否错误
									errors : function(response) {
										if (response.resultCode != 0) {
											return response.resultMsg;
										} else {
											return null;
										}
									},
									//获取数据
									data : function(response) {
										return response.resultData;
									},
									//获取数据条数
									total : function(response) {
										// total is returned in the "total" field of the response,需要设置serverPaging: true
										return response.resultData.total;
									}
								}
							});

							listMain.find("#"+cdmx.id).kendoDropDownList({
								dataTextField : cdmx.key,
								dataValueField : cdmx.value,
								dataSource : dropListDataSource
					
							});
				}else{
					listMain.find("#"+cdmx.id).kendoDropDownList({
						dataTextField : cdmx.value,
						dataValueField : cdmx.key,
						dataSource : cdmx.dataSource
			
					});
				}
				
			});
		}
		//其他组件 如autocomplete等
	};
	
	//构造查询条件对象
	var getQueryObj = function(){
		var queryObj ='{';
		$.each(goSource.cxunObj,function(i, cdmx) {		 
			if(i<goSource.cxunObj.length-1){
				queryObj += '"'+cdmx.cxkey+'":"'+
			 	 	listMain.find("#"+cdmx.cxkey).val()+'",';
			}else{
				queryObj += '"'+cdmx.cxkey+'":"'+
					listMain.find("#"+cdmx.cxkey).val()+'"';
			}
			
		 });
		queryObj+='}';
		qObjet =queryObj;
		return qObjet;
	};
	
	
	//先转成字符后再把字符转成对象
	var toolButtonObj = '{'+
    					'GridId: "grid",'+
    					'Url:"'+goSource.editUrl+'",'+
    					'Width: 380,'+
    					'Height: 280,';
	
	var toolButtonObjAft = ' }';
	//工具栏字符串
	var getToolButtonObj=function(toolButtonObj,toolButtonObjAft){
		$.each(goSource.toolObj,function(i, cdmx) {
			 if(i<goSource.toolObj.length-1){
				 toolButtonObj +=toolObj[i]+': true,';
			 }else{
				 toolButtonObj +=toolObj[i]+': true';
			 }
		});
		toolButtonObj += toolButtonObjAft;
		return toolButtonObj;
	};

	// 创建工具栏按键
	var createToolButton = function(){
		buttondata=getToolButtonObj(toolButtonObj,toolButtonObjAft);
		//var buttondata=getButtonObj();
		if(ifan(toolObj)){
			BL=CreateToolBarButton(
					//使用String还是要转成objct?
					//eval('('+data+')')
					buttondata
			);
			return BL;
		}else{
			alert('导入的工具栏按钮不符合配置固定，请确认并修改!工具栏按键为'+toolObj.join("|||"));
			return false;
		}
	};
	//根据条件创建工作栏
	var CreateToolBarButton =function (Options) {
		var COptions = eval('('+Options+')');
	    this.Str = "";
	    //新增
	    if (COptions.Add != null && COptions.Add) {
	        this.Str = "<a title='新增' class='k-button' id='AddBtn' style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"openwindow('" + COptions.Url + "' ," + COptions.Width + "," + COptions.Height + ",'add','div');\">新增</a>";
	    }
	    if (COptions.Copy != null && COptions.Copy) {
	        this.Str += "<a title='复制' class='k-button' id='CopyBtn' style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"openwindow('" + COptions.Url + "' ," + COptions.Width + "," + COptions.Height + ",'Copy','div');\">复制</a>";
	    }
	    //修改
	    if (COptions.ReName != null && COptions.ReName) {
	        this.Str += "<a title='改名' class='k-button' id='ReNameBtn'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"openwindow('" + COptions.Url + "' ," + COptions.Width + "," + COptions.Height + ",'ReName','div');\">改名</a>";
	    }
	    //修改
	    if (COptions.Edit != null && COptions.Edit) {
	        this.Str += "<a title='修改' class='k-button' id='UpdateBtn'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"openwindow('" + COptions.Url + "' ," + COptions.Width + "," + COptions.Height + ",'edit','div');\">修改</a>";
	    }
	    if (COptions.Del != null && COptions.Del) {
	        this.Str += "<a title='删除' class='k-button' id='DelBtn'  style='margin-right:4px;' href=\"\\javascript:void(0);\" onclick=\"DelConfirmation('"+delUrl+"');\">删除</a>";
	    }
	    if (COptions.Filter != null && COptions.Filter) {
	        this.Str += "<a title='高级查询' class='k-button'  id='SearchBtn' style='margin-right:4px;' href=\"\\javascript:void(0);\" onclick=\"onSearch('" + COptions.GridId + "');\">高级查询</a>";
	    }
	    if (COptions.Export != null && COptions.Export) {
	        this.Str += "<a title='导出' class='k-button'  id='ExportBtn' style='margin-right:4px;' href=\"\\javascript:void(0);\" onclick=\"onExport('" + COptions.GridId + "','" + COptions.Export.Title + "');\">导出</a>";
	    }
	    if (COptions.Import != null && COptions.Import) {
	        OnImportCallback = COptions.Import.ImportCallback;
	        this.Str += "<a title='导入' class='k-button'  id='ImportBtn' style='margin-right:4px;' href=\"\\javascript:void(0);\" onclick=\"onImport('" + COptions.GridId + "');\")>导入</a>";
	    }
	    return this.Str;
	};
	
	// 创建数据源
	var kendoDataSource = function(){
		var qObjet=getQueryObj();
		dataSource = new kendo.data.DataSource({
			transport : {
				read : {
					type : "POST",
					dataType : "json",
					contentType : "application/x-www-form-urlencoded",
					url : goSource.listUrl
				},
				parameterMap : function(data, type) {
					var queryObj = eval('('+qObjet+')');
					var filters = {
						// 跳过多少条数据(即查询的开始下标),必须属性
						skip : data.skip,
						// 每页数据条数,必须属性
						pageSize : data.pageSize,
						// 查询条件，可选属性
						queryObj : queryObj
					};
					return {
						filters : kendo.stringify(filters)
					};
				}
			},
			batch : true, // Boolean(default:
							// false),设置为true，批量处理curd操作,更新两条数据发一次http请求
			serverPaging : true, // Boolean(default: false),服务器端实现分页
			page : 1, // 当前页是第几页
			pageSize : 20, // 每页显示多少条数据
			schema : { // Object,解析服务器响应
				type : "json", // String(default: "json")
				data : function(response) { // Function|String
					return response.resultData.items;
				},
				total : function(response) {
					return response.resultData.total; // total is returned in
														// the "total" field of
														// the
														// response,需要设置serverPaging:
														// true
				},
				errors : function(response) {
					// alert(response.resultMsg);
					// return "查询出错";
					return response.error; // Function|String(default:
											// "errors"),服务器返回的格式为{ "error":
											// "错误提示信息" }
				}
			},
			error : function(e) {
				alert("查询出错"); // displays "Invalid query"
			}
		});
	};
	
	
	//获得列配置字符
	var getColumn = function(){
		var colu="[ ";		
		$.each(goSource.cdl,function(i, cdmx) {
					 if(i<goSource.cdl.length-1){
						colu +='{'+
								'field :"'+ cdmx.lmm+'",'+
								'width : 150,'+
								'filterable : true,'+
								'sortable : true,'+
								'title :"'+ cdmx.lmtitle+'"'+
							'},';
					 }else{
						colu+=' {'+
								'field :"'+ cdmx.lmm+'",'+
								'width : 150,'+
								'filterable : true,'+
								'sortable : true,'+
								'title :"'+ cdmx.lmtitle+'"'+
							'}';
					 }
					 
				 });
		colu +=']';
		return colu;
	};
	
	// 数据窗口
	dataGrid = function(BL){
		var colu = getColumn();
		var coludata =eval('('+colu+')');
		listMain.find("#grid").kendoGrid({
			dataSource : dataSource,
			pageable : { // Boolean|Object(default: false)
				refresh : true,
				pageSizes : [ 20, 50, 100 ]
			},
			height : 530, // Number|String,数字代表多少px
			toolbar : [ {
				template : BL
			} ],// Array,在工具栏上添加新增按钮，具体可以查看kendo.web.js的defaultCommands
			autoBind : true, // Boolean(default:
								// true)，绑定数据模型,如果有多个请求可以对一个模型进行修改，设置为false
			sortable : true, // Boolean|Object(default: false),表格数据排序
			reorderable : true, // Boolean(default:false)，拖拽表格列顺序
			resizable : true, // Boolean(default:false)，拖拽表格列宽
			columnMenu : true, // Boolean|Object(default: false),是否显示列菜单
			filterable : true, // Boolean|Object(default:
								// false),表格数据筛选，使用前提是columnMenu设置为true
			selectable : true, // Boolean|String(default:
								// false),可以设置"row"、"cell"、"multiple,
								// row"、"multiple, cell"
			groupable : true, // Boolean|Object(default: false),表格分组
			editable : "popup", // Boolean|Object(default: false),"inline", "incell"
								// or "popup"
			columns : coludata
		});
		// 列表对象获取
		grid = listMain.find("#grid").data("kendoGrid");
	};
	
	
	
	
	//快速查询
	this.qSearch = function(){
		//初始化dataSrouce
		kendoDataSource();
		//初始化数据窗口
		dataGrid(BL);		
		
		var Search = grid.dataSource;
		Search.page(1);
	};
	
	//重置
	this.clear = function(){
		$.each(goSource.cxunObj,function(i, cdmx) {		 
			listMain.find("#"+cdmx.cxkey).val("");
		 });
	};
	

	
	


	
	this.kendoListInit = function (yuansuID){
		//初始化list
		initqxsz();
		//初始化复杂组件
		initOtherButton();
		//初始化工具栏按键
		BL=createToolButton();
		//初始化dataSrouce
		kendoDataSource();
		//初始化数据窗口
		dataGrid(BL);		
		$("#"+yuansuID).append(listMain);
	};
			
}




//新增初始化
onAdd = function(){
	Item = new Object;
	//编辑的类型：新增
	EditType = "add";
	//可以在这里进行新增页面的初始化值设置，例如
	//Item.name = "";
	return true;
};

//修改初始化
onUpdate = function() {
	Item = new Object;
	var row = grid.select();
	selectRowIndex = row.index();
	Item = grid.dataItem(row);
	//编辑的类型：修改
	EditType = "edit";
	if (Item == null) {
		alert("请选择您要编辑的数据！");
		return false;
	} else {
		return true;
	}
};

//删除
function DelConfirmation(delUrl) {
    if (onDelete()) {
        var answer = confirm("是否需要删除选中记录？");
        if (answer) {
            del(delUrl);
        }
        else {
            return;
        }
    }
}
//删除初始化
onDelete = function() {
	Item = new Object;
	var row = grid.select();
	var data = grid.dataItem(row);
	if (data == null) {
		alert("请选择您要删除的数据！");
		return false;
	} else {
		return true;
	}
};

del = function(delUrl) {
	var row = grid.select();
	var datas = new Array();
	for ( var i = 0; i < row.length; i++) {
		var data = grid.dataItem(row[i]);
		datas.push(data);
	}
	if (typeof (data) != undefined) {
		AjaxDoPost(delUrl, "models="
				+ JSON.stringify(data), true, deleteSuccess, deleteError);
	}
};

//删除成功回调函数
function deleteSuccess(result) {
	if (result.resultCode === 0) {
		var row = grid.select();
		for ( var i = 0; i < row.length; i++) {
			grid.removeRow(row[i]);
		}
		alert(del_succeed);
	} else {
		alert(result.resultMsg);
	}
};

//删除失败回调函数
function deleteError(result) {
	alert(del_fail);
};