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
 * Tariff Service interface.
 * It contains methods to interact with the DAO and has some business logic.
 * 
 * @author ruslan
 *
 */
public interface TariffService {
	
	/**
	 * This method returns sorted and filtered by Service list of Tariffs.
	 * @param fieldName name of the field to sort.
	 * @param sortingOrder sorting order.
	 * @param service A service to show.
	 * @param page page number to show.
	 * @param entriesPerPage number of tariff to show in the page.
	 * @return list of TariffsDTO to show.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public List<TariffDTO> getTariffsForView(String fieldName, SortingOrder sortingOrder, Service service, int page, int entriesPerPage) throws TariffServiceException;
	
	/**
	 * This method returns count of the tariffs with specific Service.
	 * @param service Service to filter.
	 * @return number of tariff with Service.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public int getTariffsCount(Service service) throws TariffServiceException;
	
	/**
	 * This method returns filtered by Service list of Tariffs.
	 * @param service Service to filter.
	 * @return Filtered list of tariffs.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public List<TariffDTO> getAllTariff(Service service) throws TariffServiceException;
	
	/**
	 * This method returns all the tariff of the user.
	 * @param userId id of the user to get id from.
	 * @return List of tariffs of the user.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public List<TariffDTO> getUsersTariff(int userId) throws TariffServiceException;
	
	/**
	 * This method return all the tariff of the user that has 0 days until next payment.
	 * @param userId id of the user to get id from.
	 * @return List of tariffs of the user.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public List<TariffDTO> getUnpaidTariffs(int userId) throws TariffServiceException;
	
	/**
	 * This method returns all the tariff of the user with the number of days left until next payment.
	 * @param userId id of the user to get id from.
	 * @return Map of Tariffs with days until next payment.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public Map<TariffDTO, Integer> getUsersTariffWithDaysUntilPayment(int userId) throws TariffServiceException;
	
	/**
	 * This method remove tariff from the persistence layer.
	 * @param tariffId id of the Tariff to remove.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public void removeTariff(int tariffId) throws TariffServiceException;
	
	/**
	 * This method add tariff to the persistence layer.
	 * @param tariffForm form that contains all necessary information for new tariff.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public void addTariff(TariffForm tariffForm) throws TariffServiceException;
	
	/**
	 * This method modify tariff. 
	 * @param tariffForm Tariff form that contains new values of tariff.
	 * @param tariffId id of the tariff to modify.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public void editTariff(TariffForm tariffForm, int tariffId) throws TariffServiceException;
	
	/**
	 * This method decrement number of days until next payment for all users.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public void updateDaysUntilPayments() throws TariffServiceException;
}
