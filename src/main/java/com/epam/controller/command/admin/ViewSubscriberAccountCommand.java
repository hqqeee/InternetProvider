package com.epam.controller.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.TransactionService;
import com.epam.util.AppContext;

public class ViewSubscriberAccountCommand implements Command{

	private static final int RECORDS_PER_PAGE = 20;
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		TransactionService transactionService = AppContext.getInstance().getTransactionService();
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {
			// e.printStackTrace();
		}

		try {
			int userId = Integer.parseInt(req.getParameter("userId"));
			req.setAttribute("userId", req.getParameter("userId"));
			req.setAttribute("numberOfPages",
					Math.ceil(transactionService.getUsersTransactionNumber(userId) * 1.0 / RECORDS_PER_PAGE));
			List<Transaction> transactions = transactionService.getUserTransaction(userId, currentPage,
					RECORDS_PER_PAGE);
			req.setAttribute("transactionsToDisplay", transactions);
			req.setAttribute("page", currentPage);
			req.setAttribute("userBalance", AppContext.getInstance().getUserService().getUserBalance(userId));
		} catch (NoTransactionsFoundException e) {
			req.setAttribute("noTransactionFound", "Payment history is empty");
		} catch (TransactionServiceException e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Unable to load payment history. Try again later.");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("errorMessages", "Unable to load payment history. Try again later.");
		}
		return Page.ACCOUNT_PAGE;
	}

}
