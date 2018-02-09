<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<style type="text/css">
.calendar {
	text-align: center;
	vertical-align: middle;
	color: #66CCFF;
}
.schedule {
	z-index: 100;
	position: absolute;
	overflow: visible;
	display: none;
	background: #FFFF99;
	border: solid 2px #DDD;
	min-height: 100px;
	min-width: 200px;
	max-width: 300px;
	padding-top: 5px;
	padding-left: 5px;
	padding-right: 5px;
	word-wrap: break-word;
}
.content td {
	padding-right: 10px;
	margin: 5px;
	margin-bottom: 10px;
	font-size: 12px;
}
table caption {
	font-weight: bolder;
	font-size: 14px;
}
</style>
<script type="text/javascript">
		//显示日历
		function calendar(){
		//获取页面的年月
		var y=<%=session.getAttribute("startY")%>;
		var m=<%=session.getAttribute("startM")%>;
		//当月1号是周几
		e=new Date(y,m,1);
		var nowMonthFisrtDay=e.getDay();
		//显示	
		var s="<tr>";
		//今日实际日期
		var today = new Date();	

		for(var i=1-parseInt(nowMonthFisrtDay),j=0;j<42;i++,j++){
			if(j%7==0){
				s+="</tr><tr>";
			}
			var day=new Date(y,m,i);//日历日期
			var yy = day.getFullYear();
			var mm = day.getMonth();
			var dd = day.getDate();
			//判断是不是当前日期
			var isToday=  yy==today.getFullYear()&&mm==today.getMonth()&&dd==today.getDate();
			//添加颜色
			var bgColor="";
			var fontColor="";
			var tdid="";
			//日期格式20130128
		if(dd<10){
			dd="0"+dd;
		}
		mm+=1;//月从0开始 所以加1
		if(mm<10){
			mm="0"+mm;
		}
		tdid=yy+""+mm+""+dd;
		if($(".tr"+tdid).length>0){
			bgColor="#CCFFFF";
		}else{
			bgColor="";
		}
		if(isToday){
			fontColor="red";
		}else{
			fontColor="#66CCFF";
		}
		//添加42个日期
		s+="<td id="+tdid+" class='day' style='background-color:"+bgColor+";color:"+fontColor+"'>"+dd+"</td>";
	}
		s+="</tr>";
		$("#nums").html(s);
		
		var trClass="";
		var color="";
		//鼠标所在的td背景颜色改变
		$(".day").mouseover(function(){
			color=$(this).css("background-color");
			$(this).css("background-color","yellow");
		}).mouseout(function(){
			$(this).css("background-color",color);
		});
		//点击显示日程
		$(".day").click(function (e) {
			trClass=".tr"+$(this).attr("id");
				 if($(trClass).length>0){
			$("tr[class^='tr']").hide();
			$("#showSchedule").hide(300);
				  $("#showSchedule").show(300).css({left:e.pageX,top:e.pageY});
				  $(trClass).show();
				 }
	  	});
		//点击关闭按钮
		$("#scheduleclose").click(function(){
			$("tr[class^='tr']").hide();
			$("#showSchedule").hide(300);
		}).mouseover(function(){
			$(this).css("color","red");
			}).mouseout(function(){
				$(this).css("color","");
				});
		//上下月翻页
		$("font").click(function(){
			var changeMonth=0;
			if($(this).attr("id")=="preMonth"){
				changeMonth=-1;
			}else if($(this).attr("id")=="nextMonth"){
				changeMonth=1;
			}
			//获取该月日程
			 $.ajax({
				type:"GET",
				url:"${ctx}/schedule/parseScheduleXml.do",
				dateType: "html", 
				/* async : false, */
			    data: {'startY': <%=session.getAttribute("startY")%>,'startM':<%=session.getAttribute("startM") %>,'changeMonth':changeMonth,'timetip':new Date().getTime()}, 
				success:function(result){
					$("#myScheduleBg").html(result); 
				}
			});
		}); 
}
		
		//初始
		$(document).ready(function() {
			doAjaxPost("./oa_schedule.jsp", "#tbb");
		});
		function doAjaxPost(url, traget) {
			$.ajax({
				type: "POST",
				url: url,
				dateType: "html",
				success:function(data){
					$(traget).html(data);
					 calendar(); 
				},
				error: function() {
					$("#myScheduleBg").html("加载页面错误");
				}
			});
		}
</script>
<div class="jt_lgxxc" id="mySchedule">
	<div class="jt_grxtit">
		<table width="100%">
			<tr>
				<td align="center"><font id="preMonth" style="text-align: left; cursor: pointer;">&lt;&lt;-</font></td>
				<td align="center">
					<font id="y"><%=session.getAttribute("startY")%></font>年
					<font id="m"><%=(Integer)session.getAttribute("startM")+1 %></font>月 个人日程
				</td>
				<td align="center"><font id="nextMonth" style="text-align: right; cursor: pointer;">-&gt;&gt;</font></td>
			</tr>
		</table>
	</div>
	<div class="jt_grxcon">
		<table width="100%" height="100%" border="0" class="calendar">
			<tr style="font: bolder;">
				<td>日</td>
				<td>一</td>
				<td>二</td>
				<td>三</td>
				<td>四</td>
				<td>五</td>
				<td>六</td>
			</tr>
			<tbody id="nums"></tbody>
		</table>
	</div>
	<div class="jt_grxbot"></div>
</div>

<!--日程信息  -->
<div id="showSchedule" class="schedule">
	<p align="right" id="scheduleclose" style="cursor: pointer; font-size: 10px;">╳</p>
	<table class="content" width="100%" align="center">
		<caption>日程安排</caption>
		<tr>
			<td>起始时间</td>
			<td>内容</td>
		</tr>
		<tbody id="tbb">
		</tbody>
	</table>
</div>