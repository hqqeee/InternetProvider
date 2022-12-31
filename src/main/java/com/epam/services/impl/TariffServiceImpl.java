package com.epam.services.impl;

import java.util.List;

import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.dao.DAOException;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.TariffService;
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

}
