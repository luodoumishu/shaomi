/**
 * 将js文件写入页面
 * 
 * @param {}
 *            inc js文件路径
 */
function _IncludeScript(inc) {
	var script = '<' + 'script type="text/javascript" src="' + inc + '"' + '><'
			+ '/script>';
	document.writeln(script);
}

/**
 * 将css文件写入页面
 * 
 * @param {}
 *            inc css文件路径
 */
function _IncludeStyle(inc) {
	var style = '<' + 'link type="text/css" rel="stylesheet" href="' + inc
			+ '"' + ' />';
	document.writeln(style);
}
xjj = {};
/* 新境界JS文件 */
xjj.CONFIG = (function() {
	return {
		debug : true
	}
})();
/** *错误抛出* */
xjj.Error = function(error) {
	throw new Error(error);
};
/**
 * 命名函数(命名空间)
 * 
 * @param {}
 *            objName 对象名称
 */
xjj.nameSpace = function(objName) {     
	var a = arguments, o = null, i, j, d, rt;
	for (i = 0; i < a.length; ++i) {
		d = a[i].split(".");
		rt = d[0];
		eval('if (typeof ' + rt + ' == "undefined"){' + rt + ' = {};} o = '
				+ rt + ';');
		for (j = 1; j < d.length; ++j) {
			o[d[j]] = o[d[j]] || {};
			o = o[d[j]];
		}
	}
};
xjj.Util = (function() {
	return {
		isUndefined : function(value) {
			return typeof value == "undefined";
		},
		// 弹出窗口
		alert : function(text) {
			alert(text);
		},
		/**
		 * 蒙版
		 * 
		 * @param elementId
		 *            需要被蒙版的元素ID 若页面不存在该元素,那么则直接对 body 进行蒙版
		 */
		mask : function(elementId) {
			var needMaskEM, maskEM = $("#xjj_mask");
			needMaskEM = (typeof elementId == "undefined" || $("#" + elementId)
					.width() == null) ? $(document.body) : $("#" + elementId);
			maskEM.css({
						width : needMaskEM.width(),
						height : needMaskEM.height(),
						top : needMaskEM.offset().top,
						left : needMaskEM.offset().left
					});
			maskEM.fadeTo("slow", 0.6);
		},
		/**
		 * 取消蒙版
		 */
		disposeMask : function() {
			$("#xjj_mask").fadeOut("slow", 0);
		},
		/**
		 * 获得浏览器有效宽高
		 */
		getDocumentPixel : function() {
			// 如果是IE浏览器
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				return {
					width : window.document.documentElement.offsetWidth,
					height : window.document.documentElement.offsetHeight
				};
			} else {
				return {
					width : window.innerWidth,
					height : window.innerHeight
				};
			}
		},
		/**
		 * 获得元素的位置信息
		 * 
		 * @param {HtmlElement}
		 *            oElement html对象
		 */
		findPosition : function(oElement) {
			var x2 = 0;
			var y2 = 0;
			var width = oElement.offsetWidth;
			var height = oElement.offsetHeight;
			if (typeof(oElement.offsetParent) != 'undefined') {
				for (var posX = 0, posY = 0; oElement; oElement = oElement.offsetParent) {
					posX += oElement.offsetLeft;
					posY += oElement.offsetTop;
				}
				x2 = posX + width;
				y2 = posY + height;
				return {
					left : posX,
					top : posY,
					right : x2,
					bottom : y2
				};

			} else {
				x2 = oElement.x + width;
				y2 = oElement.y + height;
				return {
					left : oElement.x,
					top : oElement.y,
					right : x2,
					bottom : y2
				};
			}
		},
		getWindowTopZIndex : function() {
			var el = document.body.childNodes;
			var maxZIndex = 1;
			for (var i = 0; i < el.length; i++) {
				if (typeof(el[i].style) != "undefined"
						&& typeof(el[i].style) != null) {
					if (el[i].style.zIndex != "") {
						if (maxZIndex < parseInt(el[i].style.zIndex)) {
							maxZIndex = parseInt(el[i].style.zIndex);
						}
					}
				}
			}
			return maxZIndex;
		}
	}
})();
/**
 * {} 完全继承
 * 
 * @param {}
 *            origclass
 * @param {}
 *            overrides
 */
xjj.apply = function(origclass, overrides) {
	for (var method in overrides) {
		origclass[method] = overrides[method];
	}
	return origclass;
};
/**
 * {} 保留继承
 * 
 * @param {}
 *            origclass
 * @param {}
 *            overrides
 */
xjj.applyIf = function(origclass, overrides) {
	for (var method in overrides) {
		if (typeof origclass[method] == "undefined") {
			origclass[method] = overrides[method];
		}
	}
	return origclass;
};
/**
 * 继承对象property下的所有方法
 * 
 * @param {}
 *            origclass
 * @param {}
 *            overrides
 */
xjj.override = function(origclass, overrides) {
	if (overrides) {
		var p = origclass.prototype;
		for (var method in overrides) {
			p[method] = overrides[method];
		}
	}
}
/**
 * 继承
 * 
 * @param sb
 *            子类 继承
 * @param sp
 *            父类 被继承
 * @param overrides
 *            额外添加的方法
 * @return 实现了继承的子类 该子类拥有父类的所有方法,并具有了override属性 且该property属性 下的所有方法都具有
 *         override属性
 */
xjj.extend = function() {
	var io = function(o) {
		for (var m in o) {
			this[m] = o[m];
		}
	};
	var oc = Object.prototype.constructor;

	return function(sb, sp, overrides) {
		if (typeof sp == 'object') {
			overrides = sp;
			sp = sb;
			sb = overrides.constructor != oc
					? overrides.constructor
					: function() {
						sp.apply(this, arguments);
					};
		}
		var F = function() {
		}, sbp, spp = sp.prototype;
		F.prototype = spp;
		sbp = sb.prototype = new F();
		sbp.constructor = sb;
		sb.superclass = spp;
		if (spp.constructor == oc) {
			spp.constructor = sp;
		}
		sb.override = function(o) {
			xjj.override(sb, o);
		};
		sbp.override = io;
		xjj.override(sb, overrides);
		sb.extend = function(o) {
			xjj.extend(sb, o);
		};
		return sb;
	};
}();
/**
 * 模拟java的StringBuffer函数实现简单的字符串连接
 */
xjj.StringBuffer = function() {
	this.buffer = [];
};
/**
 * 转化为String对象
 */
xjj.StringBuffer.prototype.toString = function() {
	this.buffer.join("");
};
/**
 * 连接字符串函数
 * 
 * @param {}
 *            str
 */
xjj.StringBuffer.prototype.append = function(str) {
	this.buffer.push(str);
};
/**
 * 连接数组,没有注意是否重复
 * 
 * @param {}
 *            arr1
 * @param {}
 *            arr2
 * @return {}
 */
xjj.concatArray = function(arr1, arr2) {
	return Array.prototype.push.apply(arr1, arr2);
};

/**
 * JS的一些拓展方法
 */

/**
 * 去掉字符串前后的空格
 * 
 * @return 去掉空格后的字符串
 */
String.prototype.trim = function() {
	var reExtraSpace = /^\s*(.*?)\s+$/;
	return this.replace(reExtraSpace, "$1");
};

/**
 * 字符串是否以指定字符结尾
 * 
 * @param text
 *            查询结尾的字符
 * @return 如果以text结尾则返回true, 反之则返回false
 */
String.prototype.endWith = function(text) {
	var reg = new RegExp(text + '$');
	return this.search(reg) != -1;
};

/**
 * 保存信息时，对数据进行转义 转义包含字符（&、"、'、<、>）
 */
var transferMean = function(source) {
	source = source.replace(/\&/g, '&amp;');
	source = source.replace(/\"/g, '&quot;');
	source = source.replace(/\'/g, '&acute;');
	source = source.replace(/\</g, '&lt;');
	source = source.replace(/\>/g, '&gt;');
	return source;
};

/**
 * 获取信息时，对数据进行转义 与方法transferMean对应的，逆转义字符
 */
var reTransferMean = function(source) {
	source = source.replace(/\&amp;/g, '&');
	source = source.replace(/\&quot;/g, '\"');
	source = source.replace(/\&acute;/g, "\'");
	source = source.replace(/\&lt;/g, '<');
	source = source.replace(/\&gt;/g, '>');
	return source;
};

/**
 * 生成随机码
 * 
 * @param {}
 *            l
 * @return {}
 */
var uuid = function(l) {
	var l = l || 10;
	var x = "0123456789";
	var id = "";
	for (var i = 0; i < l; i++) {
		id += x.charAt(Math.ceil(Math.random() * 100000000) % x.length);
	}
	return id;
};

var JSON;
if (!JSON) {
	JSON = {};
}
(function() {
	"use strict";

	function f(n) {
		// Format integers to have at least two digits.
		return n < 10 ? '0' + n : n;
	}

	if (typeof Date.prototype.toJSON !== 'function') {

		Date.prototype.toJSON = function(key) {

			return isFinite(this.valueOf()) ? this.getUTCFullYear() + '-'
					+ f(this.getUTCMonth() + 1) + '-' + f(this.getUTCDate())
					+ 'T' + f(this.getUTCHours()) + ':'
					+ f(this.getUTCMinutes()) + ':' + f(this.getUTCSeconds())
					+ 'Z' : null;
		};

		String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function(
				key) {
			return this.valueOf();
		};
	}

	var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g, escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g, gap, indent, meta = { // table
		// of
		// character
		// substitutions
		'\b' : '\\b',
		'\t' : '\\t',
		'\n' : '\\n',
		'\f' : '\\f',
		'\r' : '\\r',
		'"' : '\\"',
		'\\' : '\\\\'
	}, rep;

	function quote(string) {

		escapable.lastIndex = 0;
		return escapable.test(string) ? '"'
				+ string.replace(escapable, function(a) {
					var c = meta[a];
					return typeof c === 'string' ? c : '\\u'
							+ ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
				}) + '"'
				: '"' + string + '"';
	}

	function str(key, holder) {

		var i, // The loop counter.
		k, // The member key.
		v, // The member value.
		length, mind = gap, partial, value = holder[key];

		if (value && typeof value === 'object'
				&& typeof value.toJSON === 'function') {
			value = value.toJSON(key);
		}

		if (typeof rep === 'function') {
			value = rep.call(holder, key, value);
		}

		switch (typeof value) {
			case 'string' :
				return quote(value);

			case 'number' :

				return isFinite(value) ? String(value) : 'null';

			case 'boolean' :
			case 'null' :

				return String(value);

			case 'object' :

				if (!value) {
					return 'null';
				}

				gap += indent;
				partial = [];

				if (Object.prototype.toString.apply(value) === '[object Array]') {

					length = value.length;
					for (i = 0; i < length; i += 1) {
						partial[i] = str(i, value) || 'null';
					}

					v = partial.length === 0 ? '[]' : gap
							? '[\n' + gap + partial.join(',\n' + gap) + '\n'
									+ mind + ']'
							: '[' + partial.join(',') + ']';
					gap = mind;
					return v;
				}

				if (rep && typeof rep === 'object') {
					length = rep.length;
					for (i = 0; i < length; i += 1) {
						if (typeof rep[i] === 'string') {
							k = rep[i];
							v = str(k, value);
							if (v) {
								partial.push(quote(k) + (gap ? ': ' : ':') + v);
							}
						}
					}
				} else {

					for (k in value) {
						if (Object.prototype.hasOwnProperty.call(value, k)) {
							v = str(k, value);
							if (v) {
								partial.push(quote(k) + (gap ? ': ' : ':') + v);
							}
						}
					}
				}

				v = partial.length === 0 ? '{}' : gap ? '{\n' + gap
						+ partial.join(',\n' + gap) + '\n' + mind + '}' : '{'
						+ partial.join(',') + '}';
				gap = mind;
				return v;
		}
	}

	if (typeof JSON.stringify !== 'function') {
		JSON.stringify = function(value, replacer, space) {

			var i;
			gap = '';
			indent = '';

			if (typeof space === 'number') {
				for (i = 0; i < space; i += 1) {
					indent += ' ';
				}

			} else if (typeof space === 'string') {
				indent = space;
			}

			rep = replacer;
			if (replacer
					&& typeof replacer !== 'function'
					&& (typeof replacer !== 'object' || typeof replacer.length !== 'number')) {
				throw new Error('JSON.stringify');
			}

			return str('', {
						'' : value
					});
		};
	}

	if (typeof JSON.parse !== 'function') {
		JSON.parse = function(text, reviver) {

			var j;

			function walk(holder, key) {

				var k, v, value = holder[key];
				if (value && typeof value === 'object') {
					for (k in value) {
						if (Object.prototype.hasOwnProperty.call(value, k)) {
							v = walk(value, k);
							if (v !== undefined) {
								value[k] = v;
							} else {
								delete value[k];
							}
						}
					}
				}
				return reviver.call(holder, key, value);
			}

			text = String(text);
			cx.lastIndex = 0;
			if (cx.test(text)) {
				text = text.replace(cx, function(a) {
					return '\\u'
							+ ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
				});
			}

			if (/^[\],:{}\s]*$/
					.test(text
							.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
							.replace(
									/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
									']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

				j = eval('(' + text + ')');

				return typeof reviver === 'function' ? walk({
							'' : j
						}, '') : j;
			}

			throw new SyntaxError('JSON.parse');
		};
	}
}());

Date.prototype.format = function(mask) {

	var d = this;
	var zeroize = function(value, length) {
		if (!length)
			length = 2;
		value = String(value);
		for (var i = 0, zeros = ''; i < (length - value.length); i++) {
			zeros += '0';
		}
		return zeros + value;
	};

	return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g,
		function($0) {
			switch ($0) {
				case 'd' :
					return d.getDate();
				case 'dd' :
					return zeroize(d.getDate());
				case 'ddd' :
					return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr',
							'Fri', 'Sat'][d.getDay()];
				case 'dddd' :
					return ['Sunday', 'Monday', 'Tuesday',
							'Wednesday', 'Thursday', 'Friday',
							'Saturday'][d.getDay()];
				case 'M' :
					return d.getMonth() + 1;
				case 'MM' :
					return zeroize(d.getMonth() + 1);
				case 'MMM' :
					return ['Jan', 'Feb', 'Mar', 'Apr', 'May',
							'Jun', 'Jul', 'Aug', 'Sep', 'Oct',
							'Nov', 'Dec'][d.getMonth()];
				case 'MMMM' :
					return ['January', 'February', 'March',
							'April', 'May', 'June', 'July',
							'August', 'September', 'October',
							'November', 'December'][d.getMonth()];
				case 'yy' :
					return String(d.getFullYear()).substr(2);
				case 'yyyy' :
					return d.getFullYear();
				case 'h' :
					return d.getHours() % 12 || 12;
				case 'hh' :
					return zeroize(d.getHours() % 12 || 12);
				case 'H' :
					return d.getHours();
				case 'HH' :
					return zeroize(d.getHours());
				case 'm' :
					return d.getMinutes();
				case 'mm' :
					return zeroize(d.getMinutes());
				case 's' :
					return d.getSeconds();
				case 'ss' :
					return zeroize(d.getSeconds());
				case 'l' :
					return zeroize(d.getMilliseconds(), 3);
				case 'L' :
					var m = d.getMilliseconds();
					if (m > 99)
						m = Math.round(m / 10);
					return zeroize(m);
				case 'tt' :
					return d.getHours() < 12 ? 'am' : 'pm';
				case 'TT' :
					return d.getHours() < 12 ? 'AM' : 'PM';
				case 'Z' :
					return d.toUTCString().match(/[A-Z]+$/);
					// Return quoted strings with the surrounding
					// quotes removed
				default :
					return $0.substr(1, $0.length - 2);
			}
		});
};
String.prototype.replaceAll  = function(s1,s2){   
	return this.replace(new RegExp(s1,"gm"),s2);   
}

/**
 * 数组去重
 */
var unique = function (data){ 
	data = data || []; 
	var a = {}; 
	for (var i=0; i<data.length; i++) { 
		var v = data[i]; 
    	if (typeof(a[v]) == 'undefined'){ 
    		a[v] = 1; 
    	} 
	}; 
	data.length=0; 
	for (var i in a){ 
		data[data.length] = i; 
	} 
	//alert(data);
	return data; 
}

////随机数
//var uuid = function(l) {
//	var l = l || 10;
//	var x = "0123456789";
//	var id = "";
//	for (var i = 0; i < l; i++) {
//		id += x.charAt(Math.ceil(Math.random() * 100000000) % x.length);
//	}
//	return id;
//};