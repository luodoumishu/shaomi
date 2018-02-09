var js = {version: '1.0',util:{}};

js.util.Map = function (map)
{
	if(arguments.length>1)
	{
		throw new Error("Map���ֻ�ܴ�һ������");
	}
	this.arr=new Array();
	if(arguments.length==1)
	{
		if((typeof map != "object"))
		{
			throw new Error("map����ֻ����Map�����");
		}
		this.putAll(map);
	}
}
js.util.Map.prototype=
{
	/**
	* ������нڵ�
	*	@param JSMAP����
	*/
	putAll:function(map)
	{
		if(arguments.length>1)
		{
			 throw new Error("putAllֻ�ܴ�һ������");
		}
		if(arguments.length==1)
		{
			if((typeof map != "object"))
			{
				throw new Error("putAllֻ�ܴ�MapΪ�����Ķ���");
			}
			for(var i=0;i<map.arr.length;i++)
			{
				this.put(map.arr[i].key,map.arr[i].v);
			}
		}	
	},
	/**
	*	��ȡ�ڵ��ֵ
	*	@param key ��ֵ
	*	@return key��Ӧ��Object�������û���ҵ�������null
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
	* ���key�ļ���
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
	*	�Ƿ����һ���ڵ�
	* @param key ��ֵ 
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
	* ��С
	* @return ����MAP�Ĵ�С
	*/
	size:function()
	{
		return this.arr.length;
	},
	/**
	* ����ֵ�ü���
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
	*	���һ���ڵ�
	* @param key ��ֻ
	* @param v ֵ
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
	*	ɾ��һ���ڵ�
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
Map�Ľڵ�
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