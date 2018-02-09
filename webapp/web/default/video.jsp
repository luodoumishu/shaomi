<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@ include file="/comm/js/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="keywords" content="${PRE_TITLE} -海南临高党建" />
<meta name="description" content="${PRE_TITLE} - 海南临高党建" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script type="text/javascript" src="${ctx}/assets/ace/js/jquery-1.10.2.min.js"></script>
<!-- <script type="text/javascript" src="${ctx}/assets/js/utils.js"></script> -->
<script type="text/javascript" src="flvplayer/swfobject.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<title>海南临高党建</title>
</head>

<body>
<div class="lg_box">
    <!-- 头部 Start -->
	<c:import url="top.jsp"></c:import>
	<!-- 头部 End  -->
    <!--主体-->
    <div class="list_main">
    	<!--内容-->
   	 	<video:video titleLength="36">
        <div class="nei_box">
        	<div class="nei_top">
		    	<h1 id="_title">$_video_video_title</h1>
		        <p>
					<span>发布人：$_video_author</span>
           			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布时间：$_video_time</span>
           			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;播放次数：$_video_count</span>
		        </p>
		    </div>
            <div class="nei_con">
          	<div id="player" style="text-align:center"></div>
				<script type="text/javascript">
					var s1 = new SWFObject("flvplayer/flvplayer.swf","single","600","480","7","#336699");
					s1.addParam("allowfullscreen","true");
					s1.addParam("menu","true");
					s1.addParam("wmode","transparent");
					s1.addVariable("file","$_video_path");
					s1.addVariable("image","$_img_path");
					s1.addVariable("width","600");
					s1.addVariable("height","480");
					s1.write("player");
				</script>
          	</div>
        </div>
        </video:video>
         <div style="text-align: center;"><input type="button" onclick="closeW();" value="关闭本页" style="cursor: pointer;"></div><br/>
        <!--内容 end-->
    </div>
    <!--主体 end-->
    <!--底部-->
	<c:import url="end.jsp"></c:import>
	<!--底部 end-->
   <script type="text/javascript">
    function closeW(){
    	window.opener = null;
		window.open("","_self") ;
		window.close(); 
    }
  </script>
</div>
</body>
</html>
