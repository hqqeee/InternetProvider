package com.epam.controller.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * The LocaleSetFilter class is a Servlet filter that sets the locale based on
 * the 'lang' cookie. If the 'lang' cookie is not present, it sets the locale to
 * English by default.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
@WebFilter(filterName = "LocaleSetFilter")
public class LocaleSetFilter implements Filter {

	/**
	 * The doFilter method sets the locale based on the 'lang' cookie in the HTTP
	 * request. If the 'lang' cookie is not present, it sets the locale to English
	 * by default.
	 *
	 * @param request  The ServletRequest object that contains the client's request.
	 * @param response The ServletResponse object that will contain the filter's
	 *                 response.
	 * @param chain    The FilterChain object representing the filter chain on which
	 *                 the filter is being applied.
	 *
	 * @throws IOException      If an I/O error occurs during this filter's
	 *                          execution.
	 * @throws ServletException If a ServletException error occurs during this
	 *                          filter's execution.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Cookie[] cookies = ((HttpServletRequest) request).getCookies();
		Locale locale = new Locale("en");
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("lang")) {
					locale = new Locale(cookie.getValue());
				}
			}
		}
		request.setAttribute("locale", locale);
		chain.doFilter(request, response);
	}

}
