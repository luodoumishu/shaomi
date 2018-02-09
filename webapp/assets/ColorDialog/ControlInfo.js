//千一网络 www.cftea.com
//ControlInfo v1.0
//应用示例：
/*
var controlInfo = new ControlInfo("btn");
controlInfo.GetInfo();
alert("该控件的相关属性：\r\nx=" + controlInfo.x + "\r\ny=" + controlInfo.y + "\r\nwidth=" + controlInfo.width + "\r\nheight=" + controlInfo.height);
*/

//参数 targetIdOrTarget，控件 Id 名称或控件对象。
//属性 x，控件在网页中坐标中 X 轴上的绝对位置。
//属性 y，控件在网页中坐标中 Y 轴上的绝对位置。
//属性 width，控件的绝对宽度，也就是控件实际占据的宽度。
//属性 height，控件的绝对高度，也就是控件实际占据的高度。
//方法 GetInfo，取得控件的位置和宽高。
function ControlInfo(targetIdOrTarget)
{
    this.target = (typeof(targetIdOrTarget)=="string") ? document.getElementById(targetIdOrTarget) : targetIdOrTarget;
    this.x;
    this.y;
    this.width;
    this.height;
    
    this.GetInfo = ControlInfo_GetInfo;
}


function ControlInfo_GetInfo()
{
    var x = this.target.offsetLeft;
    var y = this.target.offsetTop;
    var pe = this.target.offsetParent;
    while (pe)
    {
        x += pe.offsetLeft;
        y += pe.offsetTop;
        pe = pe.offsetParent;
    }
    
    this.x = x;
    this.y = y;
    this.width = this.target.offsetWidth;
    this.height = this.target.offsetHeight;
}