package com.xjj.oa.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xjj.oa.mycalendar.hessian.IMyCalendarHessianForServer;
import com.xjj.sso.SSOConfigCache;
import com.xjj.sso.remote.LocalHessianProxyFactory;
import com.xjj.sso.util.Xml2Object;

@SuppressWarnings("serial")
public class ScheduleTag extends BodyTagSupport {

	private String host;
	private String uri;
	private int startY;
	private int startM;
	private int change;
	
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
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public int getStartM() {
		return startM;
	}
	public void setStartM(int startM) {
		this.startM = startM;
	}
	public int getChange() {
		return change;
	}
	public void setChange(int change) {
		this.change = change;
	}
	
	
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		
		String token = pageContext.getSession().getAttribute("sso_token") != null ? ((String) pageContext.getSession().getAttribute("sso_token")) : "";
		if (host.toLowerCase().startsWith("http://")) {
			host = host.substring("http://".length(), host.length());
		}
		
		Calendar cal=Calendar.getInstance();
		cal.set(startY, startM, 1);
		int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(day == 0){
			day = 7;
		}
		Calendar startD = Calendar.getInstance();
		startD.set(startY, startM, 1-day);
		Calendar endD = Calendar.getInstance();
		endD.set(startY, startM, 1-day+41);
		//System.out.println(startD.getTime()+"~~|||||~~~"+endD.getTime());
		
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
		try{
			HessianProxyFactory factory = new LocalHessianProxyFactory(connectTimeOut,readTimeOut,header);
			IMyCalendarHessianForServer api = (IMyCalendarHessianForServer) factory.create(IMyCalendarHessianForServer.class, default_oa_url + uri);
			String xmlInfo = api.getSchedule(token, startD.getTime(), endD.getTime(), "");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Map<String, String>> objects = Xml2Object.xml2Object(xmlInfo,"schedule_myclendar");
			for(Map<String, String>obj: objects){
				Map<String, Object> m1 = new HashMap<String, Object>();
				Iterator<String> it = obj.keySet().iterator();
				while(it.hasNext()){
					String key = it.next();
					String value = !obj.get(key).equals("null")?obj.get(key):"无";
					m1.put(key, value);
				}
				list.add(m1);
			}
			objects = null;
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String schedule_date = map.get("schedule_date").toString();
				String content = map.get("content").toString();
				String end_date = map.get("end_date").toString();
				String begin_date = map.get("begin_date").toString();
				String mark = map.get("mark").toString();
				try {
					end_date = sdf2.format(sdf1.parse(end_date));
					begin_date = sdf2.format(sdf1.parse(begin_date));
				} catch (Exception e) {
					schedule_date = "";
				}
				String contenthtml = "<tr class='tr" + schedule_date
						+ "' style='display: none;' onmouseover='this.style.backgroundColor=\"#FFFF00\"' onmouseout='this.style.backgroundColor=\"#FFFF99\"'>" + "<td>"
						+ begin_date + "-" + end_date + "</td>" + "<td>" + content + "</td>" + "</tr>" + "<tr class='tr" + schedule_date + "'>" + "<td colspan='2'>"
						+ "<table style='border:1px;border-style: outset;' width='100%'>" + "<tr>" + "<td>备注:</td><td>" + mark + "</td>" + "</tr></table></td></tr>";
				sb.append(contenthtml);
			}
			
		} catch (Exception e) {
			sb.append("<script type='text/javascript'>$('.jt_grxcon').html('读取内容错误');</script> ");
			e.printStackTrace();
		}
		try {
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
}
