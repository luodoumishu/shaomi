<%@page import="javax.script.Invocable"%>
<%@page import="java.io.FileReader"%>
<%@page import="javax.script.ScriptContext"%>
<%@page import="javax.script.Bindings"%>
<%@page import="javax.persistence.metamodel.Bindable"%>
<%@page import="javax.script.ScriptException"%>
<%@page import="javax.script.ScriptEngine"%>
<%@page import="javax.script.ScriptEngineManager"%>
<%@page import="com.xjj.cms.base.util.BaseConstant"%>
<%@page import="com.xjj.framework.core.web.filter.WebContext"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.xjj.framework.core.util.SpringContextUtil"%>
<%@page import="com.xjj.cms.article.service.ICmsArticleService"%>
<%@page import="com.baidu.ueditor.define.BaseState"%>
<%@page import="com.baidu.ueditor.define.State"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	String stateStr = new ActionEnter( request, rootPath ).exec();
	out.write(stateStr);
%>


