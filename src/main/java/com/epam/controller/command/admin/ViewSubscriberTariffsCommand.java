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

/**
 * 
 * The ViewSubscriberTariffsCommand class implements the Command interface and
 * is used to retrieve the list of tariffs that a particular subscriber with the
 * days left until their next payment. The class logs error messages in case of
 * a service error or an unexpected error occurs during the retrieval process.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class ViewSubscriberTariffsCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(ViewSubscriberTariffsCommand.class);

	/**
	 * 
	 * This method retrieves the list of tariffs and their days left until next
	 * payment for a specific subscriber using the TariffService. The list is then
	 * stored in a request attribute to be displayed to the administrator. If a
	 * TariffServiceException or any other Exception occurs, error messages are
	 * logged and the user is redirected to the admin menu page.
	 * 
	 * @param req  {@code HttpServletRequest} object containing user request
	 * @param resp {@code HttpServletResponse} object to send the response
	 * @return URL of the page to be displayed to the user
	 */
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
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"))
				.getString("error.unable_to_load_users_tariffs"));
		return new AdminMenuCommand().execute(req, resp);
	}

}
