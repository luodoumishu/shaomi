<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/comm/kendo_css.jsp"%>
<%@ include file="/comm/kendo_js.jsp"%>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
<link href="${ctx}/assets/ace/css/font-awesome.css" rel="stylesheet">




<title>组管理</title>
</head>
<body>

	<div id="MainLayout" style="height: 700px;">
		<div id="LeftLayout">
			<div class="k-toolbar">
				<div class="xjj-tree-toolbar">

					<div class="xjj-group">
						<div class="xjj-l-bar-separator" id="TreeRefreshSeparator"></div>
						<a id="AddGroupTypeButton" class="k-button "> <span
							class=" icon-plus-sign-alt"></span>&nbsp添加组类型 </a>
					</div>

					<div class="xjj-group">
						<div class="xjj-l-bar-separator"></div>
						<a id="DelGroupTypeButton" class="k-button"> <span
							class="icon-trash"></span>&nbsp删除组类型 </a>
					</div>

					<div class="xjj-group">
						<div class="xjj-l-bar-separator"></div>
						<a id="updateGroupTypeButton" class="k-button"> <span
							class="icon-edit"></span>&nbsp修改组类型 </a>
					</div>
				</div>
			</div>
			<div id="treeviewBar"></div>
			<div id="treeview"></div>
		</div>
		<div id="RightLayout">
			<div id="groupGrid"></div>
			<div id="userGrid"></div>
			<div id="GroupType"></div>
		</div>
	</div>

</body>
<script>
    //工具栏

    var groupToolBar = null;
    var userToolBar = null;
    //数据源对象
    var groupDataSource = null;
    var userDataSource = null;
    var treeDataSource = null;
    var groupGrid = null;
    var userGrid = null;
    var groupTree = null;
    //列表对象,用于对表格数据的操作

    //查询条件对象
    var queryObj = null;
    //数据对象，用于新增和修改
    var Item = null;

    var groupInfo = null;
    //编辑的类型,默认为新增
    var EditType = "add";

    var currentGroupName = null;
    var currentGroupId = null;

    var groupTypeInfo = null;
    var winName =  null;
    var onUpdate =null;
    $(document).ready(function()
    {
        splitWindows();
       // initGroupTree();
        initTree();

        showGroupGrid(0);
    });

    $("#AddGroupTypeButton").click(
                    function()
                    {
                        openwindow(
                                "${ctx}/pages/groupUser/groupType_edit.jsp?editType='add'",
                                500, 180, "add", "div");
                    });

    $("#DelGroupTypeButton").click(function()
    {
        delGroupType();
    });

    $("#updateGroupTypeButton").click(function()
    {
        updateGroupType();
    });

    function updateGroupType()
    {

        var selectedNode = groupTree.select();
        var selectItemData = groupTree.dataItem(selectedNode);

       //console.log("dfieifmdskfoew");
        if (selectItemData != null && selectItemData.type == 1)
        {

            groupTypeInfo = new Object();
            groupTypeInfo.id = selectItemData.id;
            groupTypeInfo.typeName = selectItemData.text;
            openwindow(
                    "${ctx}/pages/groupUser/groupType_edit.jsp?editType='edit'",
                    500, 180, "", "div");
           
        }
        else
        {
            AlertDialog(
            {
                Title : "提示信息",
                Message : "请选择要修改的组类型！",
                Icon : "k-ext-error"
            });
        }

    }

    function delGroupType()
    {

        var selectedNode = groupTree.select();
        var selectItemData = groupTree.dataItem(selectedNode);

        if (selectItemData != null && selectItemData.type == 1)
        {
            if (confirm("删除组类型会附带删除组类型下所有成员信息！你确定删除吗？"))
            {
                AjaxDoPost("${ctx}/Zgroup/delGroupType.json", "typeId="
                        + selectItemData.id, false, delGroupTypeSuccess,
                        delGroupTypeError);

                groupTree.remove(selectedNode);
                groupTree.expand(".k-item");
            }

        }
        else
        {
            AlertDialog(
            {
                Title : "提示信息",
                Message : "请选择要删除的组类型！",
                Icon : "k-ext-error"
            });
        }

    }

    //删除成功回调函数
    function delGroupTypeSuccess(result)
    {
        if (result.resultCode === 0)
        {

            showGroupGrid(0);

            AlertDialog(
            {
                Title : "提示信息",
                Message : "删除成功！",
                Icon : "k-ext-information"
            });
        }
        else
        {
            alert(result.resultMsg);
        }
    }

    //删除失败回调函数
    function delGroupTypeError(result)
    {
        AlertDialog(
        {
            Title : "提示信息",
            Message : "删除失败！",
            Icon : "k-ext-error"
        });
    }

    //新增初始化
    onAdd = function()
    {
        Item = new Object;
        //编辑的类型：新增
        EditType = "add";
        //可以在这里进行新增页面的初始化值设置
        /*Item.fjgdm = fjgdm;
        Item.fgzdw = fgzdw;
        Item.fxb = 0;
        Item.fzgxl = 1;
        Item.fzy = 1;
        Item.fdj = 1;*/
        return true;
    }

    //修改初始化

    function updateGroup()
    {
        var row = groupGrid.select();
        selectRowIndex = row.index();
        Item = groupGrid.dataItem(row);
        //编辑的类型：修改
        EditType = "edit";
        if (Item == null)
        {
            AlertDialog(
            {
                Title : "提示信息",
                Message : "请选择您要编辑的数据！",
                Icon : "k-ext-warning"
            });
            return false;
        }
        else
        {
            return true;
        }
    }

    function updateUser()
    {

        groupInfo = Object();

        groupInfo.groupId = currentGroupId;
        groupInfo.groupName = currentGroupName;
        var row = userGrid.select();
        var data = userGrid.dataItem(row);
        if (data == null)
        {
            groupInfo.editType = "multiEdit";
            //编辑的类型：修改
            EditType = "edit";
            return true;
        }
        else
        {
            groupInfo.editType = "singleEdit";
            EditType = "edit";
            groupInfo.singleUserData = data;
            return true;
        }

    }

    function selectRowForDel()
    {
        var row = groupGrid.select();
        var data = groupGrid.dataItem(row);
        if (data == null)
        {
            AlertDialog(
            {
                Title : "提示信息",
                Message : "请选择您要删除的数据！",
                Icon : "k-ext-warning"
            });
            return false;
        }
        else
        {
            return true;
        }
    }

    function delGroupAction()
    {
        var row = groupGrid.select();
        var datas = new Array();
        for ( var i = 0; i < row.length; i++)
        {
            var data = groupGrid.dataItem(row[i]);
            datas.push(data);
        }
        if (typeof (data) != undefined)
        {
            AjaxDoPost("${ctx}/Zgroup/delGroup.json", "models="
                    + JSON.stringify(data), true, deleteGroupSuccess,
                    deleteGroupError);
        }
    }

    //删除成功回调函数
    function deleteGroupSuccess(result)
    {
        if (result.resultCode === 0)
        {
            var row = groupGrid.select();
            for ( var i = 0; i < row.length; i++)
            {
                groupGrid.removeRow(row[i]);
            }
            groupGrid.dataSource.read();

            AlertDialog(
            {
                Title : "提示信息",
                Message : "删除成功！",
                Icon : "k-ext-information"
            });
            groupTree.destroy();
            initTree();
            groupTree.expand(".k-item");

        }
        else
        {
            alert(result.resultMsg);
        }
    }

    //删除失败回调函数
    function deleteGroupError(result)
    {
        AlertDialog(
        {
            Title : "提示信息",
            Message : "删除失败！",
            Icon : "k-ext-error"
        });
    }

    function createGroupToolBar()
    {
        //创建工具栏按钮
        groupToolBar = new CreateToolBarButton(
        {
            GridId : "groupGrid",
            Url : "${ctx}/pages/groupUser/group_edit.jsp",
            Add : true,
            Edit : true,
            Del : true,
        });
    }
    function createUserToolBar()
    {
        //创建工具栏按钮
        userToolBar = new CreateToolBarButton(
        {
            GridId : "userGrid",
            Url : "${ctx}/pages/groupUser/user_edit.jsp",
            Edit : true,
            Width:500,
            Height:250
            
        });
    }

    function getGroupData(goupTypeId)
    {
        //数据源
        groupDataSource = CreateGridDataSource(
        {
            Transport :
            {
                ReadUrl : "${ctx}/Zgroup/listGroup.json",
                ReadDataFunc : function()
                {
                    queryObj = new Object;
                    queryObj.elementId = goupTypeId;
                    return queryObj;
                }
            }
        });
    }

    function getUsersData(groupId)
    {
        //数据源
        if(groupId!=null&&groupId!="")
            currentGroupId = groupId;
        userDataSource = CreateGridDataSource(
        {
            Transport :
            {
                ReadUrl : "${ctx}/Zgroup/listUser.json",
                ReadDataFunc : function()
                {
                    queryObj = new Object;
                    queryObj.elementId = currentGroupId;
                    return queryObj;
                }
            }
        });
    }

    function loadGroupGrid()
    {
        //数据窗口

        groupGrid = CreateGrid(
        {
            ID : "groupGrid",
            DataSource : groupDataSource,
            ToolBarTemplate : groupToolBar.Str,
            Height : 598,
            Editable : "popup",
            Columns : [
            {
                field : "groupName",
                width : 200,
                title : "组名称"
            },
            {
                field : "remark",
                width : 200,
                title : "备注"
            } ]
        });
    }

    function loadUserGrid()
    {
        //数据窗口

        userGrid = CreateGrid(
        {
            ID : "userGrid",
            DataSource : userDataSource,
            ToolBarTemplate : userToolBar.Str,
            Height : 598,
            Editable : "popup",
            Columns : [
            {
                field : "userId",
                template: "<strong><a href='javascript:test()'>#: userId # </a></strong>",
                width : 200,
                title : "用户ID"
            },
            {
                field : "userName",
                width : 200,
                title : "用户名称"
            },
            {
                field : "remark",
                width : 200,
                title : "备注"
            },
            {
                field : "pri",
                width : 100,
                title : "优先级"
            }]
        });

    }

    test=function()
    {
        updateUser();
         winName ="修改用户";
        openwindow("${ctx}/pages/groupUser/user_edit.jsp", $(
                window).width() * 0.5, $(window).height() * 0.4, "", "div",winName);
    };
    
    function splitWindows()
    {
        //页面分隔
        $("#MainLayout").kendoSplitter(
        {
            panes : [
            {
                collapsible : true,
                size : "20.5%"
            },
            {
                collapsible : true,
                size : "80%"
            }, ]
        });
    }

    function initTree()
    {
        AjaxDoPost("${ctx}/Zgroup/getGroupTypeTree.json", "", false,
                getGroupTypeSuccess, getGroupTypeError);

    }

    function getTreeDataSource()
    {
        treeDataSource = new kendo.data.HierarchicalDataSource(
        {
            transport :
            {
                read :
                {
                    url : "${ctx}/Zgroup/getGroupTypeTree.json",
                    dataType : "json"
                }
            },
            schema :
            {
                data : function(response)
                {
                    return eval(response.resultData);
                },
                model :
                {
                    id : "id",
                    dataTextField : "text",
                    hasChildren : function(item)
                    {
                        return item.items != null;
                    }
                }
            }

        });

    }

    function initGroupTree()
    {
        getTreeDataSource();
        $("#treeview").kendoTreeView(
                {
                    template : "#= item.text #",
                    dataTextField : "text",
                    dataSource : treeDataSource,
                    change : function(e)
                    {

                    },
                    select : function(e)
                    {
                      
                        if (this.dataItem(e.node).type == 2)
                        {
                            currentGroupId = this.dataItem(e.node).id;
                            currentGroupName = this.dataItem(e.node).text;
                        }

                        queryGroupOrUser(this.dataItem(e.node).type, this
                                .dataItem(e.node).id);
                    }
                });

    }

    function getGroupTypeSuccess(result)
    {
        var treeDataSource = eval(result.resultData);
        $("#treeview").kendoTreeView(
                {
                    template : "#= item.text #",
                    dataSource : treeDataSource,
                    animation :
                    {
                        expand : true
                    },

                    change : function(e)
                    {

                    },
                    select : function(e)
                    {
                   
                        if (this.dataItem(e.node).type == 2)
                        {
                            currentGroupId = this.dataItem(e.node).id;
                            currentGroupName = this.dataItem(e.node).text;
                        }

                        queryGroupOrUser(this.dataItem(e.node).type, this
                                .dataItem(e.node).id);
                    }
                });
        groupTree = $("#treeview").data("kendoTreeView");
        groupTree.expand(".k-item");
    }

    function getGroupTypeError(result)
    {
        AlertDialog(
        {
            Title : "提示信息",
            Message : "查询失败！",
            Icon : "k-ext-error"
        });

    }

    function showGroupGrid(id)
    {
        $("#userGrid").hide();
        createGroupToolBar();
        getGroupData(id);
        loadGroupGrid();
        $("#groupGrid").show();
        onUpdate = updateGroup;
        del = delGroupAction;
        onDelete = selectRowForDel;
    }

    function queryGroupOrUser(type, id)
    {

        if (type == 1 || type == 0)
        {
            showGroupGrid(id);
        }
        else if (type == 2)
        {
            $("#groupGrid").hide();
            createUserToolBar();
            getUsersData(id);
            loadUserGrid();
            $("#userGrid").show();
            onUpdate = updateUser;
        }
    }
</script>

<script id="alt-template" type="text/x-kendo-template">
    <tr data-uid="#= uid #">
        <td colspan="4">
            <strong>#: userId #</strong>
            <strong>#: userName #</strong>
             <strong>#: remark #</strong>
             <strong>#: pri #</strong>
        </td>
    </tr>
</script>
</html>