package com.epam.controller.command.admin;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.dto.TariffDTO;
import com.epam.util.AppContext;

public class ViewSubscriberTariffsCommand implements Command{

	private static final Logger LOG = LogManager.getLogger(ViewSubscriberTariffsCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Map<TariffDTO, Integer> tariffsWithDaysLeft = AppContext.getInstance().getTariffService()
					.getUsersTariffWithDaysUntilPayment(Integer.parseInt(req.getParameter("userId")));
			req.setAttribute("tariffsToDisplay", tariffsWithDaysLeft);
			req.setAttribute("userId", req.getParameter("userId"));
			return Page.ACTIVE_TARIFFS_PAGE;
		} catch (TariffServiceException e) {
			LOG.warn("A service error occurred while loading user tariffs.");
			LOG.error("Unable to load user tariffs due to service error.", e);
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while loading user tariffs.");
			LOG.error("Unable to user tariffs due to unexpected error.", e);
		}
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale)req.getAttribute("locale")).getString("error.unable_to_load_users_tariffs"));
		return new AdminMenuCommand().execute(req, resp);
	}

}
