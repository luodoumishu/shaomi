package com.xjj.oa.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class LoadingTag extends BodyTagSupport {

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		String loading = "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +"<img src=\"images/loading2.gif\" style=\"vertical-align: middle;\"/>&nbsp;正在加载...";
		try {
			out.print(loading);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
}
