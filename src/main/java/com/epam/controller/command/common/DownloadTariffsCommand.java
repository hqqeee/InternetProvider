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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * Class DownloadTariffsCommand generates a PDF document that contains a list of
 * tariffs for a selected service. The document can be downloaded by the user.
 * If an error occurs during the generation of the PDF document or during its
 * download, the user will be redirected to the tariff view page with an error
 * message.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class DownloadTariffsCommand implements Command {
	/*
	 * A Logger instance to log error messages.
	 */
	private static final Logger LOG = LogManager.getLogger(DownloadTariffsCommand.class);

	/**
	 * The execute method retrieves the tariffs of the selected service, creates a
	 * PDF document with them and sends the document to the user for downloading. If
	 * an error occurs, the user will be redirected to the tariff view page with an
	 * error message.
	 * 
	 * @param req  The HttpServletRequest object that contains the request the
	 *             client has made of the servlet.
	 * @param resp The HttpServletResponse object that contains the response the
	 *             servlet sends to the client.
	 * @return a String indicating the next page to be displayed.
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/pdf");
		resp.setHeader("content-disposition", "attachment; filename=tariffs.pdf");
		ResourceBundle rb = ResourceBundle.getBundle("lang", (Locale) req.getAttribute("locale"));
		try {
			String serviceReq = req.getParameter("service");
			Service service = Service.ALL;
			if (!serviceReq.isBlank()) {
				service = Service.getServiceByString(serviceReq.toUpperCase());
			}
			List<TariffDTO> tariffs = AppContext.getInstance().getTariffService().getAllTariff(service);
			try (OutputStream out = resp.getOutputStream()) {
				Document document = getDocument();
				Font simpleFont = FontFactory.getFont("/fonts/RobotoMono-VariableFont_wght.ttf", "cp1251",
						BaseFont.EMBEDDED, 10);
				Font headerFont = FontFactory.getFont("/fonts/RobotoMono-VariableFont_wght.ttf", "cp1251",
						BaseFont.EMBEDDED, 16);
				PdfWriter.getInstance(document, out);
				document.open();
				Paragraph paragraph = new Paragraph(rb.getString("pdf_tariff_list"), headerFont);
				paragraph.setAlignment(Element.ALIGN_CENTER);
				paragraph.setSpacingAfter((float) 4.0);
				document.add(paragraph);
				document.add(generatePDFTableWithTariffs(tariffs, simpleFont, rb));
				paragraph = new Paragraph("Telecom", headerFont);
				paragraph.setAlignment(Element.ALIGN_CENTER);
				document.add(paragraph);
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

		req.setAttribute("errorMessages", rb.getString("error.unable_to_download_tariffs"));
		return new ViewTariffsCommand().execute(req, resp);
	}

	/**
	 * 
	 * The getDocument method creates and returns a Document object from iText
	 * library.
	 * 
	 * @return a Document object from iText library.
	 */
	protected static Document getDocument() {
		return new Document();
	}

	/**
	 * The generatePDFTableWithTariffs method creates a table with tariffs data and
	 * returns it as a PdfPTable object.
	 * 
	 * @param tariffs The list of TariffDTO objects that contains the tariffs data.
	 * @param font    The font that will be used in the table.
	 * @return a PdfPTable object with tariffs data.
	 */
	private PdfPTable generatePDFTableWithTariffs(List<TariffDTO> tariffs, Font font, ResourceBundle rb) {
		PdfPTable table = new PdfPTable(3);
		PdfPCell nameCell = new PdfPCell(new Paragraph(rb.getString("pdf_tariff_name"), font));
		PdfPCell descCell = new PdfPCell(new Paragraph(rb.getString("pdf_tariff_description"), font));
		PdfPCell priceCell = new PdfPCell(new Paragraph(rb.getString("pdf_tariff_rate"), font));

		table.addCell(nameCell);
		table.addCell(descCell);
		table.addCell(priceCell);
		for (TariffDTO tariff : tariffs) {
			nameCell = new PdfPCell(new Phrase(tariff.getName(), font));
			descCell = new PdfPCell(new Phrase(tariff.getDescription(), font));
			priceCell = new PdfPCell(new Phrase(tariff.getRate().toString() + " " + rb.getString("pdf_tariff_per") + " "
					+ tariff.getPaymentPeriod() + " " + rb.getString("pdf_tariff_days") + ".", font));
			table.addCell(nameCell);
			table.addCell(descCell);
			table.addCell(priceCell);
		}
		return table;
	}

}
