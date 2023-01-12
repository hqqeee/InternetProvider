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

import com.epam.dataaccess.entity.User;

@WebFilter(filterName = "LogAccessFilter")
public class LogAccessFilter implements Filter {

	private final Logger logger = LogManager.getLogger(LogAccessFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Date dateInitRequest = new Date();

		String ip = ((HttpServletRequest) request).getRemoteAddr();
		String login = "Unlogged user";
		User user = (User) ((HttpServletRequest) request).getSession().getAttribute("loggedUser");
		if (user != null) {
			login = user.getLogin();
		}
		String page = ((HttpServletRequest) request).getServletPath() + "/"+
				((HttpServletRequest) request).getQueryString();
		chain.doFilter(request, response);

		Date dateEndRequest = new Date();

		logger.info("Login: " + login + ", IP: " + ip + " Page: " + page + " Access time : "
				+ Long.toString(dateEndRequest.getTime() - dateInitRequest.getTime()) + " ms");
	}

}
