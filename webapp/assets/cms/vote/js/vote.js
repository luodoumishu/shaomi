/**
 * 投票管理组件
 * @author yeyunfeng 2014-11-20
 * 
 */
xjj.nameSpace("xjj.cms.vote");

xjj.cms.vote = (function(){
	
	var _url = "cms/vote/remote/findVote.json";
	var _renderId = null;
	var _itemTitle_rep_str = /\$_itemTitle/g;
	var _detailTitle_rep_str = /\$_detailTitle/g;
	var _itemNum_rep_str = /\$_num/g;
	var config = null;
	var _themeTitle ={};
	var _vote4El = "";
	/**
	 * 叶云峰 20141209
	 */
	var onSuccess = function(data){
		var itemList = data.cmsVoteItems;
		if(xjj.cms.notEmpty(itemList)){
			var $el =  $("#"+_renderId);
			if("null" != _themeTitle.renderId && "undefined" != _themeTitle.renderId){
				var $thmeeTitle = $("#"+_themeTitle.renderId);
				var themeName =  _themeTitle.textPre+data.themeName;
				$thmeeTitle.text(themeName);
			}
			var itemList_length = itemList.length;
			var num= 1;
			for(var i =0;i<itemList_length;i++){
				var item = itemList[i];
				if( xjj.cms.notEmpty(item)){
					//投票项名称
					var itemName = item.item_name;
					//投票项类型 (0单1多2问答)
					var itemType = item.item_type;
					//显示类型(0横排1竖排)
					var showType = item.show_type;
					var radio_name = "radio_name_"+item.id;
					//替换投票项的个数
					config.vote4El.replace(_itemNum_rep_str, num);
					//替换投票项名称
					var vote4html = config.vote4El.replace(_itemTitle_rep_str, itemName);
					//替换投票项的个数
					vote4html = vote4html.replace(_itemNum_rep_str, num);
					//vote4html = "("+vote4Num_html+")  "+vote4html;
					//$itemPre.append(itemHtml);
					//获取投票详情
					var detailList = item.cmsVoteDetails;
					if("2" == itemType){ //问答
						//name="'+seleteName+'"
						var textarea_html = '<textarea onkeyup="xjj.cms.checkLen(this,100);" cols="80" rows="4" id="'+item.id+'" onpropertychange="style.posHeight=scrollHeight+5"></textarea>100字以内';
						vote4html = vote4html.replace(_detailTitle_rep_str, textarea_html);
					}
					if(xjj.cms.notEmpty(detailList)){
						var detailList_length = detailList.length;
						var detalHtmls  = "";
						for(var j =0;j<detailList_length;j++){
							var detail = detailList[j];
							var id = detail.id;
						//	var seleteName = "chbItemlist"+id;
							var detail_title = detail.detail_name;
							//单选
							if("0" == itemType){//单选
								//name="chbItemlist_checkbox"
								var redio_html = '<input type="radio"   name="'+radio_name+'" value="'+id+'"><span style="margin-right:8px;">'+detail_title+'</span></input>';
								if("1" == showType){
									redio_html = '<div>'+redio_html+'</div>';
								}
								detalHtmls += redio_html;
							}else if("1" == itemType){ //多选
								//name="'+seleteName+'"
								var checkbox_html = '<input type="checkbox"  value="'+id+'">'+detail_title+'</input>';
								if("1" == showType){
									checkbox_html = '<div>'+checkbox_html+'</div>'
								}
								detalHtmls += checkbox_html;
							}
						}
						vote4html = vote4html.replace(_detailTitle_rep_str, detalHtmls);
					}
					//加入每一个投票项
					$el.append(vote4html);
					num++;
				}
			}
		}
		
	}
	/**
	 * 投票列表
	 */
	var list = function(_config){
		
		config = {
			size : -1, //每页大小 -1表示全部
			start : 0,//开始的条数
			titileLength : null, //标题的长度
			url : _url,
			renderId:"", //需要渲染的id
			voteId:"",
			urlPre:"",//请求前缀，也就是http://ip：端口/上下文/
			vote4El:"",
			themeTitle:{ //投票主题名称配置
				renderId:null,
				textPre : ""
			}
		};
		
		this.config = $.extend(true, config, _config);
		_renderId = this.config.renderId;
		var _data ={themeId:this.config.voteId};
		_url = this.config.urlPre+_url;
		_themeTitle = this.config.themeTitle;
		new xjj.cms.ajax(_url,_data,null,onSuccess,null);
	}

	/**
	 * 投票
	 */
	var doVote = function () {
		var input_select_list = document.getElementsByTagName("input");
		var textarea_select_list = document.getElementsByTagName("textarea");
		var checked_detail_id = new Array();//选择框所对应的明细ID集合
		var textarea_id_list = new Array();//文本框所对应的投票项ID集合
		var textarea_content_list = new Array();//文本框正文集合
		if(input_select_list.length > 0){
			//选择框
			for ( var i = 0; i < input_select_list.length; i++) {
				var input_select = input_select_list[i];
				if(input_select.checked == true){
					checked_detail_id.push(input_select.value);
				}
			}
		}
		if(textarea_select_list.length > 0){
			//问答类型
			for ( var j = 0; j < textarea_select_list.length; j++) {
				var textarea_select = textarea_select_list[j];
				textarea_id_list.push(textarea_select.id);
				textarea_content_list.push(textarea_select.value);
			}
		}
		$.ajax({
			   type:"POST",
			   url: path+"/cms/vote/remote/doVote.json",
			   dataType:"json",
			   data:{"detailList":checked_detail_id,"itemIdList":textarea_id_list,"contentList":textarea_content_list,"themeId":voteId},
			   success: function(){
			     alert("投票成功!");
			     window.close();
			   }
		});
	}
	return {
		list:list,
		doVote:doVote
	}
})()