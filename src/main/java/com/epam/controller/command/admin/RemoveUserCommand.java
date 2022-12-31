package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.exception.services.UnableToRemoveUser;
import com.epam.services.UserService;

public class RemoveUserCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int userId = Integer.parseInt(req.getParameter("userId"));
			((UserService) req.getServletContext().getAttribute("userService")).removeUser(userId);
			req.setAttribute("successMessage", "User successfully removed.");
		} catch(UnableToRemoveUser e) {
			System.out.println(e);
			req.setAttribute("errorMessages", "Unable remove user. Try again.");
		}	catch (Exception e) {
			System.out.println(e);
			req.setAttribute("errorMessages", "Cannot remove user. Invalid request.");
		}
			
	
		return new AdminMenuCommand().execute(req, resp);
	}
	
}
