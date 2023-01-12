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

	@Override
	public Transaction get(int id) throws DAOException {
		Transaction transaction = null;
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_TRANSACTION_BY_ID)
				.setIntField(id).executeQuery()) {
			if (rs.next()) {
				transaction = getTransactionFromResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get transaction with id " + id + ".", e);
		}

		return transaction;
	}

	@Override
	public List<Transaction> getAll() throws DAOException {
		List<Transaction> transactions = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_TRANSACTIONS)
				.executeQuery()) {
			while (rs.next()) {
				transactions.add(getTransactionFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all transactions.", e);
		}

		return transactions;
	}

	@Override
	public int insert(Transaction transaction) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.ADD_TRANSACTION)
					.setIntField(transaction.getUserId()).setBigDecimalField(transaction.getAmount())
					.setStringField(transaction.getDescription()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOInsertException("Cannot insert transaction " + transaction + ".", e);
		}
	}

	@Override
	public int update(Transaction transaction) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.UPDATE_TRANSACTION)
					.setIntField(transaction.getUserId()).setTimestamField(transaction.getTimestamp())
					.setBigDecimalField(transaction.getAmount()).setStringField(transaction.getDescription())
					.setIntField(transaction.getId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot update transaction " + transaction + ".", e);
		}
	}

	@Override
	public int delete(Transaction transaction) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.DELETE_TRANSACTION)
					.setIntField(transaction.getId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete transaction " + transaction + ".", e);
		}
	}

	@Override
	public int changeUserBalance(int userId, BigDecimal diffrence, String description) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.ADD_BALANCE_TO_USER)
					.setBigDecimalField(diffrence).setIntField(userId)
					.addPreparedStatement(MariaDBConstants.ADD_TRANSACTION).setIntField(userId)
					.setBigDecimalField(diffrence).setStringField(description).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot change balance for user with id " + userId + " amount " + diffrence
					+ " description " + description + ".", e);
		}
	}

	@Override
	public List<Transaction> getUserTransactionForView(int userId, int offset, int recordsNumber) throws DAOException {
		List<Transaction> transactions = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_USERS_TRANSACTION)
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

	@Override
	public int getNumberOfUserTransaction(int userId) throws DAOException {
		int count = 0;
		try(ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_TRANSACTION_NUMBER_OF_USER).setIntField(userId).executeQuery()){
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get count of transactions.",e);
		}
		return count;
	}


	private Transaction getTransactionFromResultSet(ResultSet rs) throws DAOException {
		try {
			return new Transaction(rs.getInt(MariaDBConstants.TRANSACTION_ID_FIELD),
					rs.getInt(MariaDBConstants.TRANSACTION_USER_ID_FIELD),
					rs.getTimestamp(MariaDBConstants.TRANSACTION_TIMESTAMP_FIELD),
					rs.getBigDecimal(MariaDBConstants.TRANSACTION_AMOUNT_FIELD),
					rs.getString(MariaDBConstants.TRANSACTION_DESCRIPTION_FIELD)
			);
		} catch (SQLException e) {
			throw new DAOMappingException("Cannot map transaction from ResultSet.", e);
		}
	}

	@Override
	public int chargeUserForTariffUsing(int userId, int tariffId, String description) throws DAOException {
		try {
			return new QueryBuilder()
					.addPreparedStatement(MariaDBConstants.ADD_TARIFF_CHARGE_TRANSACTION)
					.setIntField(userId).setIntField(tariffId).setStringField(description)
					.addPreparedStatement(MariaDBConstants.UPDATE_DAYS_UNTIL_NEXT_PAYMENT).setIntField(tariffId)
					.setIntField(userId).setIntField(tariffId)
					.addPreparedStatement(MariaDBConstants.CHARGE_FOR_USING_TARIFF).setIntField(tariffId)
					.setIntField(userId)
					.executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot proccess of charging user with id " + userId + " for using tariff with id " + tariffId,e);
		}
		
	}



}
