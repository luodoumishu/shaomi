xjj.nameSpace("xjj.cms.voteShow");

xjj.cms.voteShow = (function(){
	var init_editPage = function(){
		initForm();
	  };
	  var initForm = function (){
		  $.ajax({ 
			  type:'post',
			  url: path + "/cms/vote/theme/findAll.json", 
			  success: function(data){
		          if(data.resultCode === 0){
		        	  var themeList = data.resultData;
		        	  for ( var i = 0; i < themeList.length; i++) {
						var theme = themeList[i];
						var obj = document.getElementById("themeSelect");
						obj.length = obj.length + 1;
						obj.options[obj.length - 1].innerText = theme.themeName;
						obj.options[obj.length - 1].value = theme.id;
					}
		          }
			  }
		  });
	  };
	  
	  function selectVoteTheme(){
			var obj = document.getElementById("themeSelect"); //定位id
			var index = obj.selectedIndex; // 选中索引
			var themeId = obj.options[index].value; // 选中值
			 $.ajax({ 
				  type:'post',
				  url: path + "/cms/vote/theme/findItem.json", 
				  data:{"themeId":themeId},
				  success: function(data){
			          if(data.resultCode === 0){
			        	  var theme = data.resultData;
			        	  var html = "";
			        	  var detailHtml = "";
			        	  var contentItem = new Array();
			        	  if(theme != null){
				        	  var itemList = theme.cmsVoteItems;
				        	  if(itemList.length != 0){
				        		  for ( var i = 0; i < itemList.length; i++) {
									var item = itemList[i];
									var itemVoteNum = 0;
									if(item.item_type == 0){
										var detailList = item.cmsVoteDetails;
										if(detailList.length != 0){
											detailHtml = "";
											for ( var j = 0; j < detailList.length; j++) {
												var detail = detailList[j];
												if(detail.vote_num == null){
													detail.vote_num = 0;
												}
												itemVoteNum += detail.vote_num;
												detailHtml = detailHtml+"<div class='gao_tpcon' style='padding:5px 5px;'><li style='list-style:none;'>"+(j+1)+"、"+detail.detail_name+"<span style='float:right;margin:0px;'>总投票数:"+detail.vote_num+"</span></li></div>";
											}
										}
										html = html +"<div class='gao_tpnrtop'>"+(i+1)+"、"+item.item_name+"&nbsp;&nbsp;&nbsp;(单选)" +
												"<span style='float:right;margin:0px;'>总投票数:"+itemVoteNum+"</span></div>" + detailHtml;
									}else if(item.item_type == 1){
										var detailList = item.cmsVoteDetails;
										if(detailList.length != 0){
											detailHtml = "";
											for ( var j = 0; j < detailList.length; j++) {
												var detail = detailList[j];
												if(detail.vote_num == null){
													detail.vote_num = 0;
												}
												itemVoteNum += detail.vote_num;
												detailHtml = detailHtml+"<div class='gao_tpcon' style='padding:5px 5px;'><li style='list-style:none;'>"+(j+1)+"、"+detail.detail_name+"<span style='float:right;margin:0px;'>总投票数:"+detail.vote_num+"</span></li></div>";
											}
										}
										html = html +"<div class='gao_tpnrtop'>"+(i+1)+"、"+item.item_name+"&nbsp;&nbsp;&nbsp;(多选)" +
												"<span style='float:right;margin:0px;'>总投票数:"+itemVoteNum+"</span></div>" + detailHtml;
									}else if(item.item_type == 2){
										html = html +"<div class='gao_tpnrtop'>"+(i+1)+"、"+item.item_name+"&nbsp;&nbsp;&nbsp;(问答)</div><div id='"+item.id+"'></div>";
										contentItem.push(item.id);
									}
								}
				        	  }
			        	  }
		        		  $("#message").html(html);
		        		  setContent(contentItem);
			          }
				  }
			  });
		}
		
		function setContent(contentItem){
			 for ( var i = 0; i < contentItem.length; i++) {
				 var itemId = contentItem[i];
				 
					$.ajax({ 
						  type:'post',
						  url: path + "/cms/vote/anser/getByItemId.json",
						  async:false, 
						  data:{"itemId":itemId},
						  success: function(data){
					          if(data.resultCode === 0){
					        	  var anserList = data.resultData;
					        	  var temp = 1;
					        	  var newHtml = "";
				        		  for ( var j = 0; j < anserList.length; j++) {
				  					var anser = anserList[j];
				  					if(anser.content !=null){
				  						newHtml = newHtml + "<div style='padding:5px 5px;'><li style='list-style:none;'>("+temp+")、"+anser.content+"</li></div>";
				  						temp++;
				  					}
				  				  }
				        		  $("#"+itemId).html(newHtml);
					          }
						  }
					  });
			 }
		}
		
	   /**
	    * 显示投票主题表格
	    */	
       var showThemeGrid = function(){
    	 //数据源
   		dataSource = CreateGridDataSource({
   	 	    Transport: {
   	           ReadUrl: path+"/cms/vote/theme/list.json",
   	           ReadDataFunc: function () {
   	         	    queryObj = new Object;
   	             	return queryObj;
   	           }
   	      }
   	 	  });
    	// 列表表格
           grid = CreateGrid({
	   	        ID: "sys_grid",
	   	        DataSource: dataSource,
	   	        Editable:"popup",
	   	        ColumnMenu: false,    
	   	        Filterable: false,
	   	        Selectable:"row",
	   	        Columns: [ 
	   	                  { field: "themeName",title: "投票主题"},
	   	                  {width: "100px",title: "被评系统",template:function(data){
		                	  if(undefined == data.task || null == data.task  || undefined == data.task.system || null == data.task.system
		                			  || undefined == data.task.system.name || null == data.task.system.name ){
		                		  return "";
		                	  }else{
		                		  return data.task.system.name;
		                	  }
		                  }}],
	   	       dataBound:function(e){
		   	    	var sys_grid = $("#sys_grid").data("kendoGrid");
	            	sys_grid.select("tr:eq(1)");
	            	var rowData = sys_grid.dataItem("tr:eq(1)");
	            	if(null != rowData && undefined != rowData && "" != rowData){
	            		themeId = rowData.id;
		            	if(null != themeId && undefined != themeId && "" != themeId){
		            		showResult(themeId);
		            	}
	            	}
	            	
	   	       },Change: function(e) {
	   	    	    themeId = null;
	            	var selectedRows = this.select();
	            	var dataItem = this.dataItem(selectedRows[0]);
	            	if(null != dataItem){
	            		themeId = dataItem.id;
	            		if(null != themeId && undefined != themeId && "" != themeId){
		            		showResult(themeId);
		            	}
	            	}
	             }
          }); 
       }
	
    /**
     * 显示投票结果
     */
    var showResult = function(themeId){
		 $.ajax({ 
			  type:'post',
			  url: path + "/cms/vote/theme/findItem.json", 
			  data:{"themeId":themeId},
			  success: function(data){
		          if(data.resultCode === 0){
		        	  var theme = data.resultData;
		        	  var html = "";
		        	  var detailHtml = "";
		        	  var contentItem = new Array();
		        	  if(theme != null){
			        	  var itemList = theme.cmsVoteItems;
			        	  if(itemList.length != 0){
			        		  for ( var i = 0; i < itemList.length; i++) {
								var item = itemList[i];
								var itemVoteNum = 0;
								if(item.item_type == 0){//单选
									var detailList = item.cmsVoteDetails;
									if(detailList.length != 0){
										detailHtml = "";
										for ( var j = 0; j < detailList.length; j++) {
											var detail = detailList[j];
											if(detail.vote_num == null){
												detail.vote_num = 0;
											}
											itemVoteNum += detail.vote_num;
											detailHtml = detailHtml+"<div class='gao_tpcon' style='padding:5px 5px;'><li style='list-style:none;'>"+(j+1)+"、"+detail.detail_name+"<span style='float:right;margin:0px;'>总投票数:"+detail.vote_num+"</span></li></div>";
										}
									}
									html = html +"<div class='gao_tpnrtop'>"+(i+1)+"、"+item.item_name+"&nbsp;&nbsp;&nbsp;(单选)" +
											"<span style='float:right;margin:0px;'>总投票数:"+itemVoteNum+"</span></div>" + detailHtml;
								}else if(item.item_type == 1){ //多选
									var detailList = item.cmsVoteDetails;
									if(detailList.length != 0){
										detailHtml = "";
										for ( var j = 0; j < detailList.length; j++) {
											var detail = detailList[j];
											if(detail.vote_num == null){
												detail.vote_num = 0;
											}
											itemVoteNum += detail.vote_num;
											detailHtml = detailHtml+"<div class='gao_tpcon' style='padding:5px 5px;'><li style='list-style:none;'>"+(j+1)+"、"+detail.detail_name+"<span style='float:right;margin:0px;'>总投票数:"+detail.vote_num+"</span></li></div>";
										}
									}
									html = html +"<div class='gao_tpnrtop'>"+(i+1)+"、"+item.item_name+"&nbsp;&nbsp;&nbsp;(多选)" +
											"<span style='float:right;margin:0px;'>总投票数:"+itemVoteNum+"</span></div>" + detailHtml;
								}else if(item.item_type == 2){//问答
									html = html +"<div class='gao_tpnrtop'>"+(i+1)+"、"+item.item_name+"&nbsp;&nbsp;&nbsp;(问答)</div><div id='"+item.id+"'></div>";
									contentItem.push(item.id);
								}
							}
			        	  }else{
			        		  html = "<br/><b>&nbsp;&nbsp;&nbsp;&nbsp;该投票主题还未设置投票项！</b>"; 
			        	  }
		        	  }
	        		  $("#voteResult_show").html(html);
	        		  setContent(contentItem);
		          }
			  }
		  });
    }
    
    /**
     * 界面初始化
     */
	var init_showResultPage = function(){
		//页面分隔
       	$("#MainLayout").kendoSplitter({
	        panes: [
	            { collapsible: true, size: "250px" },
	            { collapsible: true}
	        ]
        });
       	showThemeGrid();
	}
	
	return{
		  init_editPage : init_editPage,
		  selectVoteTheme:selectVoteTheme,
		  init_showResultPage:init_showResultPage
	};
})();
