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
<script type="text/javascript" src="js/wcdj.js"></script>
<script src="${ctx}/assets/_base/js/jquery-1.8.3.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function() {
	$("#shouye").removeClass("hove");
}); 
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
                   		<p>${PRE_TITLE}</p>
                    </div>
                    <div class="lis_leb">
						<ul>
							<menu:articleInSelfAndChildMenu pageSize="15" titleLength="86">
							<li><span>$_article_public_time</span></span><a href="$_article_url" title="$_article_article_full_title" target="_blank">$_article_article_title$_article_has_image_flag</a></li>
							</menu:articleInSelfAndChildMenu>
						</ul>
						<div class="lis_page">
							<menu:articlePageStr />
						</div>
					</div>
                </td>
              </tr>
            </table>
        </div>
        <!--第一屏 end-->
        
        <!--友情链接-->
        <div class="lg_link">
        	<ul>
            	<!--政府网站链接 -->
		        <chl:main chlCode="link_zfwzlj">
						<li>
							<select class="lg_liipt"> 
							<option selected>==== $_chl_name ====</option> 
			      			<chl:link>
			      				<option value="$_link_instance_addr">$_link_instance_name</option>
			      			</chl:link>
			      			</select>
			      	   </li>
			  	</chl:main>
			  	<!--政府网站链接 end-->
			  	
			  	<!--全国党建网链接 -->
		        <chl:main chlCode="link_qgdjwlj">
						<li>
							<select class="lg_liipt"> 
							<option selected>==== $_chl_name ====</option> 
			      			<chl:link>
			      				<option value="$_link_instance_addr">$_link_instance_name</option>
			      			</chl:link>
			      			</select>
			      	   </li>
			  	</chl:main>
			  	<!--全国党建网链接 end-->
			  	
			  	<!--省内党建网链接 -->
		        <chl:main chlCode="link_sndjwlj">
						<li>
							<select class="lg_liipt"> 
							<option selected>==== $_chl_name ====</option> 
			      			<chl:link>
			      				<option value="$_link_instance_addr">$_link_instance_name</option>
			      			</chl:link>
			      			</select>
			      	   </li>
			  	</chl:main>
			  	<!--省内党建网链接 end-->
			  	
			  	<!--村镇党建网链接 -->
			  	<!-- <li>
	                <select name="" class="lg_liipt">
	            	  <option>==== 村镇党建网链接 ====</option>
	            	</select>
            	</li> -->
            	<li>
		        	<select id="xzc_link" name="xzc_link" class="lg_liipt">
		            </select>
		        </li>
            	<!--村镇党建网链接 -->
            </ul>
        </div>
        <!--友情链接 end-->
    </div>
    <!--主体 end-->
    <div style="clear:both;"></div>
    <!--底部-->
	<c:import url="end.jsp"></c:import>
    <!--底部 end-->
</div>

<script type="text/javascript">
	var path = "<%=basePath%>";
	$(document).ready(function(){
  		initXzcLink();  //初始化镇(场)党建网链接
 		var yqlj = $(".lg_link select");
		if(null != yqlj && "undefined" != yqlj){
			yqlj.change(function(){
				var index = this.selectedIndex;
				if( 0 != index){					
					window.open($(this).val());  //友情链接跳转
				}
			});
		}
 	});
</script>
</body>
</html>
