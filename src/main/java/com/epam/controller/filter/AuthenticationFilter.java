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

@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {

	private final Logger logger = LogManager.getLogger(AuthenticationFilter.class);

	private static final String[] ADMIN_COMMANDS = { REGISER_USER, ADMIN_MENU, OPEN_USER_REGISTRATION,
			VIEW_SUBSCRIBER_PROFILE, VIEW_SUBSCRIBER_TARIFFS, VIEW_SUBSCRIBER_ACCOUNT, CHANGE_USER_BALANCE,
			REMOVE_TARIFF, REMOVE_USER, CHANGE_USER_STATUS, EDIT_TARIFF, ADD_TARIFF, OPEN_ADD_TARIFF };
	private static final String[] SUBSCRIBER_COMMANDS = { VIEW_PROFILE, VIEW_ACCOUNT, VIEW_ACTIVE_TARIFFS,
			CHANGE_PASSWORD, REPLENISH, CONNECT_TARIFF, DISABLE_TARIFF };
	private static final String[] COMMON_COMMANDS = { LOGIN, LOGOUT, VIEW_TARIFFS, DOWNLOAD_TARIFFS, CHANGE_LANGUAGE, RESET_PASSWORD};

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

	private boolean checkAccess(HttpServletRequest req) {
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
