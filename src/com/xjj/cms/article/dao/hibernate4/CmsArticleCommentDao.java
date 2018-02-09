package com.xjj.cms.article.dao.hibernate4;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xjj.cms.article.dao.ICmsArticleCommentDao;
import com.xjj.cms.article.model.CmsArticleComment;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("cmsArticleCommentDao")
public class CmsArticleCommentDao extends BaseHibernateDao<CmsArticleComment, String> implements
		ICmsArticleCommentDao{

	@Override
	public List<CmsArticleComment> list4articleId(String articleId) {
		String hql = "from CmsArticleComment where article_id = '" + articleId + "' order by comment_time desc";
		return list(hql);
	}
}
