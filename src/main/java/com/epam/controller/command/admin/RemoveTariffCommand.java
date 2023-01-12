package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.common.ViewTariffsCommand;
import com.epam.util.AppContext;

public class RemoveTariffCommand implements Command{

	private final Logger logger = LogManager.getLogger(RemoveTariffCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try{
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			AppContext.getInstance().getTariffService().removeTariff(tariffId);
			logger.info("Tariff with id " + tariffId + " successfully removed.");
			req.setAttribute("successMessage", "Tariff  successfully removed.");
		} catch (Exception e) {
			logger.warn("An error occurred while removing tariff.");
			logger.error("Unable to remove tariff due to unexpected error.", e);
			req.setAttribute("errorMessages", "Unable to remove tariff. Try again later.");
		}
		return new ViewTariffsCommand().execute(req, resp);
	}

}
