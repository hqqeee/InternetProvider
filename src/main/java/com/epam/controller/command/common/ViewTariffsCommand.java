package com.epam.controller.command.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.services.TariffServiceException;
import com.epam.util.AppContext;
import com.epam.util.SortingOrder;
import com.epam.services.TariffService;

public class ViewTariffsCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		int currentPage;
		try {
			currentPage = Integer.parseInt(req.getParameter("page"));
		} catch(Exception e) {
			currentPage = 1;
		}
		int currentServiceId;
		try {
			currentServiceId = Integer.parseInt(req.getParameter("serviceId"));
		} catch(Exception e) {
			currentServiceId = 0;
		}
		SortingOrder activeSortingOrder;
		try {
			if(req.getParameter("sortingOrder").toUpperCase().equals("ASC")) {
				activeSortingOrder = SortingOrder.ASC;
			} else {
				activeSortingOrder = SortingOrder.DESC;
			}
		} catch (Exception e) {
			activeSortingOrder = SortingOrder.DEFAULT;
		}
		String activeSortingField;
		activeSortingField = req.getParameter("sortingField");
		if(activeSortingField == null || activeSortingField.trim().isEmpty()) {
			activeSortingField = "name";
		}
		int currentRowNumber;
		try {
			currentRowNumber = Integer.parseInt(req.getParameter("rowNumber"));
		} catch (Exception e) {
			currentRowNumber = 5;
		}
		try {
			TariffService tariffService = AppContext.getInstance().getTariffService();
			List<Tariff> tariffs = tariffService.getTariffsForView(activeSortingField, activeSortingOrder, currentServiceId, currentPage, currentRowNumber);
			req.setAttribute("tariffsToDisplay", tariffs);
			req.setAttribute("page", currentPage);
			req.setAttribute("serviceId", currentServiceId);
			req.setAttribute("sortingOrder", activeSortingOrder.getOrder());
			req.setAttribute("sortingField", activeSortingField);
			req.setAttribute("rowNumber", currentRowNumber);
			req.setAttribute("numberOfPages", (int)Math.ceil(tariffService.getTariffsCount(currentServiceId) * 1.0/currentRowNumber));
		} catch (TariffServiceException e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Unable to show tariffs. Please try again later.");
		}
		

		return Page.VIEW_TARIFF_PAGE;
	}

}
