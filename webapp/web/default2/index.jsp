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
<title>海南保亭党建</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/16sucai.js"></script>
<script type="text/javascript" src="js/wcdj.js"></script>
<script src="${ctx}/assets/_base/js/jquery-1.8.3.js"></script>
</head>

<body>
<div class="bt_box">
  	<!-- 头部 Start -->
	<c:import url="top.jsp"></c:import>	
  	<!-- 头部 End  -->
    <!--主体-->
    <div class="bt_main">
    	<div class="bt_maleft">
        	<!--第一屏-->
            <div class="bt_mlone">
            	<!--幻灯片-->
                 <div class="bt_mlhdp">
			     	<div id="myFocus">
			        <div class="loading"><span>请稍候...</span></div>载入画面
			            <ul class="pic">内容列表
			            	<chl:main chlCode="index_tpxw" pageSize="4">
			            		<chl:article hasImage="true" titleLength="24">
			            			<li><a href="$_article_url" target="_blank"><img src="$_article_image_path" alt="$_article_article_full_title"/></a><P>$_article_article_title</P></li>
			            		</chl:article>
			            	</chl:main>
			            </ul>
			   		</div>
			     </div>
                <!--幻灯片 end-->
                <!--工作动态-->
              <div class="bt_gzdtwork">
                	<div class="bt_wrtop">
                    	<!--<span><img src="images/bt_more.png" width="52" height="19" /></span>  -->
						<!--工作动态 -->
                        <chl:main chlCode="index_gzdt">
				            	<chl:more>
						       		<a id="qh_gzdt_a" href="$_channel_more_uri" class="bt_taba" onmouseover="qiehuan('qh_gzdt')" target="_blank">$_chl_name</a></span>
						   		</chl:more>
			            </chl:main>
			            
			            
			            <!--乡镇党建-->
		            	<a id="qh_xzdjlj_a" class="bt_taba" onmouseover="qiehuan('qh_xzdjlj')" target="_blank">乡镇党建</a>
			          <%--   <chl:main chlCode="index_xzdj">
 				            	<chl:more> 
 						       		<a id="qh_xzdj_a" href="$_channel_more_uri" class="bt_taba" onmouseover="qiehuan('qh_xzdj')" target="_blank">$_chl_name</a></span>
 						   		</chl:more>
 			            </chl:main> --%>
 			            
 			            
			            <!--机关党建 -->
		            	<a id="qh_jgdjlj_a" class="bt_taba" onmouseover="qiehuan('qh_jgdjlj')" target="_blank">机关党建</a>
			            <%-- <chl:main chlCode="index_jgdj">
				            	<chl:more>
						       		<a id="qh_jgdj_a" href="$_channel_more_uri" class="bt_taba" onmouseover="qiehuan('qh_jgdj')" target="_blank">$_chl_name</a></span>
						   		</chl:more>
			            </chl:main> --%>
			            <!--重要言论 -->
                        <chl:main chlCode="index_zyyl">
				            	<chl:more>
						       		<a id="qh_zyyl_a" href="$_channel_more_uri" class="bt_taba" onmouseover="qiehuan('qh_zyyl')" target="_blank">$_chl_name</a></span>
						   		</chl:more>
			            </chl:main>
			            
                  	</div>
                  	
                    <div id="qh_gzdt" class="bt_wrlis" style="display: none;">
                    	<ul>
                    	<chl:main chlCode="index_gzdt" pageSize="7">   
			            		<chl:article  titleLength="44">
			            			<li><span>$_article_public_time</span><a href="$_article_url" target="_blank"  alt="$_article_article_full_title" >$_article_article_title</a></li>
			            		</chl:article>
			            </chl:main>
                        </ul>
                    </div>
                    
                    <div id="qh_xzdjlj" class="bt_wrlis" style="display: none;">
                    	
                    </div>
                    
                    <div id="qh_jgdjlj" class="bt_wrlis" style="display: none;">
                    </div>
                    
                    <div id="qh_xzdj" class="bt_wrlis" style="display: none;" >
                    	<ul>
                    	<chl:main chlCode="index_xzdj" pageSize="7">
			            		<chl:article  titleLength="44">
			            			<li><span>$_article_public_time</span><a href="$_article_url" target="_blank"  alt="$_article_article_full_title" >$_article_article_title</a></li>
			            		</chl:article>
			            </chl:main>
                        </ul>
                    </div>
                    
                    <div id="qh_jgdj" class="bt_wrlis" style="display: none;" >
                    	<ul>
                    	<chl:main chlCode="index_jgdj" pageSize="7">
			            		<chl:article  titleLength="44">
			            			<li><span>$_article_public_time</span><a href="$_article_url" target="_blank"  alt="$_article_article_full_title" >$_article_article_title</a></li>
			            		</chl:article>
			            </chl:main>
                        </ul>
                    </div>
                    
                    <div id="qh_zyyl" class="bt_wrlis" style="display: none;">
                    	<ul>
                    	<chl:main chlCode="index_zyyl" pageSize="7">
			            		<chl:article  titleLength="44">
			            			<li><span>$_article_public_time</span><a href="$_article_url" target="_blank"  alt="$_article_article_full_title" >$_article_article_title</a></li>
			            		</chl:article>
			            </chl:main>
                        </ul>
                    </div>
              </div>
                <!--工作动态 end-->
            </div>
            <!--第一屏 end-->
            <div class="bt_banner"><img src="images/bt_banner.jpg" width="745" height="105" /></div>
          	<!--县乡换届专栏-->
            <div class="bt_mltwo">
	            <chl:main chlCode="index_xxhjzl" pageSize="6">
		        	<div class="bt_cxtop">
		            	<chl:more>
				       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.png" width="52" height="19" /></a></span>
				   		</chl:more>
				        <p>$_chl_name</p>
		            </div>
		            <div class="bt_cxlis">
		            	<div class="bt_cxleft">
			            	<ul>
			            		<chl:article titleLength="54">
			       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
			       				</chl:article>
			                </ul>
		                </div>
		                <div class="bt_cxright">
<!--                	    		<img src="images/bt_t1.jpg" width="202" height="135" />
                        		<p><a href="">关于做好乡镇党委领导班子换届...</a></p> -->
                        	<chl:main chlCode="index_xxhjzl" pageSize="1">
			            		<chl:article hasImage="true" titleLength="24">
			            			<img src="$_article_image_path" width="202" height="135" />
                        			<p><a href="$_article_url">$_article_article_title</a></p>
			            		</chl:article>
			            	</chl:main>
	                    </div>
	                    <div style="clear:both;"></div>
		            </div>
	            </chl:main>
            </div>
         	 <!--县乡换届专栏 end-->
             <!--基层党建-->
             <div class="bt_jcdj bt_l">
	        	<chl:main chlCode="index_jcdj" pageSize="6">
	        	<div class="bt_jctop">
	            	<chl:more>
			       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.gif" width="52" height="19" /></a></span>
			   		</chl:more>
			        <p>$_chl_name</p>
	            </div>
	            <div class="bt_jclist">
	            	<ul>
	            		<chl:article titleLength="36">
	       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
	       				</chl:article>
	                </ul>
	            </div>
	            </chl:main>
	        </div>
            <!--基层党建 end-->
            
            <!--人才培训-->
            <div class="bt_jcdj bt_r">
	        	<chl:main chlCode="index_rcpx" pageSize="6">
	        	<div class="bt_jctop">
	            	<chl:more>
			       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.gif" width="52" height="19" /></a></span>
			   		</chl:more>
			        <p>$_chl_name</p>
	            </div>
	            <div class="bt_jclist">
	            	<ul>
	            		<chl:article titleLength="36">
	       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
	       				</chl:article>
	                </ul>
	            </div>
	            </chl:main>
	        </div>
            <!--人才培训 end-->
             
            <div style="clear:both;"></div>
            <!--干部监督-->
             <div class="bt_jcdj bt_l">
	        	<chl:main chlCode="index_gbjd" pageSize="6">
	        	<div class="bt_jctop">
	            	<chl:more>
			       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.gif" width="52" height="19" /></a></span>
			   		</chl:more>
			        <p>$_chl_name</p>
	            </div>
	            <div class="bt_jclist">
	            	<ul>
	            		<chl:article titleLength="36">
	       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
	       				</chl:article>
	                </ul>
	            </div>
	            </chl:main>
	        </div>
             <!--干部监督 end-->
             <!--电教工作-->
             <div class="bt_jcdj bt_r">
	        	<chl:main chlCode="index_djgz" pageSize="6">
	        	<div class="bt_jctop">
	            	<chl:more>
			       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.gif" width="52" height="19" /></a></span>
			   		</chl:more>
			        <p>$_chl_name</p>
	            </div>
	            <div class="bt_jclist">
	            	<ul>
	            		<chl:article titleLength="36">
	       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
	       				</chl:article>
	                </ul>
	            </div>
	            </chl:main>
	         </div>
             <!--电教工作 end-->
             <div style="clear:both;"></div>
             <!--政策法规-->
             <div class="bt_jcdj bt_l">
	        	<chl:main chlCode="index_zcfg" pageSize="6">
	        	<div class="bt_jctop">
	            	<chl:more>
			       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.gif" width="52" height="19" /></a></span>
			   		</chl:more>
			        <p>$_chl_name</p>
	            </div>
	            <div class="bt_jclist">
	            	<ul>
	            		<chl:article titleLength="36">
	       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
	       				</chl:article>
	                </ul>
	            </div>
	            </chl:main>
	         </div>
             <!--政策法规 end-->
             <!--经验交流-->
             <div class="bt_jcdj bt_r">
	        	<chl:main chlCode="index_jyjl" pageSize="6">
	        	<div class="bt_jctop">
	            	<chl:more>
			       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.gif" width="52" height="19" /></a></span>
			   		</chl:more>
			        <p>$_chl_name</p>
	            </div>
	            <div class="bt_jclist">
	            	<ul>
	            		<chl:article titleLength="36">
	       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
	       				</chl:article>
	                </ul>
	            </div>
	            </chl:main>
	         </div>
             <!--经验交流 end-->
             <div style="clear:both;"></div>
        </div>
        <div class="bt_maright">
        	<!--公示公告-->
            <div class="bt_gsgg">
	        	<chl:main chlCode="index_gsgg" pageSize="4">
	        	<div class="bt_ggtop">
	            	<chl:more>
			       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.png" width="52" height="19" /></a></span>
			   			<img src="images/bt_wdt.jpg" width="36" height="36" />$_chl_name
			   		</chl:more>
	            </div>
	            <div class="bt_gglis">
	            	<ul>
	            		<chl:article titleLength="50">
	       					<li>
	       						<a href="$_article_url" target="_blank">$_article_article_title</a>
	       						<span>($_article_public_time)</span>
	       					</li>
	       				</chl:article>
	                </ul>
	            </div>
	            </chl:main>
	         </div>
            <!--公示公告 end-->
            <!--图片-->
            <div class="bt_photo">
                <chl:main chlCode="index_tplj">
            	<ul>
            			<chl:link>
               				<li><a href="$_link_instance_addr" target="$_link_open_type"><img src="$_link_instance_image" width="228" height="70" /></a></li>
              			</chl:link>
                </ul>
          		</chl:main>
            </div>
            <!--图片 end-->
            <!--创先争优 end-->
            <div class="bt_cxyx" style="height: 270px">
	        	<chl:main chlCode="index_cxzy" pageSize="8">
	        	<div class="bt_cxytop">
	            	<chl:more>
			       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.png" width="52" height="19" /></a></span>
			   			<img src="images/bt_wdt.jpg" width="36" height="36" />$_chl_name
			   		</chl:more>
	            </div>
	            <div class="bt_cxylis">
	            	<ul>
	            		<chl:article titleLength="30">
	       					<li>
<!-- 	       						<span>$_article_public_time</span> -->
	       						<a href="$_article_url" target="_blank">$_article_article_title</a>
	       					</li>
	       				</chl:article>
	                </ul>
	            </div>
	            </chl:main>
	         </div>
            <!--创先争优 end-->
            <!--党务知识-->
            <div class="bt_dwzs" style="height: 290px">
	        	<chl:main chlCode="index_dwzs" pageSize="9">
	        	<div class="bt_dwztop">
	            	<chl:more>
			       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.png" width="52" height="19" /></a></span>
			   			<img src="images/bt_wdt.jpg" width="36" height="36" />$_chl_name
			   		</chl:more>
	            </div>
	            <div class="bt_dwzlis">
	            	<ul>
	            		<chl:article titleLength="30">
	       					<li>
	       						<!-- <span>$_article_public_time</span> -->
	       						<a href="$_article_url" target="_blank">$_article_article_title</a>
	       					</li>
	       				</chl:article>
	                </ul>
	            </div>
	            </chl:main>
	         </div>
            <!--党务知识 end-->
            
            <!--资料下载-->
            <div class="bt_zlxz" style="height: 300px">
	        	<chl:main chlCode="index_zlxz" pageSize="9">
	        	<div class="bt_zlxtop">
	            	<chl:more>
			       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/bt_more.png" width="52" height="19" /></a></span>
			   			<img src="images/bt_wdt.jpg" width="36" height="36" />$_chl_name
			   		</chl:more>
	            </div>
	            <div class="bt_zlxlis">
	            	<ul>
	            		<chl:article titleLength="30">
	       					<li>
	       						<!-- <span>$_article_public_time</span> -->
	       						<a href="$_article_url" target="_blank">$_article_article_title</a>
	       					</li>
	       				</chl:article>
	                </ul>
	            </div>
	            </chl:main>
	         </div>
            <!--资料下载 end-->
        </div>
    </div>
    <!--主体 end-->
    <div style="clear:both;"></div>
    <!--友情链接-->
    <div class="bt_link">
        <ul>
		<!--政府网站链接 -->
        <chl:main chlCode="index_zfwzlj">
				<li>
					<select> 
					<option selected>==== $_chl_name ====</option> 
	      			<chl:link>
	      				<option value="$_link_instance_addr">$_link_instance_name</option>
	      			</chl:link>
	      			</select>
	      	   </li>
	  	</chl:main>
	  	<!--政府网站链接 end-->
	  	<!--全国党建网链接 -->
	  	<chl:main chlCode="index_qgdjwlj">
				<li>
					<select> 
					<option selected>==== $_chl_name ====</option> 
	      			<chl:link>
	      				<option value="$_link_instance_addr">$_link_instance_name</option>
	      			</chl:link>
	      			</select>
	      	   </li>
	  	</chl:main>
	  	<!--全国党建网链接end -->
	  	<!--省内党建网链接 -->
<%-- 	  	<chl:main chlCode="index_sndjwlj">
				<li>
					<select> 
					<option selected>==== $_chl_name ====</option> 
	      			<chl:link>
	      				<option value="$_link_instance_addr">$_link_instance_name</option>
	      			</chl:link>
	      			</select>
	      	   </li>
	  	</chl:main> --%>
	  	<li>
        	<select id="xzc_link" name="xzc_link" class="wc_sele">
            </select>
        </li>
	  	<!--省内党建网链接end -->
	  	<!--其他相关网站 -->
	  	<chl:main chlCode="index_qtxgwz">
				<li>
					<select> 
					<option selected>==== $_chl_name ====</option> 
	      			<chl:link>
	      				<option value="$_link_instance_addr" >$_link_instance_name</option>
	      			</chl:link>
	      			</select>
	      	   </li>
	  	</chl:main>
	  	<!--其他相关网站end-->
	  	
      	</ul>
    </div>
    <!--友情链接 end-->
  	<!--底部-->
  	<c:import url="end.jsp"></c:import>
  	<!--底部 end-->
</div>
<script type="text/javascript">
	var path = "<%=basePath%>";
	$(document).ready(function(){
 		qiehuan("qh_gzdt");
 		initXzcLink();  //初始化镇(场)党建网链接
 		initXzLink(); //初始化乡镇党建网链接
 		initJgLink(); //初始化机关党建网链接
 		var yqlj = $(".bt_link select");
		if(null != yqlj && "undefined" != yqlj){
			yqlj.change(function(){
				var index = this.selectedIndex;
				if( 0 != index){					
					window.open($(this).val());  //友情链接跳转
				}
			});
		}
 	});
 	
 	function qiehuan(id){
		$("#qh_gzdt").hide();
		$("#qh_gzdt_a").removeClass("bt_taba hove");
		$("#qh_gzdt_a").addClass("bt_taba");
		
		$("#qh_xzdj").hide();
		$("#qh_xzdj_a").removeClass("bt_taba hove");
		$("#qh_xzdj_a").addClass("bt_taba");
		
		$("#qh_jgdj").hide();
		$("#qh_jgdj_a").removeClass("bt_taba hove");
		$("#qh_jgdj_a").addClass("bt_taba");
		
		$("#qh_zyyl").hide();
		$("#qh_zyyl_a").removeClass("bt_taba hove");
		$("#qh_zyyl_a").addClass("bt_taba");
		
		$("#qh_xzdjlj").hide();
		$("#qh_xzdjlj_a").removeClass("bt_taba hove");
		$("#qh_xzdjlj_a").addClass("bt_taba");
		
		$("#qh_jgdjlj").hide();
		$("#qh_jgdjlj_a").removeClass("bt_taba hove");
		$("#qh_jgdjlj_a").addClass("bt_taba");
		
		$("#"+id).show();
		$("#"+id+"_a").removeClass("bt_taba");
        $("#"+id+"_a").addClass("bt_taba hove");
 	}
</script>
</body>
</html>
