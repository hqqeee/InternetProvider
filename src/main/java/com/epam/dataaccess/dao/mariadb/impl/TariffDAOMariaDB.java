package com.epam.dataaccess.dao.mariadb.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * Tariff DAO implementation for MariaDB. Has methods needed by services to
 * access persistence layer.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class TariffDAOMariaDB implements TariffDAO {

	/**
	 * Return Tariff by its ID.
	 * 
	 * @param id id of the Tariff.
	 * @return Tariff entity with this id.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public Tariff get(int id) throws DAOException {
		Tariff tariff = null;
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_TARIFF_BY_ID).setIntField(id)
				.executeQuery()) {
			if (rs.next()) {
				tariff = getTariffFromResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get tariff " + tariff + ".", e);
		}
		return tariff;
	}

	/**
	 * Return all tariff entities.
	 * 
	 * @return All Tariff entities.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<Tariff> getAll() throws DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_TARIFFS).executeQuery()) {
			while (rs.next()) {
				tariffs.add(getTariffFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all tariffs.", e);
		}
		return tariffs;
	}

	/**
	 * Insert new Tariff to the persistence layer.
	 * 
	 * @param tariff Tariff to add.
	 * @return 1 if inserted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int insert(Tariff tariff) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.ADD_TARIFF).setStringField(tariff.getName())
					.setStringField(tariff.getDescription()).setIntField(tariff.getPaymentPeriod())
					.setBigDecimalField(tariff.getRate()).setIntField(tariff.getServiceId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOInsertException("Cannot insert tariff " + tariff + ".", e);
		}
	}

	/**
	 * Update Tariff in the persistence layer.
	 * 
	 * @param tariff Tariff to update.
	 * @return 1 if updated, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int update(Tariff tariff) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.UPDATE_TARIFF)
					.setStringField(tariff.getName()).setStringField(tariff.getDescription())
					.setIntField(tariff.getPaymentPeriod()).setBigDecimalField(tariff.getRate())
					.setIntField(tariff.getServiceId()).setIntField(tariff.getId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot update tariff " + tariff + ".", e);
		}
	}

	/**
	 * Delete Tariff from the persistence layer.
	 * 
	 * @param tariff Tariff to delete.
	 * @return 1 if deleted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int delete(Tariff tariff) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.DELETE_TARIFF).setIntField(tariff.getId())
					.executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete tariff " + tariff + ".", e);
		}
	}

	/**
	 * This method return all tariff filtered by serviceId, limited by offset and
	 * recordsPerPage and sorted. by fieldName in sorting order.
	 * 
	 * @param serviceId      service id to filter.
	 * @param offset         number of records to skip.
	 * @param recordsPerPage number of records of page.
	 * @param order          sorting order.
	 * @param fieldName      name of field to sort by which.
	 * @return List of tariff filtered by serviceId, limited by offset and
	 *         recordsPerPage and sorted.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<Tariff> getAll(int serviceId, int offset, int recordsPerPage, SortingOrder order, String fieldName)
			throws DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder()
				.addPreparedStatement(MariaDBConstants.GET_ALL_TARIFFS + MariaDBConstants.FILTER_TARIFF_BY_SERVICE_ID
						+ MariaDBConstants.ORDER_BY + fieldName + " " + order.getOrder() + MariaDBConstants.LIMIT)
				.setIntField(serviceId).setIntField(offset).setIntField(recordsPerPage).executeQuery()) {
			while (rs.next()) {
				tariffs.add(getTariffFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException(
					"Cannot get all tariffs with serviceId " + serviceId + " offset " + offset + " recordsPerPage "
							+ recordsPerPage + " sorting order " + order.getOrder() + " field " + fieldName + ".",
					e);

		}
		return tariffs;
	}

	protected QueryBuilder getQueryBuilder() throws SQLException {
		return new QueryBuilder();
	}

	/**
	 * This method return all tariff limited by offset and recordsPerPage and
	 * sorted. by fieldName in sorting order.
	 * 
	 * @param offset         number of records to skip.
	 * @param recordsPerPage number of records of page.
	 * @param order          sorting order.
	 * @param fieldName      name of field to sort by which.
	 * @return List of tariff limited by offset and recordsPerPage and sorted.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<Tariff> getAll(int offset, int recordsPerPage, SortingOrder order, String fieldName)
			throws DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder()
				.addPreparedStatement(MariaDBConstants.GET_ALL_TARIFFS + MariaDBConstants.ORDER_BY + fieldName + " "
						+ order.getOrder() + MariaDBConstants.LIMIT)
				.setIntField(offset).setIntField(recordsPerPage).executeQuery()) {
			while (rs.next()) {
				tariffs.add(getTariffFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all tariffs with offset " + offset + " recordsPerPage "
					+ recordsPerPage + " sorting order " + order.getOrder() + " field name " + fieldName + ".", e);

		}
		return tariffs;
	}

	/**
	 * This method return number of tariff with specific service.
	 * 
	 * @param serviceId service id to filter.
	 * @return number of tariff with specific service.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int getTariffCount(int serviceId) throws DAOException {
		int count = 0;
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_COUNT_OF_TARIFF_BY_SERVICE_ID)
				.setIntField(serviceId).executeQuery()) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get count of services with serviceId " + serviceId + ".", e);
		}
		return count;
	}

	/**
	 * This method return number of all tariffs.
	 * 
	 * @return number of all tariffs.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int getTariffCount() throws DAOException {
		int count = 0;
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_COUNT_OF_TARIFFS)
				.executeQuery()) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get count of services.", e);
		}
		return count;
	}

	/**
	 * This method return all tariffs for specific service.
	 * 
	 * @param serviceId if of service to filter.
	 * @return All tariffs with specific service.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<Tariff> getAll(int serviceId) throws DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder()
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

	/**
	 * Return list of tariff for specific users.
	 * 
	 * @param userId User ID of which tariff to get.
	 * @return All tariffs of this user.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<Tariff> getUsersTariffs(int userId) throws DAOException {
		List<Tariff> tariffs = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_USERS_TARIFFS_BY_ID)
				.setIntField(userId).executeQuery()) {
			while (rs.next()) {
				tariffs.add(getTariffFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get tariffs of user with id " + userId + ".", e);
		}

		return tariffs;
	}

	/**
	 * Return list of tariffs for specific users that has 0 days until next payment.
	 * 
	 * @param userId User ID of which tariff to get.
	 * @return All tariffs of this user that has 0 days until next payment.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<Tariff> getUsersUnpaidTariffs(int userId) throws DAOException {
		List<Tariff> unpaidTariffs = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_USERS_UNPAID_TARIFFS)
				.setIntField(userId).executeQuery()) {
			while (rs.next()) {
				unpaidTariffs.add(getTariffFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get unpaid tariffs of user with id " + userId + ".", e);
		}

		return unpaidTariffs;
	}

	/**
	 * Returns a map of Tariff and day until next payment for specific user.
	 * 
	 * @param userId id of the user to get map for.
	 * @return a map of Tariff and day until next payment for specific user.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public Map<Tariff, Integer> getUsersTariffsWithDayToPayment(int userId) throws DAOException {
		Map<Tariff, Integer> tariffsWithDayToPayment = new HashMap<>();
		try (ResultSet rs = getQueryBuilder()
				.addPreparedStatement(MariaDBConstants.GET_USERS_TARIFFS_BY_ID_WITH_DAY_TO_PAY).setIntField(userId)
				.executeQuery()) {
			while (rs.next()) {
				tariffsWithDayToPayment.put(getTariffFromResultSet(rs),
						rs.getInt(MariaDBConstants.USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get tariffs of user with id " + userId + ".", e);
		}
		return tariffsWithDayToPayment;
	}

	/**
	 * Decrement day until next payment for all user_has_tariff records that are
	 * greater than zero.
	 * 
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public void updateDaysLeftForUnblockedUsers() throws DAOException {
		try {
			getQueryBuilder().addPreparedStatement(MariaDBConstants.UPDATE_DAYS_UNTIL_PAYMENT_FOR_UNBLOCKED_USERS)
					.executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot update days until payment.", e);
		}
	}

	/**
	 * This method gets Tariff from the result set.
	 * 
	 * @param rs Result set to get Tariff from.
	 * @return Tariff from Result Set
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	private Tariff getTariffFromResultSet(ResultSet rs) throws DAOException {
		try {
			return new Tariff(rs.getInt(MariaDBConstants.TARIFF_ID_FIELD),
					rs.getString(MariaDBConstants.TARIFF_NAME_FIELD),
					rs.getString(MariaDBConstants.TARIFF_DESCRIPTION_FIELD),
					rs.getInt(MariaDBConstants.TARIFF_PAYMENT_PERIOD_FIELD),
					rs.getBigDecimal(MariaDBConstants.TARIFF_RATE_FIELD),
					rs.getInt(MariaDBConstants.TARIFF_SERVICE_ID_FIELD)

			);
		} catch (SQLException e) {
			throw new DAOMappingException("Cannot map tariff from ResultSet.", e);
		}
	}

}
