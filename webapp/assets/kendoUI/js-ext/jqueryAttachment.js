function initAtt(ctx,module,dir,dataId,setFiles){
	$.ajax({
		url : ctx+'/'+module+'/'+dir+'/common/attachment/init.json?dataId='+dataId,
		type : "POST",
		success :setFiles,
		error : function() {
			alert("获取附件失败！");
		}
	});
}