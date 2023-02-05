package com.epam.services;

import java.util.List;

import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.dto.TransactionDTO;

/**
 * 
 * Interface for the Transaction Service. Provides methods to interact with the
 * DAO and perform business logic.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public interface TransactionService {
	/**
	 * 
	 * Retrieves a specified number of transactions for a user with the given ID,
	 * with pagination.
	 * 
	 * @param userId         the ID of the user to retrieve transactions for
	 * @param page           the page number to retrieve
	 * @param recordsPerPage the number of records to retrieve per page
	 * @return a list of TransactionDTO objects for the user
	 * @throws NoTransactionsFoundException if no transactions were found for the
	 *                                      user
	 * @throws TransactionServiceException  if an error occurs in the DAO layer
	 */
	public List<TransactionDTO> getUserTransaction(int userId, int page, int recordsPerPage)
			throws NoTransactionsFoundException, TransactionServiceException;

	/**
	 * 
	 * Retrieves the number of transactions for a user with the given ID.
	 * 
	 * @param userId the ID of the user to retrieve the number of transactions for
	 * @return the number of transactions for the user
	 * @throws TransactionServiceException if an error occurs in the DAO layer
	 */
	public int getUsersTransactionNumber(int userId) throws TransactionServiceException;
}