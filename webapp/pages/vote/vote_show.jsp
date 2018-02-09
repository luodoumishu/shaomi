<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
String basePath = ip+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<link href="${ctx}/assets/_base/css/attachment.css" rel="stylesheet"></link>
<link href="${ctx}/pages/vote/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/comm/kendo_js.jsp"%>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.upload.ext.js"></script>
<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
<script src="${ctx}/assets/_base/js/jquery.form.js"></script>
<script src="${ctx}/comm/js/core.js"></script>
<script type="text/javascript">
    	var path = "<%=path%>";
    	var ip  = "<%=ip%>";
</script>

</head>
<body>
	<table border="0" cellspacing="0" cellpadding="3" width="99%" >
    <tr width="100%">
	 	<td>
			<select name="themeSelect" id="themeSelect" style="width: 300px;"><option value="0">  </option>
			<input type="button" name="button1" value="查询" class="Button" onclick="selectVoteTheme()">
		</td>
		
	</tr>
</table>
<table border="0" cellspacing="0" cellpadding="3" width="99%" height="20">
    <tr width="100%">
 		<td width="90%" >
			<!-- message -->

<div class="message" id="message">

</div>

<!-- message -->
		</td>
	</tr>
</table>
</body>
<script type="text/javascript" src="${ctx}/pages/vote/js/vote_show.js"></script>
<script type="text/javascript">
    //下拉数据源
    $(document).ready(function(){
    	xjj.cms.vote.init_editPage();
    });
</script>

</html>