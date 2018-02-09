package com.xjj.oa.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xjj.oa.pubinfo.hessian.IGovaffairHessianForServer;
import com.xjj.sso.SSOConfigCache;
import com.xjj.sso.remote.LocalHessianProxyFactory;
import com.xjj.sso.util.Xml2Object;

@SuppressWarnings("serial")
public class GovaffairTag extends BodyTagSupport {

	private String host;
	private String uri;
	private int wordCount;
	private int rows;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
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
		sb.append("<ul>");
		try{
			HessianProxyFactory factory = new LocalHessianProxyFactory(connectTimeOut,readTimeOut,header);
			IGovaffairHessianForServer api = (IGovaffairHessianForServer) factory.create(IGovaffairHessianForServer.class, default_oa_url + uri);
			String xmlInfo = api.getGovaffairs(token, rows, "");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Map<String, String>> objects = Xml2Object.xml2Object(xmlInfo,"info");
			for(Map<String, String>obj: objects){
				Map<String, Object> m = new HashMap<String, Object>();
				Iterator<String> it = obj.keySet().iterator();
				while(it.hasNext()){
					String key = it.next();
					String value = obj.get(key);
					m.put(key, value);
				}
				list.add(m);
			}
			objects = null;
			
			if(list.size()>0) {
				for(Map<String, Object> _map : list) {
					String title = _map.get("title").toString();
					String titleStr = title;
					if(wordCount>0){
						title = title.length()>wordCount?(title.substring(0,wordCount)+"..."):title;
					}
					String href = _map.get("showUrl").toString();
					sb.append("<li><a href=\""+default_oa_url + href+"\" target=_blank title=\""+titleStr+"\">"
							+ "·"+title+"</a></li>");
				}
			} else {
				sb.append("<li>·暂无数据</li>");
			}
			
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
    <li><a href="">·原告海南航空休闲汽车租赁有限公司…</a></li>
    <li><a href="">·公诉被告人曹非朋故意伤害一案… </a></li>
    <li><a href="">·原告李丽诉被告吴炳宏离婚一案</a></li>
    <li><a href="">·原告王海生告海南锴润拆迁工程有… </a></li>
    <li><a href="">·原告郑振增诉被告海南汇都产开发…</a></li>
    <li><a href="">·原告曾伟诉被告海南省科技活动中心…</a></li>
</ul>
*/
