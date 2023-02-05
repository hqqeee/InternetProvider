package com.epam.controller.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.mariadb.datasource.ConnectionPool;
import com.epam.dataaccess.dao.mariadb.impl.DAOFactoryMariaDB;
import com.epam.services.TariffService;
import com.epam.services.TransactionService;
import com.epam.services.UserService;
import com.epam.util.AppContext;

/**
 * ContextInitListener is a ServletContextListener class that is responsible for
 * initializing the database and services when the application is started.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
@WebListener
public class ContextInitListener implements ServletContextListener {

	/**
	 * This method is called when the application is started. It initializes the
	 * database and services and logs the process. If there is an error during
	 * initialization, the application will exit.
	 * 
	 * @param sce ServletContextEvent
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Logger logger = LogManager.getLogger(ContextInitListener.class);
		try {
			logger.debug("Database and services init started.");
			initServices();
			logger.debug("Database and services init finished.");
		} catch (Exception e) {
			logger.fatal("Cannot initialize database and services. Shutting down...", e);
			System.exit(1);
		}
	}

	/**
	 * This method is called when the application is stopped. It invokes the
	 * superclass's contextDestroyed method.
	 * 
	 * @param sce ServletContextEvent
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContextListener.super.contextDestroyed(sce);
	}

	/**
	 * This method initializes the database and returns the DAOFactory.
	 * 
	 * @return DAOFactory
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws SQLException
	 */
	private DAOFactory initDatabase()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		ConnectionPool cp = ConnectionPool.getInstance();
		Connection c = cp.getConnection();
		cp.releaseConnection(c);
		return (DAOFactory) DAOFactoryMariaDB.class.getDeclaredConstructor().newInstance();
	}

	/**
	 * This method initializes the services by creating instances of TariffService,
	 * UserService, and TransactionService.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private void initServices() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, SQLException {
		DAOFactory daoFactory = initDatabase();
		Constructor<TariffService> tariffServiceConstructor = (Constructor<TariffService>) Class
				.forName("com.epam.services.impl.TariffServiceImpl").getDeclaredConstructor(DAOFactory.class);
		tariffServiceConstructor.setAccessible(true);
		TariffService tariffService = tariffServiceConstructor.newInstance(daoFactory);

		Constructor<UserService> userServiceConstructor = (Constructor<UserService>) Class
				.forName("com.epam.services.impl.UserServiceImpl").getDeclaredConstructor(DAOFactory.class);
		userServiceConstructor.setAccessible(true);
		UserService userService = userServiceConstructor.newInstance(daoFactory);

		Constructor<TransactionService> transactionServiceConstructor = (Constructor<TransactionService>) Class
				.forName("com.epam.services.impl.TransactionServiceImpl").getDeclaredConstructor(DAOFactory.class);
		transactionServiceConstructor.setAccessible(true);
		TransactionService transactionService = transactionServiceConstructor.newInstance(daoFactory);

		Method initServices = Class.forName("com.epam.util.AppContext").getDeclaredMethod("initServices",
				TariffService.class, UserService.class, TransactionService.class);
		initServices.setAccessible(true);
		initServices.invoke(AppContext.getInstance(), tariffService, userService, transactionService);
	}

}
