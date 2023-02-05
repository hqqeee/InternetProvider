package com.epam.controller.command.admin;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.CommandNames;
import com.epam.controller.command.Page;
import com.epam.exception.services.UserServiceException;
import com.epam.util.AppContext;

/**
 * 
 * The class implements the Command interface. It's used for removing the user
 * from the system by administrator.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class RemoveUserCommand implements Command {
	/*
	 * A Logger instance to log error and info messages.
	 */
	private static final Logger LOG = LogManager.getLogger(RemoveUserCommand.class);

	/**
	 * 
	 * This method retrieves userId parameter from request, remove the user with
	 * this userId from the system using UserService method and then redirects
	 * to the administrator menu with "remove_user" success message.
	 * If an error occurs during the removing user process, the error message will
	 * be set to the request as an attribute "errorMessages", and then redirects to
	 * the administrator menu.
	 * 
	 * @param req  HttpServletRequest request from the client to get
	 *             parameters to work with.
	 * @param resp HttpServletResponse response to client with parameters to
	 *             work with.
	 * @return the result of executing a command as Page.
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int userId = Integer.parseInt(req.getParameter("userId"));
			AppContext.getInstance().getUserService().removeUser(userId);
			LOG.info("Tariff with id " + userId + " successfully removed.");
			resp.sendRedirect(
					req.getContextPath() + "/controller?action=" + CommandNames.ADMIN_MENU + "&success=remove_user");
			return Page.REDIRECTED;
		} catch (UserServiceException e) {
			LOG.warn("A service error occurred while removing user.");
			LOG.error("Unable to remove user due to service error.", e);
		} catch (Exception e) {
			LOG.warn("An error occurred while removing user.");
			LOG.error("Unable to remove user due to unexpected error.", e);
		}
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"))
				.getString("error.unable_to_remove_user"));
		return new AdminMenuCommand().execute(req, resp);
	}

}
