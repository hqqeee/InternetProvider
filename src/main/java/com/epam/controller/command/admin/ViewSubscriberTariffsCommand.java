package com.epam.controller.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.TariffService;

public class ViewSubscriberTariffsCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			List<Tariff> tariffs = ((TariffService) req.getServletContext().getAttribute("tariffService")).getUsersTariff(Integer.parseInt(req.getParameter("userId")));
			req.setAttribute("tariffsToDisplay", tariffs);
			req.setAttribute("userId", req.getParameter("userId"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (TariffServiceException e) {
			e.printStackTrace();
		}
		return Page.ACTIVE_TARIFFS_PAGE;
	}

}
