package com.epam.controller.command.admin;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

/**
 * 
 * AdminMenuCommand is a Command class that handles displaying a list of
 * subscribers to an administrator.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 **/
public class AdminMenuCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(AdminMenuCommand.class);

	/**
	 * This method retrieves the subscribers from the UserService, sets the number
	 * of pages, current page, search field, and number of rows to be displayed to
	 * the request object, and then returns the ADMIN_MENU_PAGE.
	 * 
	 * @param req  HttpServletRequest instance that contains the request the client
	 *             has made of the servlet.
	 * @param resp HttpServletResponse instance that contains the response the
	 *             servlet sends to the client.
	 * @return ADMIN_MENU_PAGE.
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		int currentPage;
		String reqPageString = req.getParameter("page");
		if (reqPageString != null && !reqPageString.trim().isEmpty()) {
			try {
				currentPage = Integer.parseInt(reqPageString);
			} catch (NumberFormatException e) {
				currentPage = 1;
			}
		} else {
			currentPage = 1;
		}
		int currentRowNumber;
		String reqRowNumberString = req.getParameter("rowNumber");
		if (reqRowNumberString != null && !reqRowNumberString.trim().isEmpty()) {
			try {
				currentRowNumber = Integer.parseInt(reqRowNumberString);
			} catch (NumberFormatException e) {
				currentRowNumber = 5;
			}
		} else {
			currentRowNumber = 5;
		}
		String currentSearchField = req.getParameter("searchField");
		if (currentSearchField == null) {
			currentSearchField = "";
		}

		UserService userService = AppContext.getInstance().getUserService();
		int numberOfSubscriber;
		try {
			numberOfSubscriber = userService.getSubscribersNumber(currentSearchField);
			int numberOfPages = (int) Math.ceil(numberOfSubscriber * 1.0 / currentRowNumber);
			req.setAttribute("numberOfPages", numberOfPages);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("currentSearchField", currentSearchField);
			req.setAttribute("currentRowNumber", currentRowNumber);
			List<UserDTO> subscribers = userService.viewSubscribers(currentSearchField, currentPage, currentRowNumber);
			req.setAttribute("usersToDisplay", subscribers);
		} catch (UserServiceException e) {
			LOG.warn("An error occurred while loading user view.");
			LOG.error("Unable to load user view due to service error.", e);
			req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"))
					.getString("error.unable_to_load_users"));
		}

		return Page.ADMIN_MENU_PAGE;
	}

}
