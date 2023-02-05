package com.epam.dataaccess.dao.mariadb.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.dataaccess.dao.ServiceDAO;
import com.epam.dataaccess.dao.mariadb.datasource.QueryBuilder;
import com.epam.dataaccess.entity.Service;
import com.epam.exception.dao.DAODeleteException;
import com.epam.exception.dao.DAOException;
import com.epam.exception.dao.DAOInsertException;
import com.epam.exception.dao.DAOMappingException;
import com.epam.exception.dao.DAOReadException;
import com.epam.exception.dao.DAOUpdateException;

/**
 * Service DAO implementation for MariaDB. Has methods needed by services to
 * access persistence layer.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public class ServiceDAOMariaDB implements ServiceDAO {

	/**
	 * Return Service by its ID.
	 * 
	 * @param id id of the Service.
	 * @return Service entity with this id.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public Service get(int id) throws DAOException {
		Service service = null;
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_SERVICE_BY_ID).setIntField(id)
				.executeQuery()) {
			if (rs.next()) {
				service = getServiceFromResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get service with id " + id + ".", e);
		}
		return service;
	}

	/**
	 * Return all Service entities.
	 * 
	 * @return All Service entities.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<Service> getAll() throws DAOException {
		List<Service> services = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_SERVICES).executeQuery()) {
			while (rs.next()) {
				services.add(getServiceFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all services.", e);
		}
		return services;
	}

	/**
	 * Insert new Service to the persistence layer.
	 * 
	 * @param tariff Service to add.
	 * @return 1 if inserted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int insert(Service service) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.ADD_SERVICE)
					.setStringField(service.getName()).setStringField(service.getDescription()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOInsertException("Cannot insert service " + service + ".", e);
		}
	}

	/**
	 * Update Service in the persistence layer.
	 * 
	 * @param user Service to update.
	 * @return 1 if updated, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int update(Service service) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.UPDATE_SERVICE)
					.setStringField(service.getName()).setStringField(service.getDescription())
					.setIntField(service.getId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot update service " + service + ".", e);
		}
	}

	/**
	 * Delete Service from the persistence layer.
	 * 
	 * @param user Service to delete.
	 * @return 1 if deleted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int delete(Service service) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.DELETE_SERVICE).setIntField(service.getId())
					.executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete service " + service + ".", e);
		}
	}

	/**
	 * This method gets Service from the result set.
	 * 
	 * @param rs Result set to get Service from.
	 * @return Service from Result Set
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	private Service getServiceFromResultSet(ResultSet rs) throws DAOException {
		try {
			return new Service(rs.getInt(MariaDBConstants.SERVICE_ID_FIELD),
					rs.getString(MariaDBConstants.SERVICE_NAME_FIELD),
					rs.getString(MariaDBConstants.SERVICE_DESCRIPTION_FIELD));
		} catch (SQLException e) {
			throw new DAOMappingException("Cannot map service from ResultSet.", e);
		}
	}

}
