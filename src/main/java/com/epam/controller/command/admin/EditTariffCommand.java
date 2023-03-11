package com.epam.controller.command.admin;

import java.math.BigDecimal;
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
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffForm;
import com.epam.util.AppContext;
import com.epam.util.Validator;

/**
 * Class representing the implementation of the EditTariffCommand. The
 * EditTariffCommand class is used to edit a tariff in the system.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class EditTariffCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(EditTariffCommand.class);

	/**
	 * The execute method is used to edit a tariff in the persistence layer.
	 * 
	 * @param req  HttpServletRequest object used to get information from request.
	 * @param resp HttpServletResponse object used to send response.
	 * @return The string representation of the page to be redirected to.
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		TariffForm form = null;
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		try {
			form = new TariffForm(req.getParameter("name"), Integer.parseInt(req.getParameter("paymentPeriod")),
					new BigDecimal(req.getParameter("rate")),
					Service.getServiceByString(req.getParameter("serviceSelected")), req.getParameter("description"));
			Validator.validateTariffForm(form, ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale")));
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			AppContext.getInstance().getTariffService().editTariff(form, tariffId);
			LOG.info("Tariff( {}) has been edited. {}", tariffId, form);
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.VIEW_TARIFFS
					+ "&success=tariff_edited");
			return Page.REDIRECTED;
		} catch (ValidationErrorException e) {
			req.setAttribute("tariffValidateErrors", e.getErrors());
		} catch (TariffServiceException e) {
			LOG.warn("A service error occurred while editing the tariff.");
			LOG.error("Unable to edit tariff due to service error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_modify_tariff"));
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while editing the tariff.");
			LOG.error("Unable to edit tariff due to unexpected error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_modify_tariff"));
		}
		if (form != null) {
			req.setAttribute("tariffForm", form);
		}
		return new ViewTariffsCommand().execute(req, resp);
	}

}
