package com.epam.controller.filter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.epam.services.dto.UserDTO;

class LogAccessFilterTest {

	@Mock
	private HttpServletRequest request = mock(HttpServletRequest.class);
	@Mock
	private HttpSession session = mock(HttpSession.class);
	@Mock
	private ServletResponse response = mock(ServletResponse.class);
	@Mock
	private FilterChain chain = mock(FilterChain.class);
	private LogAccessFilter logAccessFilter;
	private UserDTO userDTO;
	
	@BeforeEach
	public void setup() {
	    logAccessFilter = new LogAccessFilter();
	    userDTO = new UserDTO();
	    userDTO.setLogin("userLogin");
	    when(request.getSession()).thenReturn(session);
	    when(request.getServletContext()).thenReturn(mock(ServletContext.class));
	    when(request.getServletPath()).thenReturn("/path");
	    when(request.getQueryString()).thenReturn("query");
	    when(request.getRemoteAddr()).thenReturn("ip");
	}

	@Test
	void doFilter_LoggedUser_ShouldWriteInLoggerWithAccessTime() throws ServletException, IOException {
	    when(session.getAttribute("loggedUser")).thenReturn(userDTO);

	    logAccessFilter.doFilter(request, response, chain);

	    verify(chain).doFilter(request, response);
	}

	@Test
	void doFilter_UnloggedUser_ShouldWriteInLoggerWithAccessTime() throws ServletException, IOException {
	    when(session.getAttribute("loggedUser")).thenReturn(null);

	    logAccessFilter.doFilter(request, response, chain);

	    verify(chain).doFilter(request, response);
	}

}
