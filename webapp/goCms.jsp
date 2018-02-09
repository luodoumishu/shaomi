<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="com.xjj.oa.util.Constants"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.xjj.oa.util.OaUtil" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*, java.io.*, java.net.*,java.security.MessageDigest,java.math.BigInteger" %>
<%
response.setDateHeader("Expires",(new java.util.Date(0)).getTime());
request.setCharacterEncoding("GBK");
String mailUrl = Constants.getString("CMS_Server_Url");   // http://192.168.12.132:8080/
if(mailUrl == null || "".equals(mailUrl)) {
	out.print("配置系统常量 CMS_Server_Url");
	return;
}
try {
	int userId = ((Integer)session.getAttribute("userId")).intValue();  // 拿到用户帐号
	String userAccount = com.xjj.oa.util.OaUtil.getAccountsById(userId);
	HashMap<String, String> param = new HashMap<String, String>();
	param.put("account", userAccount);
	String url = mailUrl + "/cms.jsp";
	HashMap<String, String> rdata = getHttpURLConnection(url, param);
	String toUrl = rdata.get("result");
	System.out.println("CMS_Server_Url token:" + toUrl);
	
	//判断这个值 token
	
	response.sendRedirect(toUrl);
} catch(Exception e) {
	e.printStackTrace();
	response.sendRedirect(mailUrl);
}
%>
<%!
	public static HashMap<String, String> getHttpURLConnection(String addr, HashMap<String, String> param) throws Exception {
		HttpURLConnection httpconn = null;
		HashMap<String, String> rdata = new HashMap<String, String>();
		try {
			URL url = new URL(addr);
			httpconn = (HttpURLConnection) url.openConnection();
			httpconn.setDoOutput(true);
			httpconn.setDoInput(true);
			httpconn.setUseCaches(false);
			httpconn.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			httpconn.setRequestMethod("POST");
		} catch (Exception e) {
			rdata.put("msg", e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		try {
			if (param != null && param.size() > 0) {
				String params = new String();
				int i = 0;
				for (String key : param.keySet()) {
					if (i > 0) {
						params += "&";
					}
					params += key + "=" + param.get(key);
					i++;
				}
				OutputStream os = httpconn.getOutputStream();
				os.write(params.getBytes());
				os.flush();
				os.close();
			}

			InputStream in = httpconn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			in.close();
			rdata.put("result", sb.toString());
		} catch (Exception e) {
			rdata.put("msg", e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return rdata;
	}
%>
