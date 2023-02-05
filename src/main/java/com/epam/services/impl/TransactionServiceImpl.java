package com.epam.services.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.dao.DAOException;
import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.TransactionService;
import com.epam.services.dto.TransactionDTO;

/**
 * 
 * Transaction Service implementation with DAOFactory. It contains methods to
 * interact with the DAO and has some business logic.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class TransactionServiceImpl implements TransactionService {

	private DAOFactory daoFactory;

	/**
	 * A constructor that assigns a DAO Factory.
	 * 
	 * @param daoFactory DAO Factory impl.
	 */
	private TransactionServiceImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 * This method returns some(recordsPerPage) transactions of the user with
	 * specific id. With pagination.
	 * 
	 * @param userId         the user ID to get the transaction.
	 * @param page           page to return.
	 * @param recordsPerPage number of records to get.
	 * @return A list of TransactionsDTO of the user.
	 * @throws NoTransactionsFoundException is thrown when no transaction was found.
	 * @throws TransactionServiceException  is thrown when something wrong happens
	 *                                      in the DAO layer.
	 */
	@Override
	public List<TransactionDTO> getUserTransaction(int userId, int page, int recordsPerPage)
			throws NoTransactionsFoundException, TransactionServiceException {
		try {
			List<Transaction> transactions = new ArrayList<>();
			transactions = daoFactory.getTransactionDAO().getUserTransactionForView(userId, (page - 1) * recordsPerPage,
					recordsPerPage);
			if (transactions.isEmpty()) {
				throw new NoTransactionsFoundException("No transaction found.");
			}
			List<TransactionDTO> transactionDTOs = new ArrayList<>();
			transactions.forEach(t -> transactionDTOs.add(convertTransactionToTransactionDTO(t)));
			return transactionDTOs;
		} catch (DAOException e) {
			throw new TransactionServiceException("Cannot get user transaction userID = " + userId + " page " + page
					+ " recordsPerPage " + recordsPerPage, e);
		}
	}

	/**
	 * This method returns the count of all user transactions.
	 * 
	 * @param userId id of the user to get count of transaction.
	 * @return count of all user transactions.
	 * @throws TransactionServiceException is thrown when something wrong happens in
	 *                                     the DAO layer.
	 */
	@Override
	public int getUsersTransactionNumber(int userId) throws TransactionServiceException {
		try {
			int numberOfTransaction = daoFactory.getTransactionDAO().getNumberOfUserTransaction(userId);
			return numberOfTransaction;
		} catch (DAOException e) {
			throw new TransactionServiceException("Cannot get number of transaction for user with id = " + userId + ".",
					e);
		}
	}

	/**
	 * This meth takes Transaction(DAO) entity and converts it to TransactionDTO
	 * 
	 * @param transaction DAO transaction entity.
	 * @return Converted TransactionDTO
	 */
	protected TransactionDTO convertTransactionToTransactionDTO(Transaction transaction) {
		return new TransactionDTO(transaction.getId(), new Date(transaction.getTimestamp().getTime()),
				transaction.getAmount(), transaction.getDescription());
	}

}
