package com.xjj.idm.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xjj.idm.hessian.IUserInfoApi;
import com.xjj.idm.model.SsoUser;
import com.xjj.sso.SSOConfigCache;
import com.xjj.sso.remote.LocalHessianProxyFactory;

@SuppressWarnings("serial")
public class IDMUserInfoTag extends BodyTagSupport {

	private String host;
	private SsoUser user;
	private String uri;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public SsoUser getUser() {
		return user;
	}
	public void setUser(SsoUser user) {
		this.user = user;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		
		String token = pageContext.getSession().getAttribute("sso_token") != null ? ((String) pageContext.getSession().getAttribute("sso_token")) : "";
		if (host.toLowerCase().startsWith("http://")) {
			host = host.substring("http://".length(), host.length());
		}
		Map<String, Map<String, String>> hostSSOConfig = SSOConfigCache.getCache().getHostSSOConfigCache();
		Map<String, String> ssoConfig = hostSSOConfig.get(host);
		String idm_url = ssoConfig.get("idm_url");
		String ssoWsUrl = ssoConfig.get("sso_ws_url");
		String ssoWsName = ssoConfig.get("sso_ws_user");
		String ssoWsPw = ssoConfig.get("sso_ws_pw");
		int connectTimeOut = 10000;
		int readTimeOut = 30000;
		Map<String,String> header = new HashMap<String,String>();
		header.put("SSO_WS_HOST", ssoWsUrl);
		header.put("SSO_WS_USER", ssoWsName);
		header.put("SSO_WS_PW", ssoWsPw);
		
		String xml = getUserList2Xml(user);
		//System.out.println(xml + "-----token:" + token + "-----------");
		String msg = "";
		try{
			HessianProxyFactory factory = new LocalHessianProxyFactory(connectTimeOut,readTimeOut,header);
			IUserInfoApi api = (IUserInfoApi) factory.create(IUserInfoApi.class, idm_url + uri);
			boolean flag = api.updateUserInfo(token, xml, null);
			if(flag) {
				msg = "1";
			} else {
				msg = "-1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "-1";
		}
		try {
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
	
	public String getUserList2Xml(SsoUser user) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		sb.append("<root>");
			String s1 = "<![CDATA[";
			String s2 = "]]>";
			String name = user.getName() != null ? user.getName() : "";
			String addr = user.getAddr() != null ? user.getAddr() : "";
			String phoneNo = user.getPhoneno() != null ? user.getPhoneno() : "";
			String faxNo = user.getFaxno() != null ? user.getFaxno() : "";
			String mobileNo = user.getMobileno() != null ? user.getMobileno() : "";
			String email = user.getEmail() != null ? user.getEmail() : "";
			sb.append("<user>");
			sb.append("<id>" + user.getId() + "</id>");
			sb.append("<name>" + s1 + name + s2 + "</name>");
			sb.append("<addr>" + s1 + addr + s2 + "</addr>");
			sb.append("<phoneNo>" + phoneNo + "</phoneNo>");
			sb.append("<mobileNo>" + mobileNo + "</mobileNo>");
			sb.append("<faxNo>" + faxNo + "</faxNo>");
			sb.append("<email>" + email + "</email>");
			sb.append("<gender>" + s1 + user.getGender() + s2 + "</gender>");
			sb.append("</user>");
		sb.append("</root>");
		return sb.toString();
	}
}
