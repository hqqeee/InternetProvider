package com.epam.controller.command.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.epam.controller.command.Page;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;

class ChangeUserBalanceCommandTest {

	@Mock
	HttpServletRequest req = mock(HttpServletRequest.class);
	
	@Mock
	HttpServletResponse resp = mock(HttpServletResponse.class);
	
	@Mock
	UserService userService = mock(UserService.class);

	@Mock
	AppContext appContext = mock(AppContext.class);

	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Mockito.when(req.getAttribute("locale")).thenReturn(new Locale("en"));
		
	}
	
	@Test
	void testNullBalance() {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("amount")).thenReturn(null);
			assertEquals(new AdminMenuCommand().execute(req, resp), new ChangeUserBalanceCommand().execute(req, resp));
		}
	}

	
	@Test
	void testNegativeDiffrenece() {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("amount")).thenReturn("-1.24");
			assertEquals(new AdminMenuCommand().execute(req, resp), new ChangeUserBalanceCommand().execute(req, resp));
		}
	}
	
	@Test
	void testZeroDiffrence() {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("amount")).thenReturn("0");
			Mockito.when(req.getParameter("description"))
			.thenReturn("sadfasdfaasdasdasndnasdnasndasndqweqas  sd_asdfasdf +=sd=f-=adfjaksdfhjquwehriuqwiubzoixucvbuioasbdgfouiasdfasdgafsdgadfgasdfgasdgasgasgasdgasas");
			assertEquals(new AdminMenuCommand().execute(req, resp), new ChangeUserBalanceCommand().execute(req, resp));
		}
	}
	
	@Test
	void testTooLongDescription() {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("amount")).thenReturn("12");
			Mockito.when(req.getParameter("description"))
			.thenReturn("sadfasdfaasdasdasndnasdnasndasndqweqas  sd_asdfasdf +=sd=f-=adfjaksdfhjquwehriuqwiubzoixucvbuioasbdgfouiasdfasdgafsdgadfgasdfgasdgasgasgasdgasas");
			assertEquals(new AdminMenuCommand().execute(req, resp), new ChangeUserBalanceCommand().execute(req, resp));
		}
	}

	@Test
	void testEmptyDescription() {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("amount")).thenReturn("12");
			Mockito.when(req.getParameter("description"))
			.thenReturn("");
			assertEquals(new AdminMenuCommand().execute(req, resp), new ChangeUserBalanceCommand().execute(req, resp));
		}
	}
	
	@Test
	void testNegativeUserBalance() throws UserServiceException, NegativeUserBalanceException {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("amount")).thenReturn("10");
			Mockito.when(req.getParameter("description")).thenReturn("desc");
			Mockito.when(req.getParameter("balanceChangeType")).thenReturn("withdraw");
			Mockito.when(req.getParameter("userId")).thenReturn("1");
			BigDecimal bd = new BigDecimal("10");
			bd.setScale(2);
			bd = bd.negate();
			doThrow(NegativeUserBalanceException.class).when(userService).changeUserBalance(1, bd, "desc");
			assertEquals(new AdminMenuCommand().execute(req, resp), new ChangeUserBalanceCommand().execute(req, resp));
		}
	}
	
	@Test
	void testUserServiceException() throws UserServiceException, NegativeUserBalanceException {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("amount")).thenReturn("10");
			Mockito.when(req.getParameter("description")).thenReturn("desc");
			Mockito.when(req.getParameter("balanceChangeType")).thenReturn("withdraw");
			Mockito.when(req.getParameter("userId")).thenReturn("1");
			BigDecimal bd = new BigDecimal("10");
			bd.setScale(2);
			bd = bd.negate();
			doThrow(UserServiceException.class).when(userService).changeUserBalance(1, bd, "desc");
			assertEquals(new AdminMenuCommand().execute(req, resp), new ChangeUserBalanceCommand().execute(req, resp));
		}
	}
	
	@Test
	void testSuccessChangeBalance() throws UserServiceException, NegativeUserBalanceException {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("amount")).thenReturn("10");
			Mockito.when(req.getParameter("description")).thenReturn("desc");
			Mockito.when(req.getParameter("balanceChangeType")).thenReturn("topup");
			Mockito.when(req.getParameter("userId")).thenReturn("1");
			BigDecimal bd = new BigDecimal("10");
			bd.setScale(2);
			bd = bd.negate();
			assertEquals(Page.REDIRECTED, new ChangeUserBalanceCommand().execute(req, resp));
		}
	}
}
