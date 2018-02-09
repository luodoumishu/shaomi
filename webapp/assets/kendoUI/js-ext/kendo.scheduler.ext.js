(function($) {
    var kendo = window.kendo,
        ui = kendo.ui,
		Scheduler = ui.Scheduler;
	
	var Scheduler = Scheduler.extend({
		refresh: function(e) {
			var view = this.view();

            this._progress(false);

            e = e || {};

            if (!view) {
                return;
            }

            if (e && e.action === "itemchange" && (this._editor.editable || this._preventRefresh)) { // skip rebinding if editing is in progress
                return;
            }

            if (this.trigger("dataBinding", { action: e.action || "rebind", index: e.index, items: e.items })) {
                return;
            }

            if (!(e && e.action === "resize") && this._editor) {
                this._editor.close();
            }

            this._data = this.dataSource.expand(view.startDate(), view.endDate());

            view.render(this._data);

            this.trigger("dataBound",e);
		}
	});

	ui.plugin(Scheduler);

})(jQuery);

