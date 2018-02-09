package com.xjj.cms.web.tld.channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.article.service.impl.CmsArticleService;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.channel.service.ICmsChannelService;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.SpringContextUtil;
import com.xjj.framework.core.web.filter.WebContext;

/**
 * 频道父标签，其子标签通常用来显示 父标签是获取频道的对象，传递给子标签
 * 
 * @author fengjk
 * @date 2014-9-3
 */
public class ChannelMainTld extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * 频道代码
	 */
	private String chlCode;
	/**
	 * 每页显示记录条数
	 */
	private Integer pageSize;
	// 记录当前频道，用于传值给子标签
	private CmsChannel chl;

	public int doStartTag() throws JspTagException {

		ICmsChannelItemService iCmsChannelItemService = (ICmsChannelItemService) SpringContextUtil
				.getInstance().getBean("CmsChannelItemService");
		ICmsChannelService iCmsChannelService = (ICmsChannelService) SpringContextUtil
				.getInstance().getBean("CmsChannelService");

		CmsChannel chl = null;
		if (StringUtils.isBlank(chlCode)) {
			String strChlId = pageContext.getRequest().getParameter("chlId");
			String strChlCode = pageContext.getRequest()
					.getParameter("chlCode");
			if (StringUtils.isBlank(strChlId)
					&& StringUtils.isBlank(strChlCode)) {
				return this.SKIP_BODY;
			} else {

				if (StringUtils.isNotBlank(strChlId)) {
					chl = (CmsChannel) iCmsChannelItemService.findByChannelId(
							strChlId).get(0);
				} else if (StringUtils.isNotBlank(strChlCode)) {
					chl = (CmsChannel) iCmsChannelService
							.findByChlCode(strChlCode);
				}
			}
		} else {
			chl = (CmsChannel) iCmsChannelService.findByChlCode(chlCode);
		}

		this.chl = chl;
		return this.EVAL_BODY_AGAIN;
	}

	public int doEndTag() throws JspTagException {

		BodyContent bc = getBodyContent();
		String body = bc.getString();
		String channel_name = chl.getChanneName();
		HttpServletRequest _request = (HttpServletRequest)pageContext.getRequest();
		String orgName = (String) _request.getAttribute("orgName");
		if(orgName.contains("党委") || orgName.contains("工委")){
			channel_name = channel_name.replace("镇", "单位");
		}
		body = StringHelper.replace(body, "$_chl_name", channel_name);
		body = StringHelper.replace(body, "$_chl_id", chl.getId().toString());

		try {
			pageContext.getOut().print(body);
		} catch (IOException e) {
		}
		this.chl = null;
		return this.EVAL_PAGE;
	}

	public String getChlCode() {
		return chlCode;
	}

	public void setChlCode(String chlCode) {
		this.chlCode = chlCode;
	}

	public CmsChannel getChl() {
		return chl;
	}

	public void setChl(CmsChannel chl) {
		this.chl = chl;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
