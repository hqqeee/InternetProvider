package com.epam.dataaccess.dao.mariadb.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.dao.mariadb.datasource.QueryBuilder;
import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.dao.DAODeleteException;
import com.epam.exception.dao.DAOException;
import com.epam.exception.dao.DAOInsertException;
import com.epam.exception.dao.DAOMappingException;
import com.epam.exception.dao.DAOReadException;
import com.epam.exception.dao.DAOUpdateException;

public class TransactionDAOMariaDB implements TransactionDAO {


	/**
	 * Return Transaction by its ID.
	 * 
	 * @param id id of the Transaction.
	 * @return Transaction entity with this id.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public Transaction get(int id) throws DAOException {
		Transaction transaction = null;
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_TRANSACTION_BY_ID)
				.setIntField(id).executeQuery()) {
			if (rs.next()) {
				transaction = getTransactionFromResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get transaction with id " + id + ".", e);
		}

		return transaction;
	}

	/**
	 * Return all Transaction entities.
	 * 
	 * @return All Transaction entities.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<Transaction> getAll() throws DAOException {
		List<Transaction> transactions = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_TRANSACTIONS)
				.executeQuery()) {
			while (rs.next()) {
				transactions.add(getTransactionFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all transactions.", e);
		}

		return transactions;
	}

	/**
	 * Insert new Transaction to the persistence layer.
	 * 
	 * @param tariff Transaction to add.
	 * @return 1 if inserted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int insert(Transaction transaction) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.ADD_TRANSACTION)
					.setIntField(transaction.getUserId()).setBigDecimalField(transaction.getAmount())
					.setStringField(transaction.getDescription()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOInsertException("Cannot insert transaction " + transaction + ".", e);
		}
	}

	protected QueryBuilder getQueryBuilder() throws SQLException {
		return new QueryBuilder();
	}

	/**
	 * Update Transaction in the persistence layer.
	 * 
	 * @param user Transaction to update.
	 * @return 1 if updated, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int update(Transaction transaction) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.UPDATE_TRANSACTION)
					.setIntField(transaction.getUserId()).setTimestamField(transaction.getTimestamp())
					.setBigDecimalField(transaction.getAmount()).setStringField(transaction.getDescription())
					.setIntField(transaction.getId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot update transaction " + transaction + ".", e);
		}
	}

	/**
	 * Delete Transaction from the persistence layer.
	 * 
	 * @param user Transaction to delete.
	 * @return 1 if deleted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int delete(Transaction transaction) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.DELETE_TRANSACTION)
					.setIntField(transaction.getId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete transaction " + transaction + ".", e);
		}
	}

	/**
	 * This method modify User table's balance field and add transaction about these changes.
	 * @param userId The ID of the user whose balance needs to be changed.
	 * @param difference Difference in balance to be made.
	 * @param description Description of the transaction.
	 * @return Rows changed.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int changeUserBalance(int userId, BigDecimal diffrence, String description) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.ADD_BALANCE_TO_USER)
					.setBigDecimalField(diffrence).setIntField(userId)
					.addPreparedStatement(MariaDBConstants.ADD_TRANSACTION).setIntField(userId)
					.setBigDecimalField(diffrence).setStringField(description).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot change balance for user with id " + userId + " amount " + diffrence
					+ " description " + description + ".", e);
		}
	}

	/**
	 * Return all transaction for the specific user ID.
	 * @param userIdThe ID of the user whose transaction to be get.
	 * @param offset number of record to skip.
	 * @param recordsNumber number maximum records to get.
	 * @return List of Transaction with specififc user ID.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<Transaction> getUserTransactionForView(int userId, int offset, int recordsNumber) throws DAOException {
		List<Transaction> transactions = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_USERS_TRANSACTION)
				.setIntField(userId).setIntField(offset).setIntField(recordsNumber).executeQuery()) {
			while (rs.next()) {
				transactions.add(getTransactionFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get transaction for user with id " + userId + ", recordsNumber = "
					+ recordsNumber + " offset " + offset + ".", e);
		}

		return transactions;
	}

	/**
	 * Return number of transaction of the user.
	 * @param userId ID of the user whose number of transaction to get.
	 * @return number of transactions.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int getNumberOfUserTransaction(int userId) throws DAOException {
		int count = 0;
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_TRANSACTION_NUMBER_OF_USER)
				.setIntField(userId).executeQuery()) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get count of transactions.", e);
		}
		return count;
	}
	
	/**
	 * This method modify User table's balance field by decreasing it by rate of the Tariff(tariffId) 
	 * and add transaction about these changes.
	 * @param userId The ID of the user whose balance needs to be decreased.
	 * @param tariffId The ID of the Tariff for which to charge.
	 * @param description Description of the transaction to be added.
	 * @return Rows changed.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int chargeUserForTariffUsing(int userId, int tariffId, String description) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.ADD_TARIFF_CHARGE_TRANSACTION)
					.setIntField(userId).setIntField(tariffId).setStringField(description)
					.addPreparedStatement(MariaDBConstants.UPDATE_DAYS_UNTIL_NEXT_PAYMENT).setIntField(tariffId)
					.setIntField(userId).setIntField(tariffId)
					.addPreparedStatement(MariaDBConstants.CHARGE_FOR_USING_TARIFF).setIntField(tariffId)
					.setIntField(userId).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException(
					"Cannot proccess of charging user with id " + userId + " for using tariff with id " + tariffId, e);
		}

	}

	/**
	 * This method gets Transaction from the result set.
	 * @param rs Result set to get Transaction from.
	 * @return Transaction from Result Set
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	private Transaction getTransactionFromResultSet(ResultSet rs) throws DAOException {
		try {
			return new Transaction(rs.getInt(MariaDBConstants.TRANSACTION_ID_FIELD),
					rs.getInt(MariaDBConstants.TRANSACTION_USER_ID_FIELD),
					rs.getTimestamp(MariaDBConstants.TRANSACTION_TIMESTAMP_FIELD),
					rs.getBigDecimal(MariaDBConstants.TRANSACTION_AMOUNT_FIELD),
					rs.getString(MariaDBConstants.TRANSACTION_DESCRIPTION_FIELD));
		} catch (SQLException e) {
			throw new DAOMappingException("Cannot map transaction from ResultSet.", e);
		}
	}

}
