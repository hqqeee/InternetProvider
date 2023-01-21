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

public class DailyWithdrawCommand implements Command, Runnable {

	private static final Logger LOG = LogManager.getLogger(DailyWithdrawCommand.class);
	
	
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

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		run();
		return Page.HOME_PAGE;
	}

}
