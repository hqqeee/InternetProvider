package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.common.ViewTariffsCommand;
import com.epam.services.TariffService;
import com.epam.util.AppContext;

public class RemoveTariffCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try{
			AppContext.getInstance().getTariffService().removeTariff(Integer.parseInt(req.getParameter("tariffId")));
			req.setAttribute("successMessage", "Tariff  successfully removed.");
		} catch (Exception e) {
			req.setAttribute("errorMessages", "Unable to remove tariff. Try again later.");
		}
		return new ViewTariffsCommand().execute(req, resp);
	}

}
