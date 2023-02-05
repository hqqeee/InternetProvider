package com.epam.controller.command.common;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;

/**
 * ChangeLanguageCommand is a Command implementation class. It's responsible for
 * changing the language of the user's session, based on the selected language
 * in the request.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class ChangeLanguageCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(ChangeLanguageCommand.class);

	/**
	 * This method is the main method of the ChangeLanguageCommand class. It changes
	 * the language of the user's session based on the selected language in the
	 * request. If the language is successfully changed, the user is redirected back
	 * to the home page.
	 * 
	 * @param req  The HttpServletRequest object that is passed from the servlet
	 * @param resp The HttpServletResponse object that is passed from the servlet
	 * @return Returns a String that represents the page that the user should be
	 *         redirected to
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String lang = req.getParameter("lang");
		try {
			if (lang != null) {
				Cookie langCookie = new Cookie("lang", lang);
				resp.addCookie(langCookie);
			}
			resp.sendRedirect(req.getContextPath());
			return Page.REDIRECTED;
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while changing language.");
			LOG.error("Unable to change language due to unexpected error.", e);
		}
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"))
				.getString("error.unable_to_change_language"));
		return Page.HOME_PAGE;
	}

}
