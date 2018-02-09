package com.xjj.cms.web.tld.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.hibernate.util.StringHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.util.SpringContextUtil;


/**
 * 位置标签
 * @author fengjunkong
 * @date 2014-9-5
 */
public class MenuPlaceTld   extends BodyTagSupport {
	
	
	public int doEndTag()  throws JspException {
		//MenuChildTld处赋值
		CmsMenu curMenu = (CmsMenu)pageContext.getAttribute("curMenu");
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());
		ICmsMenuService cmsMenuManager =  (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
		
		if(curMenu == null){
			String strMenuId = pageContext.getRequest().getParameter("menuId");
			
			
			if(strMenuId.equals("")||strMenuId==null){
				return this.SKIP_BODY;
			}else{
				curMenu = (CmsMenu)cmsMenuManager.get( strMenuId);
				
			}
		}
//		
//		List next2TopMenuList = new ArrayList();
//		if(curMenu.getIs_top().intValue() == CC.YES.intValue()){
//			next2TopMenuList.add(curMenu);
//		}else{
//			getTopMenu(curMenu , next2TopMenuList);
//		}
//		
//		
//		int length = next2TopMenuList.size();
//		/*for(int i = length-1 ; i >=0 ; i -- ){*/
//			CmsMenu cm = (CmsMenu)next2TopMenuList.get(0);
//			
			String menuUrl = "menu.jsp";
			
			BodyContent bc = getBodyContent();
	        String body = bc.getString();
	        String menuName = curMenu.getMenuName();
	        if(menuName.length()>8){
	        	menuName = menuName.substring(0, 7) + "...";
	        }
	        body = StringHelper.replace(body, "$_menu_name" , menuName);
	        body = StringHelper.replace(body, "$_menu_url" , menuUrl+"?menuId="+curMenu.getId());
	        try {
	            pageContext.getOut().print(body);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		/*}*/
		
		return (EVAL_PAGE);
	}
	
	
//	protected void getTopMenu(CmsMenu cm , List next2TopMenuList){
//		next2TopMenuList.add(cm);
//		CmsMenu parentMenu = cm.getParentMenu();
//		if(parentMenu != null && parentMenu.getIs_top().intValue() != CC.YES.intValue() && parentMenu.getMenu_type() != 3){
//			getTopMenu(parentMenu , next2TopMenuList);
//		}
//
//	}

}
