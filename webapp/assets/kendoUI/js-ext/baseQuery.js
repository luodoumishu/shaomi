var grid = new Object;
var data = new Object;
var gQueryViewModel = kendo.observable({
    FilterSource: null,
    Init: function (DivElement, DataGrid, QueryCallback) {
        var that = this;
        if (that.FilterSource != null) {
            return;
        }
        var FilterSource = new Array();

        for (var i = 0; i < DataGrid.columns.length; i++) {
            var DataColumn = DataGrid.columns[i];
            if (DataColumn.filterable) {
                var FilterItem = new Object;
                FilterItem.PropertyName = DataColumn.field;
                FilterItem.Name = DataColumn.title;
                FilterItem.Options = "";
        FilterItem.PropertyValue = "";

        FilterSource.push(FilterItem);
    }
}

this.set("FilterSource", new kendo.data.ObservableArray(FilterSource));

//生成过滤和排序数据窗口
DivElement.html("<div id=QueryTabStrip><ul><li class=\"k-state-active\">查询信息</li></ul><div><div id=\"FilterGrid\"></div></div><br><button id=\"Query\" class=\"k-button\">查询</button> <button id=\"Clear\" class=\"k-button\">清除</button></div>");

$("#QueryTabStrip").kendoTabStrip().data("kendoTabStrip");

/**生成查询信息
 * 
 */
$("#FilterGrid").kendoGrid({
    dataSource: new kendo.data.DataSource({
        data: this.get("FilterSource"),
        schema: {
            model: {
                fields: {
                    Name: { editable: false }
                }
            }
        }
    }),
    columns: [
        { field: "PropertyName", title: "列名属性名", hidden: true },
        { field: "Name", title: "列名" },
        { field: "Options", title: "操作" ,editor: FilterTypeDropDownEditor},
        { field: "PropertyValue", title: "值"}
    ],
    editable: true
});

/**生成操作列的下拉框
 * 
 * @param container
 * @param options
 */
function FilterTypeDropDownEditor(container, options) {
    $('<input id="test" data-bind="value:' + options.field + '"/>')
        .appendTo(container)
        .kendoDropDownList({
            optionLabel: "-- 请选择 --",
            dataValueField: "OptionName",
            dataTextField: "OptionName",
            dataSource: OrderTypeSource
        });
}

/**切割字符串
 * 
 * @param Values 源字符串
 * @param SplitValue 切割字符
 * @returns {Array} 字符串数组
 */
function PropertyValueSplit(Values, SplitValue) {
    var SplitValues;
    var PropertyValues = new Array;
    SplitValues = Values.split(SplitValue);
    for (var i = 0; i < SplitValues.length; i++) {
        var Item = $.trim(SplitValues[i]);
        if (Item.length > 0) {
            PropertyValues.push(Item);
        }
    }

    if (PropertyValues.length <= 0 && Values.length > 0) {
        PropertyValues.push(Values);
    }

    return PropertyValues;
}

/**点击高级查询中的查询按钮执行的方法
 * 
 */
function Query() {
    if (QueryCallback != null) {
        var FilterSource = that.get("FilterSource");
        var QueryInfo = new Object;
        var Filters = new Array();
        QueryInfo.Filters = Filters;
        for (var i = 0; i < FilterSource.length; i++) {
            var Item = FilterSource[i];
            if (Item.PropertyName.length > 0 ||
                Item.PropertyValue.length > 0 ||
                Item.Options.length > 0) {
                var FilterItem = new Object;
                FilterItem.PropertyName = Item.PropertyName;
                FilterItem.PropertyValue = Item.PropertyValue;
                FilterItem.Options = Item.Options;
                Filters.push(FilterItem);
            }
        }
        QueryCallback(QueryInfo, DataGrid.element[0].id);
    }
}

/**点击高级查询中的清除按钮执行的方法
 * 
 */
function Clear() {
    that.ClearQuery();
}

$("#Query").click(Query);

$("#Clear").click(Clear);
    kendo.bind(DivElement, this);
},
ClearQuery: function () {
    var that = this;
    var FilterSource = that.get("FilterSource");

if (FilterSource != null) {
    for (var i = 0; i < FilterSource.length; i++) {
        var Item = FilterSource[i];
        Item.PropertyValue = "";
        Item.Options = "";
    }
}

var FilterGrid = $("#FilterGrid").data("kendoGrid");
    if (FilterGrid != null) {
        FilterGrid.refresh();
    }
},

//移除方法
destroy: function () {
    this.set("FilterSource", null);
var FilterGrid = $("#FilterGrid").data("kendoGrid");
        if (FilterGrid != null) {
            FilterGrid.destroy();
        }
    }
});

/**下拉框的数据源
 * 
 */
var OrderTypeSource = [
    { OptionName: "等于(=)" , OptionValue: "eq"},
{ OptionName: "小于(<)" , OptionValue: "lt"},
{ OptionName: "小于或等于(<=)" , OptionValue: "le"},
{ OptionName: "大于(>)" , OptionValue: "gt"},
{ OptionName: "大于或等于(>=)" , OptionValue: "ge"},
{ OptionName: "包含(like)" , OptionValue: "like"}
];

/*高级查询开始*/
function onSearch(GridId) {
	var QueryWindow = $("#QueryGrid");
	if (!QueryWindow.data("kendoWindow")) {
		QueryWindow.kendoWindow({
			width : "700px",
			height : "450px",
			animation : false,
			modal : true,
			actions : [ "Minimize", "Maximize", "Close" ],
			title : "高级查询"
		});
	}
	QueryWindow.data("kendoWindow").center().open();
	var dg = (GridId == undefined || GridId == "undefined") ? "grid" : GridId;
	var DataGrid = $("#" + dg).data("kendoGrid");
	gQueryViewModel.Init($("#QueryGrid"), DataGrid, QueryCallback);
}

function QueryCallback(QueryInfo, GridId) {
	var gd = GridId == undefined ? "grid" : GridId;
	var DataGrid = $("#" + gd).data("kendoGrid");
	data = QueryInfo;//将高级查询中的条件复制给要传到后台的变量
	SearchComment();//点击查询按钮
	var QueryWindow = $("#QueryGrid");//获取dialog对象
	QueryWindow.data("kendoWindow").close();//关闭dialog
}
/*高级查询结束*/

function getDoms(){
	var doms = new Array();
	var inputs = $("#condition").find("input");
	var selects = $("#condition").find("select");
	
	/**获取input对象
	 * 
	 */
	if(inputs != undefined && inputs != "endefined" && inputs.length > 0){
		for(var i = 0;i < inputs.length;i++){
			doms.push(inputs[i]);
		}
	}
	
	/**获取select对象
	 * 
	 */
	if(selects != undefined && selects != "endefined" && selects.length > 0){
		for(var i = 0;i < selects.length;i++){
			doms.push(selects[i]);
		}
	}
	return doms;
}

/**获取post参数
 * 
 * @returns {___QueryInfo3}
 */
function getParam() {
	var Filters = data.Filters;
	var QueryInfo = new Object;
	var rules = new Array();
	QueryInfo.rules = rules;
	if (Filters == undefined || Filters == "undefined") {
		var conditions = getDoms();//获取dom对象数组
		Filters = singleSearch(conditions);
	}
	for ( var i = 0; i < Filters.length; i++) {
		var rule = new Object();
		rule.field = Filters[i].PropertyName;
		rule.op = convertProperty(Filters[i].Options);
		rule.data = Filters[i].PropertyValue;
		rules.push(rule);
	}
	data.Filters = undefined;
	return QueryInfo;
}

/**获取简单查询的查询条件
 * 
 * @param doms
 * @returns {Array}
 */
function singleSearch(doms){
	var Filters = new Array();
	if(doms != undefined && doms != "undefined"){
		for(var i = 0;i < doms.length;i++){
			var dom = doms[i];
			var Filter = new Object();
			Filter.PropertyName = dom.id;
			Filter.Options = $(dom).attr("op");
			Filter.PropertyValue = dom.value;
			Filters.push(Filter);
		}
	}
	return Filters
}

function convertProperty(property) {
	var new_property = property;
	if (property == "等于(=)") {
		new_property = "eq";
	} else if (property == "小于(<)") {
		new_property = "lt";
	} else if (property == "大于(>)") {
		new_property = "gt";
	} else if (property == "大于或等于(>=)") {
		new_property = "ge";
	} else if (property == "小于或等于(<=)") {
		new_property = "le";
	} else if (property == "包含(like)") {
		new_property = "like";
	}
	return new_property;
}

//查询按钮
function SearchComment() {
	var dataSource = grid.dataSource;
	dataSource.page(1);
}
