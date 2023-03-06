package com.epam.controller.command;

import java.util.HashMap;
import java.util.Map;

import static com.epam.controller.command.CommandNames.*;

import com.epam.controller.command.admin.*;
import com.epam.controller.command.common.*;
import com.epam.controller.command.subscriber.*;
import com.epam.exception.controller.CommandNotFoundException;

/**
 * The CommandFactory class is a singleton enum that serves as a factory for
 * creating different commands for different user roles based on the command
 * name.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 **/
public enum CommandFactory {
	/**
	 * Singleton instance of the CommandFactory class.
	 */
	INSTANCE;

	/**
	 * Map of commands and their respective command names.
	 */
	private static Map<String, Command> commands = new HashMap<>();
	/**
	 * Populates the map of commands with the command name as key and the command
	 * object as value.
	 */
	static {
		/** Common commands **/
		commands.put(LOGIN, new LoginCommand());
		commands.put(LOGOUT, new LogoutCommand());
		commands.put(VIEW_TARIFFS, new ViewTariffsCommand());
		commands.put(DOWNLOAD_TARIFFS, new DownloadTariffsCommand());
		commands.put(CHANGE_LANGUAGE, new ChangeLanguageCommand());
		commands.put(RESET_PASSWORD, new ResetPasswordCommand());
		/** Subscriber commands **/
		commands.put(VIEW_PROFILE, new ViewProfileCommand());
		commands.put(VIEW_ACCOUNT, new ViewAccountCommand());
		commands.put(VIEW_ACTIVE_TARIFFS, new ViewActiveTariffsCommand());
		commands.put(CHANGE_PASSWORD, new ChangePasswordCommand());
		commands.put(CONNECT_TARIFF, new ConnectTariffCommand());
		commands.put(REPLENISH, new ReplenishCommand());
		commands.put(DISABLE_TARIFF, new DisableTariffCommand());
		/** Admin commands **/
		commands.put(REGISER_USER, new RegisterUserCommand());
		commands.put(ADMIN_MENU, new AdminMenuCommand());
		commands.put(REMOVE_USER, new RemoveUserCommand());
		commands.put(OPEN_USER_REGISTRATION, new OpenUserRegistrationCommand());
		commands.put(VIEW_SUBSCRIBER_PROFILE, new ViewSubscriberProfileCommand());
		commands.put(VIEW_SUBSCRIBER_TARIFFS, new ViewSubscriberTariffsCommand());
		commands.put(VIEW_SUBSCRIBER_ACCOUNT, new ViewSubscriberAccountCommand());
		commands.put(CHANGE_USER_STATUS, new ChangeUserStatusCommand());
		commands.put(CHANGE_USER_BALANCE, new ChangeUserBalanceCommand());
		commands.put(REMOVE_TARIFF, new RemoveTariffCommand());
		commands.put(EDIT_TARIFF, new EditTariffCommand());
		commands.put(ADD_TARIFF, new AddTariffCommand());
		commands.put(OPEN_ADD_TARIFF, new OpenAddTariffCommand());
	}

	/**
	 * 
	 * Private constructor of the CommandFactory enum, which ensures that the
	 * CommandFactory object can only be instantiated within this class.
	 */
	private CommandFactory() {
	}

	/**
	 * 
	 * Returns the singleton instance of the CommandFactory.
	 * 
	 * @return The singleton instance of the CommandFactory.
	 */
	public static CommandFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Returns the Command object that corresponds to the given command name.
	 * 
	 * @param commandName the name of the command to be retrieved.
	 * @return the Command object that corresponds to the given command name.
	 * @throws CommandNotFoundException if no Command object with the given name can
	 *                                  be found.
	 */

	public Command getCommand(String commandName) throws CommandNotFoundException {
		Command command = commands.get(commandName);
		if (command == null)
			throw new CommandNotFoundException("Cannot get command with name " + commandName + ".");
		return command;
	}
}