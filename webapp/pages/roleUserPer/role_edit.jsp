<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
    <script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
    <%@ include file="/pages/addressBookJqueryPlugin/addressBookLib.jsp"%>
</head>
<body>
<div id="DataTab">
	<ul>
        <li id="FinanceItemTab" class="k-state-active">角色信息</li>
    </ul>
    <div id="form-container-edit">
    <form id="dataform" name="dataform" method="post">
        <input type="hidden" id="id" name="id" data-bind="value:id" />
        <table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
           <tr>
                <th width="20%">角色名称</th>
                <td width="30%">
                    <input id="name" class="k-textbox" required="required" validationMessage="请输入角色名称" style="width: 180px" name="name" data-bind="value: name"/>
                </td>
                <th width="20%">使用状态</th>
                <td>
                	<input id="isValid" name="isValid" data-bind="value: isValid" style="width: 180px"/>
                </td>
            </tr>
            <tr>
                <th width="20%">系统名</th>
                <td >
                	 <input id="module" style="width: 180px" name="module" data-bind="value: module"/>
                </td>
                <th width="20%">排序号</th>
                <td>
                    <input id="pri" name="pri" data-bind="value: pri" style="width: 180px"/>
                </td>
            </tr>
            <tr>
                <th width="20%">权限</th>
                <td colspan="3">
                	<select id="permissions" multiple="multiple" name="permissions" data-bind="value: permissions" style="width: 82%"></select>
                </td>
            </tr>
            <tr>
                <th width="20%">用户</th>
                <td colspan="3">
                	<!-- <select id="userIds" multiple="multiple" name="userIds" data-bind="value: userIds" style="width: 82%"></select> -->
                	<div id='oaUserAddreessBookWrapper' ></div>
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
	//下拉数据源
	var selectSource = null;
	var SelectCorp = null;
	var permissionsDataSource = null;
	var userIds = "";
    $(document).ready(function () {
        //初始化表单
        InitForm();
        //kendoui校验器初始化
        validator = $("#dataform").kendoValidator().data("kendoValidator");
    	//标签页
	    $("#DataTab").kendoTabStrip({
            activate: OnTabActivate
        });
        $("#isValid").kendoDropDownList({
			dataTextField: "text",
			dataValueField: "value",
			dataSource: [
				{ text: "启用", value: 1},
	 			{ text: "禁用", value: 0}
			],
			change: function(e) {
		 		$("#isValid").val(this.value());
		 		Item.set("isValid",this.value());
			}
		});
    });
    
    //初始化表单
    function InitForm() {
    	//数据模型绑定,MVVM
    	Item = kendo.observable(Item);
    	//新建对象绑定模型
		if (EditType == "add") {//VIEW_MODEL层的数据初始化
			Item.set("pri",0);
			Item.set("isValid",1);
		}
		$("#pri").kendoNumericTextBox({
			decimals : 0,//小数位数，即VIEW_MODEL层的精度
			value: Item.pri,//初始值，VIEW层
			min : 0,//最小值
			format: "0",
			step : 1,//每次点击增减按钮的浮动值
     	});
     	
     	//海航版本不支持autoClose
     	//官网新版本不支持dataSeachField
     	$("#userIds").kendoMultiSelect({
            dataTextField: "showName",
            dataValueField: "id",
            itemTemplate:"#= showName#",
            tagTemplate: "#: name #",
            autoClose : false,
            pinyin:true,
            dataSource: {
                transport: {
                    read: {
                        url: "${ctx}/zuser/read.json",
                    }
                }
            },
            delay: 500,//大数据需要调高一些
            height: 260,
            toolbar:{show: true ,showText: true}
        }); 
     	
        if(Item!=null && Item.users!=null){
	        for(var i=0;Item.users.length>i;i++){
	        	if(i!=0){
	        		userIds+=",";
	        	}
	        	userIds+=Item.users[i].id;
	        }
	        //var multiSelect = $("#userIds").data("kendoMultiSelect");
	        //multiSelect.value(userIds.split(","));
	        //multiSelect.open();
	        //multiSelect.refresh();
        }
        
        permissionsDataSource = new kendo.data.DataSource(
        {
            transport :
            {
                read : function(options)
                {
                    $.ajax(
                    {
                        url : "${ctx}/zrole/readPermissions.json?module="+$("#module").val(),
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
        
        $.ajax({
    		url:"${ctx}/zrole/readModule.json",
    		type:"post",
    		cache : false,
			async : false,
    		dataType:"json",
    		success:function (callback){
       			var dropdownlist = $("#module").kendoDropDownList({
				 	dataTextField: "name",
				 	dataValueField: "id",
				 	dataSource: callback,
				 	change: function(e) {
				 		var multiSelect = $("#permissions").data("kendoMultiSelect");
	        			multiSelect.value("");
				 		$("#module").val(this.value());
				 		Item.set("module",this.value());
				 		permissionsDataSource.read();
					}
				}).data("kendoDropDownList");
				if (EditType == "add") {//VIEW_MODEL层的数据初始化
					Item.set("module",dropdownlist.value());
					$("#module").val(dropdownlist.value());
				}else{
					if(Item.module==null){
						Item.set("module",dropdownlist.value());
						$("#module").val(dropdownlist.value());
					}else{
						$("#module").val(Item.module);
					}
				}
    		},
    		error:function(){
    			alert("程序出错");
    		}
    	});        
        
        
        
        $("#permissions").kendoMultiSelect({
            dataTextField: "name",
            dataValueField: "id",
            itemTemplate:"#= name#",
            tagTemplate: "#: name #",
            autoClose : false,
            dataSource: permissionsDataSource,
            delay: 500,//大数据需要调高一些
            height: 260,
            toolbar:{show: true ,showText: true},//默认值:false 显示工具按钮条
            open: function (e) { this.list.width(240); }
        }); 
        if(Item!=null && Item.permissions!=null){
	        var multiSelect = $("#permissions").data("kendoMultiSelect");
	        multiSelect.value(Item.get("permissions").split(","));
	        multiSelect.open();
	        multiSelect.refresh();
        }
        
        //var parentId = "${orgId}";
        $('#oaUserAddreessBookWrapper').addressBook({
			//用户多选框ID（必填）
			userMultiSelectorId : "oaUserSelector",
			//地址本展现形式 TREE 为树形结构 FLAT 为扁平型结构,Ex:["TREE","FLAT"] 或 ["FLAT","TREE"] 或 ["TREE"] 或 ["FLAT"]
			showType : ["ORG"],
			//指定地址本顶级部门
			//assignOrgId :parentId,
			//指定默认选中用户（数组）
			initSelectUser:function(){
				var userList =  new Array();
				if(userIds!=null && userIds.length>0){
					userList = userIds.split(",");
				}
				return userList;
			},
			searchBoxWidth : "82%",
			//地址本按钮名称
			buttonName:"地址本",
			//支持拼音搜索
			isSupportPinyin : true,
			//指定是否显示组 true:显示 false：不显示
			isShowGroup : false,
			//打开地址本窗口用户自定义校验,true:校验通过，false:校验不通过
			validateCondition :function()
			{
				return true;
			},
			//地址选中用户改变时的回调函数
			userChangeCallBack:function(dataItems){
				
			}
		});
        
        
	    //数据模型绑定
        kendo.bind($("#form-container-edit"), Item);
        
    }
    
	//监听退出按钮
	$("#cancel").click(function () { closewindow("div"); grid.dataSource.read();});
	//监听保存按钮
	$("#submit_confirm").click(function () {
	    if (checkForm()) {
	        if (EditType == "edit") {
	            AjaxDoPost("${ctx}/zrole/update.json", "models=" + JSON.stringify(Item), false, editSuccess, editError);
	        }
	        else if (EditType == "add") {
	        	AjaxDoPost("${ctx}/zrole/add.json","models=" + JSON.stringify(Item),false,addSuccess,addError);
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
	    	var permissions = $("#permissions").data("kendoMultiSelect");
	    	Item.set("permissions",permissions.value()+"");
	    	var newUserIds = "";
	    	var dataObj = $('#oaUserAddreessBookWrapper').data("xjjAddressBook");
			dataObj.selectUserLinkMap.each(function(key,value,index){
				if(index!=0){
					newUserIds += ",";
				}
				newUserIds += value.id ;
			});
			if(newUserIds !=""){
				Item.set("userIds",newUserIds);
			}else{
	    		Item.set("userIds",userIds);
			}
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
    
    /* function onChange() {
    	var that = $("#userIds").data("kendoMultiSelect");
    	var value = that.value();

        if (!compare(value, that._old)) {
            that._old = value.slice();

            that.trigger(CHANGE);

            that.element.trigger(CHANGE);
        }
        e.preventDefault();
    } */

</script>
</body>
</html>


