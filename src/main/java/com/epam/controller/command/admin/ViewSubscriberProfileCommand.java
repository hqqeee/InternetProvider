package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;

public class ViewSubscriberProfileCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			User user = AppContext.getInstance().getUserService().getUserById(Integer.parseInt(req.getParameter("userId")));
			req.setAttribute("currentUser", user);
			req.setAttribute("userId", req.getParameter("userId"));
			return Page.PROFILE_PAGE;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new AdminMenuCommand().execute(req, resp);
	}

}
