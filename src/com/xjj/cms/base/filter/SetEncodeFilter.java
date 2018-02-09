package com.xjj.cms.base.filter;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xjj.cms.base.VO.CmsCC;
import com.xjj.jdk17.utils.StringUtil;

/**
 * 过滤关键字
 * <p>
 * @author yeyunfeng 2015年3月27日 下午4:35:54 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年3月27日
 * @modify by reason:{方法名}:{原因}
 */
public class SetEncodeFilter implements Filter {

	private String defaultEncoding;

	private String redirectURL = null;
	
	private String filterUrl = null;

	/**
	 * 无参构造方法.
	 * 
	 */
	public SetEncodeFilter() {
	}

	/**
	 * 过滤器初始化方法.
	 * @param fc
	 *            读取配置文件参数.
	 */
	public void init(FilterConfig fc) throws ServletException {
		defaultEncoding = fc.getInitParameter("defaultEncoding");
		redirectURL = fc.getInitParameter("redirectURL");
		filterUrl = fc.getInitParameter("filterUrl");
	}

	/**
	 * servlet的doFilter()方法. 关键字过滤检查
	 *  1、与KeyWord_NoBlank字典进行不带空格的关键字比较
	 * 2、将参数按照空格分割，与KeyWord_Blank字典进行比较
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding(defaultEncoding);
		httpRequest.setCharacterEncoding(defaultEncoding);
		httpResponse.setHeader("X-Frame-Options", "SAMEORIGIN");

		boolean keyWordBlankhasKey = false;
		boolean keyWordNoBlankhasKey = false;
		boolean filter = false;
		
		int keyWordNoBlank_length = CmsCC.keyWordNoBlank.length;
		int keyWordBlank_length = CmsCC.keyWordBlank.length;
		
		String re_url = httpRequest.getRequestURI();
		if(!StringUtil.isEmpty(filterUrl) ){
			String[] filterUrls = filterUrl.split(",");
			for (String url : filterUrls) {
				if(re_url.indexOf(url) > -1){
					filter = true;
					break;
				}
			}
		}
		if(filter){
			Map<String, String[]> paramMap = httpRequest.getParameterMap();
			  for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
				  String key = entry.getKey();
				  for(int j = 0 ; j < keyWordNoBlank_length ; j ++ ){
					  if(key.indexOf(CmsCC.keyWordNoBlank[j]) > -1){
						  keyWordNoBlankhasKey = true;
						  break;
					  }
				  }
				  if(keyWordNoBlankhasKey == false){
					  String[] v = key.replaceAll("%20", " ").split("\\s+");
					  int v_length = v.length;
					  for(int i = 0; i < v_length; i++ ){
						  for(int j = 0 ; j < keyWordBlank_length ; j ++ ){
							  if(v[i].equals(CmsCC.keyWordBlank[j])){
								  keyWordBlankhasKey = true;
								  break;
							  }
						  }
					  }
				  }
				  if(keyWordNoBlankhasKey == false) {
					  String[] values = entry.getValue();
					  int values_length = values.length;
					  for (int k = 0; k < values_length; k++) {
						  String value = values[k].toLowerCase().trim();
						  if (!key.equals("method")) {
							  for (int j = 0; j < keyWordNoBlank_length; j++) {
								  if (value.indexOf(CmsCC.keyWordNoBlank[j]) > -1) {
									  keyWordNoBlankhasKey = true;
									  break;
								  }
							  }
							  if (keyWordNoBlankhasKey == false) {
								  String[] v = value.replaceAll("%20", " ").split("\\s+");
								  int v_length = v.length;
								  for (int i = 0; i < v_length; i++) {
									  for (int j = 0; j < keyWordBlank_length; j++) {
										  if (v[i].equals(CmsCC.keyWordBlank[j])) {
											  keyWordBlankhasKey = true;
											  break;
										  }
									  }
								  }
							  }
						  }
					  }
				  }
				  if(keyWordBlankhasKey || keyWordNoBlankhasKey){
						break;
					}
			  }
		}

		if (keyWordBlankhasKey || keyWordNoBlankhasKey) {
			String path = httpRequest.getContextPath();  
			//String basePath = httpRequest.getScheme()+"://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+path;
			httpResponse.sendRedirect(path+redirectURL);
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            byte[] sour = "存在特殊字符！".getBytes("utf-8");
            String strRtn = new String(sour , "gb2312");
            out.println("javascript:alert(\""+strRtn+"\");");
            out.println("javascript:history.back();");
            out.println("</script>");
//            out.println("{\"resultCode\":2,\"resultMsg\":\"存在特殊字符！\",\"resultData\":null}");
            out.flush();
            out.close();
		} else {
			chain.doFilter(request, response);
		}
		return;

	}

	/**
	 * 过滤器销毁方法.
	 */
	public void destroy() {

	}

}
