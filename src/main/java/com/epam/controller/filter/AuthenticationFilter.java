package com.epam.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.epam.controller.command.Command;
import com.epam.controller.command.CommandFactory;
import com.epam.dataaccess.entity.User;

@WebFilter(filterName="AuthenticationFilter")
public class AuthenticationFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		if(session != null) {
			User user = (User) session.getAttribute("loggedUser");
			Command command;
			if(user != null) {
				int roleId  = user.getRoleId();
				switch(roleId) {
					case 1:
						System.out.println("Admin " + user.getLogin() + " logged in.");
						command = (Command) CommandFactory.getAdminCommand(httpRequest, request.getServletContext());
						if(command != null) {
							request.setAttribute("command", command);
							break;
						}
					case 2:
						System.out.println("Subscriber " + user.getLogin() + " logged in.");
						command = (Command) CommandFactory.getSubscriberCommand(httpRequest, request.getServletContext());
						if(command != null) {
							request.setAttribute("command", command);
							break;
						}
					default:
						request.setAttribute("command", (Command) CommandFactory.getCommonCommand(httpRequest, request.getServletContext()));
				}
			} else {
				System.out.println("Unlogged user action.");
				request.setAttribute("command", (Command) CommandFactory.getCommonCommand(httpRequest, request.getServletContext()));
			}
		}

		chain.doFilter(request, response);
		
	}
	
}
