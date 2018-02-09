//待推送栏目树
function ajax4SourceMenuTree(){
	// 树配置
    var setting = {
		check: {
			enable: true,chkboxType: { "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {enable: true},
			key: {children: "nodes"}
		},
		callback: {
			//选中
			onCheck: onCheck
		}
	};
	$.ajax({
		url:basePath+"/cms/menu/getAllmenu.json",
		type:"POST",
		data:{"orgId":"0"},
		success :function(data){
			var rootNode = {id:"0",name:"栏目列表",isParent:true, open:true,nodes:data};
			var menuTree = $.fn.zTree.init($("#menuTree"), setting,rootNode);
			var nodes = menuTree.getNodes();
			if (nodes.length>0) {
				//设置第一个节点选中
				menuTree.selectNode(nodes[0]);
				//展开根据点
				menuTree.expandNode(nodes[0], true, null, null, null);
			}
		}
	});
}
function onCheck(){
	var zTree = $.fn.zTree.getZTreeObj("menuTree");
	var checkCount = zTree.getCheckedNodes(true);
	var html = "";
	checkMenus = new Array();
	if(checkCount.length > 0){
		for(var i=0;i<checkCount.length;i++){
			var checkNode = checkCount[i];
			if(!checkNode.isParent){
				html += "<li>["+checkNode.getParentNode().name+"]----"+checkNode.name+"</li>";
				checkMenus.push(checkNode.id);
			}
		}
	}
	$("#check_menu").html(html);
	asyncOrgTree(checkMenus.toString());
}

//推送单位树
function asyncOrgTree(){
     // 树配置
    var setting = {
    		check: {
    			enable: true,
    			chkboxType: { "Y": "ps", "N": "ps" }
    		},
    		data: {
				simpleData: {
					enable: true
				},
				key: {
					children: "nodes"
				}
			},
			callback: {
				onCheck: orgOnCheck
			}
   	};
    $.ajax({
		url:basePath+"/Zorganize/getAllOrg.json",
		type:"POST",
		data:{"checkMenus":checkMenus.toString()},
		success :function(data){
			var rootNode = {id:"0",name:"单位列表",isParent:true, open:true,nodes:data};
		    var orgTree = $.fn.zTree.init($("#orgTree"), setting,rootNode);
		    var nodes = orgTree.getNodes();
			if (nodes.length>0) {
				//设置第一个节点选中
				orgTree.selectNode(nodes[0]);
				//展开根据点
				orgTree.expandNode(nodes[0], true, null, null, null);
			}
			var orgCheckCount = orgTree.getCheckedNodes(true);
			var orgids = new Array();
			if(orgCheckCount.length > 0){
				for(var i=0;i<orgCheckCount.length;i++){
					var checkNode = orgCheckCount[i];
					if(checkNode.id != "0" && checkNode.name != "乡镇村"){
						orgids.push(checkNode.id);
					}
				}
			}
			orgids = orgids.toString();
			if(orgids != ""){
		    	ajax4MenuTree(orgids,checkMenus);
			}else{
				$("#targetMenuTree").html("");
				$("#target_menu").html("");
			}
		}
	});
}
function orgOnCheck(){
	var zTree = $.fn.zTree.getZTreeObj("orgTree");
	var checkCount = zTree.getCheckedNodes(true);
	var orgids = new Array();
	if(checkCount.length > 0){
		for(var i=0;i<checkCount.length;i++){
			var checkNode = checkCount[i];
			if(checkNode.id != "0" && checkNode.name != "乡镇村"){
				orgids.push(checkNode.id);
			}
		}
	}
	orgids = orgids.toString();
	if(orgids != ""){
    	ajax4MenuTree(orgids,checkMenus);
	}else{
		$("#targetMenuTree").html("");
		$("#target_menu").html("");
	}
}
//推送栏目树
function ajax4MenuTree(orgids){
	// 树配置
    var setting = {
    		check: {
    			enable: true,
    			chkboxType: { "Y": "ps", "N": "ps" }
    		},
    		data: {
				simpleData: {
					enable: true
				},
				key: {
					children: "nodes"
				}
			},
			callback: {
				onCheck: menuCheck
			}
	};
	$.ajax({
		url:basePath+"/cms/menu/getMenuTreeByOrgs.json",
		type:"POST",
		data:{"orgids":orgids,"checkMenus":checkMenus.toString()},
		success :function(data){
			var rootNode = {id:"0",name:"栏目列表",isParent:true, open:true,nodes:data};
			var menuTree = $.fn.zTree.init($("#targetMenuTree"), setting,rootNode);
			var nodes = menuTree.getNodes();
			if (nodes.length>0) {
				//设置第一个节点选中
				menuTree.selectNode(nodes[0]);
				//展开根据点
				menuTree.expandNode(nodes[0], true, null, null, null);
			}
			var orgTree = $.fn.zTree.getZTreeObj("orgTree");
			var orgTreecheckCount = orgTree.getCheckedNodes(true);
			var orgObjs = new Array();//单位Object集合
			if(orgTreecheckCount.length > 0){
				for(var i=0;i<orgTreecheckCount.length;i++){
					var _checkNode = orgTreecheckCount[i];
					if(_checkNode.id != "0" && _checkNode.name != "乡镇村"){
						var orgObj = new Object();
						orgObj.id = _checkNode.id;
						orgObj.name = _checkNode.name;
						orgObj.parentName = _checkNode.getParentNode().name;
						orgObj.isParent = _checkNode.isParent;
						orgObjs.push(orgObj);
					}
				}
			}
			var zTree = $.fn.zTree.getZTreeObj("targetMenuTree");
			var checkCount = zTree.getCheckedNodes(true);
			var html = "";
			orgTreeAry = new Array();
			for (var j = 0; j < orgObjs.length; j++) {
				var obj = orgObjs[j];
				if(obj.parentName == "乡镇村"){
					//乡镇村
					if(obj.isParent){
						//镇
						if(checkCount.length > 0){
							for(var i=0;i<checkCount.length;i++){
								var checkNode = checkCount[i];
								if(!checkNode.isParent){
									if(checkNode.getParentNode().name == "镇"){
										html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
										orgTreeAry.push(obj.id);
									}
								}
							}
						}
					}else{
						//机关
						if(checkCount.length > 0){
							for(var i=0;i<checkCount.length;i++){
								var checkNode = checkCount[i];
								if(!checkNode.isParent){
									if(checkNode.getParentNode().name == "机关"){
										html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
										orgTreeAry.push(obj.id);
									}
								}
							}
						}
					}
				}else if(obj.parentName == "单位列表"){
					//县等其他单位
					if(checkCount.length > 0){
						for(var i=0;i<checkCount.length;i++){
							var checkNode = checkCount[i];
							if(!checkNode.isParent){
								if(checkNode.getParentNode().name == "临高县"){
									html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
									orgTreeAry.push(obj.id);
								}
							}
						}
					}
				}else{
					//村 居委会  社区
					if(obj.name.indexOf("村") != -1){
						if(checkCount.length > 0){
							for(var i=0;i<checkCount.length;i++){
								var checkNode = checkCount[i];
								if(!checkNode.isParent){
									if(checkNode.getParentNode().name == "村"){
										html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
										orgTreeAry.push(obj.id);
									}
								}
							}
						}
					}
					if(obj.name.indexOf("社区") != -1){
						if(checkCount.length > 0){
							for(var i=0;i<checkCount.length;i++){
								var checkNode = checkCount[i];
								if(!checkNode.isParent){
									if(checkNode.getParentNode().name == "社区"){
										html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
										orgTreeAry.push(obj.id);
									}
								}
							}
						}
					}
					if(obj.name.indexOf("居委会") != -1){
						if(checkCount.length > 0){
							for(var i=0;i<checkCount.length;i++){
								var checkNode = checkCount[i];
								if(!checkNode.isParent){
									if(checkNode.getParentNode().name == "居委会"){
										html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
										orgTreeAry.push(obj.id);
									}
								}
							}
						}
					}
				}
			}
			$("#target_menu").html(html);
		}
	});
}

function menuCheck(){
	var orgTree = $.fn.zTree.getZTreeObj("orgTree");
	var orgTreecheckCount = orgTree.getCheckedNodes(true);
	var orgObjs = new Array();//单位Object集合
	if(orgTreecheckCount.length > 0){
		for(var i=0;i<orgTreecheckCount.length;i++){
			var _checkNode = orgTreecheckCount[i];
			if(_checkNode.id != "0" && _checkNode.name != "乡镇村"){
				var orgObj = new Object();
				orgObj.id = _checkNode.id;
				orgObj.name = _checkNode.name;
				orgObj.parentName = _checkNode.getParentNode().name;
				orgObj.isParent = _checkNode.isParent;
				orgObjs.push(orgObj);
			}
		}
	}
	var zTree = $.fn.zTree.getZTreeObj("targetMenuTree");
	var checkCount = zTree.getCheckedNodes(true);
	var html = "";
	orgTreeAry = new Array();
	for (var j = 0; j < orgObjs.length; j++) {
		var obj = orgObjs[j];
		if(obj.parentName == "乡镇村"){
			//乡镇村
			if(obj.isParent){
				//镇
				if(checkCount.length > 0){
					for(var i=0;i<checkCount.length;i++){
						var checkNode = checkCount[i];
						if(!checkNode.isParent){
							if(checkNode.getParentNode().name == "镇"){
								html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
								orgTreeAry.push(obj.id);
							}
						}
					}
				}
			}else{
				//机关
				if(checkCount.length > 0){
					for(var i=0;i<checkCount.length;i++){
						var checkNode = checkCount[i];
						if(!checkNode.isParent){
							if(checkNode.getParentNode().name == "机关"){
								html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
								orgTreeAry.push(obj.id);
							}
						}
					}
				}
			}
		}else if(obj.parentName == "单位列表"){
			//县等其他单位
			if(checkCount.length > 0){
				for(var i=0;i<checkCount.length;i++){
					var checkNode = checkCount[i];
					if(!checkNode.isParent){
						if(checkNode.getParentNode().name == "临高县"){
							html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
							orgTreeAry.push(obj.id);
						}
					}
				}
			}
		}else{
			//村 居委会  社区
			if(obj.name.indexOf("村") != -1){
				if(checkCount.length > 0){
					for(var i=0;i<checkCount.length;i++){
						var checkNode = checkCount[i];
						if(!checkNode.isParent){
							if(checkNode.getParentNode().name == "村"){
								html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
								orgTreeAry.push(obj.id);
							}
						}
					}
				}
			}
			if(obj.name.indexOf("社区") != -1){
				if(checkCount.length > 0){
					for(var i=0;i<checkCount.length;i++){
						var checkNode = checkCount[i];
						if(!checkNode.isParent){
							if(checkNode.getParentNode().name == "社区"){
								html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
								orgTreeAry.push(obj.id);
							}
						}
					}
				}
			}
			if(obj.name.indexOf("居委会") != -1){
				if(checkCount.length > 0){
					for(var i=0;i<checkCount.length;i++){
						var checkNode = checkCount[i];
						if(!checkNode.isParent){
							if(checkNode.getParentNode().name == "居委会"){
								html += "<li id='"+obj.id+"' name='org_li'>["+obj.name+"]----"+checkNode.name+"</li>";
								orgTreeAry.push(obj.id);
							}
						}
					}
				}
			}
		}
	}
	$("#target_menu").html(html);
}