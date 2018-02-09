xjj.nameSpace("xjj.cms.menuFJSQ");
//创建工具栏按钮
xjj.cms.menuFJSQ = (function(){
	
	
	
	/**名称过滤**/
    var filter = function(treeId, parentNode, childNodes) {
    	if (!childNodes) return null;
    	for (var i=0, l=childNodes.length; i<l; i++) {
    		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    	}
    	return childNodes;
    }; 	
    
	/**
	 * ajax请求部门树
	 */
	var ajax4OrgTree = function(){
		// 树配置
	    var setting = {
	    		data: {
					simpleData: {
						enable: true
					},
					key: {
						children: "nodes"
					}
				}
	    	};
	    
		$.ajax({
		url:path+"/Zorganize/getOrgByLevel.json",
		type:"POST",
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
		}
	})
	}
	
	
	/**
	 * 异步加载之前的事件回调函数，判断节点是否可以加载
	 */
	var beforeAsync2OrgTree = function(treeId, treeNode) {
		var clickLevel = treeNode.level;
		if(clickLevel == level){
			return false;
		}
		return true;
	};
	
	var orgOnClick = function(event, treeId, treeNode){
		var nodeId =  treeNode.id;
		if("0" != nodeId){
			orgId = nodeId;
		}
		ajax4MenuTree(orgId);
	}
	
	/**
	 * 异步部门树
	 */
	var asyncOrgTree = function(){
	     // 树配置
	    var setting = {
	    		async: {
	    			enable: true,
	    			chkStyle: "checkbox",
	    			url:path+"/Zorganize/getByPid.json",
	    			autoParam:["id","level"],
	    			dataFilter: filter
	    		},
	    		view:{
	    			showIcon:true,
	    			showLine:true
	    		},
	    		callback: {
	    			beforeAsync: function(treeId, treeNode){
	    				return beforeAsync2OrgTree(treeId, treeNode)
	    			},
	    			onClick: orgOnClick
	    			
	    		}
	    		
	    	};
	    var rootNode = {id:"0",name:"单位列表",isParent:true, open:true};
	    var orgTree = $.fn.zTree.init($("#orgTree"), setting,rootNode);
	    var nodes = orgTree.getNodes();
		if (nodes.length>0) {
			//设置第一个节点选中
			orgTree.selectNode(nodes[0]);
			//展开根据点
			orgTree.expandNode(nodes[0], true, true, true, false);
		}
	}
	
	
	/**
	 * 授权按钮监听
	 */
	var menu4Fjsq = function () {
		
		var menuTree_check = $.fn.zTree.getZTreeObj("menuTree");
		var nodes = menuTree_check.getCheckedNodes(true);
		var checkIds = "";
		var length = nodes.length;
		for(var i=0;i<length;i++){
			var checkNode = nodes[i];
			checkIds += checkNode.id+",";
		}
		if(checkIds.length > 0){
			checkIds = checkIds.substr(0,checkIds.length-1);
			$.ajax({
				url:path+"/cms/menuOrg/save.json",
				type:"POST",
				data:{
					menuIds:checkIds,
					orgId:orgId
				},
				success :function(data){
					alert("授权成功");
				}
			})
		}
		
		
	};

	/**
	 * ajax请求栏目树
	 */
	var ajax4MenuTree = function(orgId){
		// 树配置
	    var setting = {
	    		check: {
	    			enable: true,
	    			chkboxType : { "Y": "", "N": "" }
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
					//onCheck: menuOnCheck
				}
	    	};
	    
		$.ajax({
		url:path+"/cms/menu/getAllmenu.json",
		type:"POST",
		data:{
			orgId:orgId
		},
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
		})
	}
	
	var initPage = function(){
		// 页面分隔 
		$("#MainLayout").kendoSplitter({
	  		panes: [
	  		        {collapsible: true, size: '45%' },
	                { collapsible: true }
	                ]
	      });
		
		asyncOrgTree();
		$("#ftsq_bt").click(function(){
			if("0" == orgId){
				AlertDialog({
					Title : "提示信息",
					Message : "请选择部门！",
					Icon : "k-ext-information"
				});
			}else{
				menu4Fjsq();
			}
		});
	}

	
	
  return{
	  initPage : initPage,
	  menu4Fjsq:menu4Fjsq
  }
	
})();