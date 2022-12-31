package com.epam.controller.command.subscriber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;

public class DisableTariffCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		try {
			int userID = ((User)req.getSession().getAttribute("loggedUser")).getId();
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			((UserService) req.getServletContext().getAttribute("userService")).removeTariffFromUser(userID, tariffId);
			req.setAttribute("successMessage", "Tariff removed seccessfully.");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Something went wrong. Please try again later.");
		}catch (UserServiceException e) {
			req.setAttribute("errorMessages", "Something went wrong. Please try again.");
			e.printStackTrace();
		}
		return new ViewActiveTariffsCommand().execute(req, resp);
	}

}
