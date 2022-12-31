package com.epam.controller.command.subscriber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;

public class ViewProfileCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		return Page.PROFILE_PAGE;
	}

}
