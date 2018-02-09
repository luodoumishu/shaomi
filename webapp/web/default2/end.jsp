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
<div class="bt_copyright">
  	<div class="bt_crline"></div>
     <div class="bt_crtext">
       版权所有：中共保亭黎族苗族自治县委组织部<br />
地址：海南省保亭县城县委大院 邮编：572300 电话：0898-83668425 邮箱：btzzb@163.com<br /> 
<span style="color:#ae0202;">访问人数：152562</span>  
     </div>
</div>
