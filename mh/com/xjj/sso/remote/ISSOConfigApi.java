package com.xjj.sso.remote;

import java.util.Map;

public interface ISSOConfigApi {
	
	/**
	 * key保存服务器ip value保存对应的sso配置信息
	 * @return
	 */
	public Map<String,Map<String,String>> getSSOConfig();

}
