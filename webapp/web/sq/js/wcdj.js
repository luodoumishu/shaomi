function initXzcLink(){
	var url = "/cms/index/getFirstLevelByOrgName.json";
	$.ajax({
	    url:url,    //请求的url地址
	    dataType:"json",   //返回格式为json
	    async:true,//请求是否异步，默认为异步，这也是ajax重要特性
	    data:{"orgName":"乡镇村"},    //参数值
	    type:"POST",   //请求方式
	    beforeSend:function(){
	        //请求前的处理
	    },
	    success:function(result){
	        //请求成功时处理
	    	if(null != result && null != result.resultData){
	    		if(result.resultCode == 0){
	    			var datas = result.resultData;
	    			var html = "<option>==村镇、机关党建网链接==</option>";
	    			for(var i = 0; i<datas.length;i++){
	    				var data = datas[i];
	    				org_name = data.name;
	    				var zxValue = org_name.substring(org_name.length-1,org_name.length);
	    				org_name = encodeURIComponent(data.name);
	    				if(zxValue == "镇" || zxValue =="乡"){
	    					html += "<option value='"+path+"/web/z/z_index.jsp?orgName="+org_name+"&orgCode="+data.pyCode+"'>"+data.name+"党建网</option>";
	    				}else{
	    					html += "<option value='"+path+"/web/jg/jg_index.jsp?orgName="+org_name+"&orgCode="+data.pyCode+"'>"+data.name+"党建网</option>";
	    				}
	    			}
	    			$("#xzc_link").html(html);
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

function initXzLink(){
	var url = "/cms/index/getFirstLevelByOrgName.json";
	$.ajax({
	    url:url,    //请求的url地址
	    dataType:"json",   //返回格式为json
	    async:true,//请求是否异步，默认为异步，这也是ajax重要特性
	    data:{"orgName":"乡镇村"},    //参数值
	    type:"POST",   //请求方式
	    beforeSend:function(){
	        //请求前的处理
	    },
	    success:function(result){
	        //请求成功时处理
	    	if(null != result && null != result.resultData){
	    		if(result.resultCode == 0){
	    			var datas = result.resultData;
	    			var html = "<div style='float:left;width:49%'><ul>";
	    			var html2 = "<div style='float:left;width:49%'><ul>";
	    			var temp = 0;  //用来计数
	    			for(var i = 0; i<datas.length;i++){
	    				var data = datas[i];
	    				org_name = data.name;
	    				var zxValue = org_name.substring(org_name.length-1,org_name.length);
	    				org_name = encodeURIComponent(data.name);
	    				if(zxValue == "镇" || zxValue =="乡"){
	    					if(temp<5){
	    						html +="<li><a href='"+path+"/web/z/z_index.jsp?orgName="+org_name+"&orgCode="+data.pyCode+"' target='_blank' >"+data.name+"党建网</a></li>";
	    					}else{
	    						html2 +="<li><a href='"+path+"/web/z/z_index.jsp?orgName="+org_name+"&orgCode="+data.pyCode+"' target='_blank' >"+data.name+"党建网</a></li>";
	    					}
	    					temp++;
	    				}
	    			}
	    			html +="</ul></div>";
	    			html2 +="</ul></div>";
	    			$("#qh_xzdjlj").html(html+html2);
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

function initJgLink(){
	var url = "/cms/index/getFirstLevelByOrgName.json";
	$.ajax({
	    url:url,    //请求的url地址
	    dataType:"json",   //返回格式为json
	    async:true,//请求是否异步，默认为异步，这也是ajax重要特性
	    data:{"orgName":"乡镇村"},    //参数值
	    type:"POST",   //请求方式
	    beforeSend:function(){
	        //请求前的处理
	    },
	    success:function(result){
	        //请求成功时处理
	    	if(null != result && null != result.resultData){
	    		if(result.resultCode == 0){
	    			var datas = result.resultData;
	    			var html = "<ul>";
	    			for(var i = 0; i<datas.length;i++){
	    				var data = datas[i];
	    				org_name = data.name;
	    				var zxValue = org_name.substring(org_name.length-1,org_name.length);
	    				org_name = encodeURIComponent(data.name);
	    				if(zxValue != "镇" && zxValue !="乡"){
	    					html +="<li><a href='"+path+"/web/jg/jg_index.jsp?orgName="+org_name+"&orgCode="+data.pyCode+"' target='_blank'>"+data.name+"党建网</a></li>";
	    				}
	    			}
	    			html += "</ul>"
	    			$("#qh_jgdjlj").html(html);
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