package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.CommandNames;
import com.epam.controller.command.Page;
import com.epam.exception.services.UserServiceException;
import com.epam.util.AppContext;

public class RemoveUserCommand implements Command{

	private static final Logger LOG = LogManager.getLogger(RemoveUserCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int userId = Integer.parseInt(req.getParameter("userId"));
			AppContext.getInstance().getUserService().removeUser(userId);
			LOG.info("Tariff with id " + userId + " successfully removed.");
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.ADMIN_MENU + "&success=remove_user");
			return Page.REDIRECTED;
		} catch(UserServiceException e) {
			LOG.warn("A service error occurred while removing user.");
			LOG.error("Unable to remove user due to service error.", e);
			req.setAttribute("errorMessages", "Unable remove user. Try again.");
		}	catch (Exception e) {
			LOG.warn("An error occurred while removing user.");
			LOG.error("Unable to remove user due to unexpected error.", e);
			req.setAttribute("errorMessages", "Cannot remove user. Invalid request.");
		}
		return new AdminMenuCommand().execute(req, resp);
	}
	
}
