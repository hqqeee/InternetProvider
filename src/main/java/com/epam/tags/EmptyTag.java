package com.epam.tags;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class EmptyTag extends TagSupport{
	@Override
	public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		
		try {
			writer.print(LocalDateTime.now());
			writer.print("<br/>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
