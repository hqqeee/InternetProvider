package com.epam.services;

import java.util.List;

import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.dto.TransactionDTO;

/**
 * 
 * Transaction Service interface.
 * It contains methods to interact with the DAO and has some business logic.
 * 
 * @author ruslan
 *
 */

public interface TransactionService {
	/**
	 * This method returns some(recordsPerPage) transactions of the user with specific id. With pagination.
	 * @param userId the user ID to get the transaction.
	 * @param page page to return.
	 * @param recordsPerPage number of records to get.
	 * @return A list of TransactionsDTO of the user.
	 * @throws NoTransactionsFoundException is thrown when no transaction was found.
	 * @throws TransactionServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public List<TransactionDTO> getUserTransaction(int userId, int page, int recordsPerPage) throws NoTransactionsFoundException, TransactionServiceException;
	/**
	 * This method returns the count of all user transactions.
	 * @param userId id of the user to get count of transaction.
	 * @return count of all user transactions.
	 * @throws TransactionServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public int getUsersTransactionNumber(int userId) throws TransactionServiceException;
}