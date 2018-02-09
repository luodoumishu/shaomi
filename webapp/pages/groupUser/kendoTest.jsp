<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/comm/kendo_css.jsp"%>
<%@ include file="/comm/kendo_js.jsp"%>

<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="path/to/font-awesome/css/font-awesome.min.css">

</head>

<body>

	<div id="treeview"></div>
    <div id="groupTreeview"></div>
	<script>
	var orgTree = null;
	  var treeDataSource = null;
	var groupTreee = null;
	 var groupTreeDataSource = null;
	 
        $(document).ready(function()
        {
            //initDepartTree();
           initGroupTree();
    
         
        });

        function  initGroupTree()
        {
            getGroupTreeDataSource();
            $("#groupTreeview").kendoTreeView(
           {
               dataSource : groupTreeDataSource,
               select : function(e)
               {
                   //alert(JSON.stringify(this.dataItem(e.node).className));
                  //alert(JSON.stringify(this.dataItem(e.node)));
                   //alert("id:"+this.dataItem(e.node).selfId);
                  // alert("text:"+this.dataItem(e.node).text);
                  // alert("obj:"+JSON.stringify(this.dataItem(e.node).obj));
                 
               },
               loadOnDemand: false
           });
        }
        
        function getGroupTreeDataSource()
        {
            groupTreeDataSource = new kendo.data.HierarchicalDataSource(
            {
                transport :
                {
                    read :
                    {
                        url : "${ctx}/Zgroup/getGroupTreeDataSource.json",
                        dataType : "json"
                    }
                },
                animation :
                {
                    expand : true
                },
                schema :
                {
                    data : function(response)
                    {
                        //alert(JSON.stringify(response));
                        // alert(JSON.stringify(response.resultData));
                        return response.resultData;
                    },
                    model :
                    {
                        id : "selfId",
                        dataTextField : "text",
                        children : "child",
                        hasChildren : function(item)
                        {
                            //alert(JSON.stringify(item));
                            return item.child.length != 0;
                        }
                    }
                },
                requestEnd: requestEndHandler

            });

        }
        
        
        
        function getTreeDataSource()
        {
            treeDataSource = new kendo.data.HierarchicalDataSource(
            {
                transport :
                {
                    read :
                    {
                        url : "${ctx}/Zgroup/getOrgTreeTest.json",
                        dataType : "json"
                    }
                },
                animation :
                {
                    expand : true
                },
                schema :
                {
                    data : function(response)
                    {
                        // alert(JSON.stringify(response));
                        // alert(JSON.stringify(response.resultData));
                        return response.resultData;
                    },
                    model :
                    {
                        id : "selfId",
                        dataTextField : "text",
                        children : "child",
                        hasChildren : function(item)
                        {
                            //alert(JSON.stringify(item));
                            return item.child.length != 0;
                        }
                    }
                },
                requestEnd: requestEndHandler

            });
        }
        function initDepartTree()
        {
            getTreeDataSource();
             $("#treeview").kendoTreeView(
            {
                dataSource : treeDataSource,
                select : function(e)
                {
                    
                   //alert(JSON.stringify(this.dataItem(e.node)));
                    //alert("id:"+this.dataItem(e.node).selfId);
                    //alert("text:"+this.dataItem(e.node).text);
                   // alert("obj:"+JSON.stringify(this.dataItem(e.node).obj));
                  
                },
                loadOnDemand: false
            });
             

        }
        
        function requestEndHandler()
        {
            setTimeout("expandNodes()");
        }

     

        function expandNodes()
        {
            $("#groupTreeview").data("kendoTreeView").expand(".k-item");
            //$("#treeview").data("kendoTreeView").expand(".k-item");
        }
        
    </script>

</body>
</html>