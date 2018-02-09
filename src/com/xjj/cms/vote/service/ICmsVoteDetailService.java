package com.xjj.cms.vote.service;

import java.util.List;
import java.util.Map;

import com.xjj.cms.vote.model.CmsVoteDetail;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsVoteDetailService extends IBaseService<CmsVoteDetail, String>{

	Page<CmsVoteDetail> query(Map<String, Object> filtersMap);

	List<CmsVoteDetail> getValidDetailListByItemId(String itemId);

}
