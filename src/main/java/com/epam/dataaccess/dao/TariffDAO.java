package com.epam.dataaccess.dao;

import java.util.List;
import java.util.Map;

import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.dao.DAOException;
import com.epam.util.SortingOrder;

/**
 * Tariff DAO interface. Has methods needed by services to access persistence
 * layer.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public interface TariffDAO extends DAO<Tariff> {

	/**
	 * Retrieves a list of tariffs filtered by service ID, limited by `offset` and
	 * `recordsPerPage`, and sorted by the field specified in `fieldName` in the
	 * order specified in `order`.
	 *
	 * @param serviceId      The service ID to filter by.
	 * @param offset         The number of records to skip.
	 * @param recordsPerPage The number of records per page.
	 * @param order          The sorting order.
	 * @param fieldName      The name of the field to sort by.
	 *
	 * @return A list of tariffs filtered by `serviceId`, limited by `offset` and
	 *         `recordsPerPage`, and sorted by `fieldName` in `order`.
	 *
	 * @throws DAOException If a SQL exception occurs.
	 */
	public List<Tariff> getAll(int serviceId, int offset, int recordsPerPage, SortingOrder order, String fieldName)
			throws DAOException;

	/**
	 * Retrieves a list of tariffs limited by `offset` and `recordsPerPage`, and
	 * sorted by the field specified in `fieldName` in the order specified in
	 * `order`.
	 *
	 * @param offset         The number of records to skip.
	 * @param recordsPerPage The number of records per page.
	 * @param order          The sorting order.
	 * @param fieldName      The name of the field to sort by.
	 *
	 * @return A list of tariffs limited by `offset` and `recordsPerPage`, and
	 *         sorted by `fieldName` in `order`.
	 *
	 * @throws DAOException If a SQL exception occurs.
	 */
	public List<Tariff> getAll(int offset, int recordsPerPage, SortingOrder order, String fieldName)
			throws DAOException;

	/**
	 * Retrieves the number of tariffs with a specific service ID.
	 *
	 * @param serviceId The service ID to filter by.
	 * @return The number of tariffs with the specified service ID.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public int getTariffCount(int serviceId) throws DAOException;

	/**
	 * Retrieves the number of tariffs with a specific service ID.
	 *
	 * @return The number of tariffs with the specified service ID.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public int getTariffCount() throws DAOException;

	/**
	 * Retrieves the number of all tariffs.
	 *
	 * @return The number of all tariffs.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public List<Tariff> getAll(int serviceId) throws DAOException;

	/**
	 * Returns a list of all tariffs associated with a specific service.
	 * 
	 * @param userId the id of the user to get tariffs for
	 * @return a list of tariffs for the specified service
	 * @throws DAOException if an error occurs while accessing the persistence layer
	 */
	public List<Tariff> getUsersTariffs(int userId) throws DAOException;

	/**
	 * Returns a list of tariffs associated with a specific user.
	 * 
	 * @param userId the id of the user to get tariffs for
	 * @return a list of tariffs for the specified user
	 * @throws DAOException if an error occurs while accessing the persistence layer
	 */
	public List<Tariff> getUsersUnpaidTariffs(int userId) throws DAOException;

	/**
	 * 
	 * Returns a map of Tariff and day until next payment for a specific user.
	 * 
	 * @param userId the ID of the user to get the map for.
	 * @return a map of Tariff and the number of days until the next payment for the
	 *         specific user.
	 * @throws DAOException if an SQLException occurs.
	 */
	public Map<Tariff, Integer> getUsersTariffsWithDayToPayment(int userId) throws DAOException;

	/**
	 * 
	 * Decrements the day until the next payment for all user_has_tariff records
	 * that have a value greater than zero.
	 * 
	 * @throws DAOException if an SQLException occurs.
	 */
	public void updateDaysLeftForUnblockedUsers() throws DAOException;
}
