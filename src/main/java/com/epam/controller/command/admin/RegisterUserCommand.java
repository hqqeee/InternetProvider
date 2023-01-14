package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.dto.UserForm;
import com.epam.util.AppContext;

public class RegisterUserCommand implements Command {

	private final Logger logger = LogManager.getLogger(RegisterUserCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		UserForm userForm = new UserForm();
		String regPage = Page.USER_REGISTRATION_PAGE;
		userForm.setFirstName(req.getParameter("firstName"));
		userForm.setLastName(req.getParameter("lastName"));
		userForm.setLogin(req.getParameter("login"));
		userForm.setEmail(req.getParameter("email"));
		userForm.setCity(req.getParameter("city"));
		userForm.setAddress(req.getParameter("address"));
		try {
			AppContext.getInstance().getUserService().registerUser(userForm);
		} catch (ValidationErrorException e) {
			req.setAttribute("userForm", userForm);
			req.setAttribute("errorMessages", e.getErrors());
			return regPage;
		} catch (UserAlreadyExistException e) {
			req.setAttribute("userForm", userForm);
			req.setAttribute("userAlreadyExists", e.getMessage());
			return regPage;
		} catch (UserServiceException e) {
			logger.warn("An error occurred while registering user.");
			logger.error("Unable to register user due to unexpected error.", e);
			req.setAttribute("errorMessages", "Something went wrong. Try again later");
			return regPage;
		}
		logger.info("User: [" + userForm.getFirstName() + "," + userForm.getLastName() + "," + userForm.getLogin() + ","
				+ userForm.getEmail() + ", " + userForm.getCity() + "]" + " registered successfully.");
		req.setAttribute("successMessage", "User Registered Successfully!");
		return new AdminMenuCommand().execute(req, resp);
	}

}
