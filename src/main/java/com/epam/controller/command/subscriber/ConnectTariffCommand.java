package com.epam.controller.command.subscriber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.common.ViewTariffsCommand;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.UserAlreadyHasTariffException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;

public class ConnectTariffCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("Connect tariff");
		System.out.println(req.getParameter("tariffId"));
			try {
				((UserService) req.getServletContext().getAttribute("userService")).addTariffToUser(((User)req.getSession().getAttribute("loggedUser")).getId(),Integer.parseInt(req.getParameter("tariffId")));
				req.setAttribute("successMessage", "Tariff connected seccessfully.");
			} catch (NumberFormatException e) {
				e.printStackTrace();
				req.setAttribute("errorMessages", "Something went wrong. Please try again.");
			} catch (UserAlreadyHasTariffException e) {
				e.printStackTrace();
				req.setAttribute("errorMessages", "You have already connected this tariff plan.");
			} catch (UserServiceException e) {
				e.printStackTrace();
				req.setAttribute("errorMessages", "Something went wrong. Please try again.");
			}
		
		return new ViewTariffsCommand().execute(req, resp);
	}

}
