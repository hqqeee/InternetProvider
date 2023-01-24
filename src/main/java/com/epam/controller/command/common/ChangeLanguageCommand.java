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

public class ChangeLanguageCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ChangeLanguageCommand.class);

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
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale)req.getAttribute("locale")).getString("error.unable_to_change_language"));
		return Page.HOME_PAGE;
	}

}
