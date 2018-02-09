/*
* Kendo UI Web v2013.3.1119 (http://kendoui.com)
* Copyright 2013 Telerik AD. All rights reserved.
*
* Kendo UI Web commercial licenses may be obtained at
* https://www.kendoui.com/purchase/license-agreement/kendo-ui-web-commercial.aspx
* If you do not own a commercial license, this file shall be governed by the
* GNU General Public License (GPL) version 3.
* For GPL requirements, please review: http://www.gnu.org/copyleft/gpl.html
*/
kendo_module({
    id: "window",
    name: "Window",
    category: "web",
    description: "The Window widget displays content in a modal or non-modal HTML window.",
    depends: [ "draganddrop" ]
});

(function($, undefined) {
    var kendo = window.kendo,
        Widget = kendo.ui.Widget,
        Draggable = kendo.ui.Draggable,
        isPlainObject = $.isPlainObject,
        activeElement = kendo._activeElement,
        proxy = $.proxy,
        extend = $.extend,
        each = $.each,
        template = kendo.template,
        BODY = "body",
        templates,
        NS = ".kendoWindow",
        // classNames
        KWINDOW = ".k-window",
        KWINDOWTITLE = ".k-window-title",
        KWINDOWTITLEBAR = KWINDOWTITLE + "bar",
        KWINDOWCONTENT = ".k-window-content",
        KWINDOWRESIZEHANDLES = ".k-resize-handle",
        KOVERLAY = ".k-overlay",
        KCONTENTFRAME = "k-content-frame",
        LOADING = "k-loading",
        KHOVERSTATE = "k-state-hover",
        KFOCUSEDSTATE = "k-state-focused",
        MAXIMIZEDSTATE = "k-window-maximized",
        // constants
        VISIBLE = ":visible",
        HIDDEN = "hidden",
        CURSOR = "cursor",
        // events
        OPEN = "open",
        ACTIVATE = "activate",
        DEACTIVATE = "deactivate",
        CLOSE = "close",
        REFRESH = "refresh",
        RESIZE = "resize",
        DRAGSTART = "dragstart",
        DRAGEND = "dragend",
        ERROR = "error",
        OVERFLOW = "overflow",
        ZINDEX = "zIndex",
        MINIMIZE_MAXIMIZE = ".k-window-actions .k-i-minimize,.k-window-actions .k-i-maximize",
        KPIN = ".k-i-pin",
        KUNPIN = ".k-i-unpin",
        PIN_UNPIN = KPIN + "," + KUNPIN,
        TITLEBAR_BUTTONS = ".k-window-titlebar .k-window-action",
        isLocalUrl = kendo.isLocalUrl;

    function defined(x) {
        return (typeof x != "undefined");
    }

    function constrain(value, low, high) {
        return Math.max(Math.min(parseInt(value, 10), high === Infinity ? high : parseInt(high, 10)), parseInt(low, 10));
    }

    function sizingAction(actionId, callback) {
        return function() {
            var that = this,
                wrapper = that.wrapper,
                style = wrapper[0].style,
                options = that.options;

            if (options.isMaximized || options.isMinimized) {
                return;
            }

            that.restoreOptions = {
                width: style.width,
                height: style.height
            };

            wrapper
                .children(KWINDOWRESIZEHANDLES).hide().end()
                .children(KWINDOWTITLEBAR).find(MINIMIZE_MAXIMIZE).parent().hide()
                    .eq(0).before(templates.action({ name: "Restore" }));

            callback.call(that);

            if (actionId == "maximize") {
                that.wrapper.children(KWINDOWTITLEBAR).find(PIN_UNPIN).parent().hide();
            } else {
                that.wrapper.children(KWINDOWTITLEBAR).find(PIN_UNPIN).parent().show();
            }

            return that;
        };
    }

    var Window = Widget.extend({
        init: function(element, options) {
            var that = this,
                wrapper,
                offset = {},
                visibility, display, position,
                isVisible = false,
                content,
                windowContent,
                suppressActions = options && options.actions && !options.actions.length,
                id;

            Widget.fn.init.call(that, element, options);
            options = that.options;
            position = options.position;
            element = that.element;
            content = options.content;

            if (suppressActions) {
                options.actions = [];
            }

            that.appendTo = $($(options.appendTo)[0] || document.body);

            that._animations();

            if (content && !isPlainObject(content)) {
                content = options.content = { url: content };
            }

            // remove script blocks to prevent double-execution
            element.find("script").filter(function() {
                return !this.type || this.type.toLowerCase().indexOf("script") >= 0;
            }).remove();

            if (!element.parent().is(that.appendTo) && (options.position.top === undefined || options.position.left === undefined)) {
                if (element.is(VISIBLE)) {
                    offset = element.offset();
                    isVisible = true;
                } else {
                    visibility = element.css("visibility");
                    display = element.css("display");

                    element.css({ visibility: HIDDEN, display: "" });
                    offset = element.offset();
                    element.css({ visibility: visibility, display: display });
                }
            }

            if (!defined(options.visible) || options.visible === null) {
                options.visible = element.is(VISIBLE);
            }

            wrapper = that.wrapper = element.closest(KWINDOW);

            if (!element.is(".k-content") || !wrapper[0]) {
                element.addClass("k-window-content k-content");
                that._createWindow(element, options);
                wrapper = that.wrapper = element.closest(KWINDOW);

                that._dimensions();
            }

            if (position.top !== undefined) {
                position.top = position.top.toString();
            }

            if (position.left !== undefined) {
                position.left = position.left.toString();
            }

            wrapper.css({
                top: position.top || offset.top || "",
                left: position.left || offset.left || ""
            });

            if (options.pinned) {
                that.pin(true);
            }

            if (content) {
                that.refresh(content);
            }

            if (options.visible) {
                that.toFront();
            }

            windowContent = wrapper.children(KWINDOWCONTENT);
            that._tabindex(windowContent);

            if (options.visible && options.modal) {
                that._overlay(wrapper.is(VISIBLE)).css({ opacity: 0.5 });
            }

            wrapper
                .on("mouseenter" + NS, TITLEBAR_BUTTONS, function () { $(this).addClass(KHOVERSTATE); })
                .on("mouseleave" + NS, TITLEBAR_BUTTONS, function () { $(this).removeClass(KHOVERSTATE); })
                .on("click" + NS, "> " + TITLEBAR_BUTTONS, proxy(that._windowActionHandler, that));

            windowContent
                .on("keydown" + NS, proxy(that._keydown, that))
                .on("focus" + NS, function() { wrapper.addClass(KFOCUSEDSTATE); })
                .on("blur" + NS, function() { wrapper.removeClass(KFOCUSEDSTATE); });

            this._resizable();

            this._draggable();

            id = element.attr("id");
            if (id) {
                id = id + "_wnd_title";
                wrapper.children(KWINDOWTITLEBAR)
                       .children(KWINDOWTITLE)
                       .attr("id", id);

                windowContent
                    .attr({
                        "role": "dialog",
                        "aria-labelledby": id
                    });
            }

            wrapper.add(wrapper.children(".k-resize-handle," + KWINDOWTITLEBAR))
                    .on("mousedown" + NS, proxy(that.toFront, that));

            that.touchScroller = kendo.touchScroller(element);

            that._resizeHandler = function(e) {
                return that._onDocumentResize(e);
            };

            $(window).on("resize", that._resizeHandler);

            if (options.visible) {
                that.trigger(OPEN);
                that.trigger(ACTIVATE);
            }

            kendo.notify(that);
        },

        _dimensions: function() {
            var that = this,
                wrapper = that.wrapper,
                options = that.options,
                w = options.width,
                h = options.height,
                maxh = options.maxHeight;

            that.title(options.title);

            each(["minWidth","minHeight","maxWidth","maxHeight"], function(_, prop) {
                var value = options[prop];
                if (value && value != Infinity) {
                    wrapper.css(prop, value);
                }
            });

            if (maxh && maxh != Infinity) {
                that.element.css("maxHeight", maxh);
            }

            if (w) {
                if (w.toString().indexOf("%") > 0) {
                    wrapper.width(w);
                } else {
                    wrapper.width(constrain(w, options.minWidth, options.maxWidth));
                }
            }

            if (h) {
                if (h.toString().indexOf("%") > 0) {
                    wrapper.height(h);
                } else {
                    wrapper.height(constrain(h, options.minHeight, options.maxHeight));
                }
            }

            if (!options.visible) {
                wrapper.hide();
            }
        },

        _animations: function() {
            var options = this.options;

            if (options.animation === false) {
                options.animation = { open: { effects: {} }, close: { hide: true, effects: {} } };
            }
        },

        _resize: function() {
            kendo.resize(this.element.children());
        },

        _resizable: function() {
            var resizable = this.options.resizable;
            var wrapper = this.wrapper;

            if (resizable) {
                wrapper.on("dblclick" + NS, KWINDOWTITLEBAR, proxy(function(e) {
                    if (!$(e.target).closest(".k-window-action").length) {
                        this.toggleMaximization();
                    }
                }, this));

                each("n e s w se sw ne nw".split(" "), function(index, handler) {
                    wrapper.append(templates.resizeHandle(handler));
                });

                this.resizing = new WindowResizing(this);
            } else if (this.resizing) {
                wrapper
                    .off("dblclick" + NS)
                    .children(KWINDOWRESIZEHANDLES).remove();

                this.resizing.destroy();
                this.resizing = null;
            }
        },

        _draggable: function() {
            var draggable = this.options.draggable;

            if (draggable) {
                this.dragging = new WindowDragging(this, draggable.dragHandle || KWINDOWTITLEBAR);
            } else if (this.dragging) {
                this.dragging.destroy();
                this.dragging = null;
            }
        },

        setOptions: function(options) {
            Widget.fn.setOptions.call(this, options);
            this._animations();
            this._dimensions();
            this._resizable();
            this._draggable();
        },

        events:[
            OPEN,
            ACTIVATE,
            DEACTIVATE,
            CLOSE,
            REFRESH,
            RESIZE,
            DRAGSTART,
            DRAGEND,
            ERROR
        ],

        options: {
            name: "Window",
            animation: {
                open: {
                    effects: { zoom: { direction: "in" }, fade: { direction: "in" } },
                    duration: 350
                },
                close: {
                    effects: { zoom: { direction: "out", properties: { scale: 0.7 } }, fade: { direction: "out" } },
                    duration: 350,
                    hide: true
                }
            },
            title: "",
            actions: ["Close"],
            autoFocus: true,
            modal: false,
            resizable: true,
            draggable: true,
            minWidth: 90,
            minHeight: 50,
            maxWidth: Infinity,
            maxHeight: Infinity,
            pinned: false,
            position: {},
            content: null,
            visible: null,
            height: null,
            width: null
        },

        _closable: function() {
            return $.inArray("close", $.map(this.options.actions, function(x) { return x.toLowerCase(); })) > -1;
        },

        _keydown: function(e) {
            var that = this,
                options = that.options,
                keys = kendo.keys,
                keyCode = e.keyCode,
                wrapper = that.wrapper,
                offset, handled,
                distance = 10,
                isMaximized = that.options.isMaximized,
                newWidth, newHeight, w, h;

            if (e.target != e.currentTarget || that._closing) {
                return;
            }

            if (keyCode == keys.ESC && that._closable()) {
                that._close(true);
            }

            if (options.draggable && !e.ctrlKey && !isMaximized) {
                offset = kendo.getOffset(wrapper);

                if (keyCode == keys.UP) {
                    handled = wrapper.css("top", offset.top - distance);
                } else if (keyCode == keys.DOWN) {
                    handled = wrapper.css("top", offset.top + distance);
                } else if (keyCode == keys.LEFT) {
                    handled = wrapper.css("left", offset.left - distance);
                } else if (keyCode == keys.RIGHT) {
                    handled = wrapper.css("left", offset.left + distance);
                }
            }

            if (options.resizable && e.ctrlKey && !isMaximized) {
                if (keyCode == keys.UP) {
                    handled = true;
                    newHeight = wrapper.height() - distance;
                } else if (keyCode == keys.DOWN) {
                    handled = true;
                    newHeight = wrapper.height() + distance;
                } if (keyCode == keys.LEFT) {
                    handled = true;
                    newWidth = wrapper.width() - distance;
                } else if (keyCode == keys.RIGHT) {
                    handled = true;
                    newWidth = wrapper.width() + distance;
                }

                if (handled) {
                    w = constrain(newWidth, options.minWidth, options.maxWidth);
                    h = constrain(newHeight, options.minHeight, options.maxHeight);
                    
                    if (!isNaN(w)) {
                        wrapper.width(w);
                        that.options.width = w + "px";
                    }
                    if (!isNaN(h)) {
                        wrapper.height(h);
                        that.options.height = h + "px";
                    }

                    that.resize();
                }
            }

            if (handled) {
                e.preventDefault();
            }
        },

        _overlay: function (visible) {
            var overlay = this.appendTo.children(KOVERLAY),
                wrapper = this.wrapper;

            if (!overlay.length) {
                overlay = $("<div class='k-overlay' />");
            }

            overlay
                .insertBefore(wrapper[0])
                .toggle(visible)
                .css(ZINDEX, parseInt(wrapper.css(ZINDEX), 10) - 1);

            return overlay;
        },

        _windowActionHandler: function (e) {
            var target = $(e.target).closest(".k-window-action").find(".k-icon"),
                that = this;

            if (that._closing) {
                return;
            }

            each({
                "k-i-close": function() { that._close(true); },
                "k-i-maximize": that.maximize,
                "k-i-minimize": that.minimize,
                "k-i-restore": that.restore,
                "k-i-refresh": that.refresh,
                "k-i-pin": that.pin,
                "k-i-unpin": that.unpin
            }, function (commandName, handler) {
                if (target.hasClass(commandName)) {
                    e.preventDefault();
                    handler.call(that);
                    return false;
                }
            });
        },

        _modals: function() {
            var that = this;

            return $(KWINDOW).filter(function() {
                var wnd = $(this);
                var options = that._object(wnd).options;

                return options.modal && options.visible && wnd.is(VISIBLE);
            }).sort(function(a, b){
                return +$(a).css("zIndex") - +$(b).css("zIndex");
            });
        },

        _object: function(element) {
            var content = element.children(KWINDOWCONTENT);

            return content.data("kendoWindow") || content.data("kendo" + this.options.name);
        },

        center: function () {
            var that = this,
                wrapper = that.wrapper,
                documentWindow = $(window);

            if (that.options.isMaximized) {
                return that;
            }

            wrapper.css({
                left: documentWindow.scrollLeft() + Math.max(0, (documentWindow.width() - wrapper.width()) / 2),
                top: documentWindow.scrollTop() + Math.max(0, (documentWindow.height() - wrapper.height()) / 2)
            });

            return that;
        },

        title: function (text) {
            var that = this,
                wrapper = that.wrapper,
                options = that.options,
                titleBar = wrapper.children(KWINDOWTITLEBAR),
                title = titleBar.children(KWINDOWTITLE),
                titleBarHeight = titleBar.outerHeight();

            if (!arguments.length) {
                return title.text();
            }

            if (text === false) {
                wrapper.addClass("k-window-titleless");
                titleBar.remove();
            } else {
                if (!titleBar.length) {
                    wrapper.prepend(templates.titlebar(extend(templates, options)));
                }

                wrapper.css("padding-top", titleBarHeight);
                titleBar.css("margin-top", -titleBarHeight);
            }

            title.text(text);
            that.options.title = text;

            return that;
        },

        content: function (html) {
            var content = this.wrapper.children(KWINDOWCONTENT),
                scrollContainer = content.children(".km-scroll-container");

            content = scrollContainer[0] ? scrollContainer : content;

            if (!html) {
                return content.html();
            }

            kendo.destroy(this.element.children());

            content.html(html);
            return this;
        },

        open: function () {
            var that = this,
                wrapper = that.wrapper,
                options = that.options,
                showOptions = options.animation.open,
                contentElement = wrapper.children(KWINDOWCONTENT),
                initialOverflow = contentElement.css(OVERFLOW),
                overlay;

            if (!that.trigger(OPEN)) {
                if (that._closing) {
                    wrapper.kendoStop(true, true);
                }

                that._closing = false;

                that.toFront();

                if (options.autoFocus) {
                    that.element.focus();
                }

                options.visible = true;

                if (options.modal) {
                    overlay = that._overlay(false);

                    overlay.kendoStop(true, true);

                    if (showOptions.duration && kendo.effects.Fade) {
                        var overlayFx = kendo.fx(overlay).fadeIn();
                        overlayFx.duration(showOptions.duration || 0);
                        overlayFx.endValue(0.5);
                        overlayFx.play();
                    } else {
                        overlay.css("opacity", 0.5);
                    }

                    overlay.show();
                }

                if (!wrapper.is(VISIBLE)) {
                    contentElement.css(OVERFLOW, HIDDEN);
                    wrapper.show().kendoStop().kendoAnimate({
                        effects: showOptions.effects,
                        duration: showOptions.duration,
                        complete: function() {
                            if (options.autoFocus) {
                                that.element.focus();
                            }
                            that.trigger(ACTIVATE);
                            contentElement.css(OVERFLOW, initialOverflow);
                        }
                    });
                }
            }

            if (options.isMaximized) {
                that._documentScrollTop = $(document).scrollTop();
                $("html, body").css(OVERFLOW, HIDDEN);
            }

            return that;
        },

        _removeOverlay: function(suppressAnimation) {
            var modals = this._modals();
            var options = this.options;
            var hideOverlay = options.modal && !modals.length;
            var overlay = options.modal ? this._overlay(true) : $(undefined);
            var hideOptions = options.animation.close;

            if (hideOverlay) {
                if (!suppressAnimation && hideOptions.duration && kendo.effects.Fade) {
                    var overlayFx = kendo.fx(overlay).fadeOut();
                    overlayFx.duration(hideOptions.duration || 0);
                    overlayFx.startValue(0.5);
                    overlayFx.play();
                } else {
                    this._overlay(false).remove();
                }
            } else if (modals.length) {
                this._object(modals.last())._overlay(true);
            }
        },

        _close: function(userTriggered) {
            var that = this,
                wrapper = that.wrapper,
                options = that.options,
                showOptions = options.animation.open,
                hideOptions = options.animation.close;

            if (wrapper.is(VISIBLE) && !that.trigger(CLOSE, { userTriggered: !!userTriggered })) {
                that._closing = true;
                options.visible = false;

                $(KWINDOW).each(function(i, element) {
                    var contentElement = $(element).children(KWINDOWCONTENT);

                    // Remove overlay set by toFront
                    if (element != wrapper && contentElement.find("> ." + KCONTENTFRAME).length > 0) {
                        contentElement.children(KOVERLAY).remove();
                    }
                });

                this._removeOverlay();

                wrapper.kendoStop().kendoAnimate({
                    effects: hideOptions.effects || showOptions.effects,
                    reverse: hideOptions.reverse === true,
                    duration: hideOptions.duration,
                    complete: function() {
                        wrapper.hide().css("opacity","");
                        that.trigger(DEACTIVATE);
                        var lastModal = that._object(that._modals().last());
                        if (lastModal) {
                            lastModal.toFront();
                        }
                    }
                });
            }

            if (that.options.isMaximized) {
                $("html, body").css(OVERFLOW, "");
                if (that._documentScrollTop && that._documentScrollTop > 0) {
                    $(document).scrollTop(that._documentScrollTop);
                }
            }
        },

        close: function () {
            this._close(false);
            return this;
        },

        _actionable: function(element) {
            return $(element).is(TITLEBAR_BUTTONS + "," + TITLEBAR_BUTTONS + " .k-icon,:input,a");
        },

        _shouldFocus: function(target) {
            var active = activeElement(),
                element = this.element;

            return this.options.autoFocus &&
                    !$(active).is(element) &&
                    !this._actionable(target) &&
                    (!element.find(active).length || !element.find(target).length);
        },

        toFront: function (e) {
            var that = this,
                wrapper = that.wrapper,
                currentWindow = wrapper[0],
                zIndex = +wrapper.css(ZINDEX),
                originalZIndex = zIndex,
                target = (e && e.target) || null;

            $(KWINDOW).each(function(i, element) {
                var windowObject = $(element),
                    zIndexNew = windowObject.css(ZINDEX),
                    contentElement = windowObject.children(KWINDOWCONTENT);

                if (!isNaN(zIndexNew)) {
                    zIndex = Math.max(+zIndexNew, zIndex);
                }

                // Add overlay to windows with iframes and lower z-index to prevent
                // trapping of events when resizing / dragging
                if (element != currentWindow && contentElement.find("> ." + KCONTENTFRAME).length > 0) {
                    contentElement.append(templates.overlay);
                }
            });

            if (!wrapper[0].style.zIndex || originalZIndex < zIndex) {
                wrapper.css(ZINDEX, zIndex + 2);
            }
            that.element.find("> .k-overlay").remove();

            if (that._shouldFocus(target)) {
                that.element.focus();

                var scrollTop = $(window).scrollTop(),
                    windowTop = parseInt(wrapper.position().top, 10);

                if (windowTop > 0 && windowTop < scrollTop) {
                    if (scrollTop > 0) {
                        $(window).scrollTop(windowTop);
                    } else {
                        wrapper.css("top", scrollTop);
                    }
                }
            }

            return that;
        },

        toggleMaximization: function () {
            if (this._closing) {
                return this;
            }

            return this[this.options.isMaximized ? "restore" : "maximize"]();
        },

        restore: function () {
            var that = this;
            var options = that.options;
            var minHeight = options.minHeight;
            var restoreOptions = that.restoreOptions;

            if (!options.isMaximized && !options.isMinimized) {
                return that;
            }

            if (minHeight && minHeight != Infinity) {
                that.wrapper.css("min-height", minHeight);
            }

            that.wrapper
                .css({
                    position: options.pinned ? "fixed" : "absolute",
                    left: restoreOptions.left,
                    top: restoreOptions.top,
                    width: restoreOptions.width,
                    height: restoreOptions.height
                })
                .removeClass(MAXIMIZEDSTATE)
                .find(".k-window-content,.k-resize-handle").show().end()
                .find(".k-window-titlebar .k-i-restore").parent().remove().end().end()
                .find(MINIMIZE_MAXIMIZE).parent().show().end().end()
                .find(PIN_UNPIN).parent().show();

            that.options.width = restoreOptions.width;
            that.options.height = restoreOptions.height;

            $("html, body").css(OVERFLOW, "");
            if (this._documentScrollTop && this._documentScrollTop > 0) {
                $(document).scrollTop(this._documentScrollTop);
            }

            options.isMaximized = options.isMinimized = false;

            that.resize();

            return that;
        },

        maximize: sizingAction("maximize", function() {
            var that = this,
                wrapper = that.wrapper,
                position = wrapper.position();

            extend(that.restoreOptions, {
                left: position.left,
                top: position.top
            });

            wrapper.css({
                    left: 0,
                    top: 0,
                    position: "fixed"
                })
                .addClass(MAXIMIZEDSTATE);

            this._documentScrollTop = $(document).scrollTop();
            $("html, body").css(OVERFLOW, HIDDEN);

            that.options.isMaximized = true;

            that._onDocumentResize();
        }),

        minimize: sizingAction("minimize", function() {
            var that = this;

            that.wrapper.css({
                height: "",
                minHeight: ""
            });

            that.element.hide();

            that.options.isMinimized = true;
        }),

        pin: function(force) {
            var that = this,
                win = $(window),
                wrapper = that.wrapper,
                top = parseInt(wrapper.css("top"), 10),
                left = parseInt(wrapper.css("left"), 10);

            if (force || !that.options.pinned && !that.options.isMaximized) {
                wrapper.css({position: "fixed", top: top - win.scrollTop(), left: left - win.scrollLeft()});
                wrapper.children(KWINDOWTITLEBAR).find(KPIN).addClass("k-i-unpin").removeClass("k-i-pin");

                that.options.pinned = true;
            }
        },

        unpin: function() {
            var that = this,
                win = $(window),
                wrapper = that.wrapper,
                top = parseInt(wrapper.css("top"), 10),
                left = parseInt(wrapper.css("left"), 10);

            if (that.options.pinned && !that.options.isMaximized) {
                wrapper.css({position: "", top: top + win.scrollTop(), left: left + win.scrollLeft()});
                wrapper.children(KWINDOWTITLEBAR).find(KUNPIN).addClass("k-i-pin").removeClass("k-i-unpin");

                that.options.pinned = false;
            }
        },

        _onDocumentResize: function () {
            var that = this,
                wrapper = that.wrapper,
                wnd = $(window),
                w, h;

            if (!that.options.isMaximized) {
                return;
            }

            w = wnd.width();
            h = wnd.height() - parseInt(wrapper.css("padding-top"), 10);

            wrapper.css({
                    width: w,
                    height: h
                });
            that.options.width = w;
            that.options.height = h;

            that.resize();
        },

        refresh: function (options) {
            var that = this,
                initOptions = that.options,
                element = $(that.element),
                iframe,
                showIframe,
                url;

            if (!isPlainObject(options)) {
                options = { url: options };
            }

            options = extend({}, initOptions.content, options);

            showIframe = defined(initOptions.iframe) ? initOptions.iframe : options.iframe;

            url = options.url;

            if (url) {
                if (!defined(showIframe)) {
                    showIframe = !isLocalUrl(url);
                }

                if (!showIframe) {
                    // perform AJAX request
                    that._ajaxRequest(options);
                } else {
                    iframe = element.find("." + KCONTENTFRAME)[0];

                    if (iframe) {
                        // refresh existing iframe
                        iframe.src = url || iframe.src;
                    } else {
                        // render new iframe
                        element.html(templates.contentFrame(extend({}, initOptions, { content: options })));
                    }

                    element.find("." + KCONTENTFRAME)
                        .unbind("load" + NS)
                        .on("load" + NS, function(){
                            that.trigger(REFRESH);
                        });
                }
            } else {
                if (options.template) {
                    // refresh template
                    that.content(template(options.template)({}));
                }

                that.trigger(REFRESH);
            }

            return that;
        },

        _ajaxRequest: function (options) {
            var that = this,
                contentTemplate = options.template,
                refreshIcon = that.wrapper.find(".k-window-titlebar .k-i-refresh"),
                loadingIconTimeout = setTimeout(function () {
                    refreshIcon.addClass(LOADING);
                }, 100);

            $.ajax(extend({
                type: "GET",
                dataType: "html",
                cache: false,
                error: function (xhr, status) {
                    that.trigger(ERROR, {
                        status: status,
                        xhr: xhr
                    });
                },
                complete: function () {
                    clearTimeout(loadingIconTimeout);
                    refreshIcon.removeClass(LOADING);
                },
                success: function (data) {
                    if (contentTemplate) {
                        data = template(contentTemplate)(data || {});
                    }

                    that.content(data);
                    that.element.prop("scrollTop", 0);

                    that.trigger(REFRESH);
                }
            }, options));
        },

        destroy: function () {
            var wrapper = this.wrapper;

            Widget.fn.destroy.call(this);

            kendo.destroy(wrapper);

            if (this.resizing) {
                this.resizing.destroy();
            }

            if (this.dragging) {
                this.dragging.destroy();
            }

            this.element.children("iframe").remove();
            wrapper.find(".k-resize-handle,.k-window-titlebar").off(NS);
            wrapper.remove().off(NS);

            $(window).off("resize", this._resizeHandler);

            this._removeOverlay(true);
        },

        _createWindow: function() {
            var that = this,
                contentHtml = that.element,
                options = that.options,
                iframeSrcAttributes,
                wrapper,
                isRtl = kendo.support.isRtl(contentHtml);

            if (options.scrollable === false) {
                contentHtml.attr("style", "overflow:hidden;");
            }

            wrapper = $(templates.wrapper(options));

            if (options.title !== false) {
                wrapper.append(templates.titlebar(extend(templates, options)));
            }

            // Collect the src attributes of all iframes and then set them to empty string.
            // This seems to fix this IE9 "feature": http://msdn.microsoft.com/en-us/library/gg622929%28v=VS.85%29.aspx?ppud=4
            iframeSrcAttributes = contentHtml.find("iframe:not(.k-content)").map(function() {
                var src = this.getAttribute("src");
                this.src = "";
                return src;
            });

            // Make sure the wrapper is appended to the body only once. IE9+ will throw exceptions if you move iframes in DOM
            wrapper
                .toggleClass("k-rtl", isRtl)
                .appendTo(that.appendTo)
                .append(contentHtml)
                .find("iframe:not(.k-content)").each(function(index) {
                   // Restore the src attribute of the iframes when they are part of the live DOM tree
                   this.src = iframeSrcAttributes[index];
                });

            wrapper.find(".k-window-title")
                .css(isRtl ? "left" : "right", wrapper.find(".k-window-actions").outerWidth() + 10);

            contentHtml.show();

            contentHtml.find("[data-role=editor]").each(function() {
                var editor = $(this).data("kendoEditor");

                if (editor) {
                    editor.refresh();
                }
            });
        }
    });

    templates = {
        wrapper: template("<div class='k-widget k-window' />"),
        action: template(
            "<a role='button' href='\\#' class='k-window-action k-link'>" +
                "<span role='presentation' class='k-icon k-i-#= name.toLowerCase() #'>#= name #</span>" +
            "</a>"
        ),
        titlebar: template(
            "<div class='k-window-titlebar k-header'>&nbsp;" +
                "<span class='k-window-title'>#= title #</span>" +
                "<div class='k-window-actions'>" +
                "# for (var i = 0; i < actions.length; i++) { #" +
                    "#= action({ name: actions[i] }) #" +
                "# } #" +
                "</div>" +
            "</div>"
        ),
        overlay: "<div class='k-overlay' />",
        contentFrame: template(
            "<iframe frameborder='0' title='#= title #' class='" + KCONTENTFRAME + "' " +
                "src='#= content.url #'>" +
                    "This page requires frames in order to show content" +
            "</iframe>"
        ),
        resizeHandle: template("<div class='k-resize-handle k-resize-#= data #'></div>")
    };


    function WindowResizing(wnd) {
        var that = this;

        that.owner = wnd;
        that._draggable = new Draggable(wnd.wrapper, {
            filter: KWINDOWRESIZEHANDLES,
            group: wnd.wrapper.id + "-resizing",
            dragstart: proxy(that.dragstart, that),
            drag: proxy(that.drag, that),
            dragend: proxy(that.dragend, that)
        });
    }

    WindowResizing.prototype = {
        dragstart: function (e) {
            var that = this,
                wnd = that.owner,
                wrapper = wnd.wrapper;

            that.elementPadding = parseInt(wnd.wrapper.css("padding-top"), 10);
            that.initialCursorPosition = kendo.getOffset(wrapper, "position");

            that.resizeDirection = e.currentTarget.prop("className").replace("k-resize-handle k-resize-", "");

            that.initialSize = {
                width: wrapper.width(),
                height: wrapper.height()
            };

            that.containerOffset = kendo.getOffset(wnd.appendTo);

            wrapper
                .append(templates.overlay)
                .children(KWINDOWRESIZEHANDLES).not(e.currentTarget).hide();

            $(BODY).css(CURSOR, e.currentTarget.css(CURSOR));
        },
        drag: function (e) {
            var that = this,
                wnd = that.owner,
                wrapper = wnd.wrapper,
                options = wnd.options,
                direction = that.resizeDirection,
                containerOffset = that.containerOffset,
                initialPosition = that.initialCursorPosition,
                initialSize = that.initialSize,
                newWidth, newHeight,
                windowBottom, windowRight,
                x = Math.max(e.x.location, containerOffset.left),
                y = Math.max(e.y.location, containerOffset.top);

            if (direction.indexOf("e") >= 0) {
                newWidth = x - initialPosition.left;

                wrapper.width(constrain(newWidth, options.minWidth, options.maxWidth));
            } else if (direction.indexOf("w") >= 0) {
                windowRight = initialPosition.left + initialSize.width;
                newWidth = constrain(windowRight - x, options.minWidth, options.maxWidth);

                wrapper.css({
                    left: windowRight - newWidth - containerOffset.left,
                    width: newWidth
                });
            }

            if (direction.indexOf("s") >= 0) {
                newHeight = y - initialPosition.top - that.elementPadding;

                wrapper.height(constrain(newHeight, options.minHeight, options.maxHeight));
            } else if (direction.indexOf("n") >= 0) {
                windowBottom = initialPosition.top + initialSize.height;
                newHeight = constrain(windowBottom - y, options.minHeight, options.maxHeight);

                wrapper.css({
                    top: windowBottom - newHeight - containerOffset.top,
                    height: newHeight
                });
            }

            if (newWidth) {
                wnd.options.width = newWidth + "px";
            }
            if (newHeight) {
                wnd.options.height = newHeight + "px";
            }

            wnd.resize();
        },
        dragend: function (e) {
            var that = this,
                wnd = that.owner,
                wrapper = wnd.wrapper;

            wrapper
                .find(KOVERLAY).remove().end()
                .children(KWINDOWRESIZEHANDLES).not(e.currentTarget).show();

            $(BODY).css(CURSOR, "");

            if (wnd.touchScroller) {
               wnd.touchScroller.reset();
            }
            if (e.keyCode == 27) {
                wrapper.css(that.initialCursorPosition)
                    .css(that.initialSize);
            }

            return false;
        },
        destroy: function() {
            this._draggable.destroy();
        }
    };

    function WindowDragging(wnd, dragHandle) {
        var that = this;

        that.owner = wnd;
        that._draggable = new Draggable(wnd.wrapper, {
            filter: dragHandle,
            group: wnd.wrapper.id + "-moving",
            dragstart: proxy(that.dragstart, that),
            drag: proxy(that.drag, that),
            dragend: proxy(that.dragend, that),
            dragcancel: proxy(that.dragcancel, that)
        });

        that._draggable.userEvents.stopPropagation = false;
    }

    WindowDragging.prototype = {
        dragstart: function (e) {
            var wnd = this.owner,
                element = wnd.element,
                actions = element.find(".k-window-actions"),
                containerOffset = kendo.getOffset(wnd.appendTo);

            wnd.trigger(DRAGSTART);

            wnd.initialWindowPosition = kendo.getOffset(wnd.wrapper, "position");

            wnd.startPosition = {
                left: e.x.client - wnd.initialWindowPosition.left,
                top: e.y.client - wnd.initialWindowPosition.top
            };

            if (actions.length > 0) {
                wnd.minLeftPosition = actions.outerWidth() + parseInt(actions.css("right"), 10) - element.outerWidth();
            } else {
                wnd.minLeftPosition =  20 - element.outerWidth(); // at least 20px remain visible
            }

            wnd.minLeftPosition -= containerOffset.left;
            wnd.minTopPosition = -containerOffset.top;

            wnd.wrapper
                .append(templates.overlay)
                .children(KWINDOWRESIZEHANDLES).hide();

            $(BODY).css(CURSOR, e.currentTarget.css(CURSOR));
        },

        drag: function (e) {
            var wnd = this.owner,
                coordinates = {
                    left: Math.max(e.x.client - wnd.startPosition.left, wnd.minLeftPosition),
                    top: Math.max(e.y.client - wnd.startPosition.top, wnd.minTopPosition)
                };

            $(wnd.wrapper).css(coordinates);
        },

        _finishDrag: function() {
            var wnd = this.owner;

            wnd.wrapper
                .children(KWINDOWRESIZEHANDLES).toggle(!wnd.options.isMinimized).end()
                .find(KOVERLAY).remove();

            $(BODY).css(CURSOR, "");
        },

        dragcancel: function (e) {
            this._finishDrag();

            e.currentTarget.closest(KWINDOW).css(this.owner.initialWindowPosition);
        },

        dragend: function () {
            this._finishDrag();

            this.owner.trigger(DRAGEND);

            return false;
        },
        destroy: function() {
            this._draggable.destroy();
        }
    };

    kendo.ui.plugin(Window);

})(window.kendo.jQuery);
