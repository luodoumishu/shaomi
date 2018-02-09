package com.xjj.oa.tag;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xjj.oa.archive.hessian.IArchiveHessianServlet;
import com.xjj.oa.mail.hessian.MailHessian;
import com.xjj.oa.notify.hessian.INotifyHessianForServer;
import com.xjj.sso.SSOConfigCache;
import com.xjj.sso.remote.LocalHessianProxyFactory;

@SuppressWarnings("serial")
public class TipTag extends BodyTagSupport {

	private String host;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		
		String token = pageContext.getSession().getAttribute("sso_token") != null ? ((String) pageContext.getSession().getAttribute("sso_token")) : "";
		if (host.toLowerCase().startsWith("http://")) {
			host = host.substring("http://".length(), host.length());
		}
		Map<String, Map<String, String>> hostSSOConfig = SSOConfigCache.getCache().getHostSSOConfigCache();
		Map<String, String> ssoConfig = hostSSOConfig.get(host);
		String default_oa_url = ssoConfig.get("default_oa_url");
		String ssoWsUrl = ssoConfig.get("sso_ws_url");
		String ssoWsName = ssoConfig.get("sso_ws_user");
		String ssoWsPw = ssoConfig.get("sso_ws_pw");
		int connectTimeOut = 10000;
		int readTimeOut = 30000;
		Map<String,String> header = new HashMap<String,String>();
		header.put("SSO_WS_HOST", ssoWsUrl);
		header.put("SSO_WS_USER", ssoWsName);
		header.put("SSO_WS_PW", ssoWsPw);
		StringBuffer sb = new StringBuffer();
		String notifyUrl = default_oa_url;
		String mailUrl = default_oa_url + "/pubinfo/mail/index.jsp";
		String archiveUrl = default_oa_url + "/flow/worklist/wlist.jsp";
		try {
			notifyUrl = default_oa_url + "/pubinfo/infopage/default.jsp?modelName=" + java.net.URLEncoder.encode("通知公告","GBK") + "&list=listformore&all=all";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		int unReadMialCount = 0;
		int unReadNotifyCount = 0;
		String unReadArchiveCount = "0";
		sb.append("<ul>");
		try{
			try {
				HessianProxyFactory factory = new LocalHessianProxyFactory(connectTimeOut, readTimeOut, header);
				MailHessian mailApi = (MailHessian) factory.create(
						MailHessian.class, default_oa_url + "/mailHessian");
				unReadMialCount = mailApi.getUnReadMail1(token, null);
			} catch (Exception e) {
				e.printStackTrace();
				unReadMialCount = 0;
			}
			try {
				HessianProxyFactory factory = new LocalHessianProxyFactory(connectTimeOut, readTimeOut, header);
				INotifyHessianForServer notifyApi = (INotifyHessianForServer) factory.create(
						INotifyHessianForServer.class, default_oa_url + "/notifyHessianForServer");
				unReadNotifyCount = notifyApi.getUnReadNotify(token, null);
			} catch (Exception e) {
				e.printStackTrace();
				unReadNotifyCount = 0;
			}
			try {
				HessianProxyFactory factory = new LocalHessianProxyFactory(connectTimeOut, readTimeOut, header);
				IArchiveHessianServlet archiveApi = (IArchiveHessianServlet) factory.create(
						IArchiveHessianServlet.class, default_oa_url + "/archiveHessianServlet");
				unReadArchiveCount = archiveApi.getMyWorkRemind(token, null);
			} catch (Exception e) {
				e.printStackTrace();
				unReadArchiveCount = "0";
			}
			
			sb.append("<li><img src=\"images/mail.gif\" /> <a href=\""+mailUrl+"\" class=\"a2\" target=_blank>未读邮件</a> <span>("+unReadMialCount+")</span></li>");
			sb.append("<li><img src=\"images/laba.gif\" /> <a href=\""+notifyUrl+"\" class=\"a2\" target=_blank>未读通知</a> <span>("+unReadNotifyCount+")</span></li>");
			sb.append("<li><img src=\"images/wjx.gif\" /> <a href=\""+archiveUrl+"\" class=\"a2\" target=_blank>待办公文</a> <span>("+unReadArchiveCount+")</span></li>");
		} catch (Exception e) {
			sb.append("<li>·加载数据错误</li>");
			e.printStackTrace();
		}
		sb.append("</ul>");
		try {
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
}
/*
<ul>
	<li><img src="images/mail.gif" /> <a href="" class="a2">未读邮件</a> <span>(5)</span></li>
    <li><img src="images/laba.gif" /> <a href="" class="a2">未读通知</a> <span>(5)</span></li>
    <li><img src="images/wjx.gif" /> <a href="" class="a2">待办公文</a> <span>(5)</span></li>
    <li><img src="images/sb.gif" /> <a href="" class="a2">督察信息</a> <span>(5)</span></li>
    <li></li>
</ul>
*/
