/* Load this script using conditional IE comments if you need to support IE 7 and IE 6. */

window.onload = function() {
	function addIcon(el, entity) {
		var html = el.innerHTML;
		el.innerHTML = '<span style="font-family: \'icomoon\'">' + entity + '</span>' + html;
	}
	var icons = {
			'icon-XJJ-A000' : '&#xe000;',
			'icon-XJJ-A030' : '&#xe001;',
			'icon-XJJ-A031' : '&#xe002;',
			'icon-XJJ-A032' : '&#xe003;',
			'icon-XJJ-A033' : '&#xe004;',
			'icon-XJJ-A034' : '&#xe005;',
			'icon-XJJ-A035' : '&#xe006;',
			'icon-XJJ-A036' : '&#xe007;',
			'icon-XJJ-A037' : '&#xe008;',
			'icon-XJJ-A038' : '&#xe009;',
			'icon-XJJ-A039' : '&#xe00a;',
			'icon-XJJ-A050' : '&#xe00b;',
			'icon-XJJ-A049' : '&#xe00c;',
			'icon-XJJ-A048' : '&#xe00d;',
			'icon-XJJ-A047' : '&#xe00e;',
			'icon-XJJ-A046' : '&#xe00f;',
			'icon-XJJ-A045' : '&#xe010;',
			'icon-XJJ-A044' : '&#xe011;',
			'icon-XJJ-A043' : '&#xe012;',
			'icon-XJJ-A042' : '&#xe013;',
			'icon-XJJ-A041' : '&#xe014;',
			'icon-XJJ-A040' : '&#xe015;',
			'icon-XJJ-A051' : '&#xe016;',
			'icon-XJJ-A052' : '&#xe017;',
			'icon-XJJ-A053' : '&#xe018;',
			'icon-XJJ-A054' : '&#xe019;',
			'icon-XJJ-A055' : '&#xe01a;',
			'icon-XJJ-A056' : '&#xe01b;',
			'icon-XJJ-A057' : '&#xe01c;',
			'icon-XJJ-A058' : '&#xe01d;',
			'icon-XJJ-A059' : '&#xe01e;',
			'icon-XJJ-A061' : '&#xe01f;',
			'icon-XJJ-A072' : '&#xe020;',
			'icon-XJJ-A071' : '&#xe021;',
			'icon-XJJ-A060' : '&#xe022;',
			'icon-XJJ-A070' : '&#xe023;',
			'icon-XJJ-A069' : '&#xe024;',
			'icon-XJJ-A068' : '&#xe025;',
			'icon-XJJ-A067' : '&#xe026;',
			'icon-XJJ-A066' : '&#xe027;',
			'icon-XJJ-A065' : '&#xe028;',
			'icon-XJJ-A063' : '&#xe029;',
			'icon-XJJ-A064' : '&#xe02a;',
			'icon-XJJ-A062' : '&#xe02b;',
			'icon-XJJ-A073' : '&#xe02c;',
			'icon-XJJ-A074' : '&#xe02d;',
			'icon-XJJ-A075' : '&#xe02e;',
			'icon-XJJ-A076' : '&#xe02f;',
			'icon-XJJ-A077' : '&#xe030;',
			'icon-XJJ-A078' : '&#xe031;',
			'icon-XJJ-A079' : '&#xe032;',
			'icon-XJJ-A080' : '&#xe033;',
			'icon-XJJ-A081' : '&#xe034;',
			'icon-XJJ-A083' : '&#xe035;',
			'icon-XJJ-A094' : '&#xe036;',
			'icon-XJJ-A093' : '&#xe037;',
			'icon-XJJ-A082' : '&#xe038;',
			'icon-XJJ-A092' : '&#xe039;',
			'icon-XJJ-A091' : '&#xe03a;',
			'icon-XJJ-A090' : '&#xe03b;',
			'icon-XJJ-A089' : '&#xe03c;',
			'icon-XJJ-A088' : '&#xe03d;',
			'icon-XJJ-A087' : '&#xe03e;',
			'icon-XJJ-A086' : '&#xe03f;',
			'icon-XJJ-A085' : '&#xe040;',
			'icon-XJJ-A084' : '&#xe041;',
			'icon-XJJ-A095' : '&#xe042;',
			'icon-XJJ-A096' : '&#xe043;',
			'icon-XJJ-A097' : '&#xe044;',
			'icon-XJJ-A098' : '&#xe045;',
			'icon-XJJ-A099' : '&#xe046;',
			'icon-XJJ-A100' : '&#xe047;',
			'icon-XJJ-A101' : '&#xe048;',
			'icon-XJJ-A102' : '&#xe049;',
			'icon-XJJ-A103' : '&#xe04a;',
			'icon-XJJ-A104' : '&#xe04b;',
			'icon-XJJ-A105' : '&#xe04c;',
			'icon-XJJ-A106' : '&#xe04d;'
		},
		els = document.getElementsByTagName('*'),
		i, attr, html, c, el;
	for (i = 0; ; i += 1) {
		el = els[i];
		if(!el) {
			break;
		}
		attr = el.getAttribute('data-icon');
		if (attr) {
			addIcon(el, attr);
		}
		c = el.className;
		c = c.match(/icon-[^\s'"]+/);
		if (c && icons[c[0]]) {
			addIcon(el, icons[c[0]]);
		}
	}
};