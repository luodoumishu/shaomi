<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String zt = request.getParameter("zt");
%>
<!DOCTYPE html>
<html>
<head>
    <title>栏目管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>
    <script src="${ctx}/comm/js/core.js"></script>
    <script src="${ctx}/assets/artdialog/jquery.artDialog.js?skin=blue"></script>
	<script src="${ctx}/assets/artdialog/plugins/iframeTools.js"></script>
    <script type="text/javascript">
     var path = "<%=path%>";
    	//树对象
   	 var menuTree = null;
   	 // 数据源对象
   	 var dataSource = null;
   	 // 列表对象,用于对表格数据的操作
   	 var grid = null;
   	 // 查询条件对象
   	 var queryObj = null;
   	 // 数据对象，用于新增和修改
   	 var item = null;
   	 // 编辑的类型,默认为新增
   	 var editType = "add";
   	 // 下拉数据源
   	 var selectSource = null;
   	 // 栏目名称
   	 var menuName = null;
   	 //父栏目id初始值
   	 var menuId = "0";
   	 //跟节点
   	 var rootId = "0";
   	 //下拉栏目树
   	 var selectMenuTree = null;
   	 //历史状态
   	 var zt = "<%=zt%>";
   	 //当前单位
   	 var orgId = "${sessionScope.orgId}";
   	 //当前单位名称
   	 var orgName = "${sessionScope.orgName}";
     </script>
	<script src="${ctx}/pages/article/js/article.js"></script>
</head>
<body>
    <!-- 界面 -->
    <div id="MainLayout" style="height: 598px;">
    	<div id="LeftLayout">
            <div class="k-toolbar">
                <div class="xjj-tree-toolbar" >
                    <div class="xjj-group">
                        <a id="TreeRefresh" class="k-button">
                            <span class="k-icon k-i-refresh"></span>刷新
                        </a>
                    </div>
                    <div class="xjj-l-bar-separator" id="TreeRefreshSeparator"></div>
                    <div class="xjj-group">
                        <a id="TreeExpand" class="k-button ">
                            <span class="k-icon k-i-expand"></span>展开
                        </a>
                    </div>
                    <div class="xjj-l-bar-separator"></div>
                    <div class="xjj-group">
                        <a id="TreeCollapse" class="k-button">
                            <span class="k-icon k-i-collapse"></span>收起
                        </a>
                    </div>
                </div>
            </div>
            <div id="itemTree"></div>
        </div>
           <div id="Right">
           		<div id="" style="background-color: #F3F3F4;border: #dbdbde solid 1px;">
            <table >
	              <tr>
	                   <td>
	                       	 文章标题：<input id="articleName" name="articleName" style="width: 200px" class="k-textbox" /> &nbsp;&nbsp; &nbsp;&nbsp;
	                   </td>
	                   <td>
	                        &nbsp;<input id="qsearch" type="button" class="k-button" value=" 查 询 " />
	               		 &nbsp;<input id="clear" type="button" class="k-button" value=" 重 置 " />
	                   </td>
	               </tr>
	           </table> 
          	</div>
               <div id="grid"></div>
           </div>
       </div>
</body>
 <!-- 脚本 -->
    <script type="text/javascript">
    var contentHeight = $(self.parent.frames["mainContentIframe"].document).height()-5;
	 $("#MainLayout").height(contentHeight);
	 $("#grid").height(contentHeight);
	 $("#itemTree").height(contentHeight-60);
	 $(document).ready(function () {
		 xjj.cms.article.history_listPage();
  	 });
	 //快速查询
	 $("#qsearch").click(qSearch);

	 //快速查询
	 function qSearch() {
	 	var dataSource = grid.dataSource;
     	dataSource.page(1);
	 }
	 $("#clear").click(function(){
	    	$("#articleName").val("");
	    	var dataSource = grid.dataSource;
		    dataSource.page(1);
	    });
	 
	 </script>
</html>