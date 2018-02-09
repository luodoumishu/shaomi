<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>ECharts</title>
<!-- ECharts单文件引入 -->
<script src="${ctx}/assets/echarts-2.2.7/build/dist/echarts.js"></script>
<script type="text/javascript" src="js/count.js"></script>
<%@ include file="/comm/kendo_css.jsp"%>
<%@ include file="/comm/kendo_js.jsp"%>
<script type="text/javascript">
		var path = "<%=basePath%>";
		var _height = $(window).height()*0.98;
		var parentId = "0";
		
		 //快速查询
		 function qSearch() {
		 	EchartsData(parentId,$("#startDate").val(),$("#endDate").val());
		 }
		
		$(document).ready(function () {
			$("#MainLayout").css("height",_height);
			$("#MainLayout").kendoSplitter({
				panes : [ {
					collapsible : true,
					size : '20%'
				}, {
					collapsible : true,
					size : "80%"
				} ]
			});
			
		 	//部门树初始化
			itemTree = $("#itemTree").kendoTreeView({
                dataTextField: "name",
                select: function(e) {
                	parentId = this.dataItem(e.node).id;
					EchartsData(parentId,$("#startDate").val(),$("#endDate").val());
                  }
            }).data("kendoTreeView");
			LoadItemTree();
			
			 //刷新按钮
			$("#TreeRefresh").click(function () {
			    LoadItemTree();
			});

			//展开按钮
			$("#TreeExpand").click(function () {
			    ExpandItemTree();
			});

			//收缩按钮
			$("#TreeCollapse").click(function () {
			    CollapseItemTree();
			});
			function startChange() {
                var startDate = start.value(),
                endDate = end.value();

                if (startDate) {
                    startDate = new Date(startDate);
                    startDate.setDate(startDate.getDate());
                    end.min(startDate);
                } else if (endDate) {
                    start.max(new Date(endDate));
                } else {
                    endDate = new Date();
                    start.max(endDate);
                    end.min(endDate);
                }
            }

            function endChange() {
                var endDate = end.value(),
                startDate = start.value();
                if (endDate) {
                    endDate = new Date(endDate);
                    endDate.setDate(endDate.getDate());
                    start.max(endDate);
                } else if (startDate) {
                    end.min(new Date(startDate));
                } else {
                    endDate = new Date();
                    start.max(endDate);
                    end.min(endDate);
                }
            }
            
            $("#startDate").val(getCurrentMonthFirst());
            $("#endDate").val(getCurrentMonthLast());
            
            
            var start = $("#startDate").kendoDatePicker({
                change: startChange,
            	format: "yyyy-MM-dd"
            }).data("kendoDatePicker");

            var end = $("#endDate").kendoDatePicker({
                change: endChange,
                format: "yyyy-MM-dd"
            }).data("kendoDatePicker");

            start.max(end.value());
            end.min(start.value());
            $("#startDate").attr("readOnly",true);
            $("#endDate").attr("readOnly",true);
        	//Echarts初始化
            EchartsData(parentId,$("#startDate").val(),$("#endDate").val());
         	
        	//快速查询
   		 	$("#qsearch").click(qSearch);
          
	  	 });
		
    </script>
</head>
<body>
	<div id="MainLayout">
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
            	<table>
	              <tr>
	                   <td>
	                       	开始时间：<input id="startDate" style="width: 250px;">&nbsp;&nbsp; &nbsp;&nbsp;
	                       	结束时间：<input id="endDate" style="width: 250px;">&nbsp;&nbsp; &nbsp;&nbsp;
	                   </td>
	                   <td>
	                        &nbsp;<input id="qsearch" type="button" class="k-button" value=" 查 询 " />
	                   </td>
	               </tr>
	           </table> 
          	</div>	
			<div id="barChart" style="height:400px;"></div>
			<hr />
			<div id="lineChart" style="height:400px;"></div>
			<hr />
			<div id="pieChart" style="height:400px;"></div>
           </div>
       </div>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
</body>
</html>