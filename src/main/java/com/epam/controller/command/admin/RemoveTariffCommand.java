package com.epam.controller.command.admin;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.CommandNames;
import com.epam.controller.command.Page;
import com.epam.controller.command.common.ViewTariffsCommand;
import com.epam.exception.services.TariffServiceException;
import com.epam.util.AppContext;

public class RemoveTariffCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(RemoveTariffCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			AppContext.getInstance().getTariffService().removeTariff(tariffId);
			LOG.info("Tariff with id " + tariffId + " successfully removed.");
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.VIEW_TARIFFS
					+ "&success=remove_tariff");
			return Page.REDIRECTED;
		} catch (TariffServiceException e) {
			LOG.warn("A service error occurred while removing tariff.");
			LOG.error("Unable to remove tariff due to service error.", e);
		} catch (Exception e) {
			LOG.warn("An error occurred while removing tariff.");
			LOG.error("Unable to remove tariff due to unexpected error.", e);
		}
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale)req.getAttribute("locale")).getString("error.unable_to_remove_tariff"));
		return new ViewTariffsCommand().execute(req, resp);
	}

}
