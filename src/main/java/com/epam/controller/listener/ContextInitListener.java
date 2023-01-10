package com.epam.controller.listener;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

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
import com.epam.dataaccess.entity.User;
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
			prop.load(servletContext.getResourceAsStream("/WEB-INF/init/init.properties"));
			prop.load(servletContext.getResourceAsStream("/WEB-INF/init/commands.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		try {
			initDatabase(prop);
			initServices(prop);
			initCommand(prop);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContextListener.super.contextDestroyed(sce);
	}

	private DAOFactory initDatabase(Properties prop)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		System.out.println("Database init started.");
		ConnectionPool.getInstance();
		return (DAOFactory) Class.forName(prop.getProperty("dao.factory.fqn")).getDeclaredConstructor().newInstance();
	}

	@SuppressWarnings("unchecked")
	private void initServices(Properties prop)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		System.out.println("Service init started.");
		DAOFactory daoFactory = initDatabase(prop);
		Constructor<TariffService> tariffServiceConstructor = (Constructor<TariffService>) Class
				.forName(prop.getProperty("tariff.service.fqn")).getDeclaredConstructor(DAOFactory.class);
		tariffServiceConstructor.setAccessible(true);
		TariffService tariffService = tariffServiceConstructor.newInstance(daoFactory);
		Constructor<UserService> userServiceConstructor = (Constructor<UserService>) Class
				.forName(prop.getProperty("user.service.fqn")).getDeclaredConstructor(DAOFactory.class);
		userServiceConstructor.setAccessible(true);
		UserService userService = userServiceConstructor.newInstance(daoFactory); 
		
		Constructor<TransactionService> transactionServiceConstructor = (Constructor<TransactionService>) Class
				.forName(prop.getProperty("transaction.service.fqn")).getDeclaredConstructor(DAOFactory.class);
		transactionServiceConstructor.setAccessible(true);
		TransactionService transactionService = transactionServiceConstructor.newInstance(daoFactory); 

		Method initServices = Class.forName(prop.getProperty("appcontext.fqn"))
				.getDeclaredMethod("initServices",TariffService.class, UserService.class, TransactionService.class);
		initServices.setAccessible(true);
		initServices.invoke(AppContext.getInstance(), 
				tariffService,
				userService,
				transactionService
				);
	}

	private void initCommand(Properties prop)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		System.out.println("Loading commands.");
		List<String> adminCommandNames = prop.stringPropertyNames().stream().filter(
				S -> {
					return Pattern.compile("^admin.*").matcher(S).matches();
				}
				).toList();
		List<String> commonCommandNames = prop.stringPropertyNames().stream().filter(
				S -> {
					return Pattern.compile("^common.*").matcher(S).matches();
				}
				).toList();
		List<String> subscriberCommandNames = prop.stringPropertyNames().stream().filter(
				S -> {
					return Pattern.compile("^subscriber.*").matcher(S).matches();
				}
				).toList();
		Map<String, Command> adminCommands = new HashMap<>();
		Map<String, Command> subscriberCommands = new HashMap<>();
		Map<String, Command> commonCommands = new HashMap<>();
		for(String s: adminCommandNames) {
			adminCommands.put(s.split("\\.")[1], 
					(Command) Class.forName(prop.getProperty(s)).getDeclaredConstructor().newInstance());
		}
		for(String s: commonCommandNames) {
			commonCommands.put(s.split("\\.")[1], 
					(Command) Class.forName(prop.getProperty(s)).getDeclaredConstructor().newInstance());
		}
		for(String s: subscriberCommandNames) {
			subscriberCommands.put(s.split("\\.")[1], 
					(Command) Class.forName(prop.getProperty(s)).getDeclaredConstructor().newInstance());
		}
		
		Method commandsInit = Class.forName(prop.getProperty("appcontext.fqn"))
				.getDeclaredMethod("initCommands",Map.class, Map.class, Map.class);
		commandsInit.setAccessible(true);
		commandsInit.invoke(AppContext.getInstance(), 
				adminCommands,
				subscriberCommands,
				commonCommands
				);
		System.out.println(subscriberCommands);
		System.out.println(adminCommands);
		System.out.println(commonCommands);
		System.out.println("Command load finished.");
	}
	
}
