<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@page import="com.xjj.framework.core.web.filter.WebContext"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>内容管理系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/comm/ace_css.jsp"%>
<%@ include file="/comm/ace_js.jsp"%>
<%@ include file="/comm/kendo_css.jsp"%>
<%@ include file="/comm/kendo_js.jsp"%>

<!-- <script src="ie8_js/animationForIE8.js"></script> -->

<!-- basic styles -->
<%-- <link href="${ctx}/assets/ace/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${ctx}/assets/ace/assets/css/bootstrap-responsive.min.css" rel="stylesheet" />

    <link rel="stylesheet" href="${ctx}/assets/ace/assets/font/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" href="${ctx}/assets/ace/assets/font/font-XJJ/style.css" >

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx}/assets/ace/assets/css/jquery-ui-1.10.3.custom.min.css" />
    <link rel="stylesheet" href="${ctx}/assets/ace/assets/css/jquery.gritter.css" />

    <!-- ace styles -->
    <link rel="stylesheet" href="${ctx}/assets/ace/assets/css/ace-xjj.css" />
    <link rel="stylesheet" href="${ctx}/assets/ace/assets/css/ace-responsive.min.css" /> --%>

<style>
/*#layout-left, #layout-right,#loading-frame,#loading-wrap {position: absolute;}*/
/*#layout-left{left: 0;width: 190px;z-index: 9;background-color: #ECF7FE;}*/
/*#layout-left a {font-size: 12px; font-family: "??????"}*/
/*#layout-right, #loading-frame {left: 197px;right: 0;z-index: 10; background: #FFF}*/
/*#loading-frame {background: #000; z-index: 11;}*/
#loading-wrap {
	width: 300px;
	left: 50%;
	top: 40%;
	margin-left: -150px;
}

#loading-frame {
	position: absolute;
	background: #000;
	z-index: 11;
}

#iframe-div {
	z-index: 11;
	background: #fff;
}

.dropdown-menu li a:hover,.dropdown-menu li a:focus,.dropdown-menu li a:active,.dropdown-menu li.active a,.dropdown-menu li.active a:hover,.dropdown-menu .dropdown-submenu:hover>a,.nav-tabs .dropdown-menu li>a:focus
	{
	background: #E9E9E9;
	color: #000333;
}

.shakeAnimation {
	position: relative
}
</style>
</head>

<body>
    
	<div id="c_headerBar" onclick="hideTransparentDiv();"
		style="z-index: 9999999;top:0px;right:0px;left:0px;background: none repeat scroll 0 0 #1D1D1D;padding: 30px;position: absolute;display:none;">
	</div>
	<div id="transparentDiv" onclick="hideTransparentDiv();"
		style="z-index: 9999998;top:0px;right:0px;left:0px;position: absolute;display:none;width: 100%;height: 100%;">

	</div>
	<div class="navbar navbar-inverse" id="main-head">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a href="javascript:void(0);" class="brand"
					style="padding: 10px 5px 0px 20px;"> <small> <i
						class="icon-leaf"></i> <span id="appName">Ace Admin</span> </small> </a>
				<!-- /.brand -->
				<span style="margin-top: 20px;color: white;background-color: #62a8d1 !important;" class='badge'>
					
					<a href="javascript:void(0);" onclick="showTransparentDiv();" style="color: #eee;">切换系统<i class="icon-caret-down"></i></a>
					
					<!-- <button class="btn btn-info dropdown-toggle"
						style="margin-left: 5px;padding: 0px 5px 5px 5px;border-top-width: 0px;border-bottom-width: 0px;line-height: 20px;top: 7px;border-left-width: 0px;border-right-width: 0px;"
						>
						<span class="caret"></span>
					</button>  -->
				</span>
				<ul class="nav ace-nav pull-right">
					<li class="light-blue" style="margin-top: 0px;line-height: 45px">
						<a href="javascript:void(0);" class="dropdown-toggle"> 
							<i class="icon-user"></i>
							<span class="user-info" onclick="openuser()"> 
								<small>您好,<%=WebContext.getInstance().getHandle().getUserName()%></small>
							</span> 
						</a>
					</li>
					
					
					<!-- 系统提醒 begin-->
					<li class="purple" style="width: 56px;text-align:center;display:none;"  id="purpleBox" onMouseover="openBox(this)" onMouseout="closeBox($(this))"><a data-toggle="dropdown"
						class="dropdown-toggle" href="javascript:void(0);"> <div
							class="icon-bell-alt"
							id="notificationBell"></div> <span
							class="badge badge-important notificationNum"></span> </a>

						<ul id="notificationBody" onMouseout="closeBoxDelay($('#purpleBox'))"
							class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-closer" style="width:350px">
						</ul>
					</li>
					<!-- 系统提醒 end-->

					<!-- 系统消息 begin-->
					<li class="green" id="greenBox" style="width: 56px;text-align: center;display:none;" onMouseover="openBox(this)" onMouseout="closeBox($(this))"><a data-toggle="dropdown"
						class="dropdown-toggle"> <i id="envelope"
							class="icon-envelope"></i> <span
							class="badge badge-success unreadMsgNum"></span> </a>

						<ul id="unreadMsgBody" onMouseout="closeBoxDelay($('#greenBox'))"
							class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer" style="width:350px">
						</ul></li>
					<!-- 系统消息 end-->
					
					
					<li class="light-blue" style="margin-top: 0px;line-height: 45px;width: 56px;text-align: center;">
						<a href="javascript:void(0);" onclick="logout();"> 
							<i class="icon-off"></i>
						</a>
					</li>

					
				</ul>
				<!-- /.ace-nav -->
			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /.navbar-inner -->
		<!-- <div class="navbar-line" ></div> -->
	</div>
	<!--/.navbar #main-head-->

	<div class="container-fluid" id="main-container">
		<a href="javascript:void(0);" id="menu-toggler"><span></span> </a>
		<!-- menu toggler -->
		<div id="sidebar" style="left: 0px;">
			<div id="sidebar-shortcuts">
				<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
					<button class="btn btn-small btn-success" onclick="javascript:window.history.go(-1);">
						<i class="icon-arrow-left"></i>
					</button>

					<button class="btn btn-small btn-info" onclick="javascript:window.history.go(1);">
						<i class="icon-arrow-right"></i>
					</button>

					<button class="btn btn-small btn-warning" onclick="iframeReload();">
						<i class="icon-refresh"></i>
					</button>

					<button class="btn btn-small btn-danger" onclick="iframeIndex();">
						<i class="icon-home"></i>
					</button>
				</div>
				<div id="sidebar-shortcuts-mini">
					<span class="btn btn-success"></span> <span class="btn btn-info"></span>
					<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
				</div>
				<div class="nav-search" id="nav-search" style="display: none;">
					<span class="input-icon" style="margin-top: 5px;"> <input
						type="text" placeholder="Search ..." style="width: 100px;"
						class="input-small nav-search-input" id="nav-search-input"
						autocomplete="off" /> <i class="icon-search nav-search-icon"
						style="margin-top: 1px;"></i> </span>
					<button class="btn btn-danger btn-mini"
						style="margin-bottom: 10px;" onclick="hideSearch();">
						<i class="icon-reply"></i>
					</button>
				</div>
				<!-- #nav-search -->

			</div>
			<!--#sidebar-shortcuts -->

			<ul class="nav nav-list" id="sidebar-menu"></ul>
			<!--/.nav-list,#sidebar-menu-->
			<div id="sidebar-collapse">
				<i class="icon-double-angle-left"></i>
			</div>


		</div>
		<!--/#sidebar-->


		<div id="main-content" class="clearfix">

			<div id="breadcrumbs">
				<ul class="breadcrumb" style="width: 90%">
					<li><i class="icon-home"></i> Home <span class="divider"><i
							class="icon-angle-right"></i> </span> <b class="blue"></b></li>
				</ul>
				<!--.breadcrumb-->
			</div>
			<!--#breadcrumbs-->
			<div id="iframe-div" class="row-fluid">
				<iframe id="main-content-iframe" name="mainContentIframe"
					frameborder="0" src="about:blank"></iframe>
			</div>
		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	<!-- basic scripts -->
	<!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>-->
	<!--<script type="text/javascript">-->
	<!--window.jQuery || document.write("<script src='assets/js/jquery-1.9.1.min.js'>\x3C/script>");-->
	<!--</script>-->
	<%-- <script src='${ctx}/assets/ace/assets/js/jquery-1.9.1.min.js'></script>
<script src="${ctx}/assets/ace/assets/js/bootstrap.min.js"></script>

<!-- page specific plugin scripts -->

<script type="text/javascript" src="${ctx}/assets/ace/assets/js/jquery-ui-1.10.3.custom.min.js"></script>

<script type="text/javascript" src="${ctx}/assets/ace/assets/js/jquery.ui.touch-punch.min.js"></script>

<script type="text/javascript" src="${ctx}/assets/ace/assets/js/jquery.gritter.min.js"></script>

<!-- ace scripts -->
<script src="${ctx}/assets/ace/assets/js/ace-elements.min.js"></script>
<script src="${ctx}/assets/ace/assets/js/xjj-frame.js"></script>
<script src="${ctx}/assets/ace/assets/js/json2.js"></script> --%>
	<script src="${ctx}/assets/ace/js-ext/xjj-frame.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
    if('${isEditPass}' == 0){
        openwindow("${ctx}/pages/roleUserPer/index.jsp?type=1",$(window).width()*0.6,$(window).height()*0.35,'xgmm','div','xgma');
    }
	var ctx = "<%=request.getContextPath()%>";
	var unreadMsg = null;
    var unreadMsgNum = null;
    var notificationNum=null;    
    var notifications =null;
    var userAccount = null;
    var userId = null;
    var envelopeElement = null;
    var notificationBellElement =null;
    var currentMsgObj =null;
    var getMsgOrNociceFailTag = false;
    var popup_timeout = null;
    var boole = false;
    var userId = '<%=WebContext.getInstance().getHandle().getUserId()%>';
    function openProcess(url){       
        openwindow(url,$(window).width()*0.5,$(window).height()*0.9,"process","div","process"); 
    }    
    
    function openExplain(url){       
        openwindow(url,$(window).width()*0.9,$(window).height()*0.8,"explain","div","explain");
    }  
    
    function openItemWindow(url)
    {
       // alert(url);
       window.open(url, "_blank") ;
        //openwindow(url,$(window).width()*0.8,$(window).height()*0.9,"item","div","item"); 
    }
    
    function closeItemWindow(msg)
    {
        //window.closewindow('div','item');
        mainContentIframe.window.location.reload();
    }
    
    function showAllNotification()
    {
        
    }
    
    function openBox(boxObj)
    {
            $(boxObj).addClass('open');
    }
    
    function closeBoxDelay(boxObj)
    {
            setTimeout(closeBox(boxObj),100);
    }
    
    function closeBox(boxObj)
    {
        boxObj.removeClass('open');
    }
    
	function checkMsgDetail(msgId)
	{
	       var msgObj =  null;
	      AjaxFlash("${ctx}/msgmanage/getMsgById.json?msgId="+msgId, "", false, success=function(result){
	           msgObj = result.resultData;
	          currentMsgObj = msgObj;
	       }, fail=function(result){
	          alert("查询单条消息失败,失败代码:"+JSON.stringify(result.status)); 
	       });
	   
	      var showTypePrefix = msgObj.showType;
	      var msgDetails = msgObj.details;
	    
	      var url1= msgObj.url1;
	      var url2 =msgObj.url2;
	     
	      if(showTypePrefix=="URL_WIN")
	      {
	        
	          openItemWindow(url1);
	          markReadedAndPushOld(msgId);
	      }
	      else if(showTypePrefix=="MSG_DIV")
	      {
	          showMsgByDiv(msgId);
	      }
	}
	
	function showMsgByDiv(msgId)
    {
        openwindow(ctx+"/pages/msgCenter/msgManage/checkmsg.jsp?msgId=" + msgId, 700, 400, "消息详情", "div",
                "消息详情");
    }
    
    function showAllMsgsPage()
    {
        $("#main-content-iframe").attr("src",ctx+"/pages/msgCenter/msgManage/messagelist.jsp");
    }
    
    function showSubscribePage()
    {
       // $("#main-content-iframe").attr("src",ctx+"/pages/msgCenter/msgManage/subscribe.jsp");
        window.open(ctx+"/pages/msgCenter/msgManage/subscribe.jsp", "_blank") ;
    }
    
    function queryUid()
    {
        userAccount = '<%=session.getAttribute("userAccount")%>';

            AjaxFlash("${ctx}/msgmanage/getuid.json?name=" + userAccount, "",
                    false, queryUidSuccess = function(result)
                    {
                        userId = result.resultData;
                        //alert("userId=" + userId);

                    }, queryUidError = function(result)
                    {
                        alert("查询用户ID失败");
                    });
        }

        function queryUnreadMsg()
        {
            AjaxFlash("${ctx}/msgmanage/getunreadmsg.json", "uid=" + userId,
                    false, queryMsgSuccess, queryMsgError);
        }

        function queryMsgSuccess(result)
        {
            unreadMsg = result;
            var newUnreadMsgNum = result.length;
            if (unreadMsgNum != null)
            {
                if (unreadMsgNum != newUnreadMsgNum)
                {
                    triggerEnvelopeAnimation();
                    unreadMsgNum = newUnreadMsgNum;
                }
            }
            else
            {
                unreadMsgNum = newUnreadMsgNum;
            }
            //unreadMsgNum
            var unreadMsgHtml = AjaxGetHtml("${ctx}/msgmanage/getUnreadMsgHtml.do?userId="
                    + userId);
            $("#unreadMsgBody").html(unreadMsgHtml);
            if(unreadMsgNum!=0)
                $(".unreadMsgNum").html(unreadMsgNum);

            //alert(unreadMsgHtml);

        }

        function queryNotification()
        {
            AjaxFlash("${ctx}/msgmanage/getNotifications.json", null, false,
                    queryNotificationOk, queryNotificationError);
        }

        function markReadedAndPushOld(msgId)
        {

            AjaxFlash("${ctx}/msgmanage/markMsgReaded.json?msgId=" + msgId, "",
                    false, markMsgSuccess = function(result)
                    {
                        queryUnreadMsg();
                        $("#greenBox").addClass('open');
                    }, markMsgError = function(result)
                    {
                        alert("标记已读失败:" + JSON.stringify(result.status));
                    });

        }

        function queryNotificationOk(result)
        {
            notifications = result;
            var newNotificationNum = result.length;
            if (notificationNum != null)
            {
                if (notificationNum < newNotificationNum)
                {
                    triggerNotificationBellAnimation();
                    notificationNum = newNotificationNum;
                }
            }
            else
            {
                notificationNum = newNotificationNum;
            }
            //unreadMsgNum
            var notificationHtml = AjaxGetHtml("${ctx}/msgmanage/getNotificationHtml.do?userId="
                    + userId);
            $("#notificationBody").html(notificationHtml);
            if(notificationNum!=0)
                $(".notificationNum").html(notificationNum);

            //alert(unreadMsgHtml);

        }

        function queryNotificationError(result)
        {
            if(getMsgOrNociceFailTag==false)
            {
                alert("获取通知失，请刷新页面或重新登录。");
                getMsgOrNociceFailTag=true;
            }
            console.log("消息中心：获取通知失败");
        }

        function queryMsgError(result)
        {
            if(getMsgOrNociceFailTag==false)
            {
                alert("获取消息失败，请刷新页面或重新登录。");
                getMsgOrNociceFailTag=true;
            }
            console.log("消息中心：获取消息失败");
        }

        function AjaxFlash(strUrl, strData, IsAsync, pSuccessCallback,
                pErrorCallback)
        {
            if (strUrl != "")
            {
                $.ajax(
                {
                    url : "" + strUrl + "",
                    type : "POST",
                    async : IsAsync,
                    dataType : "json",
                    data : "" + strData + "",
                    success : function(result)
                    {

                        if (pSuccessCallback != null)
                        {

                            pSuccessCallback(result);
                        }
                    },
                    error : function(result)
                    {
                        if (pErrorCallback != null)
                        {
                            pErrorCallback(result);
                        }
                    }
                });
            }
            else
            {
                var result = [];
                pSuccessCallback(result);
            }
        }

        function AjaxGetHtml(strUrl, strData)
        {
            var ret;
            if (strUrl != "")
            {
                $.ajax(
                {
                    url : "" + strUrl + "",
                    type : "POST",
                    async : false,
                    dataType : "html",
                    data : "" + strData + "",
                    success : function(result)
                    {
                        ret = result;
                    },
                    error : function(result)
                    {
                        alert("AJAX 交互错误!" + result);
                    }
                });
                return ret;
            }
            else
            {
                var result = [];
                pSuccessCallback(result);
                return null;
            }
        }

        function showNotification()
        {
            var notificationElement = $("#notificationxxx");

            // initialize the widget
            notificationElement.kendoNotification();

            // get the widget object
            var notificationWidget = notificationElement.data("kendoNotification");

            // display a "foo" message
            notificationWidget.show("foo");

        }
        
        $(function()
        {
            window.onresize = function()
            {
                var left_width = $("#sidebar").width();
                var top_height = $("#main-head").height();
                var widths = document.body.scrollWidth - left_width;
                var heights = document.documentElement.clientHeight
                        - top_height;
                $("#main-content-iframe").height(heights - 47)
                        .width(widths - 5);
                var width = document.body.clientWidth;
                if (width < 956)
                {
                    $("#main-content-iframe").width(width);
                }
            };
            var appNum ='${param.appNum}';
            if(appNum!=undefined&&appNum!=null&&appNum!="")
                 jumpToApp(appNum);
            window.onresize();
            //queryUid();
            //queryUnreadMsg();
            //queryNotification();
            initAnimation();
            if(boole){
			   alert('没有权限使用应用，请联系管理员!');
			   var url = '/logout.jsp';
		       window.location.href = ctx + url;
		    }
            
            
            //alert("less than IE10:"+lessThenIE10());
            //window.setInterval(queryUnreadMsg, 30000);
            //window.setInterval(queryNotification, 30000);
           //showNotification();
         
        });

        function jumpToApp(appNum)
        {
            var apps = $("#c_headerBar p").children();
            if(appNum>=0&&appNum<=6)
                $(apps[appNum]).click();
        }
        
        function lessThenIE10()
        {
            var UA = navigator.userAgent, isIE = UA.indexOf('MSIE') > -1, v = isIE ? /\d+/
                    .exec(UA.split(';')[1])
                    : 'no ie';
            return v < 10;

        }

        function initAnimation()
        {

            envelopeElement = document.getElementById('envelope');
            notificationBellElement = document
                    .getElementById('notificationBell');

            if (envelopeElement.addEventListener)
            {
                window.addEventListener("MSAnimationEnd", function()
                {
                    $(envelopeElement).removeClass('icon-animated-vertical');
                }, false);
            }

            if (notificationBellElement.addEventListener)
            {
                window.addEventListener("MSAnimationEnd", function()
                {
                    $(notificationBellElement).removeClass(
                            'icon-animated-vertical');
                }, false);
            }
        }

        //triggerAnimation
        function triggerEnvelopeAnimation()
        {
            //alert("trigger");
            $("#envelope").addClass('icon-animated-vertical');
            if (lessThenIE10())
            {
                alert("i am ie 8");
                shakeIcon(envelopeElement);
            }
        }

        function triggerNotificationBellAnimation()
        {
            $("#notificationBell").addClass('icon-animated-bell');
        }

        function showTransparentDiv()
        {
            $("#transparentDiv").show();
            $("#c_headerBar").slideDown(125);
        }
        function hideTransparentDiv()
        {
            $("#transparentDiv").hide();
            $("#c_headerBar").slideUp(125);
        }
        function hideSearch()
        {
            $("#nav-search").hide();
            $("#sidebar-shortcuts-large").show(125);
            document.getElementById("sidebar-shortcuts-large").style.display = "";
        }
        function showSearch()
        {
            return;
            if ($("#sidebar").attr('class') == ''
                    || $("#sidebar").attr('class') == undefined)
            {
                $("#sidebar-shortcuts-large").hide();
                $("#nav-search").show(125);
            }
            else
            {

            }
        }
        function logout()
        {
            if (!confirm("您真的要退出吗?"))
            {
                return;
            }
            var url = '/logout.jsp';
            window.location.href = ctx + url;
        }
        function createWhenRefresh()
        {
            if ($("body").find("#refresh_div").length > 0)
                $("body").find("#refresh_div").remove();
            $("body")
                    .append(
                            '<div class="widget-box-layer" id="refresh_div" style="z-index: 2000;"><i class="icon-spinner icon-spin icon-2x white"></i></div>');
        }
        function removeWhenRefresh()
        {
            $("body").find("#refresh_div").remove();
        }
        //iframe刷新
        function iframeReload(){
        	window.frames['mainContentIframe'].location.reload();
        }
        //点击主页响应
        function iframeIndex(){
        	if($(".nav-list>li:first>ul").length>0){
        		$(".nav-list>li:first>ul>li:first>a").click();
        	}else{
				$(".nav-list>li:first>a").click();        	
        	}
        }
        
        function openuser(){
        	openwindow("${ctx}/pages/roleUserPer/index.jsp?type=0",$(window).width()*0.6,$(window).height()*0.32,'edit','div','xgma');
        }
    </script>
</body>
</html>

