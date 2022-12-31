package com.epam.controller.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.UserNotFoundException;
import com.epam.services.UserService;

public class LoginCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp){
		System.out.println("Login executing");
		try {
			req.getSession().setAttribute("loggedUser", ((UserService) req.getServletContext()
					.getAttribute("userService"))
			.login(req.getParameter("login"), req.getParameter("password")));
			req.setAttribute("successMessage", "You've seccessfully logged in");
		} catch (UserNotFoundException e) {
			req.setAttribute("login", req.getParameter("login"));
			req.setAttribute("incorrectLoginOrPassword", "Incorrect login or password");
		}
		return Page.HOME_PAGE;
	}

}
