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
    id: "columnmenu",
    name: "Column Menu",
    category: "framework",
    depends: [ "popup", "filtermenu", "menu" ],
    advanced: true
});

(function($, undefined) {
    var kendo = window.kendo,
        ui = kendo.ui,
        proxy = $.proxy,
        extend = $.extend,
        grep = $.grep,
        map = $.map,
        inArray = $.inArray,
        ACTIVE = "k-state-selected",
        ASC = "asc",
        DESC = "desc",
        CHANGE = "change",
        INIT = "init",
        SELECT = "select",
        POPUP = "kendoPopup",
        FILTERMENU = "kendoFilterMenu",
        MENU = "kendoMenu",
        NS = ".kendoColumnMenu",
        Widget = ui.Widget;

    function trim(text) {
        return $.trim(text).replace(/&nbsp;/gi, "");
    }

    var ColumnMenu = Widget.extend({
        init: function(element, options) {
            var that = this,
                link;

            Widget.fn.init.call(that, element, options);

            element = that.element;
            options = that.options;
            that.owner = options.owner;
            that.dataSource = options.dataSource;

            that.field = element.attr(kendo.attr("field"));

            link = element.find(".k-header-column-menu");

            if (!link[0]) {
                link = element.prepend('<a class="k-header-column-menu" href="#"><span class="k-icon k-i-arrowhead-s"/></a>').find(".k-header-column-menu");
            }

            that.link = link
                .attr("tabindex", -1)
                .on("click" + NS, proxy(that._click, that));

            that.wrapper = $('<div class="k-column-menu"/>');
        },

        _init: function() {
            var that = this;

            that.pane = that.element.closest(kendo.roleSelector("pane")).data("kendoMobilePane");
            if (that.pane) {
                that._isMobile = true;
            }

            if (that._isMobile) {
                that._createMobileMenu();
            } else {
                that._createMenu();
            }

            that._sort();

            that._columns();

            that._filter();

            that.trigger(INIT, { field: that.field, container: that.wrapper });
        },

        events: [ INIT ],

        options: {
            name: "ColumnMenu",
            messages: {
                sortAscending: "Sort Ascending",
                sortDescending: "Sort Descending",
                filter: "Filter",
                columns: "Columns",
                done: "Done",
                settings: "Column Settings"
            },
            filter: "",
            columns: true,
            sortable: true,
            filterable: true,
            animations: {
                left: "slide"
            }
        },

        _createMenu: function() {
            var that = this,
                options = that.options;

            that.wrapper.html(kendo.template(template)({
                ns: kendo.ns,
                messages: options.messages,
                sortable: options.sortable,
                filterable: options.filterable,
                columns: that._ownerColumns(),
                showColumns: options.columns
            }));

            that.popup = that.wrapper[POPUP]({
                anchor: that.link,
                open: proxy(that._open, that),
                activate: proxy(that._activate, that),
                close: that.options.closeCallback
            }).data(POPUP);

            that.menu = that.wrapper.children()[MENU]({
                orientation: "vertical",
                closeOnClick: false
            }).data(MENU);
        },

        _createMobileMenu: function() {
            var that = this,
                options = that.options;

            var html = kendo.template(mobileTemplate)({
                ns: kendo.ns,
                field: that.field,
                messages: options.messages,
                sortable: options.sortable,
                filterable: options.filterable,
                columns: that._ownerColumns(),
                showColumns: options.columns
            });

            that.view = that.pane.append(html);

            that.wrapper = that.view.element.find(".k-column-menu");

            that.menu = new MobileMenu(that.wrapper.children(), {
                pane: that.pane
            });

            that.view.element.on("click", ".k-done", function(e) {
                that.close();
                e.preventDefault();
            });
        },

        destroy: function() {
            var that = this;

            Widget.fn.destroy.call(that);

            if (that.filterMenu) {
                that.filterMenu.destroy();
            }

            if (that._refreshHandler) {
                that.dataSource.unbind(CHANGE, that._refreshHandler);
            }

            if (that.options.columns) {
                that.owner.unbind("columnShow", that._updateColumnsMenuHandler);
                that.owner.unbind("columnHide", that._updateColumnsMenuHandler);
            }

            if (that.menu) {
                that.menu.element.off(NS);
                that.menu.destroy();
            }

            that.wrapper.off(NS);

            if (that.popup) {
                that.popup.destroy();
            }

            if (that.view) {
                that.view.purge();
            }

            that.link.off(NS);
        },

        close: function() {
            this.menu.close();
            if (this.popup) {
                this.popup.close();
                this.popup.element.off("keydown" + NS);
            }
        },

        _click: function(e) {
            e.preventDefault();
            e.stopPropagation();

            var options = this.options;

            if (options.filter && this.element.is(!options.filter)) {
                return;
            }

            if (!this.popup && !this.pane) {
                this._init();
            }

            if (this._isMobile) {
                this.pane.navigate(this.view, this.options.animations.left);
            } else {
                this.popup.toggle();
            }
        },

        _open: function() {
            var that = this;
            $(".k-column-menu").not(that.wrapper).each(function() {
                $(this).data(POPUP).close();
            });
            that.popup.element.on("keydown" + NS, function(e) {
                if (e.keyCode == kendo.keys.ESC) {
                    that.close();
                }
            });
        },

        _activate: function() {
            this.menu.element.focus();
        },

        _ownerColumns: function() {
            var columns = this.owner.columns,
                menuColumns = grep(columns, function(col) {
                    var result = true,
                        title = trim(col.title || "");

                    if (col.menu === false || (!col.field && !title.length)) {
                        result = false;
                    }

                    return result;
                });

            return map(menuColumns, function(col) {
                return {
                    originalField: col.field,
                    field: col.field || col.title,
                    title: col.title || col.field,
                    hidden: col.hidden,
                    index: inArray(col, columns)
                };
            });
        },

        _sort: function() {
            var that = this;

            if (that.options.sortable) {
                that.refresh();

                that._refreshHandler = proxy(that.refresh, that);

                that.dataSource.bind(CHANGE, that._refreshHandler);

                that.menu.bind(SELECT, function(e) {
                    var item = $(e.item),
                        dir;

                    if (item.hasClass("k-sort-asc")) {
                        dir = ASC;
                    } else if (item.hasClass("k-sort-desc")) {
                        dir = DESC;
                    }

                    if (!dir) {
                        return;
                    }

                    item.parent().find(".k-sort-" + (dir == ASC ? DESC : ASC)).removeClass(ACTIVE);

                    that._sortDataSource(item, dir);

                    that.close();
                });
            }
        },

        _sortDataSource: function(item, dir) {
            var that = this,
                sortable = that.options.sortable,
                dataSource = that.dataSource,
                idx,
                length,
                sort = dataSource.sort() || [];

            if (item.hasClass(ACTIVE) && sortable && sortable.allowUnsort !== false) {
                item.removeClass(ACTIVE);
                dir = undefined;
            } else {
                item.addClass(ACTIVE);
            }

            if (sortable === true || sortable.mode === "single") {
                sort = [ { field: that.field, dir: dir } ];
            } else {
                for (idx = 0, length = sort.length; idx < length; idx++) {
                    if (sort[idx].field === that.field) {
                        sort.splice(idx, 1);
                        break;
                    }
                }
                sort.push({ field: that.field, dir: dir });
            }

            dataSource.sort(sort);
        },

        _columns: function() {
            var that = this;

            if (that.options.columns) {

                that._updateColumnsMenu();

                that._updateColumnsMenuHandler = proxy(that._updateColumnsMenu, that);

                that.owner.bind(["columnHide", "columnShow"], that._updateColumnsMenuHandler);

                that.menu.bind(SELECT, function(e) {
                    var item = $(e.item),
                        input,
                        index,
                        column,
                        columns = that.owner.columns,
                        field;

                    if (that._isMobile) {
                        e.preventDefault();
                    }

                    if (!item.parent().closest("li.k-columns-item")[0]) {
                        return;
                    }

                    input = item.find(":checkbox");
                    if (input.attr("disabled")) {
                        return;
                    }

                    field = input.attr(kendo.attr("field"));

                    column = grep(columns, function(column) {
                        return column.field == field || column.title == field;
                    })[0];
                    index = inArray(column, columns);

                    if (column.hidden === true) {
                        that.owner.showColumn(index);
                    } else {
                        that.owner.hideColumn(index);
                    }
                });
            }
        },

        _updateColumnsMenu: function() {
            var attr = kendo.attr("field"),
                visible = grep(this._ownerColumns(), function(field) {
                    return !field.hidden;
                }),
                visibleDataFields = grep(visible, function(field) {
                    return field.originalField;
                }).length;

            visible = map(visible, function(col) {
                return col.field;
            });

            this.wrapper
                .find(".k-columns-item input[" + attr + "]")
                .prop("checked", false)
                .filter(function() {
                    return inArray($(this).attr(attr), visible) > -1;
                })
                .prop("checked", true)
                .prop("disabled", visibleDataFields == 1);
        },

        _filter: function() {
            var that = this,
                options = that.options;

            if (options.filterable !== false) {
                that.filterMenu = that.wrapper.find(".k-filterable")[FILTERMENU](
                    extend(true, {}, {
                        appendToElement: true,
                        dataSource: options.dataSource,
                        values: options.values,
                        field: that.field
                    },
                    options.filterable)
                    ).data(FILTERMENU);

                if (that._isMobile) {
                    that.menu.bind(SELECT, function(e) {
                        var item = $(e.item);

                        if (item.hasClass("k-filter-item")) {
                            that.pane.navigate(that.filterMenu.view, that.options.animations.left);
                        }
                    });
                }
            }
        },

        refresh: function() {
            var that = this,
                sort = that.options.dataSource.sort() || [],
                descriptor,
                field = that.field,
                idx,
                length;

            that.wrapper.find(".k-sort-asc, .k-sort-desc").removeClass(ACTIVE);

            for (idx = 0, length = sort.length; idx < length; idx++) {
               descriptor = sort[idx];

               if (field == descriptor.field) {
                   that.wrapper.find(".k-sort-" + descriptor.dir).addClass(ACTIVE);
               }
            }
        }
    });

    var template = '<ul>'+
                    '#if(sortable){#'+
                        '<li class="k-item k-sort-asc"><span class="k-link"><span class="k-sprite k-i-sort-asc"></span>${messages.sortAscending}</span></li>'+
                        '<li class="k-item k-sort-desc"><span class="k-link"><span class="k-sprite k-i-sort-desc"></span>${messages.sortDescending}</span></li>'+
                        '#if(showColumns || filterable){#'+
                            '<li class="k-separator"></li>'+
                        '#}#'+
                    '#}#'+
                    '#if(showColumns){#'+
                        '<li class="k-item k-columns-item"><span class="k-link"><span class="k-sprite k-i-columns"></span>${messages.columns}</span><ul>'+
                        '#for (var idx = 0; idx < columns.length; idx++) {#'+
                            '<li><input type="checkbox" data-#=ns#field="#=columns[idx].field.replace(/\"/g,"&\\#34;")#" data-#=ns#index="#=columns[idx].index#"/>#=columns[idx].title#</li>'+
                        '#}#'+
                        '</ul></li>'+
                        '#if(filterable){#'+
                            '<li class="k-separator"></li>'+
                        '#}#'+
                    '#}#'+
                    '#if(filterable){#'+
                        '<li class="k-item k-filter-item"><span class="k-link"><span class="k-sprite k-filter"></span>${messages.filter}</span><ul>'+
                            '<li><div class="k-filterable"></div></li>'+
                        '</ul></li>'+
                    '#}#'+
                    '</ul>';

    var mobileTemplate =
            '<div data-#=ns#role="view" data-#=ns#init-widgets="false" class="k-grid-column-menu">'+
                '<div data-#=ns#role="header" class="k-header">'+
                    '${messages.settings}'+
                    '<button class="k-button k-done">#=messages.done#</button>'+
                '</div>'+
                '<div class="k-column-menu k-mobile-list"><ul><li>'+
                    '<span class="k-link">${field}</span><ul>'+
                '#if(sortable){#'+
                    '<li class="k-item k-sort-asc"><span class="k-link"><span class="k-sprite k-i-sort-asc"></span>${messages.sortAscending}</span></li>'+
                    '<li class="k-item k-sort-desc"><span class="k-link"><span class="k-sprite k-i-sort-desc"></span>${messages.sortDescending}</span></li>'+
                '#}#'+
                '#if(filterable){#'+
                    '<li class="k-item k-filter-item">'+
                        '<span class="k-link k-filterable">'+
                            '<span class="k-sprite k-filter"></span>'+
                            '${messages.filter}</span>'+
                    '</li>'+
                '#}#'+
                '</ul></li>'+
                '#if(showColumns){#'+
                    '<li class="k-columns-item"><span class="k-link">${messages.columns}</span><ul>'+
                    '#for (var idx = 0; idx < columns.length; idx++) {#'+
                        '<li class="k-item"><label class="k-label"><input type="checkbox" class="k-check" data-#=ns#field="#=columns[idx].field.replace(/\"/g,"&\\#34;")#" data-#=ns#index="#=columns[idx].index#"/>#=columns[idx].title#</label></li>'+
                    '#}#'+
                    '</ul></li>'+
                '#}#'+
                '</ul></div>'+
            '</div>';

    var MobileMenu = Widget.extend({
        init: function(element, options) {
            Widget.fn.init.call(this, element, options);

            this.element.on("click" + NS, "li:not(.k-separator)", "_click");
        },

        events: [ SELECT ],

        _click: function(e) {
            if (this.trigger(SELECT, { item: e.currentTarget })) {
                e.preventDefault();
            }
        },

        close: function() {
            this.options.pane.navigate("");
        },

        destroy: function() {
            Widget.fn.destroy.call(this);

            this.element.off(NS);
        }
    });

    ui.plugin(ColumnMenu);
})(window.kendo.jQuery);
