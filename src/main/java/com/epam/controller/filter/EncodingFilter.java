package com.epam.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 
 * EncodingFilter class is used to set the character encoding of the request and
 * response. The class sets the character encoding to UTF-8 for both request and
 * response and sets the content type to text/html.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
	/**
	 * This method sets the character encoding of the request and response to UTF-8,
	 * and sets the content type of the response to `text/html` with a charset of
	 * UTF-8. The request and response are then passed along to the next filter in
	 * the chain.
	 *
	 * @param request  The ServletRequest object that contains the client's request.
	 * @param response The ServletResponse object that will contain the filter's
	 *                 response.
	 * @param chain    The FilterChain object that provides access to the next
	 *                 filter in the chain.
	 * 
	 * @throws IOException      If an input or output exception occurs.
	 * @throws ServletException If a servlet exception occurs.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		chain.doFilter(request, response);
	}

}
