/*
**	Anderson Ferminiano
**	contato@andersonferminiano.com -- feel free to contact me for bugs or new implementations.
**	jQuery ScrollPagination
**	28th/March/2011
**	http://andersonferminiano.com/jqueryscrollpagination/
**	You may use this script for free, but keep my credits.
**	Thank you.
*/

(function( $ ){
	 
		 
 $.fn.scrollPagination = function(options) { 

		var opts = $.extend($.fn.scrollPagination.defaults, options);  
		var target = opts.scrollTarget;
		if (target == null){
			target = obj; 
	 	}
		opts.scrollTarget = target;
	 
		return this.each(function() {
		  $.fn.scrollPagination.init($(this), opts);
		});

  };
  
  $.fn.stopScrollPagination = function(){
	  return this.each(function() {
	 	$(this).attr('scrollPagination', 'disabled');
	  });
	  
  };
  
  
  $.fn.reset = function(){ 
	  return this.each(function() {
	 	$(this).attr('scrollPagination', 'enabled');
	  });
	  
  };
  
  $.fn.scrollPagination.loadContent = function(obj, opts){
	 var target = opts.scrollTarget;/*- $(target).height()*/
	
	 var mayLoadContent = $(target).scrollTop()+opts.heightOffset >= $(document).height()-$(target).height();
  
	// var mayLoadContent =  $(target).scrollTop()+opts.heightOffset>=$(document).height()-$(window).height();
	/* if(mayLoadContent)
	{  alert($(target).scrollTop());
	 alert(opts.heightOffset);
	 alert($(document).height());
	 alert($(target).height());}*/
	 
	 if (mayLoadContent && $(obj).attr('scrollPagination') == 'enabled'){
		 /*alert($(target).scrollTop());
		 alert(opts.heightOffset);
		 alert($(document).height());
		 alert($(target).height());*/
		 if (opts.beforeLoad != null){
			opts.beforeLoad(); 
		 }
		 $(obj).children().attr('rel', 'loaded');
		 $.ajax({
			 
			  type: 'POST',
			  url: opts.contentPage+opts.pageNum,
			  data: opts.contentData,
			  success: function(result){
				  if(result!=null && result!=""){
				  		$("#"+divId).html("");
			        	$("#"+fyId).html("");
						if(result !=null){
							var item = result.resultData.items;
							var html = "";
							$("#"+divId).append(html);
							for(var i=0 ; i < item.length ; i++){
								if(i%2==0){
									html += '<tr><td width="80" align="center"><table class="rl"  border="0" cellspacing="0" cellpadding="0">';
								}else{
									html += '<tr><td width="80" bgcolor="f5f8fa" align="center"><table class="rl"  border="0" cellspacing="0" cellpadding="0">';
								}
								html += '<tr>'
			                         +'<td height="25">7月</td>'
			                         +'</tr>'
			                         + '<tr>'
			                         +'<td class="rlday">7日</td>'
			                         +'</tr>'
			                         +'</table></td>';
			                    if(i%2==0){
			     					html += '<td>';
			     				}else{
			     					html += '<td bgcolor="f5f8fa">';
			     				}     
			                    html += '<div class="titlecss">'+item[i].comment_name+'<span class="cz">'
			     				html +='</span></div>';
			     				var wlc = item[i].comment_content;
			     				if(wlc.length>300){
			     					wlc = wlc.substring(0,300)+"..."+"<img id='' class='up_and_down' src='/pages/workLog/images/dx01.jpg'/>";
			     				}
			     				html +='<div class="comlog">'+wlc+'</div>';
			                    html += '<div style="display:none;" id="com'+item[i].id+'"><div id="comment'+item[i].id+'">';
			                    html +='</div><br/>'
			                    +'</td>'	
			                    +'</tr>';
							}
							$("#"+divId).append(html);	
							$("#showMes").html('');
						}
				  	}else{
				  	}
			  	 
			  },
			  dataType: 'html'
		 });
		 opts.pageNum = opts.pageNum+1;
	 }
	 
  };
  
  $.fn.scrollPagination.init = function(obj, opts){ 
	 var target = opts.scrollTarget;
	 $(obj).attr('scrollPagination', 'enabled');
	 $(target).scroll(function(event){
		if ($(obj).attr('scrollPagination') == 'enabled'){
	 		$.fn.scrollPagination.loadContent(obj, opts);		
		}
		else {
			event.stopPropagation();
		}
	 });
	 
	 //$.fn.scrollPagination.loadContent(obj, opts);
	 
 };
	
 $.fn.scrollPagination.defaults = {
      	 'contentPage' : null,
     	 'contentData' : {},
		 'beforeLoad': null,
		 'afterLoad': null	,
		 'scrollTarget': null,
		 'heightOffset': 0,
		 'pageNum':2  
 };	
})( jQuery );