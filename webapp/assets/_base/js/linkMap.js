Array.prototype.remove = function(s) {
    for (var i = 0; i < this.length; i++) {
        if (s == this[i])
            this.splice(i, 1);
    }
}


/**
 * Simple Map
 * @author dewitt
 * modify by zsy
 * modify date 2015-1-8
 * @date 2008-05-24
 */
function LinkMap() {
    /** 存放键的数组(遍历用到) */
    this.keys = new Array();
    /** 存放数据 */
    this.data = new Object();
   
    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value) {
        if(this.data[key] == null){
            this.keys.push(key);
        }
        this.data[key] = value;
    };
   
    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function(key) {
        return this.data[key];
    };
   
    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function(key) {
        this.keys.remove(key);
        this.data[key] = null;
    };
   
    /**
     * 删除全部元素
     */
    this.removeAll = function(){
        var len = this.keys.length;
        for(var i=0;i<len;i++){
            var key = this.keys[i];
            this.data[key] = null;
        }
        this.keys.splice(0,this.keys.length);  
    };
    
    /**
     * 遍历Map,执行处理函数
     *
     * @param {Function} 回调函数 function(key,value,index){..}
     */
    this.each = function(fn){
        if(typeof fn != 'function'){
            return;
        }
        var len = this.keys.length;
        for(var i=0;i<len;i++){
            var k = this.keys[i];
            fn(k,this.data[k],i);
        }
    };
   
    /**
     * 获取键值数组(类似Java的entrySet())
     * @return 键值对象{key,value}的数组
     */
    this.entrys = function() {
        var len = this.keys.length;
        var entrys = new Array(len);
        for (var i = 0; i < len; i++) {
            entrys[i] = {
                key : this.keys[i],
                value : this.data[i]
            };
        }
        return entrys;
    };
   
    /**
     * 判断Map是否为空
     */
    this.isEmpty = function() {
        return this.keys.length == 0;
    };
    
    this.isExist = function(key) {
        return this.data[key]==null?false:true;
    };
   
    /**
     * 获取键值对数量
     */
    this.size = function(){
        return this.keys.length;
    };
   
    /**
     * 重写toString
     */
    this.toString = function(){
        var s = "{";
        for(var i=0;i<this.keys.length;i++,s+=','){
            var k = this.keys[i];
            s += k+"="+this.data[k];
        }
        s+="}";
        return s;
    };
}


function testMap(){
    var m = new LinkMap();
    m.put('key1','Comtop');
    m.put('key2','南方电网');
    m.put('key3','景新花园');
   
    m.put('key1','康拓普');
   
    m.remove("key2");
    m.put('key4','xjjuser');
    var s ="";
    m.each(function(key,value,index){
        s += index+":"+ key+"="+value+"\n";
    });
    alert(s);
}