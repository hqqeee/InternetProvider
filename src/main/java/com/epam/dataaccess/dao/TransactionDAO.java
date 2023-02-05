package com.epam.dataaccess.dao;

import java.math.BigDecimal;
import java.util.List;

import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.dao.DAOException;

/**
 * 
 * A DAO interface for transactions. Provides methods for services to access
 * persistence layer.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public interface TransactionDAO extends DAO<Transaction> {

	/**
	 * 
	 * Changes the balance of a user in the User table and adds a transaction entry
	 * for the changes made.
	 * 
	 * @param userId      The ID of the user whose balance will be modified.
	 * @param difference  The change to be made to the user's balance.
	 * @param description A description of the transaction.
	 * @return The number of rows changed.
	 * @throws DAOException If a SQLException occurs.
	 */
	public int changeUserBalance(int userId, BigDecimal difference, String description) throws DAOException;

	/**
	 * 
	 * Returns all transactions for a specific user.
	 * 
	 * @param userId        The ID of the user whose transactions are being
	 *                      retrieved.
	 * @param offset        The number of records to skip.
	 * @param recordsNumber The maximum number of records to retrieve.
	 * @return A list of transactions for the specified user.
	 * @throws DAOException If a SQLException occurs.
	 */
	public List<Transaction> getUserTransactionForView(int userId, int offset, int recordsNumber) throws DAOException;

	/**
	 * 
	 * Returns the number of transactions for a specific user.
	 * 
	 * @param userId The ID of the user whose number of transactions is being
	 *               retrieved.
	 * @return The number of transactions for the specified user.
	 * @throws DAOException If a SQLException occurs.
	 */
	public int getNumberOfUserTransaction(int userId) throws DAOException;

	/**
	 * 
	 * Decreases the balance of a user in the User table for a specific tariff and
	 * adds a transaction entry for the changes made.
	 * 
	 * @param userId      The ID of the user whose balance will be decreased.
	 * @param tariffId    The ID of the tariff for which the user will be charged.
	 * @param description A description of the transaction to be added.
	 * @return The number of rows changed.
	 * @throws DAOException If a SQLException occurs.
	 */
	public int chargeUserForTariffUsing(int userId, int tariffId, String description) throws DAOException;
}
