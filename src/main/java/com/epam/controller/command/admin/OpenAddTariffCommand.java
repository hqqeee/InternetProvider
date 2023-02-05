package com.epam.controller.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;

/**
 * 
 * This class provides the implementation of the Command interface. It
 * is responsible for handling the request to open the page to add a new Tariff.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public class OpenAddTariffCommand implements Command {
	/**
	 * 
	 * Executes the OpenAddTariffCommand, which opens the page for adding a new
	 * tariff.
	 * 
	 * @param req  the HttpServletRequest object that contains the request the
	 *             client made of the servlet
	 * @param resp the HttpServletResponse object that contains the response the
	 *             servlet returns to the client
	 * @return a string representing the next page(add tariff page).
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		return Page.ADD_TARIFF_PAGE;
	}

}
