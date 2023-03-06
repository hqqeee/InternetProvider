package com.epam.controller.command.subscriber;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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

/**
 * The class is responsible for disabling the selected tariff for a subscriber.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class DisableTariffCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(DisableTariffCommand.class);

	/**
	 * Method execute the disable tariff operation. The method retrieves the
	 * tariffId and userId from the request, then calls the removeTariffFromUser
	 * method from the UserService to remove the tariff from user's account. If
	 * everything goes well, the method sends a redirect response to the view active
	 * tariffs page.
	 * 
	 * @param req  HttpServletRequest object
	 * @param resp HttpServletResponse object
	 * @return a string representation of the logical next page.
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		try {
			int tariffId = Integer.parseInt(req.getParameter("tariffId"));
			int userId = ((UserDTO) req.getSession().getAttribute("loggedUser")).getId();
			AppContext appContext = AppContext.getInstance();
			appContext.getUserService().removeTariffFromUser(userId, tariffId);
			resp.sendRedirect(req.getContextPath() + "/controller?action=" + CommandNames.VIEW_ACTIVE_TARIFFS
					+ "&success=disable_tariff");
			LOG.info("User(id = {}) disabled tariff(id = {}).", userId, tariffId);
			if (appContext.getUserService().getUserStatus(userId)) {
				List<TariffDTO> usersUnpaidTariffs = appContext.getTariffService().getUnpaidTariffs(userId);
				appContext.getUserService().chargeUserForTariffsUsing(userId, usersUnpaidTariffs);
				appContext.getUserService().changeUserStatus(true, userId);
				LOG.info("User(id = {}) has been unblocked due to disconnection of the tariff.", userId);
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
		req.setAttribute("errorMessages", rs.getString("error.something_went_wrong"));
		return new ViewActiveTariffsCommand().execute(req, resp);
	}

}
