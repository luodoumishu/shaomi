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
<title>海南临高党建</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/16sucai.js"></script>
<script type="text/javascript" src="js/wcdj.js"></script>
<script src="${ctx}/assets/_base/js/jquery-1.8.3.js"></script>

<style type="text/css"> 
	#goleft {width: 980px;height: 150px;overflow: hidden;} 
	#goleft #gols {width: 33100px;} 
	#goleft1, #goleft2 {width: auto;float: left;} 
	.lg_ldjh{
		height: 261px;
	}
</style>
</head>

<body>
<div class="lg_box">
  	<!-- 头部 Start -->
	<c:import url="top.jsp"></c:import>	
  	<!-- 头部 End  -->
    <!--主体--> 
    <div class="lg_main">
    	<!--第一屏-->
        <div class="lg_one">
        	<div class="lg_oneleft lg_l">
        	  <!-- 头条 -->
              <div class="lg_toutiao">
                  <img src="images/lg_tt.png" width="86" height="36" /> 
                  <chl:main chlCode="index_tt" pageSize="1">
	            		<chl:article titleLength="48">
	       					<span><a href="$_article_url" target="_blank">$_article_article_title</a></span>
	       				</chl:article>
		        </chl:main>
              </div>
              <!-- 头条End -->
              <div class="lg_desce">
             	  <!--新闻列表 -->
               	  <div class="lg_news lg_l">
                    	<chl:main chlCode="index_tpxw" pageSize="7">
				            <div class="lg_ldlist">
				            	<ul>
				            		<chl:article titleLength="44">
				       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
				       				</chl:article>
				                </ul>
				            </div>
				        </chl:main>
                  </div>
                  <!--新闻列表End -->
                  <!-- 图片新闻  幻灯片-->
                  <div class="lg_hdp lg_r">
	                    <div id="myFocus">
				        	<div class="loading"><span>请稍候...</span></div>载入画面
				            <ul class="pic">内容列表
				            	<chl:main chlCode="index_tpxw" pageSize="4">
				            		<chl:article hasImage="true" titleLength="24">
				            			<li>
				            				<a href="$_article_url" target="_blank"><img src="$_article_image_path" alt="$_article_article_full_title" title="$_article_article_full_title"/></a>
				            				<P>$_article_article_title</P>
				            			</li>
				            		</chl:article>
				            	</chl:main>
				            </ul>
				   		</div>
                   </div>
                   <!-- 图片新闻 幻灯片End -->
                   <div style="clear:both;"></div>
                </div>
            </div>
            <!--通知公告-->
            <div class="lg_oneright lg_r">
	            <chl:main chlCode="index_tzgg" pageSize="4">
		        	<div class=lg_tzggtite>
		            	<chl:more>
				       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
							<p>$_chl_name</p>
				   		</chl:more>
		            </div>
		            <div class="lg_tzglis">
		            	<ul>
		            		<chl:article titleLength="52">
		       					<li>
		       						<a href="$_article_url" target="_blank">$_article_article_title</a>
		       						<span>($_article_public_time)</span>
		       					</li>
		       				</chl:article>
		                </ul>
		            </div>
		        </chl:main>
            </div>
            <!--通知公告 end-->
            <div style="clear:both;"></div>
        </div>
        <!--第一屏 end-->
        <div class="lg_banner">
        	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,28,0" width="982" height="90">
                    <param name="movie" value="images/banner1.swf" />
                    <param name="quality" value="high" />
                    <embed src="images/banner1.swf" quality="high" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="982" height="90"></embed>
            </object>
        </div>
        <!--第二屏-->
        <div class="lg_two">
        	<div class="lg_twoleft lg_l">
              <!--基层党建-->
              <div class="lg_ldjh lg_l">
                  <chl:main chlCode="index_jcdj" pageSize="8">
		        	<div class=lg_ldtite>
		            	<chl:more>
				       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
					        <p>$_chl_name</p>
				   		</chl:more>
		            </div>
		            <div class="lg_ldlist">
		            	<ul>
		            		<chl:article titleLength="36">
		       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
		       				</chl:article>
		                </ul>
		            </div>
		        </chl:main>
              </div>
              <!--基层党建end-->
              
              <!--干部工作 -->
              <div class="lg_ldjh lg_r">
                    <chl:main chlCode="index_gbgz" pageSize="8">
			        	<div class=lg_ldtite>
			            	<chl:more>
					       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
						        <p>$_chl_name</p>
					   		</chl:more>
			            </div>
			            <div class="lg_ldlist">
			            	<ul>
			            		<chl:article titleLength="36">
			       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
			       				</chl:article>
			                </ul>
			            </div>
			        </chl:main>
              </div>
              <!--干部工作 end-->
              <div style="clear:both;"></div>
              
              <!--人才工作-->
              <div class="lg_ldjh lg_l">
                    <chl:main chlCode="index_rcgz" pageSize="8">
			        	<div class=lg_ldtite>
			            	<chl:more>
					       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
						        <p>$_chl_name</p>
					   		</chl:more>
			            </div>
			            <div class="lg_ldlist">
			            	<ul>
			            		<chl:article titleLength="36">
			       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
			       				</chl:article>
			                </ul>
			            </div>
			        </chl:main>
              </div>
              <!--人才工作 end-->
              
              <!--教育培训-->
              <div class="lg_ldjh lg_r">
                	<chl:main chlCode="index_jypx" pageSize="8">
			        	<div class=lg_ldtite>
			            	<chl:more>
					       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
						        <p>$_chl_name</p>
					   		</chl:more>
			            </div>
			            <div class="lg_ldlist">
			            	<ul>
			            		<chl:article titleLength="36">
			       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
			       				</chl:article>
			                </ul>
			            </div>
			        </chl:main>
              </div>
              <!--教育培训 end-->
              <div class=" lg_banner2" style="margin-bottom:8px;">
                	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,28,0" width="730" height="90">
                    <param name="movie" value="images/banner2.swf" />
                    <param name="quality" value="high" />
                    <embed src="images/banner2.swf" quality="high" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="730" height="90"></embed>
                </object>
              </div>
              <!--调查研究-->
              <div class="lg_ldjh lg_l">
                	<chl:main chlCode="index_dcyj" pageSize="8">
			        	<div class=lg_ldtite>
			            	<chl:more>
					       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
						        <p>$_chl_name</p>
					   		</chl:more>
			            </div>
			            <div class="lg_ldlist">
			            	<ul>
			            		<chl:article titleLength="36">
			       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
			       				</chl:article>
			                </ul>
			            </div>
			        </chl:main>
              </div>
              <!--调查研究 end-->
              <!--政策法规-->
              <div class="lg_ldjh lg_r">
                	<chl:main chlCode="index_zcfg" pageSize="8">
			        	<div class=lg_ldtite>
			            	<chl:more>
					       		<span><a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a></span>
						        <p>$_chl_name</p>
					   		</chl:more>
			            </div>
			            <div class="lg_ldlist">
			            	<ul>
			            		<chl:article titleLength="36">
			       					<li><span>$_article_public_time</span><a href="$_article_url" target="_blank">$_article_article_title</a></li>
			       				</chl:article>
			                </ul>
			            </div>
			        </chl:main>
              </div>
              <!--政策法规 end-->
              <div style="clear:both;"></div>
          </div>
            <!--右边-->
            <div class="lg_tworight lg_r">
            	<div class="lg_trimg">
                	<ul>
<!--                     	<li><img src="images/lg_wytg.jpg" width="244" height="50" /></li> -->
	                        <!-- 我们都到基层去 -->
	                        <li>
	                			<chl:main chlCode="index_wmddjcq">
			                        	<chl:more>
			                        		<a href="$_channel_more_uri" target="_blank">
			                        			<img src="images/lg_gg1.jpg" width="244" height="64" />
			                        		</a>
			                        	</chl:more>
	                        	</chl:main>
	                        </li>
                        	<!-- 我们都到基层去  end-->
                        	<!-- 换届工作专栏 -->
                        	<li>
	                			<chl:main chlCode="index_hjgzzl">
			                        	<chl:more>
			                        		<a href="$_channel_more_uri" target="_blank">
			                        			<img src="images/lg_gg2.jpg" width="244" height="64" />
			                        		</a>
			                        	</chl:more>
	                        	</chl:main>
	                        </li>
	                        <!-- 换届工作专栏 end -->
	                        <!-- 12380 网上举报 -->
	                        <li>
	                        	<a href="http://12380.hainan.gov.cn/lingao" target="_blank">
	                        	<img src="images/lg_gg3.jpg" width="244" height="64" />
	                        	</a>
	                        </li>
	                        <!-- 12380 网上举报 end -->
	                        <!-- 临高县群众路线，教育实践活动 -->
	                        <li>
	                			<chl:main chlCode="index_jysjhd">
			                        	<chl:more>
			                        		<a href="$_channel_more_uri" target="_blank">
			                        			<img src="images/lg_gg4.jpg" width="244" height="64" />
			                        		</a>
			                        	</chl:more>
	                        	</chl:main>
	                        </li>
	                        <!-- 临高县群众路线，教育实践活动 end -->
                    </ul>
                  
                </div>
                <!--在线视频-->
                <div class="lg_viod">
					<chl:main chlCode="index_zxsp" pageSize="1">
			        	<div class=lg_cgttite>
					   		<video:more>
                        	 	<span><a href="$_video_more_uri" target="_blank">更多>></a></span>
                            	<p>$_chl_name</p>
                        	</video:more>
			            </div>
			            <div class="lg_vilis">
			            	<div class="lg_vitup" style="height:125px;overflow: hidden; ">
				            	<ul>
				       				<video:file pageSize="1" titleLength="10">
										<li>
									 			<img src="<%=basePath %>/file/$_video_img" width="225" height="133" />
									 			<div class="lg_vibf">
										 			<a href="$_video_url" title="$_video_video_full_title" target="_blank">
										 				<img src="images/lg_viod.png" width="55" height="55" />
										 			</a>
									 			</div>
									 	</li>
									</video:file>
				                </ul>
			                </div>
			            </div>
			        </chl:main>
                </div>
                <!--在线视频 end-->
                <!--村官天地-->
                <div class="lg_cgtd">
                    <chl:main chlCode="index_cgtd" pageSize="7">
			        	<div class=lg_cgttite>
			            	<chl:more>
					       		<span><a href="$_channel_more_uri" target="_blank">更多>></a></span>
						        <p>$_chl_name</p>
					   		</chl:more>
			            </div>
			            <div class="lg_cgtcon" style="height: 182px;">
			            	<ul>
			            		<chl:article titleLength="33">
			       					<li>
<!-- 			       						<span>$_article_public_time</span> -->
			       						<a href="$_article_url" target="_blank">·$_article_article_title</a>
			       					</li>
			       				</chl:article>
			                </ul>
			            </div>
			        </chl:main>
                </div>
                <!--村官天地 end-->
                <!-- 表格下载 -->
                <div class="lg_rwcdj">
                	<chl:main chlCode="index_bgxz" pageSize="7">
			        	<div class=lg_cgttite>
			            	<chl:more>
					       		<span><a href="$_channel_more_uri" target="_blank">更多>></a></span>
						        <p>$_chl_name</p>
					   		</chl:more>
			            </div>
			            <div class="lg_cgtcon" style="height: 182px;">
			            	<ul>
			            		<chl:article titleLength="33">
			       					<li>
<!-- 			       						<span>$_article_public_time</span> -->
			       						<a href="$_article_url" target="_blank">·$_article_article_title</a>
			       					</li>
			       				</chl:article>
			                </ul>
			            </div>
			        </chl:main>
                </div>
                <!-- 表格下载 end -->
            </div>
            <!--右边 end-->
            <div style="clear:both;"></div>
        </div>
        <!--第二屏 end-->
        <!--图文视窗  style="overflow:hidden;"-->
      	<div class="lg_twsc" style="margin-top: 5px;">
            <chl:main chlCode="index_twsc" pageSize="8">
	        	<div class=lg_twtite>
	            	<chl:more>
				        <span>
				        	<a href="$_channel_more_uri" target="_blank"><img src="images/lg_more.png" width="45" height="19" /></a>
				        </span>
           				<img src="images/lg_twsc.jpg" width="111" height="30" />
			   		</chl:more>
	            </div>
	            <div id="goleft">
	            	<div id="gols"> 
	            		<div id="goleft1"> 
				            <div class="lg_twlis">
				            	<ul>
				            		<chl:article titleLength="20" hasImage="true">
				       					<li>
				       						<a href="$_article_url" target="_blank"><img src="$_article_image_path" width="139" height="93" alt="$_article_article_full_title" /></a>
			                      		 	<p><a href="$_article_url" target="_blank">$_article_article_title</a></p>
				       					</li>
				       				</chl:article>
				                </ul>
				            </div>
			            </div>
			            <div id="goleft2"></div> 
		            </div>
	            </div>
	        </chl:main>
	        
      	</div>
        <!--图文视窗 end-->
        <!--专栏-->
				<div class="lg_zhuanlan">
					<div class="lg_jgzl lg_l">
						<div class="lg_znimg lg_l">
							<img src="images/lg_t4.jpg">
						</div>
						<div class="lg_znlist lg_r">
							<h2>
								<span class="lg_r" style="font-size: 13px;font-weight: normal;margin-top: 14px;">
									<chl:main chlCode="zt_jgzl">
										<chl:more>
											<a href="$_channel_more_uri" target="_blank">进入专栏>></a>
										</chl:more>
									</chl:main>
<!-- 									<a href="../default/zl_list.jsp?zl=jgzl" target="_blank" style="cursor: pointer;"> -->
<!-- 										进入专栏>> -->
<!-- 									</a> -->
								</span>
								<p>机关专栏</p>
							</h2>
							<ul>
								<div id="jgzl">
						        </div>
						        <li>
						        	<a href="../default/zl_list.jsp?zl=jgzl" target="_blank" style="cursor: pointer;">
					        		 更多>></a>
				        		 </li>
							</ul>
						</div>
					</div>
					<div class="lg_xzzl lg_r">
						<div class="lg_znimg lg_l">
							<img src="images/lg_t5.jpg">
						</div>
						<div class="lg_xztres lg_r">
						<h2>
 							<span class="lg_r" style="font-size: 13px;font-weight: normal;margin-top: 14px;">
<!-- 								<a href="../default/zl_list.jsp?zl=xzzl" target="_blank" style="cursor: pointer;"> -->
<!-- 									进入专栏>> -->
<!-- 								</a> -->
									<chl:main chlCode="zt_xzzl">
										<chl:more>
											<a href="$_channel_more_uri" target="_blank">进入专栏>></a>
										</chl:more>
									</chl:main>
							</span>
							<p>乡镇专栏</p>
						</h2>
							<ul>
								<div id="xzzl">
						        </div>
						         <li>
						        	<a href="../default/zl_list.jsp?zl=xzzl" target="_blank" style="cursor: pointer;">
					        		 更多>></a>
				        		 </li>
							</ul>
						</div>
					</div>
					<div style="clear: both;"></div>
				</div>
				<!--专栏 end-->
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
			  	<!-- 其他网站链接 -->
			  	<chl:main chlCode="link_qtwzlj">
						<li>
							<select class="lg_liipt"> 
							<option selected>==== $_chl_name ====</option> 
			      			<chl:link>
			      				<option value="$_link_instance_addr">$_link_instance_name</option>
			      			</chl:link>
			      			</select>
			      	   </li>
			  	</chl:main>
			  	<!-- 其他网站链接 -->
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
  		initXzLink();  //初始化乡镇专栏
  		initJgLink();  //初始化机关专栏
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
 	
 	//图文视窗滚动
	var speed=50; 
	var FGgoleft=document.getElementById('goleft'); 
	var FGgoleft1=document.getElementById('goleft1'); 
	var FGgoleft2=document.getElementById('goleft2'); 
 	if(FGgoleft1.offsetWidth > FGgoleft.offsetWidth){
		FGgoleft2.innerHTML=FGgoleft1.innerHTML;
	 	function Marquee(){ 
			if(FGgoleft2.offsetWidth - FGgoleft.scrollLeft<=0){ 
				FGgoleft.scrollLeft -= FGgoleft1.offsetWidth 
			}else{ 
				FGgoleft.scrollLeft++; 
			} 
		} 
		var MyMar=setInterval(Marquee,speed) 
		FGgoleft.onmouseover=function() { clearInterval(MyMar) } 
		FGgoleft.onmouseout=function() { MyMar=setInterval(Marquee,speed) } 
 	}
	
	//友情链接滚动
	/* var speed2=100; 
	var FGyqljgd=document.getElementById('yqljgd'); 
	var FGyqljgd1=document.getElementById('yqljgd1'); 
	var FGyqljgd2=document.getElementById('yqljgd2'); 
 	if(FGyqljgd1.offsetHeight > FGyqljgd.offsetHeight){
		FGyqljgd2.innerHTML=FGyqljgd1.innerHTML;
	 	function Marquee2(){ 
			if(FGyqljgd2.offsetHeight - FGyqljgd.scrollTop<=0){ 
				FGyqljgd.scrollTop -= FGyqljgd1.offsetHeight 
			}else{ 
				FGyqljgd.scrollTop++; 
			} 
		} 
		var MyMar2=setInterval(Marquee2,speed2) 
		FGyqljgd.onmouseover=function() { clearInterval(MyMar2) } 
		FGyqljgd.onmouseout=function() { MyMar2=setInterval(Marquee2,speed2) } 
 	} */
</script>
</body>
</html>
