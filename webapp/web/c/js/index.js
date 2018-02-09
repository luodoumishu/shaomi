function initChildOrgs(orgCode){
	var url = "/cms/index/getFirstLevelByorgCode.json";
	$.ajax({
	    url:url,    //请求的url地址
	    dataType:"json",   //返回格式为json
	    async:false,//请求是否异步，默认为异步，这也是ajax重要特性
	    data:{"orgCode":orgCode},    //参数值
	    type:"POST",   //请求方式
	    beforeSend:function(){
	        //请求前的处理
	    },
	    success:function(result){
	        //请求成功时处理
	    	if(null != result && null != result.resultData){
	    		if(result.resultCode == 0){
	    			var datas = result.resultData;
	    			var html = "";
	    			var index = 0;
	    			for(var i = 0; i<datas.length;i++){
	    				var data = datas[i];
	    				var pyCode = data.pyCode;
	    				var orgName = eval('(\''+data.name+'\')');  
	    				orgName = encodeURI(orgName);   
	    				orgName = encodeURI(orgName);
	    				index ++;
	    				if(pyCode.substring(pyCode.length-2,pyCode.length) == "sq"){
//	    					if(index/9 == 1){
	    						html +="<a target='_blank' href='"+path+"web/sq/sq_index.jsp?orgName="+orgName+"&orgCode="+data.pyCode+"'>"+data.name+"</a>";
//	    						index = 0;
//	    					}else{
//	    						html += "<a target='_blank' href='"+path+"/web/sq/sq_index.jsp?orgName="+orgName+"&orgCode="+data.pyCode+"'>"+data.name+"</a> |";
//	    					}
	    				}else{
//	    					if(index/9 == 1){
//	    						html += "<a target='_blank' href='"+path+"/web/c/c_index.jsp?orgName="+orgName+"&orgCode="+data.pyCode+"'>"+data.name+"</a> |<br />";
//	    						index = 0;
//	    					}else{
    						html += "<a target='_blank' href='"+path+"web/c/c_index.jsp?orgName="+orgName+"&orgCode="+data.pyCode+"'>"+data.name+"</a>";
//	    					}
	    				}
	    			}
	    			html+="<div style='clear:both;'></div>";
	    			$("#childlist").html(html);
	    		}else{
	    			alert(result.resultMsg);
	    		}
	    	}
	    },
	    complete:function(){
	        //请求完成的处理
	    },
	    error:function(){
	        //请求出错处理
	    	alert("查询乡镇村链接失败！");
	    }
	});
}