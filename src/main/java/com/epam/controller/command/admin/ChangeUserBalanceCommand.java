package com.epam.controller.command.admin;

import java.math.BigDecimal;
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
import com.epam.util.AppContext;

public class ChangeUserBalanceCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ChangeUserBalanceCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale)req.getAttribute("locale"));
		try {
			Command adminMenu = new AdminMenuCommand();
			BigDecimal difference = new BigDecimal(req.getParameter("amount"));
			difference.setScale(2);
			if (difference.compareTo(BigDecimal.ZERO) <= 0) {
				req.setAttribute("errorMessages",rs.getString(rs.getString("error.amount_cannot_be_negative")));
				return adminMenu.execute(req, resp);
			}
			String description = req.getParameter("description");
			if(description.length() > 128 || description.length() <= 0) {
				req.setAttribute("errorMessages", rs.getString("error.incorrect_description"));
				return adminMenu.execute(req, resp);
			}
			if (req.getParameter("balanceChangeType").equals("withdraw")) {
				difference = difference.negate();
			}
			int userId = Integer.parseInt(req.getParameter("userId"));
			AppContext.getInstance().getUserService().changeUserBalance(userId, difference, description);
			LOG.info("Balance of user with id " + userId + " changed.");
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.ADMIN_MENU + "&userId=" + userId
					+ "&success=balance_changed");
			return Page.REDIRECTED;
		} catch (NegativeUserBalanceException e) {
			req.setAttribute("errorMessages", rs.getString("error.user_balance_cannot_be_negative"));
		} catch (UserServiceException e) {
			LOG.warn("An error occurred while changing user balance.");
			LOG.error("Unable to change user balance due to service error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_change_balance"));
		} catch (Exception e) {
			LOG.warn("An error occurred while changing user balance.");
			LOG.error("Unable to change user balance due to unexpected error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_change_balance"));
		}
		return new AdminMenuCommand().execute(req, resp);
	}

}
