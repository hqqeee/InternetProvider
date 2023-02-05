package com.epam.controller.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;

/**
 * The LogoutCommand class implements the Command interface and is used to
 * logout from the system.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class LogoutCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

	/**
	 * The execute method is used to invalidate the current user's session and
	 * redirect the user to the home page.
	 * 
	 * @param req  HttpServletRequest object
	 * @param resp HttpServletResponse object
	 * @return a string indicating the next page to display
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		if (session != null) {
			try {
				session.invalidate();
				LOG.info("User successfully logged out.");
				resp.sendRedirect(req.getContextPath() + Page.HOME_PAGE + "?success=logout");
			} catch (Exception e) {
				LOG.warn("An error occurred while logging out.");
				LOG.error("Unable to logout due to unexpected error.", e);
			}
			return Page.REDIRECTED;
		}
		return Page.HOME_PAGE;
	}

}
