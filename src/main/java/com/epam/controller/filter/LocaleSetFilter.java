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

@WebFilter(filterName = "LocaleSetFilter")
public class LocaleSetFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		Locale locale = new Locale("en");
		if(cookies != null) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equals("lang")) {
					locale = new Locale(cookie.getValue()) ;
				}
			}
		}
		request.setAttribute("locale", locale);
		chain.doFilter(request, response);
	}

}
