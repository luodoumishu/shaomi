var uploadFileSize = 0;
function InitFile(fileId,module,dir,dataId,enabled) {
	$.ajax({
		url : crudServiceBaseUrl+'/'+module+'/'+dir+'/common/attachment/init.json?dataId='+dataId,
		type : "POST",
		success : function(files) {
			$("#"+fileId).kendoUpload({
				multiple: true
				,enabled: enabled
	            ,async: {
	                saveUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId,
	                removeUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/remove.do?dataId="+dataId,
	                autoUpload: false
	            }
	            ,success: onSuccess
	            ,upload: onUpload
	            ,select: onSelect
	            ,error: onError
	            ,remove: onRemove
	            ,template: "<div class='file-wrapper' style='width: 300px'># if (files[0].uploadSuccess) { #<a href='"+crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/download.do?localname=#=files[0].localname#&dataId=#=files[0].dataId#&name=#=files[0].name#' target='_blank'><span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button></a># } else { #<span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button># } #</div>"
	            ,files:files
	            ,localization: {
	            	"select": "选择附件",
	                "cancel": "取消",
	                "retry": "重试",
	                "remove": "删除",
	                "uploadSelectedFiles": "上传选定的文件",
	                "statusUploading": "上传中",
	                "statusUploaded": "上传",
	                "statusFailed": "上传失败",
	                "statusWarning": "上传警告",
	                "headerStatusUploading": "上传中...",
	                "headerStatusUploaded": "完成"
	            }
	        });
			if(!enabled){
				$(".k-dropzone").remove();
				$(".k-upload-button").remove();
				$(".k-upload-files").css("marginBottom","0px");
				$(".k-header").css("borderBottomWidth","0px");
			}
		},
		error : function() {
			$("#"+fileId).kendoUpload({
				multiple: true
				,enabled: true
	            ,async: {
	                saveUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do",
	                removeUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/remove.do",
	                autoUpload: false
	            }
	            ,success: onSuccess
	            ,upload: onUpload
	            ,select: onSelect
	            ,error: onError
	            ,remove: onRemove
	            ,template: "<div class='file-wrapper' style='width: 300px'># if (files[0].uploadSuccess) { #<a href='"+crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/download.do?localname=#=files[0].localname#&dataId=#=files[0].dataId#&name=#=files[0].name#' target='_blank'><span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button></a># } else { #<span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button># } #</div>"
	            ,localization: {
	                "select": "选择附件",
	                "cancel": "取消",
	                "retry": "重试",
	                "remove": "删除",
	                "uploadSelectedFiles": "上传选定的文件",
	                "statusUploading": "上传中",
	                "statusUploaded": "上传",
	                "statusFailed": "上传失败",
	                "statusWarning": "警告",
	                "headerStatusUploading": "上传中...",
	                "headerStatusUploaded": "完成"
	            }
	        });
			if(!enabled){
				$(".k-dropzone").remove();
				$(".k-upload-button").remove();
				$(".k-upload-files").css("marginBottom","0px");
				$(".k-header").css("borderBottomWidth","0px");
			}
		}
	});
}
function InitFiles(fileId,module,dir,dataId,enabled) {
	$.ajax({
		url : crudServiceBaseUrl+'/'+module+'/'+dir+'/common/attachment/init.json?dataId='+dataId,
		type : "POST",
		success : function(files) {
	    	$("#"+fileId).kendoUpload({
				multiple: true
				,enabled: enabled
	            ,async: {
	                saveUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId,
	                removeUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/remove.do?dataId="+dataId,
	                autoUpload: false
	            }
	            ,success: onSuccess
	            ,upload: onUpload
	            ,select: onSelect
	            ,error: onError
	            ,remove: onRemove
	            ,template: "<div class='file-wrapper' style='width: 300px'># if (files[0].uploadSuccess) { #<a href='"+crudServiceBaseUrl+"/yearwork/hbcl/common/attachment/download.do?localname=#=files[0].localname#&dataId=#=files[0].dataId#&name=#=files[0].name#' target='_blank'><span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4></a># } else { #<span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button># } #</div>"
	            ,files:files
	            ,localization: {
	            	"select": "选择附件",
	                "cancel": "取消",
	                "retry": "重试",
	                "remove": "删除",
	                "uploadSelectedFiles": "上传选定的文件",
	                "statusUploading": "上传中",
	                "statusUploaded": "上传",
	                "statusFailed": "上传失败",
	                "statusWarning": "警告",
	                "headerStatusUploading": "上传中...",
	                "headerStatusUploaded": "完成"
	            }
	        });
	    	if(!enabled){
	    		$(".k-dropzone").remove();
				$(".k-upload-button").remove();
				$(".k-upload-files").css("marginBottom","0px");
				$(".k-header").css("borderBottomWidth","0px");
			}
		},
		error : function() {
			$("#"+fileId).kendoUpload({
				multiple: true
				,enabled: true
	            ,async: {
	                saveUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do",
	                removeUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/attachment/remove.do",
	                autoUpload: false
	            }
	            ,success: onSuccess
	            ,upload: onUpload
	            ,select: onSelect
	            ,error: onError
	            ,remove: onRemove
	            ,template: "<div class='file-wrapper' style='width: 300px'># if (files[0].uploadSuccess) { #<a href='"+crudServiceBaseUrl+"/yearwork/hbcl/common/attachment/download.do?localname=#=files[0].localname#&dataId=#=files[0].dataId#&name=#=files[0].name#' target='_blank'><span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4></a># } else { #<span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button># } #</div>"
	            ,localization: {
	            	"select": "选择附件",
	                "cancel": "取消",
	                "retry": "重试",
	                "remove": "删除",
	                "uploadSelectedFiles": "上传选定的文件",
	                "statusUploading": "上传中",
	                "statusUploaded": "上传",
	                "statusFailed": "上传失败",
	                "statusWarning": "警告",
	                "headerStatusUploading": "上传中...",
	                "headerStatusUploaded": "完成"
	            }
	        });
			if(!enabled){
				$(".k-dropzone").remove();
				$(".k-upload-button").remove();
				$(".k-upload-files").css("marginBottom","0px");
				$(".k-header").css("borderBottomWidth","0px");
			}
		}
	});
}

//dingph add
function cjInitFiles(fileId,module,dir,dataId,enabled) {
	$.ajax({
		url : crudServiceBaseUrl+'/'+module+'/'+dir+'/common/attachment/init.json?dataId='+dataId,
		type : "POST",
		success : function(files) {
	    	$("#"+fileId).kendoUpload({
				multiple: true
				,enabled: enabled
	            ,async: {
	                saveUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId,
	                removeUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/remove.do?dataId="+dataId,
	                autoUpload: false
	            }
	            ,success: onSuccess
	            ,upload: onUpload
	            ,select: onSelect
	            ,error: onError
	            ,remove: onCancel
	            ,template: "<span># if (files[0].uploadSuccess) { #" +
	            		"<a href='"+crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/download.do?localname=#=files[0].localname#&dataId=#=files[0].dataId#&name=#=files[0].name#' target='_blank'><span>#=name#</span><button type='button' class='k-upload-action'></button></a># " +
	            				"} else {" +
	            				"$('.widget-header').css('margin-top','45px'); $('.k-file, .k-file-success').attr('style','display:inline;border:0px');$('.k-upload-files, .k-rese').attr('style','background-color:white;border:0px');#<span>#=name#</span><button type='button' class='k-upload-action'></button># } #</span>"
	            ,files:files
	            ,localization: {
	            	"select": "选择附件",
	                "cancel": "取消",
	                "retry": "重试",
	                "remove": "删除",
	                "uploadSelectedFiles": "上传选定的文件",
	                "statusUploading": "上传中",
	                "statusUploaded": "上传",
	                "statusFailed": "上传失败",
	                "statusWarning": "警告",
	                "headerStatusUploading": "上传中...",
	                "headerStatusUploaded": "完成"
	            }
	        });
	    	if(!enabled){
	    		$(".k-dropzone").remove();
				$(".k-upload-button").remove();
				$(".k-upload-files").css("marginBottom","0px");
				$(".k-header").css("borderBottomWidth","0px");
			}
	    	$(".widget-header").css("margin-top","20px");
	    	if(files.length>0){
	    		$(".widget-header").css("margin-top","45px");
	    	}
	    	//平行横放上传文件
	    	$(".k-file, .k-file-success").attr("style","display:inline;border:0px");
	    	$(".k-upload-files").attr("style","background-color:white;border:0px");
	    	//------------------------------//
		},
		error : function() {
			$("#"+fileId).kendoUpload({
				multiple: true
				,enabled: true
	            ,async: {
	                saveUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do",
	                removeUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/attachment/remove.do",
	                autoUpload: false
	            }
	            ,success: onSuccess
	            ,upload: onUpload
	            ,select: onSelect
	            ,error: onError
	            ,remove: onRemove
	            ,template: "<div class='file-wrapper' style='width: 300px'># if (files[0].uploadSuccess) { #<a href='"+crudServiceBaseUrl+"/yearwork/hbcl/common/attachment/download.do?localname=#=files[0].localname#&dataId=#=files[0].dataId#&name=#=files[0].name#&name=#=files[0].name#' target='_blank'><span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4></a># } else { #<span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button># } #</div>"
	            ,localization: {
	            	"select": "选择附件",
	                "cancel": "取消",
	                "retry": "重试",
	                "remove": "删除",
	                "uploadSelectedFiles": "上传选定的文件",
	                "statusUploading": "上传中",
	                "statusUploaded": "上传",
	                "statusFailed": "上传失败",
	                "statusWarning": "警告",
	                "headerStatusUploading": "上传中...",
	                "headerStatusUploaded": "完成"
	            }
	        });
			if(!enabled){
				$(".k-dropzone").remove();
				$(".k-upload-button").remove();
				$(".k-upload-files").css("marginBottom","0px");
				$(".k-header").css("borderBottomWidth","0px");
			}
		}
	});
}

//dingph add
function submitCjInitFiles(fileId,module,dir,dataId,enabled) {
	$.ajax({
		url : crudServiceBaseUrl+'/'+module+'/'+dir+'/common/attachment/init.json?dataId='+dataId,
		type : "POST",
		success : function(files) {
	    	$("#"+fileId).kendoUpload({
				multiple: true
				,enabled: enabled
	            ,async: {
	                saveUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId,
	                removeUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/remove.do?dataId="+dataId,
	                autoUpload: false
	            }
	            ,success: onSuccess
	            ,upload: onUpload
	            ,select: onSelect
	            ,error: onError
	            ,remove: onCancel
	            ,template: "<span># if (files[0].uploadSuccess) { #" +
	            		"<a href='"+crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/download.do?localname=#=files[0].localname#&dataId=#=files[0].dataId#&name=#=files[0].name#' target='_blank'><span>#=name#</span></button></a># " +
	            				"} else {" +
	            				"$('.widget-header').css('margin-top','45px'); $('.k-file, .k-file-success').attr('style','display:inline;border:0px');$('.k-upload-files, .k-rese').attr('style','background-color:white;border:0px');#<span>#=name#</span><button type='button' class='k-upload-action'></button># } #</span>"
	            ,files:files
	            ,localization: {
	            	"select": "选择附件",
	                "cancel": "取消",
	                "retry": "重试",
	                "remove": "删除",
	                "uploadSelectedFiles": "上传选定的文件",
	                "statusUploading": "上传中",
	                "statusUploaded": "上传",
	                "statusFailed": "上传失败",
	                "statusWarning": "警告",
	                "headerStatusUploading": "上传中...",
	                "headerStatusUploaded": "完成"
	            }
	        });
	    	if(!enabled){
	    		$(".k-dropzone").remove();
				$(".k-upload-button").remove();
				$(".k-upload-files").css("marginBottom","0px");
				$(".k-header").css("borderBottomWidth","0px");
			}
	    	//平行横放上传文件
	    	$(".k-file, .k-file-success").attr("style","display:inline;border:0px");
	    	$(".k-upload-files").attr("style","background-color:white;border:0px");
	    	$(".widget-header").css("margin-top","65px");
	    	
	    	$(".k-upload-files").css("line-height","0px");
			$(".k-widget,.k-block,.k-inline-block,.k-draghandle").css("border-top-width","0px");
	    	//------------------------------//
		},
		error : function() {
			$("#"+fileId).kendoUpload({
				multiple: true
				,enabled: true
	            ,async: {
	                saveUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do",
	                removeUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/attachment/remove.do",
	                autoUpload: false
	            }
	            ,success: onSuccess
	            ,upload: onUpload
	            ,select: onSelect
	            ,error: onError
	            ,remove: onRemove
	            ,template: "<div class='file-wrapper' style='width: 300px'># if (files[0].uploadSuccess) { #<a href='"+crudServiceBaseUrl+"/yearwork/hbcl/common/attachment/download.do?localname=#=files[0].localname#&dataId=#=files[0].dataId#&name=#=files[0].name#' target='_blank'><span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4></a># } else { #<span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button># } #</div>"
	            ,localization: {
	            	"select": "选择附件",
	                "cancel": "取消",
	                "retry": "重试",
	                "remove": "删除",
	                "uploadSelectedFiles": "上传选定的文件",
	                "statusUploading": "上传中",
	                "statusUploaded": "上传",
	                "statusFailed": "上传失败",
	                "statusWarning": "警告",
	                "headerStatusUploading": "上传中...",
	                "headerStatusUploaded": "完成"
	            }
	        });
			if(!enabled){
				$(".k-dropzone").remove();
				$(".k-upload-button").remove();
				$(".k-upload-files").css("marginBottom","0px");
				$(".k-header").css("borderBottomWidth","0px");
			}
		}
	});
}

function InitAutoUploadFile(fileId,module,dir,dataId,enabled,deleteable,rentenFuncName) {
	var canDelete = null;
	if(deleteable){
		canDelete = 1;
	}else{
		canDelete = 0;
	}
	$.ajax({
		url : crudServiceBaseUrl+'/'+module+'/'+dir+'/common/attachment/init.json?dataId='+dataId,
		type : "POST",
		success : function(files) {
	    	$("#"+fileId).kendoUpload({
				multiple: true
				,enabled: enabled
	            ,async: {
	                saveUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId,
	                removeUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/remove.do?dataId="+dataId,
	                autoUpload: true
	            }
	            ,success: onAutoUploadSuccess
	            ,upload: onUpload
	            ,select: onAutoUploadSelect
	            ,error: onAutoUploadError
	            ,remove: onRemove
	            ,template: "<div class='file-wrapper' style='width: 300px'># if (files[0].uploadSuccess) { #<a href='"+crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/download.do?localname=#=files[0].localname#&dataId=#=files[0].dataId#&name=#=files[0].name#' target='_blank'><span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4>#if('"+canDelete+"' == 1){#<button type='button' class='k-upload-action'></button>#}#</a># } else { #<span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button># } #</div>"
	            ,files:files
	            ,localization: {
	                "select": "选择附件",
	                "cancel": "取消",
	                "retry": "重试",
	                "remove": "删除",
	                "uploadSelectedFiles": "上传选定的文件",
	                "statusUploading": "上传中",
	                "statusUploaded": "上传",
	                "statusFailed": "上传失败"
	            }
	        });
	    	if(!enabled){
				$(".k-dropzone").remove();
				$(".k-upload-button").remove();
				$(".k-upload-files").css("marginBottom","0px");
				$(".k-header").css("borderBottomWidth","0px");
			}
	    	try{
	    		if (rentenFuncName != null) {
                    rentenFuncName(files);
	    		}
			}catch(e){
				alert(e.message);
			}
		},
		error : function() {
			$("#"+fileId).kendoUpload({
				multiple: true
				,enabled: enabled
	            ,async: {
	                saveUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do",
	                removeUrl: crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/remove.do",
	                autoUpload: true
	            }
	            ,success: onAutoUploadSuccess
	            ,upload: onUpload
	            ,select: onAutoUploadSelect
	            ,error: onAutoUploadError
	            ,remove: onRemove
	            ,template: "<div class='file-wrapper' style='width: 300px'># if (files[0].uploadSuccess) { #<a href='"+crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/download.do?localname=#=files[0].localname#&dataId=#=files[0].dataId#&name=#=files[0].name#' target='_blank'><span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button></a># } else { #<span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h4 class='file-heading file-name-heading'>#=name#</h4><button type='button' class='k-upload-action'></button># } #</div>"
	            ,localization: {
	                "select": "选择附件",
	                "cancel": "取消",
	                "retry": "重试",
	                "remove": "删除",
	                "uploadSelectedFiles": "上传选定的文件",
	                "statusUploading": "上传中",
	                "statusUploaded": "上传",
	                "statusFailed": "上传失败"
	            }
	        });
			if(!enabled){
				$(".k-dropzone").remove();
				$(".k-upload-button").remove();
				$(".k-upload-files").css("marginBottom","0px");
				$(".k-header").css("borderBottomWidth","0px");
			}
			try{
	    		if (rentenFuncName != null) {
                    rentenFuncName(null);
	    		}
			}catch(e){
				alert(e.message);
			}
		}
	});
}

function addExtensionClass(extension) {
    switch (extension) {
        case '.jpg':
        case '.img':
        case '.png':
        case '.gif':
            return "img-file";
        case '.doc':
        case '.docx':
            return "doc-file";
        case '.xls':
        case '.xlsx':
            return "xls-file";
        case '.pdf':
            return "pdf-file";
        case '.zip':
        case '.rar':
            return "zip-file";
        default:
            return "default-file";
    }
}
function saveSuccess(fileId,module,dir,dataId) {
    var kendoUpload = $("#"+fileId).data("kendoUpload");
    $(".k-file").each(function() {
        var fileEntry = $(this),
            started = fileEntry.is(".k-file-progress, .k-file-success, .k-file-error");
        if (!started) {
        	uploadFileSize++;
        }
    });
    var browserName=navigator.userAgent.toLowerCase();
    //先判断kendoUpload是否为空
    if(kendoUpload!=null){
        if(/msie/i.test(browserName) && !/opera/.test(browserName)){
        	kendoUpload.options.async.saveUrl = crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId;
        	var ie = browserName.match(/msie ([\d.]+)/)[1] ;
        	if(ie<10){
    	    	$(".k-file").each(function() {
    	            var fileEntry = $(this),
    	                started = fileEntry.is(".k-file-progress, .k-file-success, .k-file-error");
    	            if (!started) {
    	            	var iframe = fileEntry.data("frame");
    	            	iframe.appendTo(document.body);
    	            	var form = iframe.data("form").appendTo(document.body);
    	            	form.attr("action",crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId);
    	            	form.attr("method","post");
    	            }
    	        });
        	}
        }else if(/firefox/i.test(browserName)){
        	kendoUpload.options.async.saveUrl = crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId;
        }else if(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName)){
        	kendoUpload.options.async.saveUrl = crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId;
        }else if(/opera/i.test(browserName)){
        	kendoUpload.options.async.saveUrl = crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId;
        }else if(/webkit/i.test(browserName) &&!(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName))){
        	kendoUpload.options.async.saveUrl = crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId;
        }else{
        	kendoUpload.options.async.saveUrl = crudServiceBaseUrl+"/"+module+"/"+dir+"/common/attachment/save.do?dataId="+dataId;
        }
    }
    if(uploadFileSize>0){
    	kendoUpload._onUploadSelected();
    }else{
    	AlertDialog({
            Title: "提示信息",
            Message: "保存表单成功!",
            Icon: "k-ext-information"
        }).done(function (response) {
			window.location.href=window.location.href; 
       	});
    }
}
function onSuccess(e) {
	if (e.operation == "upload") {
		uploadFileSize--;
		if(uploadFileSize==0){
			AlertDialog({
	           	Title: "提示信息",
	           	Message: "保存表单和附件成功！",
	           	Icon: "k-ext-information"
	       	}).done(function (response) {
	       		uploadFileSize = 0;
	       		var isJxAdd = $("#jxAdd").is(":checked");
	       	 if(isJxAdd){
	       		 //刷新列表页面
	      	   grid.dataSource.read();
	      	   //关闭弹出窗口
	      	   closewindow("div");
	      	   //如果勾选了继续新增,则自动弹出新增窗口
	    		   var url = "/pages/information/manage/cjAdd.jsp";
	    		   openwindow( url,"","",'add','div');
	    	   }else{
	    		   window.location.href=window.location.href;
	    	   }  
        	});
		}
    }
}

function onError(e) {
	if (e.operation == "upload") {
		var responseText = e.XMLHttpRequest.responseText;
		var message = "提交表单成功，保存附件错误！";
		if(responseText=="error1"){
			message = "上传失败：dataId为空！";
		}else if(responseText=="error2"){
			message = "上传失败：模块名为空！";
		}else if(responseText=="error3"){
			message = "上传失败：文件名为空！";
		}else if(responseText=="error4"){
			message = "上传失败：文件为空";
		}else if(responseText=="error5"){
			message = "上传失败：上传文件不能为空";
		}else if(responseText.length>8){
			message = "上传失败：文件大小不能超过"+responseText.substring(5, responseText.length-3)+"KB";
		}
        AlertDialog({
        	Title: "提示信息",
        	Message: message,
        	Icon: "k-ext-error"
		});
        uploadFileSize--;
    }
}
function onRemove(e) {
    if (!confirm("确定要删除" + getFileInfo(e) + "附件吗?")){
    	e.preventDefault();
    }
}

function onCancel(e) {
    if (!confirm("确定要删除" + getFileInfo(e) + "附件吗?")){
    	e.preventDefault();
    	return;
    }
}

function onUpload(e) {
    var files = e.files;
    $.each(files, function () {
        if (this.extension.toLowerCase() != ".jpg") {
      		/*
      		AlertDialog({
       			Title: "提示信息",
       			Message: "提交表单成功，保存附件格式错误！",
       			Icon: "k-ext-error"
			});
            e.preventDefault();
            */
        }
    });
}

function onSelect(e) {
    $.each(e.files, function (index, value) {
    	if(value.size>10000000){
    		AlertDialog({
            	Title: "提示信息",
            	Message: "请上传小于10MB的附件！",
            	Icon: "k-ext-error"
    		});
    		e.preventDefault();
    	}
    });
};

function onAutoUploadSuccess(e) {
	if (e.operation == "upload") {
		window.location.href=window.location.href; 
    }
}

function onAutoUploadError(e) {
	if (e.operation == "upload") {
		var responseText = e.XMLHttpRequest.responseText;
		var message = "保存附件错误！";
		if(responseText=="error1"){
			message = "上传失败：dataId为空！";
		}else if(responseText=="error2"){
			message = "上传失败：模块名为空！";
		}else if(responseText=="error3"){
			message = "上传失败：文件名为空！";
		}else if(responseText=="error4"){
			message = "上传失败：文件为空";
		}else if(responseText=="error5"){
			message = "上传失败：上传文件不能为空";
		}else if(responseText.length>8){
			message = "上传失败：文件大小不能超过"+responseText.substring(5, responseText.length-3)+"KB";
		}
        alert(message);
    }
}

function onAutoUploadSelect(e) {
	$.each(e.files, function (index, value) {
		if(value.size>10000000){
			alert("请上传小于10MB的附件！");
			e.preventDefault();
		}
	});
};

function getFileInfo(e) {
    return $.map(e.files, function(file) {
        var info = file.name;
        return info;
    }).join(", ");
}

function getInitFilesModel(module, dir, dataId, pSuccessCallback, pErrorCallback) {
    $.ajax({
        url: crudServiceBaseUrl+'/'+module+'/'+dir+'/common/attachment/init.json?dataId='+dataId,
        type: "POST",
        async: true,
        dataType: "json",
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
}
