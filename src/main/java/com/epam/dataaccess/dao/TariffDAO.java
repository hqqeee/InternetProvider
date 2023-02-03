package com.epam.dataaccess.dao;

import java.util.List;
import java.util.Map;

import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.dao.DAOException;
import com.epam.util.SortingOrder;

/**
 * Tariff DAO interface. Has methods needed by services to access persistence layer.
 * @author ruslan
 *
 */

public interface TariffDAO extends DAO<Tariff> {
	
	/**
	 * This method return all tariff filtered by serviceId,  limited by offset and recordsPerPage and sorted.
	 * by fieldName in sorting order.
	 * @param serviceId service id to filter.
	 * @param offset number of records to skip.
	 * @param recordsPerPage number of records of page.
	 * @param order sorting order.
	 * @param fieldName name of field to sort by which.
	 * @return List of tariff filtered by serviceId,  limited by offset and recordsPerPage and sorted.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	public List<Tariff> getAll(int serviceId, int offset, int recordsPerPage, SortingOrder order, String fieldName) throws DAOException;

	/**
	 * This method return all tariff limited by offset and recordsPerPage and sorted.
	 * by fieldName in sorting order.
	 * @param offset number of records to skip.
	 * @param recordsPerPage number of records of page.
	 * @param order sorting order.
	 * @param fieldName name of field to sort by which.
	 * @return List of tariff limited by offset and recordsPerPage and sorted.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	public List<Tariff> getAll(int offset, int recordsPerPage, SortingOrder order, String fieldName) throws DAOException;
	
	/**
	 * This method return number of tariff with specific service.
	 * @param serviceId service id to filter.
	 * @return number of tariff with specific service.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	public int getTariffCount(int serviceId) throws DAOException;

	/**
	 * This method return number of all tariffs.
	 * @return number of all tariffs.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	public int getTariffCount()  throws DAOException;

	/**
	 * This method return all tariffs for specific service. 
	 * @param serviceId if of service to filter.
	 * @return All tariffs with specific service.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	public List<Tariff> getAll(int serviceId) throws DAOException;
	
	/**
	 * Return list of tariff for specific users.
	 * @param userId User ID of which tariff to get.
	 * @return All tariffs of this user.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
    public List<Tariff> getUsersTariffs(int userId) throws DAOException;
    
    /**
	 * Return list of tariffs for specific users that has 0 days until next payment.
	 * @param userId User ID of which tariff to get.
	 * @return All tariffs of this user  that has 0 days until next payment.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
    public List<Tariff> getUsersUnpaidTariffs(int userId) throws DAOException;
    
    /**
     * Returns a map of Tariff and day until next payment for specific user.
     * @param userId id of the user to get map for.
     * @return a map of Tariff and day until next payment for specific user.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public Map<Tariff,Integer> getUsersTariffsWithDayToPayment(int userId) throws DAOException;
    
    /**
     * Decrement day until next payment for all user_has_tariff records that are greater than zero.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public void updateDaysLeftForUnblockedUsers() throws DAOException;
}
