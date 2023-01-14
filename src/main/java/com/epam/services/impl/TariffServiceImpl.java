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
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.TariffService;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.TariffForm;
import com.epam.util.SortingOrder;

public class TariffServiceImpl implements TariffService {
	private DAOFactory daoFactory;

	private TariffServiceImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

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
			e.printStackTrace();
			throw new TariffServiceException("Cannot get tariffs for vie with service " + service + " fieldName "
					+ fieldName + " sortingOrder " + sortingOrder.getOrder() + " page " + page + " entries per page "
					+ entriesPerPage + ".");

		}
	}

	@Override
	public int getTariffsCount(Service service) throws TariffServiceException {
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			if  (service == Service.ALL) {
				return tariffDAO.getTariffCount();
			}
			return tariffDAO.getTariffCount(service.getId());
		} catch (DAOException e) {
			e.printStackTrace();
			throw new TariffServiceException("Cannot get count of tariffs with service " + service);
		}
	}

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
			e.printStackTrace();
			throw new TariffServiceException("Cannot get all tariff with service " + service);
		}
	}

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
			e.printStackTrace();
			throw new TariffServiceException("Cannot get tariffs for user with id " + userId, e);
		}
	}

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
			e.printStackTrace();
			throw new TariffServiceException("Cannot get unpiad tariffs for user with id " + userId, e);
		}
	}

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
			e.printStackTrace();
			throw new TariffServiceException("Cannot get tariffs for user with id " + userId);
		}
	}

	@Override
	public void removeTariff(int tariffId) throws TariffServiceException {
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			Tariff tariff = new Tariff();
			tariff.setId(tariffId);
			tariffDAO.delete(tariff);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new TariffServiceException("Cannot remove tariff with id " + tariffId);
		}

	}

	@Override
	public void addTariff(TariffForm tariffForm) throws TariffServiceException, ValidationErrorException {
		Tariff tariff = validateTariff(tariffForm);
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			tariffDAO.insert(tariff);
		} catch (DAOException e) {
			throw new TariffServiceException("Connot add tariff.", e);
		}

	}

	@Override
	public void editTariff(TariffForm tariffForm, int tariffId)
			throws TariffServiceException, ValidationErrorException {
		Tariff tariff = validateTariff(tariffForm);
		tariff.setId(tariffId);
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			tariffDAO.update(tariff);
		} catch (DAOException e) {
			throw new TariffServiceException("Connot edit user.", e);
		}

	}

	private Tariff validateTariff(TariffForm form) throws ValidationErrorException {
		List<String> errors = new ArrayList<>();
		validateTextFieldValues(errors, form.getName(), "name", 32);
		validateTextFieldValues(errors, form.getDescription(), "description", 255);
		if (form.getService() == Service.ALL) {
			errors.add("Invalid service. Try again.");
		}
		if (form.getRate().signum() <= 0) {
			errors.add("Invalid price. Price must be greater than 0.");
		}
		if (form.getPaymentPeriod() <= 0) {
			errors.add("Payment period must be greater than 0.");
		}
		if (errors.isEmpty()) {
			Tariff tariff = new Tariff();
			tariff.setName(form.getName());
			tariff.setRate(form.getRate());
			tariff.setPaymentPeriod(form.getPaymentPeriod());
			tariff.setDescription(form.getDescription());
			tariff.setServiceId(form.getService().getId());
			return tariff;
		} else
			throw new ValidationErrorException(errors);
	}

	private static void validateTextFieldValues(List<String> errors, String fieldValue, String fieldName,
			int maxLength) {
		if (isEmpty(fieldValue)) {
			errors.add(fieldName + " is empty.");
		} else if (fieldValue.trim().length() > maxLength) {
			errors.add(fieldName + " must not exceed " + maxLength + " characters.");
		}
	}

	private static boolean isEmpty(String fieldValue) {
		return fieldValue == null || fieldValue.trim().isEmpty();
	}

	@Override
	public void updateDaysUntilPayments() throws TariffServiceException {
		try {
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			tariffDAO.updateDaysLeftForUnblockedUsers();

		} catch (DAOException e) {
			throw new TariffServiceException("Cannot update days until payment", e);
		}

	}

	private TariffDTO convertTariffToTariffDTO(Tariff tariff) {
		return new TariffDTO(tariff.getId(), tariff.getName(), tariff.getDescription(), tariff.getPaymentPeriod(),
				tariff.getRate(), Service.valueOf(tariff.getServiceId()));
	}
}
