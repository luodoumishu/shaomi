//全选函数
function selectAll(chbox){
	var items = document.getElementsByName(chbox);
	if(items[0].checked)
		chkOperator(true) ;
	else
		chkOperator(false);
}

function chkOperator (value){
	var items = document.getElementsByName('chbox');
	if(items.length>0){
		for (var i = 0; i < items.length; i++){
			items[i].checked = value;
		}
	}
}