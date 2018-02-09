package com.xjj.oa.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xjj.oa.archive.hessian.IArchiveHessianServlet;
import com.xjj.sso.SSOConfigCache;
import com.xjj.sso.remote.LocalHessianProxyFactory;
import com.xjj.sso.util.Xml2Object;

@SuppressWarnings("serial")
public class ArchiveTag extends BodyTagSupport {

	private String host;
	private String uri;
	private int rows;
	private int wordCount;
	
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
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
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
			IArchiveHessianServlet api = (IArchiveHessianServlet) factory.create(IArchiveHessianServlet.class, default_oa_url + uri);
			String xmlInfo = api.getMyWorks(token, rows, "");
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for(Map<String, Object> _map : list) {
					String title = _map.get("title").toString();
					String titleStr = title;
					String date = _map.get("date").toString();
					String href = _map.get("showUrl").toString();
					if(wordCount>0){
						title = title.length()>wordCount?(title.substring(0,wordCount)+"..."):title;
					}
					try {
						date = sdf.format(sdf.parse(date));
					} catch (Exception e) {
						date = "";
					}
					sb.append("<li><span>"+date+"</span><a href=\""+ default_oa_url + href+"\" target=_blank title=\""+titleStr+"\">"+title+"</a></li>");
				}
			} else {
				sb.append("<li>暂无数据</li>");
			}
			
		} catch (Exception e) {
			sb.append("<li>加载数据错误</li>");
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
    <li><span>2014-08-04</span><a href="">海南二中院开门纳谏开放日、邀请单反发放媒体座谈等活动</a></li>
    <li><span>2014-08-04</span><a href="">海口海事法院开展反腐警示教育 斯蒂芬森大幅增强廉政风险意识</a></li>
    <li><span>2014-08-04</span><a href="">海南一男子“拔刀相助”为朋友斯蒂芬森的报仇砍伤他人获刑</a></li>
    <li><span>2014-08-04</span><a href="">海南一瘾君子为筹毒资多次盗伐花梨树似懂非懂干获刑一年</a></li>
    <li><span>2014-08-04</span><a href="">洋浦法院为高考缓送离婚诉讼 当事人感动主动撤诉</a></li>
</ul>
*/
