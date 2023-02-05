package com.epam.controller.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;
import com.epam.util.EmailUtil;

/**
 * The DailyWithdrawCommand implements the Runnable interface to run a daily
 * withdrawal process. It updates the days until payment for all tariffs, then
 * retrieves all subscribers that need to be charged. For each subscriber, the
 * class retrieves their unpaid tariffs and charges the user. If a user's
 * balance becomes negative, the user's status is changed to blocked and a
 * notification email is sent. The class also sends a receipt email for each
 * subscriber. If any exceptions occur during the process, a warning and fatal
 * log message is written.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class DailyWithdrawCommand implements Command, Runnable {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(DailyWithdrawCommand.class);

	/**
	 * This method implements the Runnable interface and executes the daily
	 * withdrawal process. The method first updates the days until the payment is
	 * due for all tariffs in the system. Then it gets all subscribers who are due
	 * for charging and charges them for their unpaid tariffs. If a user's balance
	 * is negative after charging, their status is changed to inactive, and an email
	 * notification is sent to them regarding the insufficient funds. Receipts are
	 * sent to the subscribers after charging their tariffs. The email notifications
	 * and receipts are sent via the EmailUtil class. If an error occurs while
	 * executing the daily withdrawal, it is logged and an error message is
	 * displayed.
	 */
	@Override
	public void run() {
		AppContext appContext = AppContext.getInstance();

		List<UserDTO> users = null;

		try {
			appContext.getTariffService().updateDaysUntilPayments();
			users = appContext.getUserService().getSubscriberForCharging();
			for (UserDTO user : users) {
				List<TariffDTO> usersUnpaidTariffs = appContext.getTariffService().getUnpaidTariffs(user.getId());
				try {
					appContext.getUserService().chargeUserForTariffsUsing(user.getId(), usersUnpaidTariffs);
					EmailUtil.INSTANCE.addReceipt(user.getEmail(), usersUnpaidTariffs);
				} catch (NegativeUserBalanceException e) {
					EmailUtil.INSTANCE.addNotEnoghtMoneyNotification(user.getEmail(), e.getValueUnderZero());
					appContext.getUserService().changeUserStatus(false, user.getId());
				}
			}
			LOG.info("Daily withdrawal successfully completed.");
			EmailUtil.INSTANCE.sendMails();
		} catch (UserServiceException | TariffServiceException e1) {
			LOG.warn("An error occurred while executing daily withdrawal.");
			LOG.fatal("Daily withdrawal failed.", e1);
		}

	}

	/**
	 * 
	 * The execute method of DailyWithdrawCommand performs the daily
	 * withdrawal task. It first invokes the run() method which updates the
	 * days until payment for all tariffs, retrieves all subscribers, charges each
	 * user for their unpaid tariffs, and sends receipts or notifications of
	 * insufficient funds to each user. The method then returns the home page
	 * Page.HOME_PAGE.
	 * 
	 * @param req  HttpServletRequest object
	 * @param resp HttpServletResponse object
	 * @return the home page URL
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		run();
		return Page.HOME_PAGE;
	}

}
