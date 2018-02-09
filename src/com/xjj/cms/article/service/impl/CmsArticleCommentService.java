package com.xjj.cms.article.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.article.dao.ICmsArticleCommentDao;
import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleComment;
import com.xjj.cms.article.service.ICmsArticleCommentService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("cmsArticleCommentService")
public class CmsArticleCommentService extends BaseService<CmsArticleComment, String>
		implements ICmsArticleCommentService{
	@Autowired
	@Qualifier("cmsArticleCommentDao")
	private ICmsArticleCommentDao cmsArticleCommentDao;

	@Autowired
	@Qualifier("cmsArticleCommentDao")
	@Override
	public void setBaseDao(IBaseDao<CmsArticleComment, String> baseDao) {
		this.baseDao = cmsArticleCommentDao;
	}

	@Override
	public Page<CmsArticleComment> list4articleId(String articleId) {
		List<CmsArticleComment> items =  cmsArticleCommentDao.list4articleId(articleId);
		// 获取总记录数
		int total = cmsArticleCommentDao.list4articleId(articleId).size();
		// 构造返回对象page
		Page<CmsArticleComment> page = PageUtil.getPage(0, 10, items, total);
		return page;
	}

	
}
