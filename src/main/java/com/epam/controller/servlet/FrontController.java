package com.epam.controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;

@WebServlet("/controller")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Command command = ((Command) req.getAttribute("command"));
		String page;
		if(command != null) {
			page = command.execute(req, resp);
			if(page == null) {
				return;
			}
		} else {
			page = Page.HOME_PAGE;
			req.setAttribute("errorMessages", "You cannot access this page");
		}
		getServletContext().getRequestDispatcher(page).forward(req, resp);
	}
}
