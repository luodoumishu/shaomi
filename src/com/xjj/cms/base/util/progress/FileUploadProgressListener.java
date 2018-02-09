package com.xjj.cms.base.util.progress;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 文件上传进度<监听器yeyunfeng 2015-12-15
 */
public class FileUploadProgressListener implements ProgressListener {

    private HttpServletRequest request;

    public FileUploadProgressListener() {
    }

    public FileUploadProgressListener(HttpServletRequest request) {
        this.request = request;
        HttpSession session = request.getSession();
        /*if (null != session.getAttribute("modelId")){
			String modelId = session.getAttribute("modelId").toString();
			status.setModelId(modelId);
			ProgressPool.getInstance().add(modelId,status);
		}*/
        String remoteAddr = request.getRemoteAddr();
        if (null != ProgressPool.getInstance().get(remoteAddr)){
            ProgressPool.getInstance().remove(remoteAddr);
        }
        Progress status = new Progress();
        status.setActionName("正在处理文件上传");
        ProgressPool.getInstance().add(remoteAddr, status);
        //session.setAttribute("upload_ps", status);
    }

    /**
     * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
     */
    @Transactional
    public void update(long pBytesRead, long pContentLength, int pItems){
        HttpSession session = request.getSession();
        String remoteAddr = request.getRemoteAddr();
        Progress status = ProgressPool.getInstance().get(remoteAddr);
        if (pContentLength == pBytesRead) { //读取完成
            status.setPercent("100%");
        } else { //读取中
            status.setPercent(status.getPercent());
        }
        status.setBytesRead(pBytesRead);
        status.setContentLength(pContentLength);
        status.setItems(pItems);
    }
}