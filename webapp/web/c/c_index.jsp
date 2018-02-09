<%@page import="com.xjj.jdk17.utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@ include file="/comm/js/taglib.jsp" %>
<%@ taglib prefix="xjj" uri="/WEB-INF/tld/xjj_portal.tld"%>
<%
	String path = request.getContextPath();
	String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String basePath = ip+path;
	
	String orgCode = request.getParameter("orgCode");
	String orgName = request.getParameter("orgName");
	orgName = java.net.URLDecoder.decode(orgName, "UTF-8");
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
<title><%=orgName%>党建网</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/16sucai.js"></script>
<script type="text/javascript" src="js/wcdj.js"></script>
<script src="${ctx}/assets/_base/js/jquery-1.8.3.js"></script>
</head>

<body>
<div class="lg_box">
  	<!-- 头部 Start -->
	<c:import url="top.jsp"></c:import>	
  	<!-- 头部 End  -->
    <!--主体--> 
    <div class="lg_main">
    	<!--第一屏-->
        <div class="lg_xzone">
            <!-- 图片新闻 -->
	        <div class="lg_xzhdp lg_l">
                <div id="myFocus">
	        	<div class="loading"><span>请稍候...</span></div>载入画面...
		            <ul class="pic"><!-- 内容列表 -->
		            	<chl:main chlCode="c_tpxw" pageSize="4">
		            		<chl:article hasImage="true" titleLength="60">
		            			<li><a href="$_article_url" target="_blank"><img src="$_article_image_path" alt="$_article_article_full_title"/></a><P>$_article_article_title</P></li>
		            		</chl:article>
		            	</chl:main>
		            </ul>
	   			</div>
	        </div>
	        <!-- 图片新闻End -->
	        <!-- 最新动态 -->
          	<div class="lg_xznews lg_r">
                <chl:main chlCode="c_zxdt" pageSize="8">
		        	<div class=lg_xzntite>
		            	<chl:more>
				       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
							<p>$_chl_name</p>
				   		</chl:more>
		            </div>
		            <div class="lg_xzntext">
		            	<ul>
		            		<chl:article titleLength="46">
		       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
		       				</chl:article>
		                </ul>
		            </div>
		        </chl:main>
          	</div>
          	<!-- 最新动态  End-->
          <div style="clear:both"></div>
        </div>
        <!--第一屏 end-->
        <!--第二屏-->
        <div class="lg_xztwo">
        	<!--本村概况-->
            <div class="lg_xzbzgk lg_l">
                <chl:main chlCode="c_bcgk" pageSize="6">
		        	<div class=lg_xzbztite>
		            	<chl:more>
				       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
							<p>$_chl_name</p>
				   		</chl:more>
		            </div>
		            <div class="lg_xzbztext">
		            	<ul>
		            		<chl:article titleLength="52">
		       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
		       				</chl:article>
		                </ul>
		            </div>
		        </chl:main>
            </div>
            <!--本村概况 end-->
            <!--村务公开-->
            <div class="lg_xzbzgk lg_r">
                <chl:main chlCode="c_cwgk" pageSize="6">
		        	<div class=lg_xzbztite>
		            	<chl:more>
				       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
							<p>$_chl_name</p>
				   		</chl:more>
		            </div>
		            <div class="lg_xzbztext">
		            	<ul>
		            		<chl:article titleLength="52">
		       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
		       				</chl:article>
		                </ul>
		            </div>
		        </chl:main>
            </div>
            <!--村务公开 end-->
            <div style="clear:both;"></div>
            <!--党建工作-->
            <div class="lg_xzbzgk lg_l">
                <chl:main chlCode="c_djgz" pageSize="6">
		        	<div class=lg_xzbztite>
		            	<chl:more>
				       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
							<p>$_chl_name</p>
				   		</chl:more>
		            </div>
		            <div class="lg_xzbztext">
		            	<ul>
		            		<chl:article titleLength="52">
		       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
		       				</chl:article>
		                </ul>
		            </div>
		        </chl:main>
            </div>
            <!--党建工作end-->
            <!--政策法规-->
            <div class="lg_xzbzgk lg_r">
            	<chl:main chlCode="c_zcfg" pageSize="6">
		        	<div class=lg_xzbztite>
		            	<chl:more>
				       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
							<p>$_chl_name</p>
				   		</chl:more>
		            </div>
		            <div class="lg_xzbztext">
		            	<ul>
		            		<chl:article titleLength="52">
		       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
		       				</chl:article>
		                </ul>
		            </div>
		        </chl:main>
            </div>
            <!--政策法规 end-->
            <div style="clear:both;"></div>
        </div>
        <!--第二屏 end-->
        <div style="clear:both;"></div>
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
        <!--底部-->
		<c:import url="end.jsp"></c:import>
        <!--底部 end-->
    </div>
    <!--主体 end-->
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
