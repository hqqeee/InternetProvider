package com.epam.controller.command.common;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;

/**
 * The ResetPasswordCommand class implements the Command interface and is used
 * to reset password for the specified email.
 * 
 * The ResetPasswordCommand class makes use of UserService to reset the password
 * for the specified email.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class ResetPasswordCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(ResetPasswordCommand.class);

	/**
	 * The execute method first retrieves the email from the request parameters. If
	 * the email is not null or blank, it makes a call to the resetPassword method
	 * of UserService with the email as the argument. If the password is
	 * reset successfully, the user is redirected to the home page with a success
	 * message.
	 * 
	 * If an error occurs while resetting the password, an error message is set in
	 * the request and the user is redirected to the home page.
	 * 
	 * @param req  the HttpServletRequest object
	 * @param resp the HttpServletResponse object
	 * @return the string representation of the next page
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		UserService userService = AppContext.getInstance().getUserService();
		String email = req.getParameter("resetEmail");
		if (email != null && !email.isBlank()) {
			try {
				userService.resetPassword(email);
				resp.sendRedirect(req.getContextPath() + Page.HOME_PAGE + "?success=reset_password");
				return Page.REDIRECTED;
			} catch (UserServiceException e) {
				LOG.warn("A service error occurred while reseting password.");
				LOG.error("Unable to reset password due to service error.", e);
			} catch (Exception e) {
				LOG.warn("An unexpected error occurred while reseting password.");
				LOG.error("Unable to reset password due to unexpected error.", e);
			}
		}
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"))
				.getString("error.unable_to_reset_password"));
		return Page.HOME_PAGE;
	}

}