package com.epam.controller.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;

public class LogoutCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

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
