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
			<li id="multiUserEdit" class="k-state-active">编辑组成员</li>
			<li id=singleUserEdit>编辑用户</li>
		</ul>
		<div>
			<form id="multiUserEditForm" name="multiUserEditForm" method="post">
				<input type="hidden" id="multiUserGroupId" name="multiUserGroupId"
					data-bind="value:groupId" />
				<table class="xjj-table-detail" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th th width="20%">部门名称</th>
						<td width="30%">
							<div id="orgId" style="width: 180px"></div>
						</td>

						<th width="20%">组名称</th>
						<td "width="30%"><input id="groupName" name="groupName"
							class="k-textbox" data-bind="value: groupName" disabled="true" />
						</td>
					</tr>
					<tr>
						<th width="20%">用户名称</th>
						<td colspan="3"><select id="users" multiple="multiple"
							name="users" data-bind="value: users" style="width: 82%"
							style="width: 82%"></select>
					</tr>
					<tr>
						<th width="20%">备注</th>
						<td colspan="3"><input id="remark" class="k-textbox"
							style="width: 180px" name="name" data-bind="value: remark" />
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

		<div>
			<form id="singleUserEditForm" name="singleUserDataForm" method="post">
				<input type="hidden" id="singleUserGroupId" name="singleUserGroupId"
					data-bind="value:groupId" />
				<table class="xjj-table-detail" border="0" cellspacing="0"
					cellpadding="0">

					<th width="20%">组名称</th>
					<td "width="30%"><input id="SUgroupName" name="SUgroupName"
						disabled="true" class="k-textbox" data-bind="value: groupName"
						disabled="true" /></td>
					</tr>
					<tr>
						<th width="20%">用户名称</th>
						<td colspan="3"><input id="singleUserName"
							name="singleUserName" class="k-textbox" disabled="true"
							data-bind="value: userName" style="width: 30%"></input>
					</tr>
					<th width="20%">优先级</th>
					<td "width="30%"><input id="singleUserPri"
						name="singleUserPri" min="1" required="required"
						validationMessage="用户优先级不能为空" data-bind="value: pri" />
					</td>
					<td><span class="k-invalid-msg" data-for="singleUserPri"></span>
					</td>
					<tr>
						<th width="20%">备注</th>
						<td colspan="3"><input id="singleUserremark"
							class="k-textbox" style="width: 180px" name="singleUserremark"
							data-bind="value: remark" /></td>
					</tr>
				</table>
			</form>
			<div class="clear" style="height: 10px"></div>
			<div align="center">
				<button id="SUsubmit_confirm" class="k-button width70">保存</button>
				<button id="SUcancel" class="k-button width70">退出</button>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
    var SelectCorp = null;
    var userDataSource = null;
    var multiUserFormObj = null;
    var singleUserFromObj = null;

    $(document).ready(function()
    {

        multiUserFormObj = new Object();
        singleUserFromObj = new Object();
        multiUserFormObj.groupName = groupInfo.groupName;
        multiUserFormObj.groupId = groupInfo.groupId;
        multiUserFormObj.id = groupInfo.groupId;
        //$("#groupName").val(groupInfo.groupName);

        initOrgTree();
        readUserByOrgId();
        initUserMutiSelector();
        initUsersInfo();
        //数据模型绑定
        multiUserFormObj = kendo.observable(multiUserFormObj);
        kendo.bind($("#multiUserEditForm"), multiUserFormObj);
        //标签页
          validator = $("#singleUserEditForm").kendoValidator().data("kendoValidator");
        if (groupInfo.editType == "singleEdit")
        {
            $("#multiUserEdit").removeClass("k-state-active");
            $("#singleUserEdit").addClass("k-state-active");
            singleUserData = groupInfo.singleUserData;

            singleUserFromObj.groupName = groupInfo.groupName;
            singleUserFromObj.groupId = groupInfo.groupId;
            singleUserFromObj.id = groupInfo.groupId;
            singleUserFromObj.userName = groupInfo.singleUserData.userName;
            singleUserFromObj.userId = groupInfo.singleUserData.userId;
            singleUserFromObj.pri = groupInfo.singleUserData.pri;
            singleUserFromObj.remark = groupInfo.singleUserData.remark;
            initPriNumbericBox();
            //数据模型绑定
            singleUserFromObj = kendo.observable(singleUserFromObj);
            kendo.bind($("#singleUserEditForm"), singleUserFromObj);

        }

        $("#DataTab").kendoTabStrip(
        {
            activate : OnTabActivate
        });
        if (groupInfo.editType == "multiEdit")
        {
            var tabStrip = $("#DataTab").data("kendoTabStrip");

            tabStrip.enable($("#singleUserEdit"), false);

        }

    });

    function initPriNumbericBox()
    {
        $("#singleUserPri").kendoNumericTextBox(
        {
            decimals : 0,//小数位数，即VIEW_MODEL层的精度
            value : singleUserFromObj.pri,//初始值，VIEW层
            min : 0,//最小值
            format : "0",
            step : 1,//每次点击增减按钮的浮动值
        });
    }

    function initOrgTree()
    {
        LoadItemTree();
        SelectCorp = CreateDropDownTreeView(
        {
            ID : "orgId",
            AutoBind : true,
            DataSource : selectSource,
            TextField : "name",
            OnSelect : function(e)
            {
                var dataItem = this.dataItem(e.node);
                $("#orgId").val(dataItem.id);
                userDataSource.read();
            },
            IsExpand:true
        });
        SelectCorp.selectItem("全部");
    }

    function LoadItemTree()
    {
        var ResultMsg = doAjax("${ctx}/Zorganize/listOrgs.json", "POST");
        var TreeData = TransformToTreeFormat(ResultMsg.resultData, "id",
                "parentId", "ChildLists");
        var ParentTree = new Array();
        var ParentData = new Object;
        ParentData.id = "";
        ParentData.parentId = 0;
        ParentData.name = "全部";
        ParentData.ChildLists = TreeData;
        ParentTree.push(ParentData);

        selectSource = new kendo.data.HierarchicalDataSource(
        {
            data : ParentTree,
            schema :
            {
                model :
                {
                    children : "ChildLists"
                }
            }
        });
    }

    function initUsersInfo()
    {
        var usersObj = doAjax("${ctx}/Zgroup/getUsersByGroupId.json?groupId="
                + multiUserFormObj.groupId, "POST");
        multiUserFormObj.users = usersObj;

        if (usersObj != null)
        {
            var userIds = "";
            for ( var i = 0; usersObj.length > i; i++)
            {
                if (i != 0)
                {
                    userIds += ",";
                }
                userIds += usersObj[i].userId;
            }
            var multiSelect = $("#users").data("kendoMultiSelect");
            multiSelect.value(userIds.split(","));
            //multiSelect.open();
            //multiSelect.refresh();
        }
    }

    function initUserMutiSelector()
    {
        $("#users").kendoMultiSelect(
        {
            dataTextField : "name",
            dataValueField : "id",
            itemTemplate : "#= name#",
            tagTemplate : "#: name #",
            autoClose : false,
            dataSource : userDataSource,
            delay : 500,//大数据需要调高一些
            height : 260,
            toolbar :
            {
                show : true,
                showText : true
            },//默认值:false 显示工具按钮条
            open : function(e)
            {
                this.list.width(240);
            }
        });
    }

    function readUserByOrgId()
    {

        userDataSource = new kendo.data.DataSource(
        {
            transport :
            {

                read : function(options)
                {

                    $.ajax(
                    {
                        url : "${ctx}/Zgroup/readUserByOrgId.json?orgId="
                                + $("#orgId").val() + "&groupId="
                                + groupInfo.groupId,
                        dataType : "json",
                        success : function(result)
                        {
                            options.success(result);
                        },
                        error : function(result)
                        {
                            options.error(result);
                        }
                    });
                }
            }

        });
    }

    //Tab单项激活事件
    function OnTabActivate(e)
    {
        // var TabText = $(e.item).find("> .k-link").text();
        // RefreshTabContent(TabText);

    }
    //刷新Tab页内容
    function RefreshTabContent(TabText)
    {

    }

  //验证
    function checkForm() {
        if (validator.validate()) {
            return true;
        }else{
            return false;
        }
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
       
        if(winName==null)
            closewindow("div");
        else
        {
           closewindow("div",winName);
            winName=null;
        }
        userGrid.dataSource.read();
    });
    //监听保存按钮
    $("#submit_confirm").click(
            function()
            {
             
                if (EditType == "edit")
                {
                    var multiSelect = $("#users").data("kendoMultiSelect");
                    multiUserFormObj.userIds = multiSelect.value();
                    if(multiSelect.value().length==0)
                        {
                        AlertDialog(
                                {
                                    Title : "提示信息",
                                    Message : "至少选择一个用户！",
                                    Icon : "k-ext-information"
                                });
                        }
                    else{
                        AjaxDoPost("${ctx}/Zgroup/saveUsers.json", "models="
                                + JSON.stringify(multiUserFormObj), false,
                                editSuccess, editError);
                        
                    }

                   
                }

            });

    //监听退出按钮
    $("#SUcancel").click(function()
    {
        if(winName==null)
            closewindow("div");
        else
        {
           closewindow("div",winName);
            winName=null;
        }
        userGrid.dataSource.read();
    });
    
    function checkForm() {
        if (validator.validate()) {
            return true;
        }else{
            return false;
        }
    }
    
    //监听保存按钮
    $("#SUsubmit_confirm").click(
            function()
            {
               
                if (EditType == "edit")
                {
                    if(checkForm())
                        AjaxDoPost("${ctx}/Zgroup/saveSingleUser.json", "models="
                            + JSON.stringify(singleUserFromObj), false,
                            editSuccess, editError);
                }

            });

    editSuccess = function(result)
    {
       
        if (result.resultCode === 0)
        {
            userGrid.dataSource.read();
            AlertDialog(
            {
                Title : "提示信息",
                Message : "修改成功！",
                Icon : "k-ext-information"
            });
            if(winName==null)
                closewindow("div");
            else
             {
                closewindow("div",winName);
                 winName=null;
             }
        }
        else
        {
            alert(result.resultMsg);
        }
    }

    function editError(result)
    {
        AlertDialog(
        {
            Title : "提示信息",
            Message : "修改失败！",
            Icon : "k-ext-error"
        });
    }
</script>
</body>
</html>