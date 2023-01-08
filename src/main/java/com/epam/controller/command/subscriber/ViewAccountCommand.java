package com.epam.controller.command.subscriber;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.command.Command;
import com.epam.controller.command.Page;
import com.epam.dataaccess.entity.Transaction;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.TransactionService;
import com.epam.services.UserService;

public class ViewAccountCommand implements Command {

	private static final int RECORDS_PER_PAGE = 20;

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		TransactionService transactionService = (TransactionService) req.getServletContext()
				.getAttribute("transactionService");
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {
			// e.printStackTrace();
		}

		try {
			int userId = ((User) req.getSession().getAttribute("loggedUser")).getId();
			req.setAttribute("userBalance", ((UserService) req.getServletContext()
					.getAttribute("userService")).getUserBalance(userId));
			req.setAttribute("numberOfPages",
					Math.ceil(transactionService.getUsersTransactionNumber(userId) * 1.0 / RECORDS_PER_PAGE));
			List<Transaction> transactions = transactionService.getUserTransaction(userId, currentPage,
					RECORDS_PER_PAGE);
			req.setAttribute("transactionsToDisplay", transactions);
			req.setAttribute("page", currentPage);
			if(((UserService) req.getServletContext()
					.getAttribute("userService")).getUserStatus(userId)) {
				req.setAttribute("errorMessages", "You cannot use our services since you have not enoght money on your account. Please replenish account.");
			}
		} catch (NoTransactionsFoundException e) {
			req.setAttribute("noTransactionFount", "Payment history is empty");
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
