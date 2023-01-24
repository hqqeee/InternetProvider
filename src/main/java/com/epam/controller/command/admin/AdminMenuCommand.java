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

public class AdminMenuCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(AdminMenuCommand.class);

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
		if(reqRowNumberString != null && !reqRowNumberString.trim().isEmpty()) {
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
			req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale)req.getAttribute("locale")).getString("error.unable_to_load_users"));
		}

		return Page.ADMIN_MENU_PAGE;
	}

}
