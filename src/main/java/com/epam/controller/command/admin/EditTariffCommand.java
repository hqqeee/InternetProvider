package com.epam.controller.command.admin;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.common.ViewTariffsCommand;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.TariffService;
import com.epam.services.forms.TariffForm;

public class EditTariffCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		TariffForm form = null;
		try {
			
			form = new TariffForm(req.getParameter("name"), new BigDecimal(req.getParameter("price")), Integer.parseInt(req.getParameter("serviceIdNew")),req.getParameter("description"));
			((TariffService) req.getServletContext().getAttribute("tariffService")).editTariff(form, 
					Integer.parseInt(req.getParameter("tariffId")));
			req.setAttribute("successMessage", "Tariff successfully edited." );
		} catch (ValidationErrorException e) {
			if(form != null) {req.setAttribute("tariffForm", form);}
			req.setAttribute("tariffValidateErrors", e.getErrors());
		} catch (TariffServiceException e) {
			req.setAttribute("errorMessages", "Cannot add user. Something went wrong. Try again later.");
		} catch (Exception e) {
			req.setAttribute("errorMessages", "Cannot add user. Something went wrong. Try again later or report bag.");
		}
		
		return new ViewTariffsCommand().execute(req, resp);
	}

}
