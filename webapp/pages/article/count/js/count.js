function EchartsData(parentId,startDate,endDate){
	$.ajax({
		url:path + "cms/article/getDataByOrgAndArticle.json",
		type : "POST",
		async:false,
		data:{"parentId":parentId,"startDate":startDate,"endDate":endDate},
		success:function(result){
			if(result != null){
				result = result.resultData;
				var length = result.length;
				var orgObj = [];
				var numObj = [];
				if(length > 0){
					for (var i = 0; i < length; i++) {
						var item = result[i];
						orgObj.push(item.org.name);
						numObj.push(item.num);
					}
					EchartsInit(orgObj,numObj);
				}else{
					$("#barChart").html("<span style='font-size:14px;'>因为该部门暂无子部门,所以柱状图暂无</span>");
					$("#lineChart").html("<span style='font-size:14px;'>因为该部门暂无子部门,所以折线图暂无</span>");
					$("#pieChart").html("<span style='font-size:14px;'>因为该部门暂无子部门,所以饼状图暂无</span>");
				}
				
			}
		},
		error:function(result){
			alert("查询发文量失败");
			return;
		}
	});
}

function EchartsInit(orgObj,numObj){
	// 路径配置
	require.config({
	    paths: {
	        echarts: path + 'assets/echarts-2.2.7/build/dist'
	    }
	});
	// 使用
	require(
	    [
	        'echarts',
	        'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
	        'echarts/chart/pie',
	        'echarts/chart/line'
	    ],
	    function (ec) {
	        // 基于准备好的dom，初始化echarts图表
	        var barChart = ec.init(document.getElementById('barChart')); 
	        var lineChart = ec.init(document.getElementById('lineChart'));
	        var pieChart = ec.init(document.getElementById('pieChart'));
	        
	        //柱状
			var bar_option = {
			    title : {
			        text: '各部门发表文章',
			        subtext: '柱状图'
			    },
			    tooltip : {
			        trigger: 'item'
			    },
			    legend: {
			        data:['发文量']
			    },
			    toolbox: {
			        show : false,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : false,
			    xAxis : [
			        {
			            type : 'category',
			            data : orgObj
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'发文量',
			            type:'bar',
			            data:numObj,
			            markLine : {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            },
			            barWidth : '50'
			        }
			    ]
			};
			                                    
	        //折线
	        var line_option = {
	       		 	title : {
		       		 	text: '各部门发表文章',
				        subtext: '折线图'
				    },
	        	    tooltip : {
	        	        trigger: 'item'
	        	    },
	        	    legend: {
	        	        data:['发文量']
	        	    },
	        	    toolbox: {
	        	        show : false,
	        	        feature : {
	        	            mark : {show: true},
	        	            dataView : {show: true, readOnly: false},
	        	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
	        	            restore : {show: true},
	        	            saveAsImage : {show: true}
	        	        }
	        	    },
	        	    calculable : false,
	        	    xAxis : [
	        	        {
	        	            type : 'category',
	        	            boundaryGap : false,
	        	            data : orgObj
	        	        }
	        	    ],
	        	    yAxis : [
	        	        {
	        	            type : 'value'
	        	        }
	        	    ],
	        	    series : [{
	        	            name:'发文量',
	        	            type:'line',
	        	            data:numObj
	        	    }]
	        	};
	        
	        //饼状
	        var pie_option = {
			    title : {
			    	text: '各部门发表文章',
			        subtext: '饼状图',
			        x:'left'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)",
			        showDelay : 20
			    },
			    legend: {
			        orient : 'vertical',
			        x : '70%',
			        y : '10%',
			        data:['发文量']
			    },
			    toolbox: {
			        show : false,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {
			                show: true, 
			                type: ['pie', 'funnel'],
			                option: {
			                    funnel: {
			                        x: '25%',
			                        width: '50%',
			                        funnelAlign: 'left',
			                        max: 1548
			                    }
			                }
			            },
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : false,
			    series : [
			        {
			            name:'发文量',
			            type:'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:(function (){
			            	var res = [];
			                var len = numObj.length;
			                for (var i = 0; i < len; i++) {
			                	 res.push({
			                         name: orgObj[i],
			                         value: numObj[i]
			                     });
							}
			                return res;
			            })()
			        }
			    ]
			};
	        
	        // 为echarts对象加载数据 
	        barChart.setOption(bar_option);
	        lineChart.setOption(line_option);
	        pieChart.setOption(pie_option);
	    }
	);
}

function LoadItemTree(){
	JsonPostForWaiting({
        Url: path + "Zorganize/listOrgs.json",
        WaittingText: "正在加载单位...",
        AimDiv: "#LeftLayout",
        Success: function (ResultMsg, Parameter) {
                var data="";
                if(ResultMsg.ResultMsg.resultData!=null){
                   data = ResultMsg.ResultMsg.resultData;
                }
            //if(ResultMsg.ResultMsg.resultData!=null){
                var TreeData = TransformToTreeFormat(data,
                "id", "parentId", "ChildLists");

                var ParentTree = new Array();
                var ParentData = new Object;
                ParentData.id = "0";
                ParentData.parentId = 0;
                ParentData.name = "单位列表";
                ParentData.ChildLists = TreeData;
                ParentTree.push(ParentData);

                var jgSource = new kendo.data.HierarchicalDataSource({
                    data: ParentTree,
                    schema: {
                        model: {
                        	children: "ChildLists"
                        }
                    }
                });

                selectSource = new kendo.data.HierarchicalDataSource({
                    data: ParentTree,
                    schema: {
                        model: {
                            children: "ChildLists"
                        }
                    }
                });
                
                itemTree.setDataSource(jgSource);
                
                itemTree.expand(".k-item");
                itemTree.expand(".k-item");
                itemTree.expand(".k-item");
                itemTree.expand(".k-item");
            //}
        },
        Error: function (ResultMsg, Parameter) {
        	DetailAlertDialog({
                Title: "出错信息",
                Message: "加载单位失败！",
                DetailMessage: GetErrorMsg(ResultMsg),
                Icon: "k-ext-error"
            });
        }
    });
}

//展开机构树
function ExpandItemTree() {
    itemTree.expand(".k-item");
}

//收缩机构树
function CollapseItemTree() {
    itemTree.collapse(".k-item");
}

/**
 * 获取当前月的第一天
 */
function getCurrentMonthFirst(){
	 var date=new Date();
	 date.setDate(1);
	 var year = date.getFullYear();
	 var month = date.getMonth()+1;
	 var day = date.getDate();
	 var date_ = year+"-"+month+"-"+day;
	 return date_;
}
/**
 * 获取当前月的最后一天
 */
function getCurrentMonthLast(){
	 var date=new Date();
	 var currentMonth=date.getMonth();
	 var nextMonth=++currentMonth;
	 var nextMonthFirstDay=new Date(date.getFullYear(),nextMonth,1);
	 var oneDay=1000*60*60*24;
	 var lasTDate = new Date(nextMonthFirstDay-oneDay);
	 var year = lasTDate.getFullYear();
	 var month = lasTDate.getMonth()+1;
	 var day = lasTDate.getDate();
	 var date_ = year+"-"+month+"-"+day;
	 return date_;
}