<%@page import="org.apache.shiro.session.Session"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="com.xjj._extensions.roleUserPer.util.SecurityUtil"%>
<%@page import="com.xjj._extensions.roleUserPer.model.Zuser"%>
<%@page
	import="com.xjj._extensions.roleUserPer.service.impl.ZuserService"%>
<%@page import="com.xjj.framework.core.util.SpringContextUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String oa_account = request.getParameter("account");
	ZuserService uService = SpringContextUtil.getInstance().getBean(
			"ZuserService");
	Zuser zuser = uService.getZuserByAccount(oa_account);
	String cms_account = "";
	if (null != zuser) {
		cms_account = zuser.getAccount();
		if (oa_account.equals(cms_account)) {
			response.getWriter().write(basePath+"login.do?username="+cms_account+"&password="+zuser.getPassword()+"&rememberMe=false");
		}else{
			response.getWriter().write(basePath+"no_authority.jsp");
			System.out.println("no_authority");
		}

	}else{
		response.getWriter().write(basePath+"no_authority.jsp");
		System.out.println("no_authority");
	}
%>

