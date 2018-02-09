package com.xjj.cms.base.util.progress;

/**
 * CustomMultipartResolver.java
 * Created on yeyunfeng 2015-12-28 19:03
 * V1.0.0 yeyunfeng 2015-12-28 19:03 初始版
 * 修改记录：修改内容 作者 修改时间
 * Copyright (c) 2015 海南新境界软件有限公司 版权所有
 */

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CustomMultipartResolver extends CommonsMultipartResolver {

    private HttpServletRequest request;
    private boolean progress = false;

    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        upload.setSizeMax(-1);
        return upload;
    }

    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        // 获取到request,要用到session
        this.request = request;
        return super.resolveMultipart(request);
    }

    @Override
    public MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        FileUploadProgressListener progressListener = new FileUploadProgressListener(request);
        fileUpload.setProgressListener(progressListener);
        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        } catch (FileUploadException ex) {
            throw new MultipartException("Could not parse multipart servlet request", ex);
        }catch (Exception e){
            e.printStackTrace();
            throw new MultipartException("Could not parse multipart servlet request", e);
        }
    }
}

