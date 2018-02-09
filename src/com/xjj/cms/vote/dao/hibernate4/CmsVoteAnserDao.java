package com.xjj.cms.vote.dao.hibernate4;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xjj.cms.vote.dao.ICmsVoteAnserDao;
import com.xjj.cms.vote.model.CmsVoteAnser;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("cmsVoteAnserDao")
public class CmsVoteAnserDao extends BaseHibernateDao<CmsVoteAnser, String> implements ICmsVoteAnserDao{

	@Override
	public List<CmsVoteAnser> listAllByItemId(String itemId) {
		String hql = "from CmsVoteAnser where item_id='"+itemId+"' and isDelete='0' order by sort asc";
		return list(hql);
	}

}
