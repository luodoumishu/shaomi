package com.xjj._extensions.attachment.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xjj._extensions.attachment.model.CommonDocumentModel;
import com.xjj._extensions.attachment.model.UploadInitialFile;
import com.xjj._extensions.attachment.service.ICommonDocumentModelService;
import com.xjj._extensions.attachment.util.ReConf;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.web.filter.WebContext;

@Controller
@RequestMapping(value = "/{module}/{dir}/common/attachment")
public class AttachmentController{

	@Autowired
    @Qualifier("CommonDocumentModelService")
    private ICommonDocumentModelService commonDocumentModelService;
	
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	
	@RequestMapping(value = "/save")
    public @ResponseBody String save(@PathVariable("module") String module,@PathVariable("dir") String dir,@RequestParam("initialFiles") List<MultipartFile> initialFiles, 
    									javax.servlet.http.HttpServletRequest request)  throws Exception {
		String dataId = request.getParameter("dataId");
		
		if (dataId == null || dataId.length()<=0) {    
			return "error1";
			//throw new Exception("上传失败：dataId为空");      
        }
		
		if (module == null || module.length()<=0) {    
			return "error2";
			//throw new Exception("上传失败：模块名为空");      
        }
		
		if (dir == null || dir.length()<=0) {    
			return "error3";
			//throw new Exception("上传失败：文件名为空");      
        }
		
		//获取文件需要上传到的路径
		String projectPath = request.getSession().getServletContext()
				.getRealPath("/");
		projectPath = projectPath.substring(0,projectPath.length()-1);
		String path =projectPath.substring(0,projectPath.length()-6);
		path = path +"uploadDir/";
		File file = new File(path);

		if (!file.exists()) {
			file.mkdir();
		}
		
		path = path + module+"/";
		file = new File(path);

		if (!file.exists()) {
			file.mkdir();
		}
		
		String _modelSize = ReConf.getString(module+"_"+dir);
		
		Integer modelSize = 10000000;
		
        if(StringUtils.isNotEmpty(_modelSize))
            modelSize = Integer.parseInt(_modelSize);
		
        dir = dir.replace(".", "/");
        
		path = path + dir+"/";
		
		file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}
		for(MultipartFile mFile:initialFiles){
			
			if (mFile == null) {    
				return "error4";     
            }
			
			
            if(mFile.getSize()>modelSize)
            {    
            	return "error"+modelSize;
                //throw new Exception("上传失败：文件大小不能超过"+modelSize+"字节");      
            }    
            //得到文件名    
            String filename=mFile.getOriginalFilename();
            String ext = filename.substring(filename.lastIndexOf("."));
            String localname=System.currentTimeMillis()+ext;
                
            if(mFile.getSize()>0){                   
                SaveFileFromInputStream(mFile.getInputStream(),path,localname);
                CommonDocumentModel documentModel = new CommonDocumentModel();
                documentModel.setCreateTime(new Date());
                documentModel.setDataId(dataId);
                documentModel.setFilesize(mFile.getSize()+"");
                documentModel.setFilename(filename);
                
                String name = filename;
                for(int i = 1; true; i++){
                	List<CommonDocumentModel> list = commonDocumentModelService.query(-1, -1, documentModel);
                	if(list!=null && list.size()>0){
                		filename = "("+i+")"+name;
                		documentModel.setFilename(filename);
                	}else{
                		break;
                	}
                }
                documentModel.setFilepath("/"+module+"/"+dir+"/"+localname);
                documentModel.setLocalname(localname);
                documentModel.setExt(ext);
                documentModel.setUserId(WebContext.getInstance().getHandle().getUserId());
                commonDocumentModelService.save(documentModel);
            }    
            else{    
            	return "error5";
            }
		}
        return "";
    }
    
	public void SaveFileFromInputStream(InputStream stream,String path,String filename) throws IOException {          
        FileOutputStream fs=new FileOutputStream( path + "/"+ filename);    
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
	
    @RequestMapping(value = "/remove")
    public @ResponseBody String remove(@PathVariable("module") String module,@PathVariable("dir") String dir,@RequestParam String[] fileNames,HttpServletRequest request) throws Exception {
    	String dataId = request.getParameter("dataId");
    	
    	if (dataId == null || dataId.length()<=0) {    
            throw new Exception("删除失败：dataId为空");      
        }
    	
    	if (module == null || module.length()<=0) {    
            throw new Exception("删除失败：模块名为空");      
        }
		
		if (dir == null || dir.length()<=0) {    
            throw new Exception("删除失败：模块名为空");      
        }
    	
    	String projectPath = request.getSession().getServletContext()
				.getRealPath("/");
		projectPath = projectPath.substring(0,projectPath.length()-1);
		dir = dir.replace(".", "/");
		String downLoadPath=projectPath.substring(0,projectPath.length()-6)+ "uploadDir/"+module+"/"+dir+"/";
		if(fileNames!=null && fileNames.length>0){
			for(String aFileName:fileNames){
				CommonDocumentModel commonDocumentModel = new CommonDocumentModel();
				commonDocumentModel.setDataId(dataId);
				commonDocumentModel.setFilename(aFileName);
				List<CommonDocumentModel> documentModels=commonDocumentModelService.query(-1, -1, commonDocumentModel);
				if(documentModels!=null && documentModels.size()>0){
					File file = new File(downLoadPath, documentModels.get(0).getLocalname());
					if (!file.isDirectory()) {  
						file.delete();  
					} else {
						
					}  
					commonDocumentModelService.deleteByPK(documentModels.get(0).getId());
				}
			}
		}
        return "";
    }

	@RequestMapping(value = "/init")
    public ModelAndView init(@PathVariable("module") String module,@PathVariable("dir") String dir,HttpServletRequest request) throws Exception{
		String dataId = request.getParameter("dataId");
		
		if (dataId == null || dataId.length()<=0) {    
            throw new Exception("文件初始化失败：dataId为空");      
        }
    	
    	if (module == null || module.length()<=0) {    
            throw new Exception("文件初始化失败：模块名为空");      
        }
		
		if (dir == null || dir.length()<=0) {    
            throw new Exception("文件初始化失败：模块名为空");      
        }
		
		List<UploadInitialFile> list = new ArrayList<UploadInitialFile>();
		
		if(dataId!=null && dataId.length()>0){
			CommonDocumentModel commonDocumentModel = new CommonDocumentModel();
			
			commonDocumentModel.setDataId(dataId);
			
			List<CommonDocumentModel> documentModels=commonDocumentModelService.query(-1, -1, commonDocumentModel);
			
			if(documentModels!=null && documentModels.size()>0){
				for(CommonDocumentModel documentModel:documentModels){
					UploadInitialFile ui =  new UploadInitialFile(dataId, documentModel.getFilename(),0,documentModel.getExt(),"true",documentModel.getLocalname(),documentModel.getCreateTime(),documentModel.getZuser()!=null?documentModel.getZuser().getName():"");
					list.add(ui);
				}
			}
		}
		return new ModelAndView().addObject(list);
    }
	
	@RequestMapping(value = "/download")
    public void download(@PathVariable("module") String module,@PathVariable("dir") String dir,HttpServletRequest request, javax.servlet.http.HttpServletResponse response)throws Exception {
		String localname = request.getParameter("localname");
		String dataId = request.getParameter("dataId");
		String filename=request.getParameter("name");
		filename=new String(filename.getBytes("ISO-8859-1"));
		
		if (dataId == null || dataId.length()<=0) {    
            throw new Exception("下载失败：dataId为空");      
        }
    	
    	if (module == null || module.length()<=0) {    
            throw new Exception("下载失败：模块名为空");      
        }
		
		if (dir == null || dir.length()<=0) {    
            throw new Exception("下载失败：模块名为空");      
        }
		
		if(localname==null || localname.length()<=0){
			throw new Exception("下载失败：文件名为空");
		}

		CommonDocumentModel commonDocumentModel = new CommonDocumentModel();
		
		commonDocumentModel.setDataId(dataId);
		
		commonDocumentModel.setLocalname(localname);
		
		commonDocumentModel.setFilename(filename);
		
		List<CommonDocumentModel> documentModels=commonDocumentModelService.query(-1, -1, commonDocumentModel);
		
		if (documentModels==null || documentModels.size()<=0) {
			response.setContentType("text/html;charset=GBK");
			response.getWriter().print("指定文件不存在！");
		    return ;
	    }
		
		String projectPath = request.getSession().getServletContext()
				.getRealPath("/");
		
		projectPath = projectPath.substring(0,projectPath.length()-1);
		
		dir = dir.replace(".", "/");
		
		String downLoadPath = projectPath.substring(0,projectPath.length()-6)+ "uploadDir/"+module+"/"+dir+"/";
		
		downLoadPath += documentModels.get(0).getLocalname();
		// 服务器绝对路径
		//path = getServletContext().getRealPath( " / " ) + path;
		
	    File del = new File(downLoadPath);
		if (!del.exists()) {
			response.setContentType("text/html;charset=GBK");
			response.getWriter().print("指定文件不存在！");
		    return ;
	    }
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		long fileLength = new File(downLoadPath).length();

		response.setContentType("application/octet-stream");
		
		String realName = documentModels.get(0).getFilename();
		realName = new String(realName.getBytes("GB2312"), "ISO_8859_1");
		
		response.setHeader("Content-disposition", "attachment; filename="+
				realName);
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
		return;
    }
}
