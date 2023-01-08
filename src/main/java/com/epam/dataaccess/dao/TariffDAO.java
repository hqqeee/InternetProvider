package com.epam.dataaccess.dao;

import java.util.List;
import java.util.Map;

import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.dao.DAOException;
import com.epam.util.SortingOrder;



public interface TariffDAO extends DAO<Tariff> {
	public List<Tariff> getAll(int serviceId, int offset, int recordsPerPage, SortingOrder order, String fieldName) throws DAOException;

	public List<Tariff> getAll(int offset, int recordsPerPage, SortingOrder order, String fieldName) throws DAOException;
	
	public int getTariffCount(int serviceId) throws DAOException;

	public int getTariffCount()  throws DAOException;

	public List<Tariff> getAll(int serviceId) throws DAOException;
	
    public List<Tariff> getUsersTariffs(int userId) throws DAOException;
    
    public List<Tariff> getUsersUnpaidTariffs(int userId) throws DAOException;
    
    public Map<Tariff,Integer> getUsersTariffsWithDayToPayment(int userId) throws DAOException;
    
    public void updateDaysLeftForUnblockedUsers() throws DAOException;
}
