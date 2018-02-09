(function($) {
    var kendo = window.kendo,
        ui = kendo.ui,
        Grid = ui.Grid;
	
	var Grid = Grid.extend({
		removeRow: function(row) {
			//使用jquery的ajax提交删除，这里不需要再度确认
//          if (!that._confirmation()) {
//              return;
//          }
            this._removeRow(row);
        }
	});

	ui.plugin(Grid);

})(jQuery);

