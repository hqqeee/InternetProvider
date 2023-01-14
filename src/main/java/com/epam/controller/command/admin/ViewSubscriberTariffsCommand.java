package com.epam.controller.command.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.dto.TariffDTO;
import com.epam.util.AppContext;

public class ViewSubscriberTariffsCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Map<TariffDTO, Integer> tariffsWithDaysLeft = AppContext.getInstance().getTariffService()
					.getUsersTariffWithDaysUntilPayment(Integer.parseInt(req.getParameter("userId")));
			req.setAttribute("tariffsToDisplay", tariffsWithDaysLeft);
			req.setAttribute("userId", req.getParameter("userId"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (TariffServiceException e) {
			e.printStackTrace();
		}
		return Page.ACTIVE_TARIFFS_PAGE;
	}

}
