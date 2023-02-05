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

/**
 * 
 * ViewSubscriberProfileCommand is a concrete implementation of Command
 * interface. It retrieves user information for the admin with the specified ID
 * and sets it as an attribute in the request. If any error occurs while
 * retrieving the user information, the error message will be set as an
 * attribute in the request.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class ViewSubscriberProfileCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(ViewSubscriberProfileCommand.class);

	/**
	 * The execute method retrieves user information for the user with the specified
	 * ID and sets it as an attribute in the request. If any error occurs while
	 * retrieving the user information, the error message will be set as an
	 * attribute in the request.
	 * 
	 * @param HttpServletRequest  req - the request object that is passed to the
	 *                            servlet
	 * @param HttpServletResponse resp - the response object that the servlet
	 *                            generates
	 * @return String - the name of the next page that the servlet should redirect
	 *         to
	 **/
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
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"))
				.getString("error.unable_to_load_user_info"));
		return new AdminMenuCommand().execute(req, resp);
	}

}
