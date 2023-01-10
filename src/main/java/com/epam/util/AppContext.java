package com.epam.util;


import java.util.Map;

import com.epam.controller.command.Command;
import com.epam.services.TariffService;
import com.epam.services.TransactionService;
import com.epam.services.UserService;

public final class AppContext {
	private TariffService tariffService;
	private UserService userService;
	private TransactionService transactionService;
	
	private Map<String, Command> adminCommands;
	private Map<String, Command> subscriberCommands;
	private Map<String, Command> commonCommands;
	
	
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

	public Map<String, Command> getAdminCommands() {
		return adminCommands;
	}

	public Map<String, Command> getSubscriberCommands() {
		return subscriberCommands;
	}

	public Map<String, Command> getCommonCommands() {
		return commonCommands;
	}
	
	@SuppressWarnings("unused")
	private void initServices(TariffService tariffService, UserService userService, TransactionService transactionService) {
		this.tariffService = tariffService;
		this.userService= userService;
		this.transactionService = transactionService;
	}
	
	@SuppressWarnings("unused")
	private void initCommands(Map<String, Command> adminCommands,Map<String, Command> subscriberCommands, Map<String, Command> commonCommands) {
		this.adminCommands = adminCommands;
		this.subscriberCommands = subscriberCommands;
		this.commonCommands = commonCommands;
	}
}
