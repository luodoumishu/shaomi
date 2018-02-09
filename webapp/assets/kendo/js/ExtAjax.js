$.ExtAjax = function (options, waittingText, aimDiv) {
    var extWaittingMaskStr = "<div id='extWaittingMask' style='position:relative;'><div style='position:absolute;left:10px;top:10px;' class='ExtAjax-Waitting'></div><div style='display:inline-block;margin-left:45px;margin-top:17px;'>" + waittingText + "</div></div>";
    var extWaittingMask = $(extWaittingMaskStr);
    var mask = $("<div id=\"maskOfProgressImage\"></div>").addClass("mask").addClass("hide"); //Div遮罩
    var PositionStyle = "fixed";
    //是否将Loading固定在aimDiv中操作,否则默认为全屏Loading遮罩
    if (aimDiv != null && aimDiv != "" && aimDiv != undefined) {
        $(aimDiv).css("position", "relative").append(extWaittingMask).append(mask);
        PositionStyle = "absolute";
    }
    else {
        $("body").append(extWaittingMask).append(mask);
    }
    mask.css({
        "position": PositionStyle,
        "top": "0",
        "right": "0",
        "bottom": "0",
        "left": "0",
        "z-index": "1000",
        "background-color": "#000000",
        "display": "none"
    });
    var complete = options.complete;
    options.complete = function (httpRequest, status) {
        extWaittingMask.hide();
        mask.hide();
        if (complete) {
            complete(httpRequest, status);
        }
    };
    options.async = false;
    extWaittingMask.show().css({
        "position": PositionStyle,
        "top": "40%",
        "left": "50%",
        "margin-top": function () { return -1 * extWaittingMask.height() / 2; },
        "margin-left": function () { return -1 * extWaittingMask.width() / 2; }
    });
    mask.show().css("opacity", "0.1");
    $.ajax(options);
};