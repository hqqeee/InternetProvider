package com.epam.tags;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ParamsTag extends TagSupport {

	private String a;
	private String b;
	
	
	@Override
	public int doStartTag() throws JspException {
JspWriter writer = pageContext.getOut();
		
		try {
			writer.print("Argument a: " + a + "<br/>");
			writer.print("Argument b: " + b + "<br/>");
			writer.print("<br/>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}


	public String getA() {
		return a;
	}


	public void setA(String a) {
		this.a = a;
	}


	public String getB() {
		return b;
	}


	public void setB(String b) {
		this.b = b;
	}
	
	
	
}
