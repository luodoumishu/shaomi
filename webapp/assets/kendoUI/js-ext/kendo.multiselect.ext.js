/*
* Copyright 2014 XJJ All rights reserved.
* license: GNU General Public License (GPL) version 3.
* description: 基于kendo.ui.MultiSelect的功能扩展，增加了拼音和全选等功能。
* authors: zhengyy(zyy.easy@gmail.com)
* version: 1.0.0
* requires: xjj.pinyin.js
*/
(function($) {
    var kendo = window.kendo,
        ui = kendo.ui,
		Query = kendo.data.Query,
        MultiSelect = ui.MultiSelect,
        FILTER = "filter",
        ACCEPT = "accept",
        isArray = $.isArray;

		
	//see kendo.data.js
	Query.prototype.filter = function(expressions) {
		var idx,
		current,
		length,
		compiled,
		predicate,
		data = this.data,
		fields,
		operators,
		result = [],
		filter;

		expressions = Query.normalizeFilter(expressions);

		if (!expressions || expressions.filters.length === 0) {
			return this;
		}

		compiled = Query.filterExpr(expressions);
		fields = compiled.fields;
		operators = compiled.operators;
		
		predicate = filter = new Function("d, __f, __o", "return " + compiled.expression);
		
		//@01 add by zhengyy for pinyin 
		var _filters = expressions.filters;
		var py_expression = "";
		for(var i=0;i<_filters.length;i++){
			var falg = _filters[i];
			if(falg.pinyin){
				py_expression += "pinyin.hit(d."+falg.field+",'"+falg.value+"')";
				if(i+1 != _filters.length){
					if(expressions.logic == "and"){
						py_expression += "&&";
					}else if(expressions.logic == "or"){
						py_expression += "||";
					}
				}
			}
		}
		predicatePy= filterPy = new Function("d", "var pinyin = new xjj.pinyin(); return " + py_expression);
		//@01 end
		
		if (fields.length || operators.length) {
			filter = function(d) {
				return predicate(d, fields, operators);
			};
			filterPy = function(d) { // add filterPy(current) by zhengyy
				return predicatePy(d);
			}
		}
		
		for (idx = 0, length = data.length; idx < length; idx++) {
			current = data[idx];
			if (filter(current)||filterPy(current)) { // add filterPy(current) by zhengyy
				result.push(current);
			}
		}
		return new Query(result);
	}
	
	var MultiSelect = MultiSelect.extend({
		options: {
			pinyin: true,
			//toolbar: false,
			toolbar:{enable: false ,showText: true ,show:false},//show:false 用于兼容旧代码
			//highlightFirst: true,
			filter:"contains",
			autoClose: false
		},
		search: function(word) {
		
			var that = this,
				options = that.options,
				ignoreCase = options.ignoreCase,
				filter = options.filter,
				field = options.dataTextField,
				inputValue = that.input.val();

			if (options.placeholder === inputValue) {
				inputValue = "";
			}

			clearTimeout(that._typing);

			word = typeof word === "string" ? word : inputValue;
			//console.log(word);
			if (word.length >= options.minLength) {
				that._state = "filter";
				that._open = true;

				that._filterSource({
					value: ignoreCase ? word.toLowerCase() : word,
					field: field,
					operator: filter,
					ignoreCase: ignoreCase,
					pinyin: options.pinyin //by zhengyy
				});
			}
		},
        //修改了value(),_unselect2(),_select2()方法，提高了大数据量全选或清空操作的执行效率。
		value2: function(value) {
            var that = this,
                tags = $(that.tagList[0].children),
                length = tags.length,
                dataItemIndex,
                idx = 0;

            if (value === undefined) {
                return that._values;
            }
            if (that._fetchItems(value)) {
                return;
            }
            
            if (value !== null) {

                value = isArray(value) || value instanceof ObservableArray ? value : [value];

                for (idx = 0, length = value.length; idx < length; idx++) {
                    dataItemIndex = that._index(value[idx]);
                    //console.log("value2.value[idx]:"+value[idx]);
                    //console.log("value2.dataItemIndex:"+dataItemIndex);
                    if (dataItemIndex > -1) {
                        that._select2(dataItemIndex);
                    }
                }
                that._height(that._visibleItems);
                that._old = that._values.slice();
            }else{
            	for (; idx < length; idx++) {
	                that._unselect2(tags.eq(idx));
	            }
	            that._height(that._visibleItems);
            }
        },
        _select2: function(li) {
            var that = this,
                values = that._values,
                dataItem,
                idx;

            if (!that._allowSelection()) {
                return;
            }

            if (!isNaN(li)) {
                idx = li;
                that.ul[0].children[idx].style.display = "none";
            } else {
                idx = li.hide().data("idx");
            }

            that.element[0].children[idx].selected = true;

            dataItem = that.dataSource.view()[idx];

            that.tagList.append(that.tagTemplate(dataItem));
            that._dataItems.push(dataItem);
            values.push(that._dataValue(dataItem));

            that._visibleItems -= 1;
            //that.currentTag(null);
            //that._placeholder();
            //that._height(that._visibleItems);

            if (that._state === FILTER) {
                that._state = ACCEPT;
            }
        },
        _unselect2: function(tag) {
            var that = this,
                index = tag.index(),
                dataItem, value,
                options, option, length;

            tag.remove();
            //that.currentTag(null);

            that._values.splice(index, 1);
            dataItem = that._dataItems.splice(index, 1)[0];

            value = that._dataValue(dataItem);
            index = that._index(value);

            if (index !== -1) {
               $(that.ul[0].children[index]).show();
               that.element[0].children[index].selected = false;
               that._visibleItems += 1;
               //that._height(that._visibleItems);
            } else {
                index = that.dataSource.view().length;
                options = that.element[0].children;
                length = options.length;

                for (; index < length; index++) {
                    option = options[index];
                    if (option.value == value) {
                        option.selected = false;
                        break;
                    }
                }
            }

            //that._placeholder();
        },
		_height: function(length) {
            if (length) {
                var that = this,
                    list = that.list,
                    visible = that.popup.visible(),
                    height = that.options.height;

                list = list.add(list.parent(".k-animation-container")).show()
                           .height(that.ul[0].scrollHeight > height ? height : "auto");

                //@02 by zhengyy
                if(that.options.toolbar.enable || that.options.toolbar.show){
                	var ul = that.ul,
	                	element = that.element,
	                	id = element.attr("id"),
	                	barId = id + "-MSBTN",
	                	size = ul.children(":visible").length,
	                	sizeTag= (element.val()||[]).length;
	                $("#"+barId).remove();
	                //console.log("size:"+size);
	                var bar = $("<div unselectable='on'></div>").attr("id",barId);
	                var btnSelectAll = $("<button type='button' style='min-width: 40px; margin: 0px 0px 1px 5px;'>全选</button>").kendoButton({
	                    	icon: "tick",
	                    	click: function(e) {
	                    		var barId = e.event.target.parentNode.id||e.event.target.parentNode.parentNode.id,
	                    			elementId = barId.replace('-MSBTN',''),
	                    			element = $("#"+elementId),
	                    			multiselect = element.data("kendoMultiSelect"),
	                    			dataValueField = multiselect.options.dataValueField,
	                    			ul = multiselect.ul,
	                    			items = ul.children(":visible"),
	                    			array = element.val()||[],
	                    			chosen = [],
	                    			data = multiselect.dataSource.view();
	                    		//console.log("element.val-start:"+$("#"+elementId).val());	
	                    		//console.log("array-start:"+array); 
	                    		
						        for(var i=0; i<items.length; i++){
									//array.push(view[i].value);
									var idx = $(items[i]).attr("data-idx");
									chosen.push(data[idx].get(dataValueField));
									//console.log("items[i]:"+$(items[i]).attr("data-idx"));
								}

								//console.log("dataValueField:"+dataValueField);
								multiselect.close();								
								multiselect.value2(chosen);
								//array.push(chose);
								//multiselect._values=array;
								//multiselect.list.attr("height");
								//multiselect._height(200);
								multiselect.focus();
								//console.log("element.val:"+$("#"+elementId).val());	
								//console.log("element.length:"+$("#"+elementId).val().length);
								//console.log("`````````````````````````````````````````````");						
						    }
	                    }).appendTo(bar);
	                var btnCancelAll = $("<button type='button' style='min-width: 40px; margin: 0px 0px 1px 5px;'>清空</button>").kendoButton({
	                    	icon: "cancel",
	                    	click: function(e) {
	                    		var barId = e.event.target.parentNode.id||e.event.target.parentNode.parentNode.id,
						        	elementId = barId.replace('-MSBTN',''),
						        	multiselect = $("#"+elementId).data("kendoMultiSelect");
						        var isHuge = multiselect.ul.children().length > 100
						        multiselect.value2(null);
						        if(isHuge){
						        	multiselect.close();
						        }else{
						        	multiselect.refresh();
						        }
						        multiselect.focus();
						        //console.log("element:"+$("#"+elementId).val());
						    }
	                    }).appendTo(bar);

					if(that.options.toolbar.showText){
		                var barText = $("<span style='vertical-align: middle; margin: 0px 5px 0px 5px; '>已选"+sizeTag+"条/共"+size+"条可选</span>");
		                barText.appendTo(bar);
	                }

	                bar.children().attr("unselectable","on");
	           		ul.height(that.ul[0].scrollHeight > height ? height-25 : "auto");
	           		ul.before(bar);
                }
           		//@02 end
           		
                if (!visible) {
                    list.hide();
                }
            }
        }
		
	});

	ui.plugin(MultiSelect);

})(jQuery);

