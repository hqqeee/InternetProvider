package com.epam.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.entity.Transaction;
import com.epam.exception.dao.DAOException;
import com.epam.exception.services.NoTransactionsFoundException;
import com.epam.exception.services.TransactionServiceException;
import com.epam.services.dto.TransactionDTO;

class TransactionServiceImplTest {

	private Transaction testTransaction = new Transaction(0, 1, new Timestamp(999), BigDecimal.TEN,"desc" );
	private TransactionDTO testTransactionDTO = null;
	
	@Mock
	private TransactionDAO transactionDAO;
	
	@Mock
	private DAOFactory daoFactory;

	@Mock
	private TransactionServiceImpl transactionService;

	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MockitoAnnotations.openMocks(this);
		@SuppressWarnings("unchecked")
		Constructor<TransactionServiceImpl> transactionServiceConstructor = (Constructor<TransactionServiceImpl>) Class
				.forName(TransactionServiceImpl.class.getName()).getDeclaredConstructor(DAOFactory.class);
		transactionServiceConstructor.setAccessible(true);
		transactionService = transactionServiceConstructor.newInstance(daoFactory);
		testTransactionDTO = transactionService.convertTransactionToTransactionDTO(testTransaction);
		Mockito.when(daoFactory.getTransactionDAO()).thenReturn(transactionDAO);
	}
	
	@Test
	public void testGetUserTransaction() throws DAOException, TransactionServiceException, NoTransactionsFoundException {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(testTransaction);
		List<TransactionDTO> transactionsDTOs = new ArrayList<>();
		transactionsDTOs.add(testTransactionDTO);
		Mockito.when(transactionDAO.getUserTransactionForView(0, 0, 1)).thenReturn(transactions);
		assertEquals(transactionsDTOs, transactionService.getUserTransaction(0,1,1));
	}
	
	@Test
	public void testGetUserTransactionNoTransactions() throws DAOException {
		List<Transaction> transactions = new ArrayList<>();
		Mockito.when(transactionDAO.getUserTransactionForView(0, 0, 1)).thenReturn(transactions);
		assertThrows(NoTransactionsFoundException.class, () -> transactionService.getUserTransaction(0,1,1));
	}
	
	@Test
	public void testGetUserTransactionDAOException() throws DAOException {
		Mockito.when(transactionDAO.getUserTransactionForView(0, 0, 1)).thenThrow(DAOException.class);
		assertThrows(TransactionServiceException.class, () -> transactionService.getUserTransaction(0,1,1));
	}
	
	@Test
	public void testGetUsersTransactionNumber() throws DAOException, TransactionServiceException {
		Mockito.when(transactionDAO.getNumberOfUserTransaction(0)).thenReturn(2);
		assertEquals(2, transactionService.getUsersTransactionNumber(0));
	}
	
	@Test
	public void testGetUsersTransactionNumberDAOException() throws DAOException {
		Mockito.when(transactionDAO.getNumberOfUserTransaction(0)).thenThrow(DAOException.class);
		assertThrows(TransactionServiceException.class, () -> transactionService.getUsersTransactionNumber(0));
	}
	
}
