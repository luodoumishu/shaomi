<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String ip = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort();
	String basePath = ip + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/comm/kendo_css.jsp"%>
<link href="<%=basePath%>/pages/video/css/fileUpload.css" type="text/css" rel="stylesheet" />
	<script src="<%=basePath%>/assets/_base/js/jquery-1.9.1.min.js"></script>
<script src="<%=basePath%>/assets/_base/js/jquery.form.js"></script>
<script src="<%=basePath%>/comm/js/core.js"></script>
<script src="<%=basePath%>/assets/cms/cms.js"></script>
<script src="<%=basePath%>/assets/ace/js/json2.js"></script>
</head>
<body>
	<form id="videoForm" name="videoForm"
		action="${ctx}/cms/video/uploadFile.json"
		enctype="multipart/form-data" method="post">
		<%--
			 onpropertychange="onVideoSelect(this)" oninput="onVideoSelect(this)"
		--%>
		<input type="file" name="file" id="file" size="40" onchange="onVideoSelect(this)" />
		<input type="button" name="uploadButton" id="uploadButton" value="开始上传"  disabled="true"/>
		<%--<input type="button" name="cancelUploadButton" id="cancelUploadButton"
			value="取消上传" disabled="true"/>--%>
		<span style="color: red;margin-lef: 8px;">（视频上传说明:&nbsp;&nbsp;最大上传:500M）</span>
	</form>
	<div id="progressBar">
		<div id="theMeter">
			<div class="box_text" id="box_text">
				<div id="totalProgressBarBox">
					<div id="totalProgressBarBoxContent"></div>
				</div>
				<div class="progressBarText" id="progressBarText"></div>
			</div>
			<div id="showFile" style="display: none">
				<span id="showFileName"></span>
				<a id="a_deleteFle" herf="#" onclick='deleteFile(this)' style="color: red;margin-left: 18px;cursor: pointer" path="">删除</a>
			</div>
		</div>
	</div>
	<script>
		var basePath = "<%=basePath%>";
		var statusInterval = null;
		var percent = "0%";
		var stateInterval = null;
		function onVideoSelect(obj) {
// 			var videoFiles = obj.files;
// 			var videoFiles = $('input[name="file"]').val();
// 			if(null != videoFiles && undefined != videoFiles && videoFiles.length>0){
// 				var videoFile = videoFiles.files[0];
// 				alert(videoFile.fileSize);
// 				var name = videoFile.name;
// 				var size = videoFile.size;
// 				var type = name.substr(name.lastIndexOf("."),name.length).toLowerCase();
// 				var $f = $(videoFile.outerHTML);
// 				alert(videoFile.outerHTML);
// 				alert($f.attr("value"));
// 				判断类型和大小
// 				if(type!=".avi"  && type!=".mpg" && type!=".wmv"  && type!=".3gp" && type!=".mov" && type!=".mp4" && type!=".flv"  && type!=".vob" && type!=".swf"){
// 					alert("上传的视频"+type+"格式暂不支持,只支持avi、mpg、wmv、3gp、mp4、mov、vob、flv、swf格式，请重新上传！");
// 					return false;
// 				}else if(size>2048741824){
// 					alert("上传的视频过大，请上传小于2G的视频！");
// 					return false;
// 				}
// 				$("#uploadButton").attr("disabled",false);
// 				return true;
// 			}else{
// 				return false;
// 			}
			var filepath=$("input[name='file']").val(); 
		 	var extStart=filepath.lastIndexOf("."); 
	        var ext=filepath.substring(extStart,filepath.length).toLowerCase(); 
	        if(ext!=".avi"  && ext!=".mpg" && ext!=".wmv"  && ext!=".3gp" && ext!=".mov" && ext!=".mp4" && ext!=".flv"  && ext!=".vob" && ext!=".swf"){
				document.videoForm.reset();//清除
// 				alert($("input[name='file']").val());
				alert("上传的视频"+ext+"格式暂不支持,只支持avi、mpg、wmv、3gp、mp4、mov、vob、flv、swf格式，请重新上传！");
				return false;
	        } 
	        var fileSize = obj.files;
	        fileSize = fileSize[0].size;
	        if(fileSize > 524288000) {
	        	alert("上传的视频过大，请上传小于500M的视频！");
				return false;
	        }
// 	        getFileSize("file");        
// 	     	while(true){ 
// 	        if(img.fileSize>0){ 
// 		        if(img.fileSize>2048741824){       
// 		        	alert("上传的视频过大，请上传小于2G的视频！");
// 					return false;
// 		        } 
// 		            break; 
// 		        } 
// 	     	} 
			$("#uploadButton").attr("disabled",false);
	        return true;  

		};

		  function getFileSize(eleId) {
	            try {
	                var size = 0;
	                if ($.browser.msie) {//ie旧版浏览器
	                    var fileMgr = new ActiveXObject("Scripting.FileSystemObject");
	                    var filePath = $('#' + eleId)[0].value;
	                    var fileObj = fileMgr.getFile(filePath);
	                    size = fileObj.size; //byte
	                    size = size / 1024;//kb
	                    //size = size / 1024;//mb
	                } else {//其它浏览器
	                    size = $('#' + eleId)[0].files[0].size;//byte
	                    size = size / 1024;//kb
	                    //size = size / 1024;//mb
	                }
	                alert('上传文件大小为' + size + 'kb');
	            } catch (e) {
	                alert("错误：" + e);
	            } 
	        }
		
		//上传处理
		function startProgress() {
			jQuery("#progressBar").show();
			$("#theMeter").show();
			jQuery("#progressBarText").html(" 上传处理进度: 0%");
			stateInterval = setInterval(refreshUploadStatus,500);
			return true;
		}

		//取消上传处理
		function cancelProgress() {
			jQuery("#cancelUploadButton").disabled = true;
			//var ajaxW = new AjaxWrapper(false);
			/*ajaxW.putRequest(/cms/common.do?method=uploadFile',
					'cancelUpload=true',
					//因为form的提交，这可能不会执行
					function(responseText) {
						eval("uploadInfo = " + responseText);
						$('progressStatusText').innerHTML = ' 反馈状态: '
						+ uploadInfo.status;
						if (msgInfo.cancel == 'true') {
							alert('删除成功!');
							window.location.reload();
						}
						;
					});*/
		}

		//刷新上传状态
		function refreshUploadStatus() {
			var url = basePath+"/cms/video/getProgress.json";
			xjj.cms.ajax(url, null, true, function (data) {
				if(null != data.resultData && "" != data.resultData && undefined != data.resultData){
					var item = data.resultData;
					if(null != item) {
						var progressPercent = item.percent;
						jQuery("#progressBarText").html("上传处理进度: " + progressPercent);
						jQuery("#totalProgressBarBoxContent").width(progressPercent);
						if ("100%" == progressPercent) {
							if(null !=stateInterval){
								clearInterval(stateInterval);
							}
						}
					}else{
						if(null !=stateInterval){
							clearInterval(stateInterval);
						}
					}
				}else{
					if(null !=stateInterval){
						clearInterval(stateInterval);
					}
				}
			});

		}

		/**
		 * 删除文件
		 */
		var deleteFile = function(obj){
			var path = $(obj).attr("path");
			var fileName = $(obj).attr("fileName");
			if(xjj.cms.notEmpty(path) && xjj.cms.notEmpty(fileName)){
				var url = basePath+"/cms/video/removeFile.json";
				var data = {
					videoPath:path,
					fileName:fileName
				};
				xjj.cms.ajax(url, data, true, function (data) {
					jQuery("#box_text").show();
					jQuery("#videoForm").show();
					jQuery("#showFile").hide();
					jQuery("#showFileName").html("");
					jQuery("#progressBarText").html("");
					$("#totalProgressBarBoxContent").width(0);
					$("#theMeter").hide();
					var objFile= document.getElementById("file");
					jQuery("#uploadButton").attr("disabled",true);
					objFile.outerHTML=objFile.outerHTML.replace(/(value=\").+\"/i,"$1\"");
					jQuery(parent.document).find("#video_filepath").val("");
					jQuery(parent.document).find("#video_filename").val("");
					jQuery(parent.document).find("#video_ext").val("");
					jQuery(parent.document).find("#video_filesize").val("");
					jQuery(parent.document).find("#video_localname").val("");
				});
			}
		};
		jQuery(document).ready(function(){
			jQuery("#progressBar").hide();
			var editType = window.parent.EditType;
			if (editType == "edit") {//VIEW_MODEL层的数据初始化
				var filePath = $(parent.document).find("#video_filepath").val();
				var video_filename = $(parent.document).find("#video_filename").val();
				var video_localname = $(parent.document).find("#video_localname").val();
				if(xjj.cms.notEmpty(filePath)){
					$("#progressBar").show();
					$("#box_text").hide();
					$("#videoForm").hide();
					$("#showFile").show();
					$("#showFileName").html(video_localname);
					$("#a_deleteFle").attr("path",filePath);
					$("#a_deleteFle").attr("fileName",video_filename);
				}

			}
			jQuery("#uploadButton").click(function(){
				var _file = jQuery("#file")[0];
				var select = onVideoSelect(_file);
				if(select){
					var options = {
						type:'POST',
						success:function(data){
							//data = data.replace (new RegExp ("\"\\[", "g"), "\'[").replace (new RegExp ("\\]\"", "g"), "]\'").replace (new RegExp ("\"\\{", ""), "\'{").replace (new RegExp ("\\}\"", ""), "}\'");
							var jsonData= JSON.parse(data);
							//var jsonData = eval("0,(" + data + ")");
							if(0 != jsonData.resultCode){
								alert(jsonData.resultMsg);
							}else{
								var videoData =  jsonData.resultData;
								var visitPath = videoData.visitPath;
								var video_filename = videoData.newName;
								var video_ext = videoData.ext;
								var video_localname = videoData.oldName;
								var video_filesize= 0;
								var videoFiles = _file.files;
								if(null != videoFiles && undefined != videoFiles && videoFiles.length>0) {
									var videoFile = videoFiles[0];
									video_filesize = videoFile.size;
								}
								jQuery("#box_text").hide();
								jQuery("#videoForm").hide();
								jQuery("#showFile").show();
								jQuery("#showFileName").html(video_localname);
								jQuery("#a_deleteFle").attr("path",visitPath);
								jQuery("#a_deleteFle").attr("fileName",video_filename);
								jQuery(parent.document).find("#video_filepath").val(visitPath);
								jQuery(parent.document).find("#video_filename").val(video_filename);
								jQuery(parent.document).find("#video_ext").val(video_ext);
								jQuery(parent.document).find("#video_filesize").val(video_filesize);
								jQuery(parent.document).find("#video_localname").val(video_localname);
							}
						},
						error:function(){
							alert("上传视频失败，请联系管理员！");
						}
					};
					jQuery("#videoForm").ajaxSubmit(options);
					startProgress();
				}

				});
		});

	</script>
</body>
</html>