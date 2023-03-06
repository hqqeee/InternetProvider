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
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.dto.UserForm;
import com.epam.util.AppContext;
import com.epam.util.Validator;

/**
 * 
 * RegisterUserCommand is a concrete implementation of the Command interface
 * that is responsible for handling user registration request. This class
 * implements the execute(HttpServletRequest, HttpServletResponse) method which
 * retrieves user input from an HTTP request and register a user by calling the
 * appropriate method of UserService. If the registration is successful, the
 * user will be redirected to the user registration page with a success message.
 * In case of any validation errors, the error messages will be sent back to the
 * user registration page.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class RegisterUserCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(RegisterUserCommand.class);
	/**
	 * Executes the user registration process. This method retrieves user input from
	 * an HTTP request and performs validation on the input. If the input is valid,
	 * the method will call the appropriate method of UserService to register a new user. If the
	 * registration is successful, the user will be redirected to the user
	 * registration page with a success message. In case of any validation errors,
	 * the error messages will be sent back to the user registration page.
	 * 
	 * @param req  an HttpServletRequest object that contains the request
	 *             the client has made of the servlet
	 * @param resp an HttpServletResponse object that contains the response
	 *             the servlet sends to the client
	 * @return a String representing the forwarding JSP page name.
	 * @throws Exception if any unexpected error occurs while registering a user.
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		UserForm userForm = new UserForm();
		userForm.setFirstName(req.getParameter("firstName"));
		userForm.setLastName(req.getParameter("lastName"));
		userForm.setLogin(req.getParameter("login"));
		userForm.setEmail(req.getParameter("email"));
		userForm.setCity(req.getParameter("city"));
		userForm.setAddress(req.getParameter("address"));
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		try {
			Validator.validateUserForm(userForm, ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale")));
			AppContext.getInstance().getUserService().registerUser(userForm);
			LOG.info("User: [{},{},{},{}, {}] registered successfully.",userForm.getFirstName(),
					userForm.getLastName(),userForm.getLogin(), userForm.getEmail(),userForm.getCity());
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.OPEN_USER_REGISTRATION
					+ "&success=register_user");
			return Page.REDIRECTED;
		} catch (ValidationErrorException e) {
			req.setAttribute("userForm", userForm);
			req.setAttribute("errorMessages", e.getErrors());
		} catch (UserAlreadyExistException e) {
			req.setAttribute("userForm", userForm);
			req.setAttribute("userAlreadyExists", rs.getString("error.user_exists_1") + " " + e.getField() + " "
					+ rs.getString("error.user_exists_2"));
		} catch (UserServiceException e) {
			LOG.warn("A service error occurred while registering user.");
			LOG.error("Unable to register user due to service error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_register_user"));
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while registering user.");
			LOG.error("Unable to register user due to unexpected error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_register_user"));
		}
		return Page.USER_REGISTRATION_PAGE;
	}

}
