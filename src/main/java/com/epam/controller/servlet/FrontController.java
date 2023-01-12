package com.epam.controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.CommandFactory;
import com.epam.controller.command.Page;
import com.epam.exception.controller.CommandNotFoundException;

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
		Command command = null;
		String page = Page.HOME_PAGE;
		try {
			command = CommandFactory.getInstance().getCommand(req.getParameter("action"));
			page = command.execute(req, resp);
		} catch (CommandNotFoundException e) {
			System.out.println(e.getMessage());
			req.setAttribute("errorMessages", "You cannot access this page.");
		}
		if(page != null) {
			getServletContext().getRequestDispatcher(page).forward(req, resp);
		}
	}
}
