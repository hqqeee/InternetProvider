package com.epam.controller.command.subscriber;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.services.dto.UserDTO;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.dto.TariffDTO;
import com.epam.util.AppContext;

public class DisableTariffCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		try {
			int userId = ((UserDTO)req.getSession().getAttribute("loggedUser")).getId();
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			AppContext appContext = AppContext.getInstance();
			appContext.getUserService().removeTariffFromUser(userId, tariffId);
			req.setAttribute("successMessage", "Tariff removed seccessfully.");
			
			if (appContext.getUserService().getUserStatus(userId)) {
				List<TariffDTO> usersUnpaidTariffs = appContext.getTariffService().getUnpaidTariffs(userId);
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
