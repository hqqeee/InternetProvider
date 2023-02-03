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
import com.epam.controller.command.common.ViewTariffsCommand;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.TariffService;
import com.epam.util.AppContext;

class RemoveTariffCommandTest {

	@Mock
	HttpServletRequest req = mock(HttpServletRequest.class);

	@Mock
	HttpServletResponse resp = mock(HttpServletResponse.class);

	@Mock
	TariffService tariffService = mock(TariffService.class);

	@Mock
	AppContext appContext = mock(AppContext.class);

	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Mockito.when(req.getAttribute("locale")).thenReturn(new Locale("en"));
	}
	
	
	@Test
	void testRemoveTariffCommand() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("tariffId")).thenReturn("1");
			assertEquals(Page.REDIRECTED, new RemoveTariffCommand().execute(req, resp));
		}
	}
	
	@Test
	void testRemoveTariffCommandTariffServiceException() throws TariffServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("tariffId")).thenReturn("1");
			doThrow(TariffServiceException.class).when(tariffService).removeTariff(1);
			assertEquals(new ViewTariffsCommand().execute(req, resp), new RemoveTariffCommand().execute(req, resp));
		}
	}
	
	@Test
	void testRemoveTariffCommandException() throws TariffServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			Mockito.when(req.getParameter("tariffId")).thenReturn("1asdfg");
			assertEquals(new ViewTariffsCommand().execute(req, resp), new RemoveTariffCommand().execute(req, resp));
		}
	}

}
