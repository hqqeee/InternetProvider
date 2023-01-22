package com.epam.controller.command.subscriber;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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
import com.epam.exception.services.PasswordNotMatchException;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.TariffService;
import com.epam.services.TransactionService;
import com.epam.services.UserService;
import com.epam.services.dto.Role;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

class ChangePasswordCommandTest {

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
	
	private UserDTO loggedUser = new UserDTO(1, false, "login", BigDecimal.TEN, "firstName", "lastName",
			"email@asd.asd", "City", "Address", Role.SUBSCRIBER);
	
	@Test
	void testChangePassword() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(req.getParameter("currentPassword")).thenReturn("12345678");
			Mockito.when(req.getParameter("newPassword")).thenReturn("password");
			assertEquals(Page.REDIRECTED, new ChangePasswordCommand().execute(req, resp));
		}
	}

	@Test
	void testChangePasswordValidationError() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(req.getParameter("currentPassword")).thenReturn("12345678");
			Mockito.when(req.getParameter("newPassword")).thenReturn("pasord");
			assertEquals( new ViewProfileCommand().execute(req, resp), new ChangePasswordCommand().execute(req, resp));
		}
	}
	
	@Test
	void testChangePasswordNotMatch() throws UserServiceException, UserNotFoundException, PasswordNotMatchException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(req.getParameter("currentPassword")).thenReturn("12345678");
			Mockito.when(req.getParameter("newPassword")).thenReturn("password");
			doThrow(PasswordNotMatchException.class).when(userService).changePassword(1, "12345678", "password");
			assertEquals( new ViewProfileCommand().execute(req, resp), new ChangePasswordCommand().execute(req, resp));
		}
	}
	
	@Test
	void testChangePasswordUserServiceException() throws UserServiceException, UserNotFoundException, PasswordNotMatchException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(req.getParameter("currentPassword")).thenReturn("12345678");
			Mockito.when(req.getParameter("newPassword")).thenReturn("password");
			doThrow(UserServiceException.class).when(userService).changePassword(1, "12345678", "password");
			assertEquals( new ViewProfileCommand().execute(req, resp), new ChangePasswordCommand().execute(req, resp));
		}
	}
	
	@Test
	void testChangePasswordNullUser() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(null);
			Mockito.when(req.getParameter("currentPassword")).thenReturn("12345678");
			Mockito.when(req.getParameter("newPassword")).thenReturn("password");
			assertEquals( new ViewProfileCommand().execute(req, resp), new ChangePasswordCommand().execute(req, resp));
		}
	}
}
