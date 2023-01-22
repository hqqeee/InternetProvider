package com.epam.controller.command.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.epam.controller.command.Page;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.TariffService;
import com.epam.services.UserService;
import com.epam.util.AppContext;

class ViewSubscriberTariffsCommandTest {


	@Mock
	HttpServletRequest req = mock(HttpServletRequest.class);
	
	@Mock
	HttpServletResponse resp = mock(HttpServletResponse.class);
	
	@Mock
	UserService userService = mock(UserService.class);
	
	@Mock
	TariffService tariffService = mock(TariffService.class);

	@Mock
	AppContext appContext = mock(AppContext.class);
	
	
	@Test
	void testViewSubscriberTariffs() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("userId")).thenReturn("1");
			assertEquals(Page.ACTIVE_TARIFFS_PAGE, new ViewSubscriberTariffsCommand().execute(req, resp));
		}
	}
	
	@Test
	void testViewSubscriberTariffsTariffServiceException() throws TariffServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("userId")).thenReturn("1");
			doThrow(TariffServiceException.class).when(tariffService).getUsersTariffWithDaysUntilPayment(1);
			assertEquals(new AdminMenuCommand().execute(req, resp), new ViewSubscriberTariffsCommand().execute(req, resp));
		}
	}
	
	@Test
	void testViewSubscriberTariffsException() throws TariffServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("userId")).thenReturn("asdf1");
			assertEquals(new AdminMenuCommand().execute(req, resp), new ViewSubscriberTariffsCommand().execute(req, resp));
		}
	}

}
