package com.xjj.idm.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xjj.idm.hessian.IUserInfoApi;
import com.xjj.sso.SSOConfigCache;
import com.xjj.sso.remote.LocalHessianProxyFactory;

@SuppressWarnings("serial")
public class IDMUpdatePwdTag extends BodyTagSupport {

	private String host;
	private String oldPwd;
	private String newPwd;
	private String uri;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
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
		
		String msg = "";
		try{
			HessianProxyFactory factory = new LocalHessianProxyFactory(connectTimeOut,readTimeOut,header);
			IUserInfoApi api = (IUserInfoApi) factory.create(IUserInfoApi.class, idm_url + uri);
			boolean flag = api.updateUserPwd(token, oldPwd, newPwd, null);
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
}
