package com.xjj.ws.sso;

import java.net.MalformedURLException;

import org.junit.Test;

public class TestSSOVerify {
	
	@Test
	public void test() {
		try {
			SSOVerifyService s = new SSOVerifyService("http://192.168.0.117:93/sso?wsdl");
			SSOVerify service = s.getSSOVerifyPort();
			System.out.println(service.userLogin("fff30512b76debb9844d174b02d865ba9e75b7804305a19326c809b2df7f1eb8ab9471d4318b09a403cdaeed434018db84f5782a9c85a34f1b548639a9a68b56"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
