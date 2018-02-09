/**
 * 效果组件（飘窗）
 * @author yeyunfeng 2015-06-12
 * 
 */



xjj.nameSpace("xjj.cms.effect");

xjj.cms.effect = (function(){
	
	var _url = "/cms/effect/remote/getShowEffect.json";
	var _pc_renderId = "body"; //飘窗代码默认位置在body标签的最前面
	var _zdy_renderId = "body"; //自定义效果的默认位置在body标签的最前面
	var _urlPre= "/";
	var x_ys =[]; //存放各个飘窗的信息
	var that;
	/**
	 * 叶云峰 20141209
	 */
	var onSuccess = function(data){
		var effects = data.resultData;
		if(xjj.cms.notEmpty(effects)){
			var length = effects.length;
			var $pc_effct = null;
			var $zfy_effct = null;
			if("body" == _pc_renderId){
				$pc_effct = $(_pc_renderId);
				$zfy_effct = $(_zdy_renderId);
			}else{
				$pc_effct = $("#"+_pc_renderId);
				$zfy_effct = $("#"+_zdy_renderId);
			}
			for(var i=0;i<length;i++){
				var effect = effects[i];
				var type = effect.type;
				var x_offset = effect.x_offset;  //X偏移量
				var y_offset = effect.y_offset; //Y偏移量
				var step = effect.step;//层移动的步长，值越大移动速度越快
				var delay = effect.delay;//层移动的时间间隔,单位为毫秒，值越小移动速度越快
				var pc_imgUrl = effect.imgUrl;
				var url = effect.url;
				var xin = true, yin = true;
				var pc_div_id = "pc_"+i;
				var pc_xy = {pcId:pc_div_id,x:x_offset,y:y_offset,xin:xin,yin:yin,left:x_offset,top:y_offset,delay:delay,step:step};
				if(0 == type){
					x_ys.push(pc_xy);
					var pcdm = '<div state="pc" delay='+delay+'  id="'+pc_div_id+'" style="position:absolute;z-index:1000;left:'+x_offset+'px;top:'+y_offset+'px">'+
				   '<div>'+
				   '<a href="'+url+'" target="_blank">'+
				      '<img src="'+pc_imgUrl+'" alt="" border="0" />'+
				  '</a>'+
				   '</div>'+
				   '<div width="187" style="text-align:right">'+
				   '<span style="CURSOR:pointer;color:red;" onclick="xjj.cms.hideObj(\''+pc_div_id+'\');">关闭</span>'+
				   '</div>'+
				'</div>';
					$pc_effct.prepend(pcdm); //插到最前面
				}else if(1 == type){
					var pc_html = effect.htmlCode || "";
					$zfy_effct.prepend(pc_html); //插到最前面
				}
			}
			floaListen();
			var pcs = $('div[state="pc"]');
			pcs.each(function(k){
				var $pc_div = $(pcs[k]);
				var pc_id = $pc_div.attr("id");
				var pc_delay = $pc_div.attr("delay")
				$pc_div.mouseenter(function(){
					clearInterval(that[pc_id]);
				})
				$pc_div.mouseleave(function(){
					that[pc_id] = setInterval("xjj.cms.effect.setFloat('"+pc_id+"')", pc_delay);
				})
			})
		}
		that = this;
	}
	
	/**
	 * 监听飘窗
	 */
	var floaListen = function(){
		for(var i=0;i<x_ys.length;i++){
			var x_y = x_ys[i];
			var pc_id = x_y.pcId;
			var pc_delay = x_y.delay;
			this[x_y.pcId] = setInterval("xjj.cms.effect.setFloat('"+pc_id+"')", pc_delay);
		}
	}
	
	/**
	 * 
	 * 设置飘窗飘动
	 */
	var  setFloat = function (pcObj_id) {
		  for(var i=0;i<x_ys.length;i++){
			  var x_y = x_ys[i];
			  var pcId = x_y.pcId;
			  if(pcId == pcObj_id){
				  var step = x_y.step;
				  var pcObj = document.getElementById(pcId);
				  var left = x_y.left;
				  var top = x_y.top;
				  var rigth = document.body.clientWidth - pcObj.offsetWidth;
				  var buttom = document.body.clientHeight - pcObj.offsetHeight;
				  pcObj.style.left = x_y.x + document.body.scrollLeft + "px";
				  pcObj.style.top = x_y.y + document.body.scrollTop + "px";
				  x_y.x = x_y.x + step * (x_y.xin ? 1 : -1);
				  if (x_y.x < left) {
					  x_y.xin = true;
					  x_y.x = left;
				  }
				  if (x_y.x >= rigth) {
					  x_y.xin = false;
					  x_y.x = rigth;
				  }
				  x_y.y = x_y.y + step * (x_y.yin ? 1 : -1)
				  if (x_y.y < top) {
					  x_y.yin = true;
					  x_y.y = top;
				  }
				  if (x_y.y >= buttom) {
					  x_y.yin = false;
					  x_y.y = buttom;
				  }
				  break;
			  }

		  }

	   }


	/**
	 * 显示
	 */
	var show = function(_config){
		config = {
			url : _url,
			pc_renderId:_pc_renderId, //需要渲染的id
			urlPre:"",//请求前缀，也就是http://ip：端口/上下文/s
			zdy_renderId:_zdy_renderId
		};
		this.config = $.extend(true, config, _config);
		_pc_renderId = this.config.pc_renderId;
		_url = this.config.urlPre+_url;
		_zdy_renderId = this.config.zdy_renderId;
		_urlPre = this.config.urlPre;
		var data = {"org":orgCode};
		new xjj.cms.ajax(_url,data,null,onSuccess,null);
	}
	return {
		show:show,
		setFloat:setFloat
	}
})()