package com.epam.controller.command.subscriber;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.Tariff;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.TariffService;

public class ViewActiveTariffsCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Map<Tariff, Integer> tariffsWithDaysLeft = ((TariffService) req.getServletContext().getAttribute("tariffService"))
					.getUsersTariffWithDaysUntilPayment(((User) req.getSession().getAttribute("loggedUser")).getId());
//			List<Tariff> tariffs = ((TariffService) req.getServletContext().getAttribute("tariffService"))
//					.getUsersTariff(((User) req.getSession().getAttribute("loggedUser")).getId());
			req.setAttribute("tariffsToDisplay", tariffsWithDaysLeft);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (TariffServiceException e) {
			e.printStackTrace();
		}
		return Page.ACTIVE_TARIFFS_PAGE;
	}

}
