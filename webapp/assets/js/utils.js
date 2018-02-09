String.prototype.endWith=function(oStr){return new RegExp(oStr+"$").test(this);};
String.prototype.startWith=function(oStr){return new RegExp("^"+oStr).test(this);};
String.prototype.trim=function(){var v=this;
	if(v.length>0){var i=0;while(v.charAt(i).search(/\s/)==0) i++;v=v.substr(i);}
	if(v.length>0){var i=v.length-1;while(v.charAt(i).search(/\s/)==0) i--;v=v.substring(0,i+1);}
	return v;
};
String.prototype.isEmpty=function(){return (this.trim()==null||this.trim.length==0);};
function isInteger(n){var v=false;if(new RegExp("^-?[\\d]*$").test(n)) v=!isNaN(parseInt(n));return v;};
function isNumber(n){var v=false;if(new RegExp("^-?[\\d]*\\.?[\\d]*$").test(n)) v=!isNaN(parseFloat(n));return v;};
function validate_field(id, minL, maxL) {
	var f=true; var v=document.getElementById(id).value; v=v.trim();
	if(v.length>0) {
		if(typeof(minL)!="undefined") {
			f=(v.length>=minL);
			if(typeof(maxL)!="undefined" && f) f=(v.length<=maxL);
		}
	} else f=false;
	if(!f) document.getElementById(id).focus();
	return f;
};
function keyClick(id) {
	if(document.addEventListener) document.addEventListener("keypress", fireFoxHandler, true);
	else document.attachEvent("onkeypress", ieHandler);  
	function fireFoxHandler(evt){if(evt.keyCode==13) document.getElementById(id).click();} 
	function ieHandler(evt){if(evt.keyCode==13) document.getElementById(id).click();}
};


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
//			   $xs_xk_bk_tj_i.show();
//			   $li.addClass(cssName);
			   var pddm = $li.attr("pddm");
			   //异步加载
			   $.post(url,{"chlCode":pddm},function(html){
					$xs_xk_bk_tj_i.html(html);
					$xs_xk_bk_tj_i.show();
					for(var j=0;j<size4tab;j++){
						  if(j != i){
							  $("#sgy_org_div").hide();
				  			  $("#sgy_org_a").removeClass("hove");
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
