<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String basePath = ip+path+"/";
	String voteId = request.getParameter("voteId");
	request.setAttribute("voteId", voteId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>投票</title>
<link href="<%=basePath %>assets/cms/vote/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>assets/_base/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/cms/core.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/cms/cms.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/cms/vote/js/vote.js"></script>
<script type="text/javascript">
var path = "<%=basePath%>";
var voteId = "<%=voteId%>"
$(document).ready(function() {
	new xjj.cms.vote.list({
		urlPre:path,
		renderId:"itemList_div", //需要渲染的id
		voteId :voteId,
		vote4El : '<div class="gao_tpnr">'+
    			  '<div class="gao_tpnrtop">'+
			      '($_num) $_itemTitle'+
			      '</div>'+
			      '<div class="gao_tpcon">'+
			      '$_detailTitle'+
			      '</div>'+
			      '</div>',
		themeTitle:{
			renderId:"themeTitle"
		}
	})
 });
</script>
</head>
<body>
<div class="gao_box">
	<div class="gao_txbg gao_biaot" id="themeTitle">
  </div>
  <div class="gao_tplist" id="itemList_div">
  </div>
	<div class="" align="center">
		<img src="images/btn_tj.jpg" width="90" height="30" onclick="xjj.cms.vote.doVote()" style="cursor: pointer; margin-bottom: 20px;" title="提交" />
		&nbsp;&nbsp;&nbsp;
		<img src="images/btn_qx.jpg" width="90" height="30" onclick="javascript:window.close();" style="cursor: pointer; margin-bottom: 20px;" title="取消" />
	</div>
</div>
</body>
</html>
