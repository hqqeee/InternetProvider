package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.exception.services.UserServiceException;
import com.epam.util.AppContext;

public class ChangeUserStatusCommand implements Command{

	private final Logger logger = LogManager.getLogger(ChangeUserStatusCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String blocked = req.getParameter("userBlocked");
		String userId = req.getParameter("userId");
		try{
			AppContext.getInstance().getUserService()
			.changeUserStatus(Boolean.parseBoolean(blocked), Integer.parseInt(userId));
			req.setAttribute("successMessage", "User status successfully changed.");
			logger.info("Status of the user with id " + userId + " successfully changed.");
		} catch (UserServiceException e) {
			logger.warn("An error occurred while changing user status.");
			logger.error("Unable to change user status due to service error.", e);
			req.setAttribute("errorMessages", "Unable change user status. Try again.");
		} catch (Exception e) {
			logger.warn("An error occurred while changing user status.");
			logger.error("Unable to change user status due to unexpected error.", e);
			req.setAttribute("errorMessages", "Unable change user status. Invalid request.");
		}
		
		return new AdminMenuCommand().execute(req, resp);
	}

}
