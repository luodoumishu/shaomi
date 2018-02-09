<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
    <script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
</head>
<body>
<div id="DataTab">
	<ul>
        <li id="FinanceItemTab" class="k-state-active">用户信息</li>
    </ul>
    <div id="form-container-edit">
    <form id="dataform" name="dataform" method="post">
        <input type="hidden" id="id" name="id" data-bind="value:id" />
        <table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
           <tr>
                <th width="20%">用户名称</th>
                <td>
                    <input id="name" class="k-textbox" required="required" validationMessage="请输入用户名称" style="width: 180px" name="name" data-bind="value: name"/>
                </td>
                <th width="20%">用户帐号</th>
                <td>
                	<input id="account" class="k-textbox" required="required" validationMessage="请输入用户帐号" style="width: 180px" name="account" data-bind="value: account"/>
                </td>
            </tr>
            <tr>
                <th width="20%">所属单位</th>
                <td>
                	<select id="orgIds" multiple="multiple" name="orgIds" data-bind="value: orgIds" style="width: 180px"></select>
                </td>
                <th width="20%">排序号</th>
                <td>
                    <input id="pri" name="pri" data-bind="value: pri" style="width: 180px"/>
                </td> 
            </tr>
            <tr>
                <th width="20%">登录密码</th>
                <td colspan="3">
                	<!-- input id="isUpPass" name="isUpPass" data-bind="value: isUpPass" style="width: 65px"/> -->
                    <input id="password" type="password" class="k-textbox" required="required" validationMessage="请输入登录密码" style="width: 112px;" name="password"  data-bind="value: password"/>
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
		var items;
    	//新建对象绑定模型
		if (EditType == "add") {//VIEW_MODEL层的数据初始化
			Item.set("pri",0);//使用频率
			//Item.set("isUpPass",1);//使用频率
			//items = [{ text: "新密码", value: 1}];
		}else{
			//$("#password").val("");
			//$("#password").hide();
			//Item.set("isUpPass",0);
			//items = [{ text: "原密码", value: 0}, { text: "新密码", value: 1}];
		}
		$("#pri").kendoNumericTextBox({
			decimals : 0,//小数位数，即VIEW_MODEL层的精度
			value: Item.pri,//初始值，VIEW层
			min : 0,//最小值
			format: "0",
			step : 1,//每次点击增减按钮的浮动值
     	});
		/*$("#isUpPass").kendoDropDownList({
			dataTextField: "text",
			dataValueField: "value",
			dataSource: items,
			select: onSelect
		});*/
		$("#orgIds").kendoMultiSelect({
            dataTextField: "name",
            dataValueField: "id",
            autoClose : false,
            pinyin:true,
            dataSource: {
                transport: {
                    read: {
                        url: "${ctx}/Zorganize/read.json",
                    }
                }
            },
            delay: 500,//大数据需要调高一些
            height: 260,
            toolbar:{show: true ,showText: true},//默认值:false 显示工具按钮条
            open: function (e) { this.list.width(240); }
        });
        
        if(Item!=null && Item.orgs!=null){
	        var orgIds = "";
	        for(var i=0;Item.orgs.length>i;i++){
	        	if(i!=0){
	        		orgIds+=",";
	        	}
	        	orgIds+=Item.orgs[i].id;
	        }
	        var multiSelect = $("#orgIds").data("kendoMultiSelect");
	        multiSelect.value(orgIds.split(","));
	        multiSelect.open();
	        multiSelect.refresh();
        }
		//数据模型绑定
        kendo.bind($("#form-container-edit"), Item);
    }
    
	//监听退出按钮
	$("#cancel").click(function () { closewindow("div"); grid.dataSource.read();});
	//监听保存按钮
	$("#submit_confirm").click(function () {
	    if (checkForm()) {
	        if (EditType == "edit") {
	            AjaxDoPost("${ctx}/zuser/update.json", "models=" + JSON.stringify(Item), false, editSuccess, editError);
	        }
	        else if (EditType == "add") {
	        	AjaxDoPost("${ctx}/zuser/add.json","models=" + JSON.stringify(Item),false,addSuccess,addError);
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
	    	var multiSelect = $("#orgIds").data("kendoMultiSelect");
	    	Item.set("orgIds",multiSelect.value());
	        return true;
	    }else{
	    	return false;
	    }
	}
	//验船师新增成功回调函数
    addSuccess = function (result) {
        if (result.resultCode === 0) {
            grid.dataSource.read();
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
    
    function onSelect(e) {
        var dataItem = this.dataItem(e.item.index());
        if(dataItem.value!=null && dataItem.value==1){
        	$("#password").val("");
        	$("#password").show();
        }else{
        	$("#password").hide();
        	$("#password").val("1");
        }
    };
</script>
</body>
</html>


