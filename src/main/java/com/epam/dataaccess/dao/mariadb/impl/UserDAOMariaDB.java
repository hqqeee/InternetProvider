package com.epam.dataaccess.dao.mariadb.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.epam.dataaccess.dao.UserDAO;
import com.epam.dataaccess.dao.mariadb.datasource.QueryBuilder;
import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAODeleteException;
import com.epam.exception.dao.DAOException;
import com.epam.exception.dao.DAOInsertException;
import com.epam.exception.dao.DAOMappingException;
import com.epam.exception.dao.DAOReadException;
import com.epam.exception.dao.DAORecordAlreadyExistsException;
import com.epam.exception.dao.DAOUpdateException;

public class UserDAOMariaDB implements UserDAO {

	@Override
	public User get(int id) throws DAOException {
		User user = null;

		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_USER_BY_ID).setIntField(id)
				.executeQuery();) {

			if (rs.next()) {
				user = getUserFromResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get user with id " + id + ".", e);
		}
		return user;
	}

	@Override
	public List<User> getAll() throws DAOException {
		List<User> users = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_USERS).executeQuery();) {
			while (rs.next()) {
				users.add(getUserFromResultSet(rs));
			}
		} catch (SQLException e) {

			throw new DAOReadException("Cannot get all users.", e);
		}
		return users;
	}

	@Override
	public int insert(User user) throws DAOException {
		try {
			if(checkUserExistsByLogin(user.getLogin())) {
				throw new DAORecordAlreadyExistsException("User with login " + user.getLogin() + " already exists.");
			}
			if(checkUserExistsByEmail(user.getLogin())) {
				throw new DAORecordAlreadyExistsException("User with email " + user.getEmail() + " already exists.");
			}
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.ADD_USER).setStringField(user.getPassword())
					.setStringField(user.getSalt()).setStringField(user.getLogin()).setIntField(user.getRoleId())
					.setBooleanField(user.isBlocked()).setStringField(user.getEmail())
					.setStringField(user.getFirstName()).setStringField(user.getLastName())
					.setStringField(user.getCity()).setStringField(user.getAddress())
					.setBigDecimalField(user.getBalance()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOInsertException("Cannot add user " + user + ".", e);
		}
	}

	@Override
	public int update(User user) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.UPDATE_USER)
					.setStringField(user.getPassword()).setStringField(user.getSalt()).setStringField(user.getLogin())
					.setIntField(user.getRoleId()).setBooleanField(user.isBlocked()).setStringField(user.getEmail())
					.setStringField(user.getFirstName()).setStringField(user.getLastName())
					.setStringField(user.getCity()).setStringField(user.getAddress())
					.setBigDecimalField(user.getBalance()).setIntField(user.getId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot update user " + user + ".", e);
		}
	}

	@Override
	public int delete(User user) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.DELETE_USER).setIntField(user.getId())
					.executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete user " + user, e);
		}
	}

	@Override
	public String getSalt(String login) throws DAOException {
		String salt = "";
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_SALT_BY_LOGIN)
				.setStringField(login).executeQuery()) {
			if (rs.next()) {
				salt = rs.getString(MariaDBConstants.USER_SALT_FIELD);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get salt for user with login " + login + ".", e);
		}
		return salt;
	}

	@Override
	public User getUser(String login, String password) throws DAOException {
		User user = null;

		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_USER_BY_LOGIN_AND_PASSWORD)
				.setStringField(login).setStringField(password).executeQuery();) {
			if (rs.next()) {
				user = getUserFromResultSet(rs);
			}
		} catch (SQLIntegrityConstraintViolationException e) {

		} catch (SQLException e) {
			throw new DAOReadException("Cannot get user with login " + login + " password " + password + ".", e);
		}
		return user;
	}

	@Override
	public int changeBlocked(boolean blocked, int id) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.CHANGE_BLOCK_STATUS)
					.setBooleanField(blocked).setIntField(id).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot change blocked status for user with id " + id + ".", e);
		}
	}

	@Override
	public void addTariffToUser(int userId, int tariffId) throws DAOException {
		try {
			if(new QueryBuilder().addPreparedStatement(MariaDBConstants.ADD_TARIFF_TO_USER)
					.setIntField(userId).setIntField(tariffId).setIntField(0)
					.setIntField(userId).setIntField(tariffId).executeUpdate() ==0) {
				throw new DAORecordAlreadyExistsException(
						"Pair tariffId-userId(" + tariffId + "-" + userId + ") already exists.");
			} else {
				return;
		}
		} catch (SQLException e) {
			throw new DAOInsertException(
					"Cannot insert tariff with id " + tariffId + " for user with id " + userId + ".", e);
		}
	}

	@Override
	public List<User> getAllSubscriber() throws DAOException {
		List<User> subscribers = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_USERS_WITH_ROLE_ID)
				.setIntField(2).executeQuery()) {
			while (rs.next()) {
				subscribers.add(getUserFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all subscribers.", e);
		}
		return subscribers;
	}

	@Override
	public List<User> getAllUnblockedSubscriber() throws DAOException {
		List<User> subscribers = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_UNBLOCKED_SUBSCRIBERS)
				.setIntField(2).executeQuery()) {
			while (rs.next()) {
				subscribers.add(getUserFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all unblocked subscribers.", e);
		}
		return subscribers;
	}

	@Override
	public List<User> getSubscriberForCharging() throws DAOException {
		List<User> subscribers = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder()
				.addPreparedStatement(MariaDBConstants.GET_UNBLOCKED_AND_PAYMENT_NEEDED_SUBSCRIBERS).executeQuery()) {
			while (rs.next()) {
				subscribers.add(getUserFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all unblocked subscribers.", e);
		}
		return subscribers;
	}

	@Override
	public List<User> getSubscriberForView(String searchField, int offset, int entriesPerPage) throws DAOException {
		List<User> subscribers = new ArrayList<>();
		try (ResultSet rs = new QueryBuilder()
				.addPreparedStatement(
						MariaDBConstants.GET_USERS_FOR_VIEW + "LOWER('%" + searchField + "%')" + MariaDBConstants.LIMIT)
				.setIntField(2).setIntField(offset).setIntField(entriesPerPage).executeQuery()) {
			while (rs.next()) {
				subscribers.add(getUserFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get subscribers login contains " + searchField + ".", e);
		}
		return subscribers;
	}

	@Override
	public int getSubscriberNumber(String searchField) throws DAOException {
		int count = 0;
		try (ResultSet rs = new QueryBuilder()
				.addPreparedStatement(MariaDBConstants.GET_SUBSCRIBER_COUNT + "LOWER('%" + searchField + "%')")
				.executeQuery()) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get count of services.", e);
		}
		return count;
	}

	@Override
	public int changePassword(int userId, String newPassword, String salt) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.CHANGE_USER_PASSWORD)
					.setStringField(newPassword).setStringField(salt).setIntField(userId).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot change password for user with id " + userId + ".", e);
		}

	}
	
	@Override
	public int changePassword(String email, String newPassword, String salt) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.CHANGE_USER_PASSWORD_BY_EMAIL)
					.setStringField(newPassword).setStringField(salt).setStringField(email).executeUpdate();
		} catch(SQLException e) {
			throw new DAOUpdateException("Cannot change password for user with email " + email + ".", e);
		}
	}

	@Override
	public int removeTariffFromUser(int userId, int tariffId) throws DAOException {
		try {
			return new QueryBuilder().addPreparedStatement(MariaDBConstants.REMOVE_USER_TARIFF).setIntField(userId)
					.setIntField(tariffId).executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete record in user_has_tariff with userId: " + userId
					+ " and tariffId " + tariffId + ".", e);
		}
	}

	@Override
	public BigDecimal getUserBalance(int userId) throws DAOException {
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_USER_BALANCE)
				.setIntField(userId).executeQuery()) {
			BigDecimal result = null;
			if (rs.next()) {
				result = rs.getBigDecimal(MariaDBConstants.USER_BALANCE_FIELD);
			}
			return result;
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get balance for user with id " + userId + ".", e);
		}
	}

	@Override
	public boolean getUserStatus(int userId) throws DAOException {
		try (ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_USER_BLOCKED_STATUS)
				.setIntField(userId).executeQuery()) {
			boolean result = false;
			if (rs.next()) {
				result = rs.getBoolean(MariaDBConstants.USER_BLOCKED_FIELD);
			}
			return result;
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get status of the user with id " + userId + ".", e);
		}
	}

	private User getUserFromResultSet(ResultSet rs) throws DAOException {
		try {
			return new User(rs.getInt(MariaDBConstants.USER_ID_FIELD),
					rs.getString(MariaDBConstants.USER_PASSWORD_FIELD), rs.getString(MariaDBConstants.USER_SALT_FIELD),
					rs.getString(MariaDBConstants.USER_LOGIN_FIELD), rs.getInt(MariaDBConstants.USER_ROLE_ID_FIELD),
					rs.getBoolean(MariaDBConstants.USER_BLOCKED_FIELD), rs.getString(MariaDBConstants.USER_EMAIL_FIELD),
					rs.getString(MariaDBConstants.USER_FIRST_NAME_FIELD),
					rs.getString(MariaDBConstants.USER_LAST_NAME_FIELD), rs.getString(MariaDBConstants.USER_CITY_FIELD),
					rs.getString(MariaDBConstants.USER_ADDRESS_FIELD),
					rs.getBigDecimal(MariaDBConstants.USER_BALANCE_FIELD));
		} catch (SQLException e) {
			throw new DAOMappingException("Cannot map user from ResultSet.", e);
		}
	}

	@Override
	public String getLoginByEmail(String email) throws DAOException {
		try(ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.GET_LOGIN_BY_EMAIL).setStringField(email).executeQuery()){
			if(rs.next()) {
				return rs.getString(MariaDBConstants.USER_LOGIN_FIELD);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get user with email " + email + ".",e);
	
		}
		return null;
	}


	private boolean checkUserExistsByLogin(String login) throws SQLException {
		try(ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.EXISTS_USER_BY_LOGIN).setStringField(login).executeQuery()){
			return rs.next();
		}
	}
	
	private boolean checkUserExistsByEmail(String email) throws SQLException {
		try(ResultSet rs = new QueryBuilder().addPreparedStatement(MariaDBConstants.EXISTS_USER_BY_EMAIL).setStringField(email).executeQuery()){
			return rs.next();
		}
	}
}
