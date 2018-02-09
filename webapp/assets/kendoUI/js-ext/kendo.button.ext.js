(function($) {
    var kendo = window.kendo,
        ui = kendo.ui,
        Button = ui.Button;
	
	var Button = Button.extend({
		_graphics: function() {
            var that = this,
                element = that.element,
                options = that.options,
                icon = options.icon,
                spriteCssClass = options.spriteCssClass,
                imageUrl = options.imageUrl,
                span, img, isEmpty;

            if (spriteCssClass || imageUrl || icon) {
                isEmpty = true;

                element.contents().not("span.k-sprite").not("span.k-icon").not("img.k-image").each(function(idx, el){
                    if (el.nodeType == 1 || el.nodeType == 3 && el.nodeValue.replace(/(^\s*)|(\s*$)/g, "").length > 0) {
                        isEmpty = false;
                    }
                });

                if (isEmpty) {
                    element.addClass("k-button-icon");
                } else {
                    element.addClass("k-button-icontext");
                }
            }

            if (icon) {
                span = element.children("span.k-icon").first();
                if (!span[0]) {
                    span = $('<span class="k-icon"></span>').prependTo(element);
                }
                span.addClass("k-i-" + icon);
            } else if (spriteCssClass) {
                span = element.children("span.k-sprite").first();
                if (!span[0]) {
                    span = $('<span class="k-sprite"></span>').prependTo(element);
                }
                span.addClass(spriteCssClass);
            } else if (imageUrl) {
                img = element.children("img.k-image").first();
                if (!img[0]) {
                    img = $('<img alt="icon" class="k-image" />').prependTo(element);
                }
                img.attr("src", imageUrl);
            }
        }
	});

	ui.plugin(Button);

})(jQuery);

