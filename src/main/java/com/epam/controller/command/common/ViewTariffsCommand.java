package com.epam.controller.command.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.TariffServiceException;
import com.epam.util.AppContext;
import com.epam.util.SortingOrder;
import com.epam.services.TariffService;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;

public class ViewTariffsCommand implements Command {

	private final Logger logger = LogManager.getLogger(ViewTariffsCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		int currentPage;
		try {
			currentPage = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {
			currentPage = 1;
		}
		Service service = Service.ALL;
		String serviceReq = req.getParameter("service");
		if(serviceReq != null && !serviceReq.isBlank()) {
			System.out.println(serviceReq);
			service = Service.getServiceByString(serviceReq.toUpperCase());
		}
		
		SortingOrder activeSortingOrder;
		try {
			if (req.getParameter("sortingOrder").toUpperCase().equals("ASC")) {
				activeSortingOrder = SortingOrder.ASC;
			} else {
				activeSortingOrder = SortingOrder.DESC;
			}
		} catch (Exception e) {
			activeSortingOrder = SortingOrder.DEFAULT;
		}
		String activeSortingField;
		activeSortingField = req.getParameter("sortingField");
		if (activeSortingField == null || activeSortingField.trim().isEmpty()) {
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
			List<TariffDTO> tariffs = tariffService.getTariffsForView(activeSortingField, activeSortingOrder,
					service, currentPage, currentRowNumber);
			req.setAttribute("tariffsToDisplay", tariffs);
			req.setAttribute("page", currentPage);
			req.setAttribute("service", service);
			req.setAttribute("sortingOrder", activeSortingOrder.getOrder());
			req.setAttribute("sortingField", activeSortingField);
			req.setAttribute("rowNumber", currentRowNumber);
			req.setAttribute("numberOfPages",
					(int) Math.ceil(tariffService.getTariffsCount(service) * 1.0 / currentRowNumber));
		} catch (TariffServiceException e) {
			logger.warn("An error occurred while loading tariffs view.");
			logger.error("Unable to load tariffs view due to service error.", e);
			req.setAttribute("errorMessages", "Unable to show tariffs. Please try again later.");
		} catch (Exception e) {
			logger.warn("An error occurred while loading tariffs view.");
			logger.error("Unable to load tariffs view due to unexpected error.", e);
			req.setAttribute("errorMessages", "Unable to show tariffs. Please try again later.");
		}

		return Page.VIEW_TARIFF_PAGE;
	}

}
