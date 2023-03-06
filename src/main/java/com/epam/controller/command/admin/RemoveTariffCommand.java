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

/**
 * 
 * The RemoveTariffCommand class implements the {@link Command} interface and
 * removes a tariff. The RemoveTariffCommand class uses the TariffService
 * instance and remove a tariff by its id. In case of success, the user is
 * redirected to the view tariffs page. In case of error, the user stays on the
 * view tariffs page and an error message is displayed. The error can be caused
 * by a service error or an unexpected error.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class RemoveTariffCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(RemoveTariffCommand.class);

	/**
	 * This method is used to handle the removal of a tariff from the persistence
	 * layer. If the removal is successful, the user is redirected to the view
	 * tariffs page with a success message. If an error occurs, the user is
	 * redirected to the view tariffs page with an error message.
	 * 
	 * @param req  an HttpServletRequest object that contains the request the client
	 *             has made of the servlet
	 * @param resp an HttpServletResponse object that contains the response the
	 *             servlet sends to the client
	 * 
	 * @return a String object representing the next page to be displayed. This can
	 *         either be the redirected view tariffs page or the view tariffs page
	 *         with an error message.
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			AppContext.getInstance().getTariffService().removeTariff(tariffId);
			LOG.info("Tariff with id {} successfully removed.", tariffId);
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
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"))
				.getString("error.unable_to_remove_tariff"));
		return new ViewTariffsCommand().execute(req, resp);
	}

}
