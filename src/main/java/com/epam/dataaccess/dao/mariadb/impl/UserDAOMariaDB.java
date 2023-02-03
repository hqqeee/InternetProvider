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

/**
 * User DAO implementation for MariaDB. Has methods needed by services to access
 * persistence layer.
 * 
 * @author ruslan
 *
 */

public class UserDAOMariaDB implements UserDAO {

	/**
	 * Return User by its ID.
	 * 
	 * @param id id of the User.
	 * @return User entity with this id.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public User get(int id) throws DAOException {
		User user = null;

		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_USER_BY_ID).setIntField(id)
				.executeQuery();) {

			if (rs.next()) {
				user = getUserFromResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get user with id " + id + ".", e);
		}
		return user;
	}

	/**
	 * Return all user's entity.
	 * 
	 * @return All User's entity.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<User> getAll() throws DAOException {
		List<User> users = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_USERS).executeQuery();) {
			while (rs.next()) {
				users.add(getUserFromResultSet(rs));
			}
		} catch (SQLException e) {

			throw new DAOReadException("Cannot get all users.", e);
		}
		return users;
	}

	/**
	 * Insert new User to the persistence layer.
	 * 
	 * @param user User to add.
	 * @return 1 if inserted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int insert(User user) throws DAOException {
		try {
			if (checkUserExistsByLogin(user.getLogin())) {
				throw new DAORecordAlreadyExistsException("User with login " + user.getLogin() + " already exists.");
			}
			if (checkUserExistsByEmail(user.getLogin())) {
				throw new DAORecordAlreadyExistsException("User with email " + user.getEmail() + " already exists.");
			}
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.ADD_USER).setStringField(user.getPassword())
					.setStringField(user.getSalt()).setStringField(user.getLogin()).setIntField(user.getRoleId())
					.setBooleanField(user.isBlocked()).setStringField(user.getEmail())
					.setStringField(user.getFirstName()).setStringField(user.getLastName())
					.setStringField(user.getCity()).setStringField(user.getAddress())
					.setBigDecimalField(user.getBalance()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOInsertException("Cannot add user " + user + ".", e);
		}
	}

	/**
	 * Update User in the persistence layer.
	 * 
	 * @param user User to update.
	 * @return 1 if updated, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int update(User user) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.UPDATE_USER)
					.setStringField(user.getPassword()).setStringField(user.getSalt()).setStringField(user.getLogin())
					.setIntField(user.getRoleId()).setBooleanField(user.isBlocked()).setStringField(user.getEmail())
					.setStringField(user.getFirstName()).setStringField(user.getLastName())
					.setStringField(user.getCity()).setStringField(user.getAddress())
					.setBigDecimalField(user.getBalance()).setIntField(user.getId()).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot update user " + user + ".", e);
		}
	}

	/**
	 * Delete User from the persistence layer.
	 * 
	 * @param user User to delete.
	 * @return 1 if deleted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int delete(User user) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.DELETE_USER).setIntField(user.getId())
					.executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete user " + user, e);
		}
	}

	/**
	 * This method returns salt of the user by login.
	 * 
	 * @param login login of the user.
	 * @return salt(neede for hashing)
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public String getSalt(String login) throws DAOException {
		String salt = "";
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_SALT_BY_LOGIN)
				.setStringField(login).executeQuery()) {
			if (rs.next()) {
				salt = rs.getString(MariaDBConstants.USER_SALT_FIELD);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get salt for user with login " + login + ".", e);
		}
		return salt;
	}

	/**
	 * Return user by login and Password if exists.
	 * 
	 * @param login    login of the user.
	 * @param password password(hashed) of the user.
	 * @return User entity.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public User getUser(String login, String password) throws DAOException {
		User user = null;

		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_USER_BY_LOGIN_AND_PASSWORD)
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

	/**
	 * This method sets blocked status for the user.
	 * 
	 * @param blocked new blocked status(true - blocked, false - unbloced)
	 * @param id      id of the user.
	 * @return 0 if status not changed, 1 if status changed.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int changeBlocked(boolean blocked, int id) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.CHANGE_BLOCK_STATUS)
					.setBooleanField(blocked).setIntField(id).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot change blocked status for user with id " + id + ".", e);
		}
	}

	/**
	 * This method add record to user has tariff table.
	 * 
	 * @param userId   id of the user.
	 * @param tariffId if of the traiff.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public void addTariffToUser(int userId, int tariffId) throws DAOException {
		try {
			if (getQueryBuilder().addPreparedStatement(MariaDBConstants.ADD_TARIFF_TO_USER).setIntField(userId)
					.setIntField(tariffId).setIntField(0).setIntField(userId).setIntField(tariffId)
					.executeUpdate() == 0) {
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

	/**
	 * This method return list of the all users with role Subscriber and status
	 * unblocked.
	 * 
	 * @return all users with role Subscriber and status unblocked.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<User> getAllSubscriber() throws DAOException {
		List<User> subscribers = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_USERS_WITH_ROLE_ID)
				.setIntField(2).executeQuery()) {
			while (rs.next()) {
				subscribers.add(getUserFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all subscribers.", e);
		}
		return subscribers;
	}

	/**
	 * This method return list of the all users with role Subscriber, status
	 * unblocked and with tariffs with days until next payment eq 0.
	 * 
	 * @return user that needs to be charged.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<User> getAllUnblockedSubscriber() throws DAOException {
		List<User> subscribers = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_ALL_UNBLOCKED_SUBSCRIBERS)
				.setIntField(2).executeQuery()) {
			while (rs.next()) {
				subscribers.add(getUserFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all unblocked subscribers.", e);
		}
		return subscribers;
	}

	/**
	 * This method return list of the all users with role Subscriber, status
	 * unblocked and with tariffs with days until next payment eq 0.
	 * 
	 * @return user that needs to be charged.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<User> getSubscriberForCharging() throws DAOException {
		List<User> subscribers = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder()
				.addPreparedStatement(MariaDBConstants.GET_UNBLOCKED_AND_PAYMENT_NEEDED_SUBSCRIBERS).executeQuery()) {
			while (rs.next()) {
				subscribers.add(getUserFromResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get all unblocked subscribers.", e);
		}
		return subscribers;
	}

	/**
	 * This method return filtered list of subscriber for view with pagination.
	 * 
	 * @param searchField    part of login to filter.
	 * @param offset         offset of the records.
	 * @param entriesPerPage number of records to get.
	 * @return filtered list of users.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public List<User> getSubscriberForView(String searchField, int offset, int entriesPerPage) throws DAOException {
		List<User> subscribers = new ArrayList<>();
		try (ResultSet rs = getQueryBuilder()
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

	/**
	 * This method return number of the user filtered by part of login.
	 * 
	 * @param searchField part of login to filter.
	 * @return number of subscriber which login contains "login part"
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int getSubscriberNumber(String searchField) throws DAOException {
		int count = 0;
		try (ResultSet rs = getQueryBuilder()
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

	/**
	 * This method update User record in the persistence layer with new password and
	 * salt.
	 * 
	 * @param userId      id of the user.
	 * @param newPassword new password(hashed)
	 * @param salt        salt(needed to hash).
	 * @return 1 if password changed.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int changePassword(int userId, String newPassword, String salt) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.CHANGE_USER_PASSWORD)
					.setStringField(newPassword).setStringField(salt).setIntField(userId).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot change password for user with id " + userId + ".", e);
		}

	}

	/**
	 * Return user blocked status.
	 * 
	 * @param userId id of the user which status will be returned.
	 * @return true if user blocked, false if unblocked.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int changePassword(String email, String newPassword, String salt) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.CHANGE_USER_PASSWORD_BY_EMAIL)
					.setStringField(newPassword).setStringField(salt).setStringField(email).executeUpdate();
		} catch (SQLException e) {
			throw new DAOUpdateException("Cannot change password for user with email " + email + ".", e);
		}
	}

	/**
	 * This method removes record from user has tariff table.
	 * 
	 * @param userId   if of the user.
	 * @param tariffId if of the tariff.
	 * @return 0 if record wasn't deleted, 1 if deleted
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public int removeTariffFromUser(int userId, int tariffId) throws DAOException {
		try {
			return getQueryBuilder().addPreparedStatement(MariaDBConstants.REMOVE_USER_TARIFF).setIntField(userId)
					.setIntField(tariffId).executeUpdate();
		} catch (SQLException e) {
			throw new DAODeleteException("Cannot delete record in user_has_tariff with userId: " + userId
					+ " and tariffId " + tariffId + ".", e);
		}
	}

	/**
	 * This method return balance of the user.
	 * 
	 * @param userId id of the user.
	 * @return balance of the user.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public BigDecimal getUserBalance(int userId) throws DAOException {
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_USER_BALANCE)
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

	/**
	 * Return user blocked status.
	 * 
	 * @param userId id of the user which status will be returned.
	 * @return true if user blocked, false if unblocked.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public boolean getUserStatus(int userId) throws DAOException {
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_USER_BLOCKED_STATUS)
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

	protected QueryBuilder getQueryBuilder() throws SQLException {
		return new QueryBuilder();
	}

	/**
	 * Return login of the user by its email
	 * 
	 * @param email email of the user.
	 * @return login of the user.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	@Override
	public String getLoginByEmail(String email) throws DAOException {
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.GET_LOGIN_BY_EMAIL)
				.setStringField(email).executeQuery()) {
			if (rs.next()) {
				return rs.getString(MariaDBConstants.USER_LOGIN_FIELD);
			}
		} catch (SQLException e) {
			throw new DAOReadException("Cannot get user with email " + email + ".", e);

		}
		return null;
	}

	/**
	 * This method returns user form ResultSet.
	 * @param rs ResultSet from which get User.
	 * @return User entity.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
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

	/**
	 * Checks if user exists by login.
	 * @param login login of the user.
	 * @return true if user exists, false if not.
	 * @throws SQLException is thrown when SQLException occurs.
	 */
	private boolean checkUserExistsByLogin(String login) throws SQLException {
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.EXISTS_USER_BY_LOGIN)
				.setStringField(login).executeQuery()) {
			return rs.next();
		}
	}

	/**
	 * Checks if user exists by email.
	 * @param email email of the user.
	 * @return true if user exists, false if not.
	 * @throws SQLException is thrown when SQLException occurs.
	 */
	private boolean checkUserExistsByEmail(String email) throws SQLException {
		try (ResultSet rs = getQueryBuilder().addPreparedStatement(MariaDBConstants.EXISTS_USER_BY_EMAIL)
				.setStringField(email).executeQuery()) {
			return rs.next();
		}
	}
	
}
