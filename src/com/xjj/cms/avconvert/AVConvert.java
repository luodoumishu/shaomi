package com.xjj.cms.avconvert;

import com.xjj.cms.base.util.progress.NumUtil;
import com.xjj.cms.base.util.progress.Progress;
import com.xjj.cms.base.util.progress.ProgressPool;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ResourceBundle;

/**
 * @Description: 使用ffmpeg进行视频、音频格式转换
 * @author 张运宇 QQ:554766941   
 * @date 2014-11-4 上午11:26:37 
 */
public class AVConvert {
	//配置文件路径
	private static final String BUNDLE_NAME = "com.xjj.cms.avconvert.configuration"; 
	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	//支持转换的格式
	private static final String SUPPORTED_FORMATS = RESOURCE_BUNDLE.getString("SUPPORTED_FORMATS");

	private HttpServletRequest request;
	private String videoId;
	public AVConvert(HttpServletRequest request, String videoId){
		this.request = request;
		this.videoId = videoId;

	}
	/**
	  * @Description: 视频、音频格式转换 
	  * @param inFile 原文件    
	  * @param outFile 目标文件
	  * @return String 结果代码 
	  * @author 张运宇 2014-11-5下午2:55:58
	 */
	public String convert(String inFile,String outFile) {
		int _ar=Integer.parseInt(RESOURCE_BUNDLE.getString("_AR"));
		int _ab=Integer.parseInt(RESOURCE_BUNDLE.getString("_AB"));
		int _qscale=Integer.parseInt(RESOURCE_BUNDLE.getString("_QSCALE"));
	 return convert(inFile,outFile,_ar,_ab,_qscale);
	}
    /**
      * @Description:  视频、音频格式转换
      * @param  inFile 原文件
      * @param  outFile 目标文件
      * @param  _ar 音频采样率Hz
      * @param  _ab 音频比特率kbps
      * @param  _qscale 视频动态码率 以<数值>质量为基础的VBR，取值0.01-255，越小质量越好
      * @return String 结果代码  
      * @author 张运宇 2014-11-4上午11:28:05
     */
    public String convert(String inFile,String outFile,int _ar,int _ab,int _qscale) {


		/***************edit by yeyunfeng  添加进度条***************/
		if (null != ProgressPool.getInstance().get(this.videoId)){
			ProgressPool.getInstance().remove(this.videoId);
		}
		Progress status = new Progress();
		status.setActionName("正在视频转换");
		ProgressPool.getInstance().add(this.videoId, status);
		/***************edit by yeyunfeng  end******************/
         //检查原文件是否存在
    	 if(!checkFile(inFile)){
    		return RESOURCE_BUNDLE.getString("NOFILE_ERROR");
    	 }
    	//检查输出文件夹是否存在
    	 if(!checkFolder(outFile)){
    		return RESOURCE_BUNDLE.getString("NOFOLDER_ERROR");
    	 }
    	 //检查格式是否支持
		 if((!checkContentType(inFile))||(!checkContentType(outFile))){
			 return RESOURCE_BUNDLE.getString("NOSUPPORT_ERROR");
		 }
    	 //创建转换格式命令
		 String commend=RESOURCE_BUNDLE.getString("FFMPEG_PATH");
         commend=commend+" -i "+inFile+" -ar "+_ar+" -ab "+_ab+" -qscale "+_qscale+" -y "+outFile;
    	 try {
	    	//执行命令行创建进程(启动程序ffmpeg.exe)
	        Runtime rt = Runtime.getRuntime();
	        Process proc = rt.exec(commend);
            //及时读取标准错误流缓冲区的数据，避免造成死锁
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
			int total_ms = 0;
            while ( (line = br.readLine()) != null){
				//Duration: 00:00:30.09, start: 0.000000, bitrate: 6977 kb/s
				//frame=  331 fps=110 q=8.0 size=    6069kB time=00:00:11.07 bitrate=4488.2kbits/s
				//ffmpeg -y –i input.flv –r 1 –q:v 2 –f image2 output.jpg
				//ffmpeg -i test.asf -y -f  image2  -ss 60 -vframes 1  test1.jpg
				//ffmpeg -i test.asf -y -f image2 -t 0.001 -s 352x240 a.jpg
				line = line.trim();

				if (line.startsWith("Duration")){
					line = line.split(",")[0];
					line = line.replace("Duration: ","").trim();
					try {
						total_ms = getTotalMS(line);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
				String zh_sj = "";
				int zh_ms = 0;
				if (line.startsWith("frame")){
					String strs[] =  line.split(" ");
					int length = strs.length;
					for (int i=0;i<length;i++){
						String str = strs[i];
						if (str.startsWith("time")){
							zh_sj = str.split("=")[1];
							break;
						}
					}
					try {
						zh_ms = getTotalMS(zh_sj);
					}catch (Exception e){
						e.printStackTrace();
					}
					if (0 !=total_ms && 0!=zh_ms){
							String per = NumUtil.getPercent(zh_ms,total_ms);
							Progress c_status = ProgressPool.getInstance().get(this.videoId);
							c_status.setConv_percent(per);
					}
				}
            }
			try {
				//waitFor方法会阻塞当前线程，返回值0表示正常终止。
				int exitVal = proc.waitFor();
				System.out.println("Process exitValue: " + exitVal);
				Progress c_status = ProgressPool.getInstance().get(this.videoId);
				c_status.setConv_percent("100%");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				br.close();
				isr.close();
				stderr.close();
			}
	    	} catch (IOException e) {
	    		e.printStackTrace();
	            return RESOURCE_BUNDLE.getString("CONVERT_ERROR");
	    	}
    	 
    	 
        return RESOURCE_BUNDLE.getString("CONVERT_SUCCESS");
    }

    
	/**
	 * @Description:判断文件是否存在
	 * @param path 文件路径
	 * @return boolean
	 * @author 张运宇 2014-11-4上午11:28:05
	 */
	private static boolean checkFile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	} 
	
	/**
	 * @Description:判断文件夹是否存在
	 * @param path 文件夹路径
	 * @return boolean
	 * @author 张运宇 2014-11-4上午11:28:05
	 */
	private static boolean checkFolder(String path) {
		if(path!=null&&path.length()>0){
			if("0".equals(RESOURCE_BUNDLE.getString("OS_TYPE"))&&path.contains("\\")){
				path=path.substring(0,path.lastIndexOf("\\"));
			}else{
				path=path.substring(0,path.lastIndexOf("/"));
			}
			File file = new File(path);
			if (file .isDirectory()){
				return true;
			}
		}
		return false;
	} 

    /**
      * @Description:判断文件格式是否支持
      * @param path    
      * @return boolean
      * @author 张运宇 2014-11-4下午4:50:05
     */
	private static boolean checkContentType(String path) {
		String type = path.substring(path.lastIndexOf(".") + 1, path.length()).toLowerCase(); 
		if(AVConvert.SUPPORTED_FORMATS.contains(","+type+",")){
			return true;
		}else{
			return false;
		}
	}
	
	/*public void saveAndConvert(String path,MultipartFile mFile) throws Exception  {
		//得到文件名    
        String filename=mFile.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf("."));
        String localname=System.currentTimeMillis()+randomNumber(3)+ext;
		SaveFileFromInputStream(mFile.getInputStream(),path,localname);
        if(!ext.equals(".flv") && !ext.equals(".jpg")){  //转换视频格式为flv
        	String infile=path+localname;
        	localname = localname.replace(ext,".flv");
        	ext=".flv";
            String outfile=path+localname;
            AVConvert convert = new AVConvert();
            convert.convert(infile, outfile);
        }
	}*/
	
	private void SaveFileFromInputStream(InputStream stream,String path,String filename) throws Exception {          
        FileOutputStream fs=new FileOutputStream( path + File.separator+ filename);    
        byte[] buffer =new byte[1024*1024];    
        int byteread = 0;     
        while ((byteread=stream.read(buffer))!=-1)    
        {    
           fs.write(buffer,0,byteread);    
           fs.flush();    
        }     
        fs.close();    
        stream.close();          
    }
	
	/**
	 * 获取随机数
	 * @author luping 2013-12-13  上午9:14:50
	 * @param l 随机数位数
	 * @return
	 */
	private String randomNumber(Integer l) {
		if(l==null || l<0){
			l=10;
		}
		String x = "0123456789";
		String id = "";
		for (int i = 0; i < l; i++) {
			id += x.charAt((int) (Math.ceil(Math.random() * 100000000) % x.length()));
		}
		return id;
	}

	/**
	 * 获取毫秒数
	 * @param timeStr 格式为hh:mm:ss.sss
	 * @return
	 * @throws Exception
	 */
	private int getTotalMS (String timeStr)throws  Exception{
		int total_ms =0;
		if (!timeStr.isEmpty()) {
			int index = timeStr.lastIndexOf(".");
			String millisecond_str = ""; //毫秒
			String second_str = "";//m秒
			String minute_str = "";//分
			String hour_str = "";//时
			if (-1 != index) {
				millisecond_str = timeStr.substring(index + 1, timeStr.length());
			}
			String[] hms = timeStr.substring(0, index).split(":");
			second_str = hms[2];
			minute_str = hms[1];
			hour_str = hms[0];
			int millisecond = 0; //毫秒
			int second = 0;//m秒
			int minute = 0;//分
			int hour = 0;//时
			if (!millisecond_str.isEmpty()) {
				millisecond = Integer.parseInt(millisecond_str);
			}
			if (!second_str.isEmpty()) {
				second = Integer.parseInt(second_str);
			}
			if (!minute_str.isEmpty()) {
				minute = Integer.parseInt(minute_str);
			}
			if (!hour_str.isEmpty()) {
				hour = Integer.parseInt(hour_str);
			}
			total_ms = millisecond + second * 1000 + minute * 60 * 1000 + hour * 60 * 60 * 1000; //总的毫秒数

		}
		return total_ms;
	}

	public void getVideoImg(String width,String height,String videoPath,String imgSavePath){

		String commend=RESOURCE_BUNDLE.getString("FFMPEG_PATH");
		//ffmpeg -i test.asf -y -f image2 -t 0.001 -s 352x240 a.jpg
		//ffmpeg -i test2.asf -y -f image2 -ss 08.010 -t 0.001 -s 352x240 b.jpg
		//取视频中第十秒的截图
		commend = commend+" -i "+videoPath+" -y -f image2 -ss 10 -t 0.001 -s "+width+"x"+height+" "+imgSavePath;
		try {
			//执行命令行创建进程(启动程序ffmpeg.exe)
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(commend);
			//及时读取标准错误流缓冲区的数据，避免造成死锁
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {

			}
			proc.waitFor();
		}catch (Exception e){
			e.printStackTrace();
		}

	}

}