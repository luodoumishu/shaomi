<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div id="DataTab">
    <ul>
        <li id="FinanceItemTab" class="k-state-active">修改密码</li>
    </ul>
    <div id="form-container-edit">
        <form id="abroadForm">
            <div id="smy" style="color: red;">您好，系统检测到您三个月未修改密码，请先修改密码!</div>
            <table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th width="10%" class="required">原密码</th>
                    <td width="35%">
                        <input id="password" type="password" class="required k-textbox" style="width: 112px;" name="password"  data-bind="value: password"/>
                    </td>
                </tr>
                <tr>
                    <th width="10%" class="required">新密码</th>
                    <td width="35%">
                        <input id="npassword" type="password" class="required k-textbox" style="width: 112px;" name="npassword"  />
                    </td>
                </tr>
                <tr>
                    <th width="10%" class="required">再输一次</th>
                    <td width="35%">
                        <input id="npassword2" type="password" class="required k-textbox" style="width: 112px;" name="npassword2"  />
                    </td>
                </tr>
            </table>
        </form>
        <div align="center" >
            <input id="save" type="button" class="k-button width60" value="保 存 " />
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        //标签页
        $("#DataTab").kendoTabStrip({
            activate: OnTabActivate
        });
        //Tab单项激活事件
        function OnTabActivate(e) {
            var TabText = $(e.item).find("> .k-link").text();
        }
        if("${param.type}" == "1")
       		$("#smy").show();
        else
        	$("#smy").hide();
    });


    $("#cancel").click(function(){
        closewindow("div","xgma");
    })

    $("#save").click(function(){

        if($("#npassword").val()==""){
            AlertDialog({
                Title: "提示信息",
                Message: "请输入新密码!",
                Icon: "k-ext-warning"
            })
            return false;
        }
        if($("#npassword2").val()==""){
            AlertDialog({
                Title: "提示信息",
                Message: "请再输入一次新密码!",
                Icon: "k-ext-warning"
            })
            return false;
        }
        if($("#npassword").val() != $("#npassword2").val()){
            AlertDialog({
                Title: "提示信息",
                Message: "两次新密码输入不相同!",
                Icon: "k-ext-warning"
            })
            return false;
        }
        var reg = new RegExp('^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,20}$');
        if(!reg.test($("#npassword").val())){
            AlertDialog({
                Title: "提示信息",
                Message: "密码中必须包含字母、数字、特称字符，至少8个字符，最多20个字符。!",
                Icon: "k-ext-warning"
            });
        	return false;
        }
        $.when(ConfirmDialog({
                  Title: "提示信息",
                  Message: "确定操作？",
                  Icon: "k-ext-question"
              })).done(function (response) {
                  if (response.button == "确定"){
  		        	$.ajax({
  						url : "${ctx}/zuser/modifyPwd.json",
  						type : "POST",
  						data : {"oldPwd": $("#password").val(), "newPwd" :$("#npassword").val(), "yesNewPwd" : $("#npassword2").val()},
  						cache : false,
  						async : false,
  						error : function(result){
				            AlertDialog({
				                Title: "提示信息",
				                Message: result.resultMsg,
				                Icon: "k-ext-error"
				            });
  						},success : function(result){
					        if (result.resultCode === 0) {
					            AlertDialog({
					                Title: "提示信息",
					                Message: "修改成功,重新登录系统!",
					                Icon: "k-ext-information"
					            }).done(function (response) {
					                        var url = '${ctx}/logout.jsp';
					                        window.location.href =  url;
					                    });
					        }else {
					            AlertDialog({
					                Title: "提示信息",
					                Message: result.resultMsg,
					                Icon: "k-ext-error"
					            });
					        }
  						}
  					});
                  }else{
                      return;
                  }
              });
    });

</script>
</body>
</html>