package com.xjj.sso.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.xml.sax.SAXException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xjj.sso.ISSOSession;
import com.xjj.sso.SSOConfigCache;
import com.xjj.sso.remote.ISSOConfigApi;
import com.xjj.sso.remote.LocalHessianProxyFactory;
import com.xjj.sso.util.UrlUtil;
import com.xjj.sso.util.Xml2Object;
import com.xjj.ws.sso.SSOVerify;
import com.xjj.ws.sso.SSOVerifyService;

public class SSOFilter implements Filter{
	
	private String sessionInteface = null;			//  SSO登录后回调方法,主要设置 session
	private String localLogoutUrl = null;			//	退出单点登录地址
	private String exceptionFileUrls = null;		//	例外url
	private String exceptionFloderUrls = null;		//	例外路径 
	private String sso_config_download_url = null;

	public void init(FilterConfig config) throws ServletException {
		sessionInteface  = config.getInitParameter("com.xjj.sso.local.sessionInteface");
		localLogoutUrl = config.getInitParameter("com.xjj.sso.localLogoutUrl");
		exceptionFileUrls = config.getInitParameter("com.xjj.sso.exception.files");
		exceptionFloderUrls = config.getInitParameter("com.xjj.sso.exception.floders");
		sso_config_download_url = config.getInitParameter("com.xjj.sso.config.download.url"); 
		
		Map<String,String> configCache = new HashMap<String,String>();
		try{
			configCache.put("com.xjj.sso.config.download.url", sso_config_download_url);
			configCache.put("com.xjj.sso.localLogoutUrl", localLogoutUrl);
			configCache.put("com.xjj.sso.exception.files", exceptionFileUrls);
			configCache.put("com.xjj.sso.exception.floders", exceptionFloderUrls);
			SSOConfigCache cache = SSOConfigCache.getCache();
			cache.initCache(configCache);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//从SSO服务器获得单点登录配置信息
		try {
			downloadSsoConfigFromSsoServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-----------------开始初始化SSO过滤器参数...--------------------------------");
		Iterator<String> it = configCache.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			System.out.println(key+"="+configCache.get(key));
		}
		System.out.println(SSOConfigCache.getCache().getHostSSOConfigCache());
		System.out.println("----------------初始化SSO过滤器参数完成！----------------------------------");
	}
	
	public void destroy() {
	}

	private static final String TOKEN_NAME = "sso_token";
	private static final String LAST_CHECK_TOKEN_TIME = "LAST_CHECK_TOKEN_TIME";
	

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest)request);
		HttpServletResponse httpResponse = ((HttpServletResponse)response);
		
		//判断例外
		boolean isException = isExceptionFileOrFloder(httpRequest);
		if(isException){
			chain.doFilter(request, response);
			return;
		}
		
		SSOConfigCache cache = SSOConfigCache.getCache();
		Map<String, Map<String, String>> hostSSOConfig = cache.getHostSSOConfigCache();
		
		Map<String, String> ssoConfig = null;
		String host = getRequestHost(httpRequest);
		try {
			if (hostSSOConfig.containsKey(httpRequest.getHeader("Host"))) {
				ssoConfig = hostSSOConfig.get(httpRequest.getHeader("Host"));
			} else if (hostSSOConfig.containsKey(host)) {
				ssoConfig = hostSSOConfig.get(host);
			} else {
				ssoConfig = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ssoConfig == null){
			response.getOutputStream().write(("没有找到 '"+httpRequest.getHeader("Host")+"'或'"+host+"'对应的SSO配置!请确认SSO服务器增加的该应用的配置...").getBytes());
			return;
		}
		
		HttpSession session = httpRequest.getSession();
		session.setAttribute("default_oa_url", ssoConfig.get("default_oa_url"));
		session.setAttribute("sso_login_url", ssoConfig.get("sso_cookie_url").replace("checkByCookie.action", "ssologin.action"));
		
		//从请求中获取sso_token的值
		String sso_token = getSsoTokenFromRequest(httpRequest);
		
		
		//判断是否存在errrorCode参数,如果存在则直接跳转到登录界面
		try {
			String errorCode = getParameterFromQueryString(httpRequest.getQueryString(), "ssoErrorCode");
			if (errorCode != null) {
				String host_ = null;
				if (httpRequest.getHeader("Host") != null) {
					try {
						host_ = httpRequest.getHeader("Host");
						if ((!host_.toLowerCase().startsWith("http://"))
								&& (!host_.toLowerCase().startsWith("https://"))) {
							host_ = "http://" + host_;
						}
						String uri = httpRequest.getRequestURI();
						host_ += (uri.startsWith("/") ? "" : "/") + uri;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (!host_.toLowerCase().startsWith(ssoConfig.get("login_url").toLowerCase())) {// 此判断避免死循环
					String lastUrl = getParameterFromQueryString(httpRequest.getQueryString(), "lastUrl");
					if (lastUrl != null && lastUrl.length() > 0) {
						String redirectUrl = ssoConfig.get("login_url") + "?" + httpRequest.getQueryString();
						httpResponse.sendRedirect(redirectUrl);
					} else {
						String redirectUrl = ssoConfig.get("login_url") + "?lastUrl=" + host_;
						httpResponse.sendRedirect(redirectUrl);
					}
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*		
		HttpSession session = httpRequest.getSession(false);
		if(sso_token != null && sso_token.length() > 0 && session != null && session.getAttribute("userId") != null) {
			checkSSOLogout(httpRequest, sso_token, this.localLogoutUrl, sso_ws_url);
			chain.doFilter(request, response);
			return;
		}
		*/
		if(sso_token != null && sso_token.length() > 0){
			boolean isLogout = checkSSOLogout(httpRequest, sso_token, this.localLogoutUrl, ssoConfig.get("sso_ws_url"));
			if(isLogout){
				chain.doFilter(request, response);
				return;
			}
			try{
				loginByToken(sso_token,httpRequest,httpRequest.getSession(), ssoConfig.get("sso_ws_url"));
				httpRequest.getSession().setAttribute(LAST_CHECK_TOKEN_TIME, System.currentTimeMillis());
				chain.doFilter(request, response);
				return;
			}catch(Exception e){
				e.printStackTrace();
				httpRequest.getSession().invalidate();
				sso_token = null;
			}
		}
		
		if(sso_token == null){
			String checkCookieUrl = ssoConfig.get("sso_cookie_url");
			String redirectUrl = checkCookieUrl+"?lastUrl="+UrlUtil.encodeLastRequestUrl(getTargetUrl(httpRequest));
			httpResponse.sendRedirect(redirectUrl);
			return;
		}
		
	}
	


	/**
	 * 从请求中获得sso_token的值
	 * @param httpRequest
	 * @return
	 */
	private String getSsoTokenFromRequest(HttpServletRequest httpRequest) {
		String sso_token = getParameterFromQueryString(httpRequest.getQueryString(),TOKEN_NAME);
		HttpSession localSession = httpRequest.getSession();
		String sessionToken = localSession.getAttribute(TOKEN_NAME)!=null? localSession.getAttribute(TOKEN_NAME).toString():null;
		//判断参数token和会话token是否一致 
		if(sessionToken!=null && sessionToken.length()>0 && sso_token!=null && sso_token.length()>0) {
			if(sso_token.equals(sessionToken)) {
				return sessionToken;
			} else {
				localSession.invalidate();
				return null;
			}
		}else if(sessionToken!=null && sessionToken.length()>0){
			return sessionToken;
		}
		return sso_token;
	}

	/**
	 * 从请求中获取参数值 无值返回null
	 * @param queryString
	 * @param pname
	 * @return
	 */
	private String getParameterFromQueryString(String queryString,String pname) {
		try{
			if(queryString!=null){
				String [] t = queryString.split(pname+"=");
				if(t.length>1){
					t = t[1].split("&");
					return t[0];
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断例外
	 * @param httpRequest
	 * @return
	 */
	private boolean isExceptionFileOrFloder(HttpServletRequest httpRequest) {
		boolean flag = false;
		String _str = httpRequest.getServletPath();
		if(exceptionFileUrls != null && exceptionFileUrls.length() > 0){
			String [] files = exceptionFileUrls.split(",");
			for(String furl: files){
				if(_str.equals(furl)) {
					flag = true;
					break;
				}
			}
		}
		if(!flag) {
			if(exceptionFloderUrls!=null && exceptionFloderUrls.length()>0){
				String[] floders = exceptionFloderUrls.split(",");
				for(String furl: floders){
					if(_str.startsWith(furl)){
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	private String getTargetUrl(HttpServletRequest httpRequest) {
		String targetUrl = "";
		if(httpRequest.getHeader("Host")!=null){
			try{
				String host_ = httpRequest.getHeader("Host");
				if((!host_.toLowerCase().startsWith("http://")) && (!host_.toLowerCase().startsWith("https://"))){
					targetUrl = "http://"+host_;
				}else{
					targetUrl = host_;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String uri = httpRequest.getRequestURI();
		String qs = httpRequest.getQueryString();//"?"
		targetUrl+=(uri.startsWith("/")? "":"/")+uri+(qs!=null? ("?"+UrlUtil.encodeLastRequestUrl(qs)):"");
		return targetUrl;
	}


	/**
	 * SSO登出 
	 * @param httpRequest
	 * @param token
	 * @param localLogoutUrl
	 * @param sso_ws_url
	 * @param sso_ws_user
	 * @param sso_ws_pw
	 * @return
	 */
	private boolean checkSSOLogout(HttpServletRequest httpRequest, String token, String localLogoutUrl,String sso_ws_url) {
		//判断是否退出单点登陆
		if(localLogoutUrl!=null && localLogoutUrl.length()>0){
			if(httpRequest.getServletPath().toLowerCase().contains(localLogoutUrl.toLowerCase())){
				System.out.println("--------------------SSO配置的本地系统退出路径："+localLogoutUrl+" 当前的符合SSO退出系统的访问路径："+httpRequest.getServletPath());
			}
			if(httpRequest.getServletPath().startsWith(localLogoutUrl)){//
				System.out.println("---------------------------退出系统!同时退出SSO单点登录!");
				ssoLogout(token,sso_ws_url);
				return true;
			}
		}
		return false;
	}

	/**
	 * 使用token登陆本地系统
	 * @param token
	 * @param httpRequest
	 * @param loaclSeasion
	 * @param ssoWsUrl
	 * @param ssoWsName
	 * @param ssoWsPassword
	 * @throws Exception
	 * @throws SAXException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void loginByToken(String token,HttpServletRequest httpRequest, HttpSession loaclSeasion ,String ssoWsUrl) throws Exception, SAXException, IOException {
		Map<String,String> userInfo = getUserInfoBySSOWebService(token,ssoWsUrl);
		if(userInfo!=null){
			Class<ISSOSession> c = (Class<ISSOSession>) Class.forName(sessionInteface);
			ISSOSession ci = c.newInstance();
			ci.setSeasion(httpRequest,userInfo);
			loaclSeasion.setAttribute(TOKEN_NAME,token);//设置本地会话
			loaclSeasion.setAttribute(LAST_CHECK_TOKEN_TIME,new Long(System.currentTimeMillis()));//设置最后一次检测token的时间
		}else{
			throw new Exception("验证失败!");
		}
	}


	/**
	 * 验证登陆
	 * @param token
	 * @param ssoWsUrl
	 * @param ssoWsName
	 * @param ssoWsPassword
	 * @return
	 */
	private Map<String, String> getUserInfoBySSOWebService(String token,String ssoWsUrl){
	 		try {
	 			SSOVerifyService s = new SSOVerifyService(ssoWsUrl);
				SSOVerify service = s.getSSOVerifyPort();
	 		 	String xml = service.userLogin(token);
	 		 	if(xml!=null){
	 		 		return Xml2Object.xml2Object(xml).get(0);
	 		 	}
			} catch (Exception e) {
				System.out.println("通过token验证异常! sso_token="+token);
				e.printStackTrace();
				return null;
			}
	 	
		return null;
	}
	
	/**
	 * sso登出系统
	 * @param token
	 * @param ssoWsUrl
	 * @param ssoWsName
	 * @param ssoWsPassword
	 */
	private void ssoLogout(String token,String ssoWsUrl) {
		try {
			SSOVerifyService s = new SSOVerifyService(ssoWsUrl);
			SSOVerify service = s.getSSOVerifyPort();
		 	 Boolean isLogout = service.userLogout(token);
			if(isLogout){
				System.out.println("退出SSO单点登录成功! sso_token="+token);
		 	}else{
		 		System.out.println("退出SSO单点登录失败! sso_token="+token);
		 	}
		} catch (Exception e) {
			System.out.println("退出SSO单点登录异常! sso_token="+token);
			e.printStackTrace();
		}
	}
	
	/**
	 * 从SSO服务器获得单点登录配置信息
	 * @throws Exception 
	 */
	private String downloadSsoConfigFromSsoServer() throws Exception {
		StringBuffer ssoConfigInfo = new StringBuffer();
		String url = sso_config_download_url;
		ssoConfigInfo.append("配置下在地址:"+url+"<hr>");
		try{
			int readTimeOut=10000;
			int connectTimeOut = 10000;
			HessianProxyFactory factory = new LocalHessianProxyFactory(connectTimeOut ,readTimeOut,null );
			ISSOConfigApi api = (ISSOConfigApi)factory.create(ISSOConfigApi.class,url);
			Map<String, Map<String, String>> hostSSOConfig = null;
			hostSSOConfig = api.getSSOConfig();
			SSOConfigCache cache = SSOConfigCache.getCache();
			cache.initHostSSOConfigCache(hostSSOConfig);
			System.out.println("----------------从SSO服务器下在配置完成！----------------------------------"+hostSSOConfig.size());
			ssoConfigInfo.append("下载的配置信息:"+hostSSOConfig+"<hr>");
		}catch(Exception e){
			String msg = ("----------------从SSO服务器下在配置失败！["+url+"]（errror:"+e.getMessage()+"）！----------------------------------");
			System.out.println(msg);
			ssoConfigInfo.append("下载配置失败:"+msg+"<hr>");
			throw e;
		}
		return ssoConfigInfo.toString();
	}
	
	/**
	 * 获得发起请求的服务器头 如：192.168.0.100:8081 
	 * 返回的数据只有主机IP 没有端口
	 * @param httpRequest
	 * @return
	 */
	private String getRequestHost(HttpServletRequest httpRequest) {
		String host = null;
		if(httpRequest.getHeader("Host") != null){
			try{
				host = UrlUtil.filterHost(httpRequest.getHeader("Host"));
			}catch(Exception e){
				System.out.println("------请求头中没有找到Host默认启用系统配置的host信息--------------host=" + host);
				e.printStackTrace();
			}
		}
		return host;
	}
}
