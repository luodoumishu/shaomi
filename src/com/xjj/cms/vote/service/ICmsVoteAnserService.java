package com.xjj.cms.vote.service;

import java.util.List;

import com.xjj.cms.vote.model.CmsVoteAnser;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsVoteAnserService extends IBaseService<CmsVoteAnser, String>{

	List<CmsVoteAnser> listAllByItemId(String itemId);

}
