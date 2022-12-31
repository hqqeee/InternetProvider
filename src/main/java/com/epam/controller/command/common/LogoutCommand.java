package com.epam.controller.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;

public class LogoutCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		if(session!=null) {
			session.invalidate();
			req.setAttribute("successMessage", "You've seccessfully logged out.");
		}
		return Page.HOME_PAGE;
	}

}
