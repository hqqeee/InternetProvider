package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;

public class RemoveUserCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int userId = Integer.parseInt(req.getParameter("userId"));
			AppContext.getInstance().getUserService().removeUser(userId);
			req.setAttribute("successMessage", "User successfully removed.");
		} catch(UserServiceException e) {
			System.out.println(e);
			req.setAttribute("errorMessages", "Unable remove user. Try again.");
		}	catch (Exception e) {
			System.out.println(e);
			req.setAttribute("errorMessages", "Cannot remove user. Invalid request.");
		}
			
	
		return new AdminMenuCommand().execute(req, resp);
	}
	
}
