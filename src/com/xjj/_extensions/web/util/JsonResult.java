package com.xjj._extensions.web.util;

/**
* @ClassName: JsonResult
* @Description: json结构的返回结果集
* @author wanqing0806@gmail.com
* @date 2013-11-29 上午9:23:24
* 
*/
public class JsonResult {
	
	public JsonResult(){}
	
	public JsonResult(int resultCode, String resultMsg, Object resultData){
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.resultData = resultData;
	}
	
	private int resultCode;

	private String resultMsg;
	
	private Object resultData;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

	@Override
	public String toString() {
		return "JsonResult [resultCode=" + resultCode + ", resultMsg="
				+ resultMsg + ", resultData=" + resultData + "]";
	}
	
}
