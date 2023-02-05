package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;

/**
 * 
 * This class provides the implementation of the Command interface. It is
 * responsible for handling the request to open the page to register new user.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class OpenUserRegistrationCommand implements Command {
	/**
	 * 
	 * Executes the OpenUserRegistrationCommand, which opens the page for
	 * registering new user.
	 * 
	 * @param req  the HttpServletRequest object that contains the request the
	 *             client made of the servlet
	 * @param resp the HttpServletResponse object that contains the response the
	 *             servlet returns to the client
	 * @return a string representing the next page(user registration page).
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		return Page.USER_REGISTRATION_PAGE;
	}

}
