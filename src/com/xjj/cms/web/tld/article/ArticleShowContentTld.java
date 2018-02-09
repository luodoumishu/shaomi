package com.xjj.cms.web.tld.article;


import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.cms.article.service.ICmsArticleHistoryService;
import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.base.util.DateTools;
import com.xjj.cms.base.util.NumberTools;
import com.xjj.cms.base.util.StringTools;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.util.SpringContextUtil;

public class ArticleShowContentTld  extends BodyTagSupport {
	private String showAll;
	private CmsArticle ca;
	private CmsArticleHistory cah;
	private Boolean showArticle;
	public Boolean isShowArticle() {
		return showArticle;
	}

	public void setShowArticle(Boolean showArticle) {
		this.showArticle = showArticle;
	}

	public CmsArticle getCa() {
		return ca;
	}

	public void setCa(CmsArticle ca) {
		this.ca = ca;
	}

	public String getShowAll() {
		return showAll;
	}

	public void setShowAll(String showAll) {
		this.showAll = showAll;
	}

	public CmsArticleHistory getCah() {
		return cah;
	}

	public void setCah(CmsArticleHistory cah) {
		this.cah = cah;
	}

	public int doStartTag() throws JspException {
		ICmsArticleService cmsArticleService = (ICmsArticleService)SpringContextUtil.getInstance().getBean("cmsArticleService");
		ICmsArticleHistoryService cmsArticleHistoryService = (ICmsArticleHistoryService)SpringContextUtil.getInstance().getBean("cmsArticleHistoryService");
		
		String strArticleId = pageContext.getRequest().getParameter("articleId");  
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());
		
		ca = cmsArticleService.get(strArticleId);
		
		if(ca == null){
			cah = cmsArticleHistoryService.get(strArticleId);
		}
		
		if(cah == null){
			return SKIP_BODY;
		}
		
		showArticle = true;
		

		if(showArticle){
			//阅读数加1
			try {
				if(ca == null){
					cmsArticleHistoryService.saveVisitNumAddOne(cah);
				}else{
					cmsArticleService.saveVisitNumAddOne(ca);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this.EVAL_BODY_BUFFERED;
		}else{
			return SKIP_BODY;
		}
	}
	
	public int doEndTag() throws JspException {
		ICmsArticleService cmsArticleService = (ICmsArticleService)SpringContextUtil.getInstance().getBean("cmsArticleService");
		ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService)SpringContextUtil.getInstance().getBean("CmsChannelItemService");
		ICmsMenuService cmsMenuService =  (ICmsMenuService)SpringContextUtil.getInstance().getBean("cmsMenuService");
		
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletConfig().getServletContext());
        String pg = pageContext.getRequest().getParameter("pg");           //第几页
        String menuId = pageContext.getRequest().getParameter("menuId"); 
        Map map1 = new HashMap();
	/*	if(ca != null){
			if(showArticle){
				BodyContent bc = getBodyContent();
				String body = bc.getString();
				String articleTitle = "<font style=\"font-size:"+ca.getTitleSize()+"px;color:"+ca.getTitleColor()+"\">"+ca.getTitle()+"</font>";
				body = StringHelper.replace(body, "$_article_title" , articleTitle);
				
				String aContent = ca.getContent();
				if(aContent == null){
					body = StringHelper.replace(body, "$_article_content" , "");
				}else{
					if(StringUtils.isBlank(aContent)){
						body = StringHelper.replace(body, "$_article_content" , "");
                        body = StringHelper.replace(body, "$_article_page_num_str" , "");
                    }else{
                        //第一种方法
                        String content = this.getPgString(aContent,pg,menuId);
                        //第二种方法
                        //String content = pageBreak(cac.getArticle_content());
                        body = StringHelper.replace(body, "$_article_content" , content);
                        body = StringHelper.replace(body, "$_article_page_num_str" , this.getPgNumString()==null?"":this.getPgNumString());
                    }

                    //取出相关附件 xsg add
                    String hql = "from CmsArticleEnclosureOther c where c.cmsArticle.id =:articleId";
                    Map map = new HashMap();
                    map.put("articleId", ca.getId());
                    List<CmsArticleEnclosureOther> listEcl = cmsArticleManager.getByHQL(hql, map);
                    if(listEcl!=null&&!listEcl.isEmpty()){
                        StringBuffer buffer = new StringBuffer("<table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"boder3\">");
                        buffer.append("<tr><td><b>相关附件：</b></td></tr>");
                        int index = 1;
                        for(CmsArticleEnclosureOther cmlo:listEcl){
                            buffer.append("<tr><td class=\"font14\">"+(index++)+"、<a href=\""+context.getServletContext().getContextPath()+"/web/ecl.do?method=down&eclId="+cmlo.getId()+"\">"+cmlo.getEcl_name()+"</a></td></tr>");
                        }
                        buffer.append("</table>");
                        body = StringHelper.replace(body, "$_article_Affix_str" , buffer.toString());
                    }else body = StringHelper.replace(body, "$_article_Affix_str" ,"");
                }
				//作者
				body = StringHelper.replace(body, "$_article_author" , StringTools.formatNull2Blank(ca.getAuthor()));//根据用户要求更改getA_author()
				
//				body = StringHelper.replace(body, "$_article_image_path" , StringTools.formatNull2Blank( CmsCC.ENCLOSURE_URI + ca.getImage_path()));
//				body = StringHelper.replace(body, "$_article_video_path" , StringTools.formatNull2Blank( ca.getVideo_file_path()));
				//来源
				body = StringHelper.replace(body, "$_article_source" , StringTools.formatNull2Blank(ca.getSource()));
				//发布时间
				String issue_ymd = DateTools.dateToString(ca.getCreateTime(), DateTools.FULL_YMD); 
				body = StringHelper.replace(body, "$_article_issue_time" , issue_ymd);
				body = StringHelper.replace(body, "$_article_read_number" , ca.getReadCount());

		        //副标题
//				if(StringUtils.isEmpty(ca.getArticle_sub_title())){
//					body = StringHelper.replace(body, "$_article_sub_title" , "");
//				}else{
//					body = StringHelper.replace(body, "$_article_sub_title" , "——"+StringTools.formatNull2Blank(ca.getArticle_sub_title()));
//				}
				try {
					pageContext.getOut().print(body);
				} catch (IOException e) {
				}
				return this.EVAL_PAGE;
			}else{
				return this.SKIP_BODY;
			}
		}else */
		if(cah != null){
			if(showArticle){
				BodyContent bc = getBodyContent();
				String body = bc.getString();
				String articleTitle = "<font style=\"font-size:"+cah.getTitleSize()+"px;color:"+cah.getTitleColor()+"\">"+cah.getTitle()+"</font>";
				body = StringHelper.replace(body, "$_article_title" , articleTitle);
				
				String aContent = cah.getContent();
				if(aContent == null){
					body = StringHelper.replace(body, "$_article_content" , "");
				}else{
					if(StringUtils.isBlank(aContent)){
						body = StringHelper.replace(body, "$_article_content" , "");
                        body = StringHelper.replace(body, "$_article_page_num_str" , "");
                    }else{
                        //第一种方法
                        String content = this.getPgString(aContent,pg,menuId);
                        //第二种方法
                        //String content = pageBreak(cac.getArticle_content());
                        body = StringHelper.replace(body, "$_article_content" , content);
                        body = StringHelper.replace(body, "$_article_page_num_str" , this.getPgNumString()==null?"":this.getPgNumString());
                    }

                   /* //取出相关附件 xsg add
                    String hql = "from CmsArticleEnclosureOther c where c.cmsArticle.id =:articleId";
                    Map map = new HashMap();
                    map.put("articleId", ca.getId());
                    List<CmsArticleEnclosureOther> listEcl = cmsArticleManager.getByHQL(hql, map);
                    if(listEcl!=null&&!listEcl.isEmpty()){
                        StringBuffer buffer = new StringBuffer("<table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"boder3\">");
                        buffer.append("<tr><td><b>相关附件：</b></td></tr>");
                        int index = 1;
                        for(CmsArticleEnclosureOther cmlo:listEcl){
                            buffer.append("<tr><td class=\"font14\">"+(index++)+"、<a href=\""+context.getServletContext().getContextPath()+"/web/ecl.do?method=down&eclId="+cmlo.getId()+"\">"+cmlo.getEcl_name()+"</a></td></tr>");
                        }
                        buffer.append("</table>");
                        body = StringHelper.replace(body, "$_article_Affix_str" , buffer.toString());
                    }else body = StringHelper.replace(body, "$_article_Affix_str" ,"");*/
                }
				//作者
				body = StringHelper.replace(body, "$_article_author" , StringTools.formatNull2Blank(cah.getAuthor()));//根据用户要求更改getA_author()
				
//				body = StringHelper.replace(body, "$_article_image_path" , StringTools.formatNull2Blank( CmsCC.ENCLOSURE_URI + ca.getImage_path()));
//				body = StringHelper.replace(body, "$_article_video_path" , StringTools.formatNull2Blank( ca.getVideo_file_path()));
				//来源
				body = StringHelper.replace(body, "$_article_source" , StringTools.formatNull2Blank(cah.getSource()));
				//发布时间
				String issue_ymd = DateTools.dateToString(cah.getCreateTime(), DateTools.FULL_YMD); 
				body = StringHelper.replace(body, "$_article_issue_time" , issue_ymd);
				
				Date publicTime = cah.getPublicTime();
		        String strPublicTime = DateTools.dateToString( publicTime , DateTools.FULL_YMD);
		        body = StringHelper.replace(body, "$_article_public_time" , strPublicTime);
				
				body = StringHelper.replace(body, "$_article_read_number" , cah.getReadCount());

		        //副标题
//				if(StringUtils.isEmpty(ca.getArticle_sub_title())){
//					body = StringHelper.replace(body, "$_article_sub_title" , "");
//				}else{
//					body = StringHelper.replace(body, "$_article_sub_title" , "——"+StringTools.formatNull2Blank(ca.getArticle_sub_title()));
//				}
				try {
					pageContext.getOut().print(body);
				} catch (IOException e) {
				}
				return this.EVAL_PAGE;
			}else{
				return this.SKIP_BODY;
			}
		}else{
			return this.SKIP_BODY;
		}

		
		
	}
	
    public void release() {
        super.release();
        ca = null;
        showArticle = null;
    }

    //第一种方法
    //翻页用的
    public String pgNumString;
    public String getPgString(String content,String pg,String menuId){
        pgNumString = "";
        //显示分页
        String pageNum = "<div id=\"page_break\">";
        StringBuffer pageBufferNum = null;
        String menuIdStr = menuId!=null&&menuId.length()>0?"&menuId="+menuId:"";

        content = filterContext(content);
        String pattern = "<div style=\"page-break-after: always\"><span style=\"display: none\">&nbsp;</span></div>";
        String[] newContent = content.split(pattern);
        if(newContent.length>1) pageBufferNum = new StringBuffer(pageNum);

        //当前页
        int p = 0;
        try {
            p = Integer.parseInt(pg);
            if(p>newContent.length) p = newContent.length;
        } catch (Exception e) { p = 0;}

        //共几页
        int m = 0;
        if(newContent.length>1){
            pageBufferNum.append("<div class='num'>");
            for (int i = 0; i < newContent.length; i++) {
                m = i + 1;
                String classStr = "";
                if(m==p) classStr = "class='on'";
                if(p==0&&m==1) classStr = "class='on'";
                pageBufferNum.append("<li "+classStr+" onclick=\"np('?articleId="+ca.getId() + menuIdStr+ "&pg=" + m + "')\">" + m + "</li>");
                //pageBufferNum.append("<li "+classStr+"><a href='?articleId="+ca.getId() + menuIdStr+ "&pg=" + m + "'>" + m + "</a></li>");
            }
            pageBufferNum.append("</div>");
        }
        if(pageBufferNum!=null){
            pageBufferNum.append("</div>");
            pgNumString = pageBufferNum.toString();
        }

        if (p == 0 || p == 1) {
            // 如果是第一次显示新闻（p＝0）或是显示第一页的时候（p=1)
            return newContent[0];
        } else {
            return newContent[p - 1];
        }

    }

    public String getPgNumString() {
        return pgNumString;
    }

    //由于后台的分页格式在JAVA中获取不到，先替换。
    private String filterContext(String content){
        String patternStr = "(?is)<div style=\"page-break-after: always\">(.*?)<span style=\"display: none\">&nbsp;</span></div>";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        return m.replaceAll("<div style=\"page-break-after: always\"><span style=\"display: none\">&nbsp;</span></div>");
    }

    //取出内容分页代码   第二种方法
    private String pageBreak(String content){
        //由于后台的分页格式在JAVA中获取不到，先替换。
        /*String patternStr = "(?is)<div style=\"page-break-after: always\">(.*?)<span style=\"display: none\">&nbsp;</span></div>";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        content = m.replaceAll("<div style=\"page-break-after: always\"><span style=\"display: none\">&nbsp;</span></div>");*/
        content = filterContext(content);
        //对内容进行分页
        String pattern = "<div style=\"page-break-after: always\"><span style=\"display: none\">&nbsp;</span></div>";
        String[] strSplit = content.split(pattern);
        int count = strSplit.length;
        String outstr = "";
        String returnValue = "";
        if (count > 1) {
            outstr = "<div id=\"page_break\">";
            for (int i = 1; i <= strSplit.length; i++) {
                if (i <= 1) {
                    outstr += "<div id=\"page_" + String.valueOf(i) + "\">" + strSplit[i - 1] + "</div>";
                } else {
                    outstr += "<div id=\"page_" + String.valueOf(i) + "\" class=\"collapse\">" + strSplit[i - 1] + "</div>";
                }
            }
            outstr += "<div class='num'>";
            for (int j = 1; j <= strSplit.length; j++) {
                outstr += "<li>" + String.valueOf(j) + "</li>";
            }
            outstr += "</div></div>";
            returnValue = outstr;
        } else {
            returnValue = content;
        }
        return returnValue;
    }


    //调用翻页程序
    private String actionUrl;

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }


}
