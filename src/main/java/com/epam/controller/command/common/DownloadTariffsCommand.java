package com.epam.controller.command.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.TariffService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DownloadTariffsCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/pdf");
		resp.setHeader("content-disposition", "attachment; filename=tariffs.pdf");


		try {
			List<Tariff> tariffs = ((TariffService) req.getServletContext().getAttribute("tariffService"))
					.getAllTariff(Integer.parseInt(req.getParameter("serviceId")));
			try (OutputStream out = resp.getOutputStream()) {
				Document document = new Document();

				PdfWriter.getInstance(document, out);
				document.open();
				document.add(new Paragraph("Tariff list"));
				document.add(generatePDFTableWithTariffs(tariffs));
				document.add(new Paragraph("Telecom"));
				document.close();

			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TariffServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private PdfPTable generatePDFTableWithTariffs(List<Tariff> tariffs) {
		PdfPTable table = new PdfPTable(3);
		PdfPCell nameCell = new PdfPCell(new Paragraph("Name"));
		PdfPCell descCell = new PdfPCell(new Paragraph("Description"));
		PdfPCell priceCell = new PdfPCell(new Paragraph("Price"));

		table.addCell(nameCell);
		table.addCell(descCell);
		table.addCell(priceCell);
		for (Tariff tariff : tariffs) {
			nameCell = new PdfPCell(new Phrase(tariff.getName()));
			descCell = new PdfPCell(new Phrase(tariff.getDescription()));
			priceCell = new PdfPCell(new Phrase(tariff.getRate().toString() + " per " + tariff.getPaymentPeriod() + " days."));
			table.addCell(nameCell);
			table.addCell(descCell);
			table.addCell(priceCell);
		}
		return table;
	}

}
