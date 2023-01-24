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

@WebServlet("/AppErrorHandler")
public class AppErrorHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processError(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processError(request, response);
	}

	private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
		if (statusCode != 500) {
			error.add("Something went wrong.");
			error.add("Status Code: " + statusCode);
			error.add("Requested URI: " + requestUri);
		} else {
			error.add("Something went wrong.");
			error.add("Exception Name: " + throwable.getClass().getName());
			error.add("Requested URI: " + requestUri);
			error.add("Exception Message:" + throwable.getMessage());
		}
		request.setAttribute("errorMessages", error);
		request.getRequestDispatcher(Page.HOME_PAGE).forward(request, response);
	}
}
