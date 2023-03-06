package com.epam.controller.command.common;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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

/**
 * The ViewTariffsCommand class implements the Command interface and represents
 * the implementation of the view tariffs command. It retrieves the information
 * required to display the tariffs on the view tariffs page, processes it, and
 * sets it as request attributes.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class ViewTariffsCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(ViewTariffsCommand.class);

	/**
	 * The method retrieves the necessary information for displaying tariffs,
	 * processes it, and sets it as request attributes.
	 *
	 * @param HttpServletRequest req, HttpServletResponse resp
	 * @return String the URL of the view tariffs page
	 * @throws Exception
	 */
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
		if (serviceReq != null && !serviceReq.isBlank()) {
			service = Service.getServiceByString(serviceReq.toUpperCase());
		}

		SortingOrder activeSortingOrder;
		try {
			if (req.getParameter("sortingOrder").equalsIgnoreCase("ASC")) {
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
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		try {
			TariffService tariffService = AppContext.getInstance().getTariffService();
			List<TariffDTO> tariffs = tariffService.getTariffsForView(activeSortingField, activeSortingOrder, service,
					currentPage, currentRowNumber);
			req.setAttribute("tariffsToDisplay", tariffs);
			req.setAttribute("page", currentPage);
			req.setAttribute("service", service);
			req.setAttribute("sortingOrder", activeSortingOrder.getOrder());
			req.setAttribute("sortingField", activeSortingField);
			req.setAttribute("rowNumber", currentRowNumber);
			req.setAttribute("numberOfPages",
					(int) Math.ceil(tariffService.getTariffsCount(service) * 1.0 / currentRowNumber));
		} catch (TariffServiceException e) {
			LOG.warn("An error occurred while loading tariffs view.");
			LOG.error("Unable to load tariffs view due to service error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_load_tariffs"));
		} catch (Exception e) {
			LOG.warn("An error occurred while loading tariffs view.");
			LOG.error("Unable to load tariffs view due to unexpected error.", e);
			req.setAttribute("errorMessages", rs.getString("error.unable_to_load_tariffs"));
		}

		return Page.VIEW_TARIFF_PAGE;
	}

}
