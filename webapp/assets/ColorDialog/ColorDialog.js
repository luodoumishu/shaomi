//千一网络 www.cftea.com
//ColorDialog（颜色对话框） v2.0
//http://www.cftea.com/products/webComponents/ColorDialog/
//需要 ControlInfo v1.0
//应用示例：
/*
var colorDialog = new ColorDialog("colorButton");
colorDialog.onColorSelecting = function () { document.body.style.backgroundColor = colorDialog.selectingColor; }
colorDialog.onColorSelected = function () { document.body.style.backgroundColor = colorDialog.selectedColor; }
colorDialog.onColorCancelled = function () { document.body.style.backgroundColor = colorDialog.selectedColor; }
colorDialog.create();
*/
//若在使用中出现在空白区域单击鼠标左键，但对话框不消失的情况，请注意，这不属于程序的错误，关于该情况的说明，请参见：http://www.cftea.com/c/2007/11/T8E0QPE0V0KIN6W9.asp。


//参数 targetIdOrTarget，触发对话框显示的 HTML 控件 Id 名称或控件对象。
//属性 selectingColor，正在选择的颜色。
//属性 selectedColor，已经选择的颜色。
//事件 onColorSelecting，当正在选择颜色时对应的方法。
//事件 onColorSelected，当已经选择颜色后对应的方法。
//事件 onColorCancelled，当没有选择颜色并关闭对话框后对应的方法。
//方法 create()，创建对话框。
function ColorDialog(targetIdOrTarget)
{
    this.target = (typeof(targetIdOrTarget)=="string") ? document.getElementById(targetIdOrTarget) : targetIdOrTarget; //target 触发对话框显示
    this.selectingColor = ""; //正在选择的颜色
    this.selectedColor = ""; //已经选择的颜色
    this.onColorSelecting = null; //当正在选择颜色时对应的方法
    this.onColorSelected = null; //当已经选择颜色后对应的方法
    this.onColorCancelled = null; //当没有选择颜色并关闭对话框后对应的方法
    
    this.colorHex = new Array("00", "33", "66", "99", "CC", "FF");
    this.spColorHex = new Array("FF0000", "00FF00", "0000FF", "FFFF00", "00FFFF", "FF00FF");
    
    this.dialog = null;
    this.previewBox = null;
    this.previewValueBox = null;
    
    this.create = ColorDialog_create; //创建对话框（但不显示出来）
    this.createPreviewPanel = ColorDialog_createPreviewPanel;
    this.createColorPanel = ColorDialog_createColorPanel;
    this.createColorElement = ColorDialog_createColorElement;
    
    this.initializeDialog = ColorDialog_initializeDialog;
    this.showDialog = ColorDialog_showDialog;
    this.getIsDialogShowing = ColorDialog_getIsDialogShowing;
    
    //初始化
    this.commandSource = "body"; //表明最先触发事件的是哪个，因为对控件的动作（比如点击）最终 document.body 也会接收到，为了知晓到底点击了谁，加此成员。
    var me = this;
    if (window.attachEvent)
    {
        //IE 系列浏览器
        this.target.attachEvent("onclick", function () {
            me.commandSource = "target";
            me.initializeDialog();
            me.showDialog(true);
            });
        document.body.attachEvent("onclick", function () {
            if (me.commandSource == "body")
            {
                //直接点击的 document.body
                if (me.getIsDialogShowing())
                {
                    //对话框处于显示状态，此时隐藏对话框，并触发事件。
                    me.showDialog(false);
                    
                    if (typeof(me.onColorCancelled) == "function")
                    {
                        me.onColorCancelled();
                    }
                }
            }
            me.commandSource = "body"; //在本次动作中，commandSource 已经不再使用，恢复默认值。
            });
    }
    else
    {
        //FF 系列浏览器
        this.target.addEventListener("click", function () {
            me.commandSource = "target";
            me.initializeDialog();
            me.showDialog(true);
            }, false);
        document.body.addEventListener("click", function () {
            if (me.commandSource == "body")
            {
                //直接点击的 document.body
                if (me.getIsDialogShowing())
                {
                    //对话框处于显示状态，此时隐藏对话框，并触发事件。
                    me.showDialog(false);
                    
                    if (typeof(me.onColorCancelled) == "function")
                    {
                        me.onColorCancelled();
                    }
                }
            }
            me.commandSource = "body"; //在本次动作中，commandSource 已经不再使用，恢复默认值。
            }, false);
    }
}


//创建对话框（但不显示出来）
//公开 dialog 
function ColorDialog_create(showId)
{
    var table = document.createElement("table");
    table.border = "0";
    table.cellspacing = "0";
    table.cellpadding = "0";
    table.style.border = "1px solid #000000";
    table.style.borderCollapse = "separate"; //为了保证对话框的面板之间有间隔，使用 separate
    table.style.backgroundColor = "#CCCCCC";
    table.style.display = "none"; //创建的对话框默认是不显示的。
    table.style.zIndex= "15000";
    var tbody = document.createElement("tbody");
    table.appendChild(tbody);
    
    //创建并加入预览面板
    var tr = document.createElement("tr");
    tbody.appendChild(tr);
    var td = document.createElement("td");
    tr.appendChild(td);
    var previewPanel = this.createPreviewPanel();
    td.appendChild(previewPanel);
    
    //创建并加入颜色面板
    var tr2 = document.createElement("tr");
    tbody.appendChild(tr2);
    var td2 = document.createElement("td");
    tr2.appendChild(td2);
    var colorPanel = this.createColorPanel();
    td2.appendChild(colorPanel);
    
    //加入到 document.body
   // document.body.appendChild(table);
    if(null == showId){
    	document.body.appendChild(table);
    }else{
    	var showObj = (typeof(showId)=="string") ? document.getElementById(showId) : showId;
        showObj.appendChild(table)
    }
    this.dialog = table;
}


//创建对话框中的预览面板，返回面板对象。
//公开 previewBox 和 previewValueBox
function ColorDialog_createPreviewPanel()
{
    var table = document.createElement("table");
    table.border = "0";
    table.cellspacing = "0";
    table.cellpadding = "0";
    table.style.borderCollapse = "collapse";
    
    var tbody = document.createElement("tbody");
    table.appendChild(tbody);
    
    var tr = document.createElement("tr");
    tbody.appendChild(tr);
    
    var td1 = document.createElement("td");
    tr.appendChild(td1);
    
    var td2 = document.createElement("td");
    tr.appendChild(td2);
    
    //预览框
    this.previewBox = document.createElement("input");
    this.previewBox.type = "text";
    this.previewBox.disabled = true;
    this.previewBox.style.width = "40px";
    this.previewBox.style.border = "1px solid #000000";
    this.previewBox.style.fontFamily = "Arial";
    this.previewBox.style.fontSize = "13px";
    td1.appendChild(this.previewBox);
    
    //预览颜色值框
    this.previewValueBox = document.createElement("input");
    this.previewValueBox.type = "text";
    this.previewValueBox.disabled = true;
    this.previewValueBox.style.width = "70px";
    this.previewValueBox.style.border = "1px inset #CCCCCC";
    this.previewValueBox.style.fontFamily = "Arial";
    this.previewValueBox.style.fontSize = "13px";
    this.previewValueBox.style.backgroundColor = "#FFFFFF";
    this.previewValueBox.style.color = "#000000";
    td2.appendChild(this.previewValueBox);
    
    return table;
}


//创建对话框中的颜色面板，返回面板对象。
function ColorDialog_createColorPanel()
{
    var table = document.createElement("table");
    table.border = "0";
    table.cellspacing = "0";
    table.cellpadding = "0";
    table.style.borderCollapse = "collapse";
    
    var tbody = document.createElement("tbody");
    table.appendChild(tbody);
    
    //横向扫描分两批显示颜色
    for (i = 0; i < 2; i++)
    {
        for (j = 0; j < this.colorHex.length; j++)
        {
            var tr = document.createElement("tr");
            tbody.appendChild(tr);
            
            //左边的特殊颜色
            if (i == 0)
            {
                tr.appendChild(this.createColorElement("#" + this.colorHex[j] + this.colorHex[j] + this.colorHex[j], false));
            }
            else
            {
                tr.appendChild(this.createColorElement("#" + this.spColorHex[j], false));
            }
            
            //间隔块
            tr.appendChild(this.createColorElement("", true));
            
            for (k = 0; k < 3; k++)
            {
                for (l = 0; l < this.colorHex.length; l++)
                {
                    tr.appendChild(this.createColorElement("#" + this.colorHex[k+i*3] + this.colorHex[l] + this.colorHex[j], false));
                }
            }
        }
    }
    
    return table;
}


//创建颜色面板中的颜色元素，返回颜色元素对象。
//参数 color，颜色值。
//参数 isSeparator，是否只是作分隔符作用的颜色元素，如果为 true，则只是呈现，不包含相关事件等。
function ColorDialog_createColorElement(color, isSeparator)
{
    var me = this;
    
    var td = document.createElement("td");
    td.style.width = "11px";
    td.style.height = "11px";
    
    if (!isSeparator)
    {
        td.style.border = "1px solid #000000";
        td.style.backgroundColor = color;
        td.style.cursor = "pointer";
        
        if (window.attachEvent)
        {
            //IE 系列浏览器
            td.attachEvent("onmouseover", function () {
                td.style.backgroundColor = "#FFFFFF"; //突显当前选择项
                me.previewBox.style.backgroundColor = color; //预览
                me.previewValueBox.value = color; //预览值
                me.selectingColor = color; //设置当前选择颜色
                if (typeof(me.onColorSelecting) == "function")
                {
                    me.onColorSelecting(); //事件
                }
                });
            td.attachEvent("onmouseout", function () {
                td.style.backgroundColor = color; //恢复当前选择项
                me.previewBox.style.backgroundColor = ""; //预览
                me.previewValueBox.value = ""; //预览值
                me.selectingColor = ""; //设置当前选择颜色
                });
            td.attachEvent("onclick", function () {
                me.selectedColor = color; //设置已经选择的颜色
                me.commandSource = "dialog"; //告知 document.body 的 onclick 事件是由对话框触发的。
                me.showDialog(false); //隐藏对话框
                if (typeof(me.onColorSelected) == "function")
                {
                    me.onColorSelected(); //事件
                }
                });
        }
        else
        {
            //FF 系列浏览器
            td.addEventListener("mouseover", function () {
                td.style.backgroundColor = "#FFFFFF"; //突显当前选择项
                me.previewBox.style.backgroundColor = color; //预览
                me.previewValueBox.value = color; //预览值
                me.selectingColor = color; //设置当前选择颜色
                if (typeof(me.onColorSelecting) == "function")
                {
                    me.onColorSelecting(); //事件
                }
                }, false);
            td.addEventListener("mouseout", function () {
                td.style.backgroundColor = color; //恢复当前选择项
                me.previewBox.style.backgroundColor = ""; //预览
                me.previewValueBox.value = ""; //预览值
                me.selectingColor = ""; //设置当前选择颜色
                }, false);
            td.addEventListener("click", function () {
                me.selectedColor = color; //设置已经选择的颜色
                me.commandSource = "dialog"; //告知 document.body 的 onclick 事件是由对话框触发的。
                me.showDialog(false); //隐藏对话框
                if (typeof(me.onColorSelected) == "function")
                {
                    me.onColorSelected(); //事件
                }
                }, false);
        }
    }
    
    return td;
}


//初始化对话框
function ColorDialog_initializeDialog()
{
    this.previewBox.style.backgroundColor = this.selectedColor;
    this.previewValueBox.value = this.selectedColor;
}


//显示或隐藏对话框
//参数 show，指定是显示还是隐藏对话框。
function ColorDialog_showDialog(show)
{
    if (show)
    {
        //在 this.target 的下方显示，与 this.target 左对齐。
        var controlInfo = new ControlInfo(this.target);
        controlInfo.GetInfo();
        this.dialog.style.position = "absolute";
        this.dialog.style.left = controlInfo.x + "px";
        this.dialog.style.top = controlInfo.y + controlInfo.height + "px";

        this.dialog.style.display = "block";
    }
    else
    {
        this.dialog.style.display = "none";
    }
}


//返回对话框是否处于显示状态
function ColorDialog_getIsDialogShowing()
{
    return (this.dialog.style.display != "none");
}