/**
 * ajax
 * @author yeyunfeng 2014-11-20
 * 
 */
xjj.nameSpace("xjj.cms");

xjj.cms  = (function(){
	
	/**
	 * 发送ajax请求
	 * yeyunfeng 2014-12-05
	 */
	var ajax = function(url,data,async,onSucceed,onError){
		this.url = "";
		this.async = true,
		//if("null" == url || 0 == url.length){
		//	throw "缺少配置参数:url。"
	//	}else{
			this.url = url;
	//	}
		if(null == this.async){
			this.async = true;
		}
		$.ajax({
			url : this.url,
			data : data,
			async : this.async,
			type: "POST",
			success: function(data){
				if(onSucceed){
					onSucceed(data);
				}
			},
			error:function(data){
				if(onError){
					onError(data);
				}
			}
		})
	}
	
	/**
	 * 判断是否为空
	 * yeyunfeng 2014-12-05
	 */
	var notEmpty = function(obj){
		if(null == obj || "undefined" == obj || "" == obj){
			return false;
		}
		return true
	}
	
	/**
	 * 检查长度
	 * yeyunfeng 2014-12-05
	 */
	var checkLen = function(obj,maxChars) {  

		if (obj.value.length > maxChars)  
			obj.value = obj.value.substring(0,maxChars);  
	}
	
	/**
	 * 隐藏元素
	 */
	var hideObj = function(id){
		$("#"+id).hide();
	}

	/**
	 * json转字符串
	 * @param json
	 */
	var jsonToString = function(jsonObj){
		var jStr = "{ ";
		for(var item in jsonObj){
			jStr += "'"+item+"':'"+jsonObj[item]+"',";
		}
		jStr += " }";
		return jStr;
	}
	return{
		ajax : ajax,
		notEmpty : notEmpty,
		checkLen:checkLen,
		hideObj:hideObj,
		jsonToString:jsonToString
	}
	
})()
