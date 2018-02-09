package com.xjj.cms.article.web.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xjj._extensions.web.controller.BaseController;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.article.model.CmsArticleComment;
import com.xjj.cms.article.service.ICmsArticleCommentService;
import com.xjj.cms.base.util.StringTools;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;

@Controller
@RequestMapping(value = "/cms/article/comment")
public class CmsArticleCommentController extends BaseController<CmsArticleComment> {

	@Autowired
	@Qualifier("cmsArticleCommentService")
	private ICmsArticleCommentService cmsArticleCommentService;

	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}

	@RequestMapping(value = "/saveComment", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult saveComment(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		/*if (StringTools.StringFilter(request,response)) {
			JsonResult jr = new JsonResult(2, "评论内容存在特殊字符！", null);
			return jr;
		}*/
		String username = request.getParameter("username");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String neirong = request.getParameter("neirong");
		String login_image_check = request.getParameter("login_image_check");
		String articleId = request.getParameter("articleId");
		String sys_rand_str = (String) request.getSession().getAttribute("SYS_RAND_STR");
		
		if(login_image_check.equals(sys_rand_str)){
			CmsArticleComment articleComment = new CmsArticleComment();
			articleComment.setArticle_id(articleId);
			articleComment.setComment_content(neirong);
			articleComment.setComment_email(email);
			articleComment.setComment_name(username);
			articleComment.setComment_phone(tel);
			articleComment.setComment_time(new Date());
			cmsArticleCommentService.saveOrUpdate(articleComment);
			JsonResult jr = new JsonResult(0, "评论成功！", null);
			return jr;
		}else{
			JsonResult jr = new JsonResult(1, "验证码错误！", null);
			return jr;
		}
	}
	
	@RequestMapping(value = "/list4articleId", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult list4articleId(HttpServletRequest request) throws Exception {
		String articleId = request.getParameter("articleId");
		Page<CmsArticleComment> list = cmsArticleCommentService.list4articleId(articleId);
		JsonResult jr = new JsonResult(0, "", list);
		return jr;
	}
}
