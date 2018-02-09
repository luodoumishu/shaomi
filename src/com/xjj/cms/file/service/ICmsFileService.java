package com.xjj.cms.file.service;


import java.util.List;
import java.util.Map;

import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.file.model.CmsFile;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsFileService extends IBaseService<CmsFile, String>{

	Page<CmsFile> query(Map<String, Object> filtersMap);

	/**
	 * 保存文件信息和附件
	 * @param cmsFile
	 * @param affixs
	 *  下午3:28:40
	 */
	void saveFileAndAffix(CmsFile cmsFile, List<CmsAffix> affixs);

	List<CmsAffix> findAffixByModelId(String modelId);

	void del(String modelId);

	CmsAffix findAffixById(String affixId);

	Page<CmsAffix> getALLFilesByMenuIds(String[] listMenuId,
			Map<String, Object> map) throws Exception;

}
