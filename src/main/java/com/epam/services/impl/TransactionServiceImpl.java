package com.epam.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.dao.DAOException;
import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.TransactionService;

public class TransactionServiceImpl implements TransactionService{
	private TransactionDAO transactionDAO;
	
	public TransactionServiceImpl(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}
	
	@Override
	public List<Transaction> getUserTransaction(int userId, int page, int recordsPerPage) throws NoTransactionsFoundException, TransactionServiceException {
		try {
			List<Transaction> transactions = new ArrayList<>();
			transactions = transactionDAO.getUserTransactionForView(userId, (page - 1)*recordsPerPage, recordsPerPage);
			if(transactions.isEmpty()) {
				throw new NoTransactionsFoundException("No transaction found.");
			}
			return transactions;
		} catch(DAOException e) {
			throw new TransactionServiceException("Cannot get user transaction userID = " + userId + " page " + page + " recordsPerPage " + recordsPerPage,e);
		}
	}

	@Override
	public int getUsersTransactionNumber(int userId) throws TransactionServiceException {
		try {
			int numberOfTransaction = transactionDAO.getNumberOfUserTransaction(userId);
			return numberOfTransaction;
		} catch(DAOException e) {
			throw new TransactionServiceException("Cannot get number of transaction for user with id = " + userId + ".",e);
		}
	}

}
