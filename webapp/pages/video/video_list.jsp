<%
    String path = request.getContextPath();
    String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>附件管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>
    <style>

        .box_text{
            width: 100%;
            height: 20px;
        }
        .progressBar { padding-top: 5px; }
        .totalProgressBarBox {
            width: 85%;
            border: 1px inset;
            background: #eee;
            float: left;
        }
        .totalProgressBarBoxContent {
            width: 0;
            height: 20px;
            border-right: 1px solid #444;
            background: #9ACB34;
        }
        .progressBarText{
            float: right;
            line-height: 20px;
            height: 20px;
            vertical-align: middle;
            text-align: left;
            color: #0000cc;
            font-size: 16px;
        }
    </style>
    <script type="text/javascript">var crudServiceBaseUrl = "${ctx}";</script>
    <script src="${ctx}/assets/_base/js/jquery.form.js"></script>
    <script src="${ctx}/comm/js/core.js"></script>
    <script src="${ctx}/assets/cms/cms.js"></script>
</head>
<body>
    <!-- 界面 -->
    <div id="MainLayout">
        <div id="Right">
            <div id="grid"></div>
        </div>
        <div style="display: none">
            <form id="dataform_hide" action="${ctx}/cms/video/addAndEdit.json" name="dataform" method="post" enctype="multipart/form-data">
                <input type="hidden" id="id" value="id"/>
                <input  type="file" name="files"  />
                <input  type="file"  name="files"/>
            </form>
        </div>
    </div>

    <!-- 脚本 -->
    <script type="text/javascript">
         var path = "<%=path%>";
          //树对象
          var ycjyjgTree = null;
		  //数据源对象
		  var dataSource = null;
		  //列表对象,用于对表格数据的操作
		  var grid = null;
		  //查询条件对象
		  var queryObj = null;
		  //数据对象，用于新增和修改
		  var Item = null;
		  //编辑的类型,默认为新增
		  var EditType = "add";
		  //下拉数据源
		  var selectSource = null;
		  //指标名称
		  var kpiName = null;
          // 栏目名称
          var menuName = null;
          //父栏目id初始值
          var menuId = "0";
          //跟节点
          var rootId = "0";
          //下拉栏目树
          var selectMenuTree = null;

        $(document).ready(function () {
        	//创建工具栏按钮
          	var ToolbarHtml = new CreateToolBarButton({
                  GridId: "grid",
                  Url: "${ctx}/pages/video/video_edit.jsp",
                  Add: true,
                  Edit: true,
                  Del: true
              });
        	
          //数据源
       	  dataSource = CreateGridDataSource({
       	    Transport: {
                 ReadUrl: "${ctx}/cms/video/list.json",
                 ReadDataFunc: function () {
               	  queryObj = new Object;
                	  queryObj.name=kpiName;
                     return queryObj;
                 }
                 }
       	  });
        	      
            //数据窗口
            grid = CreateGrid({
            ID: "grid",
            DataSource: dataSource,
            ToolBarTemplate: ToolbarHtml.Str,
            Height: $(window).height()*0.9,         
            Editable:"popup", 
            ColumnMenu: false,    
            Filterable: false,  
            Groupable: false, 
            Columns: [ 
                      { field: "name",title: "名称",width:"20%"},
                      { field: "menuName",width: "10%",title: "所属栏目名称"},
                      { field: "remark",width: "20%",title: "备注"},
                      {field:"",width:"30%",title:"转换状态",template:function(dataItem){
                          var state = dataItem.state;
                          if(3 != state){
                              var id = dataItem.id;
                              startProgress(id);
                              return '<div class="progressBar" id="progressBar_'+id+'">'+
                                      '<div id="theMeter_'+id+'">'+
                                      '<div class="box_text" id="box_text_'+id+'">'+
                                      '<div class="totalProgressBarBox" id="totalProgressBarBox_'+id+'">'+
                                      '<div class="totalProgressBarBoxContent" id="totalProgressBarBoxContent_'+id+'"></div>'+
                                      '</div>'+
                                      '<div class="progressBarText" id="progressBarText_'+id+'"></div>'+
                                      '</div>'+
                                      '</div>'+
                                      '</div>';
                             /* return '<div class="progressBar" id="progressBar_'+id+'">'+
                                      '<div class="theMeter" id="theMeter_'+id+'">'+
                                      '<div class="progressBarText" id="progressBarText_'+id+'"></div>'+
                                      '<div class="totalProgressBarBox" id="totalProgressBarBox_'+id+'">'+
                                      '<div class="totalProgressBarBoxContent" id="totalProgressBarBoxContent_'+id+'"></div>'+
                                      '</div>'+
                                      '</div>'+
                                      '<div class="progressStatusText"id="progressStatusText_'+id+'"></div>'+
                                      '</div>';*/
                          }
                          return "已完成";
                      }}]
             });

        });
        
      	//新增初始化
        onAdd = function () {
        	Item = new Object;
        	//编辑的类型：新增
     		EditType="add";
            //可以在这里进行新增页面的初始化值设置
            return true;
        };
      
        //修改初始化
    	onUpdate = function () {
            var row = grid.select();
            selectRowIndex = row.index();
            Item = grid.dataItem(row);
            //编辑的类型：修改
     		EditType="edit";
            if (Item == null) {
            	AlertDialog({
                    Title: "提示信息",
                    Message: "请选择您要编辑的数据！",
                    Icon: "k-ext-warning"
                });
                return false;
            }
            else {
                return true;
            }
        };
        
        //删除初始化
        onDelete = function () {
            var row = grid.select();
            var data = grid.dataItem(row);
            if (data == null) {
            	AlertDialog({
                    Title: "提示信息",
                    Message: "请选择您要删除的数据！",
                    Icon: "k-ext-warning"
                });
                return false;
            }
            else {
                return true;
            }
        };
        
        del = function () {
            var row = grid.select();
            var datas = new Array();
            for (var i = 0; i < row.length; i++) {
                var data = grid.dataItem(row[i]);
                datas.push(data);
            }
            if (typeof (data) != undefined) {
            	AjaxDoPost("${ctx}/cms/video/delete.json","models=" + JSON.stringify(data),true,deleteSuccess, deleteError);
            }
        };
        
         //删除成功回调函数
        function deleteSuccess(result) {
            if (result.resultCode === 0) {
                var row = grid.select();
                for (var i = 0; i < row.length; i++) {
                    grid.removeRow(row[i]);
                }
                grid.dataSource.read();
                AlertDialog({
                    Title: "提示信息",
                    Message: "删除成功！",
                    Icon: "k-ext-information"
                });
            }
            else {
            	AlertDialog({
	                Title: "提示信息",
	                Message: result.resultMsg,
	                Icon: "k-ext-error"
	            });
            }
        };

        //删除失败回调函数
        function deleteError(result) {
        	AlertDialog({
                Title: "提示信息",
                Message: "删除失败！",
                Icon: "k-ext-error"
            });
        }


    </script>
</body>
</html>
<script>
    var intervals = this;
    //上传处理
    function startProgress(key) {
        intervals["jd"+key] = setInterval("refreshUploadStatus('"+key+"')",500);
        return true;
    }

    //刷新上传状态
    var refreshUploadStatus = function (key) {
        var url = path+"/cms/video/getProgressByKey.json";
        var p_data = {
            key:key
        }
         xjj.cms.ajax(url, p_data, true, function (data) {
           if(null != data.resultData && "" != data.resultData && undefined != data.resultData)
               var item = data.resultData;
               if(null != item){
                   var progressPercent = item.conv_percent || "";
                   $("#progressBar_"+key).show();
                   $("#progressBarText_"+key).html(" " + progressPercent);
                   $("#totalProgressBarBoxContent_"+key).width(progressPercent);
                   if("100%" == progressPercent){
                       clearInterval(intervals["jd"+key]);
                       jQuery("#box_text_"+key).hide();
                       jQuery("#theMeter_"+key).html("已完成");
                       var r_url = path+ "/cms/video/removeProgressByKey.json";
                       //移除进度条池
                       xjj.cms.ajax(r_url, p_data, true, function (data) {

                       })

                   }
               }

         },null)

    }


</script>