package com.xjj.cms.video;

import com.xjj.cms.avconvert.AVConvert;
import com.xjj.cms.base.util.FileTools;
import com.xjj.cms.video.model.CmsVideo;
import com.xjj.cms.video.service.ICmsVideoService;
import com.xjj.framework.core.util.SpringContextUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

public class VideoConvertThread implements Runnable {


    private String path;
    private HttpServletRequest request;
    private CmsVideo video;
    public VideoConvertThread(String path, CmsVideo video, HttpServletRequest request) {
        this.path = path;
		this.video = video;
        this.request = request;
    }

    @Override
	public void run() {
		String ext = this.path.substring(this.path.lastIndexOf(".")).toLowerCase();
		if (!ext.equals(".flv")) {  //如果是其他格式的视频，将其转换成flv格式的
			System.out.println("开始转换格式!");
			String outFilePath = path.replace(ext, ".flv");
			String imgFilePath = path.replace(ext, ".jpg");
			String videoId = this.video.getId();
			AVConvert me = new AVConvert(this.request,videoId);
			me.convert(path, outFilePath); //转换视频格式 flv
			System.out.println("格式转换完成！");
			ICmsVideoService cmsVideoService =  SpringContextUtil.getInstance().getBean("cmsVideoService");
			this.video.setState("3");
			me.getVideoImg("600","480",outFilePath,imgFilePath);
			this.video.setImg_ext("jpg");
			String imgFileName = imgFilePath.substring(imgFilePath.lastIndexOf(File.separator)+1,imgFilePath.length()) ;
			this.video.setImg_filename(imgFileName);
			this.video.setImg_filepath(this.video.getVideo_filepath());
			this.video.setVideo_filename(outFilePath.substring(outFilePath.lastIndexOf(File.separator) + 1, outFilePath.length()));
			FileTools.deleteFile(path);//转换完成后删除文件
			cmsVideoService.update(this.video);
		}else {
			String imgFilePath = path.replace(ext, ".jpg");
			String videoId = this.video.getId();
			AVConvert me = new AVConvert(this.request,videoId);
			ICmsVideoService cmsVideoService =  SpringContextUtil.getInstance().getBean("cmsVideoService");
			me.getVideoImg("200","200",path,imgFilePath);
			this.video.setImg_ext("jpg");
			String imgFileName = imgFilePath.substring(imgFilePath.lastIndexOf(File.separator)+1,imgFilePath.length()) ;
			this.video.setImg_filename(imgFileName);
			this.video.setImg_filepath(this.video.getVideo_filepath());
			this.video.setVideo_filename(path.substring(path.lastIndexOf(File.separator) + 1, path.length()));
			cmsVideoService.update(this.video);
		}

	}

}
