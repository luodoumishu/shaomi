package com.xjj.cms.web.tld.channel;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.cms.article.service.ICmsArticleHistoryService;
import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.util.SpringContextUtil;


/**
 * 此标签作为栏目、文章、投票页面的最开始执行的类，分别去栏目id、文章id、投票id来判断jsp的类型
 * 判断出类型后取得具体的对象，赋值到pageContext范围内，
 * 供后续标签取值，及直接显示到页头、head里
 * @author fengjunkong
 *
 */
public class ChannelGetObjectTld extends BodyTagSupport {
	
	public int doStartTag() throws JspException {
		
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());
		
		ICmsMenuService cmsMenuManager =  (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
		ICmsArticleHistoryService cmsArticleHistoryService = (ICmsArticleHistoryService)SpringContextUtil.getInstance().getBean("cmsArticleHistoryService");
		ICmsArticleService cmsArticleService = (ICmsArticleService)SpringContextUtil.getInstance().getBean("cmsArticleService");
		String strMenuId = pageContext.getRequest().getParameter("menuId");
		
		String strArticleId = pageContext.getRequest().getParameter("articleId");
		String strMainId = pageContext.getRequest().getParameter("mainId");
		String zt = pageContext.getRequest().getParameter("tc");
		
		
		if(StringUtils.isNotEmpty(strArticleId)){
			CmsArticle ca;
			CmsArticleHistory cah = null;
			if(strArticleId==null||strArticleId.equals("")){
				return this.SKIP_BODY;
			}else{
				ca = (CmsArticle)cmsArticleService.get(strArticleId);
			}
			if(null == ca){
				cah = (CmsArticleHistory)cmsArticleHistoryService.get(strArticleId);
				ca = cmsArticleHistoryService.history2article(cah);
			}
			String preTitle = ca.getTitle();
			if(StringUtils.isNotEmpty(strMenuId)){
				CmsMenu cm;
				if(strMenuId==null||strMenuId.equals("")){
					return this.SKIP_BODY;
				}else{
					cm = (CmsMenu)cmsMenuManager.get(strMenuId);
				}
				preTitle = preTitle + " - " + cm.getMenuName();
			}
			pageContext.setAttribute("curArticle", ca);
			pageContext.setAttribute("PRE_TITLE", preTitle);
			pageContext.setAttribute("PRE_DESCRIPTION", ca.getSummary());
		}
		
		if(StringUtils.isNotEmpty(strMenuId)){
			CmsMenu cm;
			CmsMenu topMenu = new CmsMenu();
			if(strMenuId==null||strMenuId.equals("")){
				return this.SKIP_BODY;
			}else{
				cm = (CmsMenu)cmsMenuManager.get(strMenuId);
//				/******start 得到菜单最顶层栏目  fengjunkong 14-09-05**********/
//				topMenu = cmsMenuManager.getTopMenuByTheChildMenu(cm);
			}
			pageContext.setAttribute("curMenu", cm);
			pageContext.setAttribute("PRE_TITLE", cm.getMenuName());
//			pageContext.setAttribute("topMenuName", topMenu.getMenuName());
		}/*else if(StringUtils.isNotEmpty(strMainId)){
			
			Integer intId = NumberTools.formatObject2IntDefaultZeroNoExp(strMainId);
			CmsVoteMain cvm ;
			if(intId.intValue() <= 0){
				return this.SKIP_BODY;
			}else{
				cvm = (CmsVoteMain)baseDao.get(CmsVoteMain.class, intId);
			}
			
			pageContext.setAttribute("curCvm", cvm);
			pageContext.setAttribute("PRE_TITLE", cvm.getVote_title());
		}else if(StringUtils.isNotEmpty(zt)){
			CmsTopicsManager cmsTopicsManager = (CmsTopicsManager) context.getBean("cmsTopicsManager");
			CmsTopics ct = (CmsTopics)cmsTopicsManager.getObjectByTc(zt);
			pageContext.setAttribute("curTopic", ct);
			pageContext.setAttribute("PRE_TITLE", ct.getTopic_name());
		}*/
		return this.EVAL_BODY_AGAIN;
	}
}
