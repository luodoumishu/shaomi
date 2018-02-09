package com.xjj.cms.vote.dao;

import java.util.List;

import com.xjj.cms.vote.model.CmsVoteAnser;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsVoteAnserDao extends IBaseDao<CmsVoteAnser, String>{

	List<CmsVoteAnser> listAllByItemId(String itemId);

}
