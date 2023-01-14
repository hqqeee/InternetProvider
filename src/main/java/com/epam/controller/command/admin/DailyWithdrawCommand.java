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

public class DailyWithdrawCommand implements Command, Runnable {

	private final Logger logger = LogManager.getLogger(DailyWithdrawCommand.class);
	
	
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
				} catch (NegativeUserBalanceException e) {
					appContext.getUserService().changeUserStatus(false, user.getId());
				}
			}
			logger.info("Daily withdrawal successfully completed.");
		} catch (UserServiceException | TariffServiceException e1) {
			logger.warn("An error occurred while executing daily withdrawal.");
			logger.fatal("Daily withdrawal failed.", e1);
		}

	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		run();
		return Page.HOME_PAGE;
	}

}
