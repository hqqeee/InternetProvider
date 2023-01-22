package com.epam.controller.command.subscriber;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.CommandNames;
import com.epam.controller.command.Page;
import com.epam.services.dto.UserDTO;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.dto.TariffDTO;
import com.epam.util.AppContext;

public class DisableTariffCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(DisableTariffCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		try {
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			int userId = ((UserDTO) req.getSession().getAttribute("loggedUser")).getId();
			AppContext appContext = AppContext.getInstance();
			appContext.getUserService().removeTariffFromUser(userId, tariffId);
			req.setAttribute("successMessage", "Tariff removed seccessfully.");
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.VIEW_ACTIVE_TARIFFS
					+ "&success=disable_tariff");
			LOG.info("User(id = " + userId + ") disabled tariff(id = " + tariffId + ").");
			if (appContext.getUserService().getUserStatus(userId)) {
				List<TariffDTO> usersUnpaidTariffs = appContext.getTariffService().getUnpaidTariffs(userId);
				appContext.getUserService().chargeUserForTariffsUsing(userId, usersUnpaidTariffs);
				appContext.getUserService().changeUserStatus(true, userId);
				LOG.info("User(id = " + userId + ") has been unblocked due to disconnection of the tariff.");
			}
			return Page.REDIRECTED;
		} catch (NegativeUserBalanceException e) {
			return Page.REDIRECTED;
		} catch (UserServiceException | TariffServiceException e) {
			LOG.warn("A service error occurred while disabling tariff.");
			LOG.error("Unable to disable tariff due to service error.", e);
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while disabling tariff.");
			LOG.error("Unable to disable tariff due to unexpected error.", e);
		}
		req.setAttribute("errorMessages", "Something went wrong. Please try again.");
		return new ViewActiveTariffsCommand().execute(req, resp);
	}

}
