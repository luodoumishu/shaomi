var ctx=getContextPath();


function getContextPath() {
    var pathName = document.location.pathname;
    //console.log("pathName: "+pathName);
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    if(result=="/pages")
    	result="";
    return result;
}

function showErrorMsg(msg)
{
	alert(msg);
	throw msg
}

/**
 * 渲染搜索结果
 * @param text 未加工搜索结果
 * @param userInfo	用户信息其中，userInfo.highLightStr为需要高亮文字
 * @returns {String} 渲染文字结果
 */
function setItemTemplate(text,userInfo)
{
	var hightLightStr = userInfo.highLightStr;
	if(text!=null)
	text = text.replace(hightLightStr,"<span style='font-weight:bold;color:#3d882d'>"
		   +hightLightStr+"</span>");
	else
	text = userInfo.userName.replace(hightLightStr,"<span style='font-weight:bold;color:#3d882d'>"
			   +hightLightStr+"</span>");
	//console.log("After replace text: "+text);
	return "<span>"+text+"</span>";
}




setSelectUser = function (pluginId) {
	var optObj = $('#'+pluginId).data("xjjAddressBook");
	if(!(optObj))
		showErrorMsg("setSelectUser：无法根据ID["+pluginId+"]获取到地址本选项对象！")
	
	var userIds = optObj.selectUserLinkMap.keys;
	var multiSelect = $("#"+optObj.userMultiSelectorId).data("kendoMultiSelect");
	
	multiSelect.dataSource.data([]);
	optObj.selectUserLinkMap.each(function(key,value,index){
		multiSelect.dataSource.insert(index,value);
	});
	multiSelect.value(userIds);
	if(optObj.userChangeCallBack)
		optObj.userChangeCallBack(multiSelect.dataItems());
};

function initDefaultSelectUser(optsObj)
{
	var initUsers = null;
	
	//获取初始化用户ID列表数组
	if( (typeof optsObj.initSelectUser)=='function')
		initUsers = optsObj.initSelectUser();
	else if((typeof optsObj.initSelectUser)=='object')
		initUsers =  optsObj.initSelectUser;
	else{
		
	}
	$.ajax({
    	url: ctx+"/addressbook/getUserByUserIds.json",
    	async:true,
        data:"_userIdList="+JSON.stringify(initUsers),
        dataType: "json", 
        type:"POST",
        success: function(_result) {
        	var result = _result.resultData;
        	if(result!=null&&result!="null")
    		{
        		var multiSelect = $("#"+optsObj.userMultiSelectorId).data("kendoMultiSelect");
        		multiSelect.dataSource.data(result);
            	multiSelect.value(initUsers);
            	//kendo 2015-03版本必须有此方法调用，否则不显示选中的项目
            	//multiSelect.refresh();
    		}
        	return;
        },
        error: function(result) {
        	
        }
    });
	return;
}


// 创建一个闭包     
(function($) {
	
	openAddressBook = function(pluginId) 
	{
			//console.log("pluginId: "+pluginId);
			var optObj = $("#"+pluginId).data("xjjAddressBook");
			if(!optObj)
				showErrorMsg("openAddressBook： 无法根据ID["+pluginId+"]获取到地址本选项对象！")
			if(optObj.validateCondition&&optObj.validateCondition()==false)
			{
				return;
			}
			var dataItems = optObj.XJJAB_multiSelector.dataItems();
			//console.log("dataItems:"+JSON.stringify(dataItems));
			var that = optObj;
			optObj.selectUserLinkMap.removeAll();
			$.each(dataItems, function(i, item) {
				that.selectUserLinkMap.put(item.id, item);
			});
			openwindow(ctx+"/pages/addressBookJqueryPlugin/selectUsers.jsp?pluginId="+optObj.id, $(window).width() * optObj.addressBookWidth, $(window).height() * optObj.addressBookHight, "", "div", "地址本");
	};
	
  // 插件的定义     
  $.fn.addressBook = function(Options) {     
    
	//console.log("init XJJ_AddressBook");
	if(this.size()>1)
		showErrorMsg("Jquery选择器选择了多个对象,地址本每次只能初始化为一个实例!");
	
	//console.log("uuid: "+Math.uuid());
	var opts = {};
	opts.id = (this.attr("id"))?this.attr("id"):getUUID();
	this.attr("id",opts.id);
	opts.errorMsgPromptId = getUUID();
	
	
	
	opts.isSupportPinyin = (Options&&Options.isSupportPinyin)?Options.isSupportPinyin:false;
	//多选框是否可以编辑
	opts.isSelectorEditable = (Options&&Options.isSelectorEditable)?Options.isSelectorEditable:true;
	opts.userMultiSelectorId = (Options&&Options.userMultiSelectorId)?Options.userMultiSelectorId:showErrorMsg("错误：未指定多选框ID[userMultiSelectorId]!");

	//扁平型数据源默认地址
	opts.groupServiceRoot = (Options&&Options.flatServiceRoot)?Options.flatServiceRoot:ctx+"/addressbook/getFlatUsers.json";
	//树型数据源默认地址
	opts.orgServiceRoot = (Options&&Options.HierarchichalServiceRoot)?Options.HierarchichalServiceRoot:ctx+"/addressbook/getHierarchichalUsers.json";
	//搜索框数据源默认地址
	opts.searchUserRoot = (Options&&Options.searchUserRoot)?Options.searchUserRoot:ctx+"/addressbook/getUserByFilter.json";
	//地址本展现形式 ORG 为机构列表 GROUP 为组列表,Ex:["ORG","GROUP"] 或 ["GROUP","ORG"] 或 ["ORG"] 或 ["GROUP"]
	opts.showType = (Options&&Options.showType&&Options.showType.length>0)?Options.showType:["ORG","GROUP"];
	//指定地址本顶级部门
	opts.assignOrgId = (Options&&Options.assignOrgId)?Options.assignOrgId:null;
	//打开地址本窗口宽度百分百
	opts.addressBookWidth = (Options&&Options.addressBookWidth)?Options.addressBookWidth:0.7;
	//打开地址本窗口高度百分百
	opts.addressBookHight = (Options&&Options.addressBookHight)?Options.addressBookHight:0.6;
	//搜索框长度
	opts.searchBoxWidth = (Options&&Options.searchBoxWidth)?Options.searchBoxWidth:"280px";
	//指定是否显示组 true:显示 false：不显示
	opts.isShowGroup = (Options&&Options.isShowGroup!==undefined)?Options.isShowGroup:true;
	//地址选中用户改变时的回调函数
	opts.userChangeCallBack= (Options&&Options.userChangeCallBack!==undefined)?Options.userChangeCallBack:null;
	//打开地址本窗口用户自定义校验,true:校验通过，false:校验不通过
	opts.validateCondition= (Options&&Options.validateCondition!==undefined)?Options.validateCondition:null;
	opts.buttonName = (Options&&Options.buttonName)?Options.buttonName:'地址本';
	opts.XJJAB_multiSelector = null;
	opts.selectUserLinkMap = (Options&&Options.selectUserLinkMap)?Options.selectUserLinkMap:new LinkMap();
	//初始化用户ID数组，或一个返回用户ID数组的函数
	opts.initSelectUser = (Options&&Options.initSelectUser)?Options.initSelectUser:null;
	
	
	this.data("xjjAddressBook",opts);
	createInnerHtml(opts);
	initUserMultiSelector(opts);
	initDefaultSelectUser(opts);
    return this;
    };   
  
    $.fn.resetAddressBook = function(){
    	var opts = this.data("xjjAddressBook");
    	opts.selectUserLinkMap =  new LinkMap();
    	var multiSelect = opts.XJJAB_multiSelector;
    	multiSelect.dataSource.data([]);
    	multiSelect.value([]);
    };
    
   
    
    function isContainLetter(str)
    {
    	var reg = /[a-zA-Z]/g;
    	//console.log("str: "+str);
    	if(str)
		{
    		//console.log(str.match(reg)!=null);
    		return str.match(reg)!=null;
		}
    		
    	else 
    		return false;
    }
    
    function getUUID()
    {
    	var uuid = Math.uuid()
    	return uuid
    }
    
	function createInnerHtml(optsObj)
	{
		var searchBoxWidth = optsObj.searchBoxWidth;
		var innerHtml = ' <span class="adddressBookSelector k-textbox k-space-right" style="width:'+searchBoxWidth+'">'+
            					'<select id="'+optsObj.userMultiSelectorId+'" multiple="multiple" ></select>'+
            					'<a href="#" class="k-icon k-i-search">&nbsp;</a>'+
            			 '</span>'+
            			 '<span>'+
            			 	'<a class="k-button" style="font-weight: bold" onclick="javascript:openAddressBook(\''+optsObj.id+'\')" >'+optsObj.buttonName+'</a> '+
            			 '</span></span>';
		$("#"+optsObj.id).html(innerHtml);
	}
	
	
	function initUserMultiSelector(optsObj) {
		var that = optsObj;
		optsObj.XJJAB_multiSelector = $("#"+optsObj.userMultiSelectorId).kendoMultiSelect({
		       dataTextField: "text",
		       tagTemplate: "#:userInfo.userName#",
		       dataValueField: "id",
		       itemTemplate:"#=setItemTemplate(text,userInfo)#",
			   autoBind: false,
		       filter: "contains",
			   filtering:function(e)
			   {
				   var filter = e.filter;
				   if (!filter.value) {
					   e.preventDefault();
				   }
			   },
		       delay: 1000,
			   enable: that.isSelectorEditable,
		       autoClose: false,
		       change: function(e) {
		    	   var dataItems = this.dataItems();
		    		//console.log("dataItems:"+JSON.stringify(dataItems));
		    	   that.selectUserLinkMap.removeAll();
		    		$.each(dataItems, function(i, item) {
		    			that.selectUserLinkMap.put(item.id, item);
		    		});
		    		if(that.userChangeCallBack)
		    			that.userChangeCallBack(dataItems);
		       },
		       dataSource: {
		           serverFiltering: true,
		           transport: {
		                read: function(options) {
							//console.log("options: "+JSON.stringify(options));
							var filterObj = new Object();
							var data = options.data;
							var _result = new Array();
							if( (data.filter!=null&& data.filter!=undefined))
							{
								filterObj.assignOrgId = that.assignOrgId?that.assignOrgId:"";
								filterObj.filterStr = data.filter["filters"][0]!=null?data.filter["filters"][0]["value"]:"";
								if(that.isSupportPinyin==false&&isContainLetter(filterObj.filterStr))
								{
									data.filter = null;
									options.success(new Array());
									$("#"+that.errorMsgPromptId).text("地址本不支持字母搜索，请输入中文用户名或机构");
									$("#"+that.errorMsgPromptId).show();
									return;
								}
								else
								{
									$("#"+that.errorMsgPromptId).hide();
								}
								$.ajax({
									url: that.searchUserRoot,
									data:"filter="+JSON.stringify(filterObj),
									dataType: "json",
									type:"POST",
									success: function(result) {

										//console.log("result before: "+JSON.stringify(result));
										for (var i = 0; i < result.length; i++) {
											if(!that.selectUserLinkMap.isExist(result[i].id))
												_result.push(result[i]);
										}
										//console.log("result after: "+JSON.stringify(_result));
										options.success(_result);
									},
									error: function(result) {
										// notify the data source that the request failed
										options.error(result);
									}
								});
							}
							else
							{
								options.success(_result);
							}

						}
		           }
		       }
		}).data("kendoMultiSelect");
	}
  

// 闭包结束     
})(jQuery);   