var read_fail = "读取失败！";
var select_nothing = "您还没有选择操作项！";

var del_succeed = "删除成功！";
var del_fail = "删除失败！";
var del_confirmation = "是否需要删除选中记录？";

var add_title = "[新增]";
var add_succeed = "添加成功！";
var add_fail = "添加失败！";
var add_again = "再次添加！";

var edit_title = "[修改]";
var edit_succeed = "修改成功！";
var edit_fail = "修改失败！";
var invalid = "表单中有无效信息，请检查！";

var post_fail = "操作失败！";
var post_succeed = "操作成功！";

var ShowUpdateTitle = "更新日志";

var true_n = "是";
var false_n = "否";
function edittype(typename) {
    if (typename == "add") {
        this.mode = "add";
        this.ok = add_succeed;
        this.fail = add_fail;
        this.title = add_title;
        this.post_fail = post_fail;
    }
    else if (typename == "edit") {
        this.mode = "edit";
        this.ok = edit_succeed;
        this.fail = edit_fail;
        this.title = edit_title;
        this.post_fail = post_fail;
    }
    else if (typename == "del") {
        this.mode = "del";
        this.ok = del_succeed;
        this.fail = del_fail;
        this.post_fail = post_fail;
    }
}

