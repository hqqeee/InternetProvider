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

public class RegisterUserCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(RegisterUserCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		UserForm userForm = new UserForm();
		userForm.setFirstName(req.getParameter("firstName"));
		userForm.setLastName(req.getParameter("lastName"));
		userForm.setLogin(req.getParameter("login"));
		userForm.setEmail(req.getParameter("email"));
		userForm.setCity(req.getParameter("city"));
		userForm.setAddress(req.getParameter("address"));
		try {
			Validator.validateUserForm(userForm, ResourceBundle.getBundle("lang", (Locale)req.getAttribute("locale")));
			AppContext.getInstance().getUserService().registerUser(userForm);
			LOG.info("User: [" + userForm.getFirstName() + "," + userForm.getLastName() + "," + userForm.getLogin() + ","
					+ userForm.getEmail() + ", " + userForm.getCity() + "]" + " registered successfully.");
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.OPEN_USER_REGISTRATION + "&success=register_user");
			return Page.REDIRECTED;
		} catch (ValidationErrorException e) {
			req.setAttribute("userForm", userForm);
			req.setAttribute("errorMessages", e.getErrors());
		} catch (UserAlreadyExistException e) {
			req.setAttribute("userForm", userForm);
			req.setAttribute("userAlreadyExists", e.getMessage());
		} catch (UserServiceException e) {
			LOG.warn("A service error occurred while registering user.");
			LOG.error("Unable to register user due to service error.", e);
			req.setAttribute("errorMessages", "Something went wrong. Try again later.");
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while registering user.");
			LOG.error("Unable to register user due to unexpected error.", e);
			req.setAttribute("errorMessages", "Something went wrong. Try again.");
		}
		return Page.USER_REGISTRATION_PAGE;
	}

}
