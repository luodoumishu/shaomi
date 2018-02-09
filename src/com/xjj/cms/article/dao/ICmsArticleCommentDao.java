package com.xjj.cms.article.dao;

import java.util.List;

import com.xjj.cms.article.model.CmsArticleComment;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsArticleCommentDao extends IBaseDao<CmsArticleComment, String>{

	List<CmsArticleComment> list4articleId(String articleId);
  
    
}
