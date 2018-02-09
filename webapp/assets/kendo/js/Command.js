//解决json时区问题
	Date.prototype.toJSON = function () {
	    return kendo.toString(this, "yyyy-MM-dd HH:mm:ss");
	
	};
//新增准备
function onAdd() {
    return true;
}
//修改准备
function onUpdate() {
    return true;
}
//删除准备
function onDelete() {
    return true;
}
//删除准备
function onShowTemp() {
    return true;
}

//提交准备
function onSub() {
    return true;
}

//审核准备
function onCheck() {
    return true;
}
function onDCheck() {
    return true;
}
//文章推荐
function onArticleTJ(){
	 return true;	
}

//退回
function onTuiHui() {
    return true;
}
//彻底删除
function onCdDel() {
    return true;
}
//窗体打开时
function windowOpen(winName) {
    return true;
}
//窗体关闭时
function windowCLose(winName) {
    return true;
}

function CreateToolBarButton(Options) {
    this.Str = "";
    if (Options.Add != null && Options.Add) {
        this.Str = "<a title='新增' class='k-button' id='AddBtn' style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"openwindow('" + Options.Url + "' ," + Options.Width + "," + Options.Height + ",'add','div');\">新增</a>";
    }
    if (Options.Edit != null && Options.Edit) {
        this.Str += "<a title='修改' class='k-button' id='UpdateBtn'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"openwindow('" + Options.Url + "' ," + Options.Width + "," + Options.Height + ",'edit','div');\">修改</a>";
    } else if (Options.EditUrl != null && Options.EditUrl) {
        this.Str += "<a title='修改' class='k-button' id='UpdateBtn'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"openwindow('" + Options.EditUrl + "' ," + Options.Width + "," + Options.Height + ",'edit','div');\">修改</a>";
    }
    if (Options.Del != null && Options.Del) {
        this.Str += "<a title='删除' class='k-button' id='DelBtn'  style='margin-right:4px;' href=\"\\javascript:void(0);\" onclick=\"DelConfirmation();\">删除</a>";
    }
    if (Options.Detail != null && Options.Detail) {
        this.Str += "<a title='查看详情' class='k-button' id='Detail'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"openwindow('" + Options.Url + "' ," + Options.Width + "," + Options.Height + ",'detail','div');\">查看详情</a>";
    }
    if (Options.Filter != null && Options.Filter) {
        this.Str += "<a title='高级查询' class='k-button'  id='SearchBtn' style='margin-right:4px;' href=\"\\javascript:void(0);\" onclick=\"onSearch('" + Options.GridId + "');\">高级查询</a>";
    }
    if (Options.ShowTemp != null && Options.ShowTemp) {
        this.Str += "<a title='查看指标' class='k-button' id='ShowTemp'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"openwindow('" + Options.ShowUrl + "' ," + Options.Width + "," + Options.Height + ",'showTemp','div');\">查看指标</a>";
    }
    if (Options.Sub != null && Options.Sub) {
        this.Str += "<a title='提交' class='k-button' id='Sub'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"SubConfirmation();\">提交</a>";
    }
    if (Options.Check != null && Options.Check) {
        this.Str += "<a title='提交审核' class='k-button' id='Check'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"CheckConfirmation();\">提交审核</a>";
    }
    if (Options.DCheck != null && Options.DCheck) {
        this.Str += "<a title='审核' class='k-button' id='DCheck'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"DCheckConfirmation();\">审核</a>";
    }
    if (Options.TuiHui != null && Options.TuiHui) {
        this.Str += "<a title='退回' class='k-button' id='TuiHui'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"TuiHuiConfirmation();\">退回</a>";
    }
    if (Options.CdDel != null && Options.CdDel) {
        this.Str += "<a title='删除' class='k-button' id='CdDel'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"CdDelConfirmation();\">删除</a>";
    }
    if (Options.ArticleTJ != null && Options.ArticleTJ) {
        this.Str += "<a title='文章推荐' class='k-button' id='ArticleTJ'  style='margin-right:4px;'  href=\"\\javascript:void(0);\" onclick=\"ArticleTJConfirmation();\">文章推荐</a>";
    }
}

function openwindow(url, width, height, type_n, open_type, winName) {
    if (type_n == "add") {
        if (onAdd()) {
            if (open_type == "window") {
                window.open(url, "", "toolbar=no,width=" + width + ",height=" + height + ",status=no,scrollbars=yes,resize=no,menuber=no");
            }
            else if (open_type == "div") {
                newEvent(url, width, height, type_n, winName);
                createevent_window.open();
            }
        }
    }
    else if (type_n == "edit") {
        if (onUpdate()) {
            if (open_type == "window") {
                window.open(url, "", "toolbar=no,width=" + width + ",height=" + height + ",status=no,scrollbars=yes,resize=no,menuber=no");
            }
            else if (open_type == "div") {
                newEvent(url, width, height, type_n, winName);
                createevent_window.open();
            }
        }
    }
    else if (type_n == "showTemp") {
        if (onShowTemp()) {
            if (open_type == "window") {
                window.open(url, "", "toolbar=no,width=" + width + ",height=" + height + ",status=no,scrollbars=yes,resize=no,menuber=no");
            }
            else if (open_type == "div") {
                newEvent(url, width, height, type_n, winName);
                createevent_window.open();
            }
        }
    } else if (type_n == "report") {
        if (onShowTemp()) {
            if (open_type == "window") {
                window.open(url, "", "toolbar=no,width=" + width + ",height=" + height + ",status=no,scrollbars=yes,resize=no,menuber=no");
            }
            else if (open_type == "div") {
                newEvent(url, width, height, type_n, winName);
                createevent_window.open();
            }
        }
    }  else if (type_n == "item") {
        if (onShowTemp()) {
            if (open_type == "window") {
                window.open(url, "", "toolbar=no,width=" + width + ",height=" + height + ",status=no,scrollbars=no,resize=no,menuber=no");
            }
            else if (open_type == "div") {
                newEvent(url, width, height, type_n, winName);
                createevent_window.open();
            }
        }
    } else if (type_n == "explain") {
        if (onShowTemp()) {
            if (open_type == "window") {
                window.open(url, "", "toolbar=no,width=" + width + ",height=" + height + ",status=no,scrollbars=no,resize=no,menuber=no");
            }
            else if (open_type == "div") {
                newEvent(url, width, height, type_n, winName);
                createevent_window.open();
            }
        }
    }else if (type_n == "file") {
        if (onShowTemp()) {
            if (open_type == "window") {
                window.open(url, "", "toolbar=no,width=" + width + ",height=" + height + ",status=no,scrollbars=no,resize=no,menuber=no");
            }
            else if (open_type == "div") {
                newEvent(url, width, height, type_n, winName);
                createevent_window.open();
            }
        }
    }else if (type_n == "process") {
        if (onShowTemp()) {
            if (open_type == "window") {
                window.open(url, "", "toolbar=no,width=" + width + ",height=" + height + ",status=no,scrollbars=no,resize=no,menuber=no");
            }
            else if (open_type == "div") {
                newEvent(url, width, height, type_n, winName);
                createevent_window.open();
            }
        }
    }else if (type_n == "xgmm") {
        if (onShowTemp()) {
            if (open_type == "window") {
                window.open(url, "", "toolbar=no,width=" + width + ",height=" + height + ",status=no,scrollbars=no,resize=no,menuber=no");
            }
            else if (open_type == "div") {
            	newEvent_xgmm(url, width, height, type_n, winName);
                createevent_window.open();
            }
        }
    }else {
        if (open_type == "window") {
            window.open(url, "", "toolbar=no,width=" + width + ",height=" + height + ",status=no,scrollbars=yes,resize=no,menuber=no");
        }
        else if (open_type == "div") {
            newEvent(url, width, height, type_n, winName);
            createevent_window.open();
        }
    }
}

function closewindow(close_type, winName) {
    if (close_type == "window") {
        this.window.close();
    }
    else if (close_type == "div") {
        $("#" + winName + "").kendoWindow({
            animation: false,
            modal: true,
            deactivate: function () {
                windowCLose(winName);
                this.destroy();
            }
        }).data("kendoWindow").close();
    }
}

function newEvent(url, width, height, type_n, winName) {
	var Title = "";
    if (type_n == "add") {
    	Title = "[新增]";
    }else if(type_n == "showTemp") {
    	Title = "[创建报表]";
    }else if(type_n == "detail") {
    	Title = "[查看详情]";
    }else if(type_n == "dbAdd") {
    	Title = "[督办]";
    }else if(type_n == "report") {
    	Title = "[汇报]";
    }else if(type_n == "item") {
    	Title = "[日常事项]";
    }else if(type_n == "explain") {
    	Title = "[申请说明]";
    }else if(type_n =="edit"){
    	Title = "[修改]";
    }else if(type_n =="file"){
    	Title = "[附件]";
    }else if(type_n =="process"){
    	Title = "[流转记录]";
    }else {
       if(winName!=null && winName !=""){
    	   Title = "["+winName+"]";
       }else{
    	   Title = "";
       }
    }
    
    createevent_window = $("<div id=" + winName + "/>").kendoWindow({
        actions: ["Minimize", "Maximize", "Close"], 
        title: Title,
        height: height,
        width: width,
        animation: false,
        modal: true,
        activate: function () {
            windowOpen(winName);
        },
        deactivate: function () {
            windowCLose(winName);
            this.destroy();
        },
        content: url
    }).data("kendoWindow");
    
    if ((height === undefined || height==="")&& (width === undefined)|| width=="") {
    	createevent_window.maximize();
    }else{
    	createevent_window.center();
    }
}

function newEvent_xgmm(url, width, height, type_n, winName) {
	var Title = "";
    if (type_n == "xgmm") {
    	Title = "[修改密码]";
    }
    
    createevent_window = $("<div id=" + winName + "/>").kendoWindow({
        actions: ["Minimize", "Maximize"], 
        title: Title,
        height: height,
        width: width,
        animation: false,
        modal: true,
        activate: function () {
            windowOpen(winName);
        },
        deactivate: function () {
            windowCLose(winName);
            this.destroy();
        },
        content: url
    }).data("kendoWindow");
    
    if ((height === undefined || height==="")&& (width === undefined)|| width=="") {
    	createevent_window.maximize();
    }else{
    	createevent_window.center();
    }
}



function AjaxDoPost(strUrl, strData, IsAsync, pSuccessCallback, pErrorCallback) {
    if (strUrl != "") {
    	try {
    		if(strData!=null){
		    	strData = strData.replace(/%/g,"%25");
		    	strData = strData.replace(/&/g,"%26");
		    	strData = strData.replace(/\?/g,"%3f");
	    	}
		} catch (e) {
		}
        $.ajax({
            url: "" + strUrl + "",
            type: "POST",
            async: IsAsync,
            dataType: "json",
            data: "" + strData + "",
            success: function (result) {
                if (pSuccessCallback != null) {
                    pSuccessCallback(result);
                }
            },
            error: function (result) {
                if (pErrorCallback != null) {
                    pErrorCallback(result);
                }
            }
        });
    }else {
        var result = [];
        pSuccessCallback(result);
    }
}

function DelConfirmation() {
    if (onDelete()) {
    	$.when(ConfirmDialog({
            Title: "提示信息",
            Message: "确定删除选中的记录?",
            Icon: "k-ext-question"
        })).done(function (response) {
            if (response.button == "确定"){
            	del();
            }else{
            	return
            }
            });
    }
}

function SubConfirmation() {
    if (onSub()) {
    	$.when(ConfirmDialog({
            Title: "提示信息",
            Message: "确定提交选中的记录?",
            Icon: "k-ext-question"
        })).done(function (response) {
            if (response.button == "确定"){
            	submit();
            }else{
            	return
            }
            });
    }
}
function CheckConfirmation() {
    if (onCheck()) {
    	$.when(ConfirmDialog({
            Title: "提示信息",
            Message: "确定提交选中的记录进行审核?",
            Icon: "k-ext-question"
        })).done(function (response) {
            if (response.button == "确定"){
            	check();
            }else{
            	return
            }
            });
    }
}

function DCheckConfirmation() {
    if (onDCheck()) {
    	dCheck();
    }
}

function TuiHuiConfirmation() {
    if (onTuiHui()) {
    	$.when(ConfirmDialog({
            Title: "提示信息",
            Message: "确定退回选中的记录?",
            Icon: "k-ext-question"
        })).done(function (response) {
            if (response.button == "确定"){
            	tuiHui();
            }else{
            	return
            }
            });
    }
}

function ArticleTJConfirmation(){
	 if (onArticleTJ()) {
		 articleTJ();
    }
}


function CdDelConfirmation() {
    if (onCdDel()) {
    	$.when(ConfirmDialog({
            Title: "提示信息",
            Message: "确定删除选中的记录?",
            Icon: "k-ext-question"
        })).done(function (response) {
            if (response.button == "确定"){
            	cdDel();
            }else{
            	return
            }
            });
    }
}

/*高级查询开始*/
function onSearch(GridId) {
    var QueryWindow = $("#QueryGrid");
    if (!QueryWindow.data("kendoWindow")) {
        QueryWindow.kendoWindow({
            width: "700px",
            height: "450px",
            animation: false,
            modal: true,
            actions: ["Minimize", "Maximize", "Close"],
            title: "高级查询"
        });
    }
    QueryWindow.data("kendoWindow").center().open();
    var dg = (GridId == undefined || GridId == "undefined") ? "grid" : GridId;
    var DataGrid = $("#" + dg).data("kendoGrid");
    gQueryViewModel.Init($("#QueryGrid"), DataGrid, QueryCallback);
}
function QueryCallback(QueryInfo, GridId) {
    var PostData = new Object();
    PostData.IsPage = false;
    PostData.InObject = QueryInfo;
    var gd = GridId == undefined ? "grid" : GridId;
    var DataGrid = $("#" + gd).data("kendoGrid");
    var dataSource = DataGrid.dataSource;
    dataSource.options.InObject = QueryInfo;
    dataSource.page(1);
    var QueryWindow = $("#QueryGrid");
    QueryWindow.data("kendoWindow").close();
}
/*高级查询结束*/



function TransformToTreeFormat(Nodes, IdName, ParentIdName, ChildItemName) {
    var i, l;
    var r = [];
    var tmpMap = [];
    for (i = 0, l = Nodes.length; i < l; i++) {
        tmpMap[Nodes[i][IdName]] = Nodes[i];
    }
    for (i = 0, l = Nodes.length; i < l; i++) {
        if (tmpMap[Nodes[i][ParentIdName]] && Nodes[i][IdName] != Nodes[i][ParentIdName]) {
            if (!tmpMap[Nodes[i][ParentIdName]][ChildItemName])
                tmpMap[Nodes[i][ParentIdName]][ChildItemName] = [];
            tmpMap[Nodes[i][ParentIdName]][ChildItemName].push(Nodes[i]);
        } else {
            r.push(Nodes[i]);
        }
    }
    return r;
}

function JsonPostForWaiting(Options) {
    $.ExtAjax({
        url: Options.Url,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: Options.Data,
        success: function (Result) {
            var ResultMsg = new Object;
            ResultMsg.ResultType = 1;   //成功结果
            ResultMsg.ResultMsg = Result;
            ResponsionHandle(ResultMsg, Options.Success, Options.Error, Options.Parameter);
        },
        error: function (Result) {
            var ResultMsg = new Object;
            ResultMsg.ResultType = 0;   //失败结果
            ResultMsg.ResultMsg = Result;
            ResponsionHandle(ResultMsg, Options.Success, Options.Error, Options.Parameter);
        }
    }, Options.WaittingText, Options.AimDiv);
}

//Ajax应答处理
function ResponsionHandle(ResultMsg, Success, Error, Parameter) {
    if (ResultMsg.ResultType == 0) {
        if (Error != null) {
            Error(ResultMsg, Parameter);
        }
    } else if (ResultMsg.ResultType == 1) {
        if (ResultMsg.ResultMsg.resultCode == 0) {
            if (Success != null) {
                Success(ResultMsg, Parameter);
            }
        } else {
            if (Error != null) {
                Error(ResultMsg, Parameter);
            }
        }
    }
}

function GetErrorMsg(ResultMsg) {
    var ErrorMsg ="";
    if (ResultMsg.ResultType == 0) {
        ErrorMsg = "请求失败";
    } else if (ResultMsg.ResultType == 1) {
        ErrorMsg = ResultMsg.ResultMsg.ResultMsg;
    }
    
    return ErrorMsg;
}

/* fengjunkong 提交*/
function onSubmit() {
	
        $.when(ConfirmDialog({
            Title: "提示信息",
            Message: "是否需要提交选中记录？",
            Icon: "k-ext-question"
        })).done(function (response) {
            if (response.button == "确定"){
            	submit();
            }else{
            	return
            }
            });
	
}

//Div滚动特效
function startmarquee(objId,lh,speed,delay){
    var p=false;
    var o=document.getElementById(objId);
    //o.innerHTML+=o.innerHTML;
    o.onmouseover=function(){p=true;};
    o.onmouseout=function(){p=false;};
    o.scrollTop = 0;
    function start(){
        t=setInterval(scrolling,speed);
        if(!p){ o.scrollTop += 1;}
    }
    function scrolling(){
        if(o.scrollTop%lh!=0){
            o.scrollTop += 1;
            if(o.scrollTop+o.offsetHeight>=o.scrollHeight) o.scrollTop = 0;
        }else{
            clearInterval(t);
            setTimeout(start,delay);
        }
    }
    setTimeout(start,delay);
}//**startmarquee(要滚动的模块Id,一次滚动高度,速度,停留时间);**/
