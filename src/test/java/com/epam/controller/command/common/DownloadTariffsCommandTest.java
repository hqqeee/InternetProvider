package com.epam.controller.command.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.epam.controller.command.Page;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.TariffService;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.util.AppContext;
import com.itextpdf.text.Document;

class DownloadTariffsCommandTest {
	@Mock
	private HttpServletRequest req = mock(HttpServletRequest.class);

	@Mock
	private HttpServletResponse resp = mock(HttpServletResponse.class);

	@Mock
	private AppContext appContext = mock(AppContext.class);

	@Mock
	private TariffService tariffService = mock(TariffService.class);

	@Mock
	private Document mockedDocument = mock(Document.class);

	@BeforeEach
	public void setUp() {
		Mockito.when(req.getAttribute("locale")).thenReturn(new Locale("en"));
	}

	@Test
	void testExecuteSuccess() throws Exception {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class);
				MockedStatic<DownloadTariffsCommand> staticCommand = Mockito.mockStatic(DownloadTariffsCommand.class)) {
			staticCommand.when(() -> DownloadTariffsCommand.getDocument()).thenReturn(mockedDocument);
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			List<TariffDTO> tariffs = new ArrayList<>();
			tariffs.add(new TariffDTO(1,"name","desc", 14, BigDecimal.TEN, Service.CABLE_TV));
			when(appContext.getTariffService()).thenReturn(tariffService);
			when(req.getParameter("service")).thenReturn("all");
			when(appContext.getTariffService().getAllTariff(Service.ALL)).thenReturn(tariffs);

			// Act
			DownloadTariffsCommand command = new DownloadTariffsCommand();
			String result = command.execute(req, resp);

			// Assert
			verify(resp).setContentType("application/pdf");
			verify(resp).setHeader("content-disposition", "attachment; filename=tariffs.pdf");
			assertEquals(Page.REDIRECTED, result);
		}
	}

	@Test
	void testExecuteFailure() throws Exception {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class);
				MockedStatic<DownloadTariffsCommand> staticCommand = Mockito.mockStatic(DownloadTariffsCommand.class)) {
			staticCommand.when(() -> DownloadTariffsCommand.getDocument()).thenReturn(mockedDocument);
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			when(appContext.getTariffService()).thenReturn(tariffService);
			// Arrange
			when(req.getParameter("service")).thenReturn("all");
			when(AppContext.getInstance().getTariffService().getAllTariff(Service.ALL))
					.thenThrow(TariffServiceException.class);
			// Act
			DownloadTariffsCommand command = new DownloadTariffsCommand();
			String result = command.execute(req, resp);
			// Assert
			assertEquals(new ViewTariffsCommand().execute(req, resp), result);
		}
	}
}