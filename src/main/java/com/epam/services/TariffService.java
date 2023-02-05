package com.epam.services;

import java.util.List;
import java.util.Map;

import com.epam.exception.services.TariffServiceException;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.TariffForm;
import com.epam.util.SortingOrder;

/**
 * 
 * The TariffService interface provides methods for interacting with the DAO and
 * performing business logic related to tariffs.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public interface TariffService {

	/**
	 * 
	 * Retrieves a sorted and filtered list of TariffDTOs based on the specified
	 * parameters.
	 * 
	 * @param fieldName      the name of the field to sort by
	 * @param sortingOrder   the order to sort the tariffs
	 * @param service        the service to filter tariffs by
	 * @param page           the page number to display
	 * @param entriesPerPage the number of tariffs to display per page
	 * @return a list of TariffDTOs to display
	 * @throws TariffServiceException if an error occurs in the DAO layer
	 */
	public List<TariffDTO> getTariffsForView(String fieldName, SortingOrder sortingOrder, Service service, int page,
			int entriesPerPage) throws TariffServiceException;

	/**
	 * 
	 * Retrieves the number of tariffs for a specific service.
	 * 
	 * @param service the service to count tariffs for
	 * @return the number of tariffs for the specified service
	 * @throws TariffServiceException if an error occurs in the DAO layer
	 */
	public int getTariffsCount(Service service) throws TariffServiceException;

	/**
	 * 
	 * Retrieves a filtered list of all tariffs for a specific service.
	 * 
	 * @param service the service to filter tariffs by
	 * @return a list of tariffs for the specified service
	 * @throws TariffServiceException if an error occurs in the DAO layer
	 */
	public List<TariffDTO> getAllTariff(Service service) throws TariffServiceException;

	/**
	 * 
	 * Retrieves a list of all tariffs for a specific user.
	 * 
	 * @param userId the id of the user to retrieve tariffs for
	 * @return a list of tariffs for the specified user
	 * @throws TariffServiceException if an error occurs in the DAO layer
	 */
	public List<TariffDTO> getUsersTariff(int userId) throws TariffServiceException;

	/**
	 * 
	 * Retrieves a list of all tariffs for a specific user that have 0 days until
	 * next payment.
	 * 
	 * @param userId the id of the user to retrieve tariffs for
	 * @return a list of tariffs for the specified user with 0 days until next
	 *         payment
	 * @throws TariffServiceException if an error occurs in the DAO layer
	 */
	public List<TariffDTO> getUnpaidTariffs(int userId) throws TariffServiceException;

	/**
	 * 
	 * Retrieves a map of all tariffs for a specific user with the number of days
	 * until next payment.
	 * 
	 * @param userId the id of the user to retrieve tariffs for
	 * @return a map of tariffs for the specified user with the number of days until
	 *         next payment
	 * @throws TariffServiceException if an error occurs in the DAO layer
	 */
	public Map<TariffDTO, Integer> getUsersTariffWithDaysUntilPayment(int userId) throws TariffServiceException;

	/**
	 * 
	 * Removes a Tariff from the persistence layer.
	 * 
	 * @param tariffId ID of the Tariff to remove.
	 * @throws TariffServiceException if an error occurs in the DAO layer.
	 */
	public void removeTariff(int tariffId) throws TariffServiceException;

	/**
	 * 
	 * Adds a Tariff to the persistence layer.
	 * 
	 * @param tariffForm Form containing all necessary information for the new
	 *                   Tariff.
	 * @throws TariffServiceException if an error occurs in the DAO layer.
	 */
	public void addTariff(TariffForm tariffForm) throws TariffServiceException;

	/**
	 * 
	 * Modifies a Tariff.
	 * 
	 * @param tariffForm Form containing new values for the Tariff.
	 * @param tariffId   ID of the Tariff to modify.
	 * @throws TariffServiceException if an error occurs in the DAO layer.
	 */
	public void editTariff(TariffForm tariffForm, int tariffId) throws TariffServiceException;

	/**
	 * 
	 * Decrements the number of days until the next payment for all users.
	 * 
	 * @throws TariffServiceException if an error occurs in the DAO layer.
	 */
	public void updateDaysUntilPayments() throws TariffServiceException;
}
