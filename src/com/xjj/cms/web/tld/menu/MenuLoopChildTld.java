package com.xjj.cms.web.tld.menu;


import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
//import org.apache.jasper.runtime.BodyContentImpl;
import org.hibernate.util.StringHelper;

import com.xjj.cms.menu.model.CmsMenu;


/**
 * 循环子栏目
 * @author fengjunkong
 * @date 2014-9-4
 */
public class MenuLoopChildTld  extends BodyTagSupport {
	public String childMenuId;
	public Iterator itr;
	public String sourceBody = "";
	public String body = "";
	public CmsMenu curChildCm;
	protected int i;
	/**
	 * 样式，多样式以;分隔
	 */
	public String styleClass;
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}	
	/**
	 * 样式显示类型
	 * 暂规定两种模式，分别为：
	 * ABBB模式  和  ABAB模式
	 * 默认为 ABAB模式
	 */
	public String styleClassType;	
	public String getStyleClassType() {
		return styleClassType;
	}
	public void setStyleClassType(String styleClassType) {
		this.styleClassType = styleClassType;
	}
   
	//如果body的内容需要被计算，那么doStartTag方法必须返回EVAL_BODY_BUFFERED，否则，它将返回 SKIP_BODY。
	public int doStartTag() throws JspException {
		//System.out.println("------MenuLoopChildTld-----doStartTag()------");
		CmsMenu cm = (CmsMenu)pageContext.getAttribute("curMenu");
//		Set setChildMenu = cm.getSetChildMenu();
//		itr = setChildMenu.iterator();
		return this.EVAL_BODY_BUFFERED;
	}

	
    public void doInitBody() {
    	//System.out.println("------MenuLoopChildTld-----doInitBody()------");
    	i = 0;
    }
	
	//EVAL_BODY_BUFFERED，否则，它应返回SKIP_BODY
	public int doAfterBody() throws JspException {
		//System.out.println("------MenuLoopChildTld-----doAfterBody()------");

		BodyContent bc = getBodyContent();
		sourceBody = bc.getString();
		bc.clearBody();


		if(curChildCm != null){
			
			childMenuId = curChildCm.getId();
			String tmp = sourceBody;
			tmp = StringHelper.replace(tmp, "$_child_menu_name" , curChildCm.getMenuName());
//			tmp = StringHelper.replace(tmp, "$_menu_loop_style" , TldCommon.getClass((i-1) , styleClass , styleClassType));
			body += tmp;
			
	        return super.EVAL_BODY_BUFFERED;//继续循环
		}else{
			return super.SKIP_BODY;
		}
		
	}
	
	
	public int doEndTag() throws JspException {
		//System.out.println("------MenuLoopChildTld-----doEndTag()------");
		
		try {
            pageContext.getOut().print(body);
        } catch (IOException e) {
        }
        
        itr = null;
        sourceBody = "";
        body = "";
        curChildCm = null;
        childMenuId = null;
		return this.EVAL_PAGE;
	}


}
