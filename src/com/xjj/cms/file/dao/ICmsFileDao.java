package com.xjj.cms.file.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.file.model.CmsFile;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsFileDao  extends IBaseDao<CmsFile, String>{

	List<CmsFile> query(int skip, int pageSize, Map<String, Object> queryMap,
			String string);

	int total(Map<String, Object> queryMap);

	List<CmsFile> query(int skip, int pageSize, String string,
			Map<String, Object> queryMap) throws Exception;

	List<CmsAffix> affixQuery(int skip, int pageSize, String string,
			Map<String, Object> queryMap);

}
