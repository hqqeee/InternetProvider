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

/**
 * The ReplenishCommand class implements Command interface and represents a
 * command to replenish a user's account. This class changes the user's balance
 * and sends a redirect to ViewAccountCommand if the account replenishment was
 * successful.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class ReplenishCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(ReplenishCommand.class);

	/**
	 * Executes the replenishment of a user's account. The method retrieves the
	 * amount to be replenished and the user who is currently logged in from the
	 * request. Then it updates the user's balance by calling
	 * UserService.changeUserBalance method If the account has been
	 * successfully replenished, the method sends a redirect to
	 * ViewAccountCommand. If the amount is negative, the method sets an
	 * error message in the request and returns to ViewAccountCommand. If
	 * the user's balance becomes positive, the method calls
	 * UserService.changeUserStatus method to unblock the
	 * user. If an error occurs while replenishing the account, the method sets an
	 * error message in the request and returns to ViewAccountCommand.
	 *
	 * @param req  the HttpServletRequest request
	 * @param resp the HttpServletResponse response
	 * @return the string representation of the next page
	 */
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
			appContext.getUserService().changeUserBalance(userId, amount, description);
			resp.sendRedirect(
					req.getContextPath() + "/controller?action=" + CommandNames.VIEW_ACCOUNT + "&success=replenish");
			LOG.info("User(id = {}) replenished the account with ${}", amount,userId);
			if (appContext.getUserService().getUserStatus(userId)) {
				List<TariffDTO> usersUnpaidTariffs = appContext.getTariffService().getUnpaidTariffs(user.getId());
				appContext.getUserService().chargeUserForTariffsUsing(user.getId(), usersUnpaidTariffs);
				appContext.getUserService().changeUserStatus(true, userId);
				LOG.info("User(id = {}) has been unblocked due to replenishment the account.",userId);
			}
			return Page.REDIRECTED;
		} catch (NegativeUserBalanceException e) {
			return Page.REDIRECTED;
		} catch (UserServiceException e) {
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
