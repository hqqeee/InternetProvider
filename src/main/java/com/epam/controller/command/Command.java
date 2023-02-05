package com.epam.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * The Command interface is implemented by any class whose instances are
 * intended to represent a command to be executed in the context of a HTTP
 * request and response.
 * 
 * The HttpServletRequest, HttpServletResponse method of each implementation
 * class should contain the logic for executing the corresponding command and
 * returning the appropriate string that represents the path of the next JSP
 * page to be displayed to the user.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 **/
public interface Command {
	/**
	 * Executes the corresponding command in the context of the given HTTP request
	 * and response.
	 * 
	 * @param req  the HttpServletRequest} representing the HTTP request
	 *             received
	 * @param resp the HttpServletResponse representing the HTTP response to
	 *             be sent
	 * @return the string that represents the path of the next JSP page to be
	 *         displayed to the user
	 */
	public String execute(HttpServletRequest req, HttpServletResponse resp);
}
