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

/**
 * 
 * ConnectTariffCommand is a class that implements the Command interface. It is
 * used to connect a tariff to the user. This class gets the user id and tariff
 * id from the request and tries to connect the tariff to the user by calling
 * the addTariffToUser method of the UserService. If the operation is
 * successful, the user will be redirected to the view tariffs page with a
 * success message. In case of exceptions, appropriate error messages will be
 * set as request attributes for display.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public class ConnectTariffCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(ConnectTariffCommand.class);

	/**
	 * 
	 * The method execute connects the selected tariff to the user. If the tariff
	 * was connected successful, the user will be redirected to the tariffs page. If
	 * there's not enough money on the user's account, an error message will be
	 * displayed. If the user already has the selected tariff, another error message
	 * will be displayed. If a service error occurs, the error message will be
	 * displayed. If an unexpected error occurs, an error message will be displayed.
	 * 
	 * @param req  HttpServletRequest request object.
	 * @param resp HttpServletResponse response object.
	 * @return A string representing the next page to display.
	 */
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
