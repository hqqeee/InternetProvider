package com.epam.services;

import java.util.List;

import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.dto.TransactionDTO;

public interface TransactionService {
	public List<TransactionDTO> getUserTransaction(int userId, int page, int recordsPerPage) throws NoTransactionsFoundException, TransactionServiceException;
	public int getUsersTransactionNumber(int userId) throws TransactionServiceException;
}