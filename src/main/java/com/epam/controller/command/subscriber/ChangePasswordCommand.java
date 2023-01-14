package com.epam.controller.command.subscriber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.exception.services.PasswordNotMatchException;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

public class ChangePasswordCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		UserDTO user = (UserDTO)req.getSession().getAttribute("loggedUser");
		String currentPassword = req.getParameter("currentPassword");
		String newPassword = req.getParameter("newPassword");
		
		if(user == null || currentPassword == null || newPassword == null) req.setAttribute("errorMessages", "Something went wrong. Cannot change password. Try againg later.");
		else {try {
			AppContext.getInstance().getUserService().changePassword(user.getId(), currentPassword, newPassword);
			req.setAttribute("successMessage", "Password seccsessfully changed");
		} catch (UserNotFoundException e) {
			req.setAttribute("errorMessages", "Something went wrong. Cannot change password. Relogin and try again.");
			e.printStackTrace();
		} catch (UserServiceException e) {
			req.setAttribute("errorMessages", "Something went wrong. Cannot change password. Try againg later.");
			e.printStackTrace();
		} catch (PasswordNotMatchException e) {
		req.setAttribute("incorrectChangePasswordFormError", "Current password is incorrect. Try again.");
			e.printStackTrace();
		} catch (ValidationErrorException e) {
			req.setAttribute("incorrectChangePasswordFormError", e.getErrors().get(0));
			e.printStackTrace();
		}	
		}
		return new ViewProfileCommand().execute(req, resp);
	}

}
