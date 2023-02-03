package com.epam.controller.command.subscriber;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.TariffService;
import com.epam.services.TransactionService;
import com.epam.services.UserService;
import com.epam.services.dto.Role;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

class DisableTariffCommandTest {


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

	private UserDTO loggedUser = new UserDTO(1, false, "login", BigDecimal.TEN, "firstName", "lastName",
			"email@asd.asd", "City", "Address", Role.SUBSCRIBER);
	
	
	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Mockito.when(req.getAttribute("locale")).thenReturn(new Locale("en"));
	}
	
	
	@Test
	void testDisableTariff() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("tariffId")).thenReturn("2");
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			assertEquals(Page.REDIRECTED, new DisableTariffCommand().execute(req, resp));
		}
	}
	
	@Test
	void testDisableTariffException() {
		Mockito.when(req.getParameter("tariffId")).thenReturn("2asdf");
		assertEquals(new ViewActiveTariffsCommand().execute(req, resp), new DisableTariffCommand().execute(req, resp));
	}
	
	@Test
	void testDisableTariffUserServiceException() throws UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("tariffId")).thenReturn("2");
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			doThrow(UserServiceException.class).when(userService).getUserStatus(1);
			assertEquals(new ViewActiveTariffsCommand().execute(req, resp), new DisableTariffCommand().execute(req, resp));
		}
	}
	
	@Test
	void testDisableTariffNegativeUserBalance() throws UserServiceException, NegativeUserBalanceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("tariffId")).thenReturn("2");
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(userService.getUserStatus(1)).thenReturn(true);
			List<TariffDTO> usersUnpaidTariffs = new ArrayList<>();
			doThrow(NegativeUserBalanceException.class).when(userService).chargeUserForTariffsUsing(1, usersUnpaidTariffs);
			assertEquals(Page.REDIRECTED, new DisableTariffCommand().execute(req, resp));
		}
	}
	
	@Test
	void testDisableTariffSuccess() throws UserServiceException, NegativeUserBalanceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("tariffId")).thenReturn("2");
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(userService.getUserStatus(1)).thenReturn(true);
			assertEquals(Page.REDIRECTED, new DisableTariffCommand().execute(req, resp));
		}
	}
}
