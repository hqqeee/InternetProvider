package com.epam.dataaccess.dao;

/**
 * Interface for DAOFactory. Implements factory methods for DAOs.
 * @author ruslan
 *
 */

public interface DAOFactory {
    public UserDAO getUserDAO();
    public TariffDAO getTariffDAO();
    public TransactionDAO getTransactionDAO();
    public ServiceDAO getServiceDAO();
    public RoleDAO getRoleDAO();
}