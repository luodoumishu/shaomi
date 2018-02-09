/*
 * myFocus焦点图基本库代码开始
 */
var myFocus={
	//myFocus JavaScript Library v1.0.0 beta
	//Design By Koen @ 2010.9.16
	//http://hi.baidu.com/koen_li
	$:function(id){return document.getElementById(id);},
	$$:function(tag,obj){return (typeof obj=='object'?obj:this.$(obj)).getElementsByTagName(tag);},
	$li:function(obj,n){return this.$$('li',this.$$('ul',obj)[n])},
	linear:function(t,b,c,d){return c*t/d + b;},
	easeIn:function(t,b,c,d){return c*(t/=d)*t*t*t + b;},
	easeOut:function(t,b,c,d){return -c*((t=t/d-1)*t*t*t - 1) + b;},
	easeInOut:function(t,b,c,d){return ((t/=d/2) < 1)?(c/2*t*t*t*t + b):(-c/2*((t-=2)*t*t*t - 2) + b);},
	style:function(obj,style){return (+[1,])?window.getComputedStyle(obj,null)[style]:obj.currentStyle[style];},//getStyle简化版
	opa:function(obj,v){//取得或设置对象透明度,默认100
		if(v!=undefined) {v=v>100?100:(v<0?0:v); obj.style.filter = "alpha(opacity=" + v + ")"; obj.style.opacity = (v / 100);}
		else return (!+[1,])?((obj.filters.alpha)?obj.filters.alpha.opacity:100):((obj.style.opacity)?obj.style.opacity*100:100);
	},
	animate:function(obj,prop,val,spd,type,fn){
		var opa=prop=='opacity'?true:false;
		if(opa&&obj.style.display=='none'){obj.style.display='';this.opa(obj,0);}
		var t=0,b=opa?this.opa(obj):parseInt(this.style(obj,prop)),c=val-b,d=spd||50,st=type||'easeOut',m=c>0?'ceil':'floor';
		if(obj[prop+'Timer']) clearInterval(obj[prop+'Timer']);
		obj[prop+'Timer']=setInterval(function(){
			if(opa&&t<d){myFocus.opa(obj,Math[m](myFocus[st](++t,b,c,d)));}
			else if(!opa&&t<d){obj.style[prop]=Math[m](myFocus[st](++t,b,c,d))+'px';}
			else {if(opa&&val==0){obj.style.display='none'}clearInterval(obj[prop+'Timer']);fn&&fn.call(obj);}
		},10);return this;
	},
	fadeIn:function(obj,speed,fn){this.animate(obj,'opacity',100,speed==undefined?20:speed,'linear',fn);return this;},
	fadeOut:function(obj,speed,fn){this.animate(obj,'opacity',0,speed==undefined?20:speed,'linear',fn);return this;},
	slide:function(obj,params,speed,easing,fn){for(var p in params) this.animate(obj,p,params[p],speed,easing,fn);return this;},
	stop:function(obj){//停止所有运动函数
		var animate=['left','right','top','bottom','width','height','opacity'];
		for(var i=0;i<animate.length;i++) if(obj[animate[i]+'Timer']) clearInterval(obj[animate[i]+'Timer']);
		return this;
	},
	initCSS:function(p){
		var css=[],oStyle = document.createElement('style');oStyle.type='text/css';
		if(p.width){css.push('.'+p.style+' *{margin:0;padding:0;border:0;list-style:none;}.'+p.style+'{position:relative;width:'+p.width+'px;height:'+p.height+'px;overflow:hidden;font:12px/1.5 Verdana,Geneva,sans-serif;background:#fff;}.'+p.style+' .loading{position:absolute;z-index:9999;width:100%;height:100%;color:#666;text-align:center;padding-top:'+0.3*p.height+'px;background:#fff url(http://nethd.zhongsou.com/wtimg/i_41956/28236-loading.gif) center '+0.4*p.height+'px no-repeat;}.'+p.style+' .pic,.'+p.style+' .pic *{width:'+p.width+'px;height:'+p.height+'px;}.'+p.style+' .txt li,.'+p.style+' .txt li span,.'+p.style+' .txt-bg{width:'+p.width+'px;height:'+p.txtHeight+'px;line-height:'+p.txtHeight+'px;overflow:hidden;}')}
		if(oStyle.styleSheet){oStyle.styleSheet.cssText=css.join('');} else {oStyle.innerHTML=css.join('');}
		var oHead = this.$$('head',document)[0];oHead.insertBefore(oStyle,oHead.firstChild);
	},
	setting:function(par){
		if(window.attachEvent){(function(){try{myFocus.$(par.id).className=par.style;myFocus.initCSS(par);}catch(e){setTimeout(arguments.callee,0);}})();window.attachEvent('onload',function(){myFocus[par.style](par)});}
　　		else{document.addEventListener("DOMContentLoaded",function(){myFocus.$(par.id).className=par.style;myFocus.initCSS(par);},false);window.addEventListener('load',function(){myFocus[par.style](par)},false);}
	},
	addList:function(obj,cla){//生成HMTL,cla为生成列表的class,其中封装有:cla='txt'(生成alt文字),cla='num'(生成按钮数字),cla='thumb'(生成小图)
		var s=[],n=this.$li(obj,0).length,num=cla.length;
		for(var j=0;j<num;j++){
			s.push('<ul class='+cla[j]+'>');
			for(var i=0;i<n;i++){s.push('<li>'+(cla[j]=='num'?('<a>'+(i+1)+'</a>'):(cla[j]=='txt'?this.$li(obj,0)[i].innerHTML.replace(/\>(.|\n|\r)*?(\<\/a\>)/i,'>'+(this.$$('img',obj)[i]?this.$$('img',obj)[i].alt:'')+'</a>'):(cla[j]=='thumb'?'<img src='+(this.$$('img',obj)[i].getAttribute("thumb")||this.$$('img',obj)[i].src)+' />':'')))+'<span></span></li>')};
			s.push('</ul>');
		}; obj.innerHTML+=s.join('');
	},
	switchMF:function(fn1,fn2,auto){
		return "box.removeChild(this.$$('div',box)[0]);var run=function(idx){("+fn1+")();if (index == n - 1) index = -1;var next = idx != undefined ? idx: index + 1;("+fn2+")();index=next;};run(index);if("+auto+"!==false) var auto=setInterval(function(){run()},t);box.onmouseover=function(){if(auto) clearInterval(auto);};box.onmouseout=function(){if(auto) auto=setInterval(function(){run()},t);}"
	},
	bind:function(arrStr,type,delay){
		return "for (var j=0;j<n;j++){"+arrStr+"[j].index=j;if("+type+"=='click'){"+arrStr+"[j].onmouseover=function(){if(this.className!='current') this.className='hover'};"+arrStr+"[j].onmouseout=function(){if(this.className=='hover') this.className=''};"+arrStr+"[j].onclick=function(){if(this.index!=index) run(this.index)};}else if("+type+"=='mouseover'){"+arrStr+"[j].onmouseover=function(){var self=this;if("+delay+"==0){if(!self.className) run(self.index)}else "+arrStr+".d=setTimeout(function(){if(!self.className) run(self.index)},"+(delay==undefined?100:delay)+")};"+arrStr+"[j].onmouseout=function(){clearTimeout("+arrStr+".d)};}else{alert('myFocus 不支持这样的事件 \"'+"+type+"+'\"');break;}}"
	},
	extend:function(fnObj){for(var p in fnObj) this[p]=fnObj[p];}
}
/*
 * myFocus焦点图基本库代码结束
 * 下面是各款基于myFocus库制作的焦点图风格皮肤，可自行按需选择
 * myFocus焦点图库及皮肤可自由使用，保留作者相关信息即可，谢谢支持！^^
 */
myFocus.extend({
	mF_classicHC:function(par){//*************经典怀旧系列一--慧聪风格*********
		var box=this.$(par.id);//定义焦点图盒子
		this.addList(box,['P','num-bg','num']);//添加ul列表
		var pic=this.$li(box,0),txt=this.$li(box,1),num=this.$li(box,3);//定义焦点图元素
		var index=0,n=pic.length,t=par.time*1000;//运行时相关参数
		//CSS
		var txtH=par.txtHeight;
		box.style.width=par.width+2+'px';box.style.height=par.height+txtH+2+'px';
		this.$$('ul',box)[2].style.bottom=this.$$('ul',box)[3].style.bottom=txtH+1+'px';
		for(var i=0;i<n;i++){
			pic[i].style.display='none';txt[i].style.display='none';
			txt[i].style.top=par.height+2+'px';txt[i].style.width=par.width+2+'px';
		}
		//PLAY
		eval(this.switchMF(function(){
			pic[index].style.display='none';
			txt[index].style.display='none';
			num[index].className='';
		},function(){
			myFocus.fadeIn(pic[next]);
			txt[next].style.display='';
			num[next].className='current';
		}))
		eval(this.bind('num','par.trigger',par.delay));
	}
})
//下面为焦点图的调用，可在网页任意位置调用
myFocus.setting({
	style:'mF_classicHC',//风格样式
	id:'myFocus',//焦点图ID
	trigger:'click',//按钮切换模式：'click'(点击触发)/'mouseover'(悬停触发,默认有0.1秒延迟，可以自行添加参数'delay:毫秒'设置)
	time:3,//切换图片的时间间隔，单位秒
	txtHeight:0,//文字层高，26为推荐，0为隐藏
	width:528,//宽(大图)，注意:整个焦点图的宽等于或大于图片宽
	height:285//高(大图)，注意同上
});