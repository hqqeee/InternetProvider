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
import com.epam.controller.command.common.ViewTariffsCommand;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.TariffService;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffForm;
import com.epam.util.AppContext;

class EditTariffCommandTest {

	@Mock
	HttpServletRequest req = mock(HttpServletRequest.class);

	@Mock
	HttpServletResponse resp = mock(HttpServletResponse.class);

	@Mock
	TariffService tariffService = mock(TariffService.class);

	@Mock
	AppContext appContext = mock(AppContext.class);

	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Mockito.when(req.getAttribute("locale")).thenReturn(new Locale("en"));
	}
	
	@Test
	public void testValidationError() {
		Mockito.when(req.getParameter("name")).thenReturn(null);
		Mockito.when(req.getParameter("paymentPeriod")).thenReturn("14");
		Mockito.when(req.getParameter("rate")).thenReturn("12");
		Mockito.when(req.getParameter("serviceSelected")).thenReturn("INTERNET");
		Mockito.when(req.getParameter("description")).thenReturn(null);
		Mockito.when(req.getParameter("tariffId")).thenReturn("1");
		assertEquals(new ViewTariffsCommand().execute(req, resp), new EditTariffCommand().execute(req, resp));
	}

	@Test
	void testEditTariffTariffServiceException() throws TariffServiceException, ValidationErrorException {
		Mockito.when(req.getParameter("name")).thenReturn("name");
		Mockito.when(req.getParameter("paymentPeriod")).thenReturn("14");
		Mockito.when(req.getParameter("rate")).thenReturn("12");
		Mockito.when(req.getParameter("serviceSelected")).thenReturn("INTERNET");
		Mockito.when(req.getParameter("description")).thenReturn("desc");
		Mockito.when(req.getParameter("tariffId")).thenReturn("1");
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			TariffForm tariffForm = new TariffForm("name", Integer.parseInt("14"), new BigDecimal("12"),
					Service.INTERNET, "desc");
			doThrow(TariffServiceException.class).when(tariffService).editTariff(tariffForm, 1);
			assertEquals(new ViewTariffsCommand().execute(req, resp), new EditTariffCommand().execute(req, resp));
		}
	}

	
	@Test
	void testEditTariffException() {
		Mockito.when(req.getParameter("name")).thenReturn(null);
		Mockito.when(req.getParameter("paymentPeriod")).thenReturn("14");
		Mockito.when(req.getParameter("rate")).thenReturn("12asd");
		Mockito.when(req.getParameter("serviceSelected")).thenReturn("INTERNET");
		Mockito.when(req.getParameter("description")).thenReturn(null);
		Mockito.when(req.getParameter("tariffId")).thenReturn("1");
		assertEquals(new ViewTariffsCommand().execute(req, resp), new EditTariffCommand().execute(req, resp));
	}
	
	@Test
	void testEditTariffSuccess() {
		Mockito.when(req.getParameter("name")).thenReturn("name");
		Mockito.when(req.getParameter("paymentPeriod")).thenReturn("14");
		Mockito.when(req.getParameter("rate")).thenReturn("12");
		Mockito.when(req.getParameter("serviceSelected")).thenReturn("INTERNET");
		Mockito.when(req.getParameter("description")).thenReturn("desc");
		Mockito.when(req.getParameter("tariffId")).thenReturn("1");
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			assertEquals(Page.REDIRECTED, new EditTariffCommand().execute(req, resp));
		}
	}
}
