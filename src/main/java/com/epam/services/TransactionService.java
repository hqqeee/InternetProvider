package com.epam.services;

import java.util.List;

import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;

public interface TransactionService {
	public List<Transaction> getUserTransaction(int userId, int page, int recordsPerPage) throws NoTransactionsFoundException, TransactionServiceException;
	public int getUsersTransactionNumber(int userId) throws TransactionServiceException;
}