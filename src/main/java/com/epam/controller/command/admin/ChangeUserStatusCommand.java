package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;

public class ChangeUserStatusCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String blocked = req.getParameter("userBlocked");
		String userId = req.getParameter("userId");
		try{
			((UserService) req.getServletContext().getAttribute("userService"))
			.changeUserStatus(Boolean.parseBoolean(blocked), Integer.parseInt(userId));
			req.setAttribute("successMessage", "User status successfully changed.");
		} catch (UserServiceException e) {
			System.out.println(e);
			req.setAttribute("errorMessages", "Unable change user status. Try again.");
		} catch (Exception e) {
			System.out.println(e);
			req.setAttribute("errorMessages", "Unable change user status. Invalid request.");
		}
		
		return new AdminMenuCommand().execute(req, resp);
	}

}
