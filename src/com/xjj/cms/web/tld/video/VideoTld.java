package com.xjj.cms.web.tld.video;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.hibernate.util.StringHelper;

import com.xjj.cms.base.util.DateTools;
import com.xjj.cms.video.model.CmsVideo;
import com.xjj.cms.video.service.ICmsVideoService;
import com.xjj.framework.core.util.SpringContextUtil;
import com.xjj.jdk17.utils.StringUtil;

public class VideoTld  extends BodyTagSupport {
	
	private Integer titleLength; 
	
	public int doEndTag() throws JspException {
		try {
			ICmsVideoService cmsVideoService = (ICmsVideoService)SpringContextUtil.getInstance().getBean("cmsVideoService");
			
			 HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	        String path = request.getContextPath();
	    	String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	    	String basePath = ip+path + "/file/";
	    	
			String videoId = pageContext.getRequest().getParameter("videoId");
//			System.out.println(videoId + "***************");
			CmsVideo video = cmsVideoService.get(videoId);
			
			String body ="";
			BodyContent bc = getBodyContent();
			body = bc.getString();
			if(null != video){
				Integer count = video.getRead_count();
				if(null == count){
					count = 0;
				}
		        count ++;
		        video.setRead_count(count);
		        cmsVideoService.update(video);
				
		        body = StringHelper.replace(body, "$_video_count" , count.toString());
		        
				String videoTitle = video.getName().replaceAll("<BR>", " ").replaceAll("<br>", " ");
		        String showTitle = null;
		        if(titleLength == null){
		        	showTitle = videoTitle;
		        }else{
		            if(videoTitle.length() <=titleLength){
		            	showTitle = videoTitle;
		            }else{
		            	showTitle = videoTitle.substring(0 , titleLength-1) + "...";
		            }
		        }
		        body = StringHelper.replace(body, "$_video_video_title" , showTitle);
		        body = StringHelper.replace(body, "$_video_video_full_title" , videoTitle);
		        body = StringHelper.replace(body, "$_video_video_id" , video.getId().toString());
		        
		        String author = video.getCreatUserName();
		        if(StringUtil.isEmpty(author)){
		        	body = StringHelper.replace(body, "$_video_author" , "");
		        }else{
		        	body = StringHelper.replace(body, "$_video_author" , author);
		        }
		        
		        Date createTime = video.getCreateTime();
		        String strCreateTime = DateTools.dateToString( createTime , DateTools.FULL_YMD);
		        body = StringHelper.replace(body, "$_video_time" , strCreateTime);
				
		        String videoFileName = video.getVideo_filename();
		        String videoFilePath = video.getVideo_filepath();
		        if(null != videoFileName && !videoFileName.equals("") && null != videoFilePath && !videoFilePath.equals("")){
		        	if(videoFilePath.contains("\\")){
		        		videoFilePath = videoFilePath.replace("\\", "/");
		        	}
		        	body = StringHelper.replace(body, "$_video_path" ,basePath+  videoFilePath + "/" + videoFileName);
		        }else{
		        	body = StringHelper.replace(body, "$_video_path" , "");
		        }
		        String imgFileName = video.getImg_filename();
		        String imgFilePath = video.getImg_filepath();
		        
		        if(null != imgFileName && !imgFileName.equals("") && null != imgFilePath && !imgFilePath.equals("")){
		        	if(imgFilePath.contains("\\")){
		        		imgFilePath = imgFilePath.replace("\\", "/");
		        	}
		        	body = StringHelper.replace(body, "$_img_path" , basePath + imgFilePath + "/" +imgFileName);
		        }else{
		        	body = StringHelper.replace(body, "$_img_path" , "");
		        }
		        String remark = video.getRemark();
		        if(StringUtil.isEmpty(remark)){
		        	body = StringHelper.replace(body, "$_video_remark" , "");
		        }else{
		        	body = StringHelper.replace(body, "$_video_remark" , remark);
		        }
			}
			pageContext.getOut().print(body);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.EVAL_PAGE;
		}
		return this.EVAL_PAGE;
	}

	public Integer getTitleLength() {
		return titleLength;
	}

	public void setTitleLength(Integer titleLength) {
		this.titleLength = titleLength;
	}
	
	
}
