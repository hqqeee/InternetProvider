package com.epam.dataaccess.dao;

import java.math.BigDecimal;
import java.util.List;

import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.dao.DAOException;

/**
 * Transaction DAO interface. Has methods needed by services to access persistence layer.
 * @author ruslan
 *
 */
public interface TransactionDAO extends DAO<Transaction> {
	
	/**
	 * This method modify User table's balance field and add transaction about these changes.
	 * @param userId The ID of the user whose balance needs to be changed.
	 * @param difference Difference in balance to be made.
	 * @param description Description of the transaction.
	 * @return Rows changed.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	public int changeUserBalance(int userId, BigDecimal difference,String description) throws DAOException;
	
	/**
	 * Return all transaction for the specific user ID.
	 * @param userIdThe ID of the user whose transaction to be get.
	 * @param offset number of record to skip.
	 * @param recordsNumber number maximum records to get.
	 * @return List of Transaction with specififc user ID.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	public List<Transaction> getUserTransactionForView(int userId, int offset, int recordsNumber) throws DAOException;
	
	/**
	 * Return number of transaction of the user.
	 * @param userId ID of the user whose number of transaction to get.
	 * @return number of transactions.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	public int getNumberOfUserTransaction(int userId) throws DAOException;
	
	/**
	 * This method modify User table's balance field by decreasing it by rate of the Tariff(tariffId) 
	 * and add transaction about these changes.
	 * @param userId The ID of the user whose balance needs to be decreased.
	 * @param tariffId The ID of the Tariff for which to charge.
	 * @param description Description of the transaction to be added.
	 * @return Rows changed.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	public int chargeUserForTariffUsing(int userId, int tariffId, String description) throws DAOException;
}
