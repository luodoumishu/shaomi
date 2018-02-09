package com.xjj.cms.base.util.progress;

import com.xjj.cms.base.util.FileTools;
import com.xjj.cms.base.util.Random;
import com.xjj.jdk17.utils.DateUtil;
import com.xjj.jdk17.utils.FileUtil;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BackFileUploadUtil {

    String savePath = "";
    String tmpDir = savePath + File.separator + "temp";

    public BackFileUploadUtil(String savePath) throws Exception {
        this.savePath = savePath;
        tmpDir = savePath + File.separator + "temp";
        File df = new File(savePath);
        if (!df.exists()) {
            df.mkdirs();
        }
    }


    /**
     * 上传文件
     *
     * @param request
     * @param fileInput_name //form中的type=file对应的name名称
     * @throws Exception
     */
    public Map<String,Object> fileUpload(HttpServletRequest request, String fileInput_name) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("up",false);
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                List<MultipartFile> files = multipartRequest.getFiles(fileInput_name);
                for (MultipartFile mFile : files) {
                    // 得到文件名
                    String file_oldname = mFile.getOriginalFilename();
                   //后缀
                    String ext = file_oldname.substring(file_oldname.lastIndexOf(".")).toLowerCase();
                    // 新文件名
                    String file_newName = DateUtil.getDate2String(new Date(), "yyyyMMddHHmmssSSS") + Random.getStrRandom(3) + ext;
                    String filePath = savePath + File.separator + file_newName;  //保存附件的路径
//                    FileTools.uploadFile(mFile.getInputStream(), filePath);
                    File targetFile = new File(filePath);
                    mFile.transferTo(targetFile);
                    map.put("newName",file_newName);
                    map.put("savePath",savePath);
                    map.put("ext",ext);
                    map.put("oldName",file_oldname);
                    map.put("up","true");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  map;

    }
}

