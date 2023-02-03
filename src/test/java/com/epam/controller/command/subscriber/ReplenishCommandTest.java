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
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.TariffService;
import com.epam.services.TransactionService;
import com.epam.services.UserService;
import com.epam.services.dto.Role;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

class ReplenishCommandTest {

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
	void testReplenish() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("amount")).thenReturn("10");
			Mockito.when(req.getSession()).thenReturn(session);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			assertEquals(Page.REDIRECTED, new ReplenishCommand().execute(req, resp));
		}
	}

	@Test
	void testReplenishUserServiceException() throws UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(appContext.getTransactionService()).thenReturn(transactionService);
			Mockito.when(req.getParameter("amount")).thenReturn("10");
			Mockito.when(req.getSession()).thenReturn(session);
			loggedUser.setBlocked(true);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			doThrow(UserServiceException.class).when(userService).getUserStatus(1);
			assertEquals(new ViewAccountCommand().execute(req, resp), new ReplenishCommand().execute(req, resp));
		}
	}

	@Test
	void testReplenishNegativeBalance()
			throws UserServiceException, NegativeUserBalanceException, TariffServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(appContext.getTransactionService()).thenReturn(transactionService);
			Mockito.when(req.getParameter("amount")).thenReturn("10");
			Mockito.when(req.getSession()).thenReturn(session);
			loggedUser.setBlocked(true);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(userService.getUserStatus(1)).thenReturn(true);
			List<TariffDTO> usersUnpaidTariffs = new ArrayList<>();
			Mockito.when(tariffService.getUnpaidTariffs(1)).thenReturn(usersUnpaidTariffs);
			doThrow(NegativeUserBalanceException.class).when(userService).chargeUserForTariffsUsing(1,
					usersUnpaidTariffs);
			assertEquals(Page.REDIRECTED, new ReplenishCommand().execute(req, resp));
		}
	}

	@Test
	void testReplenishNegativeAmount() {
		Mockito.when(req.getParameter("amount")).thenReturn("-10");
		assertEquals(new ViewAccountCommand().execute(req, resp), new ReplenishCommand().execute(req, resp));
	}

	@Test
	void testReplenishNegativeException() {
		Mockito.when(req.getParameter("amount")).thenReturn("-1asdf0");
		assertEquals(new ViewAccountCommand().execute(req, resp), new ReplenishCommand().execute(req, resp));
	}

	@Test
	void testReplenishReplenishAndUnblock() throws TariffServiceException, UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(appContext.getTransactionService()).thenReturn(transactionService);
			Mockito.when(req.getParameter("amount")).thenReturn("10");
			Mockito.when(req.getSession()).thenReturn(session);
			loggedUser.setBlocked(true);
			Mockito.when((UserDTO) session.getAttribute("loggedUser")).thenReturn(loggedUser);
			Mockito.when(userService.getUserStatus(1)).thenReturn(true);
			List<TariffDTO> usersUnpaidTariffs = new ArrayList<>();
			Mockito.when(tariffService.getUnpaidTariffs(1)).thenReturn(usersUnpaidTariffs);
			assertEquals(Page.REDIRECTED, new ReplenishCommand().execute(req, resp));
		}
	}

}
