<%@page import="com.xjj.jdk17.utils.StringUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/comm/taglib.jsp"%>
<%@ include file="/comm/js/taglib.jsp" %>
<%@page import="java.util.List"%>
<%@page import="com.xjj.cms.channel.service.*"%>
<%@page import="com.xjj.cms.menu.service.*"%>
<%@page import="com.xjj.cms.menu.model.*"%>
<%@page import="com.xjj.cms.article.service.ICmsArticleService"%>
<%@page import="com.xjj.framework.core.util.SpringContextUtil"%>
<%
// 	String path = request.getContextPath();
// 	String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
// 	String basePath = ip+path+"/";
	
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
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>海南临高党建</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/assets/ace/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/wcdj.js"></script>
<!-- 	<script type="text/javascript" src="${ctx}/assets/js/utils.js"></script> -->
    <% 
		String path = request.getContextPath(); 
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
	%>
	<style type="text/css">
		.lis_leb2 ul{
			padding:20px;
			}
		.lis_leb2 ul li{
			line-height:40px;
			border-bottom:1px dashed #ccd3bd;
			background:url(../images/lis_sjx.jpg) no-repeat 0 16px;
			padding-left:12px;
			font-size:14px;
			}
		.lis_leb2 ul li span{
			color:#999;
			}
	</style>
</head>
<body>

<div class="lg_box">
    <!-- 头部 Start -->
	<c:import url="top.jsp"></c:import>
	<!-- 头部 End  -->
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
                   		<p>在线视频</p>
                    </div>
                    <div class="lis_leb2" >
						<ul>
	                    	<chl:main chlCode="index_zxsp">
	             				<video:filePage pageSize="8" titleLength="10">
	             					<li style="float: left;margin-left: 20px;margin-top:10px;">
	             						<div style="text-align: center;">
		             						<span style="width: 156px;">
				             						<a href="$_video_url" title="$_video_video_full_title" target="_blank"><img src="<%=basePath %>/file/$_video_img" width="156" height="112" /></a>
		                            				<br/>
		                            				<a href="$_video_url" title="$_video_video_full_title" target="_blank">$_video_video_title</a>
		             						</span>
                            			</div>
	             					</li>
								</video:filePage> 
	                        </chl:main>
                        </ul>
					</div>
					<div style="clear:both;padding-top: 20px" class="lis_page" >
						<video:videoPageStr />
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
