package com.epam.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.dao.DAOException;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.TariffService;
import com.epam.services.forms.TariffForm;
import com.epam.util.SortingOrder;

public class TariffServiceImpl implements TariffService {
	private TariffDAO tariffDAO;

	private TariffServiceImpl(TariffDAO tariffDAO) {
		this.tariffDAO = tariffDAO;
	}

	@Override
	public List<Tariff> getTariffsForView(String fieldName, SortingOrder sortingOrder, int serviceId, int page,
			int entriesPerPage) throws TariffServiceException {
		try {
			if (serviceId == 0) {
				return tariffDAO.getAll(entriesPerPage * (page - 1), entriesPerPage, sortingOrder, fieldName);
			}
			return tariffDAO.getAll(serviceId, entriesPerPage * (page - 1), entriesPerPage, sortingOrder, fieldName);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new TariffServiceException("Cannot get tariffs for vie with service id " + serviceId + " fieldName "
					+ fieldName + " sortingOrder " + sortingOrder.getOrder() + " page " + page + " entries per page "
					+ entriesPerPage + ".");

		}
	}

	@Override
	public int getTariffsCount(int serviceId) throws TariffServiceException {
		try {
			if(serviceId == 0) {
				return tariffDAO.getTariffCount();
			} 
			return tariffDAO.getTariffCount(serviceId);
		}
		catch (DAOException e) {
			e.printStackTrace();
			throw new TariffServiceException("Cannot get count of tariffs with service id "+ serviceId);
		}
	}

	@Override
	public List<Tariff> getAllTariff(int serviceId) throws TariffServiceException {
		try {
			if(serviceId == 0) {
				return tariffDAO.getAll();
			} 
			return tariffDAO.getAll(serviceId);
		}
		catch (DAOException e) {
			e.printStackTrace();
			throw new TariffServiceException("Cannot get all tariff with service id "+ serviceId);
		}
	}

	@Override
	public List<Tariff> getUsersTariff(int userId) throws TariffServiceException {
		
		try {
			return tariffDAO.getUsersTariffs(userId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new TariffServiceException("Cannot get tariffs for user with id " + userId);
		}
	}

	@Override
	public void removeTariff(int tariffId) throws TariffServiceException {
		try {
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
			tariffDAO.insert(tariff);
		} catch (DAOException e) {
			throw new TariffServiceException("Connot add user.", e);
		}
		
	}

	@Override
	public void editTariff(TariffForm tariffForm, int tariffId) throws TariffServiceException, ValidationErrorException {
		Tariff tariff = validateTariff(tariffForm);
		tariff.setId(tariffId);
		try {
			tariffDAO.update(tariff);
		} catch(DAOException e) {
			throw new TariffServiceException("Connot edit user.", e);
		}
		
	}
	
	private Tariff validateTariff(TariffForm form) throws ValidationErrorException{
		List<String> errors = new ArrayList<>();
		validateTextFieldValues(errors, form.getName(), "name", 32);
		validateTextFieldValues(errors, form.getDescription(), "description" , 255);
		if(form.getServiceId() < 1 || form.getServiceId() > 4) {
			errors.add("Invalid service Id. Service id must be between 1 and 4.");
		}
		if (form.getPrice().signum() <= 0) {
			errors.add("Invalid price. Price must be greater than 0.");
		}
		if(errors.isEmpty()) {
			Tariff tariff = new Tariff();
			tariff.setName(form.getName());
			tariff.setPrice(form.getPrice());
			tariff.setDescription(form.getDescription());
			tariff.setServiceId(form.getServiceId());
			return tariff;
		}
		else throw new ValidationErrorException(errors);
	}

	private static void validateTextFieldValues(List<String> errors, String fieldValue, String fieldName, int maxLength) {
		if(isEmpty(fieldValue)) {
			errors.add(fieldName + " is empty.");
		} else if(fieldValue.trim().length() > maxLength) {
			errors.add(fieldName + " must not exceed " + maxLength +" characters.");
		} 
	}
	private static boolean isEmpty(String fieldValue) {
		return fieldValue ==null || fieldValue.trim().isEmpty();
	}

}
