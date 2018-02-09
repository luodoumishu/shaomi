<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
	String articleId = request.getParameter("articleId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <%@ include file="/comm/kendo_css.jsp"%> 
    <%@ include file="/comm/kendo_js.jsp"%> 
    <link href="${ctx}/pages/article/css/style_comment.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/pages/article/css/pinglun.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/pages/article/js/scrollpagination.js"></script>
    <script src="${ctx}/pages/article/js/article.js"></script>
 </head>
<body>
<div>
	<div class="xjj_lisboxd">
				<div class="xjj_neirbox">
					<div>
						<div id="article_img" style="width: 21px; float: left;text-align: center;vertical-align: middle;line-height: 30px;height: 30px; " >
						<img src="images/zk.jpg" style="text-align: center;vertical-align: middle;line-height: 30px" height="21px" onclick="changeImg()"></div>
						<div style="margin-left: 25px;"><h1 id="article_title"></h1></div>
					</div>
					
					<div class="xjj_nerzz">
						<span>文章来源：</span><span id="wzly"></span><span>发布时间：</span><span id="fbsj"></span>
					</div>
					<div class="xjj_nener" id="article_neirong" style="display: none;"></div>
				</div>
	</div>
</div>
<div class="log_edit" id="whole">
   	 <table class="table01Div" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    <td class="nrtable">
      <table width="100%" class="tablerz" border="0" cellspacing="0" cellpadding="0" id="logcontent">

      </table>
      </td>
  </tr>
</table> 
<table class="fy" width="100%" border="0" cellspacing="0" cellpadding="0" id="fy" >
  
</table>
</div> 
<script type="text/javascript">
	var articleId = '<%=articleId%>'; 
	$(function(){
	/* 	$('#whole').scrollPagination({
			'heightOffset':100,
			'contentPage': 'chat/Chatroom/chatroomList.json?cpage=',
			'contentData': {}, // you can pass the children().size() to know where is the pagination
			'scrollTarget':$('#whole'), // who gonna scroll? in this example, the full window
			// how many pixels before reaching end of the page would loading start? positives numbers only please
			'beforeLoad': function(){ // before load, some function, maybe display a preloader div
				$('#loading').fadeIn();
				$('#content').stopScrollPagination();
			},
			'afterLoad': function(elementsLoaded){ // after loading, some function to animate results and hide a preloader div
				 $('#loading').fadeOut();
				 $('#content').reset();
			}
		}); */
		$('#whole').scrollPagination({
    		"contentPage": path + "/pages/article/test.jsp?articleId="+articleId,
    		"scrollTarget": $("#whole1"), 
    		"heightOffset": 10,
    		"afterLoad": function(elementsLoaded){ // after loading, some function to animate results and hide a preloader div
    			mouseoverEvent();
   			}
    	});
	});
	 $(document).ready(function () {
	        //初始化表单
	    /*     InitForm();
	        queryObj.ifGd = 0;
	        //kendoui校验器初始化 */	
	    showArticle("article_title","wzly","fbsj","article_neirong","${ctx}/cms/article/getArticleById.json?articleId="+articleId);    
	    showWorkLog("logcontent","fy","${ctx}/cms/article/comment/list4articleId.json","?articleId="+articleId);
 });    
    //初始化表单
    function InitForm() {
    	
    }
    function changeImg(){
    	var html = "<img src='images/sq.jpg' style='text-align: center;vertical-align: middle;line-height: 30px' height='21px' onclick='changeHidden()'>";
    	$("#article_img").html(html);
    	$("#article_neirong").fadeOut();
    }
    function changeHidden(){
    	var html = "<img src='images/zk.jpg' style='text-align: center;vertical-align: middle;line-height: 30px' height='21px' onclick='changeImg()'>";
    	$("#article_img").html(html);
    	$("#article_neirong").fadeIn();
    }
</script>
</body>
</html>