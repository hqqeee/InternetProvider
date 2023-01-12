package com.epam.controller.command.admin;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.common.ViewTariffsCommand;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.forms.TariffForm;
import com.epam.util.AppContext;

public class AddTariffCommand implements Command {

	private final Logger logger = LogManager.getLogger(AddTariffCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		TariffForm form = null;
		try {
			form = new TariffForm(
					req.getParameter("name"),
					Integer.parseInt(req.getParameter("paymentPeriod")),
					new BigDecimal(req.getParameter("rate")),
					Integer.parseInt(req.getParameter("serviceIdNew")),
					req.getParameter("description"));
			AppContext.getInstance().getTariffService().addTariff(form);
			req.setAttribute("successMessage", "Tariff successfully added." );
			logger.info("Tariff " + form.getName() + " with rate $" + form.getRate() + "/" + form.getPaymentPeriod() + " day(s) successfully added.");
			return new ViewTariffsCommand().execute(req, resp);
		} catch (ValidationErrorException e) {
			req.setAttribute("errorMessages", e.getErrors());
		} catch (TariffServiceException e) {
			logger.warn("An error occurred while adding a tariff.");
			logger.error("Unable to add tariff due to service error.", e);
			req.setAttribute("errorMessages", "Cannot add tariff. Something went wrong. Try again later.");
		} catch (Exception e) {
			logger.warn("An error occurred while adding a tariff.");
			logger.error("Unable to add tariff due to unexpected error error.", e);
			req.setAttribute("errorMessages", "Cannot add tariff. Something went wrong. Try again later or report bag.");
		}
		if(form != null) req.setAttribute("tariffForm", form);
		return new OpenAddTariffCommand().execute(req, resp);
	}

}
