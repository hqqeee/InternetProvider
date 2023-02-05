package com.epam.controller.command.subscriber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.User;

/**
 * ViewProfileCommand is a command class that implements the Command interface.
 * The class provides the execute method which sets the user profile to be
 * displayed.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class ViewProfileCommand implements Command {
	/**
	 * Logger instance to log the events occurred.
	 */
	private static final Logger LOG = LogManager.getLogger(ViewProfileCommand.class);

	/**
	 * The method retrieves the user profile to be displayed on the profile page. If
	 * the current user is not set, the user session data is retrieved and set as
	 * the current user.
	 * 
	 * @param req  HttpServletRequest object that is used to retrieve user session
	 *             data
	 * @param resp HttpServletResponse object that is not used in this method.
	 * @return the URL of the profile page.
	 **/
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
