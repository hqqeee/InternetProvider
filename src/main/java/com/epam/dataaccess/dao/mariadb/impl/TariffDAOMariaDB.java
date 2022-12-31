package com.epam.dataaccess.dao.mariadb.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.dao.mariadb.datasource.QueryBuilder;
import com.epam.dataaccess.entity.Tariff;
import com.epam.exception.dao.DAODeleteException;
import com.epam.exception.dao.DAOException;
import com.epam.exception.dao.DAOInsertException;
import com.epam.exception.dao.DAOMappingException;
import com.epam.exception.dao.DAOReadException;
import com.epam.exception.dao.DAOUpdateException;
import com.epam.util.SortingOrder;

public class TariffDAOMariaDB implements TariffDAO {

	@Override
	public Tariff get(int id) throws DAOException {
		Tariff tariff = null;
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_TARIFF_BY_ID).setIntField(id)
				.executeQuery()) {
			if (rs.next()) {
				tariff = getTariffFromResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get tariff " + tariff + ".", e);
		}
		return tariff;
	}

	@Override
	public List<Tariff> getAll() throws DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_TARIFFS).executeQuery()) {
			while (rs.next()) {
				tariffs.add(getTariffFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all tariffs.", e);
		}
		return tariffs;
	}

	@Override
	public int insert(Tariff tariff) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.ADD_TARIFF).setStringField(tariff.getName())
					.setStringField(tariff.getDescription()).setBigDecimalField(tariff.getPrice())
					.setIntField(tariff.getServiceId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOInsertException("Cannot insert tariff " + tariff +".", e);
		}
	}

	@Override
	public int update(Tariff tariff) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.UPDATE_TARIFF).setStringField(tariff.getName())
					.setStringField(tariff.getDescription()).setBigDecimalField(tariff.getPrice())
					.setIntField(tariff.getServiceId()).setIntField(tariff.getId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot update tariff " + tariff + ".",e);
		}
	}

	@Override
	public int delete(Tariff tariff) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.DELETE_TARIFF).setIntField(tariff.getId())
					.executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete tariff "+ tariff + ".",e);
		}
	}

	@Override
	public List<Tariff> getAll(int serviceId, int offset, int recordsPerPage, SortingOrder order, String fieldName)
			throws DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder()
				.addPreparedStatement(MariaDBConstants.GET_ALL_TARIFFS + MariaDBConstants.FILTER_TARIFF_BY_SERVICE_ID
						+ MariaDBConstants.ORDER_BY + fieldName + " " + order.getOrder() + MariaDBConstants.LIMIT)
				.setIntField(serviceId).setIntField(offset).setIntField(recordsPerPage).executeQuery()) {
			while (rs.next()) {
				tariffs.add(getTariffFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all tariffs with serviceId " + serviceId + " offset " + offset
					+ " recordsPerPage " + recordsPerPage + " sorting order " + order.getOrder() + " field " +
					fieldName + ".", e
					);
			
		}
		return tariffs;
	}



	@Override
	public List<Tariff> getAll(int offset, int recordsPerPage, SortingOrder order, String fieldName)
			throws DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder()
				.addPreparedStatement(MariaDBConstants.GET_ALL_TARIFFS +
						 MariaDBConstants.ORDER_BY + fieldName + " " + order.getOrder() + MariaDBConstants.LIMIT)
				.setIntField(offset).setIntField(recordsPerPage).executeQuery()) {
			while (rs.next()) {
				tariffs.add(getTariffFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all tariffs with offset " + offset
					+ " recordsPerPage " + recordsPerPage + " sorting order " + order.getOrder() + " field name " +
					fieldName + ".", e
					);
			
		}
		return tariffs;
	}
	
	@Override
	public int getTariffCount(int serviceId) throws DAOException {
		int count = 0;
		try(ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_COUNT_OF_TARIFF_BY_SERVICE_ID).setIntField(serviceId).executeQuery()){
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get count of services with serviceId " + serviceId + ".", e);
		}
		return count;
	}

	@Override
	public int getTariffCount() throws DAOException {
		int count = 0;
		try(ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_COUNT_OF_TARIFFS).executeQuery()){
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get count of services.",e);
		}
		return count;
	}

	@Override
	public List<Tariff> getAll(int serviceId) throws DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder()
				.addPreparedStatement(MariaDBConstants.GET_ALL_TARIFFS + MariaDBConstants.FILTER_TARIFF_BY_SERVICE_ID)
				.setIntField(serviceId).executeQuery()) {
			while (rs.next()) {
				tariffs.add(getTariffFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get services with serviceId " + serviceId + ".", e);
		}
		return tariffs;
	}

	
	@Override
	public List<Tariff> getUsersTariffs(int userId) throws DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_USERS_TARIFFS_BY_ID)
				.setIntField(userId).executeQuery()) {
			while (rs.next()) {
				tariffs.add(getTariffFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get tariffs of user with id " + userId + ".", e);
		}

		return tariffs;
	}

	private Tariff getTariffFromResultSet(ResultSet rs) throws DAOException {
		try {
			return new Tariff(rs.getInt(MariaDBConstants.TARIFF_ID_FIELD), rs.getString(MariaDBConstants.TARIFF_NAME_FIELD),
					rs.getString(MariaDBConstants.TARIFF_DESCRIPTION_FIELD),
					rs.getBigDecimal(MariaDBConstants.TARIFF_PRICE_FIELD),
					rs.getInt(MariaDBConstants.TARIFF_SERVICE_ID_FIELD)

			);
		} catch (SQLException e) {
			throw new DAOMappingException("Cannot map tariff from ResultSet.", e);
		}
	}


}
