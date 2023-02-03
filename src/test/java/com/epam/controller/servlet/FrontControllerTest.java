package com.epam.controller.servlet;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import com.epam.controller.command.Command;
import com.epam.controller.command.CommandFactory;
import com.epam.controller.command.Page;
import com.epam.exception.controller.CommandNotFoundException;

import net.bytebuddy.description.type.TypeDescription.Generic;

class FrontControllerTest {

	private FrontController frontController;

	@Mock
	private HttpServletRequest request = mock(HttpServletRequest.class);

	@Mock
	private HttpServletResponse response = mock(HttpServletResponse.class);

	@Mock
	private ServletContext servletContext = mock(ServletContext.class);

	@Mock
	private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

	@Mock
	private Command command = mock(Command.class);

	@BeforeEach
	public void setUp() {

		frontController = new FrontController();
	}

	@Test
	public void testDoGet() throws ServletException, IOException, CommandNotFoundException {
		when(request.getParameter("action")).thenReturn("someAction");
		try (MockedStatic<CommandFactory> commandFacotryMock = mockStatic(CommandFactory.class)) {
			CommandFactory commandFactory = mock(CommandFactory.class);
			commandFacotryMock.when(() -> CommandFactory.getInstance()).thenReturn(commandFactory);
			when(commandFactory.getCommand("someAction")).thenReturn(command);
			when(command.execute(request, response)).thenReturn("/somePage.jsp");
			when(request.getRequestDispatcher("/somePage.jsp")).thenReturn(requestDispatcher);
			doNothing().when(requestDispatcher).forward(request, response);
			frontController.doGet(request, response);
			verify(request).getParameter("action");
			verify(commandFactory).getCommand("someAction");
			verify(command).execute(request, response);
			verify(request).getRequestDispatcher("/somePage.jsp");
			verify(requestDispatcher).forward(request, response);
		}
	}
	
	@Test
	public void testDoPost() throws CommandNotFoundException, ServletException, IOException {
		when(request.getParameter("action")).thenReturn("someAction");
		try (MockedStatic<CommandFactory> commandFacotryMock = mockStatic(CommandFactory.class)) {
			CommandFactory commandFactory = mock(CommandFactory.class);
			commandFacotryMock.when(() -> CommandFactory.getInstance()).thenReturn(commandFactory);
			when(commandFactory.getCommand("someAction")).thenThrow(CommandNotFoundException.class);
			when(request.getRequestDispatcher(Page.HOME_PAGE)).thenReturn(requestDispatcher);
			doNothing().when(requestDispatcher).forward(request, response);
			frontController.doPost(request, response);
			verify(request).setAttribute("errorMessages", "You cannot access this page.");
		}
	}

}
