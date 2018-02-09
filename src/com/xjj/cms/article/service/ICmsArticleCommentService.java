package com.xjj.cms.article.service;



import com.xjj.cms.article.model.CmsArticleComment;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsArticleCommentService extends IBaseService<CmsArticleComment, String>{

	Page<CmsArticleComment> list4articleId(String articleId);

}
