package com.epam.services;

import java.math.BigDecimal;
import java.util.List;

import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.PasswordNotMatchException;
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserAlreadyHasTariffException;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.services.dto.UserForm;

/**
 * 
 * UserService interface contains methods to interact with user data and perform
 * business logic.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public interface UserService {

	/**
	 * 
	 * Verifies the user's credentials and returns the UserDTO of the user if the
	 * user exists.
	 * 
	 * @param login    The user's login name.
	 * @param password The user's password.
	 * @return The UserDTO of the user with the given credentials.
	 * @throws UserNotFoundException If the user with the given credentials does not
	 *                               exist.
	 * @throws UserServiceException  If an error occurs in the DAO layer.
	 */
	public UserDTO login(String login, String password) throws UserNotFoundException, UserServiceException;

	/**
	 * 
	 * Registers a new user by taking the user form and adding the user to the DAO
	 * layer. Sends a random password to the user's email.
	 * 
	 * @param userForm The user form containing all necessary information to add a
	 *                 new subscriber. The form is not validated.
	 * @throws UserAlreadyExistException If the user is already in the persistent
	 *                                   layer.
	 * @throws UserServiceException      If an error occurs in the DAO layer.
	 */
	public void registerUser(UserForm userForm) throws UserAlreadyExistException, UserServiceException;

	/**
	 * 
	 * Returns all subscribers from the persistent layer.
	 * 
	 * @return A list of all subscribers from the persistent layer.
	 * @throws UserServiceException If an error occurs in the DAO layer.
	 */
	public List<UserDTO> getAllSubscribers() throws UserServiceException;

	/**
	 * 
	 * Returns all unblocked subscribers from the persistent layer.
	 * 
	 * @return A list of all unblocked subscribers from the persistent layer.
	 * @throws UserServiceException If an error occurs in the DAO layer.
	 */
	public List<UserDTO> getAllUnblockedSubscribers() throws UserServiceException;

	/**
	 * 
	 * Returns all unblocked subscribers from the persistent layer with tariffs that
	 * have 0 days until next payment.
	 * 
	 * @return A list of all unblocked subscribers from the persistent layer with
	 *         tariffs that have 0 days until next payment.
	 * @throws UserServiceException If an error occurs in the DAO layer.
	 */
	public List<UserDTO> getSubscriberForCharging() throws UserServiceException;

	/**
	 * 
	 * Returns a filtered and sorted list of UserDTO based on the specified
	 * parameters.
	 * 
	 * @param searchField    The sortable field as a string (e.g. "name", "rate").
	 * @param page           The page number to view, starting from 1.
	 * @param entriesPerPage The number of records per page.
	 * @return A list of UserDTO matching the specified sorting and filtering
	 *         parameters.
	 * @throws UserServiceException If an error occurs in the DAO layer.
	 */
	public List<UserDTO> viewSubscribers(String searchField, int page, int entriesPerPage) throws UserServiceException;

	/**
	 * This method returns the user with a specific id.
	 * 
	 * @param userId the user id to get.
	 * @return a UserDTO object representing the user information.
	 * @throws UserNotFoundException is thrown when the user with this id does not
	 *                               exist.
	 * @throws UserServiceException  is thrown when something goes wrong in the DAO
	 *                               layer.
	 */
	public UserDTO getUserById(int userId) throws UserNotFoundException, UserServiceException;

	/**
	 * This method returns the balance of the user with specific user id.
	 * 
	 * @param userId the user id to get the balance of.
	 * @return a BigDecimal object representing the user's balance.
	 * @throws UserServiceException is thrown when something goes wrong in the DAO
	 *                              layer.
	 */
	public BigDecimal getUserBalance(int userId) throws UserServiceException;

	/**
	 * This method checks the blocked status of the user with specific user id.
	 * 
	 * @param userId the user id to check the blocked status of.
	 * @return a boolean representing the user's blocked status. True if the user is
	 *         blocked, false if unblocked.
	 * @throws UserServiceException is thrown when something goes wrong in the DAO
	 *                              layer.
	 */
	public boolean getUserStatus(int userId) throws UserServiceException;

	/**
	 * This method returns the number of subscribers that contain a specific text in
	 * their login.
	 * 
	 * @param searchField the text to search in the subscriber's login.
	 * @return an integer representing the number of subscribers that contain the
	 *         specified text in their login.
	 * @throws UserServiceException is thrown when something goes wrong in the DAO
	 *                              layer.
	 */
	public int getSubscribersNumber(String searchField) throws UserServiceException;

	/**
	 * This method removes the user with a specific id from the persistent layer.
	 * 
	 * @param userId the id of the user to remove.
	 * @throws UserServiceException is thrown when something goes wrong in the DAO
	 *                              layer.
	 */
	public void removeUser(int userId) throws UserServiceException;

	/**
	 * This method changes the status of a user.
	 * 
	 * @param blocked the new status of the user. True if blocked, false if
	 *                unblocked.
	 * @param id      the id of the user to change the status of.
	 * @throws UserServiceException is thrown when something goes wrong in the DAO
	 *                              layer.
	 */
	public void changeUserStatus(boolean blocked, int id) throws UserServiceException;

	/**
	 * This method changes the balance of a user.
	 * 
	 * @param userId      the id of the user to change the balance of.
	 * @param difference  the difference by which to change the balance.
	 * @param description a description of the transaction.
	 * @throws UserServiceException         is thrown when something goes wrong in
	 *                                      the DAO layer.
	 * @throws NegativeUserBalanceException is thrown when the user's balance will
	 *                                      become negative after the transaction.
	 */
	public void changeUserBalance(int userId, BigDecimal difference, String description)
			throws UserServiceException, NegativeUserBalanceException;

	/**
	 * 
	 * Charges the user a fee for using the specified tariffs.
	 * 
	 * @param userId        ID of the user to be charged
	 * @param unpaidTariffs List of Tariffs that the user needs to pay for
	 * @throws UserServiceException         If an error occurs in the DAO layer
	 * @throws NegativeUserBalanceException If the user's balance will become
	 *                                      negative after the transaction
	 */
	public void chargeUserForTariffsUsing(int userId, List<TariffDTO> unpaidTariffs)
			throws UserServiceException, NegativeUserBalanceException;

	/**
	 * 
	 * Changes the user's password.
	 * 
	 * @param userId          ID of the user whose password is being changed
	 * @param currentPassword The current password of the user
	 * @param newPassword     The new password for the user
	 * @throws UserServiceException      If an error occurs in the DAO layer
	 * @throws UserNotFoundException     If a user with the specified ID does not
	 *                                   exist
	 * @throws PasswordNotMatchException If the current password is incorrect
	 */
	public void changePassword(int userId, String currentPassword, String newPassword)
			throws UserServiceException, UserNotFoundException, PasswordNotMatchException;

	/**
	 * 
	 * Resets the user's password to a random one and sends the new password to the
	 * provided email.
	 * 
	 * @param email The email of the user whose password will be reset
	 * @throws UserServiceException If an error occurs in the DAO layer
	 */
	public void resetPassword(String email) throws UserServiceException;

	/**
	 * 
	 * Records that a user has a specified tariff.
	 * 
	 * @param userId   ID of the user who will be added to the tariff
	 * @param tariffId ID of the tariff that will be added to the user
	 * @throws UserAlreadyHasTariffException If a record with the specified user ID
	 *                                       and tariff ID already exists
	 * @throws UserServiceException          If an error occurs in the DAO layer
	 * @throws NegativeUserBalanceException  If the user does not have enough
	 *                                       balance to pay for the first tariff
	 *                                       period
	 */
	public void addTariffToUser(int userId, int tariffId)
			throws UserAlreadyHasTariffException, UserServiceException, NegativeUserBalanceException;

	/**
	 * This method removes record about user has tariff.
	 * 
	 * @param userId   id of the user.
	 * @param tariffId id of the tariff.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	public void removeTariffFromUser(int userId, int tariffId) throws UserServiceException;
}
