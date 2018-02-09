package com.xjj.cms.web.tld.channel;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.channel.service.ICmsChannelService;
import com.xjj.cms.link.model.CmsChlLink;
import com.xjj.cms.link.model.CmsLink;
import com.xjj.cms.link.service.ICmsChlLinkService;
import com.xjj.cms.link.service.ICmsLinkService;
import com.xjj.framework.core.util.SpringContextUtil;

public class ChannelChildTld extends BodyTagSupport {

	public int doEndTag() throws JspException {

		HttpServletRequest _request = (HttpServletRequest) pageContext
				.getRequest();
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(pageContext.getServletConfig()
						.getServletContext());
		ICmsChannelItemService cmsChannelItemService = (ICmsChannelItemService) SpringContextUtil
				.getInstance().getBean("CmsChannelItemService");
		ICmsLinkService cmsLinkService = (ICmsLinkService) SpringContextUtil
				.getInstance().getBean("cmsLinkService");
		ICmsChlLinkService cmsChlLinkService = (ICmsChlLinkService) SpringContextUtil
				.getInstance().getBean("cmsChlLinkService");
		ICmsChannelService cmsChannelService = (ICmsChannelService) SpringContextUtil
				.getInstance().getBean("CmsChannelService");
		// BaseDao baseDao = (BaseDao) context.getBean("baseDao");

		ChannelMainTld channelMainTld = (ChannelMainTld) TagSupport
				.findAncestorWithClass(this, ChannelMainTld.class);
		Integer pageSize = channelMainTld.getPageSize();
		if (pageSize == null) {
			pageSize = 9999;
		}
		Integer index = 1;
		CmsChannel cmsChl = channelMainTld.getChl();
		// 栏目类型0-栏目类型 1-超链接类型 2-投票类型
		if (cmsChl.getRelevanceType().equals("0")) {
			
			return this.SKIP_BODY;
		} else if (cmsChl.getRelevanceType().equals("1")) {
			List<CmsChannel> setChlItem = cmsChannelService
					.findByChildChlById(cmsChl.getId());
			if (setChlItem.isEmpty()) {
				return this.SKIP_BODY;
			} else {
				Iterator itr = setChlItem.iterator();
				while (itr.hasNext() && pageSize > 0) {
					CmsChannel ccitem = (CmsChannel) itr.next();
					BodyContent bc = getBodyContent();
					String body = bc.getString();
					body = StringHelper.replace(body, "$_child_chlCode",
							ccitem.getChanneCode());
					body = StringHelper.replace(body, "$_child_chlName",
							ccitem.getChanneName());
					body = StringHelper.replace(body, "$_child_page", index
							+ "");
					try {
						pageContext.getOut().print(body);
						index++;
					} catch (IOException e) {
					}
					if (pageSize != null) {
						pageSize--;
					}
				}

			}

		}else if(cmsChl.getRelevanceType().equals("2")){
			return this.SKIP_BODY;
		}
		return this.EVAL_PAGE;
	}
}
