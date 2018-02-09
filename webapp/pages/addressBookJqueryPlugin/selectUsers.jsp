<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>

<style type="text/css">

ul[role='listbox']>li.k-button{
display:none;
}

</style>
</head>
<body style='overflow-x:hidden'>

	<div class="book-full">
		<div class="bookBody">
			<div class="bookContent">
				<div class="book-contacts-from">
					<div class="book-contacts-listTitle" ><span>所有联系人</span>
					   <span id="btn-group-show-type">
					   <!-- 
					   		 <button id="btn-showFlat" class="k-button"><span class="icon-reorder"/></button>
					   		 <button id="btn-showHierarchical"class="k-button"><span class="icon-align-left"/></button>
					    -->
					  
					   </span>
					</div>
					<div class="book-contacts-input book-contacts-border"style="width:100%">
						 <span class="k-textbox k-space-right" style="width:100%">
                                <select id="multiselect" multiple="multiple" placeholder="请输入用户帐号或姓名..."></select>
                                <a href="#" class="k-icon k-i-search">&nbsp;</a>
                        </span>
					</div>
					<div class="book-contacts-list  book-contacts-border" style="overflow-y:auto;position:relative;">
					   <div id="treeviewFlat" style="position:relative;overflow-y:hidden;" ></div>
					   <div id="treeviewHierarchical" style="display:none;position:relative;overflow-y:hidden;"></div>
					</div>
				</div>
				<i class="book-contacts-attr icon-double-angle-right icon-large"></i>
				<div class="book-contacts-to ">
					<div class="book-contacts-listTitle">
					   <a href="javascript:void(0)" id="removeAllId" class="book-contacts-to-removeAll book-txt-link " hidefocus="true" style="display: none;">移除全部</a>
						选中用户
						<span id="spnSelectContactSelectedNum">(0)</span>
					</div>
					<div class="book-contacts-selectedList book-scroll">
					   
					</div>
				</div>
			</div>

		</div>
		<div class="book-foot">
			<div class="book-foot-btns">
				<div class="book-mainBtn book-btn">
					<a class="k-button .book-btn-text"
						style="background-color:#459830;color:white" id="confirmAddressBook">确&nbsp认</a>
				</div>
				<div class="book-btn">
					<a class="k-button .nui-btn-text" id="cancleAddressBook">取&nbsp消</a>
				</div>
			</div>
		</div>
	</div>


<script>


var groupDataSource = null;
var orgDataSource = null;
var adrsBookTree  = null;
var serviceRoot = null;
var searchSelector = null;
var adrBookObj = null;
//声明查询条件对象
var queryObj={
  id:null,
  type:null
};


$(document).ready(function(){
	getAddressBookInstData();
	buildQueryObj();
	buildShowTypeBtns();
	initSearchBox();
	initFlatBookDataSource();
	initHierarchicalBookDataSource();
	initShowTree(); 
	refreshSelectList();
});

function getBtns(type)
{
	if(type=="ORG")
		return '<button id="btn-showHierarchical"class="k-button"><span class="icon-align-left"/></button>&nbsp';
	else if(type=="GROUP")
		return  '<button id="btn-showFlat" class="k-button"><span class="icon-reorder"/></button>&nbsp';
	else
		alert("buildShowTypeBtns：错误的参数，参数应该指定为 ORG或GROUP！type:["+type+"]");

}

function getRadio()
{
	return "<input type='radio' name='ORG_GROUP' value='ORG'>机构</input><input type='radio' name='ORG_GROUP'  value='GROUP'>组</input>";	
}

function buildQueryObj()
{
	if(adrBookObj.assignOrgId!=null&&adrBookObj.assignOrgId!="")
	{
		 queryObj.type = "ASSIGN_ORG";
		 queryObj.id = adrBookObj.assignOrgId;
	}
	else
	{
		  queryObj.type = null;
		  queryObj.id = null;
	}
}

function buildShowTypeBtns()
{
	adrBookObj.showType = adrBookObj.showType==null?["ORG","GROUP"]:adrBookObj.showType;
	var showTypeBtnHtml = "";
	
	showTypeBtnHtml += getRadio();
	
	if(adrBookObj.showType.length>1)
	{
		$("#btn-group-show-type").html(showTypeBtnHtml);
	
		//监听 机构-组分类 radio 按键事件
		$("input:radio[name='ORG_GROUP']").click(function(e){
			var checkval = $(e.target).val();
			if(checkval=='GROUP'){
			   buildQueryObj();
			   serviceRoot = adrBookObj.groupServiceRoot;
			   adrsBookTree.setDataSource(groupDataSource);
			}
			else if(checkval=='ORG'||checkval==null){
			   buildQueryObj();
			    serviceRoot = adrBookObj.orgServiceRoot;
			    adrsBookTree.setDataSource(orgDataSource);
			 }
		});
	}
	
}


function initSearchBox()
{
	searchSelector = $("#multiselect").kendoMultiSelect({
       dataTextField: "text",
       dataValueField: "id",
       itemTemplate:"#=setItemTemplate(text,userInfo)#",
       filter: "contains",
       delay: 800,
       autoClose: false,
       select: function(e) {
           var dataItem = this.dataSource.view()[e.item.index()];
           //console.log("dataItem:"+JSON.stringify(dataItem));
           adrBookObj.selectUserLinkMap.put(dataItem.id,dataItem);
           refreshSelectList();
         },
       dataSource: {
           serverFiltering: true,
           transport: {
               read: function(options) {
                   //console.log("options: "+JSON.stringify(options));
                   var filterObj = new Object();
                   var data = options.data;
                   if( (data.filter!=null&& data.filter!=undefined)||(adrBookObj.assignOrgId!=null&& adrBookObj.assignOrgId!=undefined))
                    {
                       filterObj.assignOrgId = adrBookObj.assignOrgId!=null?adrBookObj.assignOrgId:"";
                       //filterObj.name =  data.filter["filters"][0]!=null? data.filter["filters"][0]["field"]:null;
                       //filterObj.operator = data.filter["filters"][0]!=null?data.filter["filters"][0]["operator"]:null;
                       if(data.filter!=null&& data.filter!=undefined)
                        filterObj.filterStr = data.filter["filters"][0]!=null?data.filter["filters"][0]["value"]:"";
                    }
                  
                  //console.log("after first encoding : "+encodeURI(adrBookObj.searchUserRoot+"?filter="+JSON.stringify(filterObj)));
                  //console.log("after second encoding :"+encodeURI(encodeURI(adrBookObj.searchUserRoot+"?filter="+JSON.stringify(filterObj))));
                    $.ajax({
                    	url: adrBookObj.searchUserRoot,
                        //url: encodeURI(encodeURI(adrBookObj.searchUserRoot+"?filter="+JSON.stringify(filterObj))),
                        data:"filter="+JSON.stringify(filterObj),
                        dataType: "json", 
                        type:"POST",
                        success: function(result) {
                        	var _result = new Array();
                           //console.log("result before: "+JSON.stringify(result));
                            for (var i = 0; i < result.length; i++) {
                                if(!adrBookObj.selectUserLinkMap.isExist(result[i].id))
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
           }
       }
     }).data("kendoMultiSelect");
}


function initFlatBookDataSource()
{
	groupDataSource = new kendo.data.HierarchicalDataSource({
          transport: {
              read: function(options) {
                     //console.log("queryObj :"+JSON.stringify(queryObj));                 
                      $.ajax({
                          url: adrBookObj.groupServiceRoot + "?id="+queryObj.id+"&type="+queryObj.type+"&isShowGroup="+adrBookObj.isShowGroup,
                          dataType: "json", 
                          success: function(result) {
                            // notify the data source that the request succeeded
                            options.success(result);
                          },
                          error: function(result) {
                            // notify the data source that the request failed
                            options.error(result);
                          }
                      });
              }
          },
          schema: {
              data : function(response)
              {
                  return eval(response);
              },
              model: {
                  id: "id",
                  hasChildren: "hasChildren"
              }
          }
      });
}

function initHierarchicalBookDataSource()
{
	orgDataSource = new kendo.data.HierarchicalDataSource({
        transport: {
            read: function(options) {
                   ////console.log("queryObj :"+JSON.stringify(queryObj));                 
                    $.ajax({
                        url: adrBookObj.orgServiceRoot + "?id="+queryObj.id+"&type="+queryObj.type+"&isShowGroup="+adrBookObj.isShowGroup,
                        dataType: "json", 
                        success: function(result) {
                          // notify the data source that the request succeeded
                          options.success(result);
                        },
                        error: function(result) {
                          // notify the data source that the request failed
                          options.error(result);
                        }
                    });
            }
        },
        schema: {
            data : function(response)
            {
                return eval(response);
            },
            model: {
                id: "id",
                hasChildren: "hasChildren"
            }
        }
    });
}

function getFirstDataSource()
{
	if(adrBookObj.showType[0]!=null&&adrBookObj.showType[0]=="ORG")
	{
		 serviceRoot = adrBookObj.orgServiceRoot;
		 return orgDataSource;
	}
		
	else if(adrBookObj.showType[0]!=null&&adrBookObj.showType[0]=="GROUP")
	{
		serviceRoot = adrBookObj.groupServiceRoot;
		return groupDataSource;
	}
	else
		alert("getFirstDataSource：错误的参数，参数应该指定为ORG|GROUP！");
}	

function initShowTree()
{
	 //初始化选择树
	var firstDataSource = getFirstDataSource();
    adrsBookTree = $("#treeviewFlat").kendoTreeView({
        template: function(data){
            var item = data.item;
           //console.log("width:"+$("div.book-contacts-list").css('width')+"\n");
            var width = parseFloat($("div.book-contacts-list").css('width'));
            var textMaxWidth = width*0.8;
           //console.log("textMaxWidth:"+textMaxWidth+"\n");
            var temphtml ="<span title='"+item.text+"' style='width:100%;'>";
            
            if(item.hasChildren&&item.type=='ORG'&&item.userCount!=null)
                temphtml += "<i class='icon-home icon-large left-position'></i><span data-type='"+item.type+"' data-id='"+item.id+"' title='单击选择当前组或机构的全部用户'class='chooseAll'>("+item.userCount+")</span>";
           else if(item.type=='GROUP')
                    temphtml += "<i class='icon-group icon-large left-position'></i><span data-type='"+item.type+"' data-id='"+item.id+"' title='单击选择当前组或机构的全部用户'class='chooseAll'>("+item.userCount+")</span>";
           else if(item.type=="ORG_USER"||item.type=="GROUP_USER")
                temphtml +="<i class='icon-user icon-large left-position' ></i>";
           else if(item.type=="GROUP_TYPE")
                    temphtml +="<i class='icon-group icon-large left-position'></i>";
           else if(item.type=="ORG")
                        temphtml +="<i class='icon-home icon-large left-position'></i>";
                        
             
            if(item.type=="GROUP"||item.type=="GROUP_TYPE"||item.type=="ORG")
                 temphtml +="<span style='word-wrap:break-word; word-break:break-all;display:block;max-width:100%'>"+item.text+"</span>";
            else if(item.type=="ORG_USER"||item.type=="GROUP_USER")
                temphtml +="<span style='word-wrap:break-word; word-break:break-all;'>"+item.text+"</span>";   
                
             temphtml +="</span>";
            
          //console.log("tempHtml:"+temphtml);
           return temphtml;
          
        },
        dataSource: firstDataSource,
        select : function(e)
        {
        
           // alert(JSON.stringify(this.dataItem(e.node).className));
            //alert("obj:"+JSON.stringify(this.dataItem(e.node).obj));
            //当用户被选中时,添加到 adrBookObj.selectUserLinkMap
            if ((this.dataItem(e.node).type=='GROUP_USER'||this.dataItem(e.node).type=='ORG_USER')&&this.dataItem(e.node).hasChildren == false)
            {
                var userItem =this.dataItem(e.node);
                //console.log("oneUserMap:"+adrBookObj.selectUserLinkMap.get(userItem.id));
               if(adrBookObj.selectUserLinkMap.get(userItem.id)==null)
                   adrBookObj.selectUserLinkMap.put(userItem.id,userItem);
               refreshSelectList();
            }
            if ((this.dataItem(e.node).type=='ORG'))
            {
                var orgItem =this.dataItem(e.node);
                //TODO 支持某个单位下的搜索
                //assignOrgId = orgItem.id;
                
            }
        },
        expand: function(e) {
            var item  = this.dataItem(e.node);
            queryObj.id= item.id;
            queryObj.type= item.type;
            //console.log("Expand node:",JSON.stringify( this.dataItem(e.node)));
          }
    }).data("kendoTreeView");
 
   
   
    //全部用户加入选择类别contactToMap
    $(document).on("click", ".chooseAll", function(e) {
        e.preventDefault();
        //console.log("type: "+$(this).data("type"));
        //console.log("id: "+$(this).data("id"));
        queryObj.id=$(this).data("id");
        queryObj.type=$(this).data("type");
        //console.log("serviceRoot: "+serviceRoot);
        $.ajax({
            url: serviceRoot + "?id="+queryObj.id+"&type="+queryObj.type+"&isShowGroup="+adrBookObj.isShowGroup,
            dataType: "json", 
            async: true,
            success: function(result) {
              $.each(result,function(i,item){
                 //console.log("type: "+item.type); 
                 if(item.type=="GROUP_USER"||item.type=="ORG_USER")
                     adrBookObj.selectUserLinkMap.put(item.id,item);
              });
              refreshSelectList();
            },
            error: function(result) {
              //console.log("Get users error: "+ JSON.stringify(result));
            }
        });
    });
}

function getAddressBookInstData()
{
	var pluginId = '${param.pluginId}';
	if(!(pluginId)||pluginId=='')
		showErrorMsg("获取不到地址本实例数据，pluginId：["+pluginId+"]");
	adrBookObj = $("#"+pluginId).data("xjjAddressBook");
	//console.log("adrBookObj: "+$("#"+pluginId).data("xjjAddressBook"));
	if(!(adrBookObj))
		showErrorMsg("获取不到地址本实例数据，pluginId：["+pluginId+"]");
}


//刷新选择列表
function refreshSelectList()
{
    var selectListHtml =" ";
    adrBookObj.selectUserLinkMap.each(function(key,value,index){
        //console.log('key is ' + key +' and value is' + value.userInfo);
        var userInfo =  value.userInfo;
        selectListHtml+="<div class='book-item'><span onclick=removeUser('"+key+"') class='book-txt-link js-remove' style='cursor: pointer;'>移除</span>";
        if(userInfo.orgName)
         	selectListHtml+="<span class='book-contacts-selectedList-name'>"+ userInfo.userName+"&nbsp("+userInfo.orgName+")</span></div>";
       	else
    	{
       		selectListHtml+="<span class='book-contacts-selectedList-name'>"+ userInfo.userName+"</span></div>";
    	}
    });
    if(adrBookObj.selectUserLinkMap.size()>0)
        $("#removeAllId").css('display', 'block');
    $("#spnSelectContactSelectedNum").html("("+adrBookObj.selectUserLinkMap.size()+")");
    $(".book-contacts-selectedList").html(selectListHtml);
    
    
}

function resetUserSelector()
{
	//更新搜索框被选用户
    var userIds = adrBookObj.selectUserLinkMap.keys;
    var multiSelect = $("#multiselect").data("kendoMultiSelect");
    multiSelect.value(userIds);
}

//从选中列表删除用户
function removeUser(id)
{
    //console.log("remove user "+id);
    adrBookObj.selectUserLinkMap.remove(id);
    var userIds = adrBookObj.selectUserLinkMap.keys;
   
    //这条语句必须有，否则已经删除的人还会出现在多选框内
  
    resetUserSelector();
    refreshSelectList();
}

$("#removeAllId").click(function(){
    adrBookObj.selectUserLinkMap.removeAll();
    resetUserSelector();
    refreshSelectList();
});

$("#cancleAddressBook").click(function(){
    closewindow("div","地址本");
});

$("#confirmAddressBook").click(function(){
    closewindow("div","地址本");
    setSelectUser(adrBookObj.id);
});
</script>

</body>




</html>