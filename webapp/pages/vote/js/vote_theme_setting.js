xjj.nameSpace("xjj.cms.vote");

xjj.cms.vote = (function(){
	var init_editPage = function(){
		//初始化表单
			initForm();
	  };
	  //初始化投票项和明细
	  var initItem = function(themeId){
		  $.ajax({ 
			  type:'post',
			  data:{"themeId":themeId},
			  url: path + "/cms/vote/theme/findItem.json", 
			  success: function(data){
		          if(data.resultCode === 0){
		        	  var html = "";
		        	  var itemList = data.resultData.cmsVoteItems;
		        	  if(itemList.length != 0){
		        		  for ( var i = 0; i < itemList.length; i++) {
		        			  var item = itemList[i];
		        			  html = html + "<div class='gao_tpnr'>" +
		        			  		"<div class='gao_tpnrtop'>" +
		        			  		"<span><img src='images/gao_del.png' width='13' height='14' onclick=delItem('"+item.id+"'); style='cursor: pointer;'/></span>"+
		        			  		"<span><img src='images/gao_edit.png' width='14' height='14' onclick=editItem('"+item.id+"'); style='cursor: pointer;'/></span>" +
		        			  		""+(i+1)+"、"+item.item_name+"</div>";
		        			  html = html + "<div class='gao_tpcon'>";
		        			  if(item.item_type == 2){
		        				//问答
		        				html = html + "<textarea rows='3' cols='60'></textarea>";
		        			  }
		        			  var detailList = item.cmsVoteDetails;
		        			  var show_type = item.show_type;
		        			  if(show_type == 0){
		        				  //横排显示
		        				  if(detailList.length != 0){
				        			  for ( var j = 0; j < detailList.length; j++) {
				        				 var detail = detailList[j];
				        				 if(item.item_type == 0){
				        					 //单选
				        					 html = html + "<input type='radio' name='radio'/>&nbsp;"+detail.detail_name+"&nbsp;&nbsp;&nbsp;&nbsp;";
				        				 }
				        				 if(item.item_type == 1){
				        					//多选
				        					 html = html + "<input type='checkbox' name='checkbox'/>&nbsp;"+detail.detail_name+"&nbsp;&nbsp;&nbsp;&nbsp;";
				        				 }
				        			  }
				        		  }
		        			  }
		        			  if(show_type == 1){
		        				  //竖排显示
		        				  if(detailList.length != 0){
				        			  for ( var j = 0; j < detailList.length; j++) {
				        				 var detail = detailList[j];
				        				 if(item.item_type == 0){
				        					 //单选
				        					 html = html + "<li style='list-style:none'><input type='radio' name='radio'/>&nbsp;"+detail.detail_name+"&nbsp;&nbsp;&nbsp;&nbsp;</li>";
				        				 }
				        				 if(item.item_type == 1){
				        					//多选
				        					 html = html + "<li style='list-style:none'><input type='checkbox' name='checkbox'/>&nbsp;"+detail.detail_name+"&nbsp;&nbsp;&nbsp;&nbsp;</li>";
				        				 }
				        			  }
				        		  }
		        			  }
			        		html = html + "</div></div>";
						}
		        	  }
		        	  $("#itemUL").append(html);
		          }else{
		        	  alert("查询失败");
		          }
		      }
		  });
	  };
	  //初始化表单
	  var initForm = function (){
	      //数据模型绑定,MVVM
	      item = kendo.observable(item);
	      //数据模型绑定
	      kendo.bind($("#form-container-edit"), item);
		  //新建对象绑定模型
		 if (editType == "add") {//VIEW_MODEL层的数据初始化
			 item.is_valid = 0;
		 }
		 if (editType == "edit") {//VIEW_MODEL层的数据初始化

		 }
	  };
	  // 删除成功回调函数
		 var deleteSuccess  = function(result) {
		      if (result.resultCode === 0) {
		          if(result.resultMsg==""){
		  	          var row = grid.select();
		              for (var i = 0; i < row.length; i++) {
		                  grid.removeRow(row[i]);
		              }
		              grid.dataSource.read();
		              loadItemTree();
		         }else{
		              alert(result.resultMsg);
		         }
		      }else {
		          alert(result.resultMsg);
		      }
		  };
		  // 删除失败回调函数
		  var deleteError = function (result) {
		  	AlertDialog({
		          Title: "提示信息",
		          Message: "删除失败！",
		          Icon: "k-ext-error"
		      });
		  };
	  return{
		  init_editPage : init_editPage,
		  initItem : initItem,
		  deleteSuccess : deleteSuccess,
		  deleteError : deleteError
	};
})();
function setDetail(){
	var item_type = checkItemType();
	$("#detailList").html("");
	var obj = document.getElementById("detailNum"); //定位id
	var index = obj.selectedIndex; // 选中索引
	var detailNum = obj.options[index].value; // 选中值
	var html = "";
	var show_type = checkShowType();
	if(show_type == 0){
		if(detailNum > 0){
			for(var i = 0;i<detailNum-1;i++){
				html = html + "<input type='text' id='detail"+(i+1)+"' name='detailName' class='gao_ipt2' onkeyup='checkLen(this);'/>"+
					  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+(i+1)+"'); style='cursor: pointer;'/>&nbsp;&nbsp;";
			}
			html = html + "<input type='text' id='detail"+detailNum+"' name='detailName' class='gao_ipt2' onkeyup='checkLen(this);'/>"+
			  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+detailNum+"'); style='cursor: pointer;'/>&nbsp;"+
			  "<img src='images/gao_adde.jpg' width='14' height='14' onclick=editDetail('add'); style='cursor: pointer;'/>&nbsp;"
		}
	}
	if(show_type == 1){
		if(detailNum > 0){
			for(var i = 0;i<detailNum-1;i++){
				html = html + "<li style='list-style:none'><input type='text' id='detail"+(i+1)+"' name='detailName' class='gao_ipt2' style='width:400px;' onkeyup='checkLen(this);'/>"+
					  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+(i+1)+"'); style='cursor: pointer;'/>&nbsp;&nbsp;</li>";
			}
			html = html + "<li style='list-style:none'><input type='text' id='detail"+detailNum+"' name='detailName' class='gao_ipt2' style='width:400px;' onkeyup='checkLen(this);'/>"+
			  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+detailNum+"'); style='cursor: pointer;'/>&nbsp;"+
			  "<img src='images/gao_adde.jpg' width='14' height='14' onclick=editDetail('add'); style='cursor: pointer;'/>&nbsp;</li>"
		}
	}
	$("#detailList").append(html);
}
function editDetail(type,id){
	var detailNameList = document.getElementsByName("detailName");
	var show_type = checkShowType();
	var html = "";
	var detailNames = new Array();
	if(type == "del"){
		if(detailNameList.length != 0){
			for ( var i = 0; i < detailNameList.length; i++) {
				if(i != (id-1)){
					var detailName = detailNameList[i].value;
					detailNames.push(detailName);
				}
			}
		}
		var detailNum = detailNameList.length - 1;
	}
	if(type == "add"){
		if(detailNameList.length != 0){
			for ( var i = 0; i < detailNameList.length; i++) {
					var detailName = detailNameList[i].value;
					detailNames.push(detailName);
			}
		}
		var detailNum = detailNameList.length + 1;
	}
	$("#detailList").html("");
	if(show_type == 0){
		if(detailNum > 0){
			for(var i = 0;i<detailNum-1;i++){
				html = html + "<input type='text' id='detail"+(i+1)+"' name='detailName' class='gao_ipt2' onkeyup='checkLen(this);'/>"+
					  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+(i+1)+"'); style='cursor: pointer;'/>&nbsp;&nbsp;";
			}
			html = html + "<input type='text' id='detail"+detailNum+"' name='detailName' class='gao_ipt2'/>"+
			  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+detailNum+"'); style='cursor: pointer;' onkeyup='checkLen(this);'/>&nbsp;"+
			  "<img src='images/gao_adde.jpg' width='14' height='14' onclick=editDetail('add'); style='cursor: pointer;'/>&nbsp;"
		}
	}
	if(show_type == 1){
		if(detailNum > 0){
			for(var i = 0;i<detailNum-1;i++){
				html = html + "<li style='list-style:none'><input type='text' id='detail"+(i+1)+"' name='detailName' class='gao_ipt2' style='width:400px;' onkeyup='checkLen(this);'/>"+
					  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+(i+1)+"'); style='cursor: pointer;'/>&nbsp;&nbsp;</li>";
			}
			html = html + "<li style='list-style:none'><input type='text' id='detail"+detailNum+"' name='detailName' class='gao_ipt2' style='width:400px;' onkeyup='checkLen(this);'/>"+
			  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+detailNum+"'); style='cursor: pointer;'/>&nbsp;"+
			  "<img src='images/gao_adde.jpg' width='14' height='14' onclick=editDetail('add'); style='cursor: pointer;'/>&nbsp;</li>"
		}
	}
	$("#detailList").append(html);
	if(detailNameList.length != 0){
		for ( var z = 0; z < detailNameList.length; z++) {
			if(detailNames[z] == null && detailNames[z] == undefined){
				detailNameList[z].value = "";
			}else{
				detailNameList[z].value = detailNames[z];
			}
		}
	}
}
function cancel(){
	window.location.href = path + "/pages/vote/vote_theme_list.jsp";
}
//监听保存按钮
function submitItemForm(themeId){
	var item_name = document.getElementById("item_name").value;
	var detailNameList = document.getElementsByName("detailName");
	var detailNames = new Array();
	if(item_name == ""){
		alert("请输入投票项名称！");
		return;
	}
	var item_type = checkItemType();
	if(item_type != 2){
		if(detailNameList.length == 0){
			alert("请配置投票项！");
			return;
		}else{
			for ( var i = 0; i < detailNameList.length; i++) {
				var detailName = detailNameList[i].value;
				detailNames.push(detailName);
			}
		}
	}
	var options = {
		type : 'post',
		data : {"themeId":themeId,"detailNames":detailNames},
		success : function(data) {
			if (data.jsonResult.resultCode === 0) {
				$("#detailList").html("");
				$("#itemform")[0].reset();
				$("#itemUL").html("");
				location.href = location.href;
			} else {
				alert(decodeURIComponent(data.jsonResult.resultMsg, "utf-8"));
			}
		},
		error : function() {
			alert("保存失败");
		}
	}
	if (options != null) {
		$("#itemform").ajaxSubmit(options);
	}
}
function delItem(itemId){
	AjaxDoPost(path+"/cms/vote/item/deleteByItemId.json","itemId=" + JSON.stringify(itemId),true,function(){
		location.href = location.href;
	},null);
}
function editItem(itemId){
	AjaxDoPost(path+"/cms/vote/item/findByItemId.json","itemId=" + JSON.stringify(itemId),true,function(data){
		var item = data.resultData;
		document.getElementById("id").value = item.id;
		document.getElementById("sort").value = item.sort;
		document.getElementById("theme_id").value = item.theme_id;
		document.getElementById("item_name").value = item.item_name;
		var item_type = document.getElementsByName("item_type");
		for ( var i = 0; i < item_type.length; i++) {
			var type = item_type[i].value;
			if(type == item.item_type){
				item_type[i].checked = true;
			}else{
				item_type[i].checked = false;
			}
		}
		var show_type = document.getElementsByName("show_type");
		for ( var j = 0; j < show_type.length; j++) {
			var type = show_type[j].value;
			if(type == item.show_type){
				show_type[j].checked = true;
			}else{
				show_type[j].checked = false;
			}
		}
		if(item.cmsVoteDetails.length!=0){
			document.getElementById("detailNum").value = item.cmsVoteDetails.length;
			setDetail();
			var detailList = item.cmsVoteDetails;
			for ( var z = 0; z < detailList.length; z++) {
				var detail = detailList[z];
				document.getElementById("detail"+(z+1)).value = detail.detail_name;
			}
		}
		scroll(0,0);
	},null);
}
function checkItemType(){
	var item_type = document.getElementsByName("item_type");
	for(var i=0;i<item_type.length;i++)
	{
	     if(item_type[i].checked){
	      return item_type[i].value;
	     }
	}
}
function checkShowType(){
	var show_type = document.getElementsByName("show_type");
	for(var i=0;i<show_type.length;i++)
	{
	     if(show_type[i].checked){
	      return show_type[i].value;
	     }
	}
}
function changeItem(){
	var show_type = document.getElementsByName("show_type");
	for(var i=0;i<show_type.length;i++)
	{
	   show_type[i].disabled = true;
	}
	document.getElementById("detailNum").disabled = true;
	document.getElementById("imgSC").disabled = true;
}
function unChangeItem(){
	var show_type = document.getElementsByName("show_type");
	for(var i=0;i<show_type.length;i++)
	{
	   show_type[i].disabled = false;
	}
	document.getElementById("detailNum").disabled = false;
	document.getElementById("imgSC").disabled = false;
}
function changeShowType0(){
	var detailNameList = document.getElementsByName("detailName");
	var detailNum = detailNameList.length;
	var detailNames = new Array();
	if(detailNameList.length != 0){
		for ( var j = 0; j < detailNameList.length; j++) {
				var detailName = detailNameList[j].value;
				detailNames.push(detailName);
		}
	}
	$("#detailList").html("");
	var html = "";
	if(detailNum > 0){
		for(var i = 0;i<detailNum-1;i++){
			html = html + "<input type='text' id='detail"+(i+1)+"' name='detailName' class='gao_ipt2'/>"+
				  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+(i+1)+"'); style='cursor: pointer;'/>&nbsp;&nbsp;";
		}
		html = html + "<input type='text' id='detail"+detailNum+"' name='detailName' class='gao_ipt2'/>"+
		  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+detailNum+"'); style='cursor: pointer;'/>&nbsp;"+
		  "<img src='images/gao_adde.jpg' width='14' height='14' onclick=editDetail('add'); style='cursor: pointer;'/>&nbsp;"
	}
	$("#detailList").append(html);
	if(detailNameList.length != 0){
		for ( var z = 0; z < detailNameList.length; z++) {
			if(detailNames[z] == null && detailNames[z] == undefined){
				detailNameList[z].value = "";
			}else{
				detailNameList[z].value = detailNames[z];
			}
		}
	}
}
function changeShowType1(){
	var detailNameList = document.getElementsByName("detailName");
	var detailNum = detailNameList.length;
	var detailNames = new Array();
	if(detailNameList.length != 0){
		for ( var j = 0; j < detailNameList.length; j++) {
				var detailName = detailNameList[j].value;
				detailNames.push(detailName);
		}
	}
	$("#detailList").html("");
	var html = "";
	if(detailNum > 0){
		for(var i = 0;i<detailNum-1;i++){
			html = html + "<li style='list-style:none'><input type='text' id='detail"+(i+1)+"' name='detailName' class='gao_ipt2' style='width:400px;'/>"+
				  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+(i+1)+"'); style='cursor: pointer;'/>&nbsp;&nbsp;</li>";
		}
		html = html + "<li style='list-style:none'><input type='text' id='detail"+detailNum+"' name='detailName' class='gao_ipt2' style='width:400px;'/>"+
		  "<img src='images/gao_del.png' width='13' height='14' onclick=editDetail('del','"+detailNum+"'); style='cursor: pointer;'/>&nbsp;"+
		  "<img src='images/gao_adde.jpg' width='14' height='14' onclick=editDetail('add'); style='cursor: pointer;'/>&nbsp;</li>"
	}
	$("#detailList").append(html);
	if(detailNameList.length != 0){
		for ( var z = 0; z < detailNameList.length; z++) {
			if(detailNames[z] == null && detailNames[z] == undefined){
				detailNameList[z].value = "";
			}else{
				detailNameList[z].value = detailNames[z];
			}
		}
	}
}
function checkLen(obj) {  

	var maxChars = 55;//最多字符数  

	if (obj.value.length > maxChars)  obj.value = obj.value.substring(0,maxChars);  

	var curr = maxChars - obj.value.length;  

	document.getElementById("count").innerHTML = curr.toString(); 
}