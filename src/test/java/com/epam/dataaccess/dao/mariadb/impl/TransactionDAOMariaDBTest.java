package com.epam.dataaccess.dao.mariadb.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.epam.dataaccess.dao.mariadb.datasource.QueryBuilder;
import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.dao.DAOException;

class TransactionDAOMariaDBTest {

	@Mock
	private QueryBuilder mockQueryBuilder = mock(QueryBuilder.class);

	@Mock
	private ResultSet mockResultSet = mock(ResultSet.class);

	private TransactionDAOMariaDB transactionDAOMariaDB;

	@BeforeEach
	void setUp() throws Exception {
		transactionDAOMariaDB = new TransactionDAOMariaDB() {
			@Override
			protected QueryBuilder getQueryBuilder() throws SQLException {
				return mockQueryBuilder;
			}
		};
	}

	@Test
	void testGet() throws DAOException, SQLException {
		int id = 1;
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Transaction expectedTransaction = new Transaction(id, 1, ts, BigDecimal.valueOf(100), "test");
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TRANSACTION_ID_FIELD)).thenReturn(expectedTransaction.getId());
		when(mockResultSet.getInt(MariaDBConstants.TRANSACTION_USER_ID_FIELD))
				.thenReturn(expectedTransaction.getUserId());
		when(mockResultSet.getTimestamp(MariaDBConstants.TRANSACTION_TIMESTAMP_FIELD)).thenReturn(ts);
		when(mockResultSet.getBigDecimal(MariaDBConstants.TRANSACTION_AMOUNT_FIELD))
				.thenReturn(expectedTransaction.getAmount());
		when(mockResultSet.getString(MariaDBConstants.TRANSACTION_DESCRIPTION_FIELD))
				.thenReturn(expectedTransaction.getDescription());
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_TRANSACTION_BY_ID))
				.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(id)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		Transaction transaction = transactionDAOMariaDB.get(id);
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.GET_TRANSACTION_BY_ID);
		verify(mockQueryBuilder, times(1)).setIntField(id);
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(expectedTransaction, transaction);
	}

	@Test
	void testGetAll() throws DAOException, SQLException {
		int id1 = 1;
		int id2 = 2;
		Timestamp ts1 = new Timestamp(System.currentTimeMillis());
		Timestamp ts2 = new Timestamp(System.currentTimeMillis());
		Transaction expectedTransaction1 = new Transaction(id1, 1, ts1, BigDecimal.valueOf(100), "test1");
		Transaction expectedTransaction2 = new Transaction(id2, 1, ts2, BigDecimal.valueOf(200), "test2");
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TRANSACTION_ID_FIELD)).thenReturn(expectedTransaction1.getId())
				.thenReturn(expectedTransaction2.getId());
		when(mockResultSet.getInt(MariaDBConstants.TRANSACTION_USER_ID_FIELD))
				.thenReturn(expectedTransaction1.getUserId()).thenReturn(expectedTransaction2.getUserId());
		when(mockResultSet.getTimestamp(MariaDBConstants.TRANSACTION_TIMESTAMP_FIELD)).thenReturn(ts1).thenReturn(ts2);
		when(mockResultSet.getBigDecimal(MariaDBConstants.TRANSACTION_AMOUNT_FIELD))
				.thenReturn(expectedTransaction1.getAmount()).thenReturn(expectedTransaction2.getAmount());
		when(mockResultSet.getString(MariaDBConstants.TRANSACTION_DESCRIPTION_FIELD))
				.thenReturn(expectedTransaction1.getDescription()).thenReturn(expectedTransaction2.getDescription());
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_ALL_TRANSACTIONS)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		List<Transaction> transactions = transactionDAOMariaDB.getAll();
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.GET_ALL_TRANSACTIONS);
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(2, transactions.size());
		assertEquals(expectedTransaction1, transactions.get(0));
		assertEquals(expectedTransaction2, transactions.get(1));
	}

	@Test
	void testInsert() throws DAOException, SQLException {
		Transaction transaction = new Transaction(1, 1, new Timestamp(System.currentTimeMillis()),
				BigDecimal.valueOf(100), "test");
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.ADD_TRANSACTION)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(transaction.getUserId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setTimestamField(transaction.getTimestamp())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBigDecimalField(transaction.getAmount())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(transaction.getDescription())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int insertedId = transactionDAOMariaDB.insert(transaction);
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.ADD_TRANSACTION);
		verify(mockQueryBuilder, times(1)).setIntField(transaction.getUserId());
		verify(mockQueryBuilder, times(1)).setBigDecimalField(transaction.getAmount());
		verify(mockQueryBuilder, times(1)).setStringField(transaction.getDescription());
		verify(mockQueryBuilder, times(1)).executeUpdate();
		assertNotEquals(0, insertedId);
	}

	@Test
	void testUpdate() throws DAOException, SQLException {
		Transaction transaction = new Transaction(1, 1, new Timestamp(System.currentTimeMillis()),
				BigDecimal.valueOf(100), "test");
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.UPDATE_TRANSACTION)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(transaction.getUserId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setTimestamField(transaction.getTimestamp())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBigDecimalField(transaction.getAmount())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(transaction.getDescription())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(transaction.getId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int updatedCount = transactionDAOMariaDB.update(transaction);
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.UPDATE_TRANSACTION);
		verify(mockQueryBuilder, times(2)).setIntField(transaction.getUserId());
		verify(mockQueryBuilder, times(1)).setStringField(transaction.getDescription());
		verify(mockQueryBuilder, times(1)).executeUpdate();
		assertNotEquals(0, updatedCount);
	}

	@Test
	void testDelete() throws DAOException, SQLException {
		int id = 1;
		Transaction transaction = new Transaction(id, 1, new Timestamp(System.currentTimeMillis()),
				BigDecimal.valueOf(100), "test");
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.DELETE_TRANSACTION)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(id)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int actual = transactionDAOMariaDB.delete(transaction);
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.DELETE_TRANSACTION);
		verify(mockQueryBuilder, times(1)).setIntField(id);
		verify(mockQueryBuilder, times(1)).executeUpdate();
		assertEquals(1, actual);
	}

	@Test
	void testChangeUserBalance() throws DAOException, SQLException {
		int userId = 1;
		BigDecimal amount = BigDecimal.valueOf(100);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.ADD_BALANCE_TO_USER)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.ADD_TRANSACTION)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(userId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBigDecimalField(amount)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField("desc")).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int result = transactionDAOMariaDB.changeUserBalance(userId, amount, "desc");
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.ADD_TRANSACTION);
		verify(mockQueryBuilder, times(2)).setIntField(userId);
		verify(mockQueryBuilder, times(2)).setBigDecimalField(amount);
		verify(mockQueryBuilder, times(1)).executeUpdate();
		assertEquals(1, result);
	}

	@Test
	void testGetUserTransactionForView() throws DAOException, SQLException {
		int userId = 1;
		int start = 0;
		int limit = 10;
		Transaction expectedTransaction = new Transaction(1, userId, new Timestamp(System.currentTimeMillis()),
				BigDecimal.valueOf(100), "test");
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TRANSACTION_ID_FIELD)).thenReturn(expectedTransaction.getId());
		when(mockResultSet.getInt(MariaDBConstants.TRANSACTION_USER_ID_FIELD))
				.thenReturn(expectedTransaction.getUserId());
		when(mockResultSet.getTimestamp(MariaDBConstants.TRANSACTION_TIMESTAMP_FIELD))
				.thenReturn(expectedTransaction.getTimestamp());
		when(mockResultSet.getBigDecimal(MariaDBConstants.TRANSACTION_AMOUNT_FIELD))
				.thenReturn(expectedTransaction.getAmount());
		when(mockResultSet.getString(MariaDBConstants.TRANSACTION_DESCRIPTION_FIELD))
				.thenReturn(expectedTransaction.getDescription());
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_USERS_TRANSACTION))
				.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(userId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(start)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(limit)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		List<Transaction> transactions = transactionDAOMariaDB.getUserTransactionForView(userId, start, limit);
		assertEquals(expectedTransaction, transactions.get(0));
	}

	@Test
	void testGetNumberOfUserTransactions() throws DAOException, SQLException {
		int userId = 1;
		int expectedResult = 5;
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(1)).thenReturn(expectedResult);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_TRANSACTION_NUMBER_OF_USER))
				.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(userId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		int result = transactionDAOMariaDB.getNumberOfUserTransaction(userId);
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.GET_TRANSACTION_NUMBER_OF_USER);
		verify(mockQueryBuilder, times(1)).setIntField(userId);
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(expectedResult, result);
	}

	@Test
	void testChargeUserForTariffUsing() throws DAOException, SQLException {
		int userId = 1;
		int tariffId = userId;
		BigDecimal amount = BigDecimal.valueOf(100);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.ADD_TARIFF_CHARGE_TRANSACTION))
				.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.UPDATE_DAYS_UNTIL_NEXT_PAYMENT))
		.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.CHARGE_FOR_USING_TARIFF))
		.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(userId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBigDecimalField(amount)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField("desc")).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int result = transactionDAOMariaDB.chargeUserForTariffUsing(userId, tariffId, "desc");
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.ADD_TARIFF_CHARGE_TRANSACTION);
		verify(mockQueryBuilder, times(7)).setIntField(userId);
		verify(mockQueryBuilder, times(1)).executeUpdate();
		assertEquals(1, result);
	}
}