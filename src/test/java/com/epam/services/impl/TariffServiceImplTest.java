package com.epam.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.dao.DAOException;
import com.epam.exception.services.TariffServiceException;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.TariffForm;
import com.epam.util.SortingOrder;

class TariffServiceImplTest {

	private Tariff testTariff = new Tariff(0, "tariffName", "desc", 14, BigDecimal.ONE, Service.INTERNET.getId());
	private TariffForm testTariffForm = new TariffForm("tariffName", 14, BigDecimal.ONE, Service.INTERNET, "desc");
	private TariffDTO testTariffDTO = null;

	@Mock
	private TariffDAO tariffDAO;

	@Mock
	private DAOFactory daoFactory;

	@Mock
	private TariffServiceImpl tariffService;

	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MockitoAnnotations.openMocks(this);
		@SuppressWarnings("unchecked")
		Constructor<TariffServiceImpl> tariffServiceConstructor = (Constructor<TariffServiceImpl>) Class
				.forName(TariffServiceImpl.class.getName()).getDeclaredConstructor(DAOFactory.class);
		tariffServiceConstructor.setAccessible(true);
		tariffService = tariffServiceConstructor.newInstance(daoFactory);
		testTariffDTO = tariffService.convertTariffToTariffDTO(testTariff);
		Mockito.when(daoFactory.getTariffDAO()).thenReturn(tariffDAO);
	}

	@Test
	void testGetAllTariffsForView() throws DAOException, TariffServiceException {
		List<Tariff> tariffs = new ArrayList<>();
		tariffs.add(testTariff);
		Tariff tariff2 = new Tariff(2, "tariff2", "desc ", 14, BigDecimal.ONE, 1);
		tariffs.add(tariff2);
		List<TariffDTO> tariffsDTOs = new ArrayList<>();
		tariffsDTOs.add(testTariffDTO);
		tariffsDTOs.add(tariffService.convertTariffToTariffDTO(tariff2));
		Mockito.when(tariffDAO.getAll(0, 2, SortingOrder.ASC, "name")).thenReturn(tariffs);
		assertEquals(tariffsDTOs, tariffService.getTariffsForView("name", SortingOrder.ASC, Service.ALL, 1, 2));
	}

	@Test
	void testGetWithServiceTariffsForView() throws DAOException, TariffServiceException {
		List<Tariff> tariffs = new ArrayList<>();
		tariffs.add(testTariff);
		Tariff tariff2 = new Tariff(2, "tariff2", "desc ", 14, BigDecimal.ONE, 1);
		tariffs.add(tariff2);
		List<TariffDTO> tariffsDTOs = new ArrayList<>();
		tariffsDTOs.add(testTariffDTO);
		tariffsDTOs.add(tariffService.convertTariffToTariffDTO(tariff2));
		Mockito.when(tariffDAO.getAll(Service.INTERNET.getId(), 0, 2, SortingOrder.ASC, "name")).thenReturn(tariffs);
		assertEquals(tariffsDTOs, tariffService.getTariffsForView("name", SortingOrder.ASC, Service.INTERNET, 1, 2));
	}

	@Test
	void testGetTariffsForViewDAOException() throws DAOException {
		Mockito.when(tariffDAO.getAll(Service.INTERNET.getId(), 0, 2, SortingOrder.ASC, "name"))
				.thenThrow(DAOException.class);
		assertThrows(TariffServiceException.class,
				() -> tariffService.getTariffsForView("name", SortingOrder.ASC, Service.INTERNET, 1, 2));
	}

	@Test
	void testGetAllTariffsCount() throws DAOException, TariffServiceException {
		Mockito.when(tariffDAO.getTariffCount()).thenReturn(5);
		assertEquals(5, tariffService.getTariffsCount(Service.ALL));
	}

	@Test
	void testGetWithServiceTariffsCount() throws DAOException, TariffServiceException {
		Mockito.when(tariffDAO.getTariffCount(Service.INTERNET.getId())).thenReturn(5);
		assertEquals(5, tariffService.getTariffsCount(Service.INTERNET));
	}

	@Test
	void testGetTariffsCountDAOException() throws DAOException {
		Mockito.when(tariffDAO.getTariffCount(Service.INTERNET.getId())).thenThrow(DAOException.class);
		assertThrows(TariffServiceException.class, () -> tariffService.getTariffsCount(Service.INTERNET));
	}

	@Test
	void testGetAllTariffAllServices() throws DAOException, TariffServiceException {
		List<Tariff> tariffs = new ArrayList<>();
		tariffs.add(testTariff);
		Tariff tariff2 = new Tariff(2, "tariff2", "desc ", 14, BigDecimal.ONE, 1);
		tariffs.add(tariff2);
		List<TariffDTO> tariffsDTOs = new ArrayList<>();
		tariffsDTOs.add(testTariffDTO);
		tariffsDTOs.add(tariffService.convertTariffToTariffDTO(tariff2));
		Mockito.when(tariffDAO.getAll()).thenReturn(tariffs);
		assertEquals(tariffsDTOs, tariffService.getAllTariff(Service.ALL));
	}

	@Test
	void testGetAllTariffWithService() throws DAOException, TariffServiceException {
		List<Tariff> tariffs = new ArrayList<>();
		tariffs.add(testTariff);
		Tariff tariff2 = new Tariff(2, "tariff2", "desc ", 14, BigDecimal.ONE, 1);
		tariffs.add(tariff2);
		List<TariffDTO> tariffsDTOs = new ArrayList<>();
		tariffsDTOs.add(testTariffDTO);
		tariffsDTOs.add(tariffService.convertTariffToTariffDTO(tariff2));
		Mockito.when(tariffDAO.getAll(Service.IP_TV.getId())).thenReturn(tariffs);
		assertEquals(tariffsDTOs, tariffService.getAllTariff(Service.IP_TV));
	}

	@Test
	void testGetAllTariffDAOException() throws DAOException, TariffServiceException {
		Mockito.when(tariffDAO.getAll(Service.IP_TV.getId())).thenThrow(DAOException.class);
		assertThrows(TariffServiceException.class, () -> tariffService.getAllTariff(Service.IP_TV));
	}

	@Test
	void testGetUsersTariff() throws TariffServiceException, DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		tariffs.add(testTariff);
		Tariff tariff2 = new Tariff(2,"tariff2", "desc ", 14, BigDecimal.ONE, 1);
		tariffs.add(tariff2);
		List<TariffDTO> tariffsDTOs = new ArrayList<>();
		tariffsDTOs.add(testTariffDTO);
		tariffsDTOs.add(tariffService.convertTariffToTariffDTO(tariff2));
		Mockito.when(tariffDAO.getUsersTariffs(1)).thenReturn(tariffs);
		assertEquals(tariffsDTOs, tariffService.getUsersTariff(1));
	}
	
	@Test
	void testGetUsersTariffDAOException() throws DAOException {
		Mockito.when(tariffDAO.getUsersTariffs(3)).thenThrow(DAOException.class);
		assertThrows(TariffServiceException.class,() -> tariffService.getUsersTariff(3));
	}
	
	@Test
	void testGetUnpaidTariffs() throws DAOException, TariffServiceException {
		List<Tariff> tariffs = new ArrayList<>();
		tariffs.add(testTariff);
		List<TariffDTO> tariffsDTOs = new ArrayList<>();
		tariffsDTOs.add(testTariffDTO);
		Mockito.when(tariffDAO.getUsersUnpaidTariffs(4)).thenReturn(tariffs);
		assertEquals(tariffsDTOs, tariffService.getUnpaidTariffs(4));
	}
	
	@Test
	void testGetUnpaidTariffsDAOException() throws DAOException {
		Mockito.when(tariffDAO.getUsersUnpaidTariffs(4)).thenThrow(DAOException.class);
		assertThrows(TariffServiceException.class,  () -> tariffService.getUnpaidTariffs(4));
	}
	
	@Test
	void testGetUsersTariffWithDaysUntilPayment() throws DAOException, TariffServiceException {
		Map<Tariff, Integer> tariffsWithDays = new HashMap<>();
		tariffsWithDays.put(testTariff, 6);
		Map<TariffDTO, Integer> tariffDTOsWithDays = new HashMap<>();
		tariffDTOsWithDays.put(testTariffDTO, 6);
		Mockito.when(tariffDAO.getUsersTariffsWithDayToPayment(3)).thenReturn(tariffsWithDays);
		assertEquals(tariffDTOsWithDays, tariffService.getUsersTariffWithDaysUntilPayment(3));
	}
	
	@Test
	void testGetUsersTariffWithDaysUntilPaymentDAOException() throws DAOException {
		Mockito.when(tariffDAO.getUsersTariffsWithDayToPayment(3)).thenThrow(DAOException.class);
		assertThrows(TariffServiceException.class, () -> tariffService.getUsersTariffWithDaysUntilPayment(3));
	}
	
	@Test
	void testRemoveTariffDAOException() throws DAOException {
		Tariff tariffNew = new Tariff();
		tariffNew.setId(1);
		Mockito.when(tariffDAO.delete(tariffNew)).thenThrow(DAOException.class);
		assertThrows(TariffServiceException.class, () -> tariffService.removeTariff(1));
	}
	
	@Test
	void testAddTariffDAOException() throws DAOException {
		Mockito.when(tariffDAO.insert(testTariff)).thenThrow(DAOException.class);
		assertThrows(TariffServiceException.class, () -> tariffService.addTariff(testTariffForm));
	}
	
	@Test
	void testEditTariffDAOException() throws DAOException {
		Mockito.when(tariffDAO.update(testTariff)).thenThrow(DAOException.class);
		assertThrows(TariffServiceException.class, () -> tariffService.editTariff(testTariffForm, 0));
	}
	
	@Test
	void testUpdateDaysUntilPayment() throws DAOException {
		doThrow(DAOException.class).when(tariffDAO).updateDaysLeftForUnblockedUsers();
		assertThrows(TariffServiceException.class, () -> tariffService.updateDaysUntilPayments());
	}
}
