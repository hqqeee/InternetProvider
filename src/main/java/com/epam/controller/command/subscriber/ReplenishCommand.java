package com.epam.controller.command.subscriber;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.dataaccess.entity.Tariff;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;

public class ReplenishCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			BigDecimal amount = new BigDecimal(req.getParameter("amount"));
			// TODO validate amount not negative!!
			User user = (User) req.getSession().getAttribute("loggedUser");
			String description = "Replenish via web site.";
			int userId = user.getId();
			AppContext appContext = AppContext.getInstance();
			appContext.getUserService().changeUserBalance(userId, amount,
					description);
			req.setAttribute("successMessage", "Funds have been successfully added!");
			if (appContext.getUserService().getUserStatus(userId)) {
				List<Tariff> usersUnpaidTariffs = appContext.getTariffService().getUnpaidTariffs(user.getId());
				appContext.getUserService().chargeUserForTariffsUsing(user.getId(), usersUnpaidTariffs);
				appContext.getUserService().changeUserStatus(true, userId);
			}
		} catch(NegativeUserBalanceException e) {
			
		}
		catch (UserServiceException e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Unable replenish balance. Please try again later.");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Unable replenish balance. Please check your input.");
		}
		return new ViewAccountCommand().execute(req, resp);
	}

}
