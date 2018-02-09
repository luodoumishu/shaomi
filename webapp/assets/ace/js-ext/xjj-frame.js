//xjj-frame.js
jQuery(function () {
    xjj.frame.loadApp();
    //reload_menu();
    //xjj.frame.handleSideMenu();
    xjj.frame.changeBreadcrumb('');
    xjj.frame.handleScrollBtn();
    //enable_search_ahead();
    add_browser_detection(jQuery);
    //general_things();
    //widget_boxes();
    //$(document).off("click .dropdown-menu");
    $(".nav-list>.active>a").click();
});
var xjj;
if(!xjj) xjj = {};
xjj.frame = {};
xjj.frame.handleSideMenu=function(){
    $("#menu-toggler").off("click");
    $("#menu-toggler").on("click", function () {
        $("#sidebar").toggleClass("display");
        $(this).toggleClass("display");
        return false;
    });
    var a = false;
    $("#sidebar-collapse ").off("click");
    $("#sidebar-collapse ").on("click", function () {
        var winWidths = document.body.scrollWidth;//
        var winHeights = document.documentElement.clientHeight;
        $("#sidebar").toggleClass("menu-min");
        $(this.firstChild).toggleClass("icon-double-angle-right");
        a = $("#sidebar").hasClass("menu-min");
        if (a) {
            $(".open > .submenu").removeClass("open");
        }
        var oldie = $.browser.msie && $.browser.version < 9;
        if(oldie)  $("#main-content-iframe").focus();//if<ie9,then fix width
        var widths = winWidths- $("#sidebar").width();
        var heights = winHeights-$("#main-head").height();
        $("#main-content-iframe").height(heights - 47).width(widths - 5);
        var width = document.documentElement.clientWidth;
        if (width < 956) {
            $("#main-content-iframe").width(width);
        }
    });
    $(".nav-list .dropdown-toggle").each(function () {
        var b = $(this).next().get(0);
        $(this).on("click", function () {
            if (a) {
                return false;
            }
            $(this).children("b").toggleClass("icon-angle-down icon-angle-left");
            $(".open > .submenu").each(function () {
                if (this != b){
                    $(this).slideUp(200).parent().removeClass("open");
                    $(this).parent().removeClass("active");
                    $(this).parent().children("a").children("b").toggleClass("icon-angle-down icon-angle-left");
                }
            });
            var browser=navigator.appName;
            var b_version=navigator.appVersion;
            var version=b_version.split(";");
            if(version[1]!=undefined){
	            var trim_Version=version[1].replace(/[ ]/g,"");
	            if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0"){
	            	$(b).parent().toggleClass("open").toggleClass("active");
	            }else{
	            	$(b).slideToggle(200).parent().toggleClass("open").toggleClass("active");
	            }
            }else{
            	$(b).slideToggle(200).parent().toggleClass("open").toggleClass("active");
            }
            return false;
        });
    });
    $(".sidebar-a").each(function () {
        $(this).on("click", function () {
            $(".sidebar-a").each(function () {
                if ($(this.parentNode).hasClass("active")) {
                    $(this.parentNode).removeClass("active");
                }
            });
            $(this.parentNode).addClass("active");//a-li
            $("#main-content-iframe").focus();//--qu diao xuxian bian kuang
            xjj.frame.changeBreadcrumb($(this.parentNode).text());
            return false;
        });
    });
};

xjj.frame.loadApp=function(){
	var obs = get_top_column(ctx+'/zrole/jsonMenuTree.json', 'post');
	/*var obs = [
       {'name': '系统系统系','url': '#' , 'icon': 'icon-cog bigger-230','colour':'btn-info', 'id': '111'},
       {'name': '信息平台的','url': '#', 'icon': 'icon-edit bigger-230','colour':'btn-primary', 'id': '222'},
       {'name': '经济运行监是','url': '#', 'icon': 'icon-refresh bigger-230','colour':'btn-success', 'id': '333'},
       {'name': '企业管理','url': '#', 'icon': 'icon-undo bigger-230','colour':'btn-warning', 'id': '444'}
   ];*/
   var colors = ['btn-info', 'btn-success', 'btn-primary','btn-warning', 'btn-danger'];
   var appStr="<p >"; 
   if(obs==null || obs.length<=0 || obs.length==undefined){
	   boole = true;
   }
   for(var i=0,j=-1;i<obs.length;i++){   //ace-active
	   j++;
	   if(j>=5){
		   j=0;
	   }
	   var last = JSON.stringify(obs[i].child); 
	   last = last.replace(/\"/g,"\'");
       appStr+= '<a href="javascript:void(0);" onclick="xjj.frame.loadMenu('+last+',\''+obs[i].menuName+'\');" class="btn btn-app '+colors[j]+' radius-4">';
       appStr+= '<i class="'+obs[i].img+' bigger-230"></i>';
       appStr+= '<span style="font-size:16px;">'+obs[i].menuName+'</span>';
       if(i==0){
    	   xjj.frame.loadMenu(obs[i].child,obs[i].menuName);
       }
       appStr+= '</a>';
       appStr+= '&nbsp;';
   }
   appStr+= '</p>';
   $("#c_headerBar").prepend(appStr);
};
xjj.frame.loadMenu=function(obs,appName){
    /*var obs = [
        {'name': '第一个栏目', 'icon': 'icon-list', 'url': '', 'submenu': [
            {'name': '子栏目1.一', 'url': 'about:blank'},
            {'name': '子栏目1.二', 'url': 'about:blank'},
            {'name': '子栏目1.三', 'url': 'about:blank'},
            {'name': '子栏目子栏目子栏目', 'url': 'about:blank'}
        ]},
        {'name': '第二个栏目', 'icon': 'icon-edit', 'url': '', 'submenu': [
            {'name': '子栏目2.一', 'url': 'about:blank'},
            {'name': '子栏目2.二', 'url': 'http://192.168.0.223:8098/jjyx/VpAction_listRpt.action'},
            {'name': '子栏目2.三', 'url': 'about:blank'},
            {'name': '子栏目2.四', 'url': 'about:blank'}
        ]},
        {'name': '第三个栏目', 'icon': 'icon-calendar', 'url': '', 'submenu': [
            {'name': '子栏目3.一', 'url': 'about:blank'},
            {'name': '子栏目3.二', 'url': 'about:blank'},
            {'name': '子栏目3.三', 'url': 'about:blank'},
            {'name': '子栏目3.四', 'url': 'about:blank'}
        ]},
        {'name': '第四个栏目', 'icon': 'icon-file', 'url': '', 'submenu': [
            {'name': '子栏目4.一', 'url': 'about:blank'},
            {'name': '子栏目4.二', 'url': 'about:blank'},
            {'name': '子栏目4.三', 'url': 'about:blank'},
            {'name': '子栏目4.四', 'url': 'about:blank'}
        ]},
        {'name': '第五个栏目', 'icon': 'icon-desktop', 'url': 'http://192.168.0.223:8098/jjyx/VpAction_listRpt.action', 'submenu': []}
    ];*/
	//var obs = eval(obsing);
    var menuStr = "";
    for (var i = 0; i < obs.length; i++) {
        var ob = obs[i];
        var subob = obs[i].child;
        var submenuStr = "";
        var hasSubmenu = (subob && subob.length > 0);
        if(i == 0){
        	menuStr += '<li class="open active">';
        }else{
        	menuStr += '<li>';
        }
        menuStr += '<a href="javascript:void(0);" onclick="javascript:goUrl(\''+(hasSubmenu ?  '#' : ob.url)+'\');return false;"   '+ (hasSubmenu ? 'class="dropdown-toggle" target="_blank">' : 'class="sidebar-a">' ) +
            '<i class="'+ob.img+'"></i>'+
            '<span>'+ob.menuName+'</span>';
            if(i == 0){
            	 menuStr += (hasSubmenu ? '<b class="arrow icon-angle-down"></b>' : '');
            }else{
            	menuStr +=  (hasSubmenu ? '<b class="arrow icon-angle-left"></b>' : '');
            }
        menuStr += '</a>';
        if (hasSubmenu){
            for (var j = 0; j < subob.length; j++) {
                submenuStr += '<li><a href="javascript:void(0);" target="_blank" onclick="javascript:goUrl(\''+subob[j].url+'\');return false;" class="sidebar-a"><i class="icon-double-angle-right"></i><span>'+subob[j].menuName+'</span></a></li>';
            }
            menuStr += '<ul class="submenu">'+submenuStr+'</ul>';
        }
        menuStr += '</li>';
    }
    $("#sidebar-menu").empty().append(menuStr);
    xjj.frame.handleSideMenu();
    $("#main-content-iframe").focus();//--qu diao xuxian bian kuang
    $("#appName").text(appName);//--qu diao xuxian bian kuang
    if($(".nav-list>li:first>ul").length>0){
		$(".nav-list>li:first>ul>li:first>a").click();
	}else{
		$(".nav-list>li:first>a").click();        	
	}
    return false;
};

xjj.frame.changeBreadcrumb=function(text){
    $("#breadcrumbs>ul").empty().append();
    var str = '<li >';
    str += '<i class="icon-home"></i>';
    var lev1 = $("#appName").html();
    var lev2 = $(".nav-list>.active>a>span").html();
    var lev3 = $(".nav-list .submenu>.active>a>span").html();
    //alert(lev1+">"+lev2+">"+lev3) ;
    str += ' 首 页';
    if(lev1){
        str += ' <span class="divider"><i class="icon-angle-right"></i></span> '+lev1;
    }
    if($(".nav-list>.active").length==2){
    	str += ' <span class="divider"><i class="icon-angle-right"></i></span> '+text ;
    }else{
	    if(lev2){
	    	str += ' <span class="divider"><i class="icon-angle-right"></i></span> '+lev2 ;
	    }
    }
    if(lev3){
        str += ' <span class="divider"><i class="icon-angle-right"></i></span> <b class="dark">'+lev3+'</b>';
        
    }
    
    str += ' </li>';
    
    $("#breadcrumbs>ul").empty().append(str);
};

xjj.frame.handleScrollBtn = function(){
	$("#btn-scroll-up-iframe").on("click", function () {
        var iframe = document.getElementById('main-content-iframe');
        var docIframe = iframe.contentDocument || iframe.contentWindow.document;
        var ob = docIframe.getElementsByTagName("html") ||docIframe.getElementsByTagName("body")||false;
        if (ob) {
            var a = Math.max(100, parseInt($(ob).scrollTop() / 3));
            $(ob).animate({scrollTop: 0}, a);
        }
        return false;
    });
};

function add_browser_detection(c) {
	if (!c.browser) {
        var a, b;
        c.uaMatch = function (e) {
            e = e.toLowerCase();
            var d = /(chrome)[ \/]([\w.]+)/.exec(e) || /(webkit)[ \/]([\w.]+)/.exec(e) || /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(e) || /(msie) ([\w.]+)/.exec(e) || e.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(e) || [];
            return{browser: d[1] || "", version: d[2] || "0"};
        };
        a = c.uaMatch(navigator.userAgent);
        b = {};
        if (a.browser) {
            b[a.browser] = true;
            b.version = a.version;
        }
        if (b.chrome) {
            b.webkit = true;
        } else {
            if (b.webkit) {
                b.safari = true;
            }
        }
        c.browser = b;
    }
};

//获取顶级栏目
function get_top_column(url, type){
	return doAjax(url, type);
}

//获取一级栏目
function get_left_colum(url, type){
	return doAjax(url, type);
}

//异步请求的方法
function doAjax(url,type){
	var ob = '-1';
	$.ajax({
		url : url,
		type : type,
		cache : false,
		async : false,
		error : function(textStatus, errorThrown){
			alert("系统ajax交互错误"+textStatus);
			return;
		},
		success : function(data){
			ob = eval(data);
		}
	});
	return ob;
}
function goUrl(cid){
	if(cid=='#'){
		return;
	}
	document.getElementById("main-content-iframe").src=ctx+"/sortToLog-new.jsp?url="+encodeURIComponent(cid);
	//var iframe = document.getElementById('main-content-iframe');
    //var docIframe = iframe.contentDocument || iframe.contentWindow.document;
    //var ob = docIframe.getElementsByTagName("body")||false;
}
