package com.epam.controller.command.subscriber;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.dataaccess.entity.Tariff;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;

public class DisableTariffCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		try {
			int userId = ((User)req.getSession().getAttribute("loggedUser")).getId();
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			((UserService) req.getServletContext().getAttribute("userService")).removeTariffFromUser(userId, tariffId);
			req.setAttribute("successMessage", "Tariff removed seccessfully.");
			if (((UserService) req.getServletContext().getAttribute("userService")).getUserStatus(userId)) {
				AppContext appContext = AppContext.getInstance();
				List<Tariff> usersUnpaidTariffs = appContext.getTariffService().getUnpaidTariffs(userId);
				appContext.getUserService().chargeUserForTariffsUsing(userId, usersUnpaidTariffs);
				appContext.getUserService().changeUserStatus(true, userId);
			}	
		} catch (NegativeUserBalanceException e) {
			
		}
		
		catch (NumberFormatException e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Something went wrong. Please try again later.");
		}catch (UserServiceException | TariffServiceException e) {
			req.setAttribute("errorMessages", "Something went wrong. Please try again.");
			e.printStackTrace();
		}
		return new ViewActiveTariffsCommand().execute(req, resp);
	}

}
