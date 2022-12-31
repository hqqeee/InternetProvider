package com.epam.services;

import java.util.List;

import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.services.TariffServiceException;
import com.epam.util.SortingOrder;

public interface TariffService {
	public List<Tariff> getTariffsForView(String fieldName, SortingOrder sortingOrder, int serviceId, int page, int entriesPerPage) throws TariffServiceException;
	public int getTariffsCount(int serviceId) throws TariffServiceException;
	public List<Tariff> getAllTariff(int serviceId) throws TariffServiceException;
	public List<Tariff> getUsersTariff(int userId) throws TariffServiceException;
}
