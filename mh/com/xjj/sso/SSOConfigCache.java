package com.xjj.sso;

import java.util.Map;

public class SSOConfigCache {
	private static SSOConfigCache config = null;
	private static Map<String,String> configInfo = null;	//单点登陆 web.xml配置信息
	Map<String, Map<String, String>> hostSSOConfig =null;	//单点登陆主机配置,从sso服务器下在下来的配置 
	public static SSOConfigCache getCache(){
		if(config==null){
			config = new SSOConfigCache();
		}
		return config;
	}
	
	public void initCache(Map<String,String> configInfo){
		this.configInfo = configInfo;
	}
	
	public String getConfig(String configName){
		if(configInfo!=null){
			return configInfo.get(configName);
		}
		return null;
	}

	public void initHostSSOConfigCache(Map<String, Map<String, String>> hostSSOConfig) {
		this.hostSSOConfig =hostSSOConfig;
	}
	
	public Map<String, Map<String, String>> getHostSSOConfigCache(){
		if(hostSSOConfig!=null){
			return hostSSOConfig;
		}
		return null;
	}

}
