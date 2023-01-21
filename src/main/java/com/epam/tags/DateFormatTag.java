package com.epam.tags;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class DateFormatTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String locale;
	private Date date;

	public int doStartTag() throws JspException {
		System.out.println("Called");
		JspWriter writer = pageContext.getOut();
		try {
			if (locale.equals("uk")) {
				ResourceBundle rb =  ResourceBundle.getBundle("lang",  new Locale("uk"));
				writer.print(date.getDay() + " " + rb.getString("month.number" + date.getMonth()) 
				+ " " + (date.getYear()+1900) + " " + date.getHours() + ":" + date.getMinutes());
			} else {
				ResourceBundle rb =  ResourceBundle.getBundle("lang",  new Locale("en"));
				writer.print(rb.getString("month.number" + date.getMonth()) + " " + date.getDay() +
						" " + (date.getYear()-100) + "' " + date.getHours() + ":" + date.getMinutes());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}


	public String getLocale() {
		return locale;
	}


	public void setLocale(String locale) {
		this.locale = locale;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
