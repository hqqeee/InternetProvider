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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The DateFormatTag class is a custom tag class in JavaServer Pages (JSP)
 * technology that formats a date to be displayed in either English or Ukrainian
 * locale.
 * 
 * The class extends TagSupport and implements the doStartTag method. The method
 * retrieves the JspWriter from the page context and creates a Calendar instance
 * for the time zone "Europe/Kiev". The method then sets the time of the
 * Calendar instance to the given date, retrieves the appropriate resource
 * bundle based on the locale and writes the formatted date to the JspWriter.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class DateFormatTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String locale;
	private Date date;

	/**
	 * Logger instance to log the events occurred.
	 */
	private static final Logger LOG = LogManager.getLogger(DateFormatTag.class);
	
	/**
	 * This method retrieves the JspWriter from the page context, creates a Calendar
	 * instance for the time zone "Europe/Kiev", sets the time of the Calendar
	 * instance to the given date, retrieves the appropriate resource bundle based
	 * on the locale and writes the formatted date to the JspWriter.
	 * 
	 * @return SKIP_BODY - a constant indicating that the body of the tag should be
	 *         skipped
	 * @throws JspException - thrown if an error occurs while writing to the
	 *                      JspWriter
	 */
	@Override
	public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Kiev"));
		cal.setTime(date);
		try {
			if (locale.equals("uk")) {
				ResourceBundle rb = ResourceBundle.getBundle("lang", new Locale("uk"));
				writer.print(cal.get(Calendar.DAY_OF_MONTH) + " "
						+ rb.getString("month.number" + cal.get(Calendar.MONTH)) + " " + cal.get(Calendar.YEAR) + " "
						+ cal.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", cal.get(Calendar.MINUTE)));
			} else {
				ResourceBundle rb = ResourceBundle.getBundle("lang", new Locale("en"));
				writer.print(rb.getString("month.number" + cal.get(Calendar.MONTH)) + " "
						+ cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.YEAR) + "' " + cal.get(Calendar.HOUR)
						+ ":" + String.format("%02d", cal.get(Calendar.MINUTE)) + (cal.get(Calendar.AM_PM) == 1 ? "PM" : "AM"));
			}
		} catch (IOException e) {
			LOG.error("Cannot display locale date.");
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
