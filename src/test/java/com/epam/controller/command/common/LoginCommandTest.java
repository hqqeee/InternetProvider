package com.epam.controller.command.common;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.epam.controller.command.Page;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.TariffService;
import com.epam.services.TransactionService;
import com.epam.services.UserService;
import com.epam.util.AppContext;

class LoginCommandTest {

	@Mock
	HttpServletRequest req = mock(HttpServletRequest.class);

	@Mock
	HttpServletResponse resp = mock(HttpServletResponse.class);

	@Mock
	UserService userService = mock(UserService.class);

	@Mock
	TariffService tariffService = mock(TariffService.class);

	@Mock
	TransactionService transactionService = mock(TransactionService.class);

	@Mock
	AppContext appContext = mock(AppContext.class);

	@Mock
	HttpSession session = mock(HttpSession.class);


	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Mockito.when(req.getAttribute("locale")).thenReturn(new Locale("en"));
	}
	
	@Test
	void testLogin() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when(req.getParameter("login")).thenReturn("login");
			Mockito.when(req.getParameter("password")).thenReturn("password");
			assertEquals(Page.REDIRECTED, new LoginCommand().execute(req, resp));
		}
	}
	
	@Test
	void testLoginUserNotFoundException() throws UserNotFoundException, UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when(req.getParameter("login")).thenReturn("login");
			Mockito.when(req.getParameter("password")).thenReturn("password");
			doThrow(UserNotFoundException.class).when(userService).login("login", "password");
			assertEquals(Page.HOME_PAGE, new LoginCommand().execute(req, resp));
		}
	}

	@Test
	void testLoginUserServiceException() throws UserNotFoundException, UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when(req.getParameter("login")).thenReturn("login");
			Mockito.when(req.getParameter("password")).thenReturn("password");
			doThrow(UserServiceException.class).when(userService).login("login", "password");
			assertEquals(Page.HOME_PAGE, new LoginCommand().execute(req, resp));
		}
	}
	
	@Test
	void testLoginException() throws UserNotFoundException, UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when(req.getParameter("login")).thenReturn(null);
			Mockito.when(req.getParameter("password")).thenReturn("password");
			doThrow(UserServiceException.class).when(userService).login("login", "password");
			assertEquals(Page.HOME_PAGE, new LoginCommand().execute(req, resp));
		}
	}
}
