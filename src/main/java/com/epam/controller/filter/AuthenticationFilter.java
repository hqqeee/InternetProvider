package com.epam.controller.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.controller.command.CommandNames.*;

import com.epam.controller.command.Page;
import com.epam.services.dto.Role;
import com.epam.services.dto.UserDTO;

/**
 * The AuthenticationFilter class is a servlet filter that checks user's
 * authorization for accessing certain commands and pages.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {
	/**
	 * The logger is used to log messages for debugging and error reporting.
	 */
	private final Logger logger = LogManager.getLogger(AuthenticationFilter.class);
	/**
	 * An array of admin commands that only admin role users can access.
	 */
	private static final String[] ADMIN_COMMANDS = { REGISER_USER, ADMIN_MENU, OPEN_USER_REGISTRATION,
			VIEW_SUBSCRIBER_PROFILE, VIEW_SUBSCRIBER_TARIFFS, VIEW_SUBSCRIBER_ACCOUNT, CHANGE_USER_BALANCE,
			REMOVE_TARIFF, REMOVE_USER, CHANGE_USER_STATUS, EDIT_TARIFF, ADD_TARIFF, OPEN_ADD_TARIFF };
	/**
	 * An array of subscriber commands that only subscriber role users can access.
	 */
	private static final String[] SUBSCRIBER_COMMANDS = { VIEW_PROFILE, VIEW_ACCOUNT, VIEW_ACTIVE_TARIFFS,
			CHANGE_PASSWORD, REPLENISH, CONNECT_TARIFF, DISABLE_TARIFF };
	/**
	 * An array of common commands that both admin and subscriber role users can
	 * access.
	 */
	private static final String[] COMMON_COMMANDS = { LOGIN, LOGOUT, VIEW_TARIFFS, DOWNLOAD_TARIFFS, CHANGE_LANGUAGE,
			RESET_PASSWORD };

	/**
	 * This method implements the doFilter method of the Filter interface. It checks
	 * if the user has access to the command and forwards the request accordingly.
	 * 
	 * @param request  ServletRequest object that is used to pass data between
	 *                 servlets.
	 * @param response ServletResponse object that is used to pass data between
	 *                 servlets.
	 * @param chain    FilterChain object that is used to connect filters and
	 *                 servlets.
	 * 
	 * @throws IOException      if an input or output error occurs.
	 * @throws ServletException if a servlet error occurs.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (checkAccess(httpRequest)) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute("errorMessages", "You cannot access this page.");
			request.getRequestDispatcher(Page.HOME_PAGE).forward(request, response);
		}
	}

	/**
	 * Method to check if the current user has access to the requested page.
	 * 
	 * @param req - the HttpServletRequest object containing the user's request.
	 * @return true if the user has access to the page, false otherwise.
	 **/
	boolean checkAccess(HttpServletRequest req) {
		String action = req.getParameter("action");
		if (action == null || action.trim().isEmpty()) {
			logger.warn("An attempt to access empty action failed.");
			return false;
		}
		if (Arrays.stream(COMMON_COMMANDS).anyMatch(action::equals)) {
			return true;
		}
		UserDTO user = (UserDTO) req.getSession().getAttribute("loggedUser");
		if (user == null) {
			logger.warn("An attempt to access " + action + " failed.");
			return false;
		}
		if ((user.getRole() == Role.ADMIN && Arrays.stream(ADMIN_COMMANDS).anyMatch(action::equals))
				|| (user.getRole() == Role.SUBSCRIBER && Arrays.stream(SUBSCRIBER_COMMANDS).anyMatch(action::equals))) {
			return true;
		} else {
			logger.warn("An attempt to access " + action + " failed. User: " + user.getLogin() + " .");
			return false;
		}
	}

}
