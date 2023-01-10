package com.epam.controller.command.common;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.util.AppContext;

public class LoginCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp){
		System.out.println("Login executing");
		ResourceBundle bundle =  ResourceBundle.getBundle("lang", (Locale)req.getAttribute("locale"));
		try {
			req.getSession().setAttribute("loggedUser",AppContext.getInstance().getUserService()
			.login(req.getParameter("login"), req.getParameter("password")));
			req.setAttribute("successMessage", bundle.getString("login.success"));
		} catch (UserNotFoundException e) {
			req.setAttribute("login", req.getParameter("login"));
			req.setAttribute("incorrectLoginOrPassword", bundle.getString("login.incorrect_login_or_password"));
		} catch (UserServiceException e) {
			req.setAttribute("errorMessages", "Something went wrong. Try again later.");
		}
		return Page.HOME_PAGE;
	}

}
