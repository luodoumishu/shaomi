<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>密码容错次数管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%> 
    <script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
	<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script> 
</head>
<body>
<div id="DataTab">
	<ul>
        <li id="FinanceItemTab" class="k-state-active">密码容错次数</li>
    </ul>
    <div id="form-container-edit">
    <form id="dataform" name="dataform" method="post">
        <input type="hidden" id="id" name="id" data-bind="value:id" />
        <table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
           <tr>
                <th width="40%">容错次数</th>
                <td width="60%">
                    <input id="rccs" class="k-textbox" required="required" validationMessage="请输入容错次数" style="width: 180px" name="rccs" />
                </td>
            </tr>
        </table>
     </form>
    <div class="clear" style="height: 10px"></div>
    <div align="center">
        <button id="submit_confirm" class="k-button width70">保存</button>
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
    	$.ajax({
    		url : "${ctx}/zuser/findRccs.json",
    		type : "POST",
    		async : false,
    		dataType : "json",
    		success : function(result) {
    			$("#rccs").val(result.resultData);
    		},error : function(result) {
    			AlertDialog({
    		       Title: "提示信息",
    		       Message: "网络链接异常！",
    		       Icon: "k-ext-error"
    		   });
    		}
    	});	
    }
    
	//监听退出按钮
	$("#cancel").click(function () { closewindow("div"); grid.dataSource.read();});
	//监听保存按钮
	$("#submit_confirm").click(function () {
	    if (checkForm()) {
	    	AjaxDoPost("${ctx}/zuser/saveRccs.json", "rccs=" + $("#rccs").val(), false, editSuccess, editError);
	    }
	});
	//Tab单项激活事件
    function OnTabActivate(e) {
        var TabText = $(e.item).find("> .k-link").text();
        RefreshTabContent(TabText);

    }
  //验证
	function checkForm() {
	    if (validator.validate()) {
	        return true;
	    }else{
	    	return false;
	    }
	}
  
    //验船师修改成功回调函数
    editSuccess = function (result) {
        if (result.resultCode === 0) {
            AlertDialog({
                Title: "提示信息",
                Message: "修改成功！",
                Icon: "k-ext-information"
            });
        }else {
        	 AlertDialog({
                 Title: "提示信息",
                 Message: "修改失败！",
                 Icon: "k-ext-error"
             });
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


