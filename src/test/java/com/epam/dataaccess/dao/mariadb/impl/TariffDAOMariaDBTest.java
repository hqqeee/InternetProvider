package com.epam.dataaccess.dao.mariadb.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.epam.dataaccess.dao.mariadb.datasource.QueryBuilder;
import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.dao.DAOException;
import com.epam.util.SortingOrder;

class TariffDAOMariaDBTest {

	@Mock
	private QueryBuilder mockQueryBuilder = mock(QueryBuilder.class);

	@Mock
	private ResultSet mockResultSet = mock(ResultSet.class);
	
	private TariffDAOMariaDB tariffDAOMariaDB; 
	
	@BeforeEach
	void setUp() throws Exception {
		tariffDAOMariaDB = new TariffDAOMariaDB() {
			@Override
			protected QueryBuilder getQueryBuilder() throws SQLException {
				return mockQueryBuilder;
			}
		};
	}
	
	@Test
	void testGetTariff() throws SQLException, DAOException {
		Tariff expectedTariff = new Tariff();
		expectedTariff.setId(1);
		expectedTariff.setName("tariff name");
		expectedTariff.setDescription("tariff desc");
		expectedTariff.setPaymentPeriod(7);
		expectedTariff.setRate(BigDecimal.ONE);
		expectedTariff.setServiceId(1);
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_ID_FIELD)).thenReturn(expectedTariff.getId());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_NAME_FIELD)).thenReturn(expectedTariff.getName());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_DESCRIPTION_FIELD)).thenReturn(expectedTariff.getDescription());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_PAYMENT_PERIOD_FIELD)).thenReturn(expectedTariff.getPaymentPeriod());
		when(mockResultSet.getBigDecimal(MariaDBConstants.TARIFF_RATE_FIELD)).thenReturn(expectedTariff.getRate());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_SERVICE_ID_FIELD)).thenReturn(expectedTariff.getServiceId());
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_TARIFF_BY_ID)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(expectedTariff.getId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		Tariff actual = tariffDAOMariaDB.get(expectedTariff.getId());
		verify(mockQueryBuilder, times(1)).setIntField(expectedTariff.getId());
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(expectedTariff, actual);
		
	}

	@Test
	void testGetAllTariffs() throws SQLException, DAOException {
		Tariff expectedTariff1 = new Tariff();
		expectedTariff1.setId(1);
		expectedTariff1.setName("tariff1 name");
		expectedTariff1.setDescription("tariff1 desc");
		expectedTariff1.setPaymentPeriod(7);
		expectedTariff1.setRate(BigDecimal.ONE);
		expectedTariff1.setServiceId(1);
		
		Tariff expectedTariff2 = new Tariff();
		expectedTariff2.setId(2);
		expectedTariff2.setName("tariff2 name");
		expectedTariff2.setDescription("tariff2 desc");
		expectedTariff2.setPaymentPeriod(30);
		expectedTariff2.setRate(BigDecimal.TEN);
		expectedTariff2.setServiceId(2);
		
		List<Tariff> expectedTariffList = new ArrayList<>();
		expectedTariffList.add(expectedTariff1);
		expectedTariffList.add(expectedTariff2);
		
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_ID_FIELD)).thenReturn(expectedTariff1.getId()).thenReturn(expectedTariff2.getId());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_NAME_FIELD)).thenReturn(expectedTariff1.getName()).thenReturn(expectedTariff2.getName());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_DESCRIPTION_FIELD)).thenReturn(expectedTariff1.getDescription()).thenReturn(expectedTariff2.getDescription());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_PAYMENT_PERIOD_FIELD)).thenReturn(expectedTariff1.getPaymentPeriod()).thenReturn(expectedTariff2.getPaymentPeriod());
		when(mockResultSet.getBigDecimal(MariaDBConstants.TARIFF_RATE_FIELD)).thenReturn(expectedTariff1.getRate()).thenReturn(expectedTariff2.getRate());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_SERVICE_ID_FIELD)).thenReturn(expectedTariff1.getServiceId()).thenReturn(expectedTariff2.getServiceId());
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_ALL_TARIFFS)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		List<Tariff> actualTariffList = tariffDAOMariaDB.getAll();
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(expectedTariffList, actualTariffList);
	}
	
	@Test
	void testInsertTariff() throws SQLException, DAOException {
		Tariff tariffToInsert = new Tariff(1,"name","name", 1, BigDecimal.ONE, 1);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.ADD_TARIFF)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(tariffToInsert.getId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(tariffToInsert.getName())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBigDecimalField(tariffToInsert.getRate())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int insertedId = tariffDAOMariaDB.insert(tariffToInsert);
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.ADD_TARIFF);
		verify(mockQueryBuilder, times(2)).setIntField(tariffToInsert.getId());
		verify(mockQueryBuilder, times(1)).setBigDecimalField(tariffToInsert.getRate());
		verify(mockQueryBuilder, times(2)).setStringField(tariffToInsert.getDescription());
		verify(mockQueryBuilder, times(1)).executeUpdate();
		assertNotEquals(0, insertedId);
	}
	
	@Test
	void testUpdateTariff() throws SQLException, DAOException {
		Tariff tariffToUpdate =  new Tariff(1,"name","name", 1, BigDecimal.ONE, 1);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.UPDATE_TARIFF)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(tariffToUpdate.getId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(tariffToUpdate.getName())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBigDecimalField(tariffToUpdate.getRate())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int updateNumber = tariffDAOMariaDB.update(tariffToUpdate);
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.UPDATE_TARIFF);
		verify(mockQueryBuilder, times(3)).setIntField(tariffToUpdate.getId());
		verify(mockQueryBuilder, times(1)).setBigDecimalField(tariffToUpdate.getRate());
		verify(mockQueryBuilder, times(2)).setStringField(tariffToUpdate.getDescription());
		verify(mockQueryBuilder, times(1)).executeUpdate();
		assertNotEquals(0, updateNumber);
	}
	
	@Test
	void testDeleteTariff() throws SQLException, DAOException {
		Tariff tariffToDelete = new Tariff(1,"name","name", 1, BigDecimal.ONE, 1);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.DELETE_TARIFF)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(tariffToDelete.getId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int deleteNumber = tariffDAOMariaDB.delete(tariffToDelete);
		verify(mockQueryBuilder, times(1)).addPreparedStatement(MariaDBConstants.DELETE_TARIFF);
		verify(mockQueryBuilder, times(1)).setIntField(tariffToDelete.getId());
		assertNotEquals(0, deleteNumber);
	}
	
	@Test
	void testGetAllWithServiceId() throws SQLException, DAOException {
		Tariff expectedTariff1 = new Tariff();
		expectedTariff1.setId(1);
		expectedTariff1.setName("tariff1 name");
		expectedTariff1.setDescription("tariff1 desc");
		expectedTariff1.setPaymentPeriod(7);
		expectedTariff1.setRate(BigDecimal.ONE);
		expectedTariff1.setServiceId(1);
		
		Tariff expectedTariff2 = new Tariff();
		expectedTariff2.setId(2);
		expectedTariff2.setName("tariff2 name");
		expectedTariff2.setDescription("tariff2 desc");
		expectedTariff2.setPaymentPeriod(30);
		expectedTariff2.setRate(BigDecimal.TEN);
		expectedTariff2.setServiceId(2);
		
		List<Tariff> expectedTariffList = new ArrayList<>();
		expectedTariffList.add(expectedTariff1);
		expectedTariffList.add(expectedTariff2);
		
		String fieldName = "filedName";
		SortingOrder order = SortingOrder.DEFAULT;
		
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_ID_FIELD)).thenReturn(expectedTariff1.getId()).thenReturn(expectedTariff2.getId());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_NAME_FIELD)).thenReturn(expectedTariff1.getName()).thenReturn(expectedTariff2.getName());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_DESCRIPTION_FIELD)).thenReturn(expectedTariff1.getDescription()).thenReturn(expectedTariff2.getDescription());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_PAYMENT_PERIOD_FIELD)).thenReturn(expectedTariff1.getPaymentPeriod()).thenReturn(expectedTariff2.getPaymentPeriod());
		when(mockResultSet.getBigDecimal(MariaDBConstants.TARIFF_RATE_FIELD)).thenReturn(expectedTariff1.getRate()).thenReturn(expectedTariff2.getRate());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_SERVICE_ID_FIELD)).thenReturn(expectedTariff1.getServiceId()).thenReturn(expectedTariff2.getServiceId());
		when(mockQueryBuilder.addPreparedStatement(
				MariaDBConstants.GET_ALL_TARIFFS + MariaDBConstants.FILTER_TARIFF_BY_SERVICE_ID
				+ MariaDBConstants.ORDER_BY + fieldName + " " + order.getOrder() + MariaDBConstants.LIMIT
				)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(1)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		List<Tariff> actualTariffList = tariffDAOMariaDB.getAll(1,1,1,order, fieldName);
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(expectedTariffList, actualTariffList);
	}
	
	@Test
	void testGetAllWithoutServiceId() throws SQLException, DAOException {
		Tariff expectedTariff1 = new Tariff();
		expectedTariff1.setId(1);
		expectedTariff1.setName("tariff1 name");
		expectedTariff1.setDescription("tariff1 desc");
		expectedTariff1.setPaymentPeriod(7);
		expectedTariff1.setRate(BigDecimal.ONE);
		expectedTariff1.setServiceId(1);
		
		Tariff expectedTariff2 = new Tariff();
		expectedTariff2.setId(2);
		expectedTariff2.setName("tariff2 name");
		expectedTariff2.setDescription("tariff2 desc");
		expectedTariff2.setPaymentPeriod(30);
		expectedTariff2.setRate(BigDecimal.TEN);
		expectedTariff2.setServiceId(2);
		
		List<Tariff> expectedTariffList = new ArrayList<>();
		expectedTariffList.add(expectedTariff1);
		expectedTariffList.add(expectedTariff2);
		
		String fieldName = "filedName";
		SortingOrder order = SortingOrder.DEFAULT;
		
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_ID_FIELD)).thenReturn(expectedTariff1.getId()).thenReturn(expectedTariff2.getId());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_NAME_FIELD)).thenReturn(expectedTariff1.getName()).thenReturn(expectedTariff2.getName());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_DESCRIPTION_FIELD)).thenReturn(expectedTariff1.getDescription()).thenReturn(expectedTariff2.getDescription());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_PAYMENT_PERIOD_FIELD)).thenReturn(expectedTariff1.getPaymentPeriod()).thenReturn(expectedTariff2.getPaymentPeriod());
		when(mockResultSet.getBigDecimal(MariaDBConstants.TARIFF_RATE_FIELD)).thenReturn(expectedTariff1.getRate()).thenReturn(expectedTariff2.getRate());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_SERVICE_ID_FIELD)).thenReturn(expectedTariff1.getServiceId()).thenReturn(expectedTariff2.getServiceId());
		when(mockQueryBuilder.addPreparedStatement(
				MariaDBConstants.GET_ALL_TARIFFS + MariaDBConstants.ORDER_BY + fieldName 
				+ " " + order.getOrder() + MariaDBConstants.LIMIT)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(1)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		List<Tariff> actualTariffList = tariffDAOMariaDB.getAll(1,1,order, fieldName);
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(expectedTariffList, actualTariffList);
	}
	
	@Test
	void testGetTariffWithServiceIdCount() throws SQLException, DAOException {
		int serviceId = 1;
		int tariffCount = 2;
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_COUNT_OF_TARIFF_BY_SERVICE_ID)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(serviceId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(1)).thenReturn(tariffCount);
		assertEquals(tariffCount, tariffDAOMariaDB.getTariffCount(serviceId));
	}
	
	@Test
	void testGetTariffCount() throws SQLException, DAOException {
		int tariffCount = 2;
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_COUNT_OF_TARIFFS)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(1)).thenReturn(tariffCount);
		assertEquals(tariffCount, tariffDAOMariaDB.getTariffCount());
	}
	
	@Test
	void testGetAllByServiceId() throws SQLException, DAOException {
		Tariff expectedTariff1 = new Tariff();
		expectedTariff1.setId(1);
		expectedTariff1.setName("tariff1 name");
		expectedTariff1.setDescription("tariff1 desc");
		expectedTariff1.setPaymentPeriod(7);
		expectedTariff1.setRate(BigDecimal.ONE);
		expectedTariff1.setServiceId(1);
		
		Tariff expectedTariff2 = new Tariff();
		expectedTariff2.setId(2);
		expectedTariff2.setName("tariff2 name");
		expectedTariff2.setDescription("tariff2 desc");
		expectedTariff2.setPaymentPeriod(30);
		expectedTariff2.setRate(BigDecimal.TEN);
		expectedTariff2.setServiceId(2);
		
		List<Tariff> expectedTariffList = new ArrayList<>();
		expectedTariffList.add(expectedTariff1);
		expectedTariffList.add(expectedTariff2);
		
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_ID_FIELD)).thenReturn(expectedTariff1.getId()).thenReturn(expectedTariff2.getId());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_NAME_FIELD)).thenReturn(expectedTariff1.getName()).thenReturn(expectedTariff2.getName());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_DESCRIPTION_FIELD)).thenReturn(expectedTariff1.getDescription()).thenReturn(expectedTariff2.getDescription());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_PAYMENT_PERIOD_FIELD)).thenReturn(expectedTariff1.getPaymentPeriod()).thenReturn(expectedTariff2.getPaymentPeriod());
		when(mockResultSet.getBigDecimal(MariaDBConstants.TARIFF_RATE_FIELD)).thenReturn(expectedTariff1.getRate()).thenReturn(expectedTariff2.getRate());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_SERVICE_ID_FIELD)).thenReturn(expectedTariff1.getServiceId()).thenReturn(expectedTariff2.getServiceId());
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_ALL_TARIFFS + MariaDBConstants.FILTER_TARIFF_BY_SERVICE_ID)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(1)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		List<Tariff> actualTariffList = tariffDAOMariaDB.getAll(1);
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(expectedTariffList, actualTariffList);
	}
	
	@Test
	void testGetUsersTariffs() throws SQLException, DAOException {
		Tariff expectedTariff1 = new Tariff();
		expectedTariff1.setId(1);
		expectedTariff1.setName("tariff1 name");
		expectedTariff1.setDescription("tariff1 desc");
		expectedTariff1.setPaymentPeriod(7);
		expectedTariff1.setRate(BigDecimal.ONE);
		expectedTariff1.setServiceId(1);
		
		Tariff expectedTariff2 = new Tariff();
		expectedTariff2.setId(2);
		expectedTariff2.setName("tariff2 name");
		expectedTariff2.setDescription("tariff2 desc");
		expectedTariff2.setPaymentPeriod(30);
		expectedTariff2.setRate(BigDecimal.TEN);
		expectedTariff2.setServiceId(2);
		
		List<Tariff> expectedTariffList = new ArrayList<>();
		expectedTariffList.add(expectedTariff1);
		expectedTariffList.add(expectedTariff2);
		
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_ID_FIELD)).thenReturn(expectedTariff1.getId()).thenReturn(expectedTariff2.getId());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_NAME_FIELD)).thenReturn(expectedTariff1.getName()).thenReturn(expectedTariff2.getName());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_DESCRIPTION_FIELD)).thenReturn(expectedTariff1.getDescription()).thenReturn(expectedTariff2.getDescription());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_PAYMENT_PERIOD_FIELD)).thenReturn(expectedTariff1.getPaymentPeriod()).thenReturn(expectedTariff2.getPaymentPeriod());
		when(mockResultSet.getBigDecimal(MariaDBConstants.TARIFF_RATE_FIELD)).thenReturn(expectedTariff1.getRate()).thenReturn(expectedTariff2.getRate());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_SERVICE_ID_FIELD)).thenReturn(expectedTariff1.getServiceId()).thenReturn(expectedTariff2.getServiceId());
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_USERS_TARIFFS_BY_ID)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(1)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		List<Tariff> actualTariffList = tariffDAOMariaDB.getUsersTariffs(1);
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(expectedTariffList, actualTariffList);
	}
	
	@Test
	void testGetUserUnpaidTariffs() throws SQLException, DAOException {
		Tariff expectedTariff1 = new Tariff();
		expectedTariff1.setId(1);
		expectedTariff1.setName("tariff1 name");
		expectedTariff1.setDescription("tariff1 desc");
		expectedTariff1.setPaymentPeriod(7);
		expectedTariff1.setRate(BigDecimal.ONE);
		expectedTariff1.setServiceId(1);
		
		Tariff expectedTariff2 = new Tariff();
		expectedTariff2.setId(2);
		expectedTariff2.setName("tariff2 name");
		expectedTariff2.setDescription("tariff2 desc");
		expectedTariff2.setPaymentPeriod(30);
		expectedTariff2.setRate(BigDecimal.TEN);
		expectedTariff2.setServiceId(2);
		
		List<Tariff> expectedTariffList = new ArrayList<>();
		expectedTariffList.add(expectedTariff1);
		expectedTariffList.add(expectedTariff2);
		
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_ID_FIELD)).thenReturn(expectedTariff1.getId()).thenReturn(expectedTariff2.getId());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_NAME_FIELD)).thenReturn(expectedTariff1.getName()).thenReturn(expectedTariff2.getName());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_DESCRIPTION_FIELD)).thenReturn(expectedTariff1.getDescription()).thenReturn(expectedTariff2.getDescription());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_PAYMENT_PERIOD_FIELD)).thenReturn(expectedTariff1.getPaymentPeriod()).thenReturn(expectedTariff2.getPaymentPeriod());
		when(mockResultSet.getBigDecimal(MariaDBConstants.TARIFF_RATE_FIELD)).thenReturn(expectedTariff1.getRate()).thenReturn(expectedTariff2.getRate());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_SERVICE_ID_FIELD)).thenReturn(expectedTariff1.getServiceId()).thenReturn(expectedTariff2.getServiceId());
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_USERS_UNPAID_TARIFFS)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(1)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		List<Tariff> actualTariffList = tariffDAOMariaDB.getUsersUnpaidTariffs(1);
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(expectedTariffList, actualTariffList);
	}
	
	@Test
	void testGetUsersTariffsWithDayToPayment() throws SQLException, DAOException {
		Tariff expectedTariff1 = new Tariff();
		expectedTariff1.setId(1);
		expectedTariff1.setName("tariff1 name");
		expectedTariff1.setDescription("tariff1 desc");
		expectedTariff1.setPaymentPeriod(7);
		expectedTariff1.setRate(BigDecimal.ONE);
		expectedTariff1.setServiceId(1);
		
		Tariff expectedTariff2 = new Tariff();
		expectedTariff2.setId(2);
		expectedTariff2.setName("tariff2 name");
		expectedTariff2.setDescription("tariff2 desc");
		expectedTariff2.setPaymentPeriod(30);
		expectedTariff2.setRate(BigDecimal.TEN);
		expectedTariff2.setServiceId(2);
		
		Map<Tariff, Integer> expectedTariffMap = new HashMap<>();
		expectedTariffMap.put(expectedTariff1,2);
		expectedTariffMap.put(expectedTariff2,2);
		
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_ID_FIELD)).thenReturn(expectedTariff1.getId()).thenReturn(expectedTariff2.getId());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_NAME_FIELD)).thenReturn(expectedTariff1.getName()).thenReturn(expectedTariff2.getName());
		when(mockResultSet.getString(MariaDBConstants.TARIFF_DESCRIPTION_FIELD)).thenReturn(expectedTariff1.getDescription()).thenReturn(expectedTariff2.getDescription());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_PAYMENT_PERIOD_FIELD)).thenReturn(expectedTariff1.getPaymentPeriod()).thenReturn(expectedTariff2.getPaymentPeriod());
		when(mockResultSet.getBigDecimal(MariaDBConstants.TARIFF_RATE_FIELD)).thenReturn(expectedTariff1.getRate()).thenReturn(expectedTariff2.getRate());
		when(mockResultSet.getInt(MariaDBConstants.TARIFF_SERVICE_ID_FIELD)).thenReturn(expectedTariff1.getServiceId()).thenReturn(expectedTariff2.getServiceId());
		when(mockResultSet.getInt(MariaDBConstants.USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD)).thenReturn(2);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_USERS_TARIFFS_BY_ID_WITH_DAY_TO_PAY)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(1)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		Map<Tariff, Integer> actualTariffMap = tariffDAOMariaDB.getUsersTariffsWithDayToPayment(1);
		verify(mockQueryBuilder, times(1)).executeQuery();
		assertEquals(expectedTariffMap, actualTariffMap);
	}
	
	@Test
	void testUpdateDaysLeftForUnblockedUsers() throws SQLException, DAOException {
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.UPDATE_DAYS_UNTIL_PAYMENT_FOR_UNBLOCKED_USERS)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		tariffDAOMariaDB.updateDaysLeftForUnblockedUsers();
		verify(mockQueryBuilder, times(1)).executeUpdate();
	}
	
}
