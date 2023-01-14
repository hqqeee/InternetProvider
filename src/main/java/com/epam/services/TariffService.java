package com.epam.services;

import java.util.List;
import java.util.Map;

import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.TariffForm;
import com.epam.util.SortingOrder;

public interface TariffService {
	public List<TariffDTO> getTariffsForView(String fieldName, SortingOrder sortingOrder, Service service, int page, int entriesPerPage) throws TariffServiceException;
	public int getTariffsCount(Service service) throws TariffServiceException;
	public List<TariffDTO> getAllTariff(Service service) throws TariffServiceException;
	public List<TariffDTO> getUsersTariff(int userId) throws TariffServiceException;
	public List<TariffDTO> getUnpaidTariffs(int userId) throws TariffServiceException;
	public Map<TariffDTO, Integer> getUsersTariffWithDaysUntilPayment(int userId) throws TariffServiceException;
	public void removeTariff(int tariffId) throws TariffServiceException;
	public void addTariff(TariffForm tariffForm) throws TariffServiceException, ValidationErrorException;
	public void editTariff(TariffForm tariffForm, int tariffId) throws TariffServiceException, ValidationErrorException;
	public void updateDaysUntilPayments() throws TariffServiceException;
}
