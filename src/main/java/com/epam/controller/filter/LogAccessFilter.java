package com.epam.controller.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.services.dto.UserDTO;

/**
 * LogAccessFilter is a servlet filter that logs the access of a user.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
@WebFilter(filterName = "LogAccessFilter")
public class LogAccessFilter implements Filter {
	/**
	 * LOG is the logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger(LogAccessFilter.class);

	/**
	 * The method implements the doFilter method in the Filter interface. It logs
	 * the access of a user, including the user's login, IP address, page, and
	 * access time in milliseconds.
	 * 
	 * @param request  the ServletRequest object
	 * @param response the ServletResponse object
	 * @param chain    the FilterChain object
	 * @throws IOException      if an I/O error occurs
	 * @throws ServletException if a servlet error occurs
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Date dateInitRequest = new Date();
		String ip = ((HttpServletRequest) request).getRemoteAddr();
		String login = "Unlogged user";
		UserDTO user = (UserDTO) ((HttpServletRequest) request).getSession().getAttribute("loggedUser");
		if (user != null) {
			login = user.getLogin();
		}
		String page = ((HttpServletRequest) request).getServletPath() + "/"
				+ ((HttpServletRequest) request).getQueryString();
		chain.doFilter(request, response);

		Date dateEndRequest = new Date();
		LOG.info("Login: " + login + ", IP: " + ip + " Page: " + page + " Access time : "
				+ Long.toString(dateEndRequest.getTime() - dateInitRequest.getTime()) + " ms");
	}

}
