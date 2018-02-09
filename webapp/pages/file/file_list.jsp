<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>栏目管理</title>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>
    <script src="${ctx}/comm/js/core.js"></script>
    <script type="text/javascript">
     var path = "<%=path%>";
    	//树对象
   	 var itemTree = null;
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
   	 var parentMenuId = "0";
   	 //跟节点
   	 var rootId = "0";
   	 //下拉栏目树
   	 var selectMenuTree = null;
     </script>
	<script src="${ctx}/pages/file/js/file.js"></script>
</head>
<body scroll="no">
    <!-- 界面 -->
     <div id="MainLayout" style="height: 598px;">
               <div id="grid"></div>
       </div>
</body>
 <!-- 脚本 -->
    <script type="text/javascript">
    var contentHeight = $(self.parent.frames["mainContentIframe"].document).height()-5;
	 $("#MainLayout").height(contentHeight);
	 $("#grid").height(contentHeight);
	 $(document).ready(function () {
		 xjj.cms.file.init_listPage();
  	 });
	 </script>
</html>