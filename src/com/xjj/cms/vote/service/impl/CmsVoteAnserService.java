package com.xjj.cms.vote.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.vote.dao.ICmsVoteAnserDao;
import com.xjj.cms.vote.model.CmsVoteAnser;
import com.xjj.cms.vote.service.ICmsVoteAnserService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.service.impl.BaseService;

@Service("cmsVoteAnserService")
public class CmsVoteAnserService extends BaseService<CmsVoteAnser, String> implements ICmsVoteAnserService {
	@Autowired
	@Qualifier("cmsVoteAnserDao")
	private ICmsVoteAnserDao cmsVoteAnserDao;

	@Autowired
	@Qualifier("cmsVoteAnserDao")
	@Override
	public void setBaseDao(IBaseDao<CmsVoteAnser, String> baseDao) {
		this.baseDao = cmsVoteAnserDao;
	}

	@Override
	public List<CmsVoteAnser> listAllByItemId(String itemId) {
		return cmsVoteAnserDao.listAllByItemId(itemId);
	}
}
