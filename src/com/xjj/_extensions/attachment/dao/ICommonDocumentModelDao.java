package com.xjj._extensions.attachment.dao;

import java.util.List;

import com.xjj._extensions.attachment.model.CommonDocumentModel;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICommonDocumentModelDao extends IBaseDao<CommonDocumentModel,String>{
	public List<CommonDocumentModel> query(int start, int pageSize, CommonDocumentModel documentModel, String orderby);
}
