package com.epam.controller.command;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.epam.controller.command.common.LoginCommand;
import com.epam.exception.action.ActionNotFoundException;

public class CommandFactory {
	private CommandFactory() {};
	public static Command getCommonCommand(HttpServletRequest req, ServletContext servletContext){
		Map <String,Command> commonCommands = ((Map<String,Command>) servletContext.getAttribute("commonCommands"));
		System.out.println(req.getParameter("action"));
		return commonCommands.get(req.getParameter("action"));
	}

	public static Command getAdminCommand(HttpServletRequest req, ServletContext servletContext) {
		Map <String,Command> adminCommands = ((Map<String,Command>) servletContext.getAttribute("adminCommands"));
		return adminCommands.get(req.getParameter("action"));
	}
	
	public static Command getSubscriberCommand(HttpServletRequest req, ServletContext servletContext) {
		Map <String,Command> subscriberCommands = ((Map<String,Command>) servletContext.getAttribute("subscriberCommands"));
		return subscriberCommands.get(req.getParameter("action"));
	}
}
