package com.epam.dataaccess.dao;

public interface DAOFactory {
    public UserDAO getUserDAO();
    public TariffDAO getTariffDAO();
    public TransactionDAO getTransactionDAO();
    public ServiceDAO getServiceDAO();
    public RoleDAO getRoleDAO();
}