package com.epam.controller.command.subscriber;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.CommandNames;
import com.epam.controller.command.Page;
import com.epam.exception.services.PasswordNotMatchException;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;
import com.epam.util.Validator;

/**
 * 
 * The ChangePasswordCommand class implements the Command interface and is used
 * to change a subscriber's password. This class retrieves the current password
 * and new password from the request and validates the new password using the
 * Validator. If the new password is valid, it changes the password using the
 * UserService. If an error occurs during password change, it sets the error
 * message in the request.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public class ChangePasswordCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(ChangePasswordCommand.class);

	/**
	 * 
	 * Changes the password of a subscriber.
	 * 
	 * @param req  HttpServletRequest
	 * @param resp HttpServletResponse
	 * @return String a path to the profile page if password change is successful,
	 *         otherwise the error message is set in the request and the path to the
	 *         profile page is returned.
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		UserDTO user = (UserDTO) req.getSession().getAttribute("loggedUser");
		String currentPassword = req.getParameter("currentPassword");
		String newPassword = req.getParameter("newPassword");
		if (user == null || currentPassword == null || newPassword == null)
			req.setAttribute("errorMessages", "Something went wrong. Cannot change password. Try againg later.");
		else {
			ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
			try {
				Validator.validatePassword(newPassword,
						ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale")));
				AppContext.getInstance().getUserService().changePassword(user.getId(), currentPassword, newPassword);
				LOG.info("{} changed password.", user);
				resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.VIEW_PROFILE
						+ "&success=change_password");
				return Page.REDIRECTED;
			} catch (ValidationErrorException e) {
				req.setAttribute("incorrectChangePasswordFormError", e.getErrors());
			} catch (PasswordNotMatchException e) {
				req.setAttribute("incorrectChangePasswordFormError", rs.getString("error.corrent_password_incorrect"));
			} catch (UserServiceException | UserNotFoundException e) {
				LOG.warn("A service error occurred while changing user balance.");
				LOG.error("Unable to change user balance due to service error.", e);
				req.setAttribute("errorMessages", rs.getString("error.unable_to_change_password"));
			} catch (Exception e) {
				LOG.warn("An error occurred while changing user balance.");
				LOG.error("Unable to change user balance due to unexpected error.", e);
				req.setAttribute("errorMessages", rs.getString("error.unable_to_change_password"));
			}
		}
		return new ViewProfileCommand().execute(req, resp);
	}

}
