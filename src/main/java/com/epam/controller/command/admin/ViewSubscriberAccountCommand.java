package com.epam.controller.command.admin;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.TransactionService;
import com.epam.services.dto.TransactionDTO;
import com.epam.util.AppContext;

public class ViewSubscriberAccountCommand implements Command{

	private static final int RECORDS_PER_PAGE = 20;
	private static final Logger LOG = LogManager.getLogger(ViewSubscriberAccountCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		ResourceBundle rs = ResourceBundle.getBundle("lang", (Locale)req.getAttribute("locale"));
		try {
			TransactionService transactionService = AppContext.getInstance().getTransactionService();
			String currentPageReq = req.getParameter("page");
			int currentPage = 1;
			if(currentPageReq != null && !currentPageReq.isBlank()) {
				currentPage = Integer.parseInt(currentPageReq);
			}
			int userId = Integer.parseInt(req.getParameter("userId"));
			req.setAttribute("userId", req.getParameter("userId"));
			req.setAttribute("numberOfPages",
					Math.ceil(transactionService.getUsersTransactionNumber(userId) * 1.0 / RECORDS_PER_PAGE));
			List<TransactionDTO> transactions = transactionService.getUserTransaction(userId, currentPage,
					RECORDS_PER_PAGE);
			req.setAttribute("transactionsToDisplay", transactions);
			req.setAttribute("page", currentPage);
			req.setAttribute("userBalance", AppContext.getInstance().getUserService().getUserBalance(userId));
			return Page.ACCOUNT_PAGE;
		} catch (NoTransactionsFoundException e) {
			req.setAttribute("noTransactionFound", rs.getString("error.payment_history_empty"));
		} catch (TransactionServiceException e) {
			LOG.warn("A service error occurred while loading account info.");
			LOG.error("Unable to load account info due to service error.", e);
		} catch (Exception e) {
			LOG.warn("An unexpected error occurred while loading account info.");
			LOG.error("Unable to load account info due to unexpected error.", e);
		}
		req.setAttribute("errorMessages", rs.getString("error.unable_to_load_account_info"));
		return Page.ACCOUNT_PAGE;
	}

}
