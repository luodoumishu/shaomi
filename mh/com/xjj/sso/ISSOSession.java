package com.xjj.sso;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ISSOSession {
	
	public void setSeasion(HttpServletRequest httpRequest, Map<String, String> userInfo);

}
