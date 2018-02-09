<%@page import="com.xjj.cms.base.VO.CmsCC"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int level = CmsCC.MENU_FJSQ_ORG_SHOWLEVEL;
%>
<!DOCTYPE html>
<html>
<head>
    <title>栏目分级授权</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/assets/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
	<%@ include file="/comm/kendo_css.jsp"%>
    <%@ include file="/comm/kendo_js.jsp"%>
    <script type="text/javascript">
   		var path = "<%=path%>";
   		var basePath ="<%=basePath%>";
   		var orgId = "0";
   		var level = "<%=level%>";
	</script>
    <script src="<%=basePath%>/comm/js/core.js"></script>
	<script type="text/javascript" src="<%=basePath %>/assets/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath %>/assets/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/pages/menu/js/menu_fjsq.js"></script>
	
</head>
<body scroll="no">
 <!-- 界面 -->
    <div id="MainLayout" style="height:598px;">
    	<div id="LeftLayout">
            <div class="k-toolbar" style="background-color: #F5F5F5;height: 3em">
                <div class="xjj-tree-toolbar" >
                    <!-- <div class="xjj-group">
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
                    </div> -->
                </div>
            </div>
            <div id="orgTree" class="ztree"></div>
        </div>
           <div id="Right">
           			<div style="background-color: #F5F5F5;height: 3em;line-height: 3em;vertical-align: middle; ">
	           			<a id="ftsq_bt" class="k-button" style="margin-left: 1.2em;"><span > 授 权  </span></a>
                	</div>
               <div id="menuTree" class="ztree"></div>
           </div>
       </div>
    <!-- 脚本 -->
    <script type="text/javascript">
    	var contentHeight = $(self.parent.frames["mainContentIframe"].document).height()-5;
		$("#MainLayout").height(contentHeight);
	 	$("#grid").height(contentHeight);
          //树对象
          var itemTree = null;
		  //数据源对象
		  var dataSource = null;
		  //列表对象,用于对表格数据的操作
		  var grid = null;
		  //查询条件对象
		  var queryObj = null;
		  //数据对象，用于新增和修改
	 	  var Item = null;
		  //下拉数据源
		  var selectSource = null;
		  //初始值
		  var parentMenuId = "0";
    	  var menuId = "0";
        $(document).ready(function () {
        	xjj.cms.menuFJSQ.initPage();
        });
    </script>
</body>
</html>