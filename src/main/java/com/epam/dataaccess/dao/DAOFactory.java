package com.epam.dataaccess.dao;

/**
 * 
 * Interface DAOFactory that implements factory methods for creating DAO
 * objects.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public interface DAOFactory {
	/**
	 * 
	 * Creates UserDAO object.
	 * 
	 * @return instance of UserDAO.
	 */
	public UserDAO getUserDAO();

	/**
	 * 
	 * Creates TariffDAO object.
	 * 
	 * @return instance of TariffDAO.
	 */
	public TariffDAO getTariffDAO();

	/**
	 * 
	 * Creates TransactionDAO object.
	 * 
	 * @return instance of TransactionDAO.
	 */
	public TransactionDAO getTransactionDAO();

	/**
	 * 
	 * Creates ServiceDAO object.
	 * 
	 * @return instance of ServiceDAO.
	 */
	public ServiceDAO getServiceDAO();

	/**
	 * 
	 * Creates RoleDAO object.
	 * 
	 * @return instance of RoleDAO.
	 */
	public RoleDAO getRoleDAO();
}