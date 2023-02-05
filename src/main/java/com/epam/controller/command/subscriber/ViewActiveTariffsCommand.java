package com.epam.controller.command.subscriber;

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
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

/**
 * Class ViewActiveTariffsCommand implements Command interface and is
 * responsible for loading tariffs for the currently logged user and providing
 * the view for active tariffs.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class ViewActiveTariffsCommand implements Command {
	/**
	 * Logger instance to log the events occurred.
	 */
	private static final Logger LOG = LogManager.getLogger(ViewActiveTariffsCommand.class);

	/**
	 * Overridden method from Command interface to execute the logic of loading
	 * tariffs and providing the view for active tariffs.
	 * 
	 * @param req  HttpServletRequest request object for reading the data from user
	 * @param resp HttpServletResponse response object for sending the response to
	 *             the user
	 * @return String value for the page to be redirected to
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Map<TariffDTO, Integer> tariffsWithDaysLeft = AppContext.getInstance().getTariffService()
					.getUsersTariffWithDaysUntilPayment(
							((UserDTO) req.getSession().getAttribute("loggedUser")).getId());
			req.setAttribute("tariffsToDisplay", tariffsWithDaysLeft);
			return Page.ACTIVE_TARIFFS_PAGE;
		} catch (TariffServiceException e) {
			LOG.warn("A service error occurred while loading user tariffs.");
			LOG.error("Unable to load user tariffs due to service error.", e);
		} catch (Exception e) {
			LOG.warn("An error occurred while loading tariffs view.");
			LOG.error("Unable to load tariffs view due to unexpected error.", e);
		}
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"))
				.getString("error.unable_to_load_your_tariffs"));
		return Page.ACTIVE_TARIFFS_PAGE;
	}

}
