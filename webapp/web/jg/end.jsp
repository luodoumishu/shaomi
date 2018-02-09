<%@page import="java.util.Date"%>
<%@page import="com.xjj.cms.visit.CountXml"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@ include file="/comm/js/taglib.jsp" %>
<%@ taglib prefix="xjj" uri="/WEB-INF/tld/xjj_portal.tld"%>
<%
	String path = request.getContextPath();
	String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String basePath = ip+path+"/";
	
//  	CountXml xmlcount=CountXml.getInstance(); 
  
//  	System.out.println(session.isNew()+"-------------------");
//    if (session.isNew()){
//      xmlcount.addcount(new Date());//增加访问量 
//      int n =xmlcount.getTotalCount();//取总访问量  
//      String count=Integer.toString(n); 
//      session.putValue("count",count);  
      
   // } 
%>
<div class="lg_copyright">
    <div class="lg_crline"></div>
    <div class="lg_crtext">
                    主办单位：中共临高县委组织部      琼ICP备11002628号-1 <br />

                    技术支持：新境界软件    联系电话：0898-28289561 投稿邮箱：lingaodj@126.com<br />
              <!--     您是本站的： 87405 位访客  当前在线 1 人 今日访问IP： 32  总共访问IP： 87405<br /> 
        51.La 网站流量统计系统<br /> -->
                 最佳效果：1024*768分辨率/建议使用微软公司浏览器IE8.0以上
    </div>
</div>
