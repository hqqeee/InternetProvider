package com.epam.tags;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

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
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Kiev"));
		cal.setTime(date);
		try {
			if (locale.equals("uk")) {
				ResourceBundle rb = ResourceBundle.getBundle("lang", new Locale("uk"));
				writer.print(cal.get(Calendar.DAY_OF_MONTH) + " "
						+ rb.getString("month.number" + cal.get(Calendar.MONTH)) + " " + cal.get(Calendar.YEAR) + " "
						+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
			} else {
				ResourceBundle rb = ResourceBundle.getBundle("lang", new Locale("en"));
				writer.print(rb.getString("month.number" + cal.get(Calendar.MONTH)) + " "
						+ cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.YEAR) + "' " + cal.get(Calendar.HOUR)
						+ ":" + cal.get(Calendar.MINUTE) + (cal.get(Calendar.AM_PM)==1?"PM":"AM"));
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
