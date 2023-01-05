package com.epam.controller.command.subscriber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.User;

public class ViewProfileCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		if((User)req.getAttribute("currentUser") == null) {
			req.setAttribute("currentUser", req.getSession().getAttribute("loggedUser"));
		}
		return Page.PROFILE_PAGE;
	}

}
