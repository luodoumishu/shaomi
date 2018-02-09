//频道tab切换
function tab_qiehuan(divId,cssName,jspUrl){
	var url = jspUrl;
	var tab_lis = $('[name='+divId+']');
	var size4tab = tab_lis.length;
	tab_lis.each(function(i,el){
		var $li = $(el);
		
		//鼠标进入的事件
		$li.mouseenter(function(e){
			var $xs_xk_bk_tj_i = $("#"+divId+"_"+i);
			   $xs_xk_bk_tj_i.show();
			   $li.addClass(cssName);
			   var pddm = $li.attr("pddm");
			   //异步加载
			   $.post(url,{"chlCode":pddm},function(html){
					$xs_xk_bk_tj_i.html(html);
					$xs_xk_bk_tj_i.show();
					for(var j=0;j<size4tab;j++){
						  if(j!=i){
							  $("#"+divId+"_"+j).hide();
							  $("#"+divId+"_"+j).children().remove(); 
							  var $other  = $(tab_lis[j]);
							  $other.removeClass(cssName);
						   }else{
							 $li.addClass(cssName);
						   }
					}  
				});
		});
	
	});
	if(size4tab>0){
		$(tab_lis[0]).mouseenter();//触发第一个
	}
}