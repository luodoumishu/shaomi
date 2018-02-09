var cnmsg = {  

    required: "请输入内容",   

    remote: "请修正该字段",   

    email: "请输入格式正确的电子邮件",   

    url: "请输入格式正确的网址",  

    date: "请输入格式正确的日期",

    dateISO: "请输入格式正确的日期 (ISO).",  

    number: "只能输入数字",

    digits: "只能输入整数",   

    creditcard: "请输入格式正确的信用卡号",   

    equalTo: "请再次输入相同的值",   

    accept: "请输入拥有合法后缀名的字符串",   

    maxlength: jQuery.format("请输入一个长度最多是 {0} 的字符串"),   

    minlength: jQuery.format("请输入一个长度最少是 {0} 的字符串"),   

    rangelength: jQuery.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),   

    range: jQuery.format("请输入一个介于 {0} 和 {1} 之间的值"),   

    max: jQuery.format("请输入一个最大为 {0} 的值"),  

    min: jQuery.format("请输入一个最小为 {0} 的值")

};

jQuery.validator.addMethod("stringCheck", function(value, element) {      
    return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);      
}, "只能包括中文字、英文字母、数字和下划线");  
 
//中文字两个字节      
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {      
    var length = value.length;      
    for(var i = 0; i < value.length; i++){      
        if(value.charCodeAt(i) > 127){      
        length++;      
        }      
    }      
    return this.optional(element) || ( length >= param[0] && length <= param[1] );      
}, jQuery.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));  
 
//身份证号码验证      
jQuery.validator.addMethod("isIdCardNo", function(value, element) {      
    return this.optional(element) || isIdCardNo(value);      
}, "请输入正确的身份证号码");
    
//手机号码验证      
jQuery.validator.addMethod("isMobile", function(value, element) {      
    var length = value.length;  
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));      
}, "请正确填写您的手机号码");      
    
//电话号码验证      
jQuery.validator.addMethod("isTel", function(value, element) {      
    //var tel = /^\d{3,4}-?\d{7,9}$/;    //电话号码格式010-12345678
    var tel = /^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的电话号码");  
 
//联系电话(手机/电话皆可)验证  
jQuery.validator.addMethod("isPhone", function(value,element) {  
    var length = value.length;  
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;  
    var tel = /^\d{3,4}-?\d{7,9}$/;  
    return this.optional(element) || (tel.test(value) || mobile.test(value));  
 
}, "请正确填写您的联系电话");  
    
//邮政编码验证
jQuery.validator.addMethod("isZipCode", function(value, element) {      
    var zip = /^[0-9]{6}$/;      
    return this.optional(element) || (zip.test(value));      
}, "请正确填写您的邮政编码"); 

//验证码校验
jQuery.validator.addMethod("checkCode", function(value, element){
    var result = false;
    // 设置同步
    $.ajaxSetup({
        async: false
    });
    var param = {
        verifyCode: value
    };

    $.post(jsCxtPath+"/user/UserAction_checkCode.action", param, function(data){
        var subItem = eval(data);
        if(subItem.showMsg!="0") {
            result = true;
        }else{
            //TODO 更新验证码 
        }
    });
    // 恢复异步
    $.ajaxSetup({
        async: true
    });
    return result;
}, "验证码填写错误");
        
jQuery.validator.addMethod("checkAccount", function(value, element){
    var result = false;
    // 设置同步
    $.ajaxSetup({
        async: false
    });
    var param = {
        account: value
    };
    $.post(jsCxtPath+"/user/UserAction_checkAccount.action", param, function(data){
        var subItem = eval(data);
        if(subItem.showMsg!="0") {
            result = true;
        }
    });
    // 恢复异步
    $.ajaxSetup({
        async: true
    });
    return result;
}, "该账号已经存在");


jQuery.validator.addMethod("checkName", function(value, element){
    var result = false;
    value = encodeURIComponent(value);
    // 设置同步
    $.ajaxSetup({
        async: false
    });
    var param = {
        name: value
    };
    $.post(jsCxtPath+"/qualification/EntInformationAction_checkName.action", param, function(data){
        var subItem = eval(data);
        if(subItem.showMsg!="0") {
            result = true;
        }
    });
    // 恢复异步
    $.ajaxSetup({
        async: true
    });
    return result;
}, "该单位名称已经存在");

//浮点数验证
jQuery.validator.addMethod("number2", function(value, element) {
    var zip = /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d{1,2})?$/;
    return this.optional(element) || (zip.test(value));
}, "只能输入数字，小数点后保留两位");

//浮点数验证
jQuery.validator.addMethod("number3", function(value, element) {
    var zip = /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d{1,2,3})?$/;
    return this.optional(element) || (zip.test(value));
}, "只能输入数字，小数点后保留三位");

jQuery.extend(jQuery.validator.messages, cnmsg);