package com.epam.controller.command.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.epam.controller.command.Page;
import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.TransactionService;
import com.epam.services.UserService;
import com.epam.util.AppContext;

class ViewSubscriberAccountCommandTest {


	@Mock
	HttpServletRequest req = mock(HttpServletRequest.class);

	@Mock
	HttpServletResponse resp = mock(HttpServletResponse.class);


	@Mock
	TransactionService transactionService = mock(TransactionService.class);
	
	@Mock
	UserService userService = mock(UserService.class);
	
	@Mock
	AppContext appContext = mock(AppContext.class);

	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Mockito.when(req.getAttribute("locale")).thenReturn(new Locale("en"));
	}
	
	@Test
	void testViewSubscriberAccountNoTransactionFoundException() throws NoTransactionsFoundException, TransactionServiceException {
		Mockito.when(req.getParameter("page")).thenReturn("1");
		Mockito.when(req.getParameter("userId")).thenReturn("3");
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getTransactionService()).thenReturn(transactionService);
			doThrow(NoTransactionsFoundException.class).when(transactionService).getUserTransaction(3,1,20);
			assertEquals(Page.ACCOUNT_PAGE, new ViewSubscriberAccountCommand().execute(req, resp));
		}
	}
	
	@Test
	void testViewSubscriberAccountTransactionServiceException() throws NoTransactionsFoundException, TransactionServiceException {
		Mockito.when(req.getParameter("userId")).thenReturn("3");
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getTransactionService()).thenReturn(transactionService);
			doThrow(TransactionServiceException.class).when(transactionService).getUserTransaction(3,1,20);
			assertEquals(Page.ACCOUNT_PAGE, new ViewSubscriberAccountCommand().execute(req, resp));
		}
	}
	
	@Test
	void testViewSubscriberAccountException() {
		Mockito.when(req.getParameter("userId")).thenReturn("asd");
		assertEquals(Page.ACCOUNT_PAGE, new ViewSubscriberAccountCommand().execute(req, resp));
	}
	
	@Test
	void testViewSubscriberAccountSuccess() {
		Mockito.when(req.getParameter("userId")).thenReturn("1");
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getTransactionService()).thenReturn(transactionService);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			assertEquals(Page.ACCOUNT_PAGE, new ViewSubscriberAccountCommand().execute(req, resp));
		}
	}

}
