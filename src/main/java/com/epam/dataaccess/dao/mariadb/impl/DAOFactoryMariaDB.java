package com.epam.dataaccess.dao.mariadb.impl;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.RoleDAO;
import com.epam.dataaccess.dao.ServiceDAO;
import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.dao.UserDAO;

/**
 * 
 * The DAOFactoryMariaDB is an implementation of the {@link DAOFactory}
 * interface for MariaDB.
 * 
 * It provides factory methods for getting DAO objects for different entities in
 * MariaDB.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public class DAOFactoryMariaDB implements DAOFactory {
	/**
	 * 
	 * Returns an instance of the UserDAOMariaDB class, which implements UserDAO
	 * interface.
	 * 
	 * @return instance of UserDAOMariaDB
	 */
	@Override
	public UserDAO getUserDAO() {
		return new UserDAOMariaDB();
	}

	/**
	 * 
	 * Returns an instance of the TariffDAOMariaDB class, which implements TariffDAO
	 * interface.
	 * 
	 * @return instance of TariffDAOMariaDB
	 */
	@Override
	public TariffDAO getTariffDAO() {
		return new TariffDAOMariaDB();
	}

	/**
	 * 
	 * Returns an instance of the TransactionDAOMariaDB class, which implements
	 * TransactionDAO interface.
	 * 
	 * @return instance of TransactionDAOMariaDB
	 */
	@Override
	public TransactionDAO getTransactionDAO() {
		return new TransactionDAOMariaDB();
	}

	/**
	 * 
	 * Returns an instance of the ServiceDAOMariaDB class, which implements
	 * ServiceDAO interface.
	 * 
	 * @return instance of ServiceDAOMariaDB
	 */
	@Override
	public ServiceDAO getServiceDAO() {
		return new ServiceDAOMariaDB();
	}

	/**
	 * 
	 * Returns an instance of the RoleDAOMariaDB class, which implements RoleDAO
	 * interface.
	 * 
	 * @return instance of RoleDAOMariaDB
	 */
	@Override
	public RoleDAO getRoleDAO() {
		return new RoleDAOMariaDB();
	}

}
