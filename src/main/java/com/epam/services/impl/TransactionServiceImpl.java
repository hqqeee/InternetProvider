package com.epam.services.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.dao.DAOException;
import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.TransactionService;
import com.epam.services.dto.TransactionDTO;

public class TransactionServiceImpl implements TransactionService{
	
	private DAOFactory daoFactory;
	private TransactionServiceImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public List<TransactionDTO> getUserTransaction(int userId, int page, int recordsPerPage) throws NoTransactionsFoundException, TransactionServiceException {
		try {
			List<Transaction> transactions = new ArrayList<>();
			transactions = daoFactory.getTransactionDAO().getUserTransactionForView(userId, (page - 1)*recordsPerPage, recordsPerPage);
			if(transactions.isEmpty()) {
				throw new NoTransactionsFoundException("No transaction found.");
			}
			List<TransactionDTO> transactionDTOs = new ArrayList<>();
			transactions.forEach(t -> transactionDTOs.add(convertUserToTransactionDTO(t)));
			return transactionDTOs;
		} catch(DAOException e) {
			throw new TransactionServiceException("Cannot get user transaction userID = " + userId + " page " + page + " recordsPerPage " + recordsPerPage,e);
		}
	}

	@Override
	public int getUsersTransactionNumber(int userId) throws TransactionServiceException {
		try {
			int numberOfTransaction = daoFactory.getTransactionDAO().getNumberOfUserTransaction(userId);
			return numberOfTransaction;
		} catch(DAOException e) {
			throw new TransactionServiceException("Cannot get number of transaction for user with id = " + userId + ".",e);
		}
	}
	
	private TransactionDTO convertUserToTransactionDTO(Transaction transaction) {
		return new TransactionDTO(
				transaction.getId(),
				new Date(transaction.getTimestamp().getTime()),
				transaction.getAmount(),
				transaction.getDescription()
				);
	}

}
