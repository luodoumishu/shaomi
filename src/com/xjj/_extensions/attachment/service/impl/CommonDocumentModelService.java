package com.xjj._extensions.attachment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.attachment.dao.ICommonDocumentModelDao;
import com.xjj._extensions.attachment.model.CommonDocumentModel;
import com.xjj._extensions.attachment.service.ICommonDocumentModelService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.service.impl.BaseService;

@Service("CommonDocumentModelService")
public class CommonDocumentModelService extends BaseService<CommonDocumentModel, String> implements ICommonDocumentModelService{
	@Autowired
    @Qualifier("CommonDocumentModelDao")
    private ICommonDocumentModelDao dao;

    @Autowired
    @Qualifier("CommonDocumentModelDao")
    @Override
	public void setBaseDao(IBaseDao<CommonDocumentModel, String> baseDao) {
		this.baseDao = dao;
	}

	@Override
	public List<CommonDocumentModel> query(int start, int pageSize,
			CommonDocumentModel commonDocumentModel) {
		return dao.query(start, pageSize, commonDocumentModel,null);
	}
    
    
    
}
