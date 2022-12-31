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

public class ServiceDAOMariaDB implements ServiceDAO {

	@Override
	public Service get(int id) throws DAOException {
		Service service = null;
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_SERVICE_BY_ID).setIntField(id)
				.executeQuery()) {
			if (rs.next()) {
				service = getServiceFromResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get service with id " + id +".", e);
		}
		return service;
	}

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

	@Override
	public int insert(Service service) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.ADD_SERVICE).setStringField(service.getName())
					.setStringField(service.getDescription()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOInsertException("Cannot insert service " + service + ".", e);
		}
	}

	@Override
	public int update(Service service) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.UPDATE_SERVICE)
					.setStringField(service.getName()).setStringField(service.getDescription()).setIntField(service.getId())
					.executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot update service " + service + ".",e);
		}
	}

	@Override
	public int delete(Service service) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.DELETE_SERVICE).setIntField(service.getId())
					.executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete service " + service +".", e);
		}
	}

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
