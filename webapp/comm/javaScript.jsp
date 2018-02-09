<script>
	function openwin(url,h,w){
		perty ="dialogHeight:"+h+"px;dialogWidth:"+w+"px;dialogHide:yes;scroll:no;help:no;status:no;center:yes";
		var v=window.open(url,"",perty);
	}

	function openLwin(url,h,w){
		var options = "width=" + (window.screen.width-15) + ",height=" + (window.screen.height - 55) + ",";
		options += "resizable=yes,scrollbars=yes,status=no,";
		options += "location=no,menubar=no,toolbar=no,directories=no,top=0,left=0";
		var v=window.open(url,"",options);
	}

	function openLwin1(url,h,w){
		var options = "width=" + h + ",height=" + w + ",";
		options += "resizable=yes,scrollbars=yes,status=no,";
		options += "location=no,menubar=no,toolbar=no,directories=no,top=0,left=0";
		var v=window.open(url,"",options);
	}

	function allSelected(){
		if(document.forms.ec.itemlist==undefined){
			return ;
		}
		for(var i=0;i<document.forms.ec.itemlist.length;i++){
			if(document.forms.ec.itemlist[i].checked==false){
				document.forms.ec.itemlist[i].checked= true;
			}
		}
		
	}

	function allCal(){
		if(document.forms.ec.itemlist==undefined){
			return ;
		}
		for(var i=0;i<document.forms.ec.itemlist.length;i++){
			if(document.forms.ec.itemlist[i].checked==true){
				document.forms.ec.itemlist[i].checked= false;
			}
		}
	}

	//使当前的窗口变为灰色
	function hideDivShow(){
		var w = (screen.width-680)/2;
		var _w = 680+w;
		var obj = document.getElementById("mainhideDiv");
		obj.style.left = 0;
		obj.style.top = 0;
		obj.style.width=screen.width;
		obj.style.height = screen.height;
		obj.style.visibility = "visible";
		
	}
	

	function hideDivClose(){
		document.getElementById("mainhideDiv").style.visibility = "hidden";
	}


	function getItems1(){
		var Items = new Array()
		for(var i=0;i<document.all("servicelevels").length;i++){
			if(document.all("servicelevels")(i).checked)
			{
				if( (document.all("servicelevels")(i).id) != 0)
				Items[Items.length] = document.all("servicelevels")(i).id
			}
		}
		return Items
	}

	function getItems(){
		var Items="";
		for(var i=0;i<document.all("servicelevels").length;i++){
			if(document.all("servicelevels")(i).checked)
			{
				if( (document.all("servicelevels")(i).id) != 0)
				Items = Items + "," +document.all("servicelevels")(i).id
			}
		}
		return Items.substring(1);
	}

	function toUrlIndex(url){
		hideDivShow();
		document.getElementById("shoId").style.display="";
		window.location.href=decodeURIComponent(url);
	}

</script>
