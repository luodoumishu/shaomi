<%@page import="com.xjj.jdk17.utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@ include file="/comm/js/taglib.jsp" %>
<%@ taglib prefix="xjj" uri="/WEB-INF/tld/xjj_portal.tld"%>
<%
	String path = request.getContextPath();
	String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String basePath = ip+path+"/";
	String searchValue = request.getParameter("searchValue");
	
	String orgCode = request.getParameter("orgCode");
	String orgName = request.getParameter("orgName");
	if(StringUtil.isEmpty(orgCode)){
		orgCode = "lgx";
	}
	if(StringUtil.isEmpty(orgName)){
		orgName = "海南临高";
	}
	request.setAttribute("orgCode", orgCode);
	request.setAttribute("orgName", orgName);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=orgName %>党建网</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/_base/js/jquery-1.8.3.js"></script>
</head>
<script type="text/javascript">
	var path = "<%=path%>";
	var orgCode = "<%=orgCode%>";
	var resultData = null;
	var pageSize = 15;
$(document).ready(function() {
	$("#shouye").removeClass("hove"); 
	$("#menuName").html("查询结果");
	$("#searchValue").val("<%=searchValue%>");
	var Ptr= $("#tab").find("li");
	var li_class_tab =function() {
		  for (var i=1;i<Ptr.length+1;i++) {
		  	Ptr[i-1].className = (i%2>0)?"t1":"t2";
		  }
	};
	li_class_tab();
	for(var i=0;i<Ptr.length;i++) {
		  Ptr[i].onmouseover=function(){
		  this.tmpClass=this.className;
		  this.className = "t3";   
		  };
		  Ptr[i].onmouseout=function(){
		  this.className=this.tmpClass;
		  };
	}
	initArticle();
});

function initArticle(){
	var url = path + "/cms/article/findArticleBySearch.json";
	var searchValue = '<%=searchValue%>';
	$.ajax({
		type: "POST",
        url:  url,
        data: {"searchValue":searchValue,"orgCode":orgCode},
        dataType: "json",
        success: function(result){
        	if(result != null && result.resultData != null){
        		if(result.resultCode == 0){
        			resultData = result.resultData;
                	var total = resultData.length;
                	if(total > 0){
                		var end = (pageSize*1);
                    	if(end > total){
                    		end = total;
                    	}
                		initUL(0,end,total,pageSize);
                	}
                	var totalPage = Math.ceil(total/pageSize);
                	$("#totalSize").html(total);
                	$("#totalPage").html(totalPage);
        		}else{
        			alert(result.result.Msg);
        		}
        	}
        },
        error:function(result){
        	alert("查询出错");
        }
	});
}

function goIndex(){
	var total = resultData.length;
	var totalPage = Math.ceil(total/pageSize);
	$("#currenPage").html(1);
	$("#totalSize").html(total);
	$("#totalPage").html(totalPage);
	var end = (pageSize*1);
	if(end > total){
		end = total;
	}
	initUL(0,end,total,pageSize);
}
function goUp(){
	var total = resultData.length;
	var totalPage = Math.ceil(total/pageSize);
	var currenPage = $("#currenPage").html();
	if(currenPage != "1"){
		$("#currenPage").html(currenPage-1);
		$("#totalSize").html(total);
    	$("#totalPage").html(totalPage);
		initUL(((currenPage -2)*pageSize),((currenPage -1)*pageSize),total,pageSize);		
	}
}
function goDown(){
	var total = resultData.length;
	var totalPage = Math.ceil(total/pageSize);
	var currenPage = $("#currenPage").html();
	if(currenPage != totalPage){
		$("#currenPage").html(currenPage - (-1));
		$("#totalSize").html(total);
    	$("#totalPage").html(totalPage);
    	var end = ((currenPage - (-1))*pageSize);
    	if(end > total){
    		end = total;
    	}
		initUL(((currenPage)*pageSize),end,total,pageSize);		
	}
}
function goEnd(){
	var total = resultData.length;
	var totalPage = Math.ceil(total/pageSize);
	$("#currenPage").html(totalPage);
	$("#totalSize").html(total);
	$("#totalPage").html(totalPage);
	initUL((totalPage -1)*pageSize,total,total,pageSize);
}
function initUL(start,end,total,pageSize){
	var html = "";
	for(var i = start;i<end;i++){
		var aticle = resultData[i];
		var time = aticle.createTime;
		time = time.substring(0,10);
		var title = aticle.title;
		var name = title;
		if(title.length > 46){
			name = title.substring(0,45)+"...";
		}
		var url = "article.jsp?articleId="+aticle.id;
		html += "<li><span>"+time+"</span></span><a href='"+url+"' title='"+title+"' target='_blank'>"+name+"</a></li>";
	}
	$("#tab").html(html);
}

</script>
<body>
<div class="lg_box">
    <!--导航-->
    <!-- 头部 Start -->
	<c:import url="top.jsp"></c:import>
	<!-- 头部 End  -->
    <!--导航 end-->
    <!--主体-->
    <div class="list_main">
    	<div class="list_line"></div>
    	<!--第一屏-->
        <div class="lg_one">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="lis_left">
                    <div class="lis_lftop">常用栏目</div>
	                <div class="lis_lfnav">
	                	<ul>
		                    <c:import url="menu_left_daohang.jsp"></c:import>
	                	</ul>
	                </div>
                </td>
                <td class="lis_right">
                    <div class="lis_rgtop">
                   		<p id="menuName"></p>
                    </div>
                    <div class="lis_leb">
                    	<ul id="tab">
	                    </ul>
						<div class="lis_page">
							第<span id="currenPage">1</span>页/共<span id="totalPage">1</span>页（共<span id="totalSize">0</span>条记录）  <a href="javascript:goIndex();">首页</a>  <a href="javascript:goUp();">上一页</a> <a href="javascript:goDown();">下一页</a> <a href="javascript:goEnd();">尾页</a>
						</div>
					</div>
                </td>
              </tr>
            </table>
        </div>
    </div>
    <!--主体 end-->
    <div style="clear:both;"></div>
    <!--底部-->
	<c:import url="end.jsp"></c:import>
    <!--底部 end-->
</div>
</body>
</html>
