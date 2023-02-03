package com.epam.controller.filter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class EncodingFilterTest {

	private EncodingFilter filter = new EncodingFilter();
	
	@Mock
	private HttpServletRequest request = mock(HttpServletRequest.class);
	
	@Mock
	private HttpServletResponse response = mock(HttpServletResponse.class);
	
	@Mock
	private FilterChain chain = mock(FilterChain.class);
	
	@Test
	public void doFilterTest() throws IOException, ServletException {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(writer);
		
		filter.doFilter(request, response, chain);
		
		verify(request).setCharacterEncoding("UTF-8");
		verify(response).setContentType("text/html; charset=UTF-8");
		verify(response).setCharacterEncoding("UTF-8");
		verify(chain).doFilter(request, response);
		
	}
}
