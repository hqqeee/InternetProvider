package com.epam.util;


import com.epam.services.TariffService;
import com.epam.services.TransactionService;
import com.epam.services.UserService;

public final class AppContext {
	private TariffService tariffService;
	private UserService userService;
	private TransactionService transactionService;
	
	
	private static final AppContext instance = new AppContext();
	
	public static AppContext getInstance(){
		return instance;
	}
	
	private AppContext() {}

	public TariffService getTariffService() {
		return this.tariffService;
	}
	public UserService getUserService() {
		return userService;
	}
	public TransactionService getTransactionService() {
		return transactionService;
	}
	
	@SuppressWarnings("unused")
	private void initServices(TariffService tariffService, UserService userService, TransactionService transactionService) {
		this.tariffService = tariffService;
		this.userService= userService;
		this.transactionService = transactionService;
	}

}
