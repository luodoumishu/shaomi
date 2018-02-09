<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<div id="groupTypeDataTab">
		<ul>
			<li id="groupTypeDataItemTab" class="k-state-active">添加组类型</li>
		</ul>
		<div id="form-container-edit">
			<form id="groupTypeDataform" name="groupTypeDataform" method="post">
				<input type="hidden" id="typeId" name="typeId" data-bind="value:id" />
				<table class="xjj-table-detail" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="20%">组类型名称</th>
						<td width="30%"><input id="typeName" class="k-textbox"
							required="required" validationMessage="请输入组名称"
							style="width: 180px" name="typeName" data-bind="value:typeName" />
						</td>
					</tr>
					<tr>
						<th width="20%">备注</th>
						<td colspan="3"><input id="remark" class="k-textbox"
							style="width: 180px" name="remark" data-bind="value: remark" />
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
</body>
<script>
var editType =null;
var dataFormModel = null;
    $(document).ready(
            function()
            {
                editType = <%=request.getParameter("editType")%>; 
                
                if(editType=="add")
                {
                    InitAddGroupTypeForm();
                }
                else if(editType=="edit")
                {
                    initEditGroupTypeForm();
                    $("#groupTypeDataItemTab").text("修改组类型");
                }
                
                //kendoui校验器初始化
                validator = $("#groupTypeDataform").kendoValidator().data(
                        "kendoValidator");

                //标签页
                $("#groupTypeDataTab").kendoTabStrip(
                {
                    activate : OnTabActivate
                });
            });

    //验证
    function checkForm()
    {
        if (validator.validate())
        {
            
            return true;
        }
        else
        {
            return false;
        }
    }
    
  //初始化表单
    function InitAddGroupTypeForm()
    {
        //数据模型绑定,MVVM
        dataFormModel= new Object();
        dataFormModel.typeName="";
        dataFormModel.remark="";
        dataFormModel.id="";
        dataFormModel = kendo.observable(dataFormModel);
       
        
        //数据模型绑定
        kendo.bind($("#form-container-edit"), dataFormModel);
    }
  
  //初始化表单
    function initEditGroupTypeForm()
    {
        //数据模型绑定,MVVM
        dataFormModel= groupTypeInfo;
      
        
        var remark =doAjax("${ctx}/Zgroup/getGroupTypeRemark.json?id="+groupTypeInfo.id , "POST").resultData;
        dataFormModel.remark=remark;
        dataFormModel = kendo.observable(dataFormModel);
       
        
        //数据模型绑定
        kendo.bind($("#form-container-edit"), dataFormModel);
    }
  
    //异步请求的方法
    function doAjax(url, type)
    {
        var ob = '[]';
        $.ajax(
        {
            url : url,
            type : type,
            cache : false,
            async : false,
            error : function(textStatus, errorThrown)
            {
                alert("系统ajax交互错误" + textStatus);
                return;
            },
            success : function(data)
            {
                ob = data;
            }
        });
        return ob;
    }
    
    //监听退出按钮
    $("#cancel").click(function()
    {
        closewindow("div");
        groupTree.destroy();
        initTree();
        groupTree.expand(".k-item");
    });
    //监听保存按钮
    $("#submit_confirm").click(
            function()
            {
                if (checkForm())
                {
                    if(editType=="add")
                        {
                        AjaxDoPost("${ctx}/Zgroup/addGroupType.json", "models="
                                + JSON.stringify(dataFormModel), false, addSuccess,
                                addError);
                        }
                    else if(editType=="edit")
                        {
                        AjaxDoPost("${ctx}/Zgroup/updateGroupType.json", "models="
                                + JSON.stringify(dataFormModel), false, updateSuccess,
                                updateError);
                        }
                        
                       
                 }
                
            });
    
    
    updateSuccess = function(result)
    {
        if (result.resultCode === 0)
        {
            groupTree.destroy();
            initTree();
            groupTree.expand(".k-item");
            AlertDialog(
            {
                Title : "提示信息",
                Message : "修改成功！",
                Icon : "k-ext-information"
            });
            closewindow("div");
        }
        else
        {
            alert(result.resultMsg);
        }
    }

    function updateError(result)
    {
        AlertDialog(
        {
            Title : "提示信息",
            Message : "修改失败！",
            Icon : "k-ext-error"
        });
    }
    
  //新增组成功回调
    addSuccess = function (result) 
    {
        if (result.resultCode === 0) {
            groupTree.destroy();
            initTree();
            groupTree.expand(".k-item");
            groupGrid.dataSource.read();
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
    
    //新增组失败回调
    function addError(result) 
    {
        AlertDialog({
            Title: "提示信息",
            Message: "添加失败！",
            Icon: "k-ext-error"
        });
    }
    
    //Tab单项激活事件
    function OnTabActivate(e)
    {
        var TabText = $(e.item).find("> .k-link").text();
        RefreshTabContent(TabText);

    }
    //刷新Tab页内容
    function RefreshTabContent(TabText)
    {

    }
</script>
</html>