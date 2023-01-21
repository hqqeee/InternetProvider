package com.epam.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class TextBodyTag extends TagSupport {

	private int iterations;
	private int counter;

	@Override
	public int doStartTag() throws JspException {
		counter = 0;
		JspWriter writer = pageContext.getOut();

		try {
			writer.print("<table border='1'><tbody>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter writer = pageContext.getOut();

		try {
			writer.print("</tbody></table>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}

	@Override
	public int doAfterBody() throws JspException {
		JspWriter writer = pageContext.getOut();

		try {
			writer.print("<tr><td>Row number #" + counter++ + "</tr></td>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(counter > iterations) return SKIP_BODY;
		return EVAL_BODY_AGAIN;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

}
