package com.epam.controller.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Page;

/**
 * The AppErrorHandler class is a servlet that handles errors in the
 * application. It processes the error message and creates a list of error
 * messages to be displayed to the user.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
@WebServlet("/AppErrorHandler")
public class AppErrorHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Overrides the doGet method to process errors in the application.
	 * 
	 * @param request  the HttpServletRequest object that contains the client's
	 *                 request
	 * @param response the HttpServletResponse object that will contain the
	 *                 servlet's response
	 * @throws ServletException if there is a servlet error
	 * @throws IOException      if there is an I/O error
	 **/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processError(request, response);
	}

	/**
	 * Overrides the doPost method to process errors in the application.
	 * 
	 * @param request  the HttpServletRequest object that contains the client's
	 *                 request
	 * @param response the HttpServletResponse object that will contain the
	 *                 servlet's response
	 * @throws ServletException if there is a servlet error
	 * @throws IOException      if there is an I/O error
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processError(request, response);
	}

	/**
	 * Processes the error in the application by analyzing the exception and
	 * creating a list of error messages to be displayed to the user.
	 * 
	 * @param request  the HttpServletRequest object that contains the client's
	 *                 request
	 * @param response the HttpServletResponse object that will contain the
	 *                 servlet's response
	 * @throws IOException      if there is an I/O error
	 * @throws ServletException if there is a servlet error
	 */
	void processError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Analyze the servlet exception
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		if (servletName == null) {
			servletName = "Unknown";
		}
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}
		List<String> error = new ArrayList<>();
		error.add("Something went wrong.");
		if (statusCode != 500) {
			error.add("Status Code: " + statusCode);
			error.add("Requested URI: " + requestUri);
		} else {
			error.add("Exception Name: " + throwable.getClass().getName());
			error.add("Requested URI: " + requestUri);
			error.add("Exception Message:" + throwable.getMessage());
		}
		request.setAttribute("errorMessages", error);
		request.getRequestDispatcher(Page.HOME_PAGE).forward(request, response);
	}
}
