package com.xjj._extensions.attachment.service;

import java.util.List;

import com.xjj._extensions.attachment.model.CommonDocumentModel;
import com.xjj.framework.core.service.IBaseService;

public interface ICommonDocumentModelService extends IBaseService<CommonDocumentModel, String>{
	
	/**
    *
    * @param start
    * @param pageSize
    * @param commonDocumentModel
    * @return
    */
   public List<CommonDocumentModel> query(int start, int pageSize, CommonDocumentModel commonDocumentModel);
}
