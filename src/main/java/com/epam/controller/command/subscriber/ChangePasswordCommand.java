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

public class ChangePasswordCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ChangePasswordCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		UserDTO user = (UserDTO) req.getSession().getAttribute("loggedUser");
		String currentPassword = req.getParameter("currentPassword");
		String newPassword = req.getParameter("newPassword");
		if (user == null || currentPassword == null || newPassword == null)
			req.setAttribute("errorMessages", "Something went wrong. Cannot change password. Try againg later.");
		else {
			
			try {
				Validator.validatePassword(newPassword,
						ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale")));
				AppContext.getInstance().getUserService().changePassword(user.getId(), currentPassword, newPassword);
				LOG.info(user + " changed password.");
				resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.VIEW_PROFILE
						+ "&success=change_password");
				return Page.REDIRECTED;
			} catch (ValidationErrorException e) {
				req.setAttribute("incorrectChangePasswordFormError", e.getErrors());
			} catch (PasswordNotMatchException e) {
				req.setAttribute("incorrectChangePasswordFormError", "Current password is incorrect. Try again.");
			} catch (UserServiceException | UserNotFoundException e) {
				LOG.warn("A service error occurred while changing user balance.");
				LOG.error("Unable to change user balance due to service error.", e);
				req.setAttribute("errorMessages", "Something went wrong. Cannot change password. Try againg later.");
			} catch (Exception e) {
				LOG.warn("An error occurred while changing user balance.");
				LOG.error("Unable to change user balance due to unexpected error.", e);
				req.setAttribute("errorMessages", "Something went wrong. Cannot change password. Try againg later.");
			}
		}
		return new ViewProfileCommand().execute(req, resp);
	}

}
