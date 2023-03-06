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

/**
 * 
 * The ChangeUserBalanceCommand class implements the Command interface and is
 * used to change the balance of a user. It retrieves the values of the
 * parameters entered by the administrator and changes the user's balance
 * accordingly.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 **/
public class ChangeUserBalanceCommand implements Command {
	/*
	 * A Logger instance to log error and info messages.
	 */
	private static final Logger LOG = LogManager.getLogger(ChangeUserBalanceCommand.class);

	/**
	 * 
	 * The method changes the balance of the user specified in the request
	 * parameters. The method retrieves the amount to change, a description of the
	 * change, and the type of change (withdraw or deposit) from the request
	 * parameters. The method retrieves the user ID from the request parameters. The
	 * method uses the UserService().changeUserBalance method to change the user's
	 * balance. If the difference is less than or equal to zero, the method sets an
	 * error message indicating that the amount cannot be negative or zero. If the
	 * description length is greater than 128 or less than or equal to zero, the
	 * method sets an error message indicating that the description is incorrect. If
	 * the change user balance method throws a NegativeUserBalanceException, the
	 * method sets an error message indicating that the user balance cannot be
	 * negative. If the change user balance method throws a UserServiceException,
	 * the method sets an error message indicating that it was unable to change the
	 * user's balance due to a service error. If any other exception is thrown, the
	 * method sets an error message indicating that it was unable to change the
	 * user's balance due to an unexpected error. In case of successful balance
	 * change, the method logs the change and redirects to the Admin menu page. In
	 * case of error, the method returns the admin menu page with an error message.
	 * 
	 * @param req  HttpServletRequest object containing the request parameters
	 * @param resp HttpServletResponse object
	 * @return String indicating the page to redirect to in case of successful
	 *         change, or the current page in case of error
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		try {
			Command adminMenu = new AdminMenuCommand();
			BigDecimal difference = new BigDecimal(req.getParameter("amount")).setScale(2);
			if (difference.compareTo(BigDecimal.ZERO) <= 0) {
				req.setAttribute("errorMessages", rs.getString(rs.getString("error.amount_cannot_be_negative")));
				return adminMenu.execute(req, resp);
			}
			String description = req.getParameter("description");
			if (description.length() > 128 || description.length() <= 0) {
				req.setAttribute("errorMessages", rs.getString("error.incorrect_description"));
				return adminMenu.execute(req, resp);
			}
			if (req.getParameter("balanceChangeType").equals("withdraw")) {
				difference = difference.negate();
			}
			int userId = Integer.parseInt(req.getParameter("userId"));
			AppContext.getInstance().getUserService().changeUserBalance(userId, difference, description);
			LOG.info("Balance of user with id {} changed.", userId);
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.ADMIN_MENU + "&userId="
					+ userId + "&success=balance_changed");
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
