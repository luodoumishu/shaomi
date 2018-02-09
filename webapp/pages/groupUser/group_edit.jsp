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
			<li id="FinanceItemTab" class="k-state-active">组信息</li>
		</ul>
		<div id="form-container-edit">
			<form id="dataform" name="dataform" method="post">
				<input type="hidden" id="id" name="id" data-bind="value:id" />
				<table class="xjj-table-detail" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th width="20%">组名称</th>
						<td width="30%"><input id="name" class="k-textbox"
							required="required" validationMessage="请输入组名称"
							style="width: 180px" name="name" data-bind="value: groupName" />
						</td>
					</tr>
					<tr>
						<th width="20%">组类型</th>
						<td colspan="3"><input id="groupType" name="groupType"
							required="required" validationMessage="请输入组类型" style="width: 82%"></input>
						</td>
					</tr>
					<tr>
						<th width="20%">备注</th>
						<td colspan="3"><input id="remark" class="k-textbox"
							style="width: 180px" name="name" data-bind="value: remark" /></td>
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
<script type="text/javascript">
    //下拉数据源

    $(document).ready(function()
    {
       

        InitForm();
        //kendoui校验器初始化
        validator = $("#dataform").kendoValidator().data("kendoValidator");

        //标签页
        $("#DataTab").kendoTabStrip(
        {
            activate : OnTabActivate
        });
    });

  //验证
    function checkForm() {
        if (validator.validate()) {
            return true;
        }else{
            return false;
        }
    }
    
    function initGroupTypeSelector()
    {
        $("#groupType").kendoComboBox(
        {
            dataTextField : "typeName",
            dataValueField : "id",
            autoClose : false,
            suggest : true,
            placeholder : Item.typeName,
            value : Item.typeId,
            dataSource :
            {
                transport :
                {
                    read :
                    {
                        url : "${ctx}/Zgroup/getAllGroupType.json",
                    }
                }
            },
            change : function(e)
            {
                if (this.selectedIndex == -1)
                {
                    this.value("");
                    //Item.set(unitId, "");
                }
                else
                {
                    //alert(JSON.stringify(Item));
                   // alert(JSON.stringify(this.value()));
                    //Item.set(id, this.value());
                }
            }

        });
    }

    //验证
    function checkForm()
    {
        if (validator.validate())
        {
            var selector = $("#groupType").data("kendoComboBox");
            Item.set("typeId", selector.value());
           
            return true;
        }
        else
        {
            return false;
        }
    }

    //初始化表单
    function InitForm()
    {
        //数据模型绑定,MVVM
        Item = kendo.observable(Item);
        //新建对象绑定模型
        initGroupTypeSelector();

        //数据模型绑定
        kendo.bind($("#form-container-edit"), Item);
    }

    //监听退出按钮
    $("#cancel").click(function()
    {
        closewindow("div");
        groupGrid.dataSource.read();
    });
    //监听保存按钮
    $("#submit_confirm").click(
            function()
            {
                if (checkForm())
                {
                    if (EditType == "edit")
                    {
                        AjaxDoPost("${ctx}/Zgroup/updateGroup.json", "models="
                                + JSON.stringify(Item), false, editSuccess,
                                editError);
                    }
                    else if (EditType == "add")
                    {
                        if(checkForm())
                        {
                            AjaxDoPost("${ctx}/Zgroup/addGroup.json", "models="
                                    + JSON.stringify(Item), false, addSuccess,
                                    addError);
                            groupTree.destroy();
                            initTree();
                            groupTree.expand(".k-item");
                        }
                       
                    }
                }
            });

    //新增组成功回调
    addSuccess = function(result)
    {
        if (result.resultCode === 0)
        {
            groupGrid.dataSource.read();
            AlertDialog(
            {
                Title : "提示信息",
                Message : "新增成功!",
                Icon : "k-ext-information"
            });
            closewindow("div");
        }
        else
        {
            alert(result.resultMsg);
        }
    }

    //新增组失败回调
    function addError(result)
    {
        AlertDialog(
        {
            Title : "提示信息",
            Message : "添加失败！",
            Icon : "k-ext-error"
        });
    }

    //修改成功回调函数
    editSuccess = function(result)
    {
        if (result.resultCode === 0)
        {
            groupGrid.dataSource.read();
            AlertDialog(
            {
                Title : "提示信息",
                Message : "修改成功！",
                Icon : "k-ext-information"
            });
            closewindow("div");
            groupTree.destroy();
            initTree();
            groupTree.expand(".k-item");
        }
        else
        {
            alert(result.resultMsg);
        }
    }

    //修改失败回调函数
    function editError(result)
    {
        AlertDialog(
        {
            Title : "提示信息",
            Message : "修改失败！",
            Icon : "k-ext-error"
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