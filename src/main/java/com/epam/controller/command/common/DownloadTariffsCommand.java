package com.epam.controller.command.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.util.AppContext;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DownloadTariffsCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(DownloadTariffsCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/pdf");
		resp.setHeader("content-disposition", "attachment; filename=tariffs.pdf");

		try {
			String serviceReq = req.getParameter("service");
			Service service = Service.ALL;
			if (!serviceReq.isBlank()) {
				service = Service.getServiceByString(serviceReq.toUpperCase());
			}
			List<TariffDTO> tariffs = AppContext.getInstance().getTariffService().getAllTariff(service);
			try (OutputStream out = resp.getOutputStream()) {
				Document document = getDocument();
				Font font = FontFactory.getFont("/fonts/RobotoMono-VariableFont_wght.ttf", "cp1251", BaseFont.EMBEDDED,
						10);
				PdfWriter.getInstance(document, out);
				document.open();
				document.add(new Paragraph("Tariff list", font));
				document.add(generatePDFTableWithTariffs(tariffs, font));
				document.add(new Paragraph("Telecom", font));
				document.close();
				return Page.REDIRECTED;

			}
		} catch (DocumentException | IOException e) {
			LOG.warn("An error occurred while forming pdf document for downloading.");
			LOG.error("Unable to form pdf document due to error.", e);
		} catch (TariffServiceException e) {
			LOG.warn("A service error occurred while downloading pdf with tariffs.");
			LOG.error("Unable to upload tariffs pdf document due to service error.", e);
		} catch (Exception e) {
			LOG.warn("A unexpected error occurred while downloading pdf with tariffs.");
			LOG.error("Unable to upload tariffs pdf document due to unexpected error.", e);
		}
		req.setAttribute("errorMessages", ResourceBundle.getBundle("lang", (Locale)req.getAttribute("locale")).getString("error.unable_to_download_tariffs"));
		return new ViewTariffsCommand().execute(req, resp);
	}

	protected static Document getDocument() {
		return new Document();
	}

	private PdfPTable generatePDFTableWithTariffs(List<TariffDTO> tariffs, Font font) {
		PdfPTable table = new PdfPTable(3);
		PdfPCell nameCell = new PdfPCell(new Paragraph("Name", font));
		PdfPCell descCell = new PdfPCell(new Paragraph("Description", font));
		PdfPCell priceCell = new PdfPCell(new Paragraph("Price", font));

		table.addCell(nameCell);
		table.addCell(descCell);
		table.addCell(priceCell);
		for (TariffDTO tariff : tariffs) {
			nameCell = new PdfPCell(new Phrase(tariff.getName(), font));
			descCell = new PdfPCell(new Phrase(tariff.getDescription(), font));
			priceCell = new PdfPCell(
					new Phrase(tariff.getRate().toString() + " per " + tariff.getPaymentPeriod() + " days.", font));
			table.addCell(nameCell);
			table.addCell(descCell);
			table.addCell(priceCell);
		}
		return table;
	}


}
