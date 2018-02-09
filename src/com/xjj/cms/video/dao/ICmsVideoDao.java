package com.xjj.cms.video.dao;

import com.xjj.cms.video.model.CmsVideo;
import com.xjj.framework.core.dao.IBaseDao;

import java.util.List;
import java.util.Map;


public interface ICmsVideoDao extends IBaseDao<CmsVideo,String> {

	List<CmsVideo> query(int skip, int pageSize,
						 Map<String, Object> queryMap, String string)throws  Exception;

	int total(Map<String, Object> queryMap)throws  Exception;

	CmsVideo findCmsVideoById(String id);

}
