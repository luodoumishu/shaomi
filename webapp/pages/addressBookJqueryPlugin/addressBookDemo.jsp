<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>


<!DOCTYPE html>
<html>
<head>
<%@ include file="/comm/kendo_css.jsp"%>
<%@ include file="/comm/kendo_js.jsp"%>

<link href="${ctx}/assets/ace/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/pages/addressBookJqueryPlugin/css/adrsBook.css" rel="stylesheet">
<script src="${ctx}/assets/_base/js/linkMap.js"></script>
<script src="${ctx}/assets/_base/js/uuid.js"></script>
<!-- 
<script src="${ctx}/pages/addressBookJqueryPlugin/js/addressBook.js"></script>
 -->

<script src="${ctx}/pages/addressBookJqueryPlugin/js/addressBook_jqp.js"></script>

<style type="text/css">

</style>
<title>地址本（JQUERY版本）</title>
</head>

<body >
	============================================================================================<br>
	<p>地址本文档说明：<p>
	&nbsp初始化：地址本通过JQUERY选择器选择需要初始化为地址本控件的DOM对象（建议最好为DIV），选择器返回的对象只能为一个（即$.size()==1)<br>
	&nbsp <br>
	============================================================================================<br>
	addressBook 1:<br>
	
	<div class="usersMultiSelectorOutter1"></div>
	
	
	============================================================================================<br>
     <div>
     	<button id="getUserInfoBtn" class="k-button" onclick="javascript:showUserInfo('usersMultiSelectorOutter1','choosenUserInfo1')">获取被选用户信息</button>
     	<div id="choosenUserInfo1">
     		
     	</div>
     </div>
	============================================================================================<br>
	addressBook 2:<br>
	<div class="usersMultiSelectorOutter2"></div>
	============================================================================================<br>
     <div>
     	<button id="getUserInfoBtn2" class="k-button" onclick="javascript:showUserInfo('usersMultiSelectorOutter2','choosenUserInfo2')">获取被选用户信息</button>
     	<div id="choosenUserInfo2">
     		
     	</div>
     </div>
	
	<script>
	
	//获取选中的用户信息
	function showUserInfo(pluginId,outputId)
	{
		//console.log("pluginId: "+pluginId);
		var dataObj = $('.'+pluginId).data("xjjAddressBook");
		var userInfoHtml ="选中用户信息列表：<br><br>";
		dataObj.selectUserLinkMap.each(function(key,value,index){
		    userInfoHtml += "用户【"+(index+1)+"】ID:"+value.id+"<br>"   
			userInfoHtml += "姓名: "+value.userInfo.userName+"<br>";
			userInfoHtml += "帐号: "+value.userInfo.userAccount+"<br>";
			userInfoHtml += "所属机构: "+value.userInfo.orgName+"<br>";
			userInfoHtml += "所属ID: "+value.userInfo.orgId+"<br>";
			userInfoHtml += "职位: "+value.userInfo.oaduty+"<br><br>";
		});
		$("#"+outputId).html(userInfoHtml);
	}
	
	
	
	$(document).ready(function() {
		$('.usersMultiSelectorOutter1').addressBook({
				//用户多选框ID（必填）
				userMultiSelectorId : "usersMultiSelector1",
				//被选用户信息容器
				selectUserLinkMap : new LinkMap(),
				//扁平型数据源地址
				flatServiceRoot :null,
				//树型数据源地址
				hierarchichalServiceRoot : null,
				//搜索框数据源地址
				searchUserRoot : null,
				//地址本展现形式 TREE 为树形结构 FLAT 为扁平型结构,Ex:["TREE","FLAT"] 或 ["FLAT","TREE"] 或 ["TREE"] 或 ["FLAT"]
				showType : null,
				//指定地址本顶级部门
				assignOrgId :null,
				isSupportPinyin :true,
				//指定是否显示组 true:显示 false：不显示
				isShowGroup : true,
				//地址选中用户改变时的回调函数
				userChangeCallBack:function(dataItems){
					$.each(dataItems, function(i, item) {
		    			//console.log("item: "+JSON.stringify(item)+"\n");
		    			//console.log("==============================================================================");
		    		});
				}
				
		});
		
		
		$('.usersMultiSelectorOutter2').addressBook({
				//用户多选框ID（必填）
				userMultiSelectorId : "usersMultiSelector2",
				//被选用户信息容器
				selectUserLinkMap : new LinkMap(),
				//扁平型数据源地址
				flatServiceRoot :null,
				//树型数据源地址
				hierarchichalServiceRoot : null,
				//搜索框数据源地址
				searchUserRoot : null,
				//地址本展现形式 ORG 为机构列表 GROUP 为组列表,Ex:["ORG","GROUP"] 或 ["GROUP","ORG"] 或 ["ORG"] 或 ["GROUP"]
				showType : ["ORG","GROUP"],
				//指定地址本顶级部门
				assignOrgId :null,
				//指定是否显示组 true:显示 false：不显示
				isShowGroup : false,
				//地址选中用户改变时的回调函数
				userChangeCallBack:function(dataItems){
					$.each(dataItems, function(i, item) {
		    			//console.log("item: "+JSON.stringify(item)+"\n");
		    			//console.log("==============================================================================");
		    		});
				}
				
		});
	
		
		//console.log("addressBook data options: "+JSON.stringify($('#usersMultiSelectorOutter1').data("opts")));
		//console.log("addressBook data options: "+JSON.stringify($('#usersMultiSelectorOutter2').data("opts"))+"\n");
		
	});
		


		
	</script>
	
</body>




</html>