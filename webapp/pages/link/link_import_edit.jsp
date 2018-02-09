<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div id="DataTab">
	<ul>
        <li id="FinanceItemTab" class="k-state-active">频道项信息</li>
    </ul>
    <div id="form-container-edit">
    <form id="dataform" name="dataform" method="post">
        <input type="hidden" data-bind="value:id" />
          <input type="hidden" data-bind="value:channelId" />
           <input type="hidden" data-bind="value:channeName" />
           <input type="hidden" data-bind="value:linkId" />
           <input type="hidden" data-bind="value:linkAddr" />
           <input type="hidden" data-bind="value:linkType" />
            <table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
               <tr>
                    <th width="100">超链接名称</th>
                    <td>
                        <input id="linkName" class="k-textbox" style="width: 95%" data-bind="value: linkName" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <th>排序号</th>
                	<td>
                    	<input id="sort" name="sort" data-bind="value: sort" style="width: 180px"/>
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
		$("#sort").kendoNumericTextBox({
			decimals : 0,//小数位数，即VIEW_MODEL层的精度
			value: Item.sortno,//初始值，VIEW层
			min : 0,//最小值
			format: "0",
			step : 1,//每次点击增减按钮的浮动值
     	});
     	
		//数据模型绑定
        kendo.bind($("#form-container-edit"), Item);
     	//下拉机构树
    }
    
    
	//监听退出按钮
	$("#cancel").click(function () { closewindow("div"); grid.dataSource.read();});
	//监听保存按钮
	$("#submit_confirm").click(function () {
	    if (checkForm()) {
	            AjaxDoPost(path+"/cms/chlLink/update.json", "models=" + JSON.stringify(Item), false, editSuccess, editError);
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
  
    editSuccess = function (result) {
        if (result.resultCode === 0) {
            grid.dataSource.read();
            closewindow("div");
        }
        else {
            alert(result.resultMsg);
        }
    }
    
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


