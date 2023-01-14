package com.epam.controller.command.admin;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.UserServiceException;
import com.epam.util.AppContext;

public class ChangeUserBalanceCommand implements Command {

	private final Logger logger = LogManager.getLogger(ChangeUserBalanceCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Command adminMenu = new AdminMenuCommand();
			BigDecimal difference = new BigDecimal(req.getParameter("amount"));
			if (difference.compareTo(BigDecimal.ZERO) < 0) {
				req.setAttribute("errorMessages", "Amount cannot be negative.");
				return adminMenu.execute(req, resp);
			}
			String description = req.getParameter("description");
			if(description.length() > 128) {
				req.setAttribute("errorMessages", "Description is too long.");
				return adminMenu.execute(req, resp);
			}
			if (req.getParameter("balanceChangeType").equals("withdraw")) {
				difference = difference.negate();
			}
			int userId = Integer.parseInt(req.getParameter("userId"));
			AppContext.getInstance().getUserService().changeUserBalance(userId, difference, description);
			req.setAttribute("successMessage", "User's balance uccessfully changed!");
			logger.info("Balance of user with id " + userId + " changed.");

		} catch (NegativeUserBalanceException e) {
			req.setAttribute("errorMessages", "User balance cannot be negative.");
		} catch (UserServiceException e) {
			logger.warn("An error occurred while changing user balance.");
			logger.error("Unable to change user balance due to service error.", e);
			req.setAttribute("errorMessages", "Unable change user balance. Please try again later.");
		} catch (Exception e) {
			logger.warn("An error occurred while changing user balance.");
			logger.error("Unable to change user balance due to unexpected error.", e);
			req.setAttribute("errorMessages", "Unable change user balance. Please check your input.");
		}
		return new AdminMenuCommand().execute(req, resp);
	}

}
