<%@page import="com.xjj.jdk17.utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@ include file="/comm/js/taglib.jsp"%>
<%@ taglib prefix="xjj" uri="/WEB-INF/tld/xjj_portal.tld"%>
<%
	String path = request.getContextPath();
	String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String basePath = ip+path+"/";
	
	String orgCode = request.getParameter("orgCode");
	String orgName = request.getParameter("orgName");
	if(StringUtil.isEmpty(orgCode)){
		orgCode = "btx";
	}
	if(StringUtil.isEmpty(orgName)){
		orgName = "海南保亭";
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
</head>

<body>
<div class="bt_box">
    <!--导航-->
    <!-- 头部 Start -->
	<c:import url="top.jsp"></c:import>
	<!-- 头部 End  -->
    <!--导航 end-->
    <!--主体-->
    <div class="bt_main">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td valign="top" class="lis_leftbox">
                <div class="lis_lefttop">功能栏目</div>
                <div class="lis_leftnav">
                    <c:import url="menu_left_daohang.jsp"></c:import>
                </div>
            </td>
            <td valign="top">
                <div class="lis_rightbox">
                    <div class="lis_rtop">
                        <div class="lis_rtoptite" id="menuName" title="${PRE_TITLE}" style="cursor: pointer;">${PRE_TITLE}</div>
                    </div>
                   <div class="lis_leib">
						<ul id="tab">
							<menu:articleInSelfAndChildMenu pageSize="15" titleLength="82">
							<li><span>$_article_public_time</span></span><a href="$_article_url" title="$_article_article_full_title" target="_blank">$_article_article_title$_article_has_image_flag</a></li>
							</menu:articleInSelfAndChildMenu>
						</ul>
						<div class="lis_page">
							<menu:articlePageStr />
						</div>
					</div>
                </div>
            </td>
          </tr>
        </table>
    </div>
    <!--主体 end-->
    <div style="clear:both;"></div>
    <!--底部-->
	<c:import url="end.jsp"></c:import>
    <!--底部 end-->
</div>
</body>
</html>
