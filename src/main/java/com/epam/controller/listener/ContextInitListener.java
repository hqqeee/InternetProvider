package com.epam.controller.listener;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.epam.controller.command.Command;
import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.ServiceDAO;
import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.dao.UserDAO;
import com.epam.dataaccess.dao.mariadb.datasource.ConnectionPool;
import com.epam.services.ServiceService;
import com.epam.services.TariffService;
import com.epam.services.TransactionService;
import com.epam.services.UserService;
import com.epam.util.AppContext;

@WebListener
public class ContextInitListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		Properties prop = new Properties();
		try {
			prop.load(servletContext.getResourceAsStream("/WEB-INF/properties/init.properties"));
			prop.load(servletContext.getResourceAsStream("/WEB-INF/properties/commands.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		try {
			initDatabase(servletContext, prop);
			initServices(servletContext, prop);
			initAppContext(servletContext, prop);
			actionLoad(servletContext, prop);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void initAppContext(ServletContext servletContext, Properties prop)
			throws NoSuchFieldException, SecurityException, NoSuchMethodException, ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException {
		Method appContexInit = Class.forName("com.epam.util.AppContext")
				.getDeclaredMethod("init",TariffService.class, UserService.class, TransactionService.class);
		appContexInit.setAccessible(true);
		appContexInit.invoke(AppContext.getInstance(), 
				servletContext.getAttribute("tariffService"),
				servletContext.getAttribute("userService"),
				servletContext.getAttribute("transactionService")
				);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContextListener.super.contextDestroyed(sce);
	}

	private void initDatabase(ServletContext servletContext, Properties prop)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		System.out.println("DatabaseInitListener started.");
		ConnectionPool.getInstance();
		servletContext.setAttribute("daoFactory",
				(DAOFactory) Class.forName(prop.getProperty("dao.factory.fqn")).getDeclaredConstructor().newInstance());
		System.out.println("DatabaseInitListener done.");
	}

	private void initServices(ServletContext servletContext, Properties prop)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		System.err.println("ServletInit started.");
		servletContext.setAttribute("userService",
				(UserService) Class.forName(prop.getProperty("user.service.fqn"))
						.getDeclaredConstructor(UserDAO.class, TransactionDAO.class, TariffDAO.class)
						.newInstance(((DAOFactory) servletContext.getAttribute("daoFactory")).getUserDAO(),
								((DAOFactory) servletContext.getAttribute("daoFactory")).getTransactionDAO(),
								((DAOFactory) servletContext.getAttribute("daoFactory")).getTariffDAO()));

		servletContext.setAttribute("transactionService",
				(TransactionService) Class.forName(prop.getProperty("transaction.service.fqn"))
						.getDeclaredConstructor(TransactionDAO.class)
						.newInstance(((DAOFactory) servletContext.getAttribute("daoFactory")).getTransactionDAO()));

		Constructor<ServiceService> serviceServiceConstructor = (Constructor<ServiceService>) Class
				.forName(prop.getProperty("service.service.fqn")).getDeclaredConstructor(ServiceDAO.class);
		serviceServiceConstructor.setAccessible(true);

		servletContext.setAttribute("serviceService", serviceServiceConstructor
				.newInstance(((DAOFactory) servletContext.getAttribute("daoFactory")).getServiceDAO()));

		Constructor<TariffService> tariffServiceConstructor = (Constructor<TariffService>) Class
				.forName(prop.getProperty("tariff.service.fqn")).getDeclaredConstructor(TariffDAO.class);
		tariffServiceConstructor.setAccessible(true);
		servletContext.setAttribute("tariffService", tariffServiceConstructor
				.newInstance(((DAOFactory) servletContext.getAttribute("daoFactory")).getTariffDAO()));
	}

	private void actionLoad(ServletContext servletContext, Properties prop)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		System.out.println("Loading commands.");
		Map<String, Command> commonCommands = new HashMap<>();

		commonCommands.put(prop.getProperty("common.login.name"),
				(Command) Class.forName(prop.getProperty("common.login.fqn")).getDeclaredConstructor().newInstance());
		commonCommands.put(prop.getProperty("common.logout.name"),
				(Command) Class.forName(prop.getProperty("common.logout.fqn")).getDeclaredConstructor().newInstance());
		commonCommands.put(prop.getProperty("common.viewTariff.name"), (Command) Class
				.forName(prop.getProperty("common.viewTariff.fqn")).getDeclaredConstructor().newInstance());
		commonCommands.put(prop.getProperty("common.downloadTariffs.name"), (Command) Class
				.forName(prop.getProperty("common.downloadTariffs.fqn")).getDeclaredConstructor().newInstance());
		servletContext.setAttribute("commonCommands", commonCommands);

		System.out.println(commonCommands);

		Map<String, Command> subscriberCommands = new HashMap<>();
		subscriberCommands.put(prop.getProperty("subscriber.viewProfile.name"), (Command) Class
				.forName(prop.getProperty("subscriber.viewProfile.fqn")).getDeclaredConstructor().newInstance());
		subscriberCommands.put(prop.getProperty("subscriber.changePassword.name"), (Command) Class
				.forName(prop.getProperty("subscriber.changePassword.fqn")).getDeclaredConstructor().newInstance());
		subscriberCommands.put(prop.getProperty("subscriber.viewAccount.name"), (Command) Class
				.forName(prop.getProperty("subscriber.viewAccount.fqn")).getDeclaredConstructor().newInstance());
		subscriberCommands.put(prop.getProperty("subscriber.viewActiveTariffs.name"), (Command) Class
				.forName(prop.getProperty("subscriber.viewActiveTariffs.fqn")).getDeclaredConstructor().newInstance());
		subscriberCommands.put(prop.getProperty("subscriber.replenish.name"), (Command) Class
				.forName(prop.getProperty("subscriber.replenish.fqn")).getDeclaredConstructor().newInstance());
		subscriberCommands.put(prop.getProperty("subscriber.connectTariff.name"), (Command) Class
				.forName(prop.getProperty("subscriber.connectTariff.fqn")).getDeclaredConstructor().newInstance());
		subscriberCommands.put(prop.getProperty("subscriber.disableTariff.name"), (Command) Class
				.forName(prop.getProperty("subscriber.disableTariff.fqn")).getDeclaredConstructor().newInstance());

		servletContext.setAttribute("subscriberCommands", subscriberCommands);
		System.out.println(subscriberCommands);

		Map<String, Command> adminCommands = new HashMap<>();
		adminCommands.put(prop.getProperty("admin.adminMenu.name"), (Command) Class
				.forName(prop.getProperty("admin.adminMenu.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.openUserRegistrationCommand.name"),
				(Command) Class.forName(prop.getProperty("admin.openUserRegistrationCommand.fqn"))
						.getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.registerUser.name"), (Command) Class
				.forName(prop.getProperty("admin.registerUser.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.removeUser.name"), (Command) Class
				.forName(prop.getProperty("admin.removeUser.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.changeUserStatus.name"), (Command) Class
				.forName(prop.getProperty("admin.changeUserStatus.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.changeUserBalance.name"), (Command) Class
				.forName(prop.getProperty("admin.changeUserBalance.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.removeTariff.name"), (Command) Class
				.forName(prop.getProperty("admin.removeTariff.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.addTariff.name"), (Command) Class
				.forName(prop.getProperty("admin.addTariff.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.editTariff.name"), (Command) Class
				.forName(prop.getProperty("admin.editTariff.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.openAddTariff.name"), (Command) Class
				.forName(prop.getProperty("admin.openAddTariff.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.viewSubscriberProfile.name"), (Command) Class
				.forName(prop.getProperty("admin.viewSubscriberProfile.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.viewSubscriberTariffs.name"), (Command) Class
				.forName(prop.getProperty("admin.viewSubscriberTariffs.fqn")).getDeclaredConstructor().newInstance());
		adminCommands.put(prop.getProperty("admin.viewSubscriberAccount.name"), (Command) Class
				.forName(prop.getProperty("admin.viewSubscriberAccount.fqn")).getDeclaredConstructor().newInstance());

		servletContext.setAttribute("adminCommands", adminCommands);
		System.out.println(adminCommands);
		System.out.println("Action load commands.");
	}
}
