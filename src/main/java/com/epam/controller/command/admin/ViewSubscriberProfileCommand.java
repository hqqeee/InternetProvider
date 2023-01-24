package com.epam.controller.command.admin;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

public class ViewSubscriberProfileCommand implements Command{

	private static final Logger LOG = LogManager.getLogger(ViewSubscriberProfileCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			UserDTO user = AppContext.getInstance().getUserService()
					.getUserById(Integer.parseInt(req.getParameter("userId")));
			req.setAttribute("currentUser", user);
			req.setAttribute("userId", req.getParameter("userId"));
			return Page.PROFILE_PAGE;
		} catch (UserServiceException | UserNotFoundException e) {
			LOG.warn("A service error occurred while loading user info.");
			LOG.error("Unable to load user info due to service error.", e);
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while loading user info.");
			LOG.error("Unable to load user info due to unexpected error.", e);
		}
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale)req.getAttribute("locale")).getString("error.unable_to_load_user_info"));
		return new AdminMenuCommand().execute(req, resp);
	}

}
