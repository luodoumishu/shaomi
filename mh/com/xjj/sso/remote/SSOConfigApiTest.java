package com.xjj.sso.remote;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;

public class SSOConfigApiTest {

	public static void main(String[] args) {
		int readTimeOut=1000;
		int connectTimeOut = 1000;
		HessianProxyFactory factory = new LocalHessianProxyFactory(connectTimeOut ,readTimeOut,null );
		
		try {
			ISSOConfigApi api = (ISSOConfigApi)factory.create(ISSOConfigApi.class,"http://192.168.0.117:93/remote/SSOConfig");
			Map<String, Map<String, String>> map = api.getSSOConfig();
			System.out.println(map);
			
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				System.out.println(key="=login_url="+map.get(key).get("login_url")+"    sso_cookie_url="+map.get(key).get("sso_cookie_url")+"   sso_ws_url="+map.get(key).get("sso_ws_url")+"    sso_ws_user="+map.get(key).get("sso_ws_user")+""+"    sso_ws_pw="+map.get(key).get("sso_ws_pw")+"");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
