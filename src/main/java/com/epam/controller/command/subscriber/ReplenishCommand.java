package com.epam.controller.command.subscriber;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.CommandNames;
import com.epam.controller.command.Page;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

public class ReplenishCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ReplenishCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		try {
			BigDecimal amount = new BigDecimal(req.getParameter("amount"));
			if (amount.compareTo(BigDecimal.ZERO) < 0) {
				req.setAttribute("errorMessages", rs.getString("error.amount_cannot_be_negative"));
				return new ViewAccountCommand().execute(req, resp);
			}
			UserDTO user = (UserDTO) req.getSession().getAttribute("loggedUser");
			String description = rs.getString("account.replenish_description");
			int userId = user.getId();
			AppContext appContext = AppContext.getInstance();
			appContext.getUserService().changeUserBalance(userId, amount,
					description);
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.VIEW_ACCOUNT + "&success=replenish");
			LOG.info("User(id = " + userId + ") replenished the account with $" + amount.toString());
			if (appContext.getUserService().getUserStatus(userId)) {
				List<TariffDTO> usersUnpaidTariffs = appContext.getTariffService().getUnpaidTariffs(user.getId());
				appContext.getUserService().chargeUserForTariffsUsing(user.getId(), usersUnpaidTariffs);
				appContext.getUserService().changeUserStatus(true, userId);
				LOG.info("User(id = " + userId + ") has been unblocked due to replenishment the account.");
			}
			return Page.REDIRECTED;
		} catch(NegativeUserBalanceException e) {
			return Page.REDIRECTED;
		}
		catch (UserServiceException e) {
			LOG.warn("A service error occurred while replenishing the account.");
			LOG.error("Unable to disable tariff due to service error.", e);
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while replenishing the account.");
			LOG.error("Unable to disable tariff due to unexpected error error.", e);
		}
		req.setAttribute("errorMessages", rs.getString("error.unable_to_replenish_account"));
		return new ViewAccountCommand().execute(req, resp);
	}

}
