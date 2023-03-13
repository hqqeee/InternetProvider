package com.epam.dataaccess.dao.mariadb.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.dataaccess.dao.RoleDAO;
import com.epam.dataaccess.dao.mariadb.datasource.QueryBuilder;
import com.epam.dataaccess.entity.Role;
import com.epam.exception.dao.DAODeleteException;
import com.epam.exception.dao.DAOException;
import com.epam.exception.dao.DAOInsertException;
import com.epam.exception.dao.DAOMappingException;
import com.epam.exception.dao.DAOReadException;
import com.epam.exception.dao.DAOUpdateException;

/**
 * Role DAO implementation for MariaDB. Has methods needed by services to access
 * persistence layer.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public class RoleDAOMariaDB implements RoleDAO {

	/**
	 * Return Role by its ID.
	 * 
	 * @param id id of the Role.
	 * @return Role entity with this id.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public Role get(int id) throws DAOException {
		Role role = null;
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_ROLE_BY_ID).setIntField(id)
				.executeQuery()) {
			if (rs.next()) {
				role = getRoleFromResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get role with id " + id + ".", e);
		}
		return role;
	}

	/**
	 * Return all Role entities.
	 * 
	 * @return All Role entities.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<Role> getAll() throws DAOException {
		List<Role> roles = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_ROLES).executeQuery()) {
			while (rs.next()) {
				roles.add(getRoleFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all roles.", e);
		}

		return roles;
	}

	/**
	 * Insert new Role to the persistence layer.
	 * 
	 * @param role Role to add.
	 * @return 1 if inserted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int insert(Role role) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.ADD_ROLE).setStringField(role.getName())
					.setStringField(role.getDescription()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOInsertException("Cannot add role " + role + ".", e);
		}
	}

	/**
	 * Update Role in the persistence layer.
	 * 
	 * @param role Role to update.
	 * @return 1 if updated, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int update(Role role) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.UPDATE_ROLE).setStringField(role.getName())
					.setStringField(role.getDescription()).setIntField(role.getId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot update role " + role + ".", e);
		}
	}

	protected QueryBuilder getQueryBuilder() throws SQLException {
		return new QueryBuilder();
	}

	/**
	 * Delete Role from the persistence layer.
	 * 
	 * @param role Role to delete.
	 * @return 1 if deleted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int delete(Role role) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.DELETE_ROLE).setIntField(role.getId())
					.executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete role " + role + ".", e);
		}

	}

	/**
	 * This method gets Role from the result set.
	 * 
	 * @param rs Result set to get Role from.
	 * @return Role from Result Set
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	private Role getRoleFromResultSet(ResultSet rs) throws DAOException {
		try {
			return new Role(rs.getInt(MariaDBConstants.ROLE_ID_FIELD), rs.getString(MariaDBConstants.ROLE_NAME_FIELD),
					rs.getString(MariaDBConstants.ROLE_DESCRIPTION_FIELD));
		} catch (SQLException e) {
			throw new DAOMappingException("Cannot map role from ResultSet.", e);
		}
	}

}
