package com.epam.controller.command.subscriber;

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
import com.epam.services.dto.UserDTO;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.UserAlreadyHasTariffException;
import com.epam.exception.services.UserServiceException;
import com.epam.util.AppContext;

public class ConnectTariffCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ConnectTariffCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		try {
			int userId = ((UserDTO) req.getSession().getAttribute("loggedUser")).getId();
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			AppContext.getInstance().getUserService().addTariffToUser(userId, tariffId);
			LOG.info("User(id = " + userId + ") added tariff(id = " + tariffId + ").");
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.VIEW_TARIFFS
					+ "&success=connect_tariff");
			return Page.REDIRECTED;
		} catch (NegativeUserBalanceException e) {
			req.setAttribute("errorMessages", rs.getString("error.not_enough_money"));
		} catch (UserAlreadyHasTariffException e) {
			req.setAttribute("errorMessages", rs.getString("error.tariff_already_connected"));
		} catch (UserServiceException e) {
			LOG.warn("A service error occurred while connecting tariff.");
			LOG.error("Unable to connect tariff due to service error.", e);
			req.setAttribute("errorMessages", "Something went wrong. Please try again.");
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while connecting tariff.");
			LOG.error("Unable to connect tariff due to unexpected error.", e);
			req.setAttribute("errorMessages", rs.getString("error.something_went_wrong"));
		}

		return new ViewTariffsCommand().execute(req, resp);
	}

}
