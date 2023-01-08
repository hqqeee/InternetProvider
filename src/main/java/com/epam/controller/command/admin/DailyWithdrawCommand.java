package com.epam.controller.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.Tariff;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.UserServiceException;
import com.epam.util.AppContext;

public class DailyWithdrawCommand implements Command, Runnable{

	@Override
	public void run() {
		AppContext appContext = AppContext.getInstance();
		
		
		List<User> users = null;

		try {
			users = appContext.getUserService().getSubscriberForCharging();
			appContext.getTariffService().updateDaysUntilPayments();
			System.out.println(users);
		} catch (UserServiceException | TariffServiceException e1) {
			e1.printStackTrace();
		}
		for(User user: users) {
			try {
				List<Tariff> usersUnpaidTariffs = appContext.getTariffService().getUnpaidTariffs(user.getId());
				try {
					appContext.getUserService().chargeUserForTariffsUsing(user.getId(), usersUnpaidTariffs);
				} catch (UserServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NegativeUserBalanceException e) {
					try {
						appContext.getUserService().changeUserStatus(false, user.getId());
					} catch (UserServiceException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} catch (TariffServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("DailyWithdraw executed");
		}
	}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		run();
		return Page.HOME_PAGE;
	}

}
