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
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffForm;
import com.epam.util.AppContext;
import com.epam.util.Validator;

/**
 * 
 * AddTariffCommand is a class that implements the Command interface. It is used
 * to add a new tariff in the system by the administrator.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class AddTariffCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(AddTariffCommand.class);

	/**
	 * 
	 * execute method takes two parameters, HttpServletRequest req and
	 * HttpServletResponse resp, which are used to add a new tariff in the
	 * persistence layer. The method retrieves the data from the request and
	 * validates it, after successful validation it calls the addTariff method of
	 * TariffService to add a new tariff.
	 * 
	 * @param req  the HttpServletRequest request
	 * @param resp the HttpServletResponse response
	 * @return the result of the executed command as a string(forward path).
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		TariffForm form = null;
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		try {
			form = new TariffForm(req.getParameter("name"), Integer.parseInt(req.getParameter("paymentPeriod")),
					new BigDecimal(req.getParameter("rate")),
					Service.getServiceByString(req.getParameter("serviceSelected")), req.getParameter("description"));
			Validator.validateTariffForm(form, rs);
			AppContext.getInstance().getTariffService().addTariff(form);
			LOG.info("Tariff {} with rate ${}/{} day(s) successfully added.", form.getName(), form.getRate(),
					form.getPaymentPeriod());
			resp.sendRedirect(
					req.getContextPath() + "/controller?action=" + CommandNames.VIEW_TARIFFS + "&success=tariff_added");
			return Page.REDIRECTED;
		} catch (ValidationErrorException e) {
			req.setAttribute("errorMessages", e.getErrors());
		} catch (TariffServiceException e) {
			LOG.warn("An error occurred while adding a tariff.");
			LOG.error("Unable to add tariff due to service error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_add_tariff"));
		} catch (Exception e) {
			LOG.warn("An error occurred while adding a tariff.");
			LOG.error("Unable to add tariff due to unexpected error error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_add_tariff"));
		}
		if (form != null)
			req.setAttribute("tariffForm", form);
		return new OpenAddTariffCommand().execute(req, resp);
	}

}
