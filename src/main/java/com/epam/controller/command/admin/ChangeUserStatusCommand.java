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
 * The class ChangeUserStatusCommand implements the Command interface. This
 * class is responsible for changing the status of a user (blocking/unblocking).
 * 
 * The class uses the UserService to change the status of a user. If the status
 * change operation is successful, the class redirects to the admin menu page.
 * Otherwise, it returns an error message to the user and stays on the same
 * page. The class logs all errors, warnings, and success messages.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class ChangeUserStatusCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(ChangeUserStatusCommand.class);

	/**
	 * The method {@code execute(HttpServletRequest req, HttpServletResponse resp)}
	 * is used to change the status of a user.
	 * The method retrieves the parameters from the request, including the user's id
	 * and blocked status. Then, it calls the UserService to change the
	 * status of the user. If the operation is successful, the method redirects to
	 * the admin menu page. Otherwise, it returns an error message to the user and
	 * stays on the same page.
	 * The method logs all errors, warnings, and success messages.
	 * 
	 * @param req  the HttpServletRequest object
	 * @param resp the HttpServletResponse object
	 * @return The Page String that corresponds to the next page
	 * @throws Exception if there is an error while changing the user's status
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String blocked = req.getParameter("userBlocked");
		String userId = req.getParameter("userId");
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		try {
			AppContext.getInstance().getUserService().changeUserStatus(Boolean.parseBoolean(blocked),
					Integer.parseInt(userId));
			LOG.info("Status of the user with id {} successfully changed.", userId);
			resp.sendRedirect(
					req.getContextPath() + "/controller?action=" + CommandNames.ADMIN_MENU + "&success=status_changed");
			return Page.REDIRECTED;
		} catch (UserServiceException e) {
			LOG.warn("An error occurred while changing user status.");
			LOG.error("Unable to change user status due to service error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_change_status"));
		} catch (Exception e) {
			LOG.warn("An error occurred while changing user status.");
			LOG.error("Unable to change user status due to unexpected error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_change_status"));
		}

		return new AdminMenuCommand().execute(req, resp);
	}

}
