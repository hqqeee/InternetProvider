package com.epam.dataaccess.dao.mariadb.impl;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.RoleDAO;
import com.epam.dataaccess.dao.ServiceDAO;
import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.dao.UserDAO;

public class DAOFactoryMariaDB implements DAOFactory{

	@Override
	public UserDAO getUserDAO() {
		return new UserDAOMariaDB();
	}

	@Override
	public TariffDAO getTariffDAO() {
		return new TariffDAOMariaDB();
	}

	@Override
	public TransactionDAO getTransactionDAO() {
		return new TransactionDAOMariaDB();
	}

	@Override
	public ServiceDAO getServiceDAO() {
		return new ServiceDAOMariaDB();
	}

	@Override
	public RoleDAO getRoleDAO() {
		return new RoleDAOMariaDB();
	}

}
