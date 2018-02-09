<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div id="DataTab">
	<ul>
        <li id="FinanceItemTab" class="k-state-active">单位信息</li>
    </ul>
    <div id="form-container-edit">
    <form id="dataform" name="dataform" method="post">
        <input type="hidden" data-bind="value:id" />
        <input type="hidden" data-bind="value:createdDate" />
        <input type="hidden" data-bind="value:createdBy" />
        <input type="hidden" data-bind="value:parentName" />
            <table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
               <tr>
                    <th width="20%">单位名称</th>
                    <td>
                        <input id="name" class="k-textbox" onmouseout="setPyName(this)" required="required" validationMessage="请输入单位名称" style="width: 180px" data-bind="value: name"/>
                    </td>
                    <th width="20%">上级单位</th>
                    <td>
                        <div id="parentId" style="width: 180px" data-bind="value: parentId"></div>
                    </td>
                    <!-- <th width="20%">单位拼音简称</th>
                    <td>
                        <input id="pyCode" class="k-textbox" style="width: 180px" data-bind="value: pyCode"/>
                    </td> -->
                </tr>
                <tr>
                    <th width="20%" >排序号</th>
                	<td colspan="3">
                    	<input id="pri" name="pri" data-bind="value: pri" style="width: 180px"/>
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
// 			Item.set("pri",0);
			Item.set("parentId",parentId);
			Item.set("parentName",parentName);
		}
		$("#pri").kendoNumericTextBox({
			decimals : 0,//小数位数，即VIEW_MODEL层的精度
			value: Item.pri,//初始值，VIEW层
			min : 0,//最小值
			format: "0",
			step : 1,//每次点击增减按钮的浮动值
     	});
     	
		//数据模型绑定
        kendo.bind($("#form-container-edit"), Item);
     	//下拉机构树
	  	SelectCorp = CreateDropDownTreeView({
	        ID: "parentId",
	        AutoBind: true,
	        DataSource: selectSource,
	        TextField: "name",
	        OnSelect: function (e) {
	            var dataItem = this.dataItem(e.node);
	            Item.parentId = dataItem.id;
	            Item.parentName = dataItem.name;
	        }
	    });
	    if(Item.parentId==0){
	    	SelectCorp.selectItem("单位列表");
	    }else{
	    	SelectCorp.selectItem(parentName);
	    }
    }
    
    
	//监听退出按钮
	$("#cancel").click(function () { closewindow("div"); grid.dataSource.read();});
	//监听保存按钮
	$("#submit_confirm").click(function () {
	    if (checkForm()) {
	        if (EditType == "edit") {
	            AjaxDoPost("${ctx}/Zorganize/update.json", "models=" + JSON.stringify(Item), false, editSuccess, editError);
	        }
	        else if (EditType == "add") {
	        	AjaxDoPost("${ctx}/Zorganize/save.json","models=" + JSON.stringify(Item),false,addSuccess,addError);
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
    
    var setPyName = function(obj){
    	var orgName = $(obj).val();
    	if(null != orgName && "" != orgName && "undefined" != orgName){
    		orgName = orgName.replace(/森林公安局/g, "");
    		orgName = orgName.replace(/森林公安/g, "");
    		$.ajax({
    			url:"${ctx}/Zorganize/setPyName.json",
    			type:"POST",
    			data:{
    				name:orgName
    			},
    			success:function(data){
    				if(null != data){
    					var pyName = data.resultData;
    				if(null != pyName && "undefined" != pyName){
	    				$("#pyCode").val(pyName);
    				}
     			  }
    			}
    		})
    	}
    }
</script>
</body>
</html>


