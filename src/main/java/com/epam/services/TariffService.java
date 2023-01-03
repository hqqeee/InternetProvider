package com.epam.services;

import java.util.List;

import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.forms.TariffForm;
import com.epam.util.SortingOrder;

public interface TariffService {
	public List<Tariff> getTariffsForView(String fieldName, SortingOrder sortingOrder, int serviceId, int page, int entriesPerPage) throws TariffServiceException;
	public int getTariffsCount(int serviceId) throws TariffServiceException;
	public List<Tariff> getAllTariff(int serviceId) throws TariffServiceException;
	public List<Tariff> getUsersTariff(int userId) throws TariffServiceException;
	public void removeTariff(int tariffId) throws TariffServiceException;
	public void addTariff(TariffForm tariffForm) throws TariffServiceException, ValidationErrorException;
	public void editTariff(TariffForm tariffForm, int tariffId) throws TariffServiceException, ValidationErrorException;
}
