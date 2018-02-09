<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@ taglib prefix="xjj" uri="/WEB-INF/tld/xjj_portal.tld"%>
<%@ page import="com.xjj.idm.model.*"%>
<%
String name=request.getParameter("name"); 
String id=request.getParameter("id"); 
String gender=request.getParameter("gender"); 
String addr=request.getParameter("addr"); 
String phoneno=request.getParameter("phoneno"); 
String mobileno=request.getParameter("mobileno"); 
String faxno=request.getParameter("faxno"); 
String email=request.getParameter("email"); 

SsoUser user=new SsoUser();
user.setId(Long.parseLong(id));
user.setName(name);
user.setGender(gender);
user.setAddr(addr);
user.setPhoneno(phoneno);
user.setMobileno(mobileno);
user.setFaxno(faxno);
user.setEmail(email);
%>
<xjj:userinfo host="${header.Host}" user='<%=user %>' uri="/remote/userInfoApi" />