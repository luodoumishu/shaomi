package com.xjj.sso.remote;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;

@SuppressWarnings("rawtypes")
public class LocalHessianProxyFactory extends HessianProxyFactory {
	
	private int connectTimeOut = 3000;
	private int readTimeOut = 3000;
	
	public int getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	public int getReadTimeOut() {
		return readTimeOut;
	}
	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	private Map<String, String> header;

	public LocalHessianProxyFactory(int connectTimeOut, int readTimeOut,
			Map<String, String> header) {
		this.connectTimeOut = connectTimeOut;
		this.readTimeOut = readTimeOut;
		this.header = header;
	}

	@Override
	public Object create(Class api, String url) throws MalformedURLException {
		this.setChunkedPost(false);
		return super.create(api, url);
	}

	protected URLConnection openConnection(URL url) throws IOException {
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		if (this.connectTimeOut > 0) {
			conn.setConnectTimeout(this.connectTimeOut);
		}
		if (this.readTimeOut > 0) {
			conn.setReadTimeout(this.readTimeOut);
		}
		conn.setRequestProperty("Content-Type", "x-application/hessian");

		if (header != null) {
			conn.setRequestProperty("SSO_WS_HOST", header.get("SSO_WS_HOST"));
			conn.setRequestProperty("SSO_WS_USER", header.get("SSO_WS_USER"));
			conn.setRequestProperty("SSO_WS_PW", header.get("SSO_WS_PW"));
		}
		return conn;
	}
}
