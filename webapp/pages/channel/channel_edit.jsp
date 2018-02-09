<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div id="DataTab">
	<ul>
        <li id="FinanceItemTab" class="k-state-active">频道信息</li>
    </ul>
    <div id="form-container-edit">
    <form id="dataform" name="dataform" method="post">
        <input type="hidden" data-bind="value:id" />
        <input type="hidden" data-bind="value:createdDate" />
        <input type="hidden" data-bind="value:createdBy" />
        <input type="hidden" data-bind="value:parentChanneName" />
            <table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
               <tr>
                    <th width="100">频道名称&nbsp;<span><font color="red"></font>*</span></th>
                    <td>
                        <input id="channeName" class="k-textbox" required="required" validationMessage="请输入频道名称" style="width: 95%" data-bind="value: channeName"/>
                    </td>
                    <th width="100">频道代码&nbsp;<span><font color="red"></font>*</span></th>
                    <td>
                        <input id="channeCode" class="k-textbox" required="required" validationMessage="请输入频道代码" style="width: 95%" data-bind="value: channeCode"/>
                    </td>
                </tr>
                <tr>
                    <th>排序号</th>
                	<td>
                    	<input id="sortno" name="sortno" data-bind="value: sortno" style="width: 95%"/>
                	</td>
                	 <th>父频道</th>
                	<td>
                        <div id="parentChanneid" style="width: 95%" data-bind="value: parentChanneid"></div>
                    </td>
                </tr>
                <tr>
                	<th>关联类型</th>
                	<td>
                		<input type="radio" name="relevanceType" value="0" data-bind="checked: relevanceType" checked="checked" />栏目类型
                		<input type="radio" name="relevanceType" value="1" data-bind="checked: relevanceType" />超链接类型
                		<input type="radio" name="relevanceType" value="2" data-bind="checked: relevanceType" />投票类型
                	</td>
                	<th >显示方式</th>
                	<td>
                		<input type="radio" name="showMode" value="0" data-bind="checked: showMode" checked="checked" />标题
                		<input type="radio" name="showMode" value="1" data-bind="checked: showMode" />内容
                	</td>
                </tr>
                <tr>
                	<th>子栏目个数</th>
                	<td colspan="3">
                		<input id="childMenuNum" class="k-textbox" style="width: 5%" data-bind="value: childMenuNum"/>个
                	</td>
                </tr>
                <tr>
                	<th >备注</th>
                	<td colspan="3">
                    	<textarea id="remark" class="k-textbox" style="width: 98%;height: 60px" name="demo" data-bind="value: demo"></textarea>
                	</td>
                </tr>
            </table>
     </form>
    <div class="clear" style="height: 10px"></div>
    <div align="center">
        <button id="submit_confirm" class="k-button width70">保存</button>
        <button id="cancel" class="k-button width70">退出</button>
    </div>
   </div>
</div>
<script type="text/javascript">
	var SelectCorp = null;
	
    $(document).ready(function () {
        //初始化表单
        InitForm();
        //kendoui校验器初始化
        validator = $("#dataform").kendoValidator().data("kendoValidator");
    	//标签页
	    $("#DataTab").kendoTabStrip({
            activate: OnTabActivate
        });
    });
    
    //初始化表单
    function InitForm() {
    	//数据模型绑定,MVVM
    	Item = kendo.observable(Item);
    	//新建对象绑定模型
		if (EditType == "add") {//VIEW_MODEL层的数据初始化
			Item.set("sortno",0);
			Item.set("parentChanneid","0")
			Item.set("relevanceType",0);
			Item.set("showMode",0);
		}
		$("#sortno").kendoNumericTextBox({
			decimals : 0,//小数位数，即VIEW_MODEL层的精度
			value: Item.sortno,//初始值，VIEW层
			min : 0,//最小值
			format: "0",
			step : 1//每次点击增减按钮的浮动值
     	});
     	
		//数据模型绑定
        kendo.bind($("#form-container-edit"), Item);
     	//下拉机构树
	  	SelectCorp = CreateDropDownTreeView({
	        ID: "parentChanneid",
	        AutoBind: true,
	        DataSource: selectSource,
	        TextField: "channeName",
	        Height: 400,
	        OnSelect: function (e) {
	            var dataItem = this.dataItem(e.node);
	            Item.parentChanneid = dataItem.id;
	            Item.parentChanneName = dataItem.channeName;
	        }
	    });
	    if(Item.parentChanneid==0){
	    	SelectCorp.selectItem("频道列表");
	    }else{
	    	SelectCorp.selectItem(Item.parentChanneName);
	    }
    }
    
    
	//监听退出按钮
	$("#cancel").click(function () { closewindow("div"); grid.dataSource.read();});
	//监听保存按钮
	$("#submit_confirm").click(function () {
	    if (checkForm()) {
	        if (EditType == "edit") {
	            AjaxDoPost("${ctx}/CmsChannel/update.json", "models=" + JSON.stringify(Item), false, editSuccess, editError);
	        }
	        else if (EditType == "add") {
	        	AjaxDoPost("${ctx}/CmsChannel/save.json","models=" + JSON.stringify(Item),false,addSuccess,addError);
	        }
	    }
	});
	//Tab单项激活事件
    function OnTabActivate(e) {
        var TabText = $(e.item).find("> .k-link").text();
        RefreshTabContent(TabText);

    }
  //刷新Tab页内容
    function RefreshTabContent(TabText) {
        
    }
  //验证
	function checkForm() {
	    if (validator.validate()) {
	        return true;
	    }else{
	    	return false;
	    }
	}
	//验船师新增成功回调函数
    addSuccess = function (result) {
        if (result.resultCode === 0) {
            grid.dataSource.read();
            LoadItemTree();
            AlertDialog({
                Title: "提示信息",
                Message: "新增成功!",
                Icon: "k-ext-information"
            });
            closewindow("div");
        } else {
            alert(result.resultMsg);
        }
    }
    
    //验船师新增失败回调函数
    function addError(result) {
    	AlertDialog({
            Title: "提示信息",
            Message: "添加失败！",
            Icon: "k-ext-error"
        });
    }
  
    //验船师修改成功回调函数
    editSuccess = function (result) {
        if (result.resultCode === 0) {
            grid.dataSource.read();
            LoadItemTree();
            AlertDialog({
                Title: "提示信息",
                Message: "修改成功！",
                Icon: "k-ext-information"
            });
            closewindow("div");
        }
        else {
            alert(result.resultMsg);
        }
    }
    
    //验船师修改失败回调函数
    function editError(result) {
    	AlertDialog({
            Title: "提示信息",
            Message: "修改失败！",
            Icon: "k-ext-error"
        });
    }
</script>
</body>
</html>


