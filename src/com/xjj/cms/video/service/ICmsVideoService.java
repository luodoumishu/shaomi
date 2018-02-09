package com.xjj.cms.video.service;

import com.xjj.cms.video.model.CmsVideo;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;
import org.hibernate.criterion.Criterion;

import java.util.List;
import java.util.Map;

public interface ICmsVideoService extends IBaseService<CmsVideo, String> {
	public List<CmsVideo> getByPage(List<Criterion> criterions, int from,
									int length);

	public CmsVideo save(CmsVideo model);

	public Page<CmsVideo> query(Map<String, Object> filtersMap)throws  Exception;

	public Page<CmsVideo> getALLVideosByMenuId(String id, Map<String, Object> map) throws Exception;

	public CmsVideo findCmsVideoById(String id);

	/**
	 * 根据id,设置状态
	 * @param id
	 * @param state
	 * @throws Exception
	 */
	public void saveStateByModelId(String id, String state)throws  Exception;
}
