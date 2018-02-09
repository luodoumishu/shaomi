﻿//数据源
function CreateGridDataSource(Options) {
    var Read = null;

    if (Options.Transport.ReadUrl != null && Options.Transport.ReadUrl.length > 0) {
        Read = {
            type: "POST",
            dataType: "json",
            contentType: "application/x-www-form-urlencoded",
            url: Options.Transport.ReadUrl,
            data: function () {
                var Data = { queryObj: null };
                if (Options.Transport.ReadDataFunc != null) {
                    Data.queryObj = Options.Transport.ReadDataFunc();
                }
                return Data;
            }
        };
    }
    
    //Boolean(default: false),服务器端实现分页
    var ServerPaging = false;
    if (Options.ServerPaging === undefined) {
        ServerPaging = true;
    } else {
        ServerPaging = Options.ServerPaging;
    }
    
    //当前页是第几页
    var Page = null;
    if (Options.Page === undefined) {
        Page = 1;
    } else {
        Page = Options.Page;
    }

    //每页显示多少条数据
    var PageSize = null;
    if (Options.PageSize === undefined) {
        PageSize = 20;
    } else {
        PageSize = Options.PageSize;
    }

    //数据窗口数据源
    var GridDataSource = new kendo.data.DataSource({
        serverPaging: ServerPaging,
        page: Page,
        pageSize: PageSize,
        transport: {
            read: Read,
            parameterMap: function(data, type) {
          	      var filters = {
                	    //跳过多少条数据(即查询的开始下标),必须属性
          			    skip: data.skip,
                	    //每页数据条数,必须属性
                        pageSize: data.pageSize,
                        //查询条件，可选属性
                        queryObj: data.queryObj
                    };
                	 return {filters: kendo.stringify(filters)};
              }
        },
        aggregate:Options.Aggregate=== undefined ? false : Options.Aggregate,
        //Object,解析服务器响应
        schema: {       
            //判断是否错误
            errors: function (response) {
                if (response.resultCode != 0) {
                    return response.resultMsg;
                }
                else {
                    return null;
                }
            },
            //获取数据
            data: function (response) {
            	 return response.resultData.items;
            },
            //获取数据条数
            total: function (response) {
            	// total is returned in the "total" field of the response,需要设置serverPaging: true
            	return response.resultData.total;   
            }
        },
        //请求失败
        error: function (e) {
        	if (e.errors != undefined){
        		alert(e.errors); 
        	}else{
        		alert("查询出错！");
        	}
        }
    });

    return GridDataSource;
}

//创建数据窗口
function CreateGrid(Options) {
    var Template = null;
    if (Options.ToolBarTemplate != null) {
        if (Options.ToolBarTemplate.length > 0) {
            Template = [{ template: Options.ToolBarTemplate }];
        }
    }

    var Pageable = null;
    if (Options.Pageable === undefined || Options.Pageable == true) {
        Pageable = {
            pageSizes: [20, 50, 100],
            refresh: true
        };
    } else {
            Pageable = Options.Pageable;
    }
    
    var Grid = $("#" + Options.ID + "").kendoGrid({
        dataSource: Options.DataSource,
        //Boolean|Object(default: false),表格数据排序
        sortable: Options.Sortable === undefined ? true : Options.Sortable,
        //Boolean(default:false)，拖拽表格列顺序
        reorderable: Options.Reorderable === undefined ? true : Options.Reorderable,
        //Boolean(default:false)，拖拽表格列宽
        resizable: Options.Resizable === undefined ? true : Options.Resizable,
        //Boolean|Object(default: false),是否显示列菜单		
        columnMenu: Options.ColumnMenu === undefined ? true : Options.ColumnMenu,
        //Boolean|Object(default: false),表格数据筛选，使用前提是columnMenu设置为true
        filterable: Options.Filterable === undefined ? true : Options.Filterable,
        //Boolean|String(default: false),可以设置"row"、"cell"、"multiple, row"、"multiple, cell"		
        selectable: Options.Selectable === undefined ? true : Options.Selectable,
        //Boolean|Object(default: false),表格分组		
        groupable: Options.Groupable === undefined ? true : Options.Groupable,
        detailInit:Options.detailInit === undefined ? undefined : Options.detailInit,
        height: Options.Height,
        //Boolean|Object(default: false)
        pageable: Pageable,
        //Boolean|Object(default: false),"inline", "incell" or "popup"
        editable: Options.Editable,
        //Array,在工具栏上添加新增按钮，具体可以查看kendo.web.js的defaultCommands
        toolbar: Template,
        columns: Options.Columns
    }).data("kendoGrid");
    return Grid;
}

//弹出详细对话框
function DetailAlertDialog(Options) {
    return kendo.ui.ExtDetailAlertDialog.show({
        title: Options.Title,
        message: Options.Message,
        detailMessage: Options.DetailMessage,
        icon: Options.Icon
    });
}

//弹出对话框
function AlertDialog(Options) {
    return kendo.ui.ExtAlertDialog.show({
        title: Options.Title,
        message: Options.Message,
        icon: Options.Icon
    });
}

//确认对话框
function ConfirmDialog(Options) {
    return kendo.ui.ExtOkCancelDialog.show({ 
    	title: Options.Title,
        message: Options.Message,
        icon: Options.Icon,
        Width: Options.Width,
        Height: Options.Height
	   });
}

//创建下拉树形控件
function CreateDropDownTreeView(Options) {
    var DropDownTreeView = $("#" + Options.ID + "").kendoExtDropDownTreeView({
    	
        treeview: {
            autoBind: Options.AutoBind == null ? false : Options.AutoBind,
            dataSource: Options.DataSource,
            dataTextField: Options.TextField,
            select: Options.OnSelect,
            width: Options.Width,
            height: Options.Height,
            dataBound: function (e) {
                var IsExpand = false;
                if (Options.IsExpand != null) {
                    IsExpand = Options.IsExpand;
                }
                if (IsExpand) {
                    this.expand(".k-item");
                }
                if (Options.OnDataBound != null) {
                    Options.OnDataBound(e);
                }
            }
        }
    }).data("kendoExtDropDownTreeView");

    return DropDownTreeView;
}

//初始化时间
var nowTime = new Date();
function initTime(id,qf){
	var Obj = null;
	if(qf !="" && qf == "start"){
		Obj=$("#"+id).kendoDatePicker({
			format:"yyyy-MM-dd",
			value:getFirstDay(nowTime.getFullYear(),nowTime.getMonth()+1)					
		}).data("kendoDatePicker");
	}else if(qf !="" && qf == "end"){
		Obj=$("#"+id).kendoDatePicker({
			format:"yyyy-MM-dd",
			value:getLastDay(nowTime.getFullYear(),nowTime.getMonth()+1)
		}).data("kendoDatePicker");
	}else if(qf !="" && qf == "now"){
		Obj=$("#"+id).kendoDatePicker({
			format:"yyyy-MM-dd",
			value:nowTime
		}).data("kendoDatePicker");			
	}else if(qf !="" && qf == "year"){
		Obj=$("#"+id).kendoDatePicker({
			value: new Date(),
		    depth: "decade",
		    start: "decade",
		    format: "yyyy"
		}).data("kendoDatePicker");	
	}else if(qf !="" && qf == "nowtime"){
		Obj=$("#"+id).kendoDatePicker({
			format:"yyyy-MM-dd HH:mm:ss",
			value:nowTime
		}).data("kendoDatePicker");			
	}else{
		Obj=$("#"+id).kendoDatePicker({
			format:"yyyy-MM-dd",
			value:qf
		}).data("kendoDatePicker");	
	}
	return Obj;
	
}
		

function getFirstDay(year, month) {
		 return new Date(year, month - 1, 1); //获取当年当月中的第一天    
	}
	
function getLastDay(year, month) {
var new_date = new Date(year, month, 1);                //获取当年下个月中的第一天        
return new Date(new_date.getTime() - 1000 * 60 * 60 * 24);//获取当月最后一天日期        
}

//创建机构多选下拉框
function createOrgMultiSelect(Id,ReadUrl){
	$("#"+Id).kendoMultiSelect({
        dataTextField: "name",
        dataValueField: "id",
        autoClose : false,
        pinyin:true,
        dataSource: {
            transport: {
                read: {
                    url: ReadUrl
                }
            }
        },
        delay: 500,//大数据需要调高一些
        height: 260,
        toolbar:{show: true ,showText: true},//默认值:false 显示工具按钮条
        open: function (e) { this.list.width(240); }
    });
}

//解析机构下拉多选框的text和value
function pareOrgSelect(selectItem){
	var arr = new Array();
	var text = "";
	var value = "";
	if(typeof (selectItem) != undefined){
		for(var i = 0;i < selectItem.length;i++){
			text += selectItem[i].name+",";
			value += selectItem[i].id+",";
		}
	}
	text = text.substring(0, text.length-1);
	value = value.substring(0, value.length-1);
	arr.push(text);
	arr.push(value);
	return arr;
}

//创建用户多选下拉框
function createUserMultiSelect(Id,ReadUrl){
	$("#"+Id).kendoMultiSelect({
        dataTextField: "showName",
        dataValueField: "id",
        itemTemplate:"#= showName#",
        tagTemplate: "#: name #",
        autoClose : false,
        pinyin:true,
        dataSource: {
            transport: {
                read: {
                    url: ReadUrl
                }
            }
        },
        delay: 500,//大数据需要调高一些
        height: 260,
        toolbar:{show: true ,showText: true},//默认值:false 显示工具按钮条
        open: function (e) { this.list.width(240); }
    }); 
}

//解析用户下拉多选框的text和value
function pareUserSelect(selectItem){
	var arr = new Array();
	var text = "";
	var value = "";
	if(typeof (selectItem) != undefined){
		for(var i = 0;i < selectItem.length;i++){
			text += selectItem[i].showName+",";
			value += selectItem[i].id+",";
		}
	}
	text = text.substring(0, text.length-1);
	value = value.substring(0, value.length-1);
	arr.push(text);
	arr.push(value);
	return arr;
}

//创建用户单选下拉框
function createUserSingleSelect(Id,ReadUrl){
	$("#"+Id).kendoComboBox({
        dataTextField: "showName",
        dataValueField: "id",
        itemTemplate:"#= showName#",
        tagTemplate: "#: name #",
        autoClose : false,
        pinyin:true,
        dataSource: {
            transport: {
                read: {
                    url: ReadUrl
                }
            }
        },
        delay: 500,//大数据需要调高一些
        height: 260,
        toolbar:{show: true ,showText: true},//默认值:false 显示工具按钮条
        open: function (e) { this.list.width(240); }
    }); 
}

//解析用户下拉单选选框的text和value
function pareSingleUserSelect(selectItem){
	var arr = new Array();
	var text = "";
	var value = "";
	if(typeof (selectItem) != undefined){
		text += selectItem.showName;
		value += selectItem.id;
	}
	arr.push(text);
	arr.push(value);
	return arr;
}