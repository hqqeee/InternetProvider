package com.epam.controller.command;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.epam.controller.command.common.LoginCommand;
import com.epam.exception.action.ActionNotFoundException;
import com.epam.util.AppContext;

public class CommandFactory {
	private CommandFactory() {};
	public static Command getCommonCommand(HttpServletRequest req, ServletContext servletContext){
		Map <String,Command> commonCommands = AppContext.getInstance().getCommonCommands();
		return commonCommands.get(req.getParameter("action"));
	}

	public static Command getAdminCommand(HttpServletRequest req, ServletContext servletContext) {
		Map <String,Command> adminCommands = AppContext.getInstance().getAdminCommands();
		return adminCommands.get(req.getParameter("action"));
	}
	
	public static Command getSubscriberCommand(HttpServletRequest req, ServletContext servletContext) {
		Map <String,Command> subscriberCommands = AppContext.getInstance().getSubscriberCommands();
		return subscriberCommands.get(req.getParameter("action"));
	}
}
