<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/comm/taglib.jsp"%>
<%
String path = request.getContextPath();
String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
String basePath = ip+path+"/";
String themeName = request.getParameter("themeName");
String themeId = request.getParameter("themeId");
%>
<!DOCTYPE html>
<html>
<head>
<link href="${ctx}/assets/_base/css/attachment.css" rel="stylesheet"></link>
<link href="${ctx}/pages/vote/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/comm/kendo_js.jsp"%>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.upload.ext.js"></script>
<script src="${ctx}/assets/_base/js/xjj.Pinyin.js"></script>
<script src="${ctx}/assets/kendoUI/js-ext/kendo.multiselect.ext.js"></script>
<script src="${ctx}/assets/_base/js/jquery.form.js"></script>
<script src="${ctx}/comm/js/core.js"></script>
<script type="text/javascript">
    	var path = "<%=path%>";
    	var ip  = "<%=ip%>";
</script>

</head>
<body>
	<div class="gao_box">
	<div class="gao_txbg">
    	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="gao_tab">
          <tr>
            <td colspan="7" class="gao_biaot"><%=themeName%></td>
          </tr>
          <!-- 投票项from 开始 -->
          <form id="itemform" name="itemform" modelAttribute="dataform" method="post" action="${ctx}/cms/vote/item/saveOrUpdate.json">
          <input type="hidden" id="id" name="id" data-bind="value:id" />
          <input type="hidden" id=theme_id name="theme_id" data-bind="value:theme_id" />
          <input type="hidden" id="sort" name="sort" data-bind="value:sort" />
          <tr>
            <td class="gao_lmtd">投票项名称:<span style="color: red;"> *</span></td>
            <td colspan="6"><label for="item_name"></label>
            <input type="text" name="item_name" id="item_name" data-bind="value:item_name" class="gao_ipt1" style="width:80%;" onkeyup="checkLen(this);"/>
            <span style="margin-left: 10px;color: gray;">还可以输入 </span><span id="count" style="color: gray;">55</span><span style="color: gray;"> 个文字</span>
            </td>
          </tr>
          <tr>
            <td class="gao_lmtd">投票类型:</td>
            <td>
            	<input type="radio" name="item_type" value="0" data-bind="checked: item_type" onclick="unChangeItem();" checked="checked" />单选&nbsp;
                <input type="radio" name="item_type" value="1" data-bind="checked: item_type" onclick="unChangeItem();"/> 多选&nbsp;
                <input type="radio" name="item_type" value="2" data-bind="checked: item_type" onclick="changeItem();"/> 问答&nbsp;
            </td>
            <td class="gao_lmtd">显示类型:</td>
            <td>
            	<input type="radio" name="show_type" value="0" data-bind="checked: show_type" checked="checked" onclick="changeShowType0();"/>横排&nbsp;
                <input type="radio" name="show_type" value="1" data-bind="checked: show_type" onclick="changeShowType1();"/>竖排&nbsp;
            </td>
            <td class="gao_lmtd">个数：</td>
            <td><select id="detailNum">  
				<option value = "2">2</option>  
				<option value = "3">3</option>  
				<option value = "4">4</option>  
				<option value = "5">5</option> 
			</select>&nbsp;</td>
            <td><img src="images/gao_sc.jpg" id="imgSC" width="69" height="26" onclick="setDetail();" style="vertical-align:middle;cursor: pointer;"/></td>
          </tr>
          </form>
          <!-- 投票项from 结束 -->
          <tr>
            <td colspan="7" class="gao_tjsr" id = "detailList">
            </td>
          </tr>
          <tr>
            <td colspan="7" class="gao_qdan">
            	<img src="images/gao_bc.jpg" width="99" height="30" onclick="submitItemForm('<%=themeId%>');" style="cursor: pointer;"/>&nbsp;
            	<img src="images/gao_add.jpg" width="99" height="30" onclick="cancel();" style="cursor: pointer;"/>
            </td>
          </tr>
        </table>
  </div>
  <div class="gao_tplist" id = "itemUL">
  <span><font style="font-weight: bold;">投票项预览 :</font></span>
  </div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/pages/vote/js/vote_theme_setting.js"></script>
<script type="text/javascript">
	var editType = "add";
	var item = null;
    //下拉数据源
    $(document).ready(function(){
    	xjj.cms.vote.init_editPage();
    	xjj.cms.vote.initItem("<%=themeId%>");
    });
</script>

</html>