package com.epam.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.dao.DAOException;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.TariffService;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.TariffForm;
import com.epam.util.SortingOrder;


/**
 * 
 * Tariff Service implementation.
 * It contains methods to interact with the DAO and has some business logic.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class TariffServiceImpl implements TariffService {
	private DAOFactory daoFactory;


	/**
	 * A constructor that assigns a DAO Factory.
	 * 
	 * @param daoFactory DAO Factory impl.
	 */
	private TariffServiceImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

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
	@Override
	public List<TariffDTO> getTariffsForView(String fieldName, SortingOrder sortingOrder, Service service, int page,
			int entriesPerPage) throws TariffServiceException {
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			List<Tariff> tariffs = new ArrayList<>();
			if (service == Service.ALL) {
				tariffs = tariffDAO.getAll(entriesPerPage * (page - 1), entriesPerPage, sortingOrder, fieldName);
			} else {
				tariffs = tariffDAO.getAll(service.getId(), entriesPerPage * (page - 1), entriesPerPage, sortingOrder,
						fieldName);
			}
			List<TariffDTO> tariffDTOs = new ArrayList<>();
			tariffs.forEach(t -> tariffDTOs.add(convertTariffToTariffDTO(t)));
			return tariffDTOs;
		} catch (DAOException e) {
			throw new TariffServiceException("Cannot get tariffs for vie with service " + service + " fieldName "
					+ fieldName + " sortingOrder " + sortingOrder.getOrder() + " page " + page + " entries per page "
					+ entriesPerPage + ".");

		}
	}

	/**
	 * This method returns count of the tariffs with specific Service.
	 * @param service Service to filter.
	 * @return number of tariff with Service.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	@Override
	public int getTariffsCount(Service service) throws TariffServiceException {
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			if  (service == Service.ALL) {
				return tariffDAO.getTariffCount();
			}
			return tariffDAO.getTariffCount(service.getId());
		} catch (DAOException e) {
			throw new TariffServiceException("Cannot get count of tariffs with service " + service);
		}
	}

	/**
	 * This method returns filtered by Service list of Tariffs.
	 * @param service Service to filter.
	 * @return Filtered list of tariffs.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	@Override
	public List<TariffDTO> getAllTariff(Service service) throws TariffServiceException {
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			List<Tariff> tariffs = new ArrayList<>();
			if (service == Service.ALL) {
				tariffs = tariffDAO.getAll();
			} else {
				tariffs = tariffDAO.getAll(service.getId());
			}
			List<TariffDTO> tariffDTOs = new ArrayList<>();
			tariffs.forEach(t -> tariffDTOs.add(convertTariffToTariffDTO(t)));
			return tariffDTOs;
		} catch (DAOException e) {
			throw new TariffServiceException("Cannot get all tariff with service " + service);
		}
	}

	/**
	 * This method returns all the tariff of the user.
	 * @param userId id of the user to get id from.
	 * @return List of tariffs of the user.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	@Override
	public List<TariffDTO> getUsersTariff(int userId) throws TariffServiceException {

		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			List<Tariff> tariffs = new ArrayList<>();
			tariffs =  tariffDAO.getUsersTariffs(userId);
			List<TariffDTO> tariffDTOs = new ArrayList<>();
			tariffs.forEach(t -> tariffDTOs.add(convertTariffToTariffDTO(t)));
			return tariffDTOs;
		} catch (DAOException e) {
			throw new TariffServiceException("Cannot get tariffs for user with id " + userId, e);
		}
	}

	/**
	 * This method return all the tariff of the user that has 0 days until next payment.
	 * @param userId id of the user to get id from.
	 * @return List of tariffs of the user.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	@Override
	public List<TariffDTO> getUnpaidTariffs(int userId) throws TariffServiceException {
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			List<Tariff> tariffs = new ArrayList<>();
			tariffs = tariffDAO.getUsersUnpaidTariffs(userId);
			List<TariffDTO> tariffDTOs = new ArrayList<>();
			tariffs.forEach(t -> tariffDTOs.add(convertTariffToTariffDTO(t)));
			return tariffDTOs;
		} catch (DAOException e) {
			throw new TariffServiceException("Cannot get unpiad tariffs for user with id " + userId, e);
		}
	}

	/**
	 * This method returns all the tariff of the user with the number of days left until next payment.
	 * @param userId id of the user to get id from.
	 * @return Map of Tariffs with days until next payment.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	@Override
	public Map<TariffDTO, Integer> getUsersTariffWithDaysUntilPayment(int userId) throws TariffServiceException {
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			Map<Tariff, Integer> tariffsWithDaysUntilPayment = new HashMap<>();
			tariffsWithDaysUntilPayment = tariffDAO.getUsersTariffsWithDayToPayment(userId);
			Map<TariffDTO, Integer> tariffDTOsWithDaysUntilPayment = new HashMap<>();
			tariffsWithDaysUntilPayment.forEach((t,i) -> tariffDTOsWithDaysUntilPayment.put(convertTariffToTariffDTO(t), i));
			return tariffDTOsWithDaysUntilPayment;
		} catch (DAOException e) {
			throw new TariffServiceException("Cannot get tariffs for user with id " + userId);
		}
	}

	/**
	 * This method remove tariff from the persistence layer.
	 * @param tariffId id of the Tariff to remove.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	@Override
	public void removeTariff(int tariffId) throws TariffServiceException {
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			Tariff tariff = new Tariff();
			tariff.setId(tariffId);
			tariffDAO.delete(tariff);
		} catch (DAOException e) {
			throw new TariffServiceException("Cannot remove tariff with id " + tariffId);
		}

	}

	/**
	 * This method add tariff to the persistence layer.
	 * @param tariffForm form that contains all necessary information for new tariff.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	@Override
	public void addTariff(TariffForm tariffForm) throws TariffServiceException {
		Tariff tariff = new Tariff();
		tariff.setName(tariffForm.getName());
		tariff.setRate(tariffForm.getRate());
		tariff.setPaymentPeriod(tariffForm.getPaymentPeriod());
		tariff.setDescription(tariffForm.getDescription());
		tariff.setServiceId(tariffForm.getService().getId());
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			tariffDAO.insert(tariff);
		} catch (DAOException e) {
			throw new TariffServiceException("Cannot add tariff.", e);
		}

	}

	/**
	 * This method modify tariff. 
	 * @param tariffForm Tariff form that contains new values of tariff.
	 * @param tariffId id of the tariff to modify.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	@Override
	public void editTariff(TariffForm tariffForm, int tariffId)
			throws TariffServiceException {
		Tariff tariff = new Tariff();
		tariff.setName(tariffForm.getName());
		tariff.setRate(tariffForm.getRate());
		tariff.setPaymentPeriod(tariffForm.getPaymentPeriod());
		tariff.setDescription(tariffForm.getDescription());
		tariff.setServiceId(tariffForm.getService().getId());
		tariff.setId(tariffId);
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			tariffDAO.update(tariff);
		} catch (DAOException e) {
			throw new TariffServiceException("Cannot edit user.", e);
		}

	}

	/**
	 * This method decrement number of days until next payment for all users.
	 * @throws TariffServiceException is thrown when something wrong happens in the DAO layer.
	 */
	@Override
	public void updateDaysUntilPayments() throws TariffServiceException {
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			tariffDAO.updateDaysLeftForUnblockedUsers();

		} catch (DAOException e) {
			throw new TariffServiceException("Cannot update days until payment", e);
		}

	}

	/**
	 * This method convert Tariff(DAO) entity to TariffDTO
	 * @param tariff tariff(DAO) entity
	 * @return converted TariffDTO
	 */
	protected TariffDTO convertTariffToTariffDTO(Tariff tariff) {
		return new TariffDTO(tariff.getId(), tariff.getName(), tariff.getDescription(), tariff.getPaymentPeriod(),
				tariff.getRate(), Service.valueOf(tariff.getServiceId()));
	}
}
