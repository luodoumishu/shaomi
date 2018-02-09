<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@page import="com.xjj.framework.core.web.filter.WebContext"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div id="DataTab">
    <div id="form-container-edits">
    <form id="dataform" name="dataform" method="post">
        <input type="hidden" id="id" name="id" data-bind="value:id" />
        <input type="hidden" id="roles" name="roles" data-bind="value:roles" />
        <table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
           <tr>
                <th width="20%">用户名称</th>
                <td>
                	<div style="width: 180px"><span id="name"  name="name" disabled="disabled"></span></div>
                    <!-- <input id="name" class="k-textbox" required="required" validationMessage="请输入用户名称" style="width: 180px" name="name" data-bind="value: name" disabled="disabled"/> -->
                </td>
                <th width="20%">登录帐号</th>
                <td>
                	<div style="width: 180px"><span id="account"  name="account" disabled="disabled"></span></div>
                </td>
            </tr>
            <tr>
                <th width="20%">所属单位</th>
                <td colspan="3">
                	<div style="width: 180px"><span id="orgIds"  name="orgIds" disabled="disabled"></span></div>
                	<!-- <select id="orgIds" name="orgIds" data-bind="value: orgIds" style="width: 180px" disabled="disabled"></select> -->
                </td>
            </tr>
            <tr>
                <th width="20%">原密码</th>
                <td colspan="3">
                    <input id="password" class="k-textbox" type="password" required="required" validationMessage="请输入原密码" style="width: 180px;margin-bottom: 0px;" name="password"/>
                </td>
            </tr>
            <tr>
                <th width="20%">新密码</th>
                <td colspan="3">
                    <input id="newPwd" class="k-textbox" type="password" required="required" validationMessage="请输入新密码" style="width: 180px;margin-bottom: 0px;" name="newPwd"/>
                </td>
            </tr>
            <tr>
                <th width="20%">确认新密码</th>
                <td colspan="3">
                    <input id="_newPwd" class="k-textbox" type="password" required="required" validationMessage="请输入确认新密码" style="width: 180px;margin-bottom: 0px;" name="_newPwd"/>
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
	var Item=null;
	var directId='${param.directId}';
	var orgIds = "";
	var options=null;
	var userId = '<%=WebContext.getInstance().getHandle().getUserId()%>';
    $(document).ready(function () {
        //初始化表单
        Item = kendo.observable(Item);
        InitForm() ;
    });
    
    //初始化表单
    function InitForm() {
		//数据模型绑定
        kendo.bind($("#form-container-edits"), Item);
		AjaxDoPost("${ctx}/zuser/getOrgData.json", "id="+directId, false, orgDataSuccess, orgDataError);
        var orgId = Item.orgs[0].orgid + '';
        AjaxDoPost("${ctx}/zuser/getOrgNameById.json", "orgId="+orgId, false, orgNameSuccess, orgNameError);
    }
    
	//监听退出按钮
	$("#cancel").click(function () {
	  	closewindow("div","修改密码");
	});
	
	//监听保存按钮
	$("#submit_confirm").click(function () {
	    if (checkForm()) {
	    	var newPwd = $("#newPwd").val();
	    	var _newPwd = $("#_newPwd").val();
	    	var oldPwd = $("#password").val();
	    	if(newPwd == _newPwd){
	    		$.ajax({
	    			url:"${ctx}/zuser/updateAccount.json",
	    			data:{"userId":userId,"oldPwd":oldPwd,"newPwd":newPwd},
	    			success:editSuccess,
	    			error:editError
	    		});
	    		//AjaxDoPost("${ctx}/zuser/updateAccount.json", "?userId="+userId+"&oldPwd="+oldPwd+"&newPwd="+newPwd, false, editSuccess, editError);
	    	}else{
	    		AlertDialog({
	                Title: "提示信息",
	                Message: "新密码与确认新密码不一致！",
	                Icon: "k-ext-error"
	            });
	    		$("#newPwd").val("");
	    		$("#_newPwd").val("");
	    		return;
	    	}
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
    
    //修改成功回调函数
    editSuccess = function (result) {
        if (result.resultCode === 0) {
            AlertDialog({
                Title: "提示信息",
                Message: "修改成功！请重新登录",
                Icon: "k-ext-information"
            });
            closewindow("div","修改密码");
            var url = '/logout.jsp';
            setTimeout(function(){
            	window.location.href = ctx+url;
            }, 1000); 
        }
        else {
            AlertDialog({
                Title: "提示信息",
                Message: result.resultMsg,
                Icon: "k-ext-information"
            });
            $("#password").val("");
            $("#newPwd").val("");
    		$("#_newPwd").val("");
    		return;
        }
    }
    
    //修改失败回调函数
    function editError(result) {
    	AlertDialog({
            Title: "提示信息",
            Message: "修改失败！",
            Icon: "k-ext-error"
        });
    }
    
    
    function directeSuccess(result) {
       if (result.resultCode === 0) {
		$("#name").html(result.resultData[0]["name"]);
		$("#account").html(result.resultData[0]["account"]);
       }
       else {
           alert(result.resultMsg);
       }
    }
    
    function directeError(result) {
    	AlertDialog({
            Title: "提示信息",
            Message: "操作失败！",
            Icon: "k-ext-error"
        });
    }
    
    function orgDataSuccess(result) {
       if (result.resultCode === 0) {
        Item.set("orgs", result.resultData);
       }
       else {
           alert(result.resultMsg);
       }
    }
    
    function orgDataError(result) {
    	AlertDialog({
            Title: "提示信息",
            Message: "操作失败！",
            Icon: "k-ext-error"
        });
    }
    
    function orgNameSuccess(result) {
      	if (result.resultCode === 0) {
     		$("#orgIds").html(result.resultData[0]["name"]);
        }
        else {
            alert(result.resultMsg);
        }
    }
    
    //验船师修改失败回调函数
    function orgNameError(result) {
    	AlertDialog({
            Title: "提示信息",
            Message: "操作失败！",
            Icon: "k-ext-error"
        });
    }
</script>
</body>
</html>


