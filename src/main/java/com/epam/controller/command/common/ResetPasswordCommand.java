package com.epam.controller.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;

public class ResetPasswordCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		UserService userService = AppContext.getInstance().getUserService();
		String email = req.getParameter("resetEmail");
		if(email != null && !email.isBlank()) {
			try {
				userService.resetPassword(email);
				req.setAttribute("successMessage", "If user with this email exists it'll get email with new credentials.");
			} catch (UserServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				req.setAttribute("errorMessages", "Unable reset password. Please try again later.");
			}
		}
		return Page.HOME_PAGE;
	}

}
