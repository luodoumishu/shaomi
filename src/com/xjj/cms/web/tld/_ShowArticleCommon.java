package com.xjj.cms.web.tld;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.DateTools;
import com.xjj.cms.menu.model.CmsMenu;


public class _ShowArticleCommon {
	
	public static String getBodyStr(String body , CmsArticle cmsArticle , Map param , Integer i){
		int addNumber = 0;
		body = StringHelper.replace(body, "$_article_no" , i+1+"");
		body = StringHelper.replace(body, "$_article_ii" , i+"");
		Object startPageNo = param.get("startPageNo");
		Object objCurMenu = param.get("curMenu");
		String strMenuIdParam = "";
		if(objCurMenu != null){
			CmsMenu curMenu = (CmsMenu)objCurMenu;
			strMenuIdParam = "menuId=" + curMenu.getId();
		}
		if(startPageNo != null){
			body = StringHelper.replace(body, "$_article_page_no" , (Integer)startPageNo+i+1+"");
		}else{
			body = StringHelper.replace(body, "$_article_page_no" , i+1+"");
		}
		
		
		
      /*  CmsMenu mainMenu = cmsArticle.getMainCmsMenu();
        body = StringHelper.replace(body, "$_article_main_menu_name" , mainMenu.getMenu_name());*/
        
        Date issueTime = cmsArticle.getCreateTime();
        String strIssueTime = DateTools.dateToString( issueTime , DateTools.FULL_YMD);
        body = StringHelper.replace(body, "$_article_issue_time" , strIssueTime);
        Date publicTime = cmsArticle.getPublicTime();
        String strPublicTime = DateTools.dateToString( publicTime , DateTools.FULL_YMD);
        body = StringHelper.replace(body, "$_article_public_time" , strPublicTime);
		String[] issues_split = strIssueTime.split("-");
		//年
		body = StringHelper.replace(body, "$_article_issue_year" , issues_split[0]);
		//月日
		if(issues_split.length>2){
		body = StringHelper.replace(body, "$_article_issue_md" , issues_split[1]+"-"+issues_split[2]);
		}
        String newcss = (String)param.get("newcss");
        if(StringUtils.isNotEmpty(newcss)){
            if(DateTools.getDateAfterDay(issueTime, CmsCC.ARTICLE_NEW_FLAG_DAY).after(new Date())){
            	body = StringHelper.replace(body, "$_article_new_flag" , "<img src=\""+newcss+"\" align=\"absmiddle\" />");
            	addNumber = addNumber - 2;
            }else{
            	body = StringHelper.replace(body, "$_article_new_flag" , "");
            }
        }else{
        	body = StringHelper.replace(body, "$_article_new_flag" , "");
        }
        
        
//        Object objJspUrl = param.get("jspUrl");
        Object objJspUrl = "article.jsp";
        if(objJspUrl != null){
        	String jspUrl = (String)objJspUrl;
        	String pyCode = "";
        	if (CmsCC.ENABLED_MENU_FJSQ) { //栏目分级授权开启
				//部门拼音代码
        		Object obj = param.get("orgCode");
        		pyCode = (String)obj;
			}
        	if(jspUrl.indexOf("?") > 0){  
        		jspUrl = jspUrl + "&"+strMenuIdParam+"&articleId=" + cmsArticle.getId();

        	}else{
        		jspUrl = jspUrl + "?"+ (StringUtils.isNotBlank(strMenuIdParam)?(strMenuIdParam+"&"):"") + "articleId=" + cmsArticle.getId();
        	}
        	if (CmsCC.ENABLED_MENU_FJSQ) { //栏目分级授权开启
        		jspUrl += "&org="+pyCode;
			}
        	body = StringHelper.replace(body, "$_article_url" , jspUrl);
        }else{
        	String articleUrl = cmsArticle.getLinkUrl();
//        	if(StringUtils.isNotBlank(articleUrl)){
        		body = StringHelper.replace(body, "$_article_url" , "article.jsp");
//        	}else{
//                String showArticleContentPage = mainMenu.getCmsArticleTemplet().getTemplet_url();
//                //System.out.println("----showArticleContentPage = " + showArticleContentPage);
//                body = StringHelper.replace(body, "$_article_url" , CC.CTX + showArticleContentPage + "?"+ (StringUtils.isNotBlank(strMenuIdParam)?(strMenuIdParam+"&"):"") +"articleId=" + cmsArticle.getId());
//        	}
        }
      
        String imagePath = cmsArticle.getBehalf_imageUrl();
        if(StringUtils.isNotBlank(imagePath)){
        	body = StringHelper.replace(body, "$_article_has_image_flag" , "<font color=\"red\">[图]</font>");
        	addNumber = addNumber - 1;
        	body = StringHelper.replace(body, "$_article_image_path" , imagePath);
        }else{
        	body = StringHelper.replace(body, "$_article_has_image_flag" , "");
        	body = StringHelper.replace(body, "$_article_image_path" , "");
        }
        
        Object objStyle = param.get("styleClass");
        if(objStyle != null){
        	String[] style = ((String)objStyle).split(";");
        	body = StringHelper.replace(body, "$_article_style" , style[i%2]);
        }else{
        	body = StringHelper.replace(body, "$_article_style" , "");
        }
        
        
        
		Integer titleLength = (Integer)param.get("titleLength");
        String articleTitle = cmsArticle.getTitle().replaceAll("<BR>", " ").replaceAll("<br>", " ");
        String showTitle = null;
        Integer memoLength = (Integer)param.get("memoLength");
        if(titleLength == null){
        	showTitle = articleTitle;
        }else{
            if(articleTitle.length() <=titleLength){
            	showTitle = articleTitle;
            }else{
            	showTitle = articleTitle.substring(0 , titleLength-1) + "...";
            }
        }
        body = StringHelper.replace(body, "$_article_article_title" , showTitle);
        
        body = StringHelper.replace(body, "$_article_article_full_title" , articleTitle);
        
        body = StringHelper.replace(body, "$_article_image_id" , "image_xixi-0"+(i+1));
        
        body = StringHelper.replace(body, "$_article_article_id" , cmsArticle.getId().toString());
        body = StringHelper.replace(body, "$_article_author" , cmsArticle.getAuthor());
        body = StringHelper.replace(body, "$_title_abstract" , cmsArticle.getSummary());
        body = StringHelper.replace(body, "$_to_linkUrl" , cmsArticle.getLinkUrl());
        body = StringHelper.replace(body, "$_article_read_num" , cmsArticle.getReadCount());
        String _article_content = "";
        _article_content = cmsArticle.getContent();
        body = StringHelper.replace(body, "$_article_content" , _article_content);
        Integer count = i+1;
        if(count>3){
        	body = StringHelper.replace(body, "$_article_toptype" ,"top10");
        }else{
        	body = StringHelper.replace(body, "$_article_toptype" ,"top3");
        }
        body = StringHelper.replace(body, "$_article_top" , count.toString());
        /***end****/
        return body;
	}
	
	public static String getBodyStr(String body , CmsArticleHistory cmsArticle , Map param , Integer i){
		int addNumber = 0;
		body = StringHelper.replace(body, "$_article_no" , i+1+"");
		body = StringHelper.replace(body, "$_article_ii" , i+"");
		Object startPageNo = param.get("startPageNo");
		Object objCurMenu = param.get("curMenu");
		String strMenuIdParam = "";
		if(objCurMenu != null){
			CmsMenu curMenu = (CmsMenu)objCurMenu;
			strMenuIdParam = "menuId=" + curMenu.getId();
		}
		if(startPageNo != null){
			body = StringHelper.replace(body, "$_article_page_no" , (Integer)startPageNo+i+1+"");
		}else{
			body = StringHelper.replace(body, "$_article_page_no" , i+1+"");
		}
		
		
		
      /*  CmsMenu mainMenu = cmsArticle.getMainCmsMenu();
        body = StringHelper.replace(body, "$_article_main_menu_name" , mainMenu.getMenu_name());*/
        
        Date issueTime = cmsArticle.getCreateTime();
        String strIssueTime = DateTools.dateToString( issueTime , DateTools.FULL_YMD);
        body = StringHelper.replace(body, "$_article_issue_time" , strIssueTime);
        Date publicTime = cmsArticle.getPublicTime();
        String strPublicTime = DateTools.dateToString( publicTime , DateTools.FULL_YMD);
        body = StringHelper.replace(body, "$_article_public_time" , strPublicTime);
		String[] issues_split = strIssueTime.split("-");
		//年
		body = StringHelper.replace(body, "$_article_issue_year" , issues_split[0]);
		//月日
		if(issues_split.length>2){
		body = StringHelper.replace(body, "$_article_issue_md" , issues_split[1]+"-"+issues_split[2]);
		}
        String newcss = (String)param.get("newcss");
        if(StringUtils.isNotEmpty(newcss)){
            if(DateTools.getDateAfterDay(issueTime, CmsCC.ARTICLE_NEW_FLAG_DAY).after(new Date())){
            	body = StringHelper.replace(body, "$_article_new_flag" , "<img src=\""+newcss+"\" align=\"absmiddle\" />");
            	addNumber = addNumber - 2;
            }else{
            	body = StringHelper.replace(body, "$_article_new_flag" , "");
            }
        }else{
        	body = StringHelper.replace(body, "$_article_new_flag" , "");
        }
        
        
//        Object objJspUrl = param.get("jspUrl");
        Object objJspUrl = "article.jsp";
        if(objJspUrl != null){
        	String jspUrl = (String)objJspUrl;
        	String pyCode = "";
        	String pyName = "";
        	if (CmsCC.ENABLED_MENU_FJSQ) { //栏目分级授权开启
				//部门拼音代码
        		Object orgCode = param.get("orgCode");
        		pyCode = (String)orgCode;
        		Object orgName = param.get("orgName");
        		pyName = (String)orgName;
			}
        	if(jspUrl.indexOf("?") > 0){  
        		jspUrl = jspUrl + "&"+strMenuIdParam+"&articleId=" + cmsArticle.getId();

        	}else{
        		jspUrl = jspUrl + "?"+ (StringUtils.isNotBlank(strMenuIdParam)?(strMenuIdParam+"&"):"") + "articleId=" + cmsArticle.getId();
        	}
        	if (CmsCC.ENABLED_MENU_FJSQ) { //栏目分级授权开启
        		jspUrl += "&orgCode="+pyCode;
        		try {
					pyName = java.net.URLEncoder.encode(pyName, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
        		jspUrl += "&orgName="+pyName;
			}
        	body = StringHelper.replace(body, "$_article_url" , jspUrl);
        }else{
        	String articleUrl = cmsArticle.getLinkUrl();
//        	if(StringUtils.isNotBlank(articleUrl)){
        		body = StringHelper.replace(body, "$_article_url" , "article.jsp");
//        	}else{
//                String showArticleContentPage = mainMenu.getCmsArticleTemplet().getTemplet_url();
//                //System.out.println("----showArticleContentPage = " + showArticleContentPage);
//                body = StringHelper.replace(body, "$_article_url" , CC.CTX + showArticleContentPage + "?"+ (StringUtils.isNotBlank(strMenuIdParam)?(strMenuIdParam+"&"):"") +"articleId=" + cmsArticle.getId());
//        	}
        }
      
        String imagePath = cmsArticle.getBehalf_imageUrl();
        if(StringUtils.isNotBlank(imagePath)){
        	body = StringHelper.replace(body, "$_article_has_image_flag" , "<font color=\"red\">[图]</font>");
        	addNumber = addNumber - 1;
        	body = StringHelper.replace(body, "$_article_image_path" , imagePath);
        }else{
        	body = StringHelper.replace(body, "$_article_has_image_flag" , "");
        	body = StringHelper.replace(body, "$_article_image_path" , "");
        }
        
        Object objStyle = param.get("styleClass");
        if(objStyle != null){
        	String[] style = ((String)objStyle).split(";");
        	body = StringHelper.replace(body, "$_article_style" , style[i%2]);
        }else{
        	body = StringHelper.replace(body, "$_article_style" , "");
        }
        
    	Integer titleLength = (Integer)param.get("titleLength");
        String articleTitle = cmsArticle.getTitle().replaceAll("<BR>", " ").replaceAll("<br>", " ");
        String showTitle = null;
        showTitle = getLimitLengthString(articleTitle,titleLength);
        
        body = StringHelper.replace(body, "$_article_article_title" , showTitle);
        
        body = StringHelper.replace(body, "$_article_article_full_title" , articleTitle);
        
        body = StringHelper.replace(body, "$_article_image_id" , "image_xixi-0"+(i+1));
        
        body = StringHelper.replace(body, "$_article_article_id" , cmsArticle.getId().toString());
        body = StringHelper.replace(body, "$_article_author" , cmsArticle.getAuthor());
        body = StringHelper.replace(body, "$_title_abstract" , cmsArticle.getSummary());
        body = StringHelper.replace(body, "$_to_linkUrl" , cmsArticle.getLinkUrl());
        body = StringHelper.replace(body, "$_article_read_num" , cmsArticle.getReadCount());
        String _article_content = "";
        _article_content = cmsArticle.getContent();
        body = StringHelper.replace(body, "$_article_content" , _article_content);
        Integer count = i+1;
        if(count>3){
        	body = StringHelper.replace(body, "$_article_toptype" ,"top10");
        }else{
        	body = StringHelper.replace(body, "$_article_toptype" ,"top3");
        }
        body = StringHelper.replace(body, "$_article_top" , count.toString());
        /***end****/
        return body;
	}
	
	/** 
	* 截取字符串 len为字节长度 
	* @param str 
	* @param len 
	* @return 
	* @throws UnsupportedEncodingException 
	*/ 
	public static String getLimitLengthString(String str,int len){ 
		try{ 
			int counterOfDoubleByte = 0; 
			byte[] b = str.getBytes("gbk"); 
			if(b.length <= len){
				return str; 
			}
			for(int i = 0; i < len; i++){
				if(b[i] < 0){
					counterOfDoubleByte++; 
				}
			} 
			if(counterOfDoubleByte % 2 == 0){
				return new String(b, 0, len, "gbk") +"..."; 
			}else{
				return new String(b, 0, len - 1, "gbk") +"..."; 
			}
		}catch(Exception ex){ 
			ex.printStackTrace();
			return ""; 
		} 
	}
}
