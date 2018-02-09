<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%@ taglib prefix="xjj" uri="/WEB-INF/tld/xjj_portal.tld"%>
<xjj:updatepwd host="${header.Host}" oldPwd="${param.oldPwd}" newPwd="${param.newPwd}" uri="/remote/userInfoApi" />