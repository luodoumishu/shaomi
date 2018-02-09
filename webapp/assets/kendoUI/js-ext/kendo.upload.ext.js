(function($) {
    var kendo = window.kendo,
        ui = kendo.ui,
        logToConsole = kendo.logToConsole,
		Upload = ui.Upload;
	
	var Upload = Upload.extend({
		_showUploadButton: function() {
		},
		_onUploadError: function(e, xhr) {
            var fileEntry = $(e.target).closest(".k-file");
            var uploadPercentage = $('.k-upload-pct', fileEntry);

            this._fileState(fileEntry, "failed");
            fileEntry.removeClass('k-file-progress').addClass('k-file-error');
            $('.k-progress', fileEntry).width("100%");

            if (uploadPercentage.length > 0) {
                uploadPercentage.empty().removeClass('k-upload-pct').addClass('k-icon k-warning');
            } else {
                $('.k-upload-status', fileEntry).prepend("<span class='k-icon k-warning'></span>");
            }

            this._updateHeaderUploadStatus();
            this._fileAction(fileEntry, "remove");

            this.trigger("error", {
                operation: "upload",
                files: fileEntry.data("fileNames"),
                XMLHttpRequest: xhr
            });

            logToConsole("Server response: " + xhr.responseText);

            this._checkAllComplete();
        }
	});
	
	ui.plugin(Upload);

})(jQuery);

