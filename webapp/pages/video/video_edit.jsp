<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String basePath = ip+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="${ctx}/assets/kendoUI/js-ext/kendo.upload.ext.js"></script>
<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
<script src="${ctx}/assets/_base/js/jquery.form.js"></script>
<script src="${ctx}/comm/js/core.js"></script>
<script src="${ctx}/assets/cms/cms.js"></script>
	<script type="text/javascript">
    	var path = "<%=path%>";
</script>
</head>
<body>
<div id="form-container-edit">
	<form id="dataform" action="${ctx}/cms/video/addAndEdit.json" name="dataform" method="post">
      		<input type="hidden" id="id" name="id" data-bind="value:id"/>
      		<input type="hidden" id="attachId" data-bind="value: attachId"/>
		<table class="xjj-table-detail" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<th width="100">文件标题<span style="color: red;"> *</span></th>
				<td width="50%"><input id="name" class="k-textbox" required="required" validationMessage="请输入文件标题" style="width: 95%" name="name" data-bind="value: name" />
			</td>
			<th>所属栏目</th>
			<td>
				<div id="menuId_div" data-bind="value: menuId" style="z-index: 15000;width: 90%;" ></div>
				<input type="hidden" id="menuId" name="menuId" data-bind="value:menuId" />
				<input type="hidden" id="menuName" name="menuName" data-bind="value:menuName" />
			</td>
		</tr>
		<tr>
			<th>备注</th>
			<td colspan="3">
				<textarea id="remark" class="k-textbox" style="width: 98%;height: 60px" name="remark" data-bind="value: remark"></textarea>
			</td>
		</tr>
		<%--<tr>
			<th width="100">代表性图片<span style="color: red;"> *</span></th>
			<td colspan="3">
					<input id="img_files" type="file" style="width: 50px;" name="files" class="k-textbox" text="选择图片"/>
					<input id="img_filepath" type="hidden" data-bind="value: img_filepath"/>
					<div id ="img_affix"></div>
            </td>
		</tr>--%>
		<tr>
			<th width="100">视频附件<span style="color: red;"> *</span></th>
			<td colspan="3" height="120">
				<input type="hidden" id="video_filepath" name="video_filepath" data-bind="value:video_filepath" />
				<input type="hidden" id="video_filename" name="video_filename" data-bind="value:video_filename" />
				<input type="hidden" id="video_ext" name="video_ext" data-bind="value:video_ext" />
				<input type="hidden" id="video_filesize" name="video_filesize" data-bind="value:video_filesize" />
				<input type="hidden" id="video_localname" name="video_localname" data-bind="value:video_localname" />


				<input type="hidden" id="img_filename" name="img_filename" data-bind="value:img_filename" />
				<input type="hidden" id="img_filepath" name="img_filepath" data-bind="value:img_filepath" />
				<input type="hidden" id="img_ext" name="img_ext" data-bind="value:img_ext" />
				<input type="hidden" id="img_filesize" name="img_filesize" data-bind="value:img_filesize" />
				<input type="hidden" id="img_localname" name="img_localname" data-bind="value:img_localname" />

	
				<iframe id="hidden_iframe" name="videoIframe" src="${ctx}/pages/video/upVideoForm.jsp" width="98%" height="100" style="overflow: hidden;">
				</iframe>
	             <%-- 	<input id="video_files" type="file" style="width: 50px;" name="files" class="k-textbox" text="选择视频"/>
	              	<input id="video_filepath" type="hidden" data-bind="value: video_filepath"/>
	              	<div id ="video_affix"></div>--%>
            </td>
		</tr>
	</table>
	</form>
<div class="clear" style="height: 10px"></div>
	<div align="center">
		<button id="submit_confirm" class="k-button width70" >保存</button>
		<button id="cancel" class="k-button width70">退出</button>
	</div>
</div>

<script type="text/javascript">
    //检验器
    var validator = null;

    $(document).ready(function () {
        //初始化表单
        InitForm();
        //kendoui校验器初始化
        validator = $("#dataform").kendoValidator().data("kendoValidator");
    });

    //初始化表单
    function InitForm() {
      	Item = kendo.observable(Item);
//       	if (EditType == "add") {//VIEW_MODEL层的数据初始化
// 			Item.set("menuId",menuId);
// 			Item.set("menuName",menuName);
// 		}

        kendo.bind($("#form-container-edit"), Item);
        var source =  new kendo.data.DataSource({
            transport :{
                read : function(options){
                    $.ajax({
                        url : "${ctx}/cms/menu/getVideoMenu.json",
                        dataType : "json",
                        success : function(result){
                            options.success(result);
                        },
                        error : function(result){
                            options.error(result);
                        }
                    });
                }
            }
        });
   	 $("#menuId").kendoDropDownList({
   		 	optionLabel: "--请选择栏目--",
            dataTextField: "menu_name",
            dataValueField: "id",
            itemTemplate:"#= menu_name#",
            tagTemplate: "#: id #",
            pinyin:true,
            dataSource: source,
            delay: 500,//大数据需要调高一些
            height: 350,
            maxSelectedItems: 1,
            autoClose: true,
            select: function(e) {
           	    $("#menuName").val(e.item.text());
       	 	}
        });
        
// 		加载栏目树
// 		var loadUserMenuTree = function() {
// 			JsonPostForWaiting({
// 				Url : path + "/cms/menu/userMenuTree.json?showMode=0,1",
// 				Url : path + "/cms/menu/getVideoMenu.json",
// 				WaittingText : "正在加载栏目...",
// 				AimDiv : "#LeftLayout",
// 				Success : function(ResultMsg, Parameter) {
// 					var data = "";
// 					if (ResultMsg.ResultMsg.resultData != null) {
// 						data = ResultMsg.ResultMsg.resultData;
// 					}
// 					var TreeData = TransformToTreeFormat(data, "id",
// 							"parentMenuId", "ChildLists");
// 					var ParentTree = new Array();
// 					var ParentData = new Object;
// 					ParentData.id = "0";
// 					ParentData.parentMenuId = "0";
// 					ParentData.menuName = "栏目列表";
// 					ParentData.ChildLists = TreeData;
// 					ParentTree.push(ParentData);
// 					selectSource = new kendo.data.HierarchicalDataSource({
// 						data : ParentTree,
// 						schema : {
// 							model : {
// 								children : "ChildLists"
// 							}
// 						}
// 					});
// 				},
// 				Error : function(ResultMsg, Parameter) {
// 					DetailAlertDialog({
// 						Title : "出错信息",
// 						Message : "加载栏目树失败！",
// 						DetailMessage : GetErrorMsg(ResultMsg),
// 						Icon : "k-ext-error"
// 					});
// 				}
// 			});
// 		}

// 		展开栏目树
// 		var expandMenuTree = function() {
// 			menuTree.expand(".k-item");
// 		}

// 		收缩栏目树
// 		var collapseMenuTree = function() {
// 			menuTree.collapse(".k-item");
// 		}
	/**
	 * 下拉栏目权限树
	 */
// 	var selectUserMenu = function() {
// 		selectMenuTree = CreateDropDownTreeView({
// 			ID : "menuId_div",
// 			AutoBind : true,
// 			DataSource : selectSource,
// 			TextField : "menuName",
// 			Height: 400,
// 			OnSelect : function(e) {
// 				var dataItem = this.dataItem(e.node);
// 				var menuId = dataItem.id;
// 				var menuName = dataItem.menuName;
// 				Item.menuId = menuId;
// 				Item.menuName = menuName;
// 				$("#menuId").val(menuId);
// 				$("#menuName").val(menuName);
// 			}
// 		});
// 	}
// 		loadUserMenuTree();
// 		selectUserMenu();

// 		if(Item.menuId==0){
// 			selectMenuTree.selectItem("栏目列表");
// 		}else{
// 			selectMenuTree.selectItem(Item.menuName);
// 		}
    }

    //删除附件
    function delelAffix(type,id){
     	$.ajax({
             url : "${ctx}/cms/video/delelAffixByIdAndType.json",
             dataType : "json",
             data:{
             	type:type,  //文件的类型，图片还是视频
             	id:id
             },
             success : function(result){
             	if(type == "img"){
             		$("#img_filepath").val("");
	             	$("#img_affix").css("display","none");
             	}else{
             		$("#video_filepath").val("");
	             	$("#video_affix").css("display","none");
             	}
             },
             error : function(result){
             	alert("删除失败！");
             }
        });
     }

     //下载附件
    function downAffix(type, id){
          	window.open("${ctx}/cms/video/downAffixByDownloadPath.json?type="+type+"&id="+id);
    }

	//监听退出按钮
	$("#cancel").click(function () {
		if(EditType == "edit"){
			if($("#video_filepath").val()== ""){
				AlertDialog({
		            Title: "提示信息",
		            Message: "还有未上传的附件选项，请添加附件！",
		            Icon: "k-ext-information"
		        });
				return;
			}
		};
		closewindow("div");
		grid.dataSource.read();
	});
	//监听保存按钮
	$("#submit_confirm").click(function () {
		Item.read_count = 0;
		var options = {
       		 data:{
       		 	editType:EditType
       		 },
	         //type:'post',
	         success:function(data){
	        	 var jsonDate = eval( "(" + data + ")" );
	        	 if (jsonDate.resultCode === 0){
	        		  window.location.href= path+"/pages/video/video_list.jsp?dsq=1";
					 closewindow("div");
				 }else{
	      	          alert(decodeURIComponent(jsonDate.resultMsg,"utf-8"));
	      	      }
	         },
	         error:function(){
	        	 alert("保存失败");
	         }
		};

	    if(checkForm()){
			var itemMenuId = Item.menuId;
			if("" == itemMenuId || undefined == itemMenuId || "0" == itemMenuId){
				alert("请选择栏目");
				return ;
			}
    		Item.menuId = $("#menuId").val();
    		Item.menuName = $("#menuName").val();
			var filePath = $("#video_filepath").val();
			if(null != filePath && "" != filePath && undefined != filePath){
				$("#dataform").ajaxSubmit(options);
			}else{
				AlertDialog({
					Title: "提示信息",
					Message: "还有未上传的附件选项，请添加附件！",
					Icon: "k-ext-information"
				});
				return;
			}
	    }
	});

	//验证
	function checkForm() {
	    if (validator.validate()) {
	        return true;
	    }else{
	    	return false;
	    }
	}

    //修改成功回调函数
	function editSuccess(result) {
	    if (result.resultCode === 0) {
	        AlertDialog({
                Title: "提示信息",
                Message: "编辑成功!",
                Icon: "k-ext-information"
            });
            closewindow("div");
	    }
	    else {
	        alert(result.resultMsg);
	    }
	}

	//新增成功回调函数
	function addSuccess(result) {
	    if (result.resultCode === 0) {
	        grid.dataSource.read();
	        $("#submit_confirm").hide();
	        saveSuccess({
	        	fileId:"files",
				module:"testuser",
				dir:"info",
				dataId:result.resultData
			});

	        AlertDialog({
                Title: "提示信息",
                Message: "新增成功!",
                Icon: "k-ext-information"
            });
            closewindow("div");
	    }
	    else {
	        alert(result.resultMsg);
	    }
	}

    //新增失败回调函数
    function addError(result) {
    	AlertDialog({
            Title: "提示信息",
            Message: "添加失败！",
            Icon: "k-ext-error"
        });
    }

    //修改失败回调函数
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


