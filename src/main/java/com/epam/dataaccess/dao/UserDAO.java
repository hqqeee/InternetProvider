package com.epam.dataaccess.dao;

import java.math.BigDecimal;
import java.util.List;

import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;

/**
 * 
 * DAO interface for managing User entities. Contains methods to access the
 * persistence layer.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public interface UserDAO extends DAO<User> {

	/**
	 * 
	 * Retrieves the salt of a user with the given login.
	 * 
	 * @param login The login of the user.
	 * @return The salt needed for hashing.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public String getSalt(String login) throws DAOException;

	/**
	 * 
	 * Retrieves the user with the given login and password, if it exists.
	 * 
	 * @param login    The login of the user.
	 * @param password The password (hashed) of the user.
	 * @return The User entity.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public User getUser(String login, String password) throws DAOException;

	/**
	 * 
	 * Changes the blocked status of a user.
	 * 
	 * @param blocked The new blocked status (true = blocked, false = unblocked).
	 * @param id      The ID of the user.
	 * @return 0 if the status was not changed, 1 if the status was changed.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public int changeBlocked(boolean blocked, int id) throws DAOException;

	/**
	 * 
	 * Adds a record to the "user has tariff" table.
	 * 
	 * @param userId   The ID of the user.
	 * @param tariffId The ID of the tariff.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public void addTariffToUser(int userId, int tariffId) throws DAOException;

	/**
	 * 
	 * Removes a record from the "user has tariff" table.
	 * 
	 * @param userId   The ID of the user.
	 * @param tariffId The ID of the tariff.
	 * @return 0 if the record was not deleted, 1 if the record was deleted.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public int removeTariffFromUser(int userId, int tariffId) throws DAOException;

	/**
	 * 
	 * Retrieves the balance of a user.
	 * 
	 * @param userId The ID of the user.
	 * @return The balance of the user.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public BigDecimal getUserBalance(int userId) throws DAOException;

	/**
	 * 
	 * Retrieves a list of all users with the role of "subscriber."
	 * 
	 * @return All users with the role of "subscriber."
	 * @throws DAOException If a SQL exception occurs.
	 */
	public List<User> getAllSubscriber() throws DAOException;

	/**
	 * Retrieves a list of all subscribers who have an unblocked status.
	 *
	 * @return A list of all unblocked subscribers.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public List<User> getAllUnblockedSubscriber() throws DAOException;

	/**
	 * Retrieves a list of subscribers who have an unblocked status and have a
	 * tariff with days until next payment equal to 0.
	 * 
	 * @return A list of subscribers who need to be charged.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public List<User> getSubscriberForCharging() throws DAOException;

	/**
	 * Retrieves a filtered list of subscribers for view, with pagination.
	 * 
	 * @param searchField    A part of the login to filter subscribers.
	 * @param offset         The offset of the records.
	 * @param entriesPerPage The number of records to retrieve.
	 * @return A filtered list of subscribers.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public List<User> getSubscriberForView(String searchField, int offset, int entriesPerPage) throws DAOException;

	/**
	 * Retrieves the number of subscribers that contain a certain login part.
	 * 
	 * @param searchField A part of the login to filter subscribers.
	 * @return The number of subscribers that contain the specified login part.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public int getSubscriberNumber(String searchField) throws DAOException;

	/**
	 * Updates a user's password and salt in the persistence layer.
	 * 
	 * @param userId      The id of the user to update.
	 * @param newPassword The new hashed password.
	 * @param salt        The salt used to hash the password.
	 * @return 1 if the password is successfully changed, otherwise 0.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public int changePassword(int userId, String newPassword, String salt) throws DAOException;

	/**
	 * Updates a user's password and salt in the persistence layer.
	 * 
	 * @param email       The email of the user to update.
	 * @param newPassword The new hashed password.
	 * @param salt        The salt used to hash the password.
	 * @return 1 if the password is successfully changed, otherwise 0.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public int changePassword(String email, String newPassword, String salt) throws DAOException;

	/**
	 * Retrieves the blocked status of a user.
	 * 
	 * @param userId The id of the user to retrieve the status for.
	 * @return True if the user is blocked, false otherwise.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public boolean getUserStatus(int userId) throws DAOException;

	/**
	 * Retrieves the login of a user based on their email.
	 * 
	 * @param email The email of the user to retrieve the login for.
	 * @return The login of the user.
	 * @throws DAOException If a SQL exception occurs.
	 */
	public String getLoginByEmail(String email) throws DAOException;;
}
