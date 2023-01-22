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
import com.epam.controller.command.common.ViewTariffsCommand;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.UserAlreadyHasTariffException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.TariffService;
import com.epam.services.TransactionService;
import com.epam.services.UserService;
import com.epam.services.dto.Role;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

class ConnectTariffCommandTest {


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
	void testConnectTariff() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(req.getParameter("tariffId")).thenReturn("1");
			assertEquals(Page.REDIRECTED, new ConnectTariffCommand().execute(req, resp));
		}
	}	
	
	@Test
	void testConnectTarifNegativeBalance() throws UserAlreadyHasTariffException, UserServiceException, NegativeUserBalanceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(req.getParameter("tariffId")).thenReturn("1");
			doThrow(NegativeUserBalanceException.class).when(userService).addTariffToUser(1, 1);
			assertEquals(new ViewTariffsCommand().execute(req, resp), new ConnectTariffCommand().execute(req, resp));
		}
	}
	
	@Test
	void testConnectTarifUserHasTariff() throws UserAlreadyHasTariffException, UserServiceException, NegativeUserBalanceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(req.getParameter("tariffId")).thenReturn("1");
			doThrow(UserAlreadyHasTariffException.class).when(userService).addTariffToUser(1, 1);
			assertEquals(new ViewTariffsCommand().execute(req, resp), new ConnectTariffCommand().execute(req, resp));
		}
	}
	
	@Test
	void testConnectTariffUserServiceException() throws UserAlreadyHasTariffException, UserServiceException, NegativeUserBalanceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(req.getParameter("tariffId")).thenReturn("1");
			doThrow(UserServiceException.class).when(userService).addTariffToUser(1, 1);
			assertEquals(new ViewTariffsCommand().execute(req, resp), new ConnectTariffCommand().execute(req, resp));
		}
	}
	
	@Test
	void testConnectTariffException() throws UserAlreadyHasTariffException, UserServiceException, NegativeUserBalanceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(req.getParameter("tariffId")).thenReturn("1asd");
			doThrow(UserServiceException.class).when(userService).addTariffToUser(1, 1);
			assertEquals(new ViewTariffsCommand().execute(req, resp), new ConnectTariffCommand().execute(req, resp));
		}
	}

}
