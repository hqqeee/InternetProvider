package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.UserService;
import com.epam.services.forms.UserForm;
import com.epam.util.AppContext;

public class RegisterUserCommand implements Command{

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
			AppContext.getInstance().getUserService().registerUser(userForm, req.getParameter("password"));
		} catch (ValidationErrorException e) {
			req.setAttribute("userForm", userForm);
			req.setAttribute("errorMessages", e.getErrors());
			return Page.USER_REGISTRATION_PAGE;
		} catch (UserAlreadyExistException e) {
			req.setAttribute("userForm", userForm);
			req.setAttribute("userAlreadyExists", e.getMessage());
			return Page.USER_REGISTRATION_PAGE;
		} catch (UserServiceException e) {
			req.setAttribute("errorMessages", "Something went wrong. Try again later");
			e.printStackTrace();
		}
		req.setAttribute("successMessage", "User Registered Successfully!");
		return new AdminMenuCommand().execute(req, resp);
	}

}
