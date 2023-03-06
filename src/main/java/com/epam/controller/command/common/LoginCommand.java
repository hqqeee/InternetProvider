package com.epam.controller.command.common;

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
import com.epam.util.AppContext;

/**
 * 
 * The LoginCommand class implements the Command interface and is used for user
 * authentication in the system. If the provided login and password are not
 * empty, it logs the user into the system by calling the login method on the
 * UserService. The locale of the user is also retrieved from the request
 * attribute and used to access the language specific messages from the resource
 * bundle. If the login and password fields are empty or if any exception occurs
 * during the login process, the corresponding error message is set as an
 * attribute in the request and the user is redirected to the home page.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class LoginCommand implements Command {
	/*
	 * A Logger instance to log error and info messages.
	 */
	private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

	/**
	 * 
	 * This method is used to execute the login command and handle the
	 * authentication of a user.
	 * 
	 * @param req  HttpServletRequest object to get request parameters
	 * @param resp HttpServletResponse object to redirect the page
	 * @return Page.HOME_PAGE if login fails or exception occurs while logging in.
	 * @return Page.REDIRECTED if the user is successfully logged in.
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		ResourceBundle bundle = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		if (login == null || password == null || login.trim().isEmpty() || password.trim().isEmpty()) {
			req.setAttribute("incorrectLoginOrPassword", bundle.getString("login.empty_login_or_password_field"));
			return Page.HOME_PAGE;
		}
		try {
			req.getSession().setAttribute("loggedUser",
					AppContext.getInstance().getUserService().login(login, password));
			LOG.info("User with login {} successfully logged in.", login);
			resp.sendRedirect(req.getContextPath() + Page.HOME_PAGE + "?success=login");
			return Page.REDIRECTED;
		} catch (UserNotFoundException e) {
			LOG.warn("Unsuccessful attempt to access account with login {}.", login);
			req.setAttribute("login", req.getParameter("login"));
			req.setAttribute("incorrectLoginOrPassword", bundle.getString("login.incorrect_login_or_password"));
		} catch (UserServiceException e) {
			LOG.warn("An error occurred while logging into the system.");
			LOG.error("Unable to login due to service error.", e);
			req.setAttribute("errorMessages", bundle.getString("error.something_went_wrong"));
		} catch (Exception e) {
			LOG.warn("An error occurred while logging into the system.");
			LOG.error("Unable to login due to unexpected error.", e);
			req.setAttribute("errorMessages", bundle.getString("error.something_went_wrong"));
		}
		return Page.HOME_PAGE;
	}

}
