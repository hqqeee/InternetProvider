package com.epam.controller.command.admin;

import java.math.BigDecimal;

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

public class EditTariffCommand implements Command{

	private static final Logger LOG = LogManager.getLogger(EditTariffCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		TariffForm form = null;
		try {
			
			form = new TariffForm(
					req.getParameter("name"),
					Integer.parseInt(req.getParameter("paymentPeriod")),
					new BigDecimal(req.getParameter("rate")),
					Service.getServiceByString(req.getParameter("serviceSelected")),
					req.getParameter("description"));
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			AppContext.getInstance().getTariffService().editTariff(form, 
					tariffId);
			LOG.info("Tariff( " + tariffId + ") has been edited. " + form);
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.VIEW_TARIFFS + "&success=tariff_edited");
			return Page.REDIRECTED;
		} catch (ValidationErrorException e) {
			if(form != null) {req.setAttribute("tariffForm", form);}
			req.setAttribute("tariffValidateErrors", e.getErrors());
		} catch (TariffServiceException e) {
			LOG.warn("A service error occurred while editing the tariff.");
			LOG.error("Unable to edit tariff due to service error.", e);
			req.setAttribute("errorMessages", "Cannot modify tariff. Something went wrong. Try again later.");
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while editing the tariff.");
			LOG.error("Unable to edit tariff due to unexpected error.", e);
			req.setAttribute("errorMessages", "Cannot modify tariff. Something went wrong. Try again later or report bag.");
		}
		
		return new ViewTariffsCommand().execute(req, resp);
	}

}
