package com.epam.dataaccess.dao;

import java.math.BigDecimal;
import java.util.List;

import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.dao.DAOException;

public interface TransactionDAO extends DAO<Transaction> {
	public int changeUserBalance(int userId, BigDecimal diffrence,String description) throws DAOException;
	public List<Transaction> getUserTransactionForView(int userId, int offset, int recordsNumber) throws DAOException;
	public int getNumberOfUserTransaction(int userId) throws DAOException;
}
