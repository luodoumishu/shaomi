<%
session.invalidate();
%>
<SCRIPT LANGUAGE="JavaScript">
top.location="<%=request.getContextPath()%>/login.jsp";
</SCRIPT>