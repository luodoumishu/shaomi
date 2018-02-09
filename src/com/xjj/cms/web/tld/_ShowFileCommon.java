package com.xjj.cms.web.tld;

import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;

import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.file.model.CmsFile;
import com.xjj.cms.menu.model.CmsMenu;


public class _ShowFileCommon {
	
	public static String getBodyStr(String body , CmsAffix cmsAffix , Map param , Integer i){
		int addNumber = 0;
		Object objCurMenu = param.get("curMenu");
		String strMenuIdParam = "";
		Integer titleLength = (Integer)param.get("titleLength");
		String articleTitle = cmsAffix.getAffix_originalName().replaceAll("<BR>", " ").replaceAll("<br>", " ");
        String showTitle = articleTitle;
        if(objCurMenu != null){
			CmsMenu curMenu = (CmsMenu)objCurMenu;
			strMenuIdParam = "menuId=" + curMenu.getId();
		}
        if(titleLength == null){
        	showTitle = articleTitle;
        }else{
            if(articleTitle.length() < titleLength+addNumber){
            	showTitle = articleTitle;
            }else{
            	showTitle = articleTitle.substring(0 , titleLength+addNumber) + "...";
            }
        }
//        CmsMenu mainMenu = cmsFileEnclosure.getMainCmsMenu();
//         <%=url %><%=CmsCC.ENCLOSURE_URI %>/${page.firstDir}/${page.secondDir}/${page.fileName} 
//        String pre_path = CmsCC.ARTICLE_AFFIX_PATH+"/";
//        String file_url = pre_path+cmsFile.getCmsAffixs().get(index);
        body = StringHelper.replace(body, "$_file_eclname" , showTitle);
        body = StringHelper.replace(body, "$_file_full_eclname" , articleTitle);
        body = StringHelper.replace(body, "$_file_id" , cmsAffix.getId().toString());
//        body = StringHelper.replace(body, "$_file_create_time" , cmsFile.getCreate_date().toString().substring(0,10));
        body = StringHelper.replace(body, "$_file_whole_title" , articleTitle);
//        body = StringHelper.replace(body, "$_file_url" , file_url);
//        body = StringHelper.replace(body, "$_file_download_count" , cmsFile.getDownloadCount().toString());
        /**** edit start by yeyunfeng 2014-05-23**************/
//        body = StringHelper.replace(body, "$_file_memo" , cmsFileEnclosure.getMemo());
//        body = StringHelper.replace(body, "$_file_slt" , pre_path+cmsFileEnclosure.getImage_slt_swf());
        /************** edit end **************/
        Object objJspUrl = param.get("jspUrl");
        if(objJspUrl != null){
        	String jspUrl = (String)objJspUrl;
        	if(jspUrl.indexOf("?") > 0){  
        		jspUrl = jspUrl + "&"+strMenuIdParam+"&fileId=" + cmsAffix.getId();
        	}else{
        		jspUrl = jspUrl + "?"+ (StringUtils.isNotBlank(strMenuIdParam)?(strMenuIdParam+"&"):"")+"&fileId=" + cmsAffix.getId() ;
        	}
        	body = StringHelper.replace(body, "$_article_url" , jspUrl);
        }else{
        	
//                String showArticleContentPage = mainMenu.getCmsArticleTemplet().getTemplet_url();
                //System.out.println("----showArticleContentPage = " + showArticleContentPage);
//               body = StringHelper.replace(body, "$_article_url" , CC.CTX + showArticleContentPage + "?"+ (StringUtils.isNotBlank(strMenuIdParam)?(strMenuIdParam+"&"):"")+"fileId=" + cmsFile.getId());
        	
        }
		return body;
	}
}
