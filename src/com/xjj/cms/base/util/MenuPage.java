package com.xjj.cms.base.util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.xjj.framework.core.page.Page;

public class MenuPage {

	private MenuPage(){}
	
	public static MenuPage getInstance(){
		return new MenuPage();
	}
	
	public String createPageStrForJs(HttpServletRequest request , Page apage ,int pageSize  ){
		int endPageNo = apage.getIndex();
		String pageNo1 = (String) request.getAttribute("pageNo");
		String strPageNo="";
		if(pageNo1 ==null){
			strPageNo = request.getParameter("pageNo");
		}else{
			strPageNo = pageNo1;
		}
		
		
		int curPageNo = 0;
		
		int[] pageNoForJs = new int[4];
		pageNoForJs[0] = 1;
		pageNoForJs[3] = endPageNo;
		if(StringUtils.isEmpty(strPageNo)){
			curPageNo = 1;
			pageNoForJs[1] = curPageNo;
			if(endPageNo <= 2){
				pageNoForJs[2] = endPageNo;
			}else{
				pageNoForJs[2] = pageNoForJs[1] + 1;
			}
		}else{
			Integer pageNo = NumberTools.formatObject2IntDefaultZeroNoExp(strPageNo);
			if(pageNo <= 1){
				curPageNo = 1;
				pageNoForJs[1] = curPageNo;
				if(endPageNo <= 2){
					pageNoForJs[2] = endPageNo;
				}else{
					pageNoForJs[2] = curPageNo + 1;
				}				
			}else if(pageNo > 1 && pageNo < endPageNo){
				curPageNo = pageNo;
				pageNoForJs[1] = curPageNo -1;
				if(endPageNo <= 2){
					pageNoForJs[2] = endPageNo;
				}else{
					pageNoForJs[2] = curPageNo + 1;
				}
			}else{
				curPageNo = endPageNo;
				pageNoForJs[1] = curPageNo -1;
				pageNoForJs[2] = curPageNo;
			}
		}
		//合计：").append(apage.getTotalCount()).append("条&nbsp共").append(endPageNo).append("页&nbsp;&nbsp;").append("当前是：第").append(curPageNo).append("页&nbsp;
		// 第1页/共8页（共158篇文章）
		StringBuffer tmp = new StringBuffer();
		int allpage = endPageNo;
		if(allpage==0){
			allpage = 1;
		}
		tmp.append("第"+curPageNo+"页/共"+allpage+"页（共"+apage.getTotal()+"条记录）&nbsp;&nbsp;")
		   .append("<a href=\"#\" onclick=\"gotoPage(").append(pageNoForJs[0]).append(")\">首页</a>&nbsp;&nbsp;")
		   .append("<a href=\"#\" onclick=\"gotoPage(").append(pageNoForJs[1]).append(")\">上页</a>&nbsp;&nbsp;")
		   .append("<a href=\"#\" onclick=\"gotoPage(").append(pageNoForJs[2]).append(")\">下页</a>&nbsp;&nbsp;")
		   .append("<a href=\"#\" onclick=\"gotoPage(").append(pageNoForJs[3]).append(")\">末页</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		 //  .append("<input type='text' style='width:30px;margin-bottom:-3px;' id='gotopage'  value='"+curPageNo+"' />&nbsp;<span onclick='gotoPage2(document.getElementById(\"gotopage\").value,"+endPageNo+")' style='cursor:pointer' id='go' >go</span>");
		return tmp.toString();
	}
	
	public String createPageStr(HttpServletRequest request , Page apage,int pageSize ){
		int totalpage = 0;
		if(apage.getTotal()%pageSize==0){
			totalpage = apage.getTotal()/pageSize;
		}else{
			totalpage =  apage.getTotal()/pageSize+1;
		}
		
		StringBuffer strbufUrl = new StringBuffer(request.getRequestURI());
		int curPageNo = 1;
		int totalCount = 0;
		int totalPageNo = 0;
		try{
			curPageNo = apage.getIndex();
			totalCount = apage.getTotal();
			totalPageNo = totalpage;
		}catch(NullPointerException ne){}
		
		String str  = request.getQueryString();
		try {
			str = URLEncoder.encode(str,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = getQueryStrArray(str);
		
		
		
		String[] indexLink = new String[2];
		String[] endLink = new String[2];
		indexLink[0] = strbufUrl.toString() + StringTools.setParameters(url, "pageNo", "1");
		indexLink[1] = strbufUrl.toString() + StringTools.setParameters(url, "pageNo", (curPageNo - 1)+"");

		endLink[0] = strbufUrl.toString() + StringTools.setParameters(url, "pageNo", (curPageNo + 1)+"");
		endLink[1] = strbufUrl.toString() + StringTools.setParameters(url, "pageNo", totalPageNo+"");
		int allpage = totalPageNo;
		if(allpage==0){
			allpage = 1;
		}
        //	"第"+curPageNo+"页/共"+endPageNo+"页（共"+apage.getTotalCount()+"条记录）"
		String pagination="第"+curPageNo+"页/共"+allpage+"页（共"+totalCount+"条记录）&nbsp;&nbsp;";
		if(curPageNo == 1){
			//当前页为第一页
			if(totalPageNo <=1){
				//总页数也只有一页
				//首页，上一页，下一页，尾页均没有链接
				pagination += pageElementTogether(true , null) + pageElementTogether(false , null);
			}else{
				//总页数不止一页
				//首页，上一页没有链接，下一页，尾页有链接
				pagination += pageElementTogether(true , null ) + pageElementTogether(false , endLink);
			}
		}else if(curPageNo > 1 && curPageNo < totalPageNo){
			//不是第一页也不是最后一页
			pagination += pageElementTogether(true , indexLink ) + pageElementTogether(false , endLink);
		}else if(curPageNo == totalPageNo){
			//最后一页
			pagination += pageElementTogether(true , indexLink ) + pageElementTogether(false , null);
		}
		//pagination += "&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' style='width:30px;margin-bottom:-3px;' id='gotopage'  value='"+curPageNo+"' />&nbsp;<span onclick='gotoPage2(document.getElementById(\"gotopage\").value,"+totalPageNo+")' style='cursor:pointer' id='go' >go</span>";
		return pagination;
		

		
	
	}
	/**
	 * 计算分页的显示内容
	 * @param isBeginSign
	 * @param link
	 * @return
	 */
	private String pageElementTogether(boolean isBeginSign , String[] link){
		String strRtn = null;
		if(link == null){
			//没有链接
			if(isBeginSign){
				strRtn = "首页&nbsp;&nbsp;上一页&nbsp;&nbsp;";
			}else{
				strRtn = "下一页&nbsp;&nbsp;尾页";
			}
		}else{
			//有链接
			StringBuffer tmp = new StringBuffer();
			if(isBeginSign){
				tmp.append("<a href=\"")
				   .append(link[0])
				   .append("\">首页</a>&nbsp;&nbsp;<a href=\"")
				   .append(link[1])
				   .append("\">上一页</a>&nbsp;&nbsp;");
			}else{
				tmp.append("<a href=\"")
				   .append(link[0])
				   .append("\">下一页</a>&nbsp;&nbsp;<a href=\"")
				   .append(link[1])
				   .append("\">尾页</a>");
			}
			strRtn = tmp.toString();
		}
		return strRtn;
	}
	
	
	private String getQueryStrArray(String queryString){
		String strRtn = "";
		try {
			if(StringUtils.isNotBlank(queryString)){
				queryString = URLDecoder.decode(queryString,"utf-8");
				String[] str = queryString.split("&");
				strRtn = "?";
				boolean hasPageNo = false;
				for(int i = 0 ; i < str.length ; i ++ ){
					if(str[i].indexOf("pageNo=") == 0){
						String[] temp = str[i].split("=");
						str[i] = temp[0]+"=${pageNo}";
						hasPageNo = true;
					}
					str[i]=new String(str[i].getBytes("ISO-8859-1"),"utf-8");
					strRtn += str[i]+"&";
				}
				if(!hasPageNo){
					strRtn = strRtn + "pageNo=${pageNo}";
				}else{
					strRtn = strRtn.substring(0, strRtn.length()-1);
				}
			}else{
				strRtn += "?pageNo=${pageNo}";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strRtn;
	}
	
}
