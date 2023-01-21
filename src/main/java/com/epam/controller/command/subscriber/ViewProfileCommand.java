package com.epam.controller.command.subscriber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.User;

public class ViewProfileCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ViewProfileCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			if ((User) req.getAttribute("currentUser") == null) {
				req.setAttribute("currentUser", req.getSession().getAttribute("loggedUser"));
			}
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while loading user info.");
			LOG.error("Unable to load user info due to unexpected error.", e);
		}
		return Page.PROFILE_PAGE;
	}

}
