package com.epam.controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.CommandFactory;
import com.epam.controller.command.Page;
import com.epam.exception.controller.CommandNotFoundException;
/**
 * FrontController is a servlet that acts as a front controller for the application.
 * It receives HTTP requests and delegates the request processing to appropriate command object
 * using CommandFactory. 
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
@WebServlet("/controller")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Logger instance to log the events occurred.
	 */
	private static final Logger LOG = LogManager.getLogger(FrontController.class);
	
	/**
	 * doGet method is called when an HTTP GET request is sent to the servlet. 
	 * This method forwards the request to the processRequest method for processing.
	 *
	 * @param req HttpServletRequest object that contains the request the client has made of the servlet.
	 * @param resp HttpServletResponse object that contains the response the servlet sends to the client.
	 * @throws ServletException If an exception occurs that interferes with the servlet's normal operation.
	 * @throws IOException If an input or output exception occurs.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	/**
	 * doPost method is called when an HTTP POST request is sent to the servlet. 
	 * This method forwards the request to the processRequest method for processing.
	 *
	 * @param req HttpServletRequest object that contains the request the client has made of the servlet.
	 * @param resp HttpServletResponse object that contains the response the servlet sends to the client.
	 * @throws ServletException If an exception occurs that interferes with the servlet's normal operation.
	 * @throws IOException If an input or output exception occurs.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	/**
	 * processRequest method processes the incoming request. 
	 * The method gets the appropriate command from CommandFactory and executes the command.
	 * The method sets the appropriate page as request attribute based on the execution result.
	 *
	 * @param req HttpServletRequest object that contains the request the client has made of the servlet.
	 * @param resp HttpServletResponse object that contains the response the servlet sends to the client.
	 * @throws ServletException If an exception occurs that interferes with the servlet's normal operation.
	 * @throws IOException If an input or output exception occurs.
	 */
	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Command command = null;
		String page = Page.HOME_PAGE;
		try {
			command = CommandFactory.getInstance().getCommand(req.getParameter("action"));
			page = command.execute(req, resp);
		} catch (CommandNotFoundException e) {
			LOG.warn("Attempt to execute not existing command.");
			req.setAttribute("errorMessages", "You cannot access this page.");
		}
		if(page != null && !page.equals(Page.REDIRECTED)) {
			req.getRequestDispatcher(page).forward(req, resp);
		}
	}
}
