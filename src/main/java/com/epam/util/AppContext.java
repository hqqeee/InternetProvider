package com.epam.util;

import com.epam.services.TariffService;
import com.epam.services.TransactionService;
import com.epam.services.UserService;

/**
 * The class `AppContext` is a singleton utility class that provides access to
 * instances of the TariffService, UserService, and TransactionService classes.
 * 
 * It follows the Singleton design pattern and ensures that only a single
 * instance of this class exists throughout the lifetime of the application.
 * 
 * The `initServices` method is used to initialize the services.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public final class AppContext {
	private TariffService tariffService;
	private UserService userService;
	private TransactionService transactionService;

	private static final AppContext instance = new AppContext();

	public static AppContext getInstance() {
		return instance;
	}

	private AppContext() {
	}

	public TariffService getTariffService() {
		return this.tariffService;
	}

	public UserService getUserService() {
		return userService;
	}

	public TransactionService getTransactionService() {
		return transactionService;
	}

	/**
	 * Initialize all the service.
	 * @param tariffService TariffService implementation.
	 * @param userService UserService implementation
	 * @param transactionService TransactionService implementation.
	 */
	@SuppressWarnings("unused")
	private void initServices(TariffService tariffService, UserService userService,
			TransactionService transactionService) {
		this.tariffService = tariffService;
		this.userService = userService;
		this.transactionService = transactionService;
	}

}
