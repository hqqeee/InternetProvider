package com.epam.controller.command.common;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;

public class ChangeLanguageCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String lang = req.getParameter("lang");
		if(lang != null) {
			Cookie langCookie = new Cookie("lang", lang);
			resp.addCookie(langCookie);
		}
		try {
			resp.sendRedirect(req.getContextPath());
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}

}
