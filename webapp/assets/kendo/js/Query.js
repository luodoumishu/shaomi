var gQueryViewModel = kendo.observable({
    FilterSource: null,
    OrderSource: null,
    Init: function (DivElement, DataGrid, QueryCallback) {
        var that = this;
        if (that.FilterSource != null && that.OrderSource != null) {
            return;
        }
        var FilterSource = new Array();
        var OrderSource = new Array();

        for (var i = 0; i < DataGrid.columns.length; i++) {
            var DataColumn = DataGrid.columns[i];
            if (DataColumn.filterable) {
                var FilterItem = new Object;
                FilterItem.PropertyName = DataColumn.field;
                FilterItem.Name = DataColumn.title;
                FilterItem.LikeValue = "";
                FilterItem.EqValue = "";
                FilterItem.NotEqValue = "";
                FilterItem.GeValue = "";
                FilterItem.Levalue = "";

                FilterSource.push(FilterItem);
            }
            if (DataColumn.sortable) {
                var OrderItem = new Object;
                OrderItem.PropertyName = DataColumn.field;
                OrderItem.Name = DataColumn.title;
                OrderItem.OrderType = "";

                OrderSource.push(OrderItem);
            }
        }

        this.set("FilterSource", new kendo.data.ObservableArray(FilterSource));
        this.set("OrderSource", new kendo.data.ObservableArray(OrderSource));

        //生成过滤和排序数据窗口
        DivElement.html("<div id=QueryTabStrip><ul><li class=\"k-state-active\">过滤信息</li><li>排序信息</li></ul><div><div id=\"FilterGrid\"></div></div><div><div id=\"OrderGrid\"></div></div><br><button id=\"Query\" class=\"k-button\">查询</button> <button id=\"Clear\" class=\"k-button\">清除</button></div>");

        $("#QueryTabStrip").kendoTabStrip().data("kendoTabStrip");

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
                { field: "LikeValue", title: "包含(like)" },
                { field: "EqValue", title: "等于(=)" },
                { field: "NotEqValue", title: "不等于(<>)" },
                { field: "GeValue", title: "大于等于(>=)" },
                { field: "Levalue", title: "小于等于(<=)" }
            ],
            editable: true
        });

        $("#OrderGrid").kendoGrid({
            dataSource: new kendo.data.DataSource({
                data: this.get("OrderSource"),
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
                { field: "Name", title: "列名", editable: false },
                { field: "OrderType", title: "排序方式", editor: OrderTypeDropDownEditor, template: "${DisplayDropDownText(OrderType,OrderTypeSource,'OrderID','OrderName')}" }
            ],
            editable: true
        });

        function OrderTypeDropDownEditor(container, options) {
            $('<input data-bind="value:' + options.field + '"/>')
                .appendTo(container)
                .kendoDropDownList({
                    optionLabel: " ",
                    dataValueField: "OrderID",
                    dataTextField: "OrderName",
                    dataSource: OrderTypeSource
                });
        }

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

        function Query() {
            if (QueryCallback != null) {
                var FilterSource = that.get("FilterSource");
                var OrderSource = that.get("OrderSource");

                var QueryInfo = new Object;
                var Filters = new Array();
                var Orders = new Array();
                QueryInfo.Filters = Filters;
                QueryInfo.Orders = Orders;
                for (var i = 0; i < FilterSource.length; i++) {
                    var Item = FilterSource[i];
                    if (Item.LikeValue.length > 0 ||
                        Item.EqValue.length > 0 ||
                        Item.NotEqValue.length > 0 ||
                        Item.GeValue.length > 0 ||
                        Item.Levalue.length > 0) {
                        var FilterItem = new Object;
                        FilterItem.PropertyName = Item.PropertyName;
                        FilterItem.LikeValues = PropertyValueSplit(Item.LikeValue, ";");
                        FilterItem.EqValues = PropertyValueSplit(Item.EqValue, ";");
                        FilterItem.NotEqValues = PropertyValueSplit(Item.NotEqValue, ";");
                        FilterItem.GeValues = PropertyValueSplit(Item.GeValue, ";");
                        FilterItem.Levalues = PropertyValueSplit(Item.Levalue, ";");

                        Filters.push(FilterItem);
                    }
                }

                for (var i = 0; i < OrderSource.length; i++) {
                    var Item = OrderSource[i];
                    if (Item.OrderType.length > 0) {
                        var OrderItem = new Object;
                        OrderItem.PropertyName = Item.PropertyName;
                        if (Item.OrderType == "0") {
                            OrderItem.IsAscending = false;
                        }
                        else {
                            OrderItem.IsAscending = true;
                        }

                        Orders.push(OrderItem);
                    }
                }
                QueryCallback(QueryInfo, DataGrid.element[0].id);
            }
        }

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
        var OrderSource = that.get("OrderSource");

        if (FilterSource != null) {
            for (var i = 0; i < FilterSource.length; i++) {
                var Item = FilterSource[i];
                Item.LikeValue = "";
                Item.EqValue = "";
                Item.NotEqValue = "";
                Item.GeValue = "";
                Item.Levalue = "";
            }
        }

        if (OrderSource != null) {
            for (var i = 0; i < OrderSource.length; i++) {
                var Item = OrderSource[i];
                Item.OrderType = "";
            }
        }

        var FilterGrid = $("#FilterGrid").data("kendoGrid");
        if (FilterGrid != null) {
            FilterGrid.refresh();
        }

        var OrderGrid = $("#OrderGrid").data("kendoGrid");
        if (OrderGrid != null) {
            OrderGrid.refresh();
        }
    },

    //移除方法
    destroy: function () {
        this.set("FilterSource", null);
        this.set("OrderSource", null);
        var FilterGrid = $("#FilterGrid").data("kendoGrid");
        if (FilterGrid != null) {
            FilterGrid.destroy();
        }
        var OrderGrid = $("#OrderGrid").data("kendoGrid");
        if (OrderGrid != null) {
            OrderGrid.destroy();
        }
    }
});

var OrderTypeSource = [
    { OrderID: "0", OrderName: "降序" },
    { OrderID: "1", OrderName: "升序" }
];

function DisplayDropDownText(Value, DataSource, ValueField, TextField) {
    var Result = "";
    $.each(DataSource, function () {
        if (this[ValueField] == Value) {
            Result = this[TextField];
            return false;
        }
    });
    return Result;
}