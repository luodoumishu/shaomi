var js = {version: '1.0',util:{}};

js.util.Map = function (map)
{
	if(arguments.length>1)
	{
		throw new Error("Map最多只能带一个参数");
	}
	this.arr=new Array();
	if(arguments.length==1)
	{
		if((typeof map != "object"))
		{
			throw new Error("map参数只能是Map类对象");
		}
		this.putAll(map);
	}
}
js.util.Map.prototype=
{
	/**
	* 添加所有节点
	*	@param JSMAP参数
	*/
	putAll:function(map)
	{
		if(arguments.length>1)
		{
			 throw new Error("putAll只能带一个参数");
		}
		if(arguments.length==1)
		{
			if((typeof map != "object"))
			{
				throw new Error("putAll只能带Map为参数的对象");
			}
			for(var i=0;i<map.arr.length;i++)
			{
				this.put(map.arr[i].key,map.arr[i].v);
			}
		}	
	},
	/**
	*	获取节点的值
	*	@param key 键值
	*	@return key对应的Object对象，如果没有找到，返回null
	*/
	get:function(key)
	{
			for(var i=0;i<this.arr.length;i++)
			{
				var temp=this.arr[i];
				if(temp.key==key)
				{
					return temp.v;
				}
			}
			return null;
	},
	/**
	* 获得key的集合
	*/
	keyArr: function ()
	{
		var result=new Array();
		for(var i=0;i<this.arr.length;i++)
		{
			result.push(this.arr[i].key)
		}
		return result;
	},
	/**
	*	是否包含一个节点
	* @param key 键值 
	*/
	containsKey:function(key)
	{
		for(var i=0;i<this.arr.length;i++)
		{
			var temp=this.arr[i];
			if(temp.key==key)
			{
				return true;
			}
		}
		return false;
	},
	/**
	* 大小
	* @return 返回MAP的大小
	*/
	size:function()
	{
		return this.arr.length;
	},
	/**
	* 返回值得集合
	*/
	values:function()
	{
		var result=new Array();
		for(var i=0;i<this.arr.length;i++)
		{
			result.push(this.arr[i].v)
		}
		return result;
	},
	/**
	*	添加一个节点
	* @param key 舰只
	* @param v 值
	*/
	put : function (key,v)
	{
		var a=new js.util.Item(key,v);
		for(var i=0;i<this.arr.length;i++)
		{
			var temp=this.arr[i];
			if(a.isSame(temp))
		   {
				temp.v=v;
				return;
			}
		}
		this.arr.push(a);
	},
	/**
	*	删除一个节点
	**/
	remove:function(key)
	{
		var select;
		for(var i=0;i<this.arr.length;i++)
		{
				var temp=this.arr[i];
				if(temp.key==key)
				{
					select=temp;
					break;
				}
		}
		if(select==null)
		{
			return;
		}
		var t=this.arr[this.arr.length-1];
		this.arr[this.arr.length-1]=select;
		this.arr[i]=t;
		this.arr.pop();
	}
};
/*
Map的节点
*/
js.util.Item = function(key,v)
{
	this.key=key;
	this.v=v;
	this.isSame = function(a)
	{
		if(this.key==a.key)
		{
			return true; 
		}else
		{
		return false;
		}	  
	}
}