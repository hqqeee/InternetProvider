package com.epam.controller.command.subscriber;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

public class ViewActiveTariffsCommand implements Command{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Map<TariffDTO, Integer> tariffsWithDaysLeft = AppContext.getInstance().getTariffService()
					.getUsersTariffWithDaysUntilPayment(((UserDTO) req.getSession().getAttribute("loggedUser")).getId());
//			List<Tariff> tariffs = ((TariffService) req.getServletContext().getAttribute("tariffService"))
//					.getUsersTariff(((User) req.getSession().getAttribute("loggedUser")).getId());
			req.setAttribute("tariffsToDisplay", tariffsWithDaysLeft);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (TariffServiceException e) {
			e.printStackTrace();
		}
		return Page.ACTIVE_TARIFFS_PAGE;
	}

}
