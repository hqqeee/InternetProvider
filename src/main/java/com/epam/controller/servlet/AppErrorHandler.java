package com.epam.controller.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
		ResourceBundle rb = ResourceBundle.getBundle("lang", (Locale) request.getAttribute("locale"));
		if (servletName == null) {
			servletName = rb.getString("error_handler_unknown");
		}
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = rb.getString("error_handler_unknown");
		}
		List<String> error = new ArrayList<>();
		error.add(rb.getString("error_handler_first_line"));
		if (statusCode != 500) {
			error.add(rb.getString("error_handler_status_code") + statusCode);
			error.add(rb.getString("error_handler_request_uri") + requestUri);
		} else {
			error.add(rb.getString("error_handler_exception_name") + throwable.getClass().getName());
			error.add(rb.getString("error_handler_request_uri") + requestUri);
			error.add(rb.getString("error_handler_exception_message") + throwable.getMessage());
		}
		request.setAttribute("errorMessages", error);
		request.getRequestDispatcher(Page.HOME_PAGE).forward(request, response);
	}
}
