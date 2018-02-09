<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>投票项明细管理</title>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>
    <script src="${ctx}/comm/js/core.js"></script>
    <script type="text/javascript">
     var path = "<%=path%>";
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
     </script>
	<script src="${ctx}/pages/vote/js/vote_detail.js"></script>
</head>
<body style="overflow: hidden;">
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
		 xjj.cms.vote.init_listPage();
  	 });
	 </script>
</html>